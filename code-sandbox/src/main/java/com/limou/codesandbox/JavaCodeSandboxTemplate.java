package com.limou.codesandbox;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import com.limou.codesandbox.model.ExecuteCodeRequest;
import com.limou.codesandbox.model.ExecuteCodeResponse;
import com.limou.codesandbox.model.ExecuteMessage;
import com.limou.codesandbox.model.JudgeInfo;
import com.limou.codesandbox.utils.ProcessUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Slf4j
public abstract class JavaCodeSandboxTemplate implements CodeSandBox {
    private static final String GLOBAL_CODE_DIR_NAME = "tmpCode";
    private static final String GLOBAL_JAVA_CLASS_NAME = "Main.java";

    //    public static void main(String[] args) {
//
//        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
//                .inputList(Arrays.asList("1 2"))
//                .code("public class Main { public static void main(String[] args) { System.out.println(args[0] + args[1]); } }")
//                .language("java").build();
//
//        JavaCodeSandboxTemplate javaNativeCodeSandbox = new JavaCodeSandboxTemplate();
//        javaNativeCodeSandbox.executeCode(executeCodeRequest);
//    }
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();
        String code = executeCodeRequest.getCode();
        String language = executeCodeRequest.getLanguage();

//        1. 把用户的代码保存为文件
        File userCodeFile = saveCodeToFile(code);

//        2. 编译代码，得到 class 文件
        ExecuteMessage compileFileExecuteMessage = compileFile(userCodeFile);
        System.out.println(compileFileExecuteMessage);

        // 3. 执行代码，得到输出结果
        List<ExecuteMessage> executeMessageList = runFile(userCodeFile, inputList);

//        4. 收集整理输出结果
        ExecuteCodeResponse outputResponse = getOutputResponse(executeMessageList);

//        5. 文件清理
        boolean b = deleteFile(userCodeFile);
        if (!b) {
            log.error("deleteFile error, userCodeFilePath = {}", userCodeFile.getAbsolutePath());
        }
        return outputResponse;
    }

    public ExecuteCodeResponse getOutputResponse(List<ExecuteMessage> executeMessageList) {

        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        ArrayList<String> outPutList = new ArrayList<>();
        long maxTime = 0;
        for (ExecuteMessage executeMessage : executeMessageList) {
            if (executeMessage.getErrorMessage() != null) {
                executeCodeResponse.setMessage(executeMessage.getErrorMessage());
                executeCodeResponse.setStatus(3);//表示运行错误
                break;
            }
            outPutList.add(executeMessage.getMessage());
            if (executeMessage.getTime() != null)
                maxTime = Math.max(maxTime, executeMessage.getTime());

        }
        if (outPutList.size() == executeMessageList.size())
            executeCodeResponse.setStatus(1);//表示正常运行完成
        executeCodeResponse.setOutputList(outPutList);
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setTime(maxTime);
        executeCodeResponse.setJudgeInfo(judgeInfo);
        return executeCodeResponse;
    }

    public boolean deleteFile(File userCodeFile) {
        String userCodePathName = userCodeFile.getParentFile().getAbsolutePath();
        if (FileUtil.exist(userCodePathName)) {
            System.out.println("删除目录：" + userCodePathName);
            FileUtil.del(userCodePathName);
            return true;
        }
        return false;
    }

    public List<ExecuteMessage> runFile(File userCodeFile, List<String> inputList) {
        String userCodePathName = userCodeFile.getParentFile().getAbsolutePath();
        ArrayList<ExecuteMessage> executeMessageList = new ArrayList<>();
        for (String inputArgs : inputList) {
            String runCmd = String.format("java -Dfile.encoding=UTF-8 -cp %s Main %s", userCodePathName, inputArgs);
            //执行
            try {
                Process runProcess = Runtime.getRuntime().exec(runCmd);
                ExecuteMessage executeMessage = ProcessUtils.runProcessAndGetMessage(runProcess, "运行");
                executeMessageList.add(executeMessage);
                System.out.println(executeMessage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return executeMessageList;

    }

    public ExecuteMessage compileFile(File userCodeFile) {
        String userCodePath = userCodeFile.getAbsolutePath();
        String compliedCmd = String.format("javac -encoding utf-8 %s", userCodePath);
        try {
            Process complieProcess = Runtime.getRuntime().exec(compliedCmd);
            ExecuteMessage executeMessage = ProcessUtils.runProcessAndGetMessage(complieProcess, "编译");
            return executeMessage;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public File saveCodeToFile(String code) {
        String userDir = System.getProperty("user.dir");
        String globalCodePathName = userDir + File.separator + GLOBAL_CODE_DIR_NAME;
        //第一次如果没有先创建
        if (!FileUtil.exist(globalCodePathName)) {
            FileUtil.mkdir(globalCodePathName);
        }
        //把用户的信息进行隔离存放
        String userCodePathName = globalCodePathName + File.separator + UUID.randomUUID();
        String userCodePath = userCodePathName + File.separator + GLOBAL_JAVA_CLASS_NAME;
        FileUtil.writeString(code, userCodePath, "utf-8");
        return FileUtil.file(userCodePathName);
    }

    private ExecuteCodeResponse getErrorResponse(Throwable error) {
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setMessage(error.getMessage());
        executeCodeResponse.setStatus(2);
        executeCodeResponse.setOutputList(new ArrayList<>());
        executeCodeResponse.setJudgeInfo(new JudgeInfo());
        return executeCodeResponse;
    }
}

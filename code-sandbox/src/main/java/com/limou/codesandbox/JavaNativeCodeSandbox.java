package com.limou.codesandbox;

import cn.hutool.core.date.StopWatch;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.stream.StreamUtil;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.StrUtil;
import com.limou.codesandbox.model.ExecuteCodeRequest;
import com.limou.codesandbox.model.ExecuteCodeResponse;
import com.limou.codesandbox.model.ExecuteMessage;
import com.limou.codesandbox.model.JudgeInfo;
import com.limou.codesandbox.utils.ProcessUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JavaNativeCodeSandbox implements CodeSandBox {
    private static final String GLOBAL_CODE_DIR_NAME = "tmpCode";
    private static final String GLOBAL_JAVA_CLASS_NAME = "Main.java";

    public static void main(String[] args) {

        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .inputList(Arrays.asList("1 2"))
                .code("public class Main { public static void main(String[] args) { System.out.println(args[0] + args[1]); } }")
                .language("java").build();

        JavaNativeCodeSandbox javaNativeCodeSandbox = new JavaNativeCodeSandbox();
        javaNativeCodeSandbox.executeCode(executeCodeRequest);
    }


    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();
        String code = executeCodeRequest.getCode();
        String language = executeCodeRequest.getLanguage();
//        1. 把用户的代码保存为文件
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
        //        2. 编译代码，得到 class 文件
        String compliedCmd = String.format("javac -encoding utf-8 %s", userCodePath);
        try {
            Process complieProcess = Runtime.getRuntime().exec(compliedCmd);
            ProcessUtils.runProcessAndGetMessage(complieProcess, "编译");
        } catch (Exception e) {
            return  getErrorResponse(e);
        }

        //异常退出
//        3. 执行代码，得到输出结果
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
               return  getErrorResponse(e);
            }
        }
        //  4. 收集整理输出结果
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
        if (outPutList.size() == inputList.size())
            executeCodeResponse.setStatus(1);//表示正常运行完成
        executeCodeResponse.setOutputList(outPutList);
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setTime(maxTime);
        executeCodeResponse.setJudgeInfo(judgeInfo);
//        5. 文件清理，释放空间
        if (FileUtil.exist(userCodePathName)) {
            System.out.println("删除目录：" + userCodePathName);
            FileUtil.del(userCodePathName);
        }
//        6. 错误处理，提升程序健壮性
        return executeCodeResponse;
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

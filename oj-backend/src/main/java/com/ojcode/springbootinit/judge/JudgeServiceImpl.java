package com.ojcode.springbootinit.judge;

import cn.hutool.json.JSONUtil;
import com.ojcode.springbootinit.common.ErrorCode;
import com.ojcode.springbootinit.exception.BusinessException;
import com.ojcode.springbootinit.judge.codesandbox.CodeSandBox;
import com.ojcode.springbootinit.judge.codesandbox.CodeSandboxFactory;
import com.ojcode.springbootinit.judge.codesandbox.CodeSandboxProxy;
import com.ojcode.springbootinit.judge.codesandbox.model.CodeSandboxEnum;
import com.ojcode.springbootinit.judge.codesandbox.model.ExecuteCodeRequest;
import com.ojcode.springbootinit.judge.codesandbox.model.ExecuteCodeResponse;
import com.ojcode.springbootinit.model.dto.question.JudgeCase;
import com.ojcode.springbootinit.model.dto.question.JudgeConfig;
import com.ojcode.springbootinit.model.dto.questionSubmit.JudgeInfo;
import com.ojcode.springbootinit.model.entity.Question;
import com.ojcode.springbootinit.model.entity.QuestionSubmit;
import com.ojcode.springbootinit.model.enums.JudgeInfoMessageEnum;
import com.ojcode.springbootinit.model.enums.QuestionSubmitLanguageEnum;
import com.ojcode.springbootinit.model.enums.QuestionSubmitStatusEnum;
import com.ojcode.springbootinit.model.vo.QuestionSubmitVO;
import com.ojcode.springbootinit.service.QuestionService;
import com.ojcode.springbootinit.service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JudgeServiceImpl implements JudgeService {
    @Value("${codeSandbox.type:example}")
    private String type;
    @Resource
    private QuestionService questionsService;
    @Resource
    private QuestionSubmitService questionSubmitService;

    @Override
    public QuestionSubmitVO doJudge(Long questionSubmitId) {
        //1）传入题目的提交 id，获取到对应的题目、提交信息（包含代码、编程语言等）
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionsService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        //2）如果题目提交状态不为等待中，就不用重复执行了
        Integer questionSubmitStatus = questionSubmit.getStatus();
        if (!questionSubmitStatus.equals(QuestionSubmitStatusEnum.WAITING.getValue()))
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目正在判题中");
        //3）更改判题（题目提交）的状态为“判题中”，防止重复执行，也能让用户即时看到状态
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmit.getId());
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean result = questionSubmitService.updateById(questionSubmitUpdate);
        if (!result) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新错误");
        }
        //4）调用沙箱，获取到执行结果
        CodeSandboxEnum enumByValue = CodeSandboxEnum.getEnumByValue(type);
        if (enumByValue == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "不支持的编程语言");
        }
        CodeSandBox codeSandbox = CodeSandboxFactory.newInstance(enumByValue);
        codeSandbox = new CodeSandboxProxy(codeSandbox);
        String code = questionSubmitUpdate.getCode();
        String language = questionSubmitUpdate.getLanguage();
        //获得输入样例
        String judgeCase = question.getJudgeCase();
        //装换为数组
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCase, JudgeCase.class);
        //得到里面的input
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        //5）根据沙箱的执行结果，设置题目的判题状态和信息

        String message = executeCodeResponse.getMessage();
        Integer status = executeCodeResponse.getStatus();
        List<String> outputList = executeCodeResponse.getOutputList();
        JudgeInfoMessageEnum judgeInfoMessageEnum = JudgeInfoMessageEnum.WAITING;
        JudgeInfo judgeInfo = executeCodeResponse.getJudgeInfo();
        if(outputList.size()!= inputList.size()){
            judgeInfoMessageEnum=JudgeInfoMessageEnum.WRONG_ANSWER;
            return null;
        }
        //判断执行结果
        for (int i = 0; i < judgeCaseList.size(); i++) {
            if(!judgeCaseList.get(i).getOutput().equals(outputList.get(i))){
                judgeInfoMessageEnum=JudgeInfoMessageEnum.WRONG_ANSWER;
               return null;
            }
        }
        //判断题目的限制是否符合
        JudgeConfig judgeConfig = JSONUtil.toBean(question.getJudgeConfig(), JudgeConfig.class);
        Long needMemory = judgeInfo.getMemory();
        Long needTime = judgeInfo.getTime();
        if (needMemory > judgeConfig.getMemoryLimit()) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED;
            return null;
        }
        if (needTime > judgeConfig.getTimeLimit()) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED;
            return null;
        }
        return null;
    }
}

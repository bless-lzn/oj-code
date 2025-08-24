package com.ojcode.springbootinit.judge.codesandbox.strategy;

import cn.hutool.json.JSONUtil;
import com.ojcode.springbootinit.model.dto.question.JudgeCase;
import com.ojcode.springbootinit.model.dto.question.JudgeConfig;
import com.ojcode.springbootinit.model.dto.questionSubmit.JudgeInfo;
import com.ojcode.springbootinit.model.enums.JudgeInfoMessageEnum;

import java.util.List;

public class JavaJudgeStrategyImpl implements JudgeStrategy {
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        List<String> outputList = judgeContext.getOutputList();
        JudgeInfoMessageEnum judgeInfoMessageEnum = JudgeInfoMessageEnum.ACCEPTED;
        JudgeInfo judgeInfo = judgeContext.getJudgeInfo();
        List<String> inputList = judgeContext.getInputList();
        if (outputList.size() != inputList.size()) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
            return null;
        }
        //判断执行结果
        List<JudgeCase> judgeCaseList = judgeContext.getJudgeCaseList();
        for (int i = 0; i < judgeCaseList.size(); i++) {
            if (!judgeCaseList.get(i).getOutput().equals(outputList.get(i))) {
                judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
                return null;
            }
        }
        //判断题目的限制是否符合
        JudgeConfig judgeConfig = JSONUtil.toBean(judgeContext.getQuestion().getJudgeConfig(), JudgeConfig.class);
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
        JudgeInfo judgeInfoResponse = new JudgeInfo();
        judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
        judgeInfoResponse.setMemory(needMemory);
        judgeInfoResponse.setTime(needTime);
        return judgeInfoResponse;
    }

}

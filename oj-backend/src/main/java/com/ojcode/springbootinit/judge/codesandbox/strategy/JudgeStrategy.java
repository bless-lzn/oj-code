package com.ojcode.springbootinit.judge.codesandbox.strategy;

import com.ojcode.springbootinit.judge.codesandbox.model.JudgeInfo;

/**
 * @description: 策略模式
 * @author: LZN
 * @date: 2023/09/07 10:07
 **/
public interface JudgeStrategy {

    JudgeInfo doJudge(JudgeContext judgeContext);

}

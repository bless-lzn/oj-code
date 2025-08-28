package com.limou.backendjudgeservice.service.strategy;


import com.limou.backendmodel.model.codeSandBox.JudgeInfo;

/**
 * @description: 策略模式
 * @author: LZN
 * @date: 2023/09/07 10:07
 **/
public interface JudgeStrategy {

    JudgeInfo doJudge(JudgeContext judgeContext);

}

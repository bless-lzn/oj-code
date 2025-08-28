package com.limou.backendjudgeservice.service.strategy;

import com.limou.backendmodel.model.codeSandBox.JudgeInfo;
import com.limou.backendmodel.model.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
 * 判题管理（简化调用）
 */
@Service
public class JudgeManager {

    /**
     * 执行判题
     *
     * @param judgeContext
     * @return
     */
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategyImpl();
        if ("java".equals(language)) {
            judgeStrategy = new JavaJudgeStrategyImpl();
        }
        return judgeStrategy.doJudge(judgeContext);
    }

}

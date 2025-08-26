package com.ojcode.springbootinit.judge;

import com.ojcode.springbootinit.model.entity.QuestionSubmit;

public interface JudgeService {
    QuestionSubmit doJudge(Long questionSubmitId);
}

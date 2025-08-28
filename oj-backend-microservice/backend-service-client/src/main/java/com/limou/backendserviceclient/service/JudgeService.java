package com.limou.backendserviceclient.service;

import com.limou.backendmodel.model.entity.QuestionSubmit;

public interface JudgeService {
    QuestionSubmit doJudge(Long questionSubmitId);
}

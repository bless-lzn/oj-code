package com.ojcode.springbootinit.judge;

import com.ojcode.springbootinit.judge.codesandbox.model.ExecuteCodeRequest;
import com.ojcode.springbootinit.judge.codesandbox.model.ExecuteCodeResponse;
import com.ojcode.springbootinit.model.dto.questionSubmit.JudgeInfo;
import com.ojcode.springbootinit.model.vo.QuestionSubmitVO;

public interface JudgeService {
    QuestionSubmitVO doJudge(Long questionSubmitId);
}

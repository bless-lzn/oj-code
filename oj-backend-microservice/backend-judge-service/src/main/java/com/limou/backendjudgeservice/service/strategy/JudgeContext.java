package com.limou.backendjudgeservice.service.strategy;


import com.limou.backendmodel.model.codeSandBox.JudgeInfo;
import com.limou.backendmodel.model.dto.question.JudgeCase;
import com.limou.backendmodel.model.entity.Question;
import com.limou.backendmodel.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

@Data
public class JudgeContext {

    private JudgeInfo judgeInfo;

    private List<String> inputList;

    private List<String> outputList;

    private List<JudgeCase> judgeCaseList;

    private Question question;

    private QuestionSubmit questionSubmit;

}

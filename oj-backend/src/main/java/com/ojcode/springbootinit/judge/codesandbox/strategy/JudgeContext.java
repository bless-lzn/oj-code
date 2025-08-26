package com.ojcode.springbootinit.judge.codesandbox.strategy;

import com.ojcode.springbootinit.model.dto.question.JudgeCase;
import com.ojcode.springbootinit.judge.codesandbox.model.JudgeInfo;
import com.ojcode.springbootinit.model.entity.Question;
import com.ojcode.springbootinit.model.entity.QuestionSubmit;
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

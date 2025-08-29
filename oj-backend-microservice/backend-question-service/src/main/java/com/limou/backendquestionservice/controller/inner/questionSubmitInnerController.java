package com.limou.backendquestionservice.controller.inner;

import com.limou.backendmodel.model.entity.QuestionSubmit;
import com.limou.backendquestionservice.service.QuestionSubmitService;
import com.limou.backendserviceclient.service.QuestionFeignClient;
import com.limou.backendserviceclient.service.QuestionSubmitFeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/question_submit/inner")
public class questionSubmitInnerController implements QuestionSubmitFeignClient {
    @Resource
    private QuestionSubmitService questionSubmitService;
    @Override

    public QuestionSubmit getQuestionSubmit(Long id) {
        return questionSubmitService.getById(id);
    }

    @Override
    public boolean updateQuestionSubmit(QuestionSubmit questionSubmit) {
        return questionSubmitService.updateById(questionSubmit);
    }
}

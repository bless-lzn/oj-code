package com.limou.backendquestionservice.controller.inner;

import com.limou.backendmodel.model.entity.Question;
import com.limou.backendquestionservice.service.QuestionService;
import com.limou.backendserviceclient.service.QuestionFeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
//公共头/api/question
@RequestMapping("/inner")
public class questionInnerController implements QuestionFeignClient {
    @Resource
    private QuestionService questionService;

    @Override
    @GetMapping("/get/id")
    public Question getQuestion(Long id) {
        return questionService.getById(id);
    }
}

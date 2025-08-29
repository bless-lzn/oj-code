package com.limou.backendserviceclient.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.limou.backendmodel.model.entity.QuestionSubmit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


/**
* @author henan
* @description 针对表【question_submit(题目提交)】的数据库操作Service
* @createDate 2025-08-18 19:11:56
*/
@FeignClient(name = "backend-question-service",path = "/api/question/question_submit/inner",contextId = "questionSubmitFeignClient")
public interface QuestionSubmitFeignClient {

    @GetMapping("/get/id")
    QuestionSubmit getQuestionSubmit(@RequestParam("id") Long id);

    @PostMapping("/update")
    boolean updateQuestionSubmit(@RequestBody QuestionSubmit questionSubmit);

}

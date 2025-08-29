package com.limou.backendserviceclient.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.limou.backendmodel.model.entity.Question;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 帖子服务
 *
 */
@FeignClient(name = "backend-question-service",path = "/api/question/inner",contextId = "questionClient")
public interface QuestionFeignClient {
    //得到题目
    @GetMapping("/get/id")
    Question getQuestion(@RequestParam("id") Long id);


}

package com.limou.backendserviceclient.service;

import com.limou.backendmodel.model.entity.QuestionSubmit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "backend-judge-service",path = "/api/judge/inner")
public interface JudgeFeignClient {
    @GetMapping("/get/id")
    QuestionSubmit doJudge(@RequestParam("id") Long questionSubmitId);
}

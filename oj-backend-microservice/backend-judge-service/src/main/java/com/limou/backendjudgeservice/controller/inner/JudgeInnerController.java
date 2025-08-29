package com.limou.backendjudgeservice.controller.inner;

import com.limou.backendjudgeservice.service.JudgeService;
import com.limou.backendmodel.model.entity.QuestionSubmit;
import com.limou.backendserviceclient.service.JudgeFeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/inner")
public class JudgeInnerController implements JudgeFeignClient {
    @Resource
    private JudgeService judgeService;

    @Override
    @RequestMapping("/get/id")
    public QuestionSubmit doJudge(Long questionSubmitId) {
        return judgeService.doJudge(questionSubmitId);
    }
}

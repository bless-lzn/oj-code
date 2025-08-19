package com.ojcode.springbootinit.service;

import com.ojcode.springbootinit.model.dto.questionSubmit.QuestionSubmitAddRequest;
import com.ojcode.springbootinit.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ojcode.springbootinit.model.entity.User;

/**
* @author henan
* @description 针对表【question_submit(题目提交)】的数据库操作Service
* @createDate 2025-08-18 19:11:56
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {
    /**
     * 提交
     *
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return
     */
    long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);


}

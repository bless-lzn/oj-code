package com.ojcode.springbootinit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ojcode.springbootinit.common.ErrorCode;
import com.ojcode.springbootinit.exception.BusinessException;
import com.ojcode.springbootinit.mapper.QuestionSubmitMapper;
import com.ojcode.springbootinit.model.dto.questionSubmit.QuestionSubmitAddRequest;
import com.ojcode.springbootinit.model.entity.Question;
import com.ojcode.springbootinit.model.entity.QuestionSubmit;
import com.ojcode.springbootinit.model.entity.User;
import com.ojcode.springbootinit.model.enums.QuestionSubmitLanguageEnum;
import com.ojcode.springbootinit.model.enums.QuestionSubmitStatusEnum;
import com.ojcode.springbootinit.service.QuestionService;
import com.ojcode.springbootinit.service.QuestionSubmitService;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public  class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
        implements QuestionSubmitService {

    @Resource
    private QuestionService questionService;

    /**
     * 提交题目
     *
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return
     */
    @Override
    public long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser) {
       //判断类型是否正确
        QuestionSubmitLanguageEnum languageEnum = QuestionSubmitLanguageEnum.getEnumByValue(questionSubmitAddRequest.getLanguage());
       if(languageEnum==null){
           throw new BusinessException(ErrorCode.PARAMS_ERROR, "编程语言类型错误");
       }
        // 判断实体是否存在，根据类别获取实
        Question question = questionService.getById(questionSubmitAddRequest.getQuestionId());
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 是否已提交
        long userId = loginUser.getId();
        // 每个用户串行提交
        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setUserId(userId);
        questionSubmit.setQuestionId(question.getId());
        questionSubmit.setCode(questionSubmitAddRequest.getCode());
        questionSubmit.setLanguage(questionSubmitAddRequest.getLanguage());
        //todo 设置初始状态
        questionSubmit.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        questionSubmit.setJudgeInfo("{}");
        boolean save = this.save(questionSubmit);
        if(!save){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目提交失败");
        }
        return questionSubmit.getId();

    }


}
package com.ojcode.springbootinit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ojcode.springbootinit.mapper.QuestionSubmitMapper;
import com.ojcode.springbootinit.model.entity.QuestionSubmit;
import com.ojcode.springbootinit.service.QuestionSubmitService;
import org.springframework.stereotype.Service;

@Service
public  class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
        implements QuestionSubmitService {

}
package com.ojcode.springbootinit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ojcode.springbootinit.model.entity.Question;
import com.ojcode.springbootinit.service.QuestionService;
import com.ojcode.springbootinit.mapper.QuestionMapper;
import org.springframework.stereotype.Service;

/**
* @author henan
* @description 针对表【question(题目)】的数据库操作Service实现
* @createDate 2025-08-18 19:08:08
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService{

}





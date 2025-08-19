package com.ojcode.springbootinit.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ojcode.springbootinit.model.dto.question.QuestionQueryRequest;
import com.ojcode.springbootinit.model.dto.questionSubmit.QuestionSubmitAddRequest;
import com.ojcode.springbootinit.model.dto.questionSubmit.QuestionSubmitQueryRequest;
import com.ojcode.springbootinit.model.entity.Question;
import com.ojcode.springbootinit.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ojcode.springbootinit.model.entity.User;
import com.ojcode.springbootinit.model.vo.QuestionSubmitVO;
import com.ojcode.springbootinit.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;

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


    /**
     * 获取查询条件
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);
//
//    /**
//     * 从 ES 查询
//     *
//     * @param questionQueryRequest
//     * @return
//     */
//    Page<Question> searchFromEs(QuestionQueryRequest questionQueryRequest);

    /**
     * 获取帖子封装
     *
     * @param questionSubmit
     * @param user
     * @return
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User user);

    /**
     * 分页获取帖子封装
     *
     * @param questionSubmitPage
     * @param user
     * @return
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User user);


}

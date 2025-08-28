package com.limou.backendserviceclient.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.limou.backendmodel.model.dto.questionSubmit.QuestionSubmitAddRequest;
import com.limou.backendmodel.model.dto.questionSubmit.QuestionSubmitQueryRequest;
import com.limou.backendmodel.model.entity.QuestionSubmit;
import com.limou.backendmodel.model.entity.User;
import com.limou.backendmodel.model.vo.QuestionSubmitVO;


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

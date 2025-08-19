package com.ojcode.springbootinit.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ojcode.springbootinit.common.BaseResponse;
import com.ojcode.springbootinit.common.ErrorCode;
import com.ojcode.springbootinit.common.ResultUtils;
import com.ojcode.springbootinit.exception.BusinessException;
import com.ojcode.springbootinit.model.dto.questionSubmit.QuestionSubmitAddRequest;
import com.ojcode.springbootinit.model.dto.questionSubmit.QuestionSubmitQueryRequest;
import com.ojcode.springbootinit.model.entity.QuestionSubmit;
import com.ojcode.springbootinit.model.entity.User;
import com.ojcode.springbootinit.model.vo.QuestionSubmitVO;
import com.ojcode.springbootinit.service.QuestionSubmitService;
import com.ojcode.springbootinit.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 帖子点赞接口
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@RestController
@RequestMapping("/question_submit")
@Slf4j
public class QuestionSubmitController {

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private UserService userService;

    /**
     * 点赞 / 取消点赞
     *
     * @param questionSubmitAddRequest
     * @param request
     * @return resultNum 本次点赞变化数
     */
    @PostMapping("/")
    public BaseResponse<Long> doQuestionSubmit(@RequestBody QuestionSubmitAddRequest questionSubmitAddRequest,
                                         HttpServletRequest request) {
        if (questionSubmitAddRequest == null || questionSubmitAddRequest.getQuestionId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 登录才能点赞
        final User loginUser = userService.getLoginUser(request);

        long result = questionSubmitService.doQuestionSubmit(questionSubmitAddRequest, loginUser);
        return ResultUtils.success(result);
    }


    @PostMapping("/list/page")
    public BaseResponse<Page<QuestionSubmitVO>> listQuestionSubmitVOByPage(@RequestBody QuestionSubmitQueryRequest questionSubmitQueryRequest,
                                                                           HttpServletRequest request){
        if (questionSubmitQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Page<QuestionSubmit> questionSubmitPage = questionSubmitService.page(new Page<>(questionSubmitQueryRequest.getCurrent(), questionSubmitQueryRequest.getPageSize()),
                questionSubmitService.getQueryWrapper(questionSubmitQueryRequest));
        User user = userService.getLoginUser(request);
        Page<QuestionSubmitVO> questionSubmitVOPage = questionSubmitService.getQuestionSubmitVOPage(questionSubmitPage, user);
        return ResultUtils.success(questionSubmitVOPage);
    }

}

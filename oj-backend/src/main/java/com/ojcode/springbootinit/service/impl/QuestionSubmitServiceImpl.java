package com.ojcode.springbootinit.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ojcode.springbootinit.common.ErrorCode;
import com.ojcode.springbootinit.constant.CommonConstant;
import com.ojcode.springbootinit.exception.BusinessException;
import com.ojcode.springbootinit.judge.JudgeService;
import com.ojcode.springbootinit.mapper.QuestionSubmitMapper;
import com.ojcode.springbootinit.model.dto.question.QuestionQueryRequest;
import com.ojcode.springbootinit.model.dto.questionSubmit.QuestionSubmitAddRequest;
import com.ojcode.springbootinit.model.dto.questionSubmit.QuestionSubmitQueryRequest;
import com.ojcode.springbootinit.model.entity.Question;
import com.ojcode.springbootinit.model.entity.QuestionSubmit;
import com.ojcode.springbootinit.model.entity.User;
import com.ojcode.springbootinit.model.enums.QuestionSubmitLanguageEnum;
import com.ojcode.springbootinit.model.enums.QuestionSubmitStatusEnum;
import com.ojcode.springbootinit.model.enums.UserRoleEnum;
import com.ojcode.springbootinit.model.vo.QuestionSubmitVO;
import com.ojcode.springbootinit.model.vo.QuestionVO;
import com.ojcode.springbootinit.model.vo.UserVO;
import com.ojcode.springbootinit.service.QuestionService;
import com.ojcode.springbootinit.service.QuestionSubmitService;
import com.ojcode.springbootinit.service.UserService;
import com.ojcode.springbootinit.utils.SqlUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
        implements QuestionSubmitService {

    @Resource
    private QuestionService questionService;
    @Resource
    private UserService userService;
    @Resource
    @Lazy
    private JudgeService judgeService;

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
        if (languageEnum == null) {
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
        if (!save) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目提交失败");
        }

            judgeService.doJudge(questionSubmit.getId());

        return questionSubmit.getId();
    }

    @Override
    public QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest) {
        QueryWrapper<QuestionSubmit> queryWrapper = new QueryWrapper<>();
        if (questionSubmitQueryRequest == null) {
            return queryWrapper;
        }
        String language = questionSubmitQueryRequest.getLanguage();
        Integer status = questionSubmitQueryRequest.getStatus();
        Long questionId = questionSubmitQueryRequest.getQuestionId();
        Long userId = questionSubmitQueryRequest.getUserId();
        String sortOrder = questionSubmitQueryRequest.getSortOrder();
        String sortField = questionSubmitQueryRequest.getSortField();

        queryWrapper.eq(ObjectUtils.isNotEmpty(language), "language", language);
        queryWrapper.eq(ObjectUtils.isNotEmpty(status), "status", status);
        queryWrapper.eq(ObjectUtils.isNotEmpty(questionId), "questionId", questionId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    @Override
    public QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User user) {
        QuestionSubmitVO questionSubmitVO = QuestionSubmitVO.objToVo(questionSubmit);
        //本人和管理员可以查看所有提交
        if (user.getId() != questionSubmit.getUserId() && !userService.isAdmin(user)) {
            questionSubmitVO.setCode(null);
        }

        return questionSubmitVO;
    }

    @Override
    public Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User user) {
        List<QuestionSubmit> questionSubmitList = questionSubmitPage.getRecords();
        Page<QuestionSubmitVO> questionSubmitVOPage = new Page<>(questionSubmitPage.getCurrent(), questionSubmitPage.getSize(), questionSubmitPage.getTotal());
        if (CollUtil.isEmpty(questionSubmitList)) {
            return questionSubmitVOPage;
        }
        // 填充信息
        List<QuestionSubmitVO> questionSubmitVOList = questionSubmitList.stream().map(questionSubmit -> {
            return this.getQuestionSubmitVO(questionSubmit, user);
        }).collect(Collectors.toList());
        questionSubmitVOPage.setRecords(questionSubmitVOList);
        return questionSubmitVOPage;
    }


}
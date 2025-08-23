package com.ojcode.springbootinit.judge.codesandbox.model;

import com.ojcode.springbootinit.model.dto.questionSubmit.JudgeInfo;

import java.util.List;

public class ExecuteCodeResponse {
  //接口信息
    private String message;
    //执行状态
    private Integer status;
    //判题信息
    private JudgeInfo judgeInfo;
}

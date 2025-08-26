package com.ojcode.springbootinit.judge.codesandbox.model;

import lombok.Data;

import java.util.List;

@Data
public class ExecuteCodeResponse {
    //接口信息
    private String message;
    //执行状态
    private Integer status;
    //输出列表
    private List<String> outputList;
    //判题信息
    private JudgeInfo judgeInfo;
}

package com.limou.backendmodel.model.codeSandBox;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteCodeRequest {
    //输入列表
    private List<String> inputList;

    //状态码
    private String code;

    //语言
    private String language;
}

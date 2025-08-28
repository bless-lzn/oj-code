package com.limou.backendjudgeservice.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.limou.backendjudgeservice.service.CodeSandBox;
import com.limou.backendmodel.model.codeSandBox.ExecuteCodeRequest;
import com.limou.backendmodel.model.codeSandBox.ExecuteCodeResponse;

public class RemoteCodeSandbox implements CodeSandBox {
    private static final String AUTH_REQUEST_HEADER = "auth";

    private static final String AUTH_REQUEST_SECRET = "secretKey";
    //远程代码沙箱
    String url="http://localhost:8090/executeCode";
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        String jsonStr = JSONUtil.toJsonStr(executeCodeRequest);
        String body = HttpUtil.createPost(url)
                .header(AUTH_REQUEST_HEADER, AUTH_REQUEST_SECRET)
                .body(jsonStr)
                .execute()
                .body();
        if(StrUtil.isBlank(body)){
            throw new RuntimeException("executeCode error");
        }
        ExecuteCodeResponse response = JSONUtil.toBean(body, ExecuteCodeResponse.class);
        return response;
    }
}

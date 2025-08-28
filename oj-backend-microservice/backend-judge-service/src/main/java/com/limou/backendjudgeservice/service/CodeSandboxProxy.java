package com.limou.backendjudgeservice.service;


import com.limou.backendmodel.model.codeSandBox.ExecuteCodeRequest;
import com.limou.backendmodel.model.codeSandBox.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CodeSandboxProxy implements CodeSandBox {
    private final CodeSandBox codeSandBox;

    public CodeSandboxProxy(CodeSandBox codeSandBox) {
        this.codeSandBox = codeSandBox;
    }

    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        log.info("代码沙箱请求信息：{}", executeCodeRequest);
        ExecuteCodeResponse executeCodeResponse = codeSandBox.executeCode(executeCodeRequest);
        log.info("代码沙箱响应信息{}", executeCodeResponse);
        return executeCodeResponse;
    }
}

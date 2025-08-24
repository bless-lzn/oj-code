package com.ojcode.springbootinit.judge.codesandbox;

import com.ojcode.springbootinit.judge.codesandbox.model.ExecuteCodeRequest;
import com.ojcode.springbootinit.judge.codesandbox.model.ExecuteCodeResponse;
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

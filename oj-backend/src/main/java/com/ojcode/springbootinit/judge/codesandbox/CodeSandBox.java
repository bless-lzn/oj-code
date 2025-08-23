package com.ojcode.springbootinit.judge.codesandbox;

import com.ojcode.springbootinit.judge.codesandbox.model.ExecuteCodeRequest;
import com.ojcode.springbootinit.judge.codesandbox.model.ExecuteCodeResponse;

public interface CodeSandBox {
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}

package com.limou.backendjudgeservice.service;


import com.limou.backendmodel.model.codeSandBox.ExecuteCodeRequest;
import com.limou.backendmodel.model.codeSandBox.ExecuteCodeResponse;

public interface CodeSandBox {
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}

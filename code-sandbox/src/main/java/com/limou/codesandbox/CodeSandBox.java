package com.limou.codesandbox;


import com.limou.codesandbox.model.ExecuteCodeRequest;
import com.limou.codesandbox.model.ExecuteCodeResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;

public interface CodeSandBox {
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) throws IOException, Exception;
}

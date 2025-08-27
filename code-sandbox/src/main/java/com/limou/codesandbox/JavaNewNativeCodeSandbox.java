package com.limou.codesandbox;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import com.limou.codesandbox.model.ExecuteCodeRequest;
import com.limou.codesandbox.model.ExecuteCodeResponse;
import com.limou.codesandbox.model.ExecuteMessage;
import com.limou.codesandbox.model.JudgeInfo;
import com.limou.codesandbox.utils.ProcessUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Service
public class JavaNewNativeCodeSandbox extends JavaCodeSandboxTemplate {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        return super.executeCode(executeCodeRequest);
    }
}

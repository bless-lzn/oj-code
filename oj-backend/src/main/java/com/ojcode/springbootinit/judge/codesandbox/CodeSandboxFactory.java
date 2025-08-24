package com.ojcode.springbootinit.judge.codesandbox;

import com.ojcode.springbootinit.judge.codesandbox.impl.ExampleCodeSandbox;
import com.ojcode.springbootinit.judge.codesandbox.impl.RemoteCodeSandbox;
import com.ojcode.springbootinit.judge.codesandbox.impl.ThirdPartyCodeSandbox;
import com.ojcode.springbootinit.judge.codesandbox.model.CodeSandboxEnum;
import com.ojcode.springbootinit.model.enums.UserRoleEnum;

import static com.ojcode.springbootinit.judge.codesandbox.model.CodeSandboxEnum.*;

public class CodeSandboxFactory {
    public static CodeSandBox newInstance(CodeSandboxEnum codeSandboxEnum) {
        switch (codeSandboxEnum) {
            case ExampleCodeSandboxEnum:
                return new ExampleCodeSandbox();
            case RemoteCodeSandboxEnum:
                return new RemoteCodeSandbox();
            case ThirdPartyCodeSandboxEnum:
                return new ThirdPartyCodeSandbox();
            default:
                return new ExampleCodeSandbox();
        }
    }
}

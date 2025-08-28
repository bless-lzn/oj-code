package com.limou.backendjudgeservice.service;


import com.limou.backendjudgeservice.service.impl.ExampleCodeSandbox;
import com.limou.backendjudgeservice.service.impl.RemoteCodeSandbox;
import com.limou.backendjudgeservice.service.impl.ThirdPartyCodeSandbox;
import com.limou.backendmodel.model.codeSandBox.CodeSandboxEnum;

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

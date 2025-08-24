package com.ojcode.springbootinit.judge.codesandbox;

import com.ojcode.springbootinit.judge.codesandbox.impl.ExampleCodeSandbox;
import com.ojcode.springbootinit.judge.codesandbox.model.CodeSandboxEnum;
import com.ojcode.springbootinit.judge.codesandbox.model.ExecuteCodeRequest;
import com.ojcode.springbootinit.judge.codesandbox.model.ExecuteCodeResponse;
import com.ojcode.springbootinit.model.enums.QuestionSubmitLanguageEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CodeSandBoxTest {
    @Value("${codeSandbox.type:example}")
    private String type;

    void test() {
        CodeSandboxEnum enumByValue = CodeSandboxEnum.getEnumByValue(type);
        CodeSandBox codeSandbox = CodeSandboxFactory.newInstance(enumByValue);
        String code = "int main() { }";
        String language = QuestionSubmitLanguageEnum.JAVA.getValue();
        List<String> inputList = Arrays.asList("1 2", "3 4");
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
    }


}

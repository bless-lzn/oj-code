package com.limou.codesandbox.model;


import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum CodeSandboxEnum {

    ExampleCodeSandboxEnum("示例代码沙箱", "example"),
    RemoteCodeSandboxEnum("远程代码沙箱", "remote"),

    ThirdPartyCodeSandboxEnum("第三方代码沙箱", "third"),
    ;
    private final String text;

    private final String value;

    CodeSandboxEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static CodeSandboxEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (CodeSandboxEnum anEnum : CodeSandboxEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }

}

package com.ojcode.springbootinit.model.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum QuestionSubmitStatusEnum {
    /**
     * 等待中（0）
     */
    WAITING("等待中", 0),

    /**
     * 判题中（1）
     */
    RUNNING("判题中", 1),

    /**
     * 成功（2）
     */
    SUCCEED("成功", 2),

    /**
     * 失败（3）
     */
    FAILED("失败", 3);
    /**
     * 中文描述
     */
    private final String text;

    /**
     * 状态值
     */
    private final Integer value;

    QuestionSubmitStatusEnum(String text, Integer value) {
        this.text = text;
        this.value = value;
    }

    public static QuestionSubmitStatusEnum getEnumByValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (QuestionSubmitStatusEnum status : values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
    public static List<Integer> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }
    public String getText() {
        return text;
    }

    /**
     * 获取中文描述
     *
     * @return 中文描述
     */
    public Integer getValue() {
        return value;
    }

}

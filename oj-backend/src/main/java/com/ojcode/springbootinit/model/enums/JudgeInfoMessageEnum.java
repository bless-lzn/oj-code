package com.ojcode.springbootinit.model.enums;

import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public enum JudgeInfoMessageEnum {


    /**
     * 成功
     */
    ACCEPTED("Accepted", "成功"),

    /**
     * 答案错误
     */
    WRONG_ANSWER("Wrong Answer", "答案错误"),

    /**
     * 编译错误
     */
    COMPILE_ERROR("Compile Error", "编译错误"),

    /**
     * 内存溢出
     */
    MEMORY_LIMIT_EXCEEDED("Memory Limit Exceeded", "内存溢出"),

    /**
     * 超时
     */
    TIME_LIMIT_EXCEEDED("Time Limit Exceeded", "超时"),

    /**
     * 展示错误
     */
    PRESENTATION_ERROR("Presentation Error", "展示错误"),

    /**
     * 输出溢出
     */
    OUTPUT_LIMIT_EXCEEDED("Output Limit Exceeded", "输出溢出"),

    /**
     * 等待中
     */
    WAITING("Waiting", "等待中"),

    /**
     * 危险操作
     */
    DANGEROUS_OPERATION("Dangerous Operation", "危险操作"),

    /**
     * 运行时错误（用户程序的问题）
     */
    RUNTIME_ERROR("Runtime Error", "运行时错误（用户程序的问题）"),

    /**
     * 系统错误（做系统人的问题）
     */
    SYSTEM_ERROR("System Error", "系统错误（做系统人的问题）");

    /**
     * 英文名称
     */

    private final String value;
    private final String text;



    /**
     * 构造函数
     *
     * @param value 英文名称
     * @param text  中文描述
     */
    JudgeInfoMessageEnum(String text, String value) {
        this.value = value;
        this.text = text;
    }



    /**
     * 根据英文名称获取枚举值
     *
     * @param value 英文名称
     * @return 枚举值
     */
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
    public static JudgeInfoMessageEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (JudgeInfoMessageEnum anEnum : JudgeInfoMessageEnum.values()) {
         if(anEnum.value.equals(value)){
             return anEnum;
         }

        }
        return null;
    }

    public String getText() {
        return text;
    }

    /**
     * 获取中文描述
     *
     * @return 中文描述
     */
    public String getValue() {
        return value;
    }
}



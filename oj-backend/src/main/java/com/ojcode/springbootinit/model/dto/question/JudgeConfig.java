package com.ojcode.springbootinit.model.dto.question;

import lombok.Data;

@Data
public class JudgeConfig {
    /**
     * 时间限制（毫秒）
     */
    private Long timeLimit;

    /**
     * 内存限制（KB）
     */
    private Long memoryLimit;

    /**
     * 栈空间限制（KB）
     */
    private Long stackLimit;
}

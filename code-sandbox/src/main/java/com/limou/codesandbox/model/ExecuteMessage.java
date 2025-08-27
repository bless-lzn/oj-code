package com.limou.codesandbox.model;

import lombok.Data;

@Data
public class ExecuteMessage {
    private String message;

    private Integer exitValue;

    private String errorMessage;

    private Long time;

    private Long memory;
}

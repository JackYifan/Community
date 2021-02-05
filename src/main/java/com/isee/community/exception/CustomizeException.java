package com.isee.community.exception;

//RuntimeException不需要上层try-catch

/**
 * Exception类，封装ErrorCode
 */
public class CustomizeException extends RuntimeException{
    private String message;
    private Integer code;
    public CustomizeException(ICustomizeErrorCode customizeErrorCode){
        this.message=customizeErrorCode.getMessage();
        this.code = customizeErrorCode.getCode();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}

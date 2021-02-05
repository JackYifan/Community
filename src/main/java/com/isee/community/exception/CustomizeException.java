package com.isee.community.exception;

//RuntimeException不需要上层try-catch
public class CustomizeException extends RuntimeException{
    private String message;
    public CustomizeException(ICustomizeErrorCode customizeErrorCode){
        this.message=customizeErrorCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
}

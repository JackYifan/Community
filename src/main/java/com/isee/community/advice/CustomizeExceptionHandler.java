package com.isee.community.advice;

import com.isee.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class) //处理什么异常
    @ResponseBody
    ModelAndView handleControllerException(HttpServletRequest request, Throwable ex, Model model) {
        //用if处理已知异常，else 为默认异常
        if(ex instanceof CustomizeException){
            model.addAttribute("message",ex.getMessage());
        }else{
            model.addAttribute("message","服务器异常，请稍后再试");
        }
        return new ModelAndView("error");
    }



}

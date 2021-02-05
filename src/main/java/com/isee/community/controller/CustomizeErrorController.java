package com.isee.community.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 通用controller处理
 * 包括4XX 5XX 的异常
 */

@Controller("/error")
public class CustomizeErrorController implements ErrorController {

    /**
     * 设置错误页面
     * @return
     */
    @Override
    public String getErrorPath() {
        return "error";
    }


    /**
     * 所有text/html格式的数据都通过这个处理
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView errorHtml(HttpServletRequest request, Model model){
        HttpStatus status = getStatus(request);
        if(status.is4xxClientError()){
            model.addAttribute("messge","客户端请求错误");
        }else if(status.is5xxServerError()){
            model.addAttribute("message","服务器错误,请稍后再试");
        }
        return new ModelAndView("error");
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}

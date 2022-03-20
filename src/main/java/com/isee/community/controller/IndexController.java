package com.isee.community.controller;

import com.alibaba.fastjson.JSONArray;
import com.isee.community.dto.PaginationDTO;
import com.isee.community.dto.QuestionDTO;
import com.isee.community.mapper.QuestionMapper;
import com.isee.community.mapper.UserMapper;
import com.isee.community.model.User;
import com.isee.community.model.Weather;
import com.isee.community.service.QuestionService;
import com.isee.community.service.WeatherService;
import com.isee.community.vo.WeatherVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private WeatherService weatherService;

    /**
     * 检验是否已经登录，若登录获得用户信息并存储在session中
     * @param request
     * @return
     */
    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page",defaultValue = "1")Integer page,
                        @RequestParam(name = "size",defaultValue = "5")Integer size,
                        @RequestParam(name="search",required = false)String search){
        //根据request中的cookie信息查询数据库,以通过拦截器完成
        PaginationDTO pagination = questionService.list(search,page,size);
        WeatherVO weather = weatherService.getWeather();
        model.addAttribute("pagination",pagination);
        model.addAttribute("search",search);
        model.addAttribute("weather",weather);
        return "index";
    }

    @GetMapping("/loginpage")
    public String login(){
        return "login";
    }

    @GetMapping("/registerpage")
    public String register(){
        return "register";
    }

}

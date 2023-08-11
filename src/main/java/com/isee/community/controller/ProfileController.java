package com.isee.community.controller;

import com.isee.community.dto.PaginationDTO;
import com.isee.community.model.User;
import com.isee.community.service.NotificationService;
import com.isee.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action")String action,
                          Model model,
                          @RequestParam(name = "page",defaultValue = "1") Integer page,
                          @RequestParam(name = "size",defaultValue = "5") Integer size,
                          HttpServletRequest request){
        //如果没有登录跳转到登录页面获得当前用户信息
        User user = (User) request.getSession().getAttribute("user");
        if(user==null) return "redirect:/";

        if("questions".equals(action)){
            //问题查询
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
            PaginationDTO pagination = questionService.list(user.getId(),page,size);
            model.addAttribute("pagination",pagination);
        }else if("replies".equals(action)){
            //查询回复
            PaginationDTO paginationDTO = notificationService.list(user.getId(),page,size);
            Long unreadCount = notificationService.unreadCount(user.getId());

            model.addAttribute("section","replies");
            model.addAttribute("pagination",paginationDTO);
            model.addAttribute("unreadCount",unreadCount);
            model.addAttribute("sectionName","最新回复");
        }


        return "profile";
    }

}

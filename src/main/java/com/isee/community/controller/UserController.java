package com.isee.community.controller;

import com.isee.community.dto.FileDTO;
import com.isee.community.model.User;
import com.isee.community.service.FileService;
import com.isee.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Yifan Wu
 * Date on 2022/4/15  21:47
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @GetMapping("/userinfo")
    public String userInfo(Model model,HttpServletRequest request){
        //如果没有登录跳转到登录页面获得当前用户信息
        User user = (User) request.getSession().getAttribute("user");
        if(user==null) return "redirect:/";

        model.addAttribute("user",user);
        return "userinfo";
    }

    @RequestMapping("/updateInfo")
    public String updateInfo(@RequestParam(value = "avatar") MultipartFile file,
                             String username, String bio, Model model, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if(file.getSize()!=0){
            FileDTO fileDTO = fileService.upload(file);
            user.setAvatarUrl(fileDTO.getUrl());
        }
        user.setName(username);
        user.setBio(bio);
        userService.updateById(user);
        model.addAttribute("user",user);
        return "userinfo";
    }

    @RequestMapping("/user/{userId}")
    public String userIntro(@PathVariable(name = "userId")Long id,
                                   Model model){
        User user = userService.getById(id);
        model.addAttribute("user",user);
        return "userintro";

    }
}

package com.isee.community.controller.rest;

import com.isee.community.model.User;
import com.isee.community.service.UserService;
import com.isee.community.util.R;
import com.isee.community.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class LoginController {

    @Autowired
    private UserService userService;
    @RequestMapping("/login")
    public R login(@RequestParam("userName")String username,
                   @RequestParam("password")String password){

        User user = userService.findByName(username);
        if(!user.getPassword().equals(password)) {
            return R.error();
        }
        String token = UUID.randomUUID().toString();
        user.setToken(token);
        userService.createOrUpdate(user);
        System.out.println(token);
        return R.ok().put("token", token);
    }

    @RequestMapping("/info")
    public R info(){
        if(UserHolder.getUser() != null) {
            return R.ok().put("user", UserHolder.getUser());
        }else {
            return R.error("用户未登录");
        }
    }

    @RequestMapping("/logout")
    public R logout(){
        if(UserHolder.getUser() != null) {
            UserHolder.removeUser();
            return R.ok();
        }else {
            return R.error("退出登录失败");
        }
    }
}

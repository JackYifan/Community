package com.isee.community.controller;

import cn.hutool.crypto.SecureUtil;
import com.isee.community.dto.AccesstokenDTO;
import com.isee.community.dto.GithubUser;
import com.isee.community.model.User;
import com.isee.community.provider.GithubProvider;
import com.isee.community.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
@Slf4j
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;


    @Autowired
    private UserService userService;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response){
        AccesstokenDTO accesstokenDTO = new AccesstokenDTO();
        accesstokenDTO.setClient_id(clientId);
        accesstokenDTO.setClient_secret(clientSecret);
        accesstokenDTO.setCode(code);
        accesstokenDTO.setRedirect_uri(redirectUri);
        accesstokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accesstokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if(githubUser!=null){
            //将github返回的用户数据封装后加入到数据库中
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setAvatarUrl(githubUser.getAvatarUrl());
            //accountId是Github中的ID是不会变化的，但每次登录会建立起一次会话需要更新token值
            userService.createOrUpdate(user);
            //将token加入cookie
            response.addCookie(new Cookie("token",token));
            return "redirect:/";
        }else{
            log.error("callback get github error,{}",githubUser);
            //登录失败，重新登录
            return "redirect:/";
        }
    }


    /**
     * 注销功能
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){
        request.getSession().removeAttribute("user"); //删除cookie
        //删除token模拟的session
        Cookie cookie = new Cookie("token",null); //名字相同会进行覆盖
        cookie.setMaxAge(0);//立即删除
        response.addCookie(cookie);
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(String username,
                        String password,
                        HttpServletRequest request,
                        HttpServletResponse response){

        User user = userService.findByName(username);
        if(!user.getPassword().equals(SecureUtil.md5(password))) {
            return "error";
        }
        String token = UUID.randomUUID().toString();
        user.setToken(token);
        userService.createOrUpdate(user);
        //将token加入cookie
        response.addCookie(new Cookie("token",token));
        return "redirect:/";
    }

    @PostMapping("register")
    public String register(String username,
                           String password,
                           String rpassword,
                           HttpServletRequest request,
                           HttpServletResponse response) throws Exception {
        if(!password.equals(rpassword)){
            throw new Exception("两次输入的密码不一致");
        }
        User user = new User();
        String token = UUID.randomUUID().toString();
        user.setToken(token);
        user.setName(username);
        user.setPassword(password);
        user.setAvatarUrl("https://avatarfiles.alphacoders.com/314/314155.jpg");
        userService.createOrUpdate(user);
        //将token加入cookie
        response.addCookie(new Cookie("token",token));
        return "redirect:/";
    }

}

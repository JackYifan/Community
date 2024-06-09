package com.isee.community.controller.rest;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.crypto.SecureUtil;
import com.isee.community.dto.UserDTO;
import com.isee.community.model.User;
import com.isee.community.service.UserService;
import com.isee.community.common.R;
import com.isee.community.common.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.isee.community.common.RedisConstants.LOGIN_USER_KEY;
import static com.isee.community.common.RedisConstants.LOGIN_USER_TTL;


@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class LoginController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserService userService;
    @RequestMapping("/login")
    public R login(@RequestParam("userName")String username,
                   @RequestParam("password")String password){

        User user = userService.findByName(username);
        if(!user.getPassword().equals(SecureUtil.md5(password))) {
            return R.error();
        }
        String token = UUID.randomUUID().toString();
        UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
        String loginToken = LOGIN_USER_KEY + token;
        Map<String, Object> userMap = new HashMap<>();
        try{
            userMap = BeanUtil.beanToMap(userDTO, new HashMap<>(),
                    CopyOptions.create()
                            .setIgnoreNullValue(true)
                            .setFieldValueEditor((fieldName, fieldValue) -> {
                                if (fieldValue == null){
                                    fieldValue = "";
                                }else {
                                    fieldValue = fieldValue.toString();
                                }
                                return fieldValue;
                            })
            );
        }catch (Exception e){
            e.printStackTrace();
        }
        stringRedisTemplate.opsForHash().putAll(loginToken,userMap);
        stringRedisTemplate.expire(loginToken, LOGIN_USER_TTL, TimeUnit.MINUTES);
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

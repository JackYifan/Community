package com.isee.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.isee.community.mapper.UserMapper;
import com.isee.community.model.User;
import com.isee.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void createOrUpdate(User user) {
        //从数据库中查询出的用户
        User dbUser = userMapper.findById(user.getId());
        if(dbUser==null){
            //数据库中没有找到，新用户直接插入数据库 id自增
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else{
            //若老用户的信息在github中有变化则插入到相应的行中，id不会变化
            dbUser.setGmtModified(System.currentTimeMillis());
            //dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setName(user.getName());
            dbUser.setToken(user.getToken());
            userMapper.update(dbUser);
        }

    }

    @Override
    public User findByName(String name){
        User user = userMapper.findByUsername(name);
        return user;
    }



}

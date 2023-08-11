package com.isee.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.isee.community.model.User;

public interface UserService extends IService<User> {
    void createOrUpdate(User user);

    User findByName(String name);
}

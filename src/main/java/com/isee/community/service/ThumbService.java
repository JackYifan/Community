package com.isee.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.isee.community.mapper.ThumbMapper;
import com.isee.community.model.Thumb;
import org.springframework.stereotype.Service;

/**
 * @Author Yifan Wu
 * Date on 2022/3/29  16:44
 */
@Service
public class ThumbService extends ServiceImpl<ThumbMapper, Thumb> {
}

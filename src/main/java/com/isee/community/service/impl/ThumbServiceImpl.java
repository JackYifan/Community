package com.isee.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.isee.community.mapper.ThumbMapper;
import com.isee.community.model.Thumb;
import com.isee.community.service.ThumbService;
import org.springframework.stereotype.Service;

/**
 * @Author Yifan Wu
 * Date on 2022/3/29  16:44
 */
@Service
public class ThumbServiceImpl extends ServiceImpl<ThumbMapper, Thumb> implements ThumbService {
}

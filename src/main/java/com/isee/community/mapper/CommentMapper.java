package com.isee.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.mapper.Mapper;
import com.isee.community.model.Comment;

/**
 * @Author Yifan Wu
 * Date on 2022/3/20  19:24
 */
public interface CommentMapper extends BaseMapper<Comment> {
    void incCommentCount(Comment parentComment);
}

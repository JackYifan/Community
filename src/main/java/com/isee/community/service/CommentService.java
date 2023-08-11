package com.isee.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.isee.community.dto.CommentDTO;
import com.isee.community.enums.CommentTypeEnum;
import com.isee.community.enums.NotificationTypeEnum;
import com.isee.community.model.Comment;
import com.isee.community.model.User;

import java.util.List;

public interface CommentService extends IService<Comment> {
    void insert(Comment comment, User commentator);

    void createNotify(Comment comment, Long receiver, String outerTitle, String notifierName, NotificationTypeEnum notificationType, Long outerId);

    List<CommentDTO> listByTargetId(Long id, CommentTypeEnum type, User currentUser);
}

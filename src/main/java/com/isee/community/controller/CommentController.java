package com.isee.community.controller;

import com.isee.community.dto.CommentCreateDTO;
import com.isee.community.dto.ResultDTO;
import com.isee.community.exception.CustomizeErrorCode;
import com.isee.community.model.Comment;
import com.isee.community.model.User;
import com.isee.community.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

//当用JSON传递数据时，可以使用RequestBody自动封装
@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;
    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request){
        //验证是否已经登录
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            ResultDTO.errorOf(2002,"未登录不能进行评论，请先登录");
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        //验证问题是否为空
        if(commentCreateDTO ==null|| StringUtils.isEmpty(commentCreateDTO.getContent())){
            return ResultDTO.errorOf(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentCreateDTO,comment);
        comment.setGmtModified(System.currentTimeMillis());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        commentService.insert(comment);
        return ResultDTO.ok();
    }

}

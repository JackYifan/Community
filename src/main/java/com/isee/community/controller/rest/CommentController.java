package com.isee.community.controller.rest;

import com.isee.community.common.R;
import com.isee.community.common.UserHolder;
import com.isee.community.dto.*;
import com.isee.community.enums.CommentTypeEnum;
import com.isee.community.exception.CustomizeErrorCode;
import com.isee.community.model.Comment;
import com.isee.community.model.User;
import com.isee.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin
@RestController("RestCommentController")
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @GetMapping("/{id}")
    public R comment(@PathVariable(name = "id") Long id){
        UserDTO userDTO = UserHolder.getUser();
        User currentUser = null;
        if(userDTO != null){
            BeanUtils.copyProperties(userDTO,currentUser);
        }
        List<CommentDTO> comments = commentService.listByTargetId(id, CommentTypeEnum.QUESTION,currentUser);
        return R.ok().put("comments",comments);
    }

    @GetMapping("/tree/{id}")
    public R commentTree(@PathVariable(name = "id") Long id){
        List<CommentDTO> comments = commentService.commentTree(id);
        return R.ok().put("comments",comments);
    }

    @GetMapping("/postComment")
    public R postComment(@RequestBody CommentCreateDTO commentCreateDTO){
        //验证是否已经登录
        UserDTO userDTO = UserHolder.getUser();
        if(userDTO==null){
            return R.error(CustomizeErrorCode.NO_LOGIN.getMessage());
        }
        User user = new User();
        BeanUtils.copyProperties(userDTO,user);
        //验证回复是否为空
        if(commentCreateDTO ==null|| StringUtils.isBlank(commentCreateDTO.getContent())){
            return R.error(CustomizeErrorCode.CONTENT_IS_EMPTY.getMessage());
        }
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentCreateDTO,comment);
        comment.setGmtModified(System.currentTimeMillis());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        commentService.insert(comment,user);
        return R.ok();
    }
}

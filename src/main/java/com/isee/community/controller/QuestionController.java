package com.isee.community.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.isee.community.dto.CommentDTO;
import com.isee.community.dto.QuestionDTO;
import com.isee.community.enums.CommentTypeEnum;
import com.isee.community.enums.NotificationTypeEnum;
import com.isee.community.model.Comment;
import com.isee.community.model.Thumb;
import com.isee.community.model.User;
import com.isee.community.service.CommentService;
import com.isee.community.service.QuestionService;
import com.isee.community.service.ThumbService;
import com.isee.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ThumbService thumbService;

    @Autowired
    private UserService userService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,Model model){
        //根据id查询question
        QuestionDTO questionDTO = questionService.getById(id);
        //查出该问题的所有相关问题
        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);
        //查出该问题的评论列表
        List<CommentDTO> comments = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        //累加阅读数
        questionService.increaseView(id);
        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",comments);
        model.addAttribute("relatedQuestions",relatedQuestions);
        return "question";
    }

    @ResponseBody
    @RequestMapping(value = "/thumb/{commentId}/{userId}/{questionId}", method = RequestMethod.GET)
    public String thumb(@PathVariable(name = "commentId") Long commentId,
                        @PathVariable(name = "userId") Long userId,
                        @PathVariable(name = "questionId") Long questionId){

        //判断是否重复点赞
        int count = thumbService.count(
                new QueryWrapper<Thumb>().eq("comment_id", commentId)
                        .eq("user_id", userId)
        );

        if(count>=1){
            //如果重复点赞，直接返回点赞数
            int totalCount  = thumbService.count(
                    new QueryWrapper<Thumb>().eq("comment_id", commentId)
            );
            return String.valueOf(totalCount);
        }
        // 保存当前用户的点赞
        Thumb thumb = new Thumb();
        thumb.setCommentId(commentId);
        thumb.setUserId(userId);
        thumbService.save(thumb);
        int totalCount  = thumbService.count(
                new QueryWrapper<Thumb>().eq("comment_id", commentId)
        );

        //通知
        Comment comment = commentService.getById(commentId);
        QuestionDTO question = questionService.getById(questionId);
        User commentator = userService.getById(userId);
        commentService.createNotify(comment,comment.getCommentator(),question.getTitle(),commentator.getName(), NotificationTypeEnum.THUMB_COMMENT,question.getId());
        comment.setLikeCount(Long.valueOf(totalCount));
        commentService.updateById(comment);

        return String.valueOf(totalCount);
    }
}

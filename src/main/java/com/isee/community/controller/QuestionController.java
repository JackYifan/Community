package com.isee.community.controller;

import com.isee.community.dto.CommentCreateDTO;
import com.isee.community.dto.CommentDTO;
import com.isee.community.dto.QuestionDTO;
import com.isee.community.service.CommentService;
import com.isee.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,Model model){
        //根据id查询question
        QuestionDTO questionDTO = questionService.getById(id);
        //查出该评论的评论列表
        List<CommentDTO> comments = commentService.listByQuestionId(id);
        //累加阅读数
        questionService.increaseView(id);
        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",comments);
        return "question";
    }
}

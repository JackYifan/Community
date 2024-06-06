package com.isee.community.controller.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.isee.community.dto.PaginationDTO;
import com.isee.community.dto.QuestionDTO;
import com.isee.community.model.Question;
import com.isee.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/question")
public class InfoController {

    @Autowired
    private QuestionService questionService;


    @RequestMapping("/list")
    public IPage<QuestionDTO> listQuestions(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size) {
        IPage<QuestionDTO> questionDTOIPage = questionService.list(page, size);
        return questionDTOIPage;
    }
    @RequestMapping("/{id}")
    public QuestionDTO info(@PathVariable("id") Long id) {
        return questionService.getById(id);
    }


}

package com.isee.community.controller.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.isee.community.dto.PaginationDTO;
import com.isee.community.dto.QuestionDTO;
import com.isee.community.model.Question;
import com.isee.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class InfoController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/getQuestions")
    public PaginationDTO getQuestions(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size,
            @RequestParam(name = "search", required = false) String search) {

        //根据request中的cookie信息查询数据库,以通过拦截器完成
        PaginationDTO pagination = questionService.list(search, page, size);
        return pagination;
    }

    @GetMapping("/question/list")
    public IPage<QuestionDTO> listQuestions(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size) {
        IPage<QuestionDTO> questionDTOIPage = questionService.list(page, size);
        return questionDTOIPage;
    }
}

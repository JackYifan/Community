package com.isee.community.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.isee.community.dto.PaginationDTO;
import com.isee.community.dto.QuestionDTO;
import com.isee.community.model.Comment;
import com.isee.community.model.Question;

import java.util.List;

public interface QuestionService extends IService<Question>{
    PaginationDTO<QuestionDTO> list(String search, Integer page, Integer size);

    PaginationDTO<QuestionDTO> list(Long userId, Integer page, Integer size);

    QuestionDTO getById(Long id);

    void createOrUpdate(Question question);

    void increaseView(Long id);

    List<QuestionDTO> selectRelated(QuestionDTO queryQuestionDTO);


    IPage<QuestionDTO> list(Integer page, Integer size);
}

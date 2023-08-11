package com.isee.community.service;

import com.isee.community.dto.PaginationDTO;
import com.isee.community.dto.QuestionDTO;
import com.isee.community.model.Question;

import java.util.List;

public interface QuestionService {
    PaginationDTO<QuestionDTO> list(String search, Integer page, Integer size);

    PaginationDTO<QuestionDTO> list(Long userId, Integer page, Integer size);

    QuestionDTO getById(Long id);

    void createOrUpdate(Question question);

    void increaseView(Long id);

    List<QuestionDTO> selectRelated(QuestionDTO queryQuestionDTO);
}

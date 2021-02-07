package com.isee.community.mapper;

import com.isee.community.model.Question;


import java.util.List;

public interface QuestionExtMapper {
    List<Question> selectRelated(Question question);
}

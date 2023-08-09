package com.isee.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.isee.community.dto.QuestionDTO;
import com.isee.community.dto.QuestionQueryDTO;
import com.isee.community.model.Question;
import com.isee.community.model.Thumb;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper extends BaseMapper<Question> {

    Integer countByUserId(@Param("userId") Long userId);

    List<Question> listByUserId(@Param("userId") Long userId, @Param("offset") Integer offset,@Param("size") Integer size);

    Question getById(@Param("id") Long id);


    void update(Question question);

    //注意sql的书写，防止高并发时产生的错误
    void updateViewCount(Question question);

    void increaseComment(Question question);

    List<Question> selectRelated(Question question);

    Integer countBySearch(QuestionQueryDTO questionQueryDTO);

    List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);
}

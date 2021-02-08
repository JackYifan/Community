package com.isee.community.mapper;

import com.isee.community.dto.QuestionDTO;
import com.isee.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,tag) " +
            "values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    public void create(Question question);

    @Select("select * from question order by gmt_create desc limit #{offset},#{size}")
    List<Question> list(@Param("offset") Integer offset,@Param("size") Integer size);

    @Select("select count(1) from question")
    Integer count();

    @Select("select count(1) from question where creator=#{userId}")
    Integer countByUserId(@Param("userId") Long userId);

    @Select("select * from question where creator=#{userId} ORDER BY gmt_create DESC limit #{offset},#{size}")
    List<Question> listByUserId(@Param("userId") Long userId, @Param("offset") Integer offset,@Param("size") Integer size);

    @Select("select * from question where id = #{id} ORDER BY gmt_create DESC")
    Question getById(@Param("id") Long id);

    @Update("update question set title=#{title},description=#{description},gmt_modified=#{gmtModified},tag=#{tag} where id=#{id}")
    void update(Question question);

    //注意sql的书写，防止高并发时产生的错误
    @Update("update question set view_count=view_count+1 where id=#{id}")
    void updateViewCount(Question question);

    @Update("update question set comment_count=comment_count+#{commentCount} where id=#{id}")
    void increaseComment(Question question);
}

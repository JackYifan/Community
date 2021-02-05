package com.isee.community.service;

import com.isee.community.enums.CommentTypeEnum;
import com.isee.community.exception.CustomizeErrorCode;
import com.isee.community.exception.CustomizeException;
import com.isee.community.mapper.CommentMapper;
import com.isee.community.mapper.QuestionMapper;
import com.isee.community.model.Comment;
import com.isee.community.model.CommentExample;
import com.isee.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    public void insert(Comment comment) {
        //验证是否有parent_id即问题是否为空
        if(comment.getParentId()==null||comment.getParentId()==0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);//抛出的异常会被Advice处理,返回Json信息
        }
        //验证评论类型
        if(comment.getType()==null|| !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if(comment.getType() == CommentTypeEnum.COMMENT.getType()){
            CommentExample example = new CommentExample();
            example.createCriteria().andIdEqualTo(comment.getParentId());
            //回复评论
            List<Comment> dbComments = commentMapper.selectByExample(example);
            Comment dbComment = dbComments.get(0);//该评论回复的评论
            if(dbComment==null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
        }else{
            //回复问题
            Question question = questionMapper.getById(comment.getParentId());
            if(question==null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            questionMapper.increaseComment(question);
        }

    }
}

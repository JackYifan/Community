package com.isee.community.service;

import com.isee.community.dto.CommentDTO;
import com.isee.community.enums.CommentTypeEnum;
import com.isee.community.enums.NotificationStatusEnum;
import com.isee.community.enums.NotificationTypeEnum;
import com.isee.community.exception.CustomizeErrorCode;
import com.isee.community.exception.CustomizeException;
import com.isee.community.mapper.CommentMapper;
import com.isee.community.mapper.NotificationMapper;
import com.isee.community.mapper.QuestionMapper;
import com.isee.community.mapper.UserMapper;
import com.isee.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NotificationMapper notificationMapper;

    /**
     *插入评论并发出通知，通知提问者
     * @param comment
     * @param commentator 当前用户即评论人
     */
    public void insert(Comment comment, User commentator) {
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
            //得到对应的问题
            Question question = questionMapper.getById(dbComment.getParentId());
            if(question==null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }

            commentMapper.insert(comment);
            //增加评论父结点的评论数
            Comment parentComment = new Comment();
            parentComment.setId(comment.getParentId());
            parentComment.setCommentCount(1);
            commentMapper.incCommentCount(parentComment);
            //创建通知
            createNotify(comment, dbComment.getCommentator(),  question.getTitle(),commentator.getName(), NotificationTypeEnum.REPLY_COMMENT,question.getId());

        }else{
            //回复问题
            Question question = questionMapper.getById(comment.getParentId());
            if(question==null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            //如果插入评论成功而增加评论数失败容易出现错误，要加入事务控制
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionMapper.increaseComment(question);
            //创建通知
            createNotify(comment,question.getCreator(),question.getTitle(),commentator.getName(), NotificationTypeEnum.REPLY_QUESTION,question.getId());
        }

    }

    private void createNotify(Comment comment, Long receiver, String outerTitle, String notifierName, NotificationTypeEnum notificationType,Long outerId) {
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(notificationType.getType());
        notification.setOuterid(outerId);//该评论的父节点
        notification.setNotifier(comment.getCommentator());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setReceiver(receiver);//此评论的创建者，上面通过查询数据库已经查出
        notification.setNotifierName(notifierName);
        notification.setOuterTitle(outerTitle);
        notificationMapper.insert(notification);
    }


    /**
     * 根据问题id查询出评论列表
     * 按时间倒序
     * @param id 问题id
     * @param type
     * @return
     */
    public List<CommentDTO> listByTargetId(Long id, CommentTypeEnum type) {

        //查询出comment
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(type.getType());
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        //将comment封装成commentDTO
        if(comments.size()==0){
            return new ArrayList<>();
        }
        //流式编程 comment映射成评论者并收集成集合的形式，防止后续重复查询降低复杂度
        Set<Long> commentators = comments.stream()
                .map(comment -> comment.getCommentator())
                .collect(Collectors.toSet());

        HashMap<Long,User> userMap = new HashMap<>();
        //查出所有User并封装成map的结构，防止匹配时两重循环n^2复杂度
        for(Long commentatorId:commentators){
            User user = userMapper.findById(commentatorId);
            userMap.put(commentatorId,user);
        }
        //将comment和commentator封装到commentDTO中
        List<CommentDTO> commentDTOS = comments.stream()
                .map(comment -> {
                    CommentDTO commentDTO = new CommentDTO();
                    BeanUtils.copyProperties(comment, commentDTO);
                    commentDTO.setUser(userMap.get(comment.getCommentator()));
                    return commentDTO;
                }).collect(Collectors.toList());

        return commentDTOS;

    }
}

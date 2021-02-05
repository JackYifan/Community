package com.isee.community.service;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.isee.community.dto.PaginationDTO;
import com.isee.community.dto.QuestionDTO;
import com.isee.community.exception.CustomizeErrorCode;
import com.isee.community.exception.CustomizeException;
import com.isee.community.mapper.QuestionMapper;
import com.isee.community.mapper.UserMapper;
import com.isee.community.model.Question;
import com.isee.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    /**
     * 查询所有的问题并分页显示
     * @param page 第几页
     * @param size 每页有几项
     * @return
     */
    public PaginationDTO list(Integer page, Integer size){
        //查询问题的总数
        Integer totalCount = questionMapper.count();
        //计算总页数
        Integer totalPage=totalCount/size + (totalCount%size==0?0:1);

        //防止越界，设置默认值
        if(page>totalPage) page = totalPage;
        if(page<1) page = 1;
        Integer offset = size*(page-1); //将页数转换为sql语句中limit的offset
        List<Question> questions = questionMapper.list(offset,size); //查出所有问题
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        //遍历所有问题并匹配问题提出者
        for(Question question:questions){
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        //封装pagination的各项参数
        paginationDTO.setPage(page);
        paginationDTO.setQuestions(questionDTOList);
        paginationDTO.setPagination(totalCount,page,size);
        return paginationDTO;
    }

    public PaginationDTO list(Long userId, Integer page, Integer size) {
        //查询问题的总数
        Integer totalCount = questionMapper.countByUserId(userId);
        //计算总页数
        Integer totalPage=totalCount/size + (totalCount%size==0?0:1);
        //防止越界，设置默认值
        if(page>totalPage) page = totalPage;
        if(page<1) page = 1;
        Integer offset = size*(page-1); //将页数转换为sql语句中limit的offset
        List<Question> questions = questionMapper.listByUserId(userId,offset,size); //查出所有问题
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        //遍历所有问题并匹配问题提出者
        for(Question question:questions){
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        //封装pagination的各项参数
        paginationDTO.setPage(page);
        paginationDTO.setQuestions(questionDTOList);
        paginationDTO.setPagination(totalCount,page,size);
        return paginationDTO;
    }

    public QuestionDTO getById(Long id) {

        Question question = questionMapper.getById(id);
        if(question==null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }

        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.findById((question.getCreator()));
        questionDTO.setUser(user);
        return questionDTO;

    }

    public void createOrUpdate(Question question) {
        if(question.getId()==null){
            //新建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.create(question);
        }else{
            //只需要修改修改的时间
            question.setGmtModified(question.getGmtCreate());
            questionMapper.update(question);
        }
    }
}

package com.isee.community.service.impl;

import com.isee.community.dto.PaginationDTO;
import com.isee.community.dto.QuestionDTO;
import com.isee.community.dto.QuestionQueryDTO;
import com.isee.community.exception.CustomizeErrorCode;
import com.isee.community.exception.CustomizeException;
import com.isee.community.mapper.QuestionMapper;
import com.isee.community.mapper.UserMapper;
import com.isee.community.model.Question;
import com.isee.community.model.User;
import com.isee.community.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionExtMapper;

    /**
     * 查询所有的问题并分页显示
     *
     * @param search
     * @param page 第几页
     * @param size 每页有几项
     * @return
     */
    @Override
    public PaginationDTO<QuestionDTO> list(String search, Integer page, Integer size){

        if(StringUtils.isNotBlank(search)){
            //分割字符串tag
            String regexTag = search.replaceAll(" ", "|");
        }

        //查询问题的总数
        QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
        questionQueryDTO.setSearch(search);
        Integer totalCount = questionExtMapper.countBySearch(questionQueryDTO);
        //计算总页数
        Integer totalPage=totalCount/size + (totalCount%size==0?0:1);

        //防止越界，设置默认值
        if(page>totalPage) page = totalPage;
        if(page<1) page = 1;
        Integer offset = size*(page-1); //将页数转换为sql语句中limit的offset
        questionQueryDTO.setSize(size);
        questionQueryDTO.setPage(offset);
        List<Question> questions = questionExtMapper.selectBySearch(questionQueryDTO); //查出所有问题
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<>();
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
        paginationDTO.setData(questionDTOList);
        paginationDTO.setPagination(totalCount,page,size);
        return paginationDTO;
    }

    @Override
    public PaginationDTO<QuestionDTO> list(Long userId, Integer page, Integer size) {
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
        PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<>();
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
        paginationDTO.setData(questionDTOList);
        paginationDTO.setPagination(totalCount,page,size);
        return paginationDTO;
    }

    @Override
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

    @Override
    public void createOrUpdate(Question question) {
        if(question.getId()==null){
            //新建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setViewCount(0);
            question.setLikeCount(0);
            question.setCommentCount(0);
            questionMapper.insert(question);
        }else{
            //只需要修改修改的时间
            question.setGmtModified(question.getGmtCreate());
            questionMapper.update(question);
        }
    }


    /**
     * 高并发的时候可能出现问题
     * @param id
     */
    @Override
    public void increaseView(Long id) {
        Question question = questionMapper.getById(id);
        questionMapper.updateViewCount(question);
    }

    /**
     * 查询问题的所有相关问题
     * @param queryQuestionDTO 传参使用DTO因为包含更多信息，查表用的是Question对象，因为与表项相对应
     * @return
     */
    @Override
    public List<QuestionDTO> selectRelated(QuestionDTO queryQuestionDTO) {
        if(StringUtils.isBlank(queryQuestionDTO.getTag())){
            return new ArrayList<>();
        }
        //分割字符串tag
        String tag = queryQuestionDTO.getTag();
        String regexTag = tag.replaceAll(",", "|");
        Question question = new Question();
        question.setId(queryQuestionDTO.getId());
        question.setTag(regexTag);
        List<Question> relatedQuestions = questionExtMapper.selectRelated(question);
        //将Question封装成QuestionDTO对象
        List<QuestionDTO> questionDTOS = relatedQuestions.stream()
                .map(q -> {
                    QuestionDTO questionDTO = new QuestionDTO();
                    BeanUtils.copyProperties(q,questionDTO);
                    return questionDTO;
                })
                .collect(Collectors.toList());
        return questionDTOS;
    }
}

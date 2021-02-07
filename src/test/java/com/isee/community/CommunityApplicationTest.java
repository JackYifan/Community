package com.isee.community;



import com.isee.community.mapper.QuestionExtMapper;
import com.isee.community.model.Question;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommunityApplicationTest {


    @Autowired
    private QuestionExtMapper questionExtMapper;
    @Test
    public void contextLoads() {
        Question question = new Question();
        question.setId(103L);
        question.setTag("Spring");
        List<Question> questions = questionExtMapper.selectRelated(question);
        System.out.println(questions);
    }

}


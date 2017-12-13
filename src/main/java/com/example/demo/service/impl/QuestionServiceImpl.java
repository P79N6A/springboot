package com.example.demo.service.impl;

import com.example.demo.dao.QuestionRespository;
import com.example.demo.model.Question;
import com.example.demo.service.QuestionService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionRespository questionRespository;

    @Override
    public boolean addQuestion(Question question) {
        if (StringUtils.isNotBlank(question.getQuestion()) && StringUtils.isNotBlank(question.getAnswer())) {
            questionRespository.save(question);

            return true;
        }
        return false;

    }

    @Override
    public Page<Question> queryByQuestionAndPage(int page, int rows, String question) {
        PageRequest pageRequest = new PageRequest(page - 1, rows);
        if (StringUtils.isNotBlank(question)) {
            return questionRespository.findByQuestionLike(question, pageRequest);
        }
        return questionRespository.findAll(pageRequest);
    }

    @Override
    public List<Question> findAll() {
        return questionRespository.findAll();
    }

    @Override
    public Question findById(String id) {
        return questionRespository.findOne(id);
    }

    @Override
    public List<Question> queryByQuestion(String question) {
        if (StringUtils.isNotBlank(question)) {
            return questionRespository.findByQuestionLike(question);
        }
        return questionRespository.findAll();
    }

    @Override
    public boolean delQuestion(String id) {
        if(StringUtils.isNotBlank(id)){
            questionRespository.delete(id);
            return  true;
        }
        return false;
    }


}

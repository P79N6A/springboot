package com.example.demo.service.impl;

import com.example.demo.dao.QuestionRespository;
import com.example.demo.model.Question;
import com.example.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService{
    @Autowired
    private QuestionRespository questionRespository;
    @Override
    public void addQuestion(Question question) {
        questionRespository.save(question);
    }

    @Override
    public List<Question> findAll() {
        return questionRespository.findAll();
    }
}

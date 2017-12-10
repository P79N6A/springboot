package com.example.demo.service;

import com.example.demo.model.Question;

import java.util.List;

public interface QuestionService {
    void addQuestion(Question question);

    List<Question> findAll();
}

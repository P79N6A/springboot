package com.example.demo.service;

import com.example.demo.model.Question;
import org.springframework.data.domain.Page;

import java.util.List;

public interface QuestionService {
    boolean addQuestion(Question question);

    Page<Question> queryByQuestionAndPage(int page, int rows, String question);

    List<Question> findAll();

    Question findById(String id);

    List<Question> queryByQuestion(String question);

    boolean delQuestion(String id);

}

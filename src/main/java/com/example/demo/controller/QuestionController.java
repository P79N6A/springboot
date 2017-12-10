package com.example.demo.controller;

import com.example.demo.model.Question;
import com.example.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @RequestMapping(value = "qa")
    public void add(){
        Question question=new Question();
        question.setId("101010");
        question.setQuestion("whereismongo");
        question.setAnswer("three");
        question.setRemark("test");
        questionService.addQuestion(question);
    }

    @RequestMapping(value = "list")
    public List<Question> list(){
        return questionService.findAll();
    }
}

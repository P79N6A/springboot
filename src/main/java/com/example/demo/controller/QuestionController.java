package com.example.demo.controller;

import com.example.demo.common.ResultBean;
import com.example.demo.model.Question;
import com.example.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    /**
     * add qa
     * @param question
     * @return
     */
    @RequestMapping(value = "add")
    public ResultBean<Boolean> add(Question question) {
        return new ResultBean<>(questionService.addQuestion(question));
    }

    /**
     * search by q
     * @param question
     * @return
     */
    @RequestMapping(value = "search")
    public ResultBean<List<Question>> search(@RequestParam(value = "question")String question) {
        return new ResultBean<>(questionService.queryByQuestion(question));
    }

    /**
     * search and page
     * @param page
     * @param rows
     * @param question
     * @return
     */
    @RequestMapping(value = "search_by_page")
    public ResultBean searchByPage(@RequestParam(value = "page")int page,@RequestParam(value = "rows")int rows,@RequestParam(value = "question")String question) {
        return new ResultBean<>(questionService.queryByQuestionAndPage(page,rows,question));
    }

    /**
     * find one by id
     * @param id
     * @return
     */
    @RequestMapping(value = "find_by_id")
    public ResultBean<Question> findById(@RequestParam(value = "id")String id) {
        return new ResultBean<>(questionService.findById(id));
    }

    /**
     *test
     * @param auth_code
     * @param template_id
     * @param request_id
     * @return
     */
    @RequestMapping(value = "open")
    public ResultBean<Boolean> open(@RequestParam(value = "auth_code", required = false, defaultValue = "none") String auth_code,@RequestParam(value = "template_id", required = false, defaultValue = "none") String template_id, @RequestParam(value = "request_id", required = false, defaultValue = "none") String request_id) {
        Question question=new Question();
        question.setQuestion(auth_code);
        question.setId(template_id);
        question.setAnswer(request_id);
        return new ResultBean<>(questionService.addQuestion(question));
    }

    /**
     * del by id
     * @param id
     * @return
     */
    @RequestMapping(value = "del")
    public ResultBean<Boolean> del(String id) {
        return new ResultBean<>(questionService.delQuestion(id));
    }

}

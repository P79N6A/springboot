package com.example.demo.service.impl;

import com.example.demo.dao.NoticeRespository;
import com.example.demo.dao.QuestionRespository;
import com.example.demo.model.Notice;
import com.example.demo.model.Question;
import com.example.demo.service.QuestionService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionRespository questionRespository;
    @Autowired
    private NoticeRespository noticeRespository;

    @Override
    public boolean addQuestion(Question question) {
        if (StringUtils.isNotBlank(question.getQuestion()) && StringUtils.isNotBlank(question.getAnswer())) {
            if(question.getType()==1){
                Notice notice=new Notice();
                notice.setTitle(question.getQuestion());
                notice.setContent(question.getAnswer());
                notice.setGmt_create(formatTime());
                noticeRespository.save(notice);
            }else{
                question.setGmt_create(formatTime());
                questionRespository.save(question);
            }

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
            return questionRespository.findByQuestionLike(question,new Sort(new Sort.Order(Sort.Direction.DESC, "id")));
        }
        return questionRespository.findAll(new Sort(new Sort.Order(Sort.Direction.DESC, "id")) );
    }

    @Override
    public boolean delQuestion(String id) {
        if(StringUtils.isNotBlank(id)){
            questionRespository.delete(id);
            return  true;
        }
        return false;
    }

    private String formatTime(){
       return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

}

package com.example.demo.dao;

import com.example.demo.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRespository extends MongoRepository<Question,String>{


    /**
     * 根据question模糊查询
     * @param question
     * @return
     */
    List<Question> findByQuestionLike(String question,Sort srot);

    /**
     * 根据question模糊查询、分页
     * @param question
     * @param pageable
     * @return
     */
    Page<Question> findByQuestionLike(String question, Pageable pageable);


}

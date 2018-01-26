package com.example.demo.dao;

import com.example.demo.model.Notice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRespository extends MongoRepository<Notice,String>{

}

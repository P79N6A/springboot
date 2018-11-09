package com.example.demo.dao;

import com.example.demo.model.Account;
import com.example.demo.model.Sequential;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SequentialRespository extends MongoRepository<Sequential,String> {
    Sequential findByType(int type);
}

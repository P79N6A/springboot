package com.example.demo.dao;

import com.example.demo.model.FunctionalSubject;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FunctionalSubjectRespository extends MongoRepository<FunctionalSubject,String>{
    FunctionalSubject findByCode(String code);
    List<FunctionalSubject> findByType(int type);
    List<FunctionalSubject> findByTypeAndFlag(int type,int flag);
}

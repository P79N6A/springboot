package com.example.demo.dao;

import com.example.demo.model.car.Institution;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstitutionRespository extends MongoRepository<Institution,String> {
    Institution findByOrgCodeAndAndOrgName(String orgCode,String orgName);
    List<Institution> findByIsDelete(int isDelete);
}

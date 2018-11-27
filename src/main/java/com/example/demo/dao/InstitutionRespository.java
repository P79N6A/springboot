package com.example.demo.dao;

import com.example.demo.model.car.Institution;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstitutionRespository extends MongoRepository<Institution,String> {
    Institution findByOrgCodeAndOrgName(String orgCode,String orgName);
    List<Institution> findByIsDelete(int isDelete,Sort sort);
    List<Institution> findByOrgNameAndIsDelete(String orgName,int isDelete,Sort sort);
    List<Institution> findByOperatorAndIsDelete(String operator,int isDelete,Sort sort);
    List<Institution> findByOrgNameAndOperatorAndIsDelete(String orgName,String operator,int isDelete,Sort sort);
}

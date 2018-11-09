package com.example.demo.dao;

import com.example.demo.model.PurchaseInstitution;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseInstitutionRespository extends MongoRepository<PurchaseInstitution,String> {


}

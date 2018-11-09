package com.example.demo.dao;

import com.example.demo.model.Account;
import com.example.demo.model.PurchaseTypeConfig;
import com.example.demo.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseTypeConfigRespository extends MongoRepository<PurchaseTypeConfig,String>{

    List<PurchaseTypeConfig> findByTypeAndStatus(int type,int status);
}

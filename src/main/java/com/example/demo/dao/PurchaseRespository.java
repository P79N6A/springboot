package com.example.demo.dao;

import com.example.demo.model.Account;
import com.example.demo.model.Purchase;
import com.example.demo.model.Record;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRespository extends MongoRepository<Purchase,String> {

   List<Purchase> findByOwner(String owner);
   List<Purchase> findByExecute(String execute);
   List<Purchase> findByRelation(String relation);
   List<Purchase> findByOwnerAndExecute(String owner, String execute);
   List<Purchase> findByRelationAndOwner(String relation,String owner);
   List<Purchase> findByExecuteAndRelation(String execute, String relation);
   List<Purchase> findByOwnerAndExecuteAndRelation(String owner, String execute, String relation);

}

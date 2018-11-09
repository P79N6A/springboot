package com.example.demo.dao;

import com.example.demo.model.PurchasePlanRequestModel;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchasePlanRequestRespository extends MongoRepository<PurchasePlanRequestModel,String> {
    List<PurchasePlanRequestModel> findByAudit(int audit,Sort sort);
    List<PurchasePlanRequestModel> findByAuditNot(int audit,Sort sort);
    List<PurchasePlanRequestModel> findByAuditLessThan(int audit,Sort sort);
    List<PurchasePlanRequestModel> findByAuditGreaterThan(int audit,Sort sort);
    List<PurchasePlanRequestModel> findByAuditAndExecute(int audit,int execute,Sort sort);
}

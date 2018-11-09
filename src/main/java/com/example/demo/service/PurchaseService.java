package com.example.demo.service;

import com.example.demo.common.ApiResult;
import com.example.demo.common.zcy.RelationInfo;
import com.example.demo.model.Purchase;
import com.example.demo.model.Relation;

import java.util.List;

public interface PurchaseService {
    ApiResult addPurchase(RelationInfo relationInfo);

    List<Purchase> findAll();
    Purchase findById(String id);
    boolean del(String id);

    List<Purchase> query(String owner, String execute, String relation);
}

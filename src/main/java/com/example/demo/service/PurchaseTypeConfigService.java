package com.example.demo.service;

import com.example.demo.model.PurchaseTypeConfig;

import java.util.List;

public interface PurchaseTypeConfigService {
    boolean addPurchaType(PurchaseTypeConfig purchaseTypeConfig);

    List<PurchaseTypeConfig> findAll();

    PurchaseTypeConfig findById(String id);

    boolean del(String id);

    List<PurchaseTypeConfig> findByTypeAndStatus(int type);
}

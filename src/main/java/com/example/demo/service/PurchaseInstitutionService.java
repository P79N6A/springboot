package com.example.demo.service;

import com.example.demo.model.PurchaseInstitution;

import java.util.List;

public interface PurchaseInstitutionService {
    boolean addInstitution(PurchaseInstitution institution);

    List<PurchaseInstitution> findAll();

    PurchaseInstitution findById(String id);
    boolean delInstitution(String id);
}

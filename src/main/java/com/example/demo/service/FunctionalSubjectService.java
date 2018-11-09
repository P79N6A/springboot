package com.example.demo.service;

import com.example.demo.model.FunctionalSubject;
import com.example.demo.model.PurchaseTypeConfig;

import java.util.List;

public interface FunctionalSubjectService {
    boolean addSubject(FunctionalSubject functionalSubject);

    List<FunctionalSubject> findAll();

    FunctionalSubject findById(String id);

    boolean del(String id);

    boolean updateFlag();

    List<FunctionalSubject> findByTypeAndFlag(int type,int flag);
}

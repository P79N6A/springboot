package com.example.demo.service;

import com.example.demo.model.Relation;

import java.util.List;

public interface RelationService {
    boolean addRelation(Relation relation);
    List<Relation> findAll();
    Relation findById(String id);
    boolean del(String id);
    boolean update(String id);
}

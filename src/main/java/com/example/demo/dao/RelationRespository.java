package com.example.demo.dao;

import com.example.demo.model.Relation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelationRespository extends MongoRepository<Relation,String> {
    Relation findByRelation (int relation);
}

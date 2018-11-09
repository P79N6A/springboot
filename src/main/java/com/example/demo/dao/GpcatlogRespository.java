package com.example.demo.dao;

import com.example.demo.model.Gpcatlog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GpcatlogRespository extends MongoRepository<Gpcatlog,String> {
    Gpcatlog findByCode(String code);
    Gpcatlog findByNode(String node);
    List<Gpcatlog> findByFlag(int flag);
    List<Gpcatlog> findByLevel(String levels);
    List<Gpcatlog> findByLevelAndPartner(String levels,String partner);
}

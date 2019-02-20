package com.example.demo.dao;

import com.example.demo.model.ApplyRole;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplyRoleRespository extends MongoRepository<ApplyRole,String> {
    List<ApplyRole> findByStatus(int status, Sort sort);
    ApplyRole findByUserId(String userId,Sort sort);
}

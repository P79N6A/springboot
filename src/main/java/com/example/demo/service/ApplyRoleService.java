package com.example.demo.service;

import com.example.demo.model.ApplyRole;

import java.util.List;

public interface ApplyRoleService {
    boolean createApply(ApplyRole applyRole);
    List<ApplyRole> findAll();
    List<ApplyRole> findByStatus(int status);
    boolean audit(String id, int status,String operator);
    ApplyRole queryApply(String userId);
}

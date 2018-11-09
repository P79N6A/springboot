package com.example.demo.service;

import com.example.demo.model.Gpcatlog;

import java.util.List;

public interface GpcatlogService {
    boolean addGpcatlog(Gpcatlog gpcatlog);
    List<Gpcatlog> findAll();
    Gpcatlog findById(String id);
    boolean delGpcatlog(String id);
    List findBySqual();
    List<Gpcatlog> findByLevel(String level);
    boolean choseFlag();
    List<Gpcatlog> findByFlag(int flag);

}

package com.example.demo.service.impl;

import com.example.demo.common.Initialization;
import com.example.demo.dao.SequentialRespository;
import com.example.demo.model.Sequential;
import com.example.demo.service.SequentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SequentialServiceImpl implements SequentialService {
    @Autowired
    private SequentialRespository sequentialRespository;
    @Override
    public boolean addSequen(Sequential sequential) {
        if (sequential.getType()==Initialization.SEQUEN_TYPE){
            sequential.setGmt_create(Initialization.formatTime());
            sequentialRespository.save(sequential);
            return true;
        }
        return false;
    }

}

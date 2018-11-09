package com.example.demo.dao;

import com.example.demo.model.DutySheet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DutySheetRespository extends MongoRepository<DutySheet,String> {
    DutySheet findByMonth(String month);
}

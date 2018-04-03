package com.example.demo.service;

import com.example.demo.model.Record;

import java.util.List;

public interface RecordService {
    boolean addRecord(Record record,String weeks);

    List<Record> findAll();

    Record findById(String id);
    boolean delRecord(String id);

    boolean updateRecord(Record record);
    List<Record> findByCondition(String uid,String group,String month);
}

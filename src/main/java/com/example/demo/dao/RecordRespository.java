package com.example.demo.dao;

import com.example.demo.model.Record;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRespository extends MongoRepository<Record,String>{

   Record findByUidAndMonth(String uid,String month);
    //Record findGroupByRecord_month(Sort sort);

   List<Record> findByUid(String uid);
   List<Record> findByGroup(String group);
   List<Record> findByMonth(String month);
   List<Record> findByUidAndGroup(String uid,String group);
   List<Record> findByMonthAndUid(String month,String uid);
   List<Record> findByMonthAndGroup(String month,String group);
   List<Record> findByUidAndGroupAndMonth(String uid,String group,String month);

}

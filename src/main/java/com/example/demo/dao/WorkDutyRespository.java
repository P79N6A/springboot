package com.example.demo.dao;

import com.example.demo.model.WorkDuty;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkDutyRespository extends MongoRepository<WorkDuty,String> {
    WorkDuty findByStartTime(String startTime);
    List<WorkDuty> findByMoth(String month, Sort sort);
    //{'startTime':{'$lte':'2018-09-07'}, 'endTime': {'$gte':'2018-09-07'}}
    @Query(value = "{'startTime':{'$lte':?0}, 'endTime': {'$gte':?0}}")
    WorkDuty findByStartTimeAndEndTime(String time);
}

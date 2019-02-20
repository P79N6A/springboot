package com.example.demo.service;

import com.example.demo.model.DutySheet;
import com.example.demo.model.WorkDuty;

import java.util.List;

public interface WorkDutyService {
    boolean createWorkDuty(WorkDuty workDuty);
    List<WorkDuty> findAllDuty();
    WorkDuty findDutyById(String id);
    boolean delDuty(String id);
    boolean delSheet(String id);
    WorkDuty findByStartTime(String startTime);
    List<WorkDuty> findByMoth(String month);
    boolean buildDutySheet(String month);
    List<DutySheet> findAllSheet();
    DutySheet findSheetById(String id);
    WorkDuty findByStartTimeRange(String time);
    String findDutyByCurrentTime(String uid);
    WorkDuty findByMothAndLockUser(String month,String lockUser);
}

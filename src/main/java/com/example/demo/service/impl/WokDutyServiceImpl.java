package com.example.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.demo.common.Initialization;
import com.example.demo.dao.AccountRespository;
import com.example.demo.dao.DutySheetRespository;
import com.example.demo.dao.WorkDutyRespository;
import com.example.demo.model.Account;
import com.example.demo.model.DutySheet;
import com.example.demo.model.WorkDuty;
import com.example.demo.service.WorkDutyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class WokDutyServiceImpl implements WorkDutyService {
    @Autowired
    private WorkDutyRespository workDutyRespository;
    @Autowired
    private AccountRespository accountRespository;
    @Autowired
    private DutySheetRespository dutySheetRespository;

    @Override
    public boolean createWorkDuty(WorkDuty workDuty) {
        log.info("duty reqParams:"+JSON.toJSON(workDuty));
        if(StringUtils.isNotBlank(workDuty.getMoth())){
            if(workDuty.getType()==0&&StringUtils.isNotBlank(workDuty.getUserId())){
                Account account=accountRespository.findOne(workDuty.getUserId());
                workDuty.setUser_name(account.getNick_name());
                workDuty.setPhone(account.getPhone());
                workDuty.setGmt_create(Initialization.formatTime());
                workDuty.setStatus(Initialization.PURCHASE_TYPE_CONFIG_STATUS);
                workDutyRespository.save(workDuty);
                return true;
            }else{
                if(workDuty.getEarly().length()!=0&&workDuty.getLate().length()!=0&&workDuty.getType()==1){
                    String[] earlys=workDuty.getEarly().replaceAll("\"","").split(",");
                    String[] lates=workDuty.getLate().replaceAll("\"","").split(",");
                    StringBuffer earlyLot=new StringBuffer();
                    StringBuffer lateLot=new StringBuffer();
                    StringBuffer earlyPhone=new StringBuffer();
                    StringBuffer latePhone=new StringBuffer();
                    Account account;
                    for (int i = 0; i <earlys.length; i++) {
                        account=accountRespository.findOne(earlys[i]);
                        if(i==earlys.length-1){
                            earlyLot.append(account.getNick_name());
                            earlyPhone.append(account.getPhone());
                        }else{
                            earlyLot.append(account.getNick_name()).append("、");
                            earlyPhone.append(account.getPhone()).append("、");
                        }

                    }
                    for (int i = 0; i <lates.length; i++) {
                        account=accountRespository.findOne(lates[i]);
                        if(i!=lates.length-1){
                            lateLot.append(account.getNick_name()).append("、");
                            latePhone.append(account.getPhone()).append("、");
                        }else{
                            lateLot.append(account.getNick_name());
                            latePhone.append(account.getPhone());
                        }
                    }
                    workDuty.setType(1);
                    workDuty.setEarlyLot(earlyLot.toString());
                    workDuty.setEarlyPhone(earlyPhone.toString());
                    workDuty.setLateLot(lateLot.toString());
                    workDuty.setLatePhone(latePhone.toString());
                    workDuty.setGmt_create(Initialization.formatTime());
                    workDuty.setStatus(Initialization.PURCHASE_TYPE_CONFIG_STATUS);
                    workDutyRespository.save(workDuty);
//                    WorkDuty workDutyEarly;
//                    WorkDuty workDutyLate;
//                    for (int i = 0; i <earlys.length; i++) {
//                        workDutyEarly=new WorkDuty();
//                        account=accountRespository.findOne(earlys[i]);
//                        workDutyEarly.setWeek(workDuty.getWeek());
//                        workDutyEarly.setMoth(workDuty.getMoth());
//                        workDutyEarly.setUserId(earlys[i]);
//                        workDutyEarly.setType(1);
//                        workDutyEarly.setUser_name(account.getNick_name());
//                        workDutyEarly.setPhone(account.getPhone());
//                        workDutyEarly.setGmt_create(Initialization.formatTime());
//                        workDutyEarly.setStatus(Initialization.PURCHASE_TYPE_CONFIG_STATUS);
//                        workDutyEarly.setEarly(workDuty.getEarly());
//                        workDutyEarly.setLate(workDuty.getLate());
//                        workDutyEarly.setStartTime(workDuty.getStartTime());
//                        workDutyEarly.setEndTime(workDuty.getEndTime());
//                        workDutyEarly.setWorkLot(workDuty.getWorkLot());
//                        workDutyRespository.save(workDutyEarly);
//                    }
//                    for (int i = 0; i <lates.length; i++) {
//                        workDutyLate=new WorkDuty();
//                        account=accountRespository.findOne(lates[i]);
//                        workDutyLate.setWeek(workDuty.getWeek());
//                        workDutyLate.setMoth(workDuty.getMoth());
//                        workDutyLate.setUserId(lates[i]);
//                        workDutyLate.setType(2);
//                        workDutyLate.setUser_name(account.getNick_name());
//                        workDutyLate.setPhone(account.getPhone());
//                        workDutyLate.setGmt_create(Initialization.formatTime());
//                        workDutyLate.setStatus(Initialization.PURCHASE_TYPE_CONFIG_STATUS);
//                        workDutyLate.setEarly(workDuty.getEarly());
//                        workDutyLate.setLate(workDuty.getLate());
//                        workDutyLate.setStartTime(workDuty.getStartTime());
//                        workDutyLate.setEndTime(workDuty.getEndTime());
//                        workDutyLate.setWorkLot(workDuty.getWorkLot());
//                        workDutyRespository.save(workDutyLate);
//                    }
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    @Override
    public List<WorkDuty> findAllDuty() {
        return workDutyRespository.findAll();
    }

    @Override
    public WorkDuty findDutyById(String id) {
        return workDutyRespository.findOne(id);

    }

    @Override
    public boolean delDuty(String id) {
        if(StringUtils.isNotBlank(id)){
            workDutyRespository.delete(id);
            return true;
        }
        return false;
    }
    @Override
    public boolean delSheet(String id) {
        if(StringUtils.isNotBlank(id)){
            DutySheet sheet=dutySheetRespository.findOne(id);
            List<WorkDuty> dutyList=workDutyRespository.findByMoth(sheet.getMonth(),new Sort(new Sort.Order(Sort.Direction.ASC,"week")));
            for (int i=0;i<dutyList.size();i++){
                workDutyRespository.delete(dutyList.get(i).getId());
            }
            dutySheetRespository.delete(id);
            return true;
        }
        return false;
    }

    @Override
    public WorkDuty findByStartTime(String startTime) {
        if (StringUtils.isNotBlank(startTime)){
            return workDutyRespository.findByStartTime(startTime);
        }
        return null;
    }

    @Override
    public List<WorkDuty> findByMoth(String month) {
        if (StringUtils.isNotBlank(month)){
            return workDutyRespository.findByMoth(month,new Sort(new Sort.Order(Sort.Direction.ASC,"week")));
        }
        return null;
    }

    @Override
    public boolean buildDutySheet(String month) {
        if (StringUtils.isNotBlank(month)){
            DutySheet exits=dutySheetRespository.findByMonth(month);
            if(exits!=null){
                return false;
            }
            DutySheet dutySheet=new DutySheet();
            List<WorkDuty> duty=workDutyRespository.findByMoth(month,new Sort(new Sort.Order(Sort.Direction.ASC,"week")));
            List<Account> accounts;
            WorkDuty dutyUpdate;
           if(duty.size()!=0){
               for (int i = 0; i <duty.size() ; i++) {
                   dutyUpdate=duty.get(i);
                   accounts=accountRespository.findByLockAndMeeting(0,1,new Sort(new Sort.Order(Sort.Direction.ASC,"sortLot")));
                   log.info("ac size="+accounts.size());
                   if(StringUtils.isNotBlank(dutyUpdate.getLockLot())){
                       if(i>=accounts.size()){
                           List<Account> accountList=accountRespository.findByMeeting(1,new Sort(new Sort.Order(Sort.Direction.ASC,"sortLot")));
                           for (int j = 0; i <accountList.size() ; i++) {
                               Account account=accountRespository.findOne(accountList.get(i).getId());
                               account.setLock(0);
                               accountRespository.save(account);
                           }
                           List<Account> accountCycle=accountRespository.findByLockAndMeeting(0,1,new Sort(new Sort.Order(Sort.Direction.ASC,"sortLot")));
                           dutyUpdate.setLockUser(accountCycle.get(0).getNick_name());
                           workDutyRespository.save(dutyUpdate);
                       }else{
                           dutyUpdate.setLockUser(accounts.get(i).getNick_name());
                           workDutyRespository.save(dutyUpdate);
                       }

                   }

               }
               List<WorkDuty> currentDuty=workDutyRespository.findByMoth(month,new Sort(new Sort.Order(Sort.Direction.ASC,"week")));
               StringBuffer stringBuffer=new StringBuffer(month);
               stringBuffer.append("月——技术支持值班安排表");
               dutySheet.setDuty(currentDuty);
               dutySheet.setMonth(month);
               dutySheet.setSheetName(stringBuffer.toString());
               dutySheet.setGmt_create(Initialization.formatTime());
               dutySheetRespository.save(dutySheet);
               return true;
           }
           return false;
        }
        return false;
    }

    @Override
    public List<DutySheet> findAllSheet() {
        return dutySheetRespository.findAll(new Sort(new Sort.Order(Sort.Direction.DESC,"month")));
    }

    @Override
    public DutySheet findSheetById(String id) {
        if (StringUtils.isNotBlank(id)){
            return dutySheetRespository.findOne(id);
        }
        return null;
    }

    @Override
    public WorkDuty findByStartTimeRange(String time) {
        return workDutyRespository.findByStartTimeAndEndTime(time);
    }

    @Override
    public String findDutyByCurrentTime(String uid) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String textMsg;
        WorkDuty workDuty=workDutyRespository.findByStartTimeAndEndTime(sdf.format(new Date()));
        if(workDuty!=null){
                if(StringUtils.isNotBlank(uid)){
                    textMsg="{\n" +
                            "     \"msgtype\": \"text\",\n" +
                            "     \"text\": {\n" +
                            "         \"content\": \"您好,@"+uid+",当前值班人是@"+workDuty.getUser_name()+"来着\"\n" +
                            "     },\n" +
                            "     \"at\": {\n" +
                            "         \"atDingtalkIds\": [\n" +
                            "             \""+uid+"\"\n" +
                            "         ], \n" +
                            "         \"isAtAll\": false\n" +
                            "     }\n" +
                            " }";
                }else{
                    textMsg = "{ \"msgtype\": \"text\", \"text\": {\"content\": \"技术支持-当前值班人:"+workDuty.getUser_name()+"\"}}";
                }
        }else{
            textMsg = "{ \"msgtype\": \"text\", \"text\": {\"content\": \"暂无值班安排\"}}";
        }

        return textMsg;
    }

    @Override
    public WorkDuty findByMothAndLockUser(String month, String lockUser) {
        if (StringUtils.isNotBlank(month)){
            return workDutyRespository.findByMothAndLockUser(month,lockUser,new Sort(new Sort.Order(Sort.Direction.ASC,"week")));
        }
        return null;
    }
}

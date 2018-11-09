package com.example.demo.service.impl;

import com.example.demo.common.Initialization;
import com.example.demo.dao.AccountRespository;
import com.example.demo.dao.DutySheetRespository;
import com.example.demo.dao.WorkDutyRespository;
import com.example.demo.model.Account;
import com.example.demo.model.DutySheet;
import com.example.demo.model.WorkDuty;
import com.example.demo.service.WorkDutyService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class WokDutyServiceImpl implements WorkDutyService {
    @Autowired
    private WorkDutyRespository workDutyRespository;
    @Autowired
    private AccountRespository accountRespository;
    @Autowired
    private DutySheetRespository dutySheetRespository;

    @Override
    public boolean createWorkDuty(WorkDuty workDuty) {
        if(StringUtils.isNotBlank(workDuty.getMoth())&&StringUtils.isNotBlank(workDuty.getUserId())){
            Account account=accountRespository.findOne(workDuty.getUserId());
            workDuty.setUser_name(account.getNick_name());
            workDuty.setPhone(account.getPhone());
            workDuty.setGmt_create(Initialization.formatTime());
            workDuty.setStatus(Initialization.PURCHASE_TYPE_CONFIG_STATUS);
            workDutyRespository.save(workDuty);
            return true;
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
           if(duty.size()!=0){
               StringBuffer stringBuffer=new StringBuffer(month);
               stringBuffer.append("月——技术支持值班安排表");
               dutySheet.setDuty(duty);
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
}

package com.example.demo.service.impl;

import com.example.demo.dao.RecordRespository;
import com.example.demo.dao.UserRespository;
import com.example.demo.model.Record;
import com.example.demo.model.User;
import com.example.demo.service.RecordService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {
    @Autowired
    private RecordRespository recordRespository;
    @Autowired
    private UserRespository userRespository;

    @Override
    public boolean addRecord(Record record, String weeks) {
        if (StringUtils.isNotBlank(record.getUid()) && StringUtils.isNotBlank(record.getMonth()) && StringUtils.isNotBlank(weeks)) {
            Record old_record = recordRespository.findByUidAndMonth(record.getUid(), record.getMonth());
            if (old_record == null) {
                record.setGmt_create(formatTime());
                User user = userRespository.findOne(record.getUid());
                List<User> info = new ArrayList<>();
                info.add(user);
                record.setUser_info(info);
                record.setGroup(user.getGroup());
                if (firstly.equals(weeks)) {
                    record.setFirstly(record.getCurrent_record());
                } else if (seconds.equals(weeks)) {
                    record.setSeconds(record.getCurrent_record());
                } else if (thirdly.equals(weeks)) {
                    record.setThirdly(record.getCurrent_record());
                } else {
                    record.setFourthly(record.getCurrent_record());
                }
                record.setAbsolute_percent();
                record.setRelative_percent();
                record.setRecord_avg();
                record.setRecord_total_score();
                record.setOnline_leave_point(appendStr(weeks,null,record.getOnline_leave_point()));
                record.setReply_week(appendStr(weeks, null, String.valueOf(record.getReply_quantity())));
                record.setSatisfy_week(appendStr(weeks, null, String.valueOf(record.getSatisfy_quantity())));
                record.setUntisfy_week(appendStr(weeks, null, String.valueOf(record.getUnsatisfy_quantity())));
                record.setEvaluate_week(appendStr(weeks, null, String.valueOf(record.getEvaluate_quantity())));
                record.setUnlawful_time_list(appendStr(weeks, null, record.getUnlawful_time_list()));
                record.setRecord_time_slot(appendStr(weeks, null, record.getRecord_time_slot()));
                record.setRemark(loadRemark(record.getMonth(), weeks, record.getReply_quantity(), record.getSatisfy_quantity(), record.getCurrent_record(), null, true));
                recordRespository.insert(record);
            } else {
                old_record.setGmt_modify(formatTime());
                old_record.setReply_quantity(sum(old_record.getReply_quantity(), record.getReply_quantity()));
                old_record.setSatisfy_quantity(sum(old_record.getSatisfy_quantity(), record.getSatisfy_quantity()));
                old_record.setUnsatisfy_quantity(sum(old_record.getUnsatisfy_quantity(), record.getUnsatisfy_quantity()));
                old_record.setEvaluate_quantity(sum(old_record.getEvaluate_quantity(), record.getEvaluate_quantity()));
                old_record.setAbsolute_percent();
                old_record.setRelative_percent();
                if (firstly.equals(weeks)) {
                    old_record.setFirstly(record.getCurrent_record());
                } else if (seconds.equals(weeks)) {
                    old_record.setSeconds(record.getCurrent_record());
                } else if (thirdly.equals(weeks)) {
                    old_record.setThirdly(record.getCurrent_record());
                } else {
                    old_record.setFourthly(record.getCurrent_record());
                }
                old_record.setRecord_avg();
                old_record.setRecord_total_score();
                old_record.setOnline_leave_point(appendStr(weeks,old_record.getOnline_leave_point(),record.getOnline_leave_point()));
                old_record.setRecord_score(sum(old_record.getRecord_score() , record.getRecord_score()));
                old_record.setHandle_time_ascore(sum(old_record.getHandle_time_ascore(), record.getHandle_time_ascore()));
                old_record.setOnline_time_ascore(sum(old_record.getOnline_time_ascore(), record.getOnline_time_ascore()));
                old_record.setOnline_num(sum(old_record.getOnline_num(),record.getOnline_num()));
                old_record.setReply_week(appendStr(weeks, old_record.getReply_week(), String.valueOf(record.getReply_quantity())));
                old_record.setSatisfy_week(appendStr(weeks, old_record.getSatisfy_week(), String.valueOf(record.getSatisfy_quantity())));
                old_record.setUntisfy_week(appendStr(weeks, old_record.getUntisfy_week(), String.valueOf(record.getUnsatisfy_quantity())));
                old_record.setEvaluate_week(appendStr(weeks, old_record.getEvaluate_week(), String.valueOf(record.getEvaluate_quantity())));
                old_record.setUnlawful_time_list(appendStr(weeks, old_record.getUnlawful_time_list(), record.getUnlawful_time_list()));
                old_record.setRecord_time_slot(appendStr(weeks, old_record.getRecord_time_slot(), record.getRecord_time_slot()));
                old_record.setUnlawful_offline_num(sum(old_record.getUnlawful_offline_num(),record.getUnlawful_offline_num()));
                old_record.setOnline_time_avg(avg(old_record.getOnline_time_avg(),record.getOnline_time_avg()));
                old_record.setHandle_time_avg(avg(old_record.getHandle_time_avg(),record.getHandle_time_avg()));
                old_record.setRemark(loadRemark(record.getMonth(), weeks, record.getReply_quantity(), record.getSatisfy_quantity(), record.getCurrent_record(), old_record.getRemark(), true));
                recordRespository.save(old_record);
            }

            return true;
        }
        return false;

    }


    @Override
    public List<Record> findAll() {
        return recordRespository.findAll(new Sort(new Sort.Order(Sort.Direction.DESC, "month")));
    }

    @Override
    public Record findById(String id) {
        return recordRespository.findOne(id);
    }

    @Override
    public boolean delRecord(String id) {
        if (StringUtils.isNotBlank(id)) {
            recordRespository.delete(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateRecord(Record record) {
        if(StringUtils.isNotBlank(record.getId())){
            boolean ex=recordRespository.exists(record.getId());
            if(ex){
                Record this_record=recordRespository.findOne(record.getId());
                this_record.setMonth(record.getMonth());
                this_record.setReply_quantity(record.getReply_quantity());
                this_record.setSatisfy_quantity(record.getSatisfy_quantity());
                this_record.setUnsatisfy_quantity(record.getUnsatisfy_quantity());
                this_record.setEvaluate_quantity(record.getEvaluate_quantity());
                this_record.setFirstly(record.getFirstly());
                this_record.setSeconds(record.getSeconds());
                this_record.setThirdly(record.getThirdly());
                this_record.setFourthly(record.getFourthly());
                this_record.setRecord_score(record.getRecord_score());
                this_record.setHandle_time_avg(record.getHandle_time_avg());
                this_record.setHandle_time_ascore(record.getHandle_time_ascore());
                this_record.setOnline_time_avg(record.getOnline_time_avg());
                this_record.setOnline_time_ascore(record.getOnline_time_ascore());
                this_record.setUnlawful_offline_num(record.getUnlawful_offline_num());
                this_record.setOnline_num(record.getOnline_num());
                this_record.setOnline_leave_point(record.getOnline_leave_point());
                this_record.setUnlawful_time_list(record.getUnlawful_time_list());
                this_record.setUnlawful_offline_num(record.getUnlawful_offline_num());
                this_record.setAbsolute_percent();
                this_record.setRelative_percent();
                this_record.setRecord_avg();
                this_record.setRecord_total_score();
                this_record.setRemark(loadRemark(null,null,0,0,0,this_record.getRemark(),false));
                recordRespository.save(this_record);
                return true;
            }
            return  false;
        }
        return false;
    }

    @Override
    public List<Record> findByCondition(String uid, String group, String month) {
        boolean id=StringUtils.isNotBlank(uid);
        boolean gp=StringUtils.isNotBlank(group);
        boolean mh=StringUtils.isNotBlank(month);
        if(id&&gp&&mh){
            return recordRespository.findByUidAndGroupAndMonth(uid,group,month);
        }else if(id&&!gp&&!mh){
            return recordRespository.findByUid(uid);
        }else if(!id&gp&!mh){
            return recordRespository.findByGroup(group);
        }else if(!id&&!gp&&mh){
            return  recordRespository.findByMonth(month);
        }else if(id&&gp&&!mh){
            return recordRespository.findByUidAndGroup(uid,group);
        }else if(id&&!gp&&mh){
            return recordRespository.findByMonthAndUid(month,uid);
        }else if(!id&gp&&mh){
            return recordRespository.findByMonthAndGroup(month,group);
        }
        return recordRespository.findAll(new Sort(new Sort.Order(Sort.Direction.DESC, "month")));
    }


    private String formatTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    private String appendStr(String weeks, String str1, String str2) {
        StringBuffer buffer = new StringBuffer();
        if (StringUtils.isNotBlank(str1)) {
            buffer.append(str1).append("|").append(weeks).append(":").append(str2);
        } else {
            buffer.append(weeks).append(":").append(str2);
        }
        return buffer.toString();
    }

    private int sum(int i1, int i2) {
        return i1 + i2;
    }

    private double avg(double d1, double d2) {
        BigDecimal b1=new BigDecimal(String.valueOf(d1));
        BigDecimal b2=new BigDecimal(String.valueOf(d2));
        return new BigDecimal((b1.add(b2).doubleValue())/2).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    private String loadRemark(String month, String weeks, int reply, int satisfy, double score, String oldStr, boolean create) {
        StringBuffer buffer = new StringBuffer();
        if (create == true) {
            if (StringUtils.isBlank(oldStr)) {
                buffer.append("[").append(formatTime()).append(",*新增*").append(month).append("*月度" + weeks + "质检，").append("应答量:" + reply + "个|满意量:" + satisfy + "个|本周质检:" + score).append("分] ");
            } else {
                buffer.append(oldStr).append("[").append(formatTime()).append(",*新增*").append(month).append("*月度" + weeks + "质检，").append("应答量:" + reply + "个|满意量:" + satisfy + "个|本周质检:" + score).append("分] ");
            }
        } else {
            buffer.append(oldStr).append("[").append(formatTime()).append(",管理员").append("进行了全量订正").append("] ");
        }
        return buffer.toString();
    }

    private static final String firstly="第一周";
    private static final String seconds="第二周";
    private static final String thirdly="第三周";
}

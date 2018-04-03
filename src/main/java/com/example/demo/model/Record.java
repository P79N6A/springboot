package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;

/**
 * Created by wb-lwc235565 on 2018/3/12.
 */
@Document
public class Record {
    @Id
    private String id;
    /**user_id、user_info**/
    private String uid;
    private List user_info;
    /**应答量**/
    private int reply_quantity;
    /**满意量**/
    private int  satisfy_quantity;
    /**不满意量**/
    private int unsatisfy_quantity;
    /**评价量**/
    private int  evaluate_quantity;
    /**相对满意率：satisfy_quantity%evaluate_quantity**/
    private String relative_percent;
    /**绝对满意率：satisfy_quantity%ereply_quantity**/
    private String absolute_percent;
    /**第一周质检分**/
    private double  firstly;
    /**第二周质检分**/
    private double  seconds;
    /**第三周质检分**/
    private double  thirdly;
    /**第四周质检分**/
    private double  fourthly;
    /**月度质检平均分**/
    private double record_avg;
    /**质检团队标分**/
    private int record_score;
    /***月度在线平均时长*/
    private double online_time_avg;
    /***月度在线调休节点*/
    private String online_leave_point;

    /***月度在线时长标分*/
    private int online_time_ascore;
    /***月度平均处理时长*/
    private double handle_time_avg;
    /***月度处理时长标分*/
    private int handle_time_ascore;
    /***月度非法离线次数*/
    private int  unlawful_offline_num;
    /***月度非法离线日期*/
    private String unlawful_time_list;

    public String getUnlawful_time_list() {
        return unlawful_time_list;
    }

    public void setUnlawful_time_list(String unlawful_time_list) {
        this.unlawful_time_list = unlawful_time_list;
    }

    /***月度标准在线线分值*/
    private int  online_num;
    /***周次数据记录*/
    private String reply_week;
    private String satisfy_week;
    private String untisfy_week;
    private String evaluate_week;
    /**质检月份、质检周次录入时段**/
    private String month;
    private String record_time_slot;

    /**创建时间、修改时间**/
    private String gmt_create;
    private String gmt_modify;
    /***本次录入质检分*/
    private double current_record;
    /**操作备注**/
    private String remark;
    private int record_total_score;
    private String group;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public int getRecord_total_score() {
        return record_total_score;
    }

    public void setRecord_total_score() {
        this.record_total_score = (this.getRecord_score()+this.getOnline_num()+this.getOnline_time_ascore()+this.getHandle_time_ascore());
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
    public double getCurrent_record() {
        return current_record;
    }

    public void setCurrent_record(double current_record) {
        this.current_record = current_record;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public List getUser_info() {
        return user_info;
    }

    public void setUser_info(List user_info) {
        this.user_info = user_info;
    }

    public int getReply_quantity() {
        return reply_quantity;
    }

    public void setReply_quantity(int reply_quantity) {
        this.reply_quantity = reply_quantity;
    }

    public int getSatisfy_quantity() {
        return satisfy_quantity;
    }

    public void setSatisfy_quantity(int satisfy_quantity) {
        this.satisfy_quantity = satisfy_quantity;
    }

    public int getUnsatisfy_quantity() {
        return unsatisfy_quantity;
    }

    public void setUnsatisfy_quantity(int unsatisfy_quantity) {
        this.unsatisfy_quantity = unsatisfy_quantity;
    }

    public int getEvaluate_quantity() {
        return evaluate_quantity;
    }

    public void setEvaluate_quantity(int evaluate_quantity) {
        this.evaluate_quantity = evaluate_quantity;
    }

    public String getRelative_percent() {
        return relative_percent;
    }

    public void setRelative_percent() {
        this.relative_percent = percent(this.satisfy_quantity,this.getEvaluate_quantity());
    }

    public String getAbsolute_percent() {
        return absolute_percent;
    }

    public void setAbsolute_percent() {
        this.absolute_percent = percent(this.satisfy_quantity,this.reply_quantity);
    }

    public double getFirstly() {
        return firstly;
    }

    public void setFirstly(double firstly) {
        this.firstly = firstly;
    }

    public double getSeconds() {
        return seconds;
    }

    public void setSeconds(double seconds) {
        this.seconds = seconds;
    }

    public double getThirdly() {
        return thirdly;
    }

    public void setThirdly(double thirdly) {
        this.thirdly = thirdly;
    }

    public double getFourthly() {
        return fourthly;
    }

    public void setFourthly(double fourthly) {
        this.fourthly = fourthly;
    }

    public double getRecord_avg() {
        return record_avg;
    }

    public void setRecord_avg() {
//        Double flag=0.00;
//        int i=0;
//        if(flag.compareTo(this.firstly)==1){
//            i=i+1;
//        }
        BigDecimal d1=new BigDecimal(String.valueOf(this.firstly));
        BigDecimal d2=new BigDecimal(String.valueOf(this.seconds));
        BigDecimal d3=new BigDecimal(String.valueOf(this.thirdly));
        BigDecimal d4=new BigDecimal(String.valueOf(this.fourthly));
        this.record_avg = new BigDecimal((d1.add(d2).add(d3).add(d4).doubleValue())/4).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public int getRecord_score() {
        return record_score;
    }

    public void setRecord_score(int record_score) {
        this.record_score = record_score;
    }

    public double getOnline_time_avg() {
        return online_time_avg;
    }

    public void setOnline_time_avg(double online_time_avg) {
        this.online_time_avg = online_time_avg;
    }

    public int getOnline_time_ascore() {
        return online_time_ascore;
    }

    public void setOnline_time_ascore(int online_time_ascore) {
        this.online_time_ascore = online_time_ascore;
    }

    public double getHandle_time_avg() {
        return handle_time_avg;
    }

    public void setHandle_time_avg(double handle_time_avg) {
        this.handle_time_avg = handle_time_avg;
    }

    public int getHandle_time_ascore() {
        return handle_time_ascore;
    }

    public void setHandle_time_ascore(int handle_time_ascore) {
        this.handle_time_ascore = handle_time_ascore;
    }

    public int getUnlawful_offline_num() {
        return unlawful_offline_num;
    }

    public void setUnlawful_offline_num(int unlawful_offline_num) {
        this.unlawful_offline_num = unlawful_offline_num;
    }

    public int getOnline_num() {
        return online_num;
    }

    public void setOnline_num(int online_num) {
        this.online_num = online_num;
    }

    public String getReply_week() {
        return reply_week;
    }

    public void setReply_week(String reply_week) {
        this.reply_week = reply_week;
    }

    public String getSatisfy_week() {
        return satisfy_week;
    }

    public void setSatisfy_week(String satisfy_week) {
        this.satisfy_week = satisfy_week;
    }

    public String getUntisfy_week() {
        return untisfy_week;
    }

    public void setUntisfy_week(String untisfy_week) {
        this.untisfy_week = untisfy_week;
    }

    public String getEvaluate_week() {
        return evaluate_week;
    }

    public void setEvaluate_week(String evaluate_week) {
        this.evaluate_week = evaluate_week;
    }

    public String getRecord_time_slot() {
        return record_time_slot;
    }

    public void setRecord_time_slot(String record_time_slot) {
        this.record_time_slot = record_time_slot;
    }

    public String getGmt_create() {
        return gmt_create;
    }

    public void setGmt_create(String gmt_create) {
        this.gmt_create = gmt_create;
    }

    public String getGmt_modify() {
        return gmt_modify;
    }

    public void setGmt_modify(String gmt_modify) {
        this.gmt_modify = gmt_modify;
    }
    public String getOnline_leave_point() {
        return online_leave_point;
    }

    public void setOnline_leave_point(String online_leave_point) {
        this.online_leave_point = online_leave_point;
    }

    private  static  String percent( int p1, int p2){
        String str;
        double  p3  = (double)p1/p2;
        NumberFormat nf  =  NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits( 2 );
        str  =  nf.format(p3);
        return  str;
    }

}

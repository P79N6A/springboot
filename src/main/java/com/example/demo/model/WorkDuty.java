package com.example.demo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class WorkDuty {
    @Id
    private String id;
    private String gmt_create;
    private String gmt_modify;
    private String moth;
    private int week;
    private String userId;
    private String user_name;
    private String phone;
    private String startTime;
    private String endTime;
    private String workLot;
    private int status;
    /**值班类型：0.正常 1.非常规、早晚班 **/
    private int type;
    private String early;
    private String late;
    private String earlyLot;
    private String lateLot;
    private String earlyPhone;
    private String latePhone;
    private String lockUser;
    private String lockLot;



}

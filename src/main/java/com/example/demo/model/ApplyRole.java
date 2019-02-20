package com.example.demo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class ApplyRole {
    @Id
    private String id;
    private String gmt_create;
    private String gmt_modify;
    /**申请者**/
    private String applyUser;
    private String userId;
    private String reason;
    /**申请权限码**/
    private String applyCode;
    private String applyList;
    /**审核状态 0待审 10通过 20驳回**/
    private int status;
    private String operator;


}

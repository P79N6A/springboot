package com.example.demo.model.car;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/***
 * 车辆信息导入、删除记录
 */
@Data
@Document
public class Vehicle {
    @Id
    private String id;
    private String gmt_create;
    private String gmt_modify;
    /**0 正常 1已删除**/
    private int isDelete;
    private String orgCode;
    private String orgName;
    private String plateNo;
    private String carClassName;
    private String carUsageName;
    private String price;
    private String registerDate;
    private String brandName;
    private String volumeOut;
    private String vinNo;
    private String engineNo;
    private String isImport;
    private String operator;


}

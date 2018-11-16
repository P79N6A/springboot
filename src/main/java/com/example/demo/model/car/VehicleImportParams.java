package com.example.demo.model.car;

import lombok.Data;

/***
 * 外部文件导入车辆信息
 */
@Data
public class VehicleImportParams {
    /** 行号**/
    private String lineNum;
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



}

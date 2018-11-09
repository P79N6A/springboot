package com.example.demo.model.car.orgQueryResp;

import lombok.Getter;
import lombok.Setter;

/***
 * 机构信息详情
 */
@Setter
@Getter
public class Data {
    private int id;
    private String name;
    private String orgCode;
    private int isFixedQuota;
    private int orgProCode;
    private String financialDep;
    private String level;
    private String districtCode;
    private String districtName;
    private String address;
    private String superAdmOrgName;
    private String thisAdmOrgName;
    private String carManager;
    private String zipCode;
    private String email;
    private String carManagerPhone;
    private int staffQuotaNum;
    private int leaderNum;
    private int unLeaderNum;
    private int cadreWorkerNum;
    private String bureauRankNum;
    private int retiredNum;
    private int inServiceStaffNum;
    private CarStatisticsInfo carStatisticsInfo;



}

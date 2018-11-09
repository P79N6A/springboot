package com.example.demo.model.car.orgQueryResp;

import lombok.Data;

import java.util.List;

@Data
public class CarStatisticsInfo {
    private int carQuotaNum;
    private int carActualNum;
    private int actualInQuotaNum;
    private int actualOutQuotaNum;
    private int inWayInQuotaApplyNum;
    private int inWayOutQuotaApplyNum;
    private int ableApplyNum;
    private int ableUpdateNum;
    private int inQuotaDisposeNum;
    private int realCarInStockNum;
    private int carNumInQuota;
    private int carNumNotInQuota;
    private List<CarByUsages> carByUsages;
    private List<CarByClasses> carByClasses;
    private List<CarQuotaByUsages> carQuotaByUsages;


}

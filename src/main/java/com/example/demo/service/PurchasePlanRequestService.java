package com.example.demo.service;

import com.example.demo.common.ApiResult;
import com.example.demo.common.ProcurementUsage;
import com.example.demo.model.CapitalType;
import com.example.demo.model.PurchasePlanRequestModel;

import java.util.List;

public interface PurchasePlanRequestService {
    boolean createPlan(PurchasePlanRequestModel purchasePlanRequestModel,ProcurementUsage procurementUsage);
    List<PurchasePlanRequestModel> findAll();
    PurchasePlanRequestModel findById(String id);
    boolean del(String id);
    boolean cancel(String id);
    boolean submit(String id);
    int query();
    ApiResult execute(String id);
    boolean audit(String id,int audit,String remark);
    boolean update(PurchasePlanRequestModel purchasePlanRequestModel,CapitalType capitalType);
    List<PurchasePlanRequestModel> queryByAudit();
    List<PurchasePlanRequestModel> queryByAuditOver();
    List<PurchasePlanRequestModel> queryByExecute();
    List<PurchasePlanRequestModel> queryByCreate();
}

package com.example.demo.controller;


import com.alibaba.fastjson.JSON;
import com.example.demo.common.ApiResult;
import com.example.demo.common.ProcurementUsage;
import com.example.demo.common.ResultBean;
import com.example.demo.model.CapitalType;
import com.example.demo.model.PurchasePlanRequestModel;
import com.example.demo.model.Sequential;
import com.example.demo.service.PurchasePlanRequestService;
import com.example.demo.service.SequentialService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "plan")
@Api(value = "plan", description = "采购计划申请接口")
public class PurchasePlanRequestController {
    @Autowired
    private PurchasePlanRequestService purchasePlanRequestService;
    @Autowired
    private SequentialService sequentialService;
    @RequestMapping(value = "create")
    public ResultBean<Boolean> create(PurchasePlanRequestModel purchasePlanRequestModel,ProcurementUsage procurementUsage) {
        return new ResultBean<>(purchasePlanRequestService.createPlan(purchasePlanRequestModel,procurementUsage));
    }

    @RequestMapping(value = "find_by_id")
    public ResultBean<PurchasePlanRequestModel> findById(@RequestParam(value = "id") String id) {
        return new ResultBean<>(purchasePlanRequestService.findById(id));
    }

    @RequestMapping(value = "findAll")
    public ResultBean<List<PurchasePlanRequestModel>> findAll() {
        return new ResultBean<>(purchasePlanRequestService.findAll());
    }

    @RequestMapping(value = "del")
    public  ResultBean<Boolean> del(String id) {
        return new ResultBean<>(purchasePlanRequestService.del(id));
    }

    @RequestMapping(value = "cancel")
    public  ResultBean<Boolean> cancel(String id) {
        return new ResultBean<>(purchasePlanRequestService.cancel(id));
    }

    @RequestMapping(value = "sequen_init")
    public  ResultBean<Boolean> sequenInit(Sequential sequential) {
        return new ResultBean<>(sequentialService.addSequen(sequential));
    }
    @RequestMapping(value = "audit")
    public ResultBean<Boolean> audit(@RequestParam(value = "id") String id,@RequestParam(value = "audit")int audit,@RequestParam(value = "audit_remark",required = false)String remark) {
        return new ResultBean<>(purchasePlanRequestService.audit(id,audit,remark));
    }
    @RequestMapping(value = "submit")
    public ResultBean<Boolean> submit(@RequestParam(value = "id") String id) {
        return new ResultBean<>(purchasePlanRequestService.submit(id));
    }
    @RequestMapping(value = "update")
    public ResultBean<Boolean> update(PurchasePlanRequestModel purchasePlanRequestModel,CapitalType capitalType) {
        return new ResultBean<>(purchasePlanRequestService.update(purchasePlanRequestModel,capitalType));
    }

    @RequestMapping(value = "query_audit")
    public ResultBean<List<PurchasePlanRequestModel>> queryByAudit() {
        return new ResultBean<>(purchasePlanRequestService.queryByAudit());
    }
    @RequestMapping(value = "query_execute")
    public ResultBean<List<PurchasePlanRequestModel>> queryByExecute() {
        return new ResultBean<>(purchasePlanRequestService.queryByExecute());
    }
    @RequestMapping(value = "query_create")
    public ResultBean<List<PurchasePlanRequestModel>> queryByCreate() {
        return new ResultBean<>(purchasePlanRequestService.queryByCreate());
    }
    @RequestMapping(value = "query_audit_over")
    public ResultBean<List<PurchasePlanRequestModel>> queryByAuditOver() {
        return new ResultBean<>(purchasePlanRequestService.queryByAuditOver());
    }
    @RequestMapping(value = "execute")
    public ResultBean<ApiResult> queryByAuditOver(@RequestParam(value = "id") String id) {
        return new ResultBean<>(purchasePlanRequestService.execute(id));
    }
    @RequestMapping(value = "count")
    public ResultBean query() {
        return new ResultBean<>(purchasePlanRequestService.query());
    }

}

package com.example.demo.controller;


import com.example.demo.common.ApiResult;
import com.example.demo.common.ResultBean;
import com.example.demo.common.zcy.RelationInfo;
import com.example.demo.model.Purchase;
import com.example.demo.service.PurchaseService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "purchase")
@Api(value = "purchase", description = "采购计划释放相关接口")
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;
    @PostMapping(value = "add")
    public ResultBean<ApiResult> add(RelationInfo relationInfo) {
        return new ResultBean<>(purchaseService.addPurchase(relationInfo));
    }

    @PostMapping(value = "sql")
    public ResultBean<ApiResult> createBySQL(@RequestParam(value = "uid") String uid,@RequestParam(value = "sql") String sql,@RequestParam(value = "request_type")int type) {
        return new ResultBean<>(purchaseService.addPurchase(buildParams(uid,sql,type)));
    }

    @RequestMapping(value = "find_by_id",method =RequestMethod.GET)
    public ResultBean<Purchase> findById(@RequestParam(value = "id") String id) {
        return new ResultBean<>(purchaseService.findById(id));
    }

    @RequestMapping(value = "findAll",method =RequestMethod.GET)
    public ResultBean<List<Purchase>> findAll() {
        return new ResultBean<>(purchaseService.findAll());
    }

    @PostMapping(value = "del")
    public  ResultBean<Boolean> del(String id) {
        return new ResultBean<>(purchaseService.del(id));
    }

    @RequestMapping(value = "query",method =RequestMethod.GET)
    public ResultBean<List<Purchase>> query(@RequestParam(value = "owner_id") String owner_id,@RequestParam(value = "execute_type") String execute_type,@RequestParam(value = "relation_type") String relation_type) {
        return new ResultBean<>(purchaseService.query(owner_id,execute_type,relation_type));
    }

    private RelationInfo buildParams(String uid,String sql,int type){
        RelationInfo relationInfo=new RelationInfo();
        relationInfo.setOperator_id(uid);
        relationInfo.setRequest_type(type);
        String flag="values";
        int pos=sql.indexOf(flag)+flag.length()+1;
        String[] params=sql.substring(pos,sql.length()-2).replaceAll("\\s*","").split(",");
        relationInfo.setPurchase_id(params[1]);
        relationInfo.setUser_id(params[2]);
        relationInfo.setRelation_type(Integer.parseInt(params[3]));
        relationInfo.setRelation_id(params[4]);
        relationInfo.setAmount(params[5]);
        relationInfo.setQuantity(Integer.parseInt(params[6]));
        return relationInfo;
    }
}

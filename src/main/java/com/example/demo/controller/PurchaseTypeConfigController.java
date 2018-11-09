package com.example.demo.controller;

import com.example.demo.common.ResultBean;
import com.example.demo.model.Gpcatlog;
import com.example.demo.model.PurchaseTypeConfig;
import com.example.demo.service.GpcatlogService;
import com.example.demo.service.PurchaseTypeConfigService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "purchase_type")
@Api(value = "type", description = "采购类型配置接口")
public class PurchaseTypeConfigController {
    @Autowired
    private PurchaseTypeConfigService purchaseTypeConfigService;

    /**
     *
     * @param purchaseTypeConfig
     * @return boolean
     */
    @RequestMapping(value = "add")
    public ResultBean<Boolean> add(PurchaseTypeConfig purchaseTypeConfig) {
        return new ResultBean<>(purchaseTypeConfigService.addPurchaType(purchaseTypeConfig));
    }

    /**
     * find one by id
     * @param id
     * @return model
     */
    @RequestMapping(value = "find_by_id")
    public ResultBean<PurchaseTypeConfig> findById(@RequestParam(value = "id") String id) {
        return new ResultBean<>(purchaseTypeConfigService.findById(id));
    }

    @RequestMapping(value = "findAll")
    public ResultBean<List<PurchaseTypeConfig>> findAll() {
        return new ResultBean<>(purchaseTypeConfigService.findAll());
    }

    @RequestMapping(value = "del")
    public  ResultBean<Boolean> del(String id) {
        return new ResultBean<>(purchaseTypeConfigService.del(id));
    }

    @RequestMapping(value = "findByType")
    public  ResultBean<List<PurchaseTypeConfig>> findByType(int type) {
        return new ResultBean<>(purchaseTypeConfigService.findByTypeAndStatus(type));
    }

}

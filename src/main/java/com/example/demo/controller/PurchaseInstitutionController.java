package com.example.demo.controller;

import com.example.demo.common.ResultBean;
import com.example.demo.model.PurchaseInstitution;
import com.example.demo.model.User;
import com.example.demo.service.PurchaseInstitutionService;
import com.example.demo.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "institution")
@Api(value = "institution", description = "采购单位录入接口")
public class PurchaseInstitutionController {
    @Autowired
    private PurchaseInstitutionService purchaseInstitutionService;

    /**
     *
     * @param purchaseInstitution
     * @return boolean
     */
    @RequestMapping(value = "add")
    public ResultBean<Boolean> add(PurchaseInstitution purchaseInstitution) {
        return new ResultBean<>(purchaseInstitutionService.addInstitution(purchaseInstitution));
    }


    /**
     * find one by id
     * @param id
     * @return model
     */
    @RequestMapping(value = "find_by_id")
    public ResultBean<PurchaseInstitution> findById(@RequestParam(value = "id") String id) {
        return new ResultBean<>(purchaseInstitutionService.findById(id));
    }

    @RequestMapping(value = "findAll")
    public ResultBean<List<PurchaseInstitution>> findAll() {
        return new ResultBean<>(purchaseInstitutionService.findAll());
    }

    @RequestMapping(value = "del")
    public  ResultBean<Boolean> del(String id) {
        return new ResultBean<>(purchaseInstitutionService.delInstitution(id));
    }

}

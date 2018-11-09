package com.example.demo.controller;

import com.example.demo.common.Initialization;
import com.example.demo.common.ResultBean;
import com.example.demo.model.FunctionalSubject;
import com.example.demo.model.PurchaseTypeConfig;
import com.example.demo.service.FunctionalSubjectService;
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
@RequestMapping(value = "subject")
@Api(value = "subject", description = "采购科目配置接口")
public class FunctionalSubjectController {
    @Autowired
    private FunctionalSubjectService functionalSubjectService;

    /**
     *
     * @param functionalSubject
     * @return boolean
     */
    @RequestMapping(value = "add")
    public ResultBean<Boolean> add(FunctionalSubject functionalSubject) {
        return new ResultBean<>(functionalSubjectService.addSubject(functionalSubject));
    }

    /**
     * find one by id
     * @param id
     * @return model
     */
    @RequestMapping(value = "find_by_id")
    public ResultBean<FunctionalSubject> findById(@RequestParam(value = "id") String id) {
        return new ResultBean<>(functionalSubjectService.findById(id));
    }

    @RequestMapping(value = "findAll")
    public ResultBean<List<FunctionalSubject>> findAll() {
        return new ResultBean<>(functionalSubjectService.findAll());
    }

    @RequestMapping(value = "del")
    public  ResultBean<Boolean> del(String id) {
        return new ResultBean<>(functionalSubjectService.del(id));
    }

    @RequestMapping(value = "findByType")
    public  ResultBean<List<FunctionalSubject>> findByType(int type) {
        return new ResultBean<>(functionalSubjectService.findByTypeAndFlag(type,Initialization.SUBJECT_DEFAULT));
    }

    @RequestMapping(value = "flag")
    public  ResultBean<Boolean> updateFlag() {
        return new ResultBean<>(functionalSubjectService.updateFlag());
    }


}

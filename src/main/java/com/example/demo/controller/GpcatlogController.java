package com.example.demo.controller;

import com.example.demo.common.Initialization;
import com.example.demo.common.ResultBean;
import com.example.demo.model.Gpcatlog;
import com.example.demo.model.PurchaseInstitution;
import com.example.demo.service.GpcatlogService;
import com.example.demo.service.PurchaseInstitutionService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "gpcatlog")
@Api(value = "gocatlog", description = "采购目录接口")
public class GpcatlogController {
    @Autowired
    private GpcatlogService gpcatlogService;

    /**
     *
     * @param gpcatlog
     * @return boolean
     */
    @RequestMapping(value = "add")
    public ResultBean<Boolean> add(Gpcatlog gpcatlog) {
        return new ResultBean<>(gpcatlogService.addGpcatlog(gpcatlog));
    }


    /**
     * find one by id
     * @param id
     * @return model
     */
    @RequestMapping(value = "find_by_id")
    public ResultBean<Gpcatlog> findById(@RequestParam(value = "id") String id) {
        return new ResultBean<>(gpcatlogService.findById(id));
    }

    @RequestMapping(value = "findAll")
    public ResultBean<List<Gpcatlog>> findAll() {
        return new ResultBean<>(gpcatlogService.findAll());
    }

    @RequestMapping(value = "del")
    public  ResultBean<Boolean> del(String id) {
        return new ResultBean<>(gpcatlogService.delGpcatlog(id));
    }

    @RequestMapping(value = "list")
    public  ResultBean list() {
        return new ResultBean<>(gpcatlogService.findBySqual());
    }

    @RequestMapping(value = "queryList")
    public  ResultBean list(@RequestParam(value = "level") String level) {
        return new ResultBean<>(gpcatlogService.findByLevel(level));
    }
    @RequestMapping(value = "flag")
    public  ResultBean<Boolean> flag() {
        return new ResultBean<>(gpcatlogService.choseFlag());
    }

    @RequestMapping(value = "findByFlag")
    public ResultBean<List<Gpcatlog>> findByFlag() {
        return new ResultBean<>(gpcatlogService.findByFlag(Initialization.GPCATLOG_DEFAULT));
    }
}

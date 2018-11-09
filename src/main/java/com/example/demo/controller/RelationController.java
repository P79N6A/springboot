package com.example.demo.controller;

import com.example.demo.common.ResultBean;
import com.example.demo.model.Relation;
import com.example.demo.service.RelationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "relation")
@Api(value = "relation", description = "关联类型配置接口")
public class RelationController {
    @Autowired
    private RelationService relationService;

    /**
     * add re
     *
     * @param relation
     * @return
     */
    @RequestMapping(value = "add")
    public ResultBean<Boolean> add(Relation relation) {
        return new ResultBean<>(relationService.addRelation(relation));
    }


    /**
     * find one by id
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "find_by_id")
    public ResultBean<Relation> findById(@RequestParam(value = "id") String id) {
        return new ResultBean<>(relationService.findById(id));
    }

    @RequestMapping(value = "findAll")
    public ResultBean<List<Relation>> findAll() {
        return new ResultBean<>(relationService.findAll());
    }

    @RequestMapping(value = "del")
    public  ResultBean<Boolean> del(String id) {
        return new ResultBean<>(relationService.del(id));
    }


}

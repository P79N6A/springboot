package com.example.demo.controller;

import com.example.demo.common.ResultBean;
import com.example.demo.model.Record;
import com.example.demo.service.RecordService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "record")
@Api(value = "record", description = "记录生成接口")
public class RecordController {
    @Autowired
    private RecordService recordService;

    /**
     * add user
     *
     * @param
     * @return
     */
    @RequestMapping(value = "add")
    public ResultBean<Boolean> add(Record record,@RequestParam(value = "weeks") String weeks) {
        return new ResultBean<>(recordService.addRecord(record,weeks));
    }

    /**
     *
     * @param record update
     * @return
     */
    @RequestMapping(value = "update")
    public ResultBean<Boolean> update(Record record) {
        return new ResultBean<>(recordService.updateRecord(record));
    }


    /**
     * find one by id
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "find_by_id")
    public ResultBean<Record> findById(@RequestParam(value = "id") String id) {
        return new ResultBean<>(recordService.findById(id));
    }

    /**
     * search by [词典]	condition
     * @return list
     */
    @RequestMapping(value = "find_by_condition")
    public ResultBean<List<Record>> findByCondition(@RequestParam(value = "uid",required = false) String uid,@RequestParam(value = "month",required = false) String month,@RequestParam(value = "group",required = false) String group) {
        return new ResultBean<>(recordService.findByCondition(uid,group,month));
    }

    @RequestMapping(value = "findAll")
    public ResultBean<List<Record>> findAll() {
        return new ResultBean<>(recordService.findAll());
    }

    @RequestMapping(value = "del")
    public  ResultBean<Boolean> del(String id) {
        return new ResultBean<>(recordService.delRecord(id));
    }

}

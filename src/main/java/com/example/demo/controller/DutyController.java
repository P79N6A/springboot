package com.example.demo.controller;

import com.example.demo.common.ResultBean;
import com.example.demo.model.DutySheet;
import com.example.demo.model.WorkDuty;
import com.example.demo.service.WorkDutyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "duty")
@Api(value = "duty", description = "值班计划接口")
public class DutyController {
    @Autowired
    private WorkDutyService workDutyService;
    /**
     * add duty
     * @param workDuty
     * @return boolean
     */
    @ApiOperation(value = "新建值班",notes = "创建值班记录接口")
    @PostMapping(value = "create")
    public ResultBean<Boolean> create(WorkDuty workDuty) {
        return new ResultBean<>(workDutyService.createWorkDuty(workDuty));
    }
    @ApiOperation(value = "查询值班记录",notes = "查询所有值班列表接口")
    @GetMapping(value = "findAllDuty")
    public ResultBean<List<WorkDuty>> findAllDuty() {
        return new ResultBean<>(workDutyService.findAllDuty());
    }
    @ApiOperation(value = "查询单条值班计划",notes = "根据id查询值班记录")
    @GetMapping(value = "findDutyById")
    public ResultBean<WorkDuty> findDutyById(String id) {
        return new ResultBean<>(workDutyService.findDutyById(id));
    }
    @ApiOperation(value = "查询月度计划",notes = "根据月度查询值班列表")
    @GetMapping(value = "findByMoth")
    public ResultBean<List<WorkDuty>> findByMoth(String month) {
        return new ResultBean<>(workDutyService.findByMoth(month));
    }
    @ApiOperation(value = "删除值班记录",notes = "根据id删除值班记录")
    @PostMapping(value = "delDuty")
    public ResultBean<Boolean> delDuty(String id) {
        return new ResultBean<>(workDutyService.delDuty(id));
    }
    @ApiOperation(value = "删除值班安排表",notes = "根据id删除值班安排表")
    @PostMapping(value = "delSheet")
    public ResultBean<Boolean> delSheet(String id) {
        return new ResultBean<>(workDutyService.delSheet(id));
    }
    @ApiOperation(value = "根据执行日期查询值班数据",notes = "根据startTime查询值班数据")
    @GetMapping(value = "findByStartTime")
    public ResultBean<WorkDuty> findByStartTime(String startTime) {
        return new ResultBean<>(workDutyService.findByStartTime(startTime));
    }
    @ApiOperation(value = "生成值班安排表",notes="根据月度值班记录生成值班安排表")
    @PostMapping(value = "buildDutySheet")
    public ResultBean<Boolean> buildDutySheet(String month) {
        return new ResultBean<>(workDutyService.buildDutySheet(month));
    }
    @ApiOperation(value = "获取所有安排表",notes="获取所有值班计划安排表")
    @GetMapping(value = "findAllSheet")
    public ResultBean<List<DutySheet>> findAllSheet() {
        return new ResultBean<>(workDutyService.findAllSheet());
    }
    @ApiOperation(value = "获取某月度值班表",notes="根据id获取某月度值班表")
    @GetMapping(value = "findSheetById")
    public ResultBean<DutySheet> findSheetById(String id) {
        return new ResultBean<>(workDutyService. findSheetById(id));
    }

    @PostMapping(value = "chat/callback")
    public String callBack(@RequestParam(value = "senderId",required = false) String uid) {
//        Enumeration em = request.getParameterNames();
//        while (em.hasMoreElements()) {
//            String name = (String) em.nextElement();
//            String value = request.getParameter(name);
//        }

        return workDutyService.findDutyByCurrentTime(uid);
    }
}

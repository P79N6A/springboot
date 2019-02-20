package com.example.demo.controller;

import com.example.demo.common.ResultBean;
import com.example.demo.model.Account;
import com.example.demo.model.ApplyRole;
import com.example.demo.service.AccountService;
import com.example.demo.service.ApplyRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "apply/role")
public class ApplyRoleController {
    @Autowired
    private ApplyRoleService applyRoleService;

    @PostMapping(value = "create")
    public ResultBean<Boolean> add(ApplyRole applyRole) {
        return new ResultBean<>(applyRoleService.createApply(applyRole));
    }

    @GetMapping(value = "findAll")
    public ResultBean<List<ApplyRole>> finAll() {
        return new ResultBean<>(applyRoleService.findAll());
    }

    @GetMapping(value = "findByStatus")
    public ResultBean<List<ApplyRole>> findByStatus(int status) {
        return new ResultBean<>(applyRoleService.findByStatus(status));
    }

    @PostMapping(value = "audit")
    public ResultBean<Boolean> audit(@RequestParam(value = "id") String id,@RequestParam(value = "status") Integer status,@RequestParam(value = "operator") String operator) {
        return new ResultBean<>(applyRoleService.audit(id,status,operator));
    }

    @GetMapping(value = "queryStatus")
    public ResultBean<ApplyRole> queryStatus(String userId) {
        return new ResultBean<>(applyRoleService.queryApply(userId));
    }

}

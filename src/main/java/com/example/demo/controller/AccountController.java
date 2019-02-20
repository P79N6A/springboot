package com.example.demo.controller;

import com.example.demo.common.ResultBean;
import com.example.demo.model.Account;
import com.example.demo.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.Inet4Address;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "account")
@Api(value = "account", description = "用户信息接口")
public class AccountController {
    @Autowired
    private AccountService accountService;

    /**
     * add account
     *
     * @param account
     * @return
     */
    @ApiOperation(value="新增用户", notes="用户新增接口")
    @PostMapping(value = "add")
    public ResultBean<Boolean> add(Account account) {
        return new ResultBean<>(accountService.addAccount(account));
    }


    /**
     * find one by id
     *
     * @param id
     * @return
     */
    @ApiOperation(value="查询用户", notes="根据id查询用户信息")
    @GetMapping(value = "find_by_id")
    public ResultBean<Account> findById(@RequestParam(value = "id") String id) {
        return new ResultBean<>(accountService.findById(id));
    }
    @ApiOperation(value = "查询所有用户",notes = "查询所有用户信息")
    @GetMapping(value = "findAll")
    public ResultBean<List<Account>> findAll() {
        return new ResultBean<>(accountService.findAll());
    }

    @ApiOperation(value = "删除用户信息",notes = "根据id删除用户")
    @PostMapping(value = "del")
    public  ResultBean<Boolean> del(String id) {
        return new ResultBean<>(accountService.del(id));
    }

    @ApiOperation(value = "用户登录",notes = "用户登录接口")
    @GetMapping(value = "login")
    public  ResultBean<Account> login(@RequestParam(value = "account") String account,@RequestParam(value = "password") String password) {
        return new ResultBean<>(accountService.findByAccountAndPassword(account,password));
    }
    @ApiOperation(value = "域登录",notes = "用户域账号登录接口")
    @GetMapping(value = "authLogin")
    public  ResultBean authLogin(@RequestParam(value = "userName") String userName,@RequestParam(value = "password") String password) {
        return new ResultBean<>(accountService.authLogin(userName,password));
    }

    @ApiOperation(value = "用户更新",notes="更新用户信息接口")
    @PostMapping(value = "update")
    public  ResultBean<Boolean> update(Account account) {
        return new ResultBean<>(accountService.updateAccount(account));
    }
    @ApiOperation(value = "会议通知更新",notes="更新用户信息")
    @PostMapping(value = "updateSequence")
    public  ResultBean<Boolean> updateSequence(@RequestParam(value = "id") String id,@RequestParam(value = "review")Integer review) {
        return new ResultBean<>(accountService.updateSequence(id,review));
    }
    @PostMapping(value = "updateLock")
    public  ResultBean<Boolean> updateLock(@RequestParam(value = "id") String id,@RequestParam(value = "lock")Integer lock) {
        return new ResultBean<>(accountService.updateLock(id,lock));
    }

    @ApiOperation(value = "会议通知列表",notes="会议通知列表")
    @GetMapping(value = "showMeetingList")
    public  ResultBean<List<Account>> showMeetingList() {
        return new ResultBean<>(accountService.showMeetingList());
    }

    @GetMapping(value = "showLockList")
    public  ResultBean<List<Account>> showLockList() {
        return new ResultBean<>(accountService.getLockSequence(0,1));
    }

    @PostMapping(value = "updateRoleWithCode")
    public  ResultBean<Boolean> updateRoleWithCode(@RequestParam(value = "id") String id,@RequestParam(value = "code")String codes) {
        return new ResultBean<>(accountService.updateRoleWithCode(id,codes));
    }

}

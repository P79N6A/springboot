package com.example.demo.controller;

import com.example.demo.common.ResultBean;
import com.example.demo.common.zcy.DESPlus;
import com.example.demo.model.Account;
import com.example.demo.model.Menu;
import com.example.demo.service.AccountService;
import com.example.demo.service.MenuService;
import io.swagger.annotations.Api;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "menu")
@Api(value = "menu", description = "系统菜单管理接口")
public class MenuController {
    @Autowired
    private MenuService menuService;

    /**
     * add menu
     *
     * @param menu
     * @return boolean
     */
    @PostMapping(value = "add")
    public ResultBean<Boolean> add(Menu menu) {
        return new ResultBean<>(menuService.addMenu(menu));
    }

    /**
     * find one by id
     *
     * @param id
     * @return menu
     */
    @GetMapping(value = "find_by_id")
    public ResultBean<Menu> findById(@RequestParam(value = "id") String id) {
        return new ResultBean<>(menuService.findById(id));
    }

    @GetMapping(value = "findAll")
    public ResultBean<List<Menu>> findAll() {
        return new ResultBean<>(menuService.findAll());
    }

    @PostMapping(value = "del")
    public  ResultBean<Boolean> del(String id) {
        return new ResultBean<>(menuService.del(id));
    }


    @GetMapping(value = "init")
    public  ResultBean<List<Menu>> findMenuByRole(int role) {
            return new ResultBean<>(menuService.findMenuByRole(role));
    }

    @GetMapping(value = "listWithCode")
    public  ResultBean<List<Menu>> queryMenuListWithCode(String id) {
        return new ResultBean<>(menuService.queryMenuListWithCode(id));
    }

    @PostMapping(value = "update")
    public  ResultBean<Boolean> del(Menu menu) {
        return new ResultBean<>(menuService.updateMenu(menu));
    }

    @PostMapping("hash")
    public ResultBean<Integer> getHash(@RequestParam(value = "handler") String handler){
        if (handler.isEmpty()){
            return new ResultBean("handler code is null");
        }
        Long str=Long.parseLong(handler);
        int result=str.hashCode()%64;
        return new ResultBean(result);
    }

    @PostMapping("des/decode")
    public ResultBean decode(@RequestParam(value = "code") String codes) throws Exception {
        if (codes.isEmpty()){
            return new ResultBean("verify code is not null");
        }
        DESPlus dESPlus = new DESPlus();
        String list[]=codes.split(",");
        StringBuffer stringBuffer=new StringBuffer();
        for (int i = 0; i <list.length ; i++) {
            String result = new String(dESPlus.decrypt(list[i]).getBytes("gbk"),"utf-8");
            if(StringUtils.isBlank(result)){
                stringBuffer.append(list[i]).append("：").append("<font color=\"green\">").append(result).append("</font>");
            }else{
                stringBuffer.append("</br>").append(list[i]).append("：").append("<font color=\"green\">").append(result).append("</font>");
            }
        }
        return new ResultBean(stringBuffer.toString());
    }
}

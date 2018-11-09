package com.example.demo.service.impl;

import com.example.demo.common.Initialization;
import com.example.demo.dao.MenuRespository;
import com.example.demo.model.Account;
import com.example.demo.model.Menu;
import com.example.demo.service.MenuService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuRespository menuRespository;

    @Override
    public boolean addMenu(Menu menu) {
        if (StringUtils.isNotBlank(menu.getMenu_name()) && StringUtils.isNotBlank(menu.getNav_name())) {
                menu.setGmt_create(Initialization.formatTime());
                menu.setRole_name(allotRole(menu.getRole()));
                menu.setStatus(Initialization.MENU_STATUS);
                menu.setStatus_name(allotStatus(Initialization.MENU_STATUS));
                menuRespository.save(menu);
                return  true;
        }
        return false;
    }

    @Override
    public List<Menu> findAll() {
        return menuRespository.findAll(new Sort(new Sort.Order(Sort.Direction.ASC,"sort_code")));}

    @Override
    public Menu findById(String id) {
        return menuRespository.findOne(id);
    }

    @Override
    public boolean del(String id) {
        if(StringUtils.isNotBlank(id)){
            menuRespository.delete(id);
            return  true;
        }
        return false;
    }

    @Override
    public List<Menu> findMenuByRole(int role) {
        if (role == Initialization.MENU_ROLE_ADMIN) {
            return menuRespository.findAll(new Sort(new Sort.Order(Sort.Direction.ASC,"sort_code")));
        }else if (role == Initialization.MENU_ROLE_CUSTOMER|| role == Initialization.MENU_ROLE_OPERATOR) {
            return menuRespository.findByRoleNot(Initialization.MENU_ROLE_ADMIN,new Sort(new Sort.Order(Sort.Direction.ASC,"sort_code")));
        }else{
            return  menuRespository.findByRole(role,new Sort(new Sort.Order(Sort.Direction.ASC,"sort_code")));
         }
    }

    @Override
    public boolean updateMenu(Menu menu) {
        if(StringUtils.isNotBlank(menu.getId())){
            boolean ex=menuRespository.exists(menu.getId());
            if(ex){
               Menu mu=menuRespository.findOne(menu.getId());
               mu.setIcon(menu.getIcon());
               menuRespository.save(mu);
                return true;
            }
            return  false;
        }
        return false;
    }


    private  String allotStatus(int sid){
        Map<Integer,String > map=new ConcurrentHashMap<>();
        map.put(10,"启用");
        map.put(20,"禁用");
        return  map.get(sid);
    }

    private  String allotRole(int rid){
        Map<Integer,String > map=new ConcurrentHashMap<>();
        map.put(0,"管理员");
        map.put(1,"运营");
        map.put(2,"审核");
        map.put(3,"通用");
        return  map.get(rid);
    }

}

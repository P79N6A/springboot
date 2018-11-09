package com.example.demo.service.impl;

import com.example.demo.common.Initialization;
import com.example.demo.dao.AccountRespository;
import com.example.demo.dao.UserRespository;
import com.example.demo.model.Account;
import com.example.demo.model.User;
import com.example.demo.service.AccountService;
import com.example.demo.service.UserService;
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
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRespository accountRespository;

    @Override
    public boolean addAccount(Account account) {
        if (StringUtils.isNotBlank(account.getAccount()) && StringUtils.isNotBlank(account.getNick_name())) {
                account.setGroup(allotGroup(account.getGroup()));
                account.setStatus_name(allotStatus(Initialization.ACCOUNT_STATUS));
                account.setRole_name(allotRole(account.getRole()));
                account.setGmt_create(Initialization.formatTime());
                account.setStatus(Initialization.ACCOUNT_STATUS);
                account.setPassword(Initialization.ACCOUNT_INIT_PWD);
                accountRespository.save(account);
            return true;
        }
        return false;

    }

    @Override
    public List<Account> findAll() {
        return accountRespository.findAll(new Sort(new Sort.Order(Sort.Direction.DESC,"id")));}

    @Override
    public Account findById(String id) {
        return accountRespository.findOne(id);
    }

    @Override
    public boolean del(String id) {
        if(StringUtils.isNotBlank(id)){
            accountRespository.delete(id);
            return  true;
        }
        return false;
    }

    @Override
    public Account findByAccountAndPassword(String account, String password) {
        if (StringUtils.isNotBlank(account) && StringUtils.isNotBlank(password)) {
            return accountRespository.findByAccountAndPasswordAndStatus(account,password,Initialization.ACCOUNT_STATUS);
        }
        return null;
    }

    @Override
    public boolean updateAccount(Account account) {
        if(StringUtils.isNotBlank(account.getId())){
            boolean ex=accountRespository.exists(account.getId());
            if(ex){
                Account this_=accountRespository.findOne(account.getId());
                this_.setPassword(account.getPassword());
                this_.setGmt_modify(Initialization.formatTime());
                //this_.setNick_name(account.getNick_name());
                //this_.setPhone(account.getPhone());
                accountRespository.save(this_);
                return true;
            }
            return  false;
        }
        return false;
    }



    private  String allotGroup(String g_id){
        Map<String,String > m=new ConcurrentHashMap<>();
        m.put("0","项目采购");
        m.put("1","网上超市");
        m.put("2","定点服务");
        m.put("3","协议供货");
        m.put("4","业务审核");
        m.put("5","客满服务");
        m.put("6","其他类别");
        return  m.get(g_id);
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
        map.put(3,"用户");
        return  map.get(rid);
    }

}

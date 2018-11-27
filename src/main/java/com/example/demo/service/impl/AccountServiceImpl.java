package com.example.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.demo.common.Initialization;
import com.example.demo.dao.AccountRespository;
import com.example.demo.dao.MenuRespository;
import com.example.demo.dao.UserRespository;
import com.example.demo.model.Account;
import com.example.demo.model.Menu;
import com.example.demo.model.User;
import com.example.demo.service.AccountService;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRespository accountRespository;
    @Autowired
    private MenuRespository menuRespository;

    @Override
    public boolean addAccount(Account account) {
        log.info("account request params:"+JSON.toJSONString(account));
        if (StringUtils.isNotBlank(account.getAccount()) && StringUtils.isNotBlank(account.getNick_name())) {
                account.setGroup(allotGroup(account.getGroup()));
                account.setStatus_name(allotStatus(Initialization.ACCOUNT_STATUS));
                account.setRole_name(allotRole(account.getRole()));
                account.setGmt_create(Initialization.formatTime());
                account.setStatus(Initialization.ACCOUNT_STATUS);
                account.setPassword(Initialization.ACCOUNT_INIT_PWD);
                if(!StringUtils.isNotBlank(account.getRoleLot())){
                    if (account.getRole()==0||account.getRole()==1){
                        List<Menu> menuList=menuRespository.findByStatus(10);
                        StringBuffer stringBuffer=new StringBuffer();
                        for (int i = 0; i <menuList.size() ; i++) {
                            stringBuffer.append(menuList.get(i).getRoleCode()).append(",");
                        }
                        account.setRoleLot(stringBuffer.toString());
                    }else{
                        account.setRoleLot("1001");
                    }
                }
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
//                this_.setNick_name(account.getNick_name());
//                this_.setMeeting(1);
//                this_.setSortLot(account.getSortLot());
                accountRespository.save(this_);
                return true;
            }
            return  false;
        }
        return false;
    }

    @Override
    public List<Account> showMeetingList() {
        return accountRespository.findByMeeting(1,new Sort(new Sort.Order(Sort.Direction.ASC,"sortLot")));
    }

    @Override
    public List<Account> getMeetingSequence(int meeting, int review) {
        return accountRespository.findByMeetingAndReview(meeting,review,new Sort(new Sort.Order(Sort.Direction.ASC,"sortLot")));
    }

    @Override
    public boolean updateSequence(String id,int review) {
        if(StringUtils.isNotBlank(id)){
            boolean exists=accountRespository.exists(id);
            if(exists){
                Account account=accountRespository.findOne(id);
                account.setReview(review);
                accountRespository.save(account);
                return true;
            }
            return  false;
        }
        return false;
    }

    @Override
    public boolean updateRoleWithCode(String id, String codes) {
        log.info("role list:"+codes);
        if(StringUtils.isNotBlank(id)){
            boolean exists=accountRespository.exists(id);
            if(exists){
                Account account=accountRespository.findOne(id);
//                Set<String> set=new HashSet<>();
//                if(StringUtils.isNotBlank(account.getRoleLot())){
//                    String[] oleCode=account.getRoleLot().split(",");
//                    for (int i = 0; i <oleCode.length ; i++) {
//                        set.add(oleCode[i]);
//                    }
//                }
//
//                log.info(JSON.toJSONString(set));
//                String[] newCode=codes.replaceAll("\"","").split(",");
//                for (int j = 0; j <newCode.length ; j++) {
//                    set.add(newCode[j]);
//                }
//                log.info(JSON.toJSONString(set));
//                String currentCode=StringUtils.join(set.toArray(),",");
//                log.info("result role code list:"+currentCode);
                account.setRoleLot(codes);
                account.setGmt_modify(Initialization.formatTime());
                accountRespository.save(account);
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

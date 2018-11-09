package com.example.demo.service.impl;

import com.example.demo.dao.UserRespository;
import com.example.demo.model.User;
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
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRespository userRespository;


    @Override
    public boolean addUser(User user) {
        if (StringUtils.isNotBlank(user.getName()) && StringUtils.isNotBlank(user.getGroup())&&StringUtils.isNotBlank(user.getJoin_date())) {
                user.setGroup_name(allotGroup(user.getGroup()));
                user.setGmt_create(formatTime());
                userRespository.save(user);
            return true;
        }
        return false;

    }



    @Override
    public List<User> findAll() {
        return userRespository.findAll(new Sort(new Sort.Order(Sort.Direction.DESC,"id")));}

    @Override
    public User findById(String id) {
        return userRespository.findOne(id);
    }

    @Override
    public boolean delUser(String id) {
        if(StringUtils.isNotBlank(id)){
            userRespository.delete(id);
            return  true;
        }
        return false;
    }


    private String formatTime(){
       return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
    private  String allotGroup(String g_id){
        Map<String,String > m=new ConcurrentHashMap<>();
        m.put("0","新人组");
        m.put("1","支付一组");
        m.put("2","支付二组");
        m.put("3","综合组");
        m.put("4","ISV小组");
        m.put("5","大商户组");
        return  m.get(g_id);
    }

}

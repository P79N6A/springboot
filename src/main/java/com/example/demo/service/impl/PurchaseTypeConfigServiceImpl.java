package com.example.demo.service.impl;

import com.example.demo.common.Initialization;
import com.example.demo.dao.PurchaseTypeConfigRespository;
import com.example.demo.dao.UserRespository;
import com.example.demo.model.PurchaseTypeConfig;
import com.example.demo.model.User;
import com.example.demo.service.PurchaseTypeConfigService;
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
public class PurchaseTypeConfigServiceImpl implements PurchaseTypeConfigService {
    @Autowired
    private PurchaseTypeConfigRespository purchaseTypeConfigRespository;

    @Override
    public boolean addPurchaType(PurchaseTypeConfig purchaseTypeConfig) {
        if (StringUtils.isNotBlank(purchaseTypeConfig.getName()) && purchaseTypeConfig.getType()!=0) {
            purchaseTypeConfig.setStatus_name(allotStatus(Initialization.PURCHASE_TYPE_CONFIG_STATUS));
            purchaseTypeConfig.setGmt_create(Initialization.formatTime());
            purchaseTypeConfig.setStatus(Initialization.PURCHASE_TYPE_CONFIG_STATUS);
            purchaseTypeConfig.setType_name(allotType(purchaseTypeConfig.getType()));
           purchaseTypeConfigRespository.save(purchaseTypeConfig);
            return true;
        }
        return false;
    }

    @Override
    public List<PurchaseTypeConfig> findAll() {
        return purchaseTypeConfigRespository.findAll(new Sort(new Sort.Order(Sort.Direction.DESC,"type")));}

    @Override
    public PurchaseTypeConfig findById(String id) {
        return purchaseTypeConfigRespository.findOne(id);
    }

    @Override
    public boolean del(String id) {
        if(StringUtils.isNotBlank(id)){
            purchaseTypeConfigRespository.delete(id);
            return  true;
        }
        return false;
    }

    @Override
    public List<PurchaseTypeConfig> findByTypeAndStatus(int type) {
        if(type!=Initialization.PURCHASE_TYPE_CONFIG_TYPE){
           return purchaseTypeConfigRespository.findByTypeAndStatus(type,Initialization.PURCHASE_TYPE_CONFIG_STATUS);
        }
        return null;
    }

    private  String allotStatus(int sid){
        Map<Integer,String > m=new ConcurrentHashMap<>();
        m.put(10,"启用");
        m.put(20,"废弃");
        return  m.get(sid);
    }
    private  String allotType(int tid){
        Map<Integer,String > m=new ConcurrentHashMap<>();
        m.put(1,"采购类型");
        m.put(2,"采购方式");
        m.put(3,"资金类型");
        m.put(4,"付款方式");
        return  m.get(tid);
    }

}

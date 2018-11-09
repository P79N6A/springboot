package com.example.demo.service.impl;

import com.example.demo.common.EnmuList;
import com.example.demo.common.Initialization;
import com.example.demo.dao.GpcatlogRespository;
import com.example.demo.model.Gpcatlog;
import com.example.demo.service.GpcatlogService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@Service
public class GpcatlogServiceImpl implements GpcatlogService {
    @Autowired
    private GpcatlogRespository gpcatlogRespository;

    @Override
    public boolean addGpcatlog(Gpcatlog gpcatlog) {
        if (StringUtils.isNotBlank(gpcatlog.getCode()) && StringUtils.isNotBlank(gpcatlog.getName())) {
            gpcatlog.setGmt_create(Initialization.formatTime());
           gpcatlogRespository.save(gpcatlog);
            return true;
        }
        return false;
    }

    @Override
    public List<Gpcatlog> findAll() {
        return gpcatlogRespository.findAll(new Sort(new Sort.Order(Sort.Direction.ASC,"level")));}

    @Override
    public Gpcatlog findById(String id) {
        return gpcatlogRespository.findOne(id);
    }

    @Override
    public boolean delGpcatlog(String id) {
        if(StringUtils.isNotBlank(id)){
            gpcatlogRespository.delete(id);
            return  true;
        }
        return false;
    }

    @Override
    public  List findBySqual() {
        Gpcatlog codeA=gpcatlogRespository.findByCode("A");
        Gpcatlog codeB=gpcatlogRespository.findByCode("B");
        Gpcatlog codeC=gpcatlogRespository.findByCode("C");
        List<Gpcatlog> levelA2=gpcatlogRespository.findByLevelAndPartner("2",codeA.getNode());
        List<Gpcatlog> levelB2=gpcatlogRespository.findByLevelAndPartner("2",codeB.getNode());
        List<Gpcatlog> levelC2=gpcatlogRespository.findByLevelAndPartner("2",codeC.getNode());
        EnmuList emA=new EnmuList();
        emA.setId(codeA.getId());
        emA.setCode(codeA.getCode());
        emA.setName(codeA.getName());
        emA.setPartner(codeA.getNode());
        emA.setChildren(levelA2);
        EnmuList emB=new EnmuList();
        emB.setId(codeB.getId());
        emB.setCode(codeB.getCode());
        emB.setName(codeB.getName());
        emB.setPartner(codeB.getNode());
        emB.setChildren(levelB2);
        EnmuList emC=new EnmuList();
        emC.setId(codeC.getId());
        emC.setCode(codeC.getCode());
        emC.setName(codeC.getName());
        emC.setPartner(codeC.getNode());
        emC.setChildren(levelC2);
        List a =new ArrayList();
        a.add(emA);
        a.add(emB);
        a.add(emC);
        return  a;
    }

    @Override
    public List<Gpcatlog> findByLevel(String level) {
        return gpcatlogRespository.findByLevel(level);
    }

    @Override
    public boolean choseFlag() {
        LinkedHashSet<String> set=new LinkedHashSet();
        List<Gpcatlog> list=gpcatlogRespository.findAll();
        if(list.size()!=0){

            for (int i = 0; i <list.size() ; i++) {
                if(StringUtils.isNotBlank(list.get(i).getPartner())) {
                    set.add(list.get(i).getPartner());
                }
                if(list.get(i).getFlag()==0){
                    Gpcatlog gpcatlog=gpcatlogRespository.findOne(list.get(i).getId());
                    gpcatlog.setFlag_name("正常");
                    gpcatlog.setGmt_modify(Initialization.formatTime());
                    gpcatlogRespository.save(gpcatlog);
                }
            }
            for (String s:set){
                Gpcatlog gpcatlog=gpcatlogRespository.findByNode(s);
                gpcatlog.setFlag(1);
                gpcatlog.setFlag_name("禁选");
                gpcatlogRespository.save(gpcatlog);
            }
            return true;
        }
       return false;
    }

    @Override
    public List<Gpcatlog> findByFlag(int flag) {
        return gpcatlogRespository.findByFlag(flag);
    }

}

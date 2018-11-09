package com.example.demo.service.impl;

import com.example.demo.common.Initialization;
import com.example.demo.dao.FunctionalSubjectRespository;
import com.example.demo.model.FunctionalSubject;
import com.example.demo.model.Gpcatlog;
import com.example.demo.service.FunctionalSubjectService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class FunctionalSubjectServiceImpl implements FunctionalSubjectService {
    @Autowired
    private FunctionalSubjectRespository functionalSubjectRespository;

    @Override
    public boolean addSubject(FunctionalSubject functionalSubject) {
        if (StringUtils.isNotBlank(functionalSubject.getName()) && functionalSubject.getType()!=0&&StringUtils.isNotBlank(functionalSubject.getCode()) ) {
            functionalSubject.setGmt_create(Initialization.formatTime());
            functionalSubject.setType_name(allotType(functionalSubject.getType()));
            functionalSubjectRespository.save(functionalSubject);
            return true;
        }
        return false;
    }

    @Override
    public List<FunctionalSubject> findAll() {
        return functionalSubjectRespository.findAll(new Sort(new Sort.Order(Sort.Direction.ASC,"type")));
    }

    @Override
    public FunctionalSubject findById(String id) {
        return functionalSubjectRespository.findOne(id);
    }

    @Override
    public boolean del(String id) {
        if(StringUtils.isNotBlank(id)){
            functionalSubjectRespository.delete(id);
            return  true;
        }
        return false;
    }

    @Override
    public boolean updateFlag() {
        LinkedHashSet<String> set=new LinkedHashSet();
        List<FunctionalSubject> list=functionalSubjectRespository.findAll();
        if(list.size()!=0){
            for (int i = 0; i <list.size() ; i++) {
                if(StringUtils.isNotBlank(list.get(i).getPartner())) {
                    set.add(list.get(i).getPartner());
                }
                if(list.get(i).getFlag()==0){
                    FunctionalSubject functionalSubject=functionalSubjectRespository.findOne(list.get(i).getId());
                    functionalSubject.setFlag_name("正常");
                    functionalSubject.setGmt_modify(Initialization.formatTime());
                   functionalSubjectRespository.save(functionalSubject);
                }
            }
            for (String s:set){
                if(!s.equals("0")) {
                    FunctionalSubject functionalSubject = functionalSubjectRespository.findByCode(s);
                    functionalSubject.setFlag(1);
                    functionalSubject.setFlag_name("禁选");
                    functionalSubject.setGmt_modify(Initialization.formatTime());
                    functionalSubjectRespository.save(functionalSubject);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public List<FunctionalSubject>  findByTypeAndFlag(int type,int flag) {
        return functionalSubjectRespository.findByTypeAndFlag(type,flag);
    }

    private  String allotType(int tid){
        Map<Integer,String > m=new ConcurrentHashMap<>();
        m.put(1,"预算科目");
        m.put(2,"经济科目");
        return  m.get(tid);
    }
}

package com.example.demo.service.impl;

import com.example.demo.common.Initialization;
import com.example.demo.dao.RelationRespository;
import com.example.demo.model.Relation;
import com.example.demo.service.RelationService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelationServiceImpl implements RelationService {
    @Autowired
    private RelationRespository relationRespository ;


    @Override
    public boolean addRelation(Relation relation) {
        if (StringUtils.isNotBlank(relation.getRelation_code())&&relation.getRelation()!=0) {
                relation.setGmt_create(Initialization.formatTime());
                relationRespository.save(relation);
            return true;
        }
        return false;

    }



    @Override
    public List<Relation> findAll() {
        return relationRespository.findAll(new Sort(new Sort.Order(Sort.Direction.DESC,"id")));}

    @Override
    public Relation findById(String id) {
        return relationRespository.findOne(id);
    }

    @Override
    public boolean del(String id) {
        if(StringUtils.isNotBlank(id)){
            relationRespository.delete(id);
            return  true;
        }
        return false;
    }
    @Override
    public boolean update(String id) {
        if(StringUtils.isNotBlank(id)){
            Relation relation=relationRespository.findOne(id);
            relation.setRelation(relation.getRelation());
            relationRespository.save(relation);
            return  true;
        }
        return false;
    }


}

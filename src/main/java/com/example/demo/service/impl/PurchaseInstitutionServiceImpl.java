package com.example.demo.service.impl;

import com.example.demo.common.Initialization;
import com.example.demo.dao.PurchaseInstitutionRespository;
import com.example.demo.model.PurchaseInstitution;
import com.example.demo.service.PurchaseInstitutionService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseInstitutionServiceImpl implements PurchaseInstitutionService {
    @Autowired
    private PurchaseInstitutionRespository purchaseInstitutionRespository;


    @Override
    public boolean addInstitution(PurchaseInstitution purchaseInstitution) {
        if (StringUtils.isNotBlank(purchaseInstitution.getName()) && StringUtils.isNotBlank(purchaseInstitution.getBudget_code())) {
            purchaseInstitution.setGmt_create(Initialization.formatTime());
            purchaseInstitutionRespository.save(purchaseInstitution);
            return true;
        }
        return false;
    }

    @Override
    public List<PurchaseInstitution> findAll() {
        return purchaseInstitutionRespository.findAll(new Sort(new Sort.Order(Sort.Direction.DESC,"id")));}

    @Override
    public PurchaseInstitution findById(String id) {
        return purchaseInstitutionRespository.findOne(id);
    }

    @Override
    public boolean delInstitution(String id) {
        if(StringUtils.isNotBlank(id)){
            purchaseInstitutionRespository.delete(id);
            return  true;
        }
        return false;
    }


}

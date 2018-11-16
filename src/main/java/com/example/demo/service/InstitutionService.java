package com.example.demo.service;

import com.example.demo.common.ResultBean;
import com.example.demo.model.car.Institution;
import com.example.demo.model.car.InstitutionImportParams;

import java.util.List;

public interface InstitutionService {
    ResultBean importOrg(List<InstitutionImportParams> institutionImportParamsList,String operator);
    ResultBean queryOrg(String districtCode,String orgName,String orgCode,String orgRealName);
    ResultBean delOrg(String orgId,String orgCode,String orgName,String carManager,String phone,String operator);
    List<Institution> findAll();
    Institution findById(String id);
    boolean removeOrgRecord(String id);
    List<Institution> findByStatus(int status);
    List<Institution> findByUser(int status,String operator);
}

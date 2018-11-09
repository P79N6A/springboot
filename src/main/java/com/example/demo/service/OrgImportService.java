package com.example.demo.service;

import com.example.demo.common.ResultBean;
import com.example.demo.model.car.Institution;
import com.example.demo.model.car.InstitutionImportParams;
import com.example.demo.model.car.InstitutionQueryParams;

import java.util.List;

public interface OrgImportService {
    ResultBean importOrg(List<InstitutionImportParams> institutionImportParamsList);
    ResultBean queryOrg(InstitutionQueryParams institutionQueryParams);
    ResultBean delOrg(String orgId,String orgCode,String orgName,String carManager,String phone,String operator);
    List<Institution> findAll();
    boolean removeOrgRecord(String id);
    List<Institution> findByStatus(int status);
}

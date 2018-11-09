package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.common.RequestUtil;
import com.example.demo.common.ResultBean;
import com.example.demo.model.car.Institution;
import com.example.demo.model.car.InstitutionImportParams;
import com.example.demo.model.car.InstitutionQueryParams;
import com.example.demo.service.OrgImportService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "org")
@Slf4j
public class OrgManageController {
    @Autowired
    private OrgImportService orgImportService;

    @PostMapping(value = "init")
     public ResultBean stock(@RequestParam("file") MultipartFile file) {
        if(file.isEmpty()){
            log.info("文件不能为空");
            return new ResultBean("文件不能为空");
        }
        try {
            List<List<Object>> list = RequestUtil.getBankListByExcel(file.getInputStream(),file.getOriginalFilename());
            List<InstitutionImportParams> institutions = new ArrayList<>();
            InstitutionImportParams institutionImportParams;
            for (int i = 0; i < list.size(); i++) {
                institutionImportParams = new InstitutionImportParams();
                if(StringUtils.isNotBlank(list.get(i).get(0).toString())&&StringUtils.isNotBlank(list.get(i).get(1).toString())){
                    institutionImportParams.setLineNum(String.valueOf(i+1));
                    institutionImportParams.setOrgCode(list.get(i).get(0).toString());
                    institutionImportParams.setOrgName(list.get(i).get(1).toString());
                    institutionImportParams.setCarManager(list.get(i).get(2).toString());
                    institutionImportParams.setPhone(list.get(i).get(3).toString());
                    institutions.add(institutionImportParams);
                }
            }
            log.info(JSON.toJSONString(institutions));
            return orgImportService.importOrg(institutions);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping(value = "findAll")
    public ResultBean<List<Institution>> findAll(){
        return new ResultBean<>(orgImportService.findAll());
    }

    @GetMapping(value = "query")
    public ResultBean queryOrg(InstitutionQueryParams institutionQueryParams){
        return new ResultBean<>(orgImportService.queryOrg(institutionQueryParams));
    }

    @PostMapping(value = "del")
    public ResultBean queryOrg(@RequestParam(value = "orgId") String orgId,@RequestParam(value = "orgCode") String orgCode,@RequestParam(value = "orgName") String orgName,@RequestParam(value = "carManager") String carManager,@RequestParam(value = "phone") String phone,@RequestParam(value = "operator")String operator){
        return new ResultBean<>(orgImportService.delOrg(orgId,orgCode,orgName,carManager,phone,operator));
    }

    @PostMapping(value = "remove")
    public ResultBean<Boolean> removeOrgRecord(@RequestParam(value = "id") String id){
        return new ResultBean<>(orgImportService.removeOrgRecord(id));
    }

    @GetMapping(value = "queryByStatus")
    public ResultBean<List<Institution>> queryByStatus(@RequestParam(value = "status") Integer status){
        return new ResultBean<>(orgImportService.findByStatus(status));
    }
}

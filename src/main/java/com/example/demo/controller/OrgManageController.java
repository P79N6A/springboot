package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.common.RequestUtil;
import com.example.demo.common.ResultBean;
import com.example.demo.model.car.Institution;
import com.example.demo.model.car.InstitutionImportParams;
import com.example.demo.service.InstitutionService;
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
    private InstitutionService institutionService;

    @PostMapping(value = "init")
     public ResultBean stock(@RequestParam("file") MultipartFile file,@RequestParam(value = "operator")String operator) {
        if(file.isEmpty()){
            log.info("文件不能为空");
            return ResultBean.isFailure("文件不能为空");
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
            return institutionService.importOrg(institutions,operator);
        } catch (Exception e) {
            return ResultBean.isThrows("文件读取异常"+e.toString());
        }
    }

    @GetMapping(value = "findAll")
    public ResultBean<List<Institution>> findAll(){
        return new ResultBean<>(institutionService.findAll());
    }

    @PostMapping(value = "query")
    public ResultBean queryOrg(@RequestParam(value = "orgName",required = false) String orgName,@RequestParam(value = "orgCode",required = false) String orgCode,@RequestParam(value = "orgRealName",required = false) String orgRealName,@RequestParam(value = "districtCode",required = false) String districtCode){
        return new ResultBean<>(institutionService.queryOrg(districtCode,orgName,orgCode,orgRealName));
    }

    @PostMapping(value = "del")
    public ResultBean queryOrg(@RequestParam(value = "orgId") String orgId,@RequestParam(value = "orgCode") String orgCode,@RequestParam(value = "orgName") String orgName,@RequestParam(value = "carManager") String carManager,@RequestParam(value = "phone") String phone,@RequestParam(value = "operator")String operator){
        return new ResultBean<>(institutionService.delOrg(orgId,orgCode,orgName,carManager,phone,operator));
    }

    @PostMapping(value = "remove")
    public ResultBean<Boolean> removeOrgRecord(@RequestParam(value = "id") String id){
        return new ResultBean<>(institutionService.removeOrgRecord(id));
    }

    @GetMapping(value = "queryByStatus")
    public ResultBean<List<Institution>> queryByStatus(@RequestParam(value = "status") Integer status){
        return new ResultBean<>(institutionService.findByStatus(status));
    }
    @GetMapping(value = "queryByStatusAndUser")
    public ResultBean<List<Institution>> queryByStatusAndUser(@RequestParam(value = "status") Integer status,@RequestParam(value = "operator") String operator){
        return new ResultBean<>(institutionService.findByUser(status,operator));
    }
    @PostMapping(value = "reset")
    public ResultBean reset(@RequestParam(value = "ids")String ids,@RequestParam(value = "operator") String operator){
        Institution institution=institutionService.findById(ids);
        if (institution==null){
            return ResultBean.isThrows("机构备份数据读取异常:"+ids);
        }
        List<InstitutionImportParams> institutionImportList = new ArrayList<>();
        InstitutionImportParams institutionImportParams=new InstitutionImportParams();
        institutionImportParams.setLineNum("1");
        institutionImportParams.setOrgCode(institution.getOrgCode());
        institutionImportParams.setOrgName(institution.getOrgName());
        institutionImportParams.setCarManager(institution.getCarManager());
        institutionImportParams.setPhone(institution.getPhone());
        institutionImportList.add(institutionImportParams);
        return institutionService.importOrg(institutionImportList,operator);
    }
}

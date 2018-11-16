package com.example.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.demo.common.RequestUtil;
import com.example.demo.common.ResultBean;
import com.example.demo.common.zcy.Config;
import com.example.demo.dao.InstitutionRespository;
import com.example.demo.model.car.Institution;
import com.example.demo.model.car.InstitutionImportParams;
import com.example.demo.model.car.InstitutionQueryParams;
import com.example.demo.model.car.delResp.OrgDelResponse;
import com.example.demo.model.car.importResp.ImportResponse;
import com.example.demo.model.car.orgQueryResp.InstitutionResponse;
import com.example.demo.service.InstitutionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class InstitutionServiceImpl implements InstitutionService {
    @Autowired
    private InstitutionRespository institutionRespository;


    @Override
    public ResultBean importOrg(List<InstitutionImportParams> institutionImportParamsList,String operator) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("importInfo",institutionImportParamsList);
        String result=RequestUtil.execRequest(jsonObject.toString(), Config.ORG_INIT_IMPORT);
        if(RequestUtil.isSuccess(result)){
            ImportResponse importResponse =JSON.parseObject(RequestUtil.getPosition(result),ImportResponse.class);
            log.info(JSON.toJSONString(importResponse));
            if(importResponse.isSuccess()){
                if(importResponse.getResult().getFailed()==0&& importResponse.getResult().getSucceed()>0){
                    saveStitution(institutionImportParamsList,operator);
                    return  ResultBean.isSuccess(importResponse);
                }else if(importResponse.getResult().getFailed()>0&& importResponse.getResult().getSucceed()>0){
                    for (int i = importResponse.getResult().getFails().size()-1; i>=0 ; i--) {
                        institutionImportParamsList.remove(Integer.valueOf(importResponse.getResult().getFails().get(i).getLine())-2);
                    }
                    saveStitution(institutionImportParamsList,operator);
                    return  ResultBean.isSuccess(importResponse);
                }else{
                    return  ResultBean.isSuccess(importResponse);
                }
            }else{
                return  ResultBean.isFailure("导入的机构参数列表有误");
            }
        }
        return ResultBean.isThrows("请求链路异常,请稍后再试");
    }

    @Override
    public ResultBean queryOrg(String districtCode,String orgName,String orgCode,String orgRealName) {
        InstitutionQueryParams params=new InstitutionQueryParams();
        if(!"".equals(districtCode)&&StringUtils.isNotBlank(districtCode)){
            params.setDistrictCode(districtCode);
        }
        if(!"".equals(orgName)&&StringUtils.isNotBlank(orgName)){
            params.setOrgName(orgName);
        }
        if(!"".equals(orgCode)&&StringUtils.isNotBlank(orgCode)){
            params.setOrgCode(orgCode);
        }
        if(!"".equals(orgRealName)||StringUtils.isNotBlank(orgRealName)){
            params.setOrgRealName(orgRealName);
        }
        log.info("request params:"+JSON.toJSONString(RequestUtil.convert2Map(params)));
        String result=RequestUtil.execRequest(JSON.toJSONString(RequestUtil.convert2Map(params)),Config.ORG_INIT_QUERY);
        log.info(result);
        if(RequestUtil.isSuccess(result)){
            InstitutionResponse institutionResponse =JSON.parseObject(RequestUtil.getPosition(result),InstitutionResponse.class);
            if(institutionResponse.isSuccess()){
                if(institutionResponse.getResult().isEmpty()&& institutionResponse.getResult().getTotal()==0){
                    return ResultBean.isFailure("查询无结果");
                }else{
                    return ResultBean.isSuccess(institutionResponse);
                }
            }else{
                return ResultBean.isFailure("查询失败,请检查入参");
            }
        }
        return ResultBean.isThrows("请求链路异常,请稍后再试");
    }

    @Override
    public ResultBean delOrg(String orgId,String orgCode,String orgName,String carManager,String phone,String operator) {
        if(StringUtils.isNotBlank(orgId)){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",orgId);
            String result=RequestUtil.execRequest(jsonObject.toString(),Config.ORG_INIT_DELETE);
            log.info(result);
            if(RequestUtil.isSuccess(result)){
                OrgDelResponse orgDelResponse =JSON.parseObject(RequestUtil.getPosition(result),OrgDelResponse.class);
                if(orgDelResponse.isSuccess()&& orgDelResponse.isResult()){
                    Institution institution_exit=institutionRespository.findByOrgCodeAndOrgName(orgCode,orgName);
                    if (institution_exit==null){
                        Institution institution=new Institution();
                        institution.setGmt_create(formatTime());
                        institution.setIsDelete(1);
                        institution.setOrgCode(orgCode);
                        institution.setOrgName(orgName);
                        institution.setCarManager(carManager);
                        institution.setPhone(phone);
                        institution.setOperator(operator);
                        institutionRespository.save(institution);
                    }else{
                        institution_exit.setGmt_modify(formatTime());
                        institution_exit.setIsDelete(1);
                        institution_exit.setOperator(operator);
                        institutionRespository.save(institution_exit);
                    }
                   return ResultBean.isSuccess("删除成功");
                }
                return ResultBean.isFailure(orgDelResponse.getMessage());
            }
            return ResultBean.isThrows("请求链路异常");
        }
        return ResultBean.isFailure("机构id不能为空");
    }

    @Override
    public List<Institution> findAll() {
        return institutionRespository.findAll(new Sort(new Sort.Order(Sort.Direction.DESC,"id")));

    }

    @Override
    public Institution findById(String id) {
        if (StringUtils.isNotBlank(id)){
            return institutionRespository.findOne(id);
        }
        return null;
    }

    @Override
    public boolean removeOrgRecord(String id) {
        if(StringUtils.isNotBlank(id)){
            institutionRespository.delete(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Institution> findByStatus(int status) {
        return institutionRespository.findByIsDelete(status,new Sort(new Sort.Order(Sort.Direction.DESC,"gmt_create")));
    }

    @Override
    public List<Institution> findByUser(int status, String operator) {
        return institutionRespository.findByIsDeleteAndOperator(status,operator,new Sort(new Sort.Order(Sort.Direction.DESC,"gmt_create")));
    }

    private String formatTime(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    private void saveStitution(List<InstitutionImportParams> institutionImportParamsList,String operator){
        Institution institution;
        Institution institution_exits;
        for (int i = 0; i <institutionImportParamsList.size() ; i++) {
            institution_exits=institutionRespository.findByOrgCodeAndOrgName(institutionImportParamsList.get(i).getOrgCode(),institutionImportParamsList.get(i).getOrgName());
            if(institution_exits==null) {
                institution = new Institution();
                institution.setGmt_create(formatTime());
                institution.setOrgCode(institutionImportParamsList.get(i).getOrgCode());
                institution.setOrgName(institutionImportParamsList.get(i).getOrgName());
                institution.setCarManager(institutionImportParamsList.get(i).getCarManager());
                institution.setPhone(institutionImportParamsList.get(i).getPhone());
                institution.setOperator(operator);
                institutionRespository.save(institution);
            }else{
                institution_exits.setIsDelete(0);
                institution_exits.setGmt_modify(formatTime());
                institution_exits.setCarManager(institutionImportParamsList.get(i).getCarManager());
                institution_exits.setPhone(institutionImportParamsList.get(i).getPhone());
                institution_exits.setOperator(operator);
                institutionRespository.save(institution_exits);
            }
        }
    }
}

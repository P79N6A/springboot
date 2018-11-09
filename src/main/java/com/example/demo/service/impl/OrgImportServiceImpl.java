package com.example.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.demo.common.RequestUtil;
import com.example.demo.common.ResultBean;
import com.example.demo.common.zcy.Config;
import com.example.demo.dao.InstitutionRespository;
import com.example.demo.model.car.Institution;
import com.example.demo.model.car.InstitutionImportParams;
import com.example.demo.model.car.InstitutionQueryParams;
import com.example.demo.model.car.orgDelResp.OrgDelResp;
import com.example.demo.model.car.orgImportResp.OrgImportResp;
import com.example.demo.model.car.orgQueryResp.InstitutionRespModel;
import com.example.demo.service.OrgImportService;
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
public class OrgImportServiceImpl implements OrgImportService {
    @Autowired
    private InstitutionRespository institutionRespository;


    @Override
    public ResultBean importOrg(List<InstitutionImportParams> institutionImportParamsList) {
       //s ResultBean resultBean=new ResultBean();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("importInfo",institutionImportParamsList);
        String result=RequestUtil.execRequest(jsonObject.toString(), Config.ORG_INIT_IMPORT);
        if(RequestUtil.isSuccess(result)){
            OrgImportResp orgImportResp=JSON.parseObject(RequestUtil.getPosition(result),OrgImportResp.class);
            log.info(JSON.toJSONString(orgImportResp));
            if(orgImportResp.isSuccess()){
                if(orgImportResp.getResult().getFailed()==0&&orgImportResp.getResult().getSucceed()>0){
                    saveStitution(institutionImportParamsList);
                    return  ResultBean.isSuccess(orgImportResp);
                }else if(orgImportResp.getResult().getFailed()>0&&orgImportResp.getResult().getSucceed()>0){
                    for (int i = orgImportResp.getResult().getFails().size()-1; i>=0 ; i--) {
                        institutionImportParamsList.remove(Integer.valueOf(orgImportResp.getResult().getFails().get(i).getLine())-2);
                    }
                    saveStitution(institutionImportParamsList);
                    return  ResultBean.isSuccess(orgImportResp);
                }else{
                    return  ResultBean.isSuccess(orgImportResp);
                }
            }else{
                return  ResultBean.isFailure("导入机构参数为空");
            }
        }
        return ResultBean.isThrows("请求链路异常,请稍后再试");
    }

    @Override
    public ResultBean queryOrg(InstitutionQueryParams institutionQueryParams) {
        String result=RequestUtil.execRequest(JSON.toJSONString(institutionQueryParams),Config.ORG_INIT_QUERY);
        log.info(result);
        if(RequestUtil.isSuccess(result)){
            InstitutionRespModel institutionRespModel=JSON.parseObject(RequestUtil.getPosition(result),InstitutionRespModel.class);
            if(institutionRespModel.isSuccess()){
                if(institutionRespModel.getResult().isEmpty()||institutionRespModel.getResult().getTotal()==0){
                    return ResultBean.isFailure("查询无结果");
                }
                    return ResultBean.isSuccess(institutionRespModel);
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
                OrgDelResp orgDelResp=JSON.parseObject(RequestUtil.getPosition(result),OrgDelResp.class);
                if(orgDelResp.isSuccess()&&orgDelResp.isResult()){
                    Institution institution_exit=institutionRespository.findByOrgCodeAndAndOrgName(orgCode,orgName);
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
                return ResultBean.isFailure(orgDelResp.getCode()+","+orgDelResp.getMessage());
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
    public boolean removeOrgRecord(String id) {
        if(StringUtils.isNotBlank(id)){
            institutionRespository.delete(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Institution> findByStatus(int status) {
        return institutionRespository.findByIsDelete(status);
    }

    private String formatTime(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    private void saveStitution(List<InstitutionImportParams> institutionImportParamsList){
        Institution institution;
        Institution institution_exits;
        for (int i = 0; i <institutionImportParamsList.size() ; i++) {
            institution_exits=institutionRespository.findByOrgCodeAndAndOrgName(institutionImportParamsList.get(i).getOrgCode(),institutionImportParamsList.get(i).getOrgName());
            if(institution_exits==null) {
                institution = new Institution();
                institution.setGmt_create(formatTime());
                institution.setOrgCode(institutionImportParamsList.get(i).getOrgCode());
                institution.setOrgName(institutionImportParamsList.get(i).getOrgName());
                institution.setCarManager(institutionImportParamsList.get(i).getCarManager());
                institution.setPhone(institutionImportParamsList.get(i).getPhone());
                institutionRespository.save(institution);
            }else{
                institution_exits.setIsDelete(0);
                institution_exits.setGmt_modify(formatTime());
                institution_exits.setCarManager(institutionImportParamsList.get(i).getCarManager());
                institution_exits.setPhone(institutionImportParamsList.get(i).getPhone());
                institutionRespository.save(institution_exits);
            }
        }
    }
}

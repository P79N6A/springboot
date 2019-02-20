package com.example.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.demo.common.RequestUtil;
import com.example.demo.common.ResultBean;
import com.example.demo.common.zcy.Config;
import com.example.demo.dao.VehicleRespository;
import com.example.demo.model.car.Vehicle;
import com.example.demo.model.car.VehicleImportParams;
import com.example.demo.model.car.delResp.VehicleDelResponse;
import com.example.demo.model.car.importResp.ImportResponse;
import com.example.demo.model.car.vehicleQueryResp.VehicleQueryResponse;
import com.example.demo.service.VehicleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class VehicleServiceImpl implements VehicleService {
    @Autowired
    private VehicleRespository vehicleRespository;
    @Override
    public ResultBean importVehicle(List<VehicleImportParams> vehicleImportParamsList, String operator) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("importInfo",vehicleImportParamsList);
        String result=RequestUtil.execRequest(jsonObject.toString(), Config.VEHICLE_INIT_IMPORT);
        log.info("exe resp:"+result);
        if(RequestUtil.isSuccess(result)){
            ImportResponse importResponse =JSON.parseObject(RequestUtil.getPosition(result),ImportResponse.class);
            log.info(JSON.toJSONString(importResponse));
            if(importResponse.isSuccess()){
                if(importResponse.getResult().getFailed()==0&& importResponse.getResult().getSucceed()>0){
                    saveVehicle(vehicleImportParamsList,operator);
                    return  ResultBean.isSuccess(importResponse);
                }else if(importResponse.getResult().getFailed()>0&& importResponse.getResult().getSucceed()>0){
                    for (int i = importResponse.getResult().getFails().size()-1; i>=0 ; i--) {
                        vehicleImportParamsList.remove(Integer.valueOf(importResponse.getResult().getFails().get(i).getLine())-2);
                    }
                    saveVehicle(vehicleImportParamsList,operator);
                    return  ResultBean.isSuccess(importResponse);
                }else{
                    return  ResultBean.isSuccess(importResponse);
                }
            }else{
                return  ResultBean.isFailure("参数校验异常,第2行组织机构代码错误,平台不存在!");
            }
        }
        return ResultBean.isThrows("请求链路异常,请稍后再试");
    }

    @Override
    public ResultBean queryVehicle(String plateNo,int page,int rows) {
        if(StringUtils.isNotBlank(plateNo)){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("plateNo",plateNo);
            jsonObject.put("labelType",1);
            jsonObject.put("pageNo",page);
            jsonObject.put("pageSize",rows);
            log.info(jsonObject.toString());
            String result=RequestUtil.execRequest(jsonObject.toString(),Config.VEHICLE_INIT_QUERY);
            if(RequestUtil.isSuccess(result)){
                VehicleQueryResponse vehicleQueryResponse =JSON.parseObject(RequestUtil.getPosition(result),VehicleQueryResponse.class);
                if(vehicleQueryResponse.isSuccess()){
                    if(vehicleQueryResponse.getResult().isEmpty()&& vehicleQueryResponse.getResult().getTotal()==0){
                        return ResultBean.isFailure("车辆不存在，请检查车牌号是否正确!");
                    }else{
                        return ResultBean.isSuccess(vehicleQueryResponse);
                    }
                }else{
                    return ResultBean.isFailure("查询数据失败,请检查入参!");
                }
            }else{
                return ResultBean.isThrows("请求链路异常,请稍后再试");
            }
        }
        return ResultBean.isFailure("plate number is null");
    }

    @Override
    public ResultBean delVehicle(VehicleImportParams vehicleImportParams, String stockId, String operator) {
        if(StringUtils.isNotBlank(stockId)){
            List<String> stock=new ArrayList<>();
            stock.add(stockId);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("stockIds",stock);
            String result=RequestUtil.execRequest(jsonObject.toString(),Config.VEHICLE_INIT_DELETE);
            log.info(result);
            if(RequestUtil.isSuccess(result)){
                VehicleDelResponse vehicleDelResponse =JSON.parseObject(RequestUtil.getPosition(result),VehicleDelResponse.class);
                if(vehicleDelResponse.isSuccess()&& vehicleDelResponse.isResult()){
                    Vehicle vehicle_exit=vehicleRespository.findByOrgNameAndPlateNo(vehicleImportParams.getOrgName(),vehicleImportParams.getPlateNo());
                    if (vehicle_exit==null){
                        Vehicle vehicle=new Vehicle();
                        vehicle.setGmt_create(formatTime());
                        vehicle.setGmt_modify(formatTime());
                        vehicle.setIsDelete(1);
                        vehicle.setOrgCode(vehicleImportParams.getOrgCode());
                        vehicle.setOrgName(vehicleImportParams.getOrgName());
                        vehicle.setPlateNo(vehicleImportParams.getPlateNo());
                        vehicle.setCarClassName(vehicleImportParams.getCarClassName());
                        vehicle.setCarUsageName(vehicleImportParams.getCarUsageName());
                        vehicle.setPrice(RequestUtil.pointToMilions(vehicleImportParams.getPrice()));
                        vehicle.setRegisterDate(RequestUtil.secondToDate(vehicleImportParams.getRegisterDate(),"yyyy-MM-dd"));
                        vehicle.setBrandName(vehicleImportParams.getBrandName());
                        vehicle.setVolumeOut(vehicleImportParams.getVolumeOut());
                        vehicle.setVinNo(vehicleImportParams.getVinNo());
                        vehicle.setEngineNo(vehicleImportParams.getEngineNo());
                        vehicle.setIsImport(formatIsMadeIn(vehicleImportParams.getIsImport()));
                        vehicle.setOperator(operator);
                        vehicleRespository.save(vehicle);
                    }else{
                        vehicle_exit.setOrgCode(vehicleImportParams.getOrgCode());
//                        vehicle_exit.setOrgName(vehicleImportParams.getOrgName());
//                        vehicle_exit.setPlateNo(vehicleImportParams.getPlateNo());
                        vehicle_exit.setCarClassName(vehicleImportParams.getCarClassName());
                        vehicle_exit.setCarUsageName(vehicleImportParams.getCarUsageName());
                        vehicle_exit.setPrice(RequestUtil.pointToMilions(vehicleImportParams.getPrice()));
                        vehicle_exit.setRegisterDate(RequestUtil.secondToDate(vehicleImportParams.getRegisterDate(),"yyyy-MM-dd"));
                        vehicle_exit.setBrandName(vehicleImportParams.getBrandName());
                        vehicle_exit.setVolumeOut(vehicleImportParams.getVolumeOut());
                        vehicle_exit.setVinNo(vehicleImportParams.getVinNo());
                        vehicle_exit.setEngineNo(vehicleImportParams.getEngineNo());
                        vehicle_exit.setIsImport(formatIsMadeIn(vehicleImportParams.getIsImport()));
                        vehicle_exit.setGmt_modify(formatTime());
                        vehicle_exit.setIsDelete(1);
                        vehicle_exit.setOperator(operator);
                        vehicleRespository.save(vehicle_exit);
                    }
                    return ResultBean.isSuccess("删除成功");
                }
                return ResultBean.isFailure(vehicleDelResponse.getMessage());
            }
            return ResultBean.isThrows("请求链路异常");
        }
        return ResultBean.isFailure("车辆id不能为空");
    }

    @Override
    public List<Vehicle> findAll() {
//        List<Vehicle> list=vehicleRespository.findAll(new Sort(new Sort.Order(Sort.Direction.DESC,"gmt_create")));
//        Vehicle vehicles;
//        for (int i = 0; i <list.size() ; i++) {
//            vehicles=vehicleRespository.findOne(list.get(i).getId());
//            vehicles.setGmt_create(null);
//            vehicles.setGmt_modify(null);
//            vehicleRespository.save(vehicles);
//        }
        return vehicleRespository.findAll(new Sort(new Sort.Order(Sort.Direction.DESC,"gmt_create")));
    }

    @Override
    public Vehicle findById(String id) {
        if(StringUtils.isNotBlank(id)){
            return vehicleRespository.findOne(id);
        }
        return null;
    }

    @Override
    public boolean removeVehicleBackUp(String id) {
        if(StringUtils.isNotBlank(id)){
            vehicleRespository.delete(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Vehicle> findByStatus(int status) {
        return vehicleRespository.findByIsDelete(status,new Sort(new Sort.Order(Sort.Direction.DESC,"gmt_create")));
    }

    @Override
    public List<Vehicle> findByUser(int status, String operator) {
        return vehicleRespository.findByIsDeleteAndOperator(status,operator,new Sort(new Sort.Order(Sort.Direction.DESC,"gmt_create")));
    }

    @Override
    public List<Vehicle> findByIsDeleteAndOrgNameAndPlateNoAndOperator(int status, String orgName, String plateNo, String operator) {
        boolean orgNames=StringUtils.isNotBlank(orgName);
        boolean plateNos=StringUtils.isNotBlank(plateNo);
        boolean operators=StringUtils.isNotBlank(operator);
        if(orgNames&&plateNos&&operators){
            return  vehicleRespository.findByIsDeleteAndOrgNameAndPlateNoAndOperator(status,orgName,plateNo,operator,new Sort(new Sort.Order(Sort.Direction.DESC,"gmt_create")));
        }else if(!orgNames&&plateNos&&operators){
            return vehicleRespository.findByIsDeleteAndOperatorAndPlateNo(status,operator,plateNo,new Sort(new Sort.Order(Sort.Direction.DESC,"gmt_create")));
        }else if(orgNames&&!plateNos&&operators){
            return vehicleRespository.findByIsDeleteAndOrgNameAndOperator(status,orgName,operator,new Sort(new Sort.Order(Sort.Direction.DESC,"gmt_create")));
        }else if(orgNames&&plateNos&&!operators){
            return vehicleRespository.findByIsDeleteAndOrgNameAndPlateNo(status,orgName,operator,new Sort(new Sort.Order(Sort.Direction.DESC,"gmt_create")));
        }else if(orgNames&&!plateNos&&!operators){
            return  vehicleRespository.findByIsDeleteAndOrgName(status,orgName,new Sort(new Sort.Order(Sort.Direction.DESC,"gmt_create")));
        }else if(!orgNames&&plateNos&&!operators){
            return vehicleRespository.findByIsDeleteAndPlateNo(status,plateNo,new Sort(new Sort.Order(Sort.Direction.DESC,"gmt_create")));
        }else if(!orgNames&&!plateNos&&operators){
            return  vehicleRespository.findByIsDeleteAndOperator(status,operator,new Sort(new Sort.Order(Sort.Direction.DESC,"gmt_create")));
        }else{
            return  vehicleRespository.findByIsDelete(status,new Sort(new Sort.Order(Sort.Direction.DESC,"gmt_create")));
        }
    }

    private void saveVehicle(List<VehicleImportParams> vehicleImportParamsList,String operator){
       Vehicle vehicle;
       Vehicle vehicle_exit;
        for (int i = 0; i <vehicleImportParamsList.size() ; i++) {
            vehicle_exit=vehicleRespository.findByOrgNameAndPlateNo(vehicleImportParamsList.get(i).getOrgName(),vehicleImportParamsList.get(i).getPlateNo());
            if(vehicle_exit==null) {
                vehicle=new Vehicle();
                vehicle.setGmt_create(formatTime());
                vehicle.setOrgCode(vehicleImportParamsList.get(i).getOrgCode());
                vehicle.setOrgName(vehicleImportParamsList.get(i).getOrgName());
                vehicle.setPlateNo(vehicleImportParamsList.get(i).getPlateNo());
                vehicle.setCarClassName(vehicleImportParamsList.get(i).getCarClassName());
                vehicle.setCarUsageName(vehicleImportParamsList.get(i).getCarUsageName());
                vehicle.setPrice(vehicleImportParamsList.get(i).getPrice());
                vehicle.setRegisterDate(vehicleImportParamsList.get(i).getRegisterDate());
                vehicle.setBrandName(vehicleImportParamsList.get(i).getBrandName());
                vehicle.setVolumeOut(vehicleImportParamsList.get(i).getVolumeOut());
                vehicle.setVinNo(vehicleImportParamsList.get(i).getVinNo());
                vehicle.setEngineNo(vehicleImportParamsList.get(i).getEngineNo());
                vehicle.setIsImport(vehicleImportParamsList.get(i).getIsImport());
                vehicle.setOperator(operator);
                vehicleRespository.save(vehicle);
            }else{
                vehicle_exit.setOrgCode(vehicleImportParamsList.get(i).getOrgCode());
//                vehicle_exit.setOrgName(vehicleImportParamsList.get(i).getOrgName());
//                vehicle_exit.setPlateNo(vehicleImportParamsList.get(i).getPlateNo());
                vehicle_exit.setCarClassName(vehicleImportParamsList.get(i).getCarClassName());
                vehicle_exit.setCarUsageName(vehicleImportParamsList.get(i).getCarUsageName());
                vehicle_exit.setPrice(vehicleImportParamsList.get(i).getPrice());
                vehicle_exit.setRegisterDate(vehicleImportParamsList.get(i).getRegisterDate());
                vehicle_exit.setBrandName(vehicleImportParamsList.get(i).getBrandName());
                vehicle_exit.setVolumeOut(vehicleImportParamsList.get(i).getVolumeOut());
                vehicle_exit.setVinNo(vehicleImportParamsList.get(i).getVinNo());
                vehicle_exit.setEngineNo(vehicleImportParamsList.get(i).getEngineNo());
                vehicle_exit.setIsImport(vehicleImportParamsList.get(i).getIsImport());
                vehicle_exit.setIsDelete(0);
                vehicle_exit.setGmt_modify(formatTime());
                vehicle_exit.setOperator(operator);
                vehicleRespository.save(vehicle_exit);
            }
        }
    }
    private String formatTime(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
    private String formatIsMadeIn(String in){
        if("2".equals(in)){
            return "进口";
        }
        return "国产";
    }

}

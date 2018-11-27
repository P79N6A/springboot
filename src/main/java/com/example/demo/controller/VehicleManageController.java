package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.common.RequestUtil;
import com.example.demo.common.ResultBean;
import com.example.demo.model.car.Vehicle;
import com.example.demo.model.car.VehicleImportParams;
import com.example.demo.service.VehicleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "vehicle")
@Slf4j
public class VehicleManageController {
    @Autowired
    private VehicleService vehicleService;
    @PostMapping(value = "init")
    public ResultBean importVehicle(@RequestParam("file") MultipartFile file,@RequestParam(value = "operator")String operator) {
        if(file.isEmpty()){
            log.info("文件不能为空");
            return ResultBean.isThrows("文件不能为空");
        }
        try {
            List<List<Object>> list = RequestUtil.getBankListByExcel(file.getInputStream(),file.getOriginalFilename());
            List<VehicleImportParams> vehicleImportParamsList = new ArrayList<>();
            VehicleImportParams vehicleImportParams;
            for (int i = 0; i < list.size(); i++) {
                vehicleImportParams = new VehicleImportParams();
                if(StringUtils.isNotBlank(list.get(i).get(0).toString())&&StringUtils.isNotBlank(list.get(i).get(1).toString())){
                    vehicleImportParams.setLineNum(String.valueOf(i+1));
                    vehicleImportParams.setOrgCode(list.get(i).get(0).toString());
                    vehicleImportParams.setOrgName(list.get(i).get(1).toString());
                    vehicleImportParams.setPlateNo(list.get(i).get(2).toString());
                    vehicleImportParams.setCarClassName(list.get(i).get(3).toString());
                    vehicleImportParams.setCarUsageName(list.get(i).get(4).toString());
                    vehicleImportParams.setPrice(list.get(i).get(5).toString());
                    vehicleImportParams.setRegisterDate(list.get(i).get(6).toString());
                    vehicleImportParams.setBrandName(list.get(i).get(7).toString());
                    vehicleImportParams.setVolumeOut(list.get(i).get(8).toString());
                    vehicleImportParams.setVinNo(list.get(i).get(9).toString());
                    vehicleImportParams.setEngineNo(list.get(i).get(10).toString());
                    vehicleImportParams.setIsImport(list.get(i).get(11).toString());
                   vehicleImportParamsList.add(vehicleImportParams);
                }
            }
            log.info(JSON.toJSONString(vehicleImportParamsList));
            return vehicleService.importVehicle(vehicleImportParamsList,operator);
        } catch (Exception e) {
            return ResultBean.isThrows("文件读取异常"+e.toString());
        }
    }
    @GetMapping(value = "findAll")
    public ResultBean<List<Vehicle>> findAll(){
        return new ResultBean<>(vehicleService.findAll());
    }
    @PostMapping(value = "query")
    public ResultBean queryVehicle(@RequestParam(value = "plateNo") String plateNo, @RequestParam(value = "page",defaultValue ="1") Integer page, @RequestParam(value = "rows",defaultValue = "10")Integer rows){
        return new ResultBean<>(vehicleService.queryVehicle(plateNo,page,rows));
    }
    @PostMapping(value = "del")
    public ResultBean delVehicle(VehicleImportParams vehicleImportParams,@RequestParam(value = "stockId") String stockId,@RequestParam(value = "operator")String operator){
        return new ResultBean<>(vehicleService.delVehicle(vehicleImportParams,stockId,operator));
    }
    @PostMapping(value = "remove")
    public ResultBean<Boolean> removeVehicle(@RequestParam(value = "id") String id){
        return new ResultBean<>(vehicleService.removeVehicleBackUp(id));
    }
    @GetMapping(value = "queryByStatus")
    public ResultBean<List<Vehicle>> queryByStatus(@RequestParam(value = "status") Integer status){
        return new ResultBean<>(vehicleService.findByStatus(status));
    }
    @GetMapping(value = "queryByStatusAndUser")
    public ResultBean<List<Vehicle>> queryByStatusAndUser(@RequestParam(value = "status") Integer status,@RequestParam(value = "operator") String operator){
        return new ResultBean<>(vehicleService.findByUser(status,operator));
    }

    @GetMapping(value = "queryByIsDeleteAndOrgNameAndPlateNoAndOperator")
    public ResultBean<List<Vehicle>> queryByIsDeleteAndOrgNameAndPlateNoAndOperator(@RequestParam(value = "status") Integer status,@RequestParam(value = "operator") String operator,@RequestParam(value = "orgName") String orgName,@RequestParam(value = "plateNo") String plateNo){
        return new ResultBean<>(vehicleService.findByIsDeleteAndOrgNameAndPlateNoAndOperator(status,orgName,plateNo,operator));
    }

    @PostMapping(value = "reset")
    public ResultBean reset(@RequestParam(value = "id")String id,@RequestParam(value = "operator") String operator){
        Vehicle vehicle=vehicleService.findById(id);
        if (vehicle==null){
            return ResultBean.isThrows("车辆备份数据读取异常:"+id);
        }
        List<VehicleImportParams> vehicleImportParamsList = new ArrayList<>();
        VehicleImportParams vehicleImportParams=new VehicleImportParams();
        vehicleImportParams.setLineNum("1");
        vehicleImportParams.setOrgCode(vehicle.getOrgCode());
        vehicleImportParams.setOrgName(vehicle.getOrgName());
        vehicleImportParams.setPlateNo(vehicle.getPlateNo());
        vehicleImportParams.setCarClassName(vehicle.getCarClassName());
        vehicleImportParams.setCarUsageName(vehicle.getCarUsageName());
        vehicleImportParams.setPrice(vehicle.getPrice());
        vehicleImportParams.setRegisterDate(vehicle.getRegisterDate());
        vehicleImportParams.setBrandName(vehicle.getBrandName());
        vehicleImportParams.setVolumeOut(vehicle.getVolumeOut());
        vehicleImportParams.setVinNo(vehicle.getVinNo());
        vehicleImportParams.setEngineNo(vehicle.getEngineNo());
        vehicleImportParams.setIsImport(vehicle.getIsImport());
        vehicleImportParamsList.add(vehicleImportParams);
        return vehicleService.importVehicle(vehicleImportParamsList,operator);
    }
}

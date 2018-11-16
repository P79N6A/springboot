package com.example.demo.service;

import com.example.demo.common.ResultBean;
import com.example.demo.model.car.Vehicle;
import com.example.demo.model.car.VehicleImportParams;

import java.util.List;

public interface VehicleService {
    ResultBean importVehicle(List<VehicleImportParams> vehicleImportParamsList, String operator);
    ResultBean queryVehicle(String plateNo);
    ResultBean delVehicle(VehicleImportParams vehicleImportParams,String stockId,String operator);
    List<Vehicle> findAll();
    Vehicle findById(String id);
    boolean removeVehicleBackUp(String id);
    List<Vehicle> findByStatus(int status);
    List<Vehicle> findByUser(int status, String operator);
}

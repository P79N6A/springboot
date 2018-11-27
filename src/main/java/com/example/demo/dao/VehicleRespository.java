package com.example.demo.dao;

import com.example.demo.model.car.Vehicle;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRespository extends MongoRepository<Vehicle,String> {
    Vehicle findByOrgNameAndPlateNo(String orgName,String plateNo);
    List<Vehicle> findByIsDelete(int isDelete,Sort sort);
    List<Vehicle> findByIsDeleteAndOperator(int isDelete,String operator,Sort sort);
    List<Vehicle> findByIsDeleteAndOrgName(int isDelete,String orgName,Sort sort);
    List<Vehicle> findByIsDeleteAndPlateNo(int isDelete,String plateNo,Sort sort);
    List<Vehicle> findByIsDeleteAndOrgNameAndOperator(int isDelete,String orgName,String operator,Sort sort);
    List<Vehicle> findByIsDeleteAndOrgNameAndPlateNo(int isDelete,String orgName,String plateNo,Sort sort);
    List<Vehicle> findByIsDeleteAndOperatorAndPlateNo(int isDelete,String operator,String plateNo,Sort sort);
    List<Vehicle> findByIsDeleteAndOrgNameAndPlateNoAndOperator(int isDelete,String orgName,String plateNo,String operator,Sort sort);
}

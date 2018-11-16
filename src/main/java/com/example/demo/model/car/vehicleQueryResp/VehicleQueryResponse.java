package com.example.demo.model.car.vehicleQueryResp;

import lombok.Data;

/***
 * 车辆查询 返回实体
 */
@Data
public class VehicleQueryResponse {
    private boolean success;
    private String code;
    private String message;
    private Result result;
}

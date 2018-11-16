package com.example.demo.model.car.delResp;

import lombok.Data;

@Data
public class VehicleDelResponse {
    private boolean success;
    private String code;
    private String message;
    private boolean result;
}

package com.example.demo.model.car.orgDelResp;

import lombok.Data;

@Data
public class OrgDelResp {
    private boolean success;
    private String code;
    private String message;
    private boolean result;
}

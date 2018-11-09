package com.example.demo.model.car.orgImportResp;


import lombok.Data;

@Data
public class OrgImportResp {
    private boolean success;
    private String code;
    private String message;
    private Result result;
}

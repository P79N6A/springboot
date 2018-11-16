package com.example.demo.model.car.importResp;


import lombok.Data;

@Data
public class ImportResponse {
    private boolean success;
    private String code;
    private String message;
    private Result result;
}

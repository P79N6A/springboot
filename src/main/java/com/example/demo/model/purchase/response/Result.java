package com.example.demo.model.purchase.response;

import lombok.Data;

@Data
public class Result {
    private boolean success;
    private String error;
    private boolean result;
}

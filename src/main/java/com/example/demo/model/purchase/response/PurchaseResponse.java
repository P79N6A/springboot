package com.example.demo.model.purchase.response;

import lombok.Data;

@Data
public class PurchaseResponse {
    private boolean success;
    private String data_response;
    private Error_response error_response;

}

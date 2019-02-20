package com.example.demo.model.purchase.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAuthResponse {
    private boolean success;
    private String code;
    private String msg;
    private Data data;
}

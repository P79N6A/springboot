package com.example.demo.common.zcy;

import lombok.Data;

import java.util.Map;

@Data
public class Params {
    private String uri;
    private Map<String, Object> bodyMap;
    private Map<String, String> headers;
}

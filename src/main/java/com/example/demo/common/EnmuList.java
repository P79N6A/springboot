package com.example.demo.common;

import lombok.Data;

import java.util.List;
@Data
public class EnmuList<T> {
    private String id;
    private  String code;
    private String name;
    private String partner;
    private T children;
}

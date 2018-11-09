package com.example.demo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Purchase {
    @Id
    private String id;
    private String gmt_create;
    private String gmt_modify;
    private String owner;
    private String owner_name;
    private int is_delete;
    private String execute_method;
    private String relation;
    private String relation_name;
    private String purchase_id;
    private String biz_content;
    private String execute;
    private String execute_name;
    private String request_data;
    private String response_data;
    private String execute_result;
}

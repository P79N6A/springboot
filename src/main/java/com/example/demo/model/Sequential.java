package com.example.demo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Sequential {
    @Id
    private String id;
    private String gmt_create;
    private String gmt_modify;
    private int seque;
    private int type;
}

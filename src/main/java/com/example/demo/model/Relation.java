package com.example.demo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Relation {
    @Id
    private String id;
    private String gmt_create;
    private String gmt_modify;
    private int relation;
    //private int relation_type;
    private String relation_code;
    private String relation_name;
}

package com.example.demo.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class CapitalType {
    private String amount;
    private  String capitalCode;
    private String capitalName;
}

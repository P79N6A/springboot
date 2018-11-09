package com.example.demo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
public class DutySheet {
    @Id
    private String id;
    private String gmt_create;
    private String gmt_modify;
    private String month;
    private String sheetName;
    private List<WorkDuty> duty;
}

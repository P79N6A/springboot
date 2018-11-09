package com.example.demo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class PurchaseInstitution {
    @Id
    private String id;
    private String gmt_create;
    private String gmt_modify;
    /**操作人**/
    private String operator_id;
    private String operator_name;
    /**业务参数**/
    private int orgid;
    private String distid;
    private String name;
    private String short_name;
    private String org_code;
    private String budget_code;
    private int status;

}

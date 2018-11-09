package com.example.demo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class FunctionalSubject {
    /***预算科目 经济科目*/
    @Id
    private String id;
    private String gmt_create;
    private String gmt_modify;
    /**操作人**/
    private String operator_id;
    private String operator_name;
    /**partner_code_id**/
    private String partner;
    private String code;
    private String name;
    /**业务类型; 1 预算科目 2经济科目**/
    private int type;
    private String type_name;
    /**有无子集 0 无 1:有 **/
    private int flag;
    private String flag_name;
}

package com.example.demo.model.car;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/***
 * 机构信息导入、删除记录
 */
@Data
@Document
public class Institution {
    @Id
    private String id;
    private String gmt_create;
    private String gmt_modify;
    /**0 正常 1已删除**/
    private int isDelete;
    private String orgCode;
    private String orgName;
    /**联系人**/
    private String carManager;
    private String  phone;
    private String operator;


}

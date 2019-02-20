package com.example.demo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Menu {
    @Id
    private String id;
    private String gmt_create;
    private String gmt_modify;
    private String menu_name;
    private String nav_name;
    private String icon;
    private String menu_url;
    private int status;
    private String status_name;
    /**0管理员 1 运营 2,"审核" 3,"通用"**/
    private int role;
    private int sort_code;
    private String  role_name;
    private String operator_id;
    private String operator_name;
    /**权限码 100X:通用权限，默认分配 200X: 需申请权限 300X:专属权限**/
    private int roleCode;
    /**归属类型:采购计划1 项目采购2 车辆控购3 常用工具4 用户管理5 其他设置6**/
    private int type;
}

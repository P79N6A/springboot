package com.example.demo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Account {
    @Id
    private String id;
    private String gmt_create;
    private String gmt_modify;
    private String nick_name;
    private String phone;
    private String account;
    private String password;
    private String group;
    private int is_delete;
    private int status;
    private String status_name;
    /***
     *  map.put(0,"管理员");
     *  map.put(1,"运营");
     *   map.put(2,"审核");
     *    map.put(3,"用户");
     */
    private int role;
    private String role_name;
}

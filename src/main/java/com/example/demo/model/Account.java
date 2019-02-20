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
    /**0 不参与 1 参与 default 0**/
    private int meeting;
    /**检阅：0 未赴 1 已赴 default 0**/
    private int review;
    /**检阅：0 未轮值 1 已轮值 default 0**/
    private int lock;
    /**会议序列**/
    private int sortLot;
    /**菜单权限列表**/
    private String roleLot;
    /**auth v2 add columns **/
    private String employeeId;
    private String realName;
    private String avatar;
    private String mail;
    private String department;

}

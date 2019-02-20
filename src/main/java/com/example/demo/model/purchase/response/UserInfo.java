package com.example.demo.model.purchase.response;

import lombok.Data;

import java.util.List;

@Data
public class UserInfo {
    private int id;
    private String createAt;
    private String updateAt;
    private String userName;
    private String nickName;
    private String employeeId;
    private String realName;
    private String avatar;
    private String mail;
    private String mobile;
    private List<Departments> departments;
}

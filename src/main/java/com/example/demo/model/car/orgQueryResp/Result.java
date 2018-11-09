package com.example.demo.model.car.orgQueryResp;

import lombok.Data;

import java.util.List;

/***
 * 机构信息详情
 */
@Data
public class Result {
    private boolean empty;
    private int total;
    private List<com.example.demo.model.car.orgQueryResp.Data> data;


}

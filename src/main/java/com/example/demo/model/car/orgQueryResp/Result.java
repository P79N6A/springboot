package com.example.demo.model.car.orgQueryResp;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/***
 * 机构信息详情
 */
@Setter
@Getter
public class Result {
    private boolean empty;
    private int total;
    private List<Data> data;


}

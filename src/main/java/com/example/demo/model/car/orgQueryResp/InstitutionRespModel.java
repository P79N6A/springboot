package com.example.demo.model.car.orgQueryResp;

import lombok.Data;

/***
 * 机构信息返回映射实体
 */
@Data
public class InstitutionRespModel {
    private boolean success;
    private String code;
    private String message;
    private Result result;


}

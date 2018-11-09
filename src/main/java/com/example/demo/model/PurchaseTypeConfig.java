package com.example.demo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/***
 * 采购计划业务配置-采购类型、方式、资金、支付
 */
@Document
@Data
public class PurchaseTypeConfig {
    @Id
    private String id;
    private String gmt_create;
    private String gmt_modify;
    /**操作人**/
    private String operator_id;
    private String operator_name;
    /**
     * 业务类型 1采购类型  2采购方式 3资金类型 4 付款方式
     */
    private int type;
    private String type_name;
    private String year;
    private String code;
    private String name;
    private int status;
    private String status_name;
}

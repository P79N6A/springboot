package com.example.demo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Gpcatlog {
    /***采购目录*/
    @Id
    private String id;
    private String gmt_create;
    private String gmt_modify;
    /**操作人**/
    private String operator_id;
    private String operator_name;
    /**node_id**/
    private String node;
    /**partner_node_id**/
    private String partner;
    private String code;
    private String level;
    private String name;
    /**有无子集 0 无 1:有 **/
    private int flag;
    private String flag_name;
}

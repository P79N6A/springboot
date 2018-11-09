package com.example.demo.common.zcy;

import lombok.Data;

@Data
public class RelationInfo {

    /**操作人id**/
    private String operator_id;
    /**请求接口类型**/
    private int request_type;
    private String user_id;
    private String local_relation_id;
    public String relation_code;
    public int relation_type;
   // public String relation_name;
    public  String relation_id;
    public String  purchase_id;
    public String amount;
    public int quantity;
}

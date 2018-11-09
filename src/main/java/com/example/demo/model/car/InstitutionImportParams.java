package com.example.demo.model.car;

import lombok.Data;

/***
 * 外部文件导入机构信息
 */
@Data
public class InstitutionImportParams {
    /** 行号**/
    private String lineNum;
    private String orgCode;
    private String orgName;
    /**联系人**/
    private String carManager;
    private String  phone;



}

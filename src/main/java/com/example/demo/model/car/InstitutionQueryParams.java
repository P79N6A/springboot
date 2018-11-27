package com.example.demo.model.car;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/***
 * 查询机构信息入参
 */
@Data
public class InstitutionQueryParams {
    private String districtCode;
    private String orgCode;
    private String orgName;
    private String orgRealName;
    private int pageNo;
    private int pageSize;


}

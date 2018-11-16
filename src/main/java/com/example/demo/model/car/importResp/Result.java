package com.example.demo.model.car.importResp;

import lombok.Data;

import java.util.List;

/***
 * 机构导入详情
 */
@Data
public class Result {
    private boolean isRunning;
    private int total;
    private int skipped;
    private int succeed;
    private int failed;
    private List<Fails> fails;
    private List<Succeeds> succeeds;
    private String quotaCreateUpdateNum;
    private String inQuotaLine;
    private String backup;


}

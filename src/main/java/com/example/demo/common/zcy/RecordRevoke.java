package com.example.demo.common.zcy;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RecordRevoke {
    private int revokeId;
    private String status;
    private String remark;
    private String timeUpdate;
}

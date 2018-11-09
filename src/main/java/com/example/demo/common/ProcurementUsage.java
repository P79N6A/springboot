package com.example.demo.common;

import lombok.Data;

@Data
public class ProcurementUsage {

    private String orgId;
    private String procurementTypeId;
    private String procurementMethodId;
    private String paymentMethodId;
    private String capitalTypeId;
    private String functionalSubjectId;
    private String economicSubjectId;
    private String gpcatalogId;

}

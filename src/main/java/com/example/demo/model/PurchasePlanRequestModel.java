package com.example.demo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
public class PurchasePlanRequestModel {
    @Id
    private String id;
    private String gmt_create;
    private String gmt_modify;
    /**请求类型 1草创 2创建并提交 3审核前编辑更新 4修改并重新提交**/
    private int type;
    /**操作人**/
    private String operator_id;
    private String operator_name;
    /**审核状态 0草稿 10待审核 20通过 30驳回  1 废弃**/
    private int audit;
    private String audit_status;
    private String audit_remark;
    /**执行状态 0暂无 10待执行 20成功 30失败**/
    private int execute;
    private String execute_status;
    private String execute_req;
    private String execute_resp;
    /**文件id list**/
    private String fileId;

    private String districtCode;
    private int applyYear;
    private String financialPurchasePlanId;
    private String financialBudgetId;
    private int financialBudgetIndexId;
    private String organBudgetCode;
    private String organName;
    private String organSuperiorCode;
    private String organSuperiorName;
    private String gpcatalogCode;
    private String gpcatalogName;
    private String functionalSubjectCode;
    private String functionalSubjectName;
    private String economicSubjectCode;
    private String economicSubjectName;
    private String financialProjectNo;
    private String projectName;
    private String procurementContent;
    private String technicalParameter;
    private String measurementUnit;
    private String paymentMethodCode;
    private String paymentMethodName;
    private String unitPrice;
    private int quantity;
    private String procurementMethodCode;
    private String procurementMethodName;
    private String procurementTypeCode;
    private String procurementTypeName;
    private String purchaseplanDOCNO;
    private int implementStart;
    private String contactPerson;
    private String contactTelephone;
    private int timeReleased;
    private String hasFinished;
    private String remark;
    private int isTemporary;
    private List capitalTypeList;
    private int capitalTypeCount;
    private String director;
    private String directorTelephone;
    private String targetOrganBudgetCode;
    private String targetOrganName;
    private int endDate;
    private int isOverSea;
    private int isUrgent;
    private int recordType;
    private int source;
    private String informalFinancialPlanId;
    private int isGovernmentPurchaseService;

}

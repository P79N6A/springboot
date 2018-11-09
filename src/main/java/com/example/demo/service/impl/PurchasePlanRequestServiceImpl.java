package com.example.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.demo.common.ApiResult;
import com.example.demo.common.Initialization;
import com.example.demo.common.ProcurementUsage;
import com.example.demo.common.zcy.*;
import com.example.demo.dao.*;
import com.example.demo.model.*;
import com.example.demo.service.PurchasePlanRequestService;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PurchasePlanRequestServiceImpl implements PurchasePlanRequestService {
    @Autowired
    private PurchasePlanRequestRespository purchasePlanRequestRespository;
    @Autowired
    private SequentialRespository sequentialRespository;
    @Autowired
    private PurchaseInstitutionRespository purchaseInstitutionRespository;
    @Autowired
    private PurchaseTypeConfigRespository purchaseTypeConfigRespository;
    @Autowired
    private FunctionalSubjectRespository functionalSubjectRespository;
    @Autowired
    private GpcatlogRespository gpcatlogRespository;


    @Override
    public boolean createPlan(PurchasePlanRequestModel purchasePlanRequestModel, ProcurementUsage procurementUsage) {
        if(purchasePlanRequestModel.getType()!=Initialization.PURCHASE_DEFAULT_TYPE) {
            purchasePlanRequestModel.setGmt_create(Initialization.formatTime());
            //purchasePlanRequestModel.setUnitPrice(purchasePlanRequestModel.getUnitPrice()*1000000);
            PurchaseTypeConfig config_capital=purchaseTypeConfigRespository.findOne(procurementUsage.getCapitalTypeId());
            CapitalType capitalType=new CapitalType();
            capitalType.setAmount(purchasePlanRequestModel.getUnitPrice());
            capitalType.setCapitalCode(config_capital.getCode());
            capitalType.setCapitalName(config_capital.getName());
            List<CapitalType> capitalTypes=new ArrayList<>();
            capitalTypes.add(capitalType);
            purchasePlanRequestModel.setCapitalTypeList(capitalTypes);
            Sequential sequential=sequentialRespository.findByType(Initialization.SEQUEN_TYPE);
            int sequen=sequential.getSeque();
            String planId=Initialization.buildPurchasePlanId();
            sequential.setSeque(sequen+Initialization.SEQUEN_ADD);
            sequential.setGmt_modify(Initialization.formatTime());
            sequentialRespository.save(sequential);
            if(sequen!=0&&StringUtils.isNotBlank(planId)){
                purchasePlanRequestModel.setPurchaseplanDOCNO(Initialization.buildPurchasePlanNO(sequen));
                purchasePlanRequestModel.setFinancialBudgetId(planId);
                purchasePlanRequestModel.setFinancialPurchasePlanId(planId);
                purchasePlanRequestModel.setFinancialProjectNo(planId);
            }
            PurchaseInstitution purchaseInstitution=purchaseInstitutionRespository.findOne(procurementUsage.getOrgId());
            purchasePlanRequestModel.setOrganBudgetCode(purchaseInstitution.getBudget_code());
            purchasePlanRequestModel.setOrganName(purchaseInstitution.getName());
           Gpcatlog gpctlog=gpcatlogRespository.findOne(procurementUsage.getGpcatalogId());
            purchasePlanRequestModel.setGpcatalogCode(gpctlog.getCode());
            purchasePlanRequestModel.setGpcatalogName(gpctlog.getName());
            PurchaseTypeConfig config_pro_method=purchaseTypeConfigRespository.findOne(procurementUsage.getProcurementMethodId());
            purchasePlanRequestModel.setProcurementMethodCode(config_pro_method.getCode());
            purchasePlanRequestModel.setProcurementMethodName(config_pro_method.getName());
            PurchaseTypeConfig config_pro_type=purchaseTypeConfigRespository.findOne(procurementUsage.getProcurementTypeId());
            purchasePlanRequestModel.setProcurementTypeCode(config_pro_type.getCode());
            purchasePlanRequestModel.setProcurementTypeName(config_pro_type.getName());
            PurchaseTypeConfig config_pay_method=purchaseTypeConfigRespository.findOne(procurementUsage.getPaymentMethodId());
            purchasePlanRequestModel.setPaymentMethodCode(config_pay_method.getCode());
            purchasePlanRequestModel.setPaymentMethodName(config_pay_method.getName());
            FunctionalSubject functionalSubject=functionalSubjectRespository.findOne(procurementUsage.getFunctionalSubjectId());
            purchasePlanRequestModel.setFunctionalSubjectCode(functionalSubject.getCode());
            purchasePlanRequestModel.setFunctionalSubjectName(functionalSubject.getName());
            FunctionalSubject economicSubject=functionalSubjectRespository.findOne(procurementUsage.getEconomicSubjectId());
            purchasePlanRequestModel.setEconomicSubjectCode(economicSubject.getCode());
            purchasePlanRequestModel.setEconomicSubjectName(economicSubject.getName());
            purchasePlanRequestModel.setRecordType(Initialization.PURCHASE_DEFAULT_RECORD_TYPE);
            purchasePlanRequestModel.setSource(Initialization.PURCHASE_DEFAULT_SOURCE);
            purchasePlanRequestModel.setCapitalTypeCount(Initialization.PURCHASE_DEFAULT_CAPITAL_COUNT);
            if(purchasePlanRequestModel.getType()==Initialization.PURCHASE_DEFAULT_SUBMIT){
                purchasePlanRequestModel.setAudit(Initialization.PURCHASE_AUDIT_SUBMIT);
                purchasePlanRequestModel.setAudit_status(allotAudit(Initialization.PURCHASE_AUDIT_SUBMIT));
                purchasePlanRequestModel.setGmt_modify(Initialization.formatTime());
            }else{
                purchasePlanRequestModel.setAudit_status(allotAudit(Initialization.PURCHASE_AUDIT_CREATE));
            }
            purchasePlanRequestRespository.save(purchasePlanRequestModel);
            return true;
        }
        return false;
    }

    @Override
    public List<PurchasePlanRequestModel> findAll() {
        return purchasePlanRequestRespository.findAll(new Sort(new Sort.Order(Sort.Direction.DESC,"id")));
    }

    @Override
    public PurchasePlanRequestModel findById(String id) {
        if(StringUtils.isNotBlank(id)){
            return  purchasePlanRequestRespository.findOne(id);
        }
        return null;
    }

    @Override
    public boolean del(String id) {
        if(StringUtils.isNotBlank(id)){
            purchasePlanRequestRespository.delete(id);
            return  true;
        }
        return false;
    }

    @Override
    public boolean cancel(String id) {
        if(StringUtils.isNotBlank(id)){
            boolean et=purchasePlanRequestRespository.exists(id);
            if(et){
                PurchasePlanRequestModel purchasePlanRequestModel=purchasePlanRequestRespository.findOne(id);
                purchasePlanRequestModel.setAudit(Initialization.PURCHASE_AUDIT_CANCEL);
                purchasePlanRequestModel.setAudit_status(allotAudit(Initialization.PURCHASE_AUDIT_CANCEL));
                purchasePlanRequestModel.setGmt_modify(Initialization.formatTime());
                purchasePlanRequestRespository.save(purchasePlanRequestModel);
                return true;
            }
            return  false;
        }
        return false;
    }

    @Override
    public boolean submit(String id) {
        if(StringUtils.isNotBlank(id)) {
            boolean exits = purchasePlanRequestRespository.exists(id);
            if (exits) {
                PurchasePlanRequestModel purchasePlanRequestModel = purchasePlanRequestRespository.findOne(id);
                purchasePlanRequestModel.setAudit(Initialization.PURCHASE_AUDIT_SUBMIT);
                purchasePlanRequestModel.setAudit_status(allotAudit(Initialization.PURCHASE_AUDIT_SUBMIT));
                purchasePlanRequestModel.setGmt_modify(Initialization.formatTime());
                purchasePlanRequestRespository.save(purchasePlanRequestModel);
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public int query() {
        return purchasePlanRequestRespository.findByAudit(Initialization.PURCHASE_AUDIT_SUBMIT,new Sort(new Sort.Order(Sort.Direction.DESC,"id"))).size();
    }

    @Override
    public ApiResult execute(String id) {
        if(StringUtils.isNotBlank(id)){
            boolean exits = purchasePlanRequestRespository.exists(id);
            if (exits) {
                PurchasePlanRequestModel purchasePlanRequestModel = purchasePlanRequestRespository.findOne(id);
                Params params=buildParams(purchasePlanRequestModel);
                HttpClient httpClient = new HttpClient();
                httpClient.setAllowedRetry(false);
                httpClient.setConnTimeOutMilSeconds(50000);
                httpClient.setTimeOutMilSeconds(50000);
                httpClient.start();

                try {
                    String result  = httpClient.httpPost(params.getUri(), Config.CHARSET, params.getHeaders(), params.getBodyMap());
                    if(result.contains("200")&&result.contains("purchaseplanId")){
                        purchasePlanRequestModel.setExecute(Initialization.PURCHASE_EXECUTE_PASS);
                        purchasePlanRequestModel.setExecute_status(allotExecute(Initialization.PURCHASE_EXECUTE_PASS));
                        purchasePlanRequestModel.setExecute_req(JSON.toJSON(params).toString());
                        purchasePlanRequestModel.setExecute_resp(result);
                        purchasePlanRequestModel.setGmt_modify(Initialization.formatTime());
                        purchasePlanRequestRespository.save(purchasePlanRequestModel);
                        return ApiResult.isSuccess(params.getUri(), JSON.toJSON(params), result.substring(431,result.length()-2));
                    }else{
                        purchasePlanRequestModel.setExecute(Initialization.PURCHASE_EXECUTE_REFUSED);
                        purchasePlanRequestModel.setExecute_status(allotExecute(Initialization.PURCHASE_EXECUTE_REFUSED));
                        purchasePlanRequestModel.setExecute_req(JSON.toJSON(params).toString());
                        purchasePlanRequestModel.setExecute_resp(result);
                        purchasePlanRequestModel.setGmt_modify(Initialization.formatTime());
                        purchasePlanRequestRespository.save(purchasePlanRequestModel);
                        return ApiResult.isFailure(params.getUri(), JSON.toJSON(params), result);
                    }

                } catch (Exception e) {
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    e.printStackTrace(pw);
                    return ApiResult.isExecError(params.getUri(), JSON.toJSON(params), sw.toString());
                }
            }
            return ApiResult.isQueryError(Initialization.QUERY_PURCHASE_ERROR);
        }
        return ApiResult.isQueryError(Initialization.QUERY_ID_ERROR);
    }

    @Override
    public boolean audit(String id,int audit,String remark) {
        if(StringUtils.isNotBlank(id)){
            boolean exits=purchasePlanRequestRespository.exists(id);
            if(exits){
                PurchasePlanRequestModel purchasePlanRequestModel=purchasePlanRequestRespository.findOne(id);
                purchasePlanRequestModel.setAudit(audit);
                purchasePlanRequestModel.setAudit_status(allotAudit(audit));
                purchasePlanRequestModel.setAudit_remark(remark);
                if(audit==Initialization.PURCHASE_AUDIT_PASS){
                    purchasePlanRequestModel.setExecute(Initialization.PURCHASE_EXECUTE_SUBMIT);
                    purchasePlanRequestModel.setExecute_status(allotExecute(Initialization.PURCHASE_EXECUTE_SUBMIT));
                }
                purchasePlanRequestModel.setGmt_modify(Initialization.formatTime());
                purchasePlanRequestRespository.save(purchasePlanRequestModel);
                return true;
            }
            return  false;
        }
        return false;
    }

    @Override
    public boolean update(PurchasePlanRequestModel purchasePlanRequestModel,CapitalType capitalType) {
        return false;
    }

    @Override
    public List<PurchasePlanRequestModel> queryByAudit() {
        return purchasePlanRequestRespository.findByAudit(Initialization.PURCHASE_AUDIT_SUBMIT,new Sort(new Sort.Order(Sort.Direction.DESC,"id")));
    }

    @Override
    public List<PurchasePlanRequestModel> queryByAuditOver() {
        return purchasePlanRequestRespository.findByAuditGreaterThan(Initialization.PURCHASE_AUDIT_SUBMIT,new Sort(new Sort.Order(Sort.Direction.DESC,"id")));
    }

    @Override
    public List<PurchasePlanRequestModel> queryByExecute() {
        return purchasePlanRequestRespository.findByAuditAndExecute(Initialization.PURCHASE_AUDIT_PASS,Initialization.PURCHASE_EXECUTE_SUBMIT,new Sort(new Sort.Order(Sort.Direction.DESC,"id")));
    }

    @Override
    public List<PurchasePlanRequestModel> queryByCreate() {
        return purchasePlanRequestRespository.findByAuditNot(Initialization.PURCHASE_AUDIT_PASS,new Sort(new Sort.Order(Sort.Direction.DESC,"id")));
    }

    private  String allotAudit(int audit){
        Map<Integer,String > map=new ConcurrentHashMap<>();
        map.put(1,"废弃");
        map.put(0,"草稿");
        map.put(10,"待审核");
        map.put(20,"通过");
        map.put(30,"驳回");
        return  map.get(audit);
    }

    private  String allotExecute(int audit){
        Map<Integer,String > ex=new ConcurrentHashMap<>();
        ex.put(0,"暂无");
        ex.put(10,"待执行");
        ex.put(20,"成功");
        ex.put(30,"失败");
        return  ex.get(audit);
    }


    private Params buildParams(PurchasePlanRequestModel purchasePlanRequestModel) {
        Params param=new Params();
        JSONObject jsonObject = new JSONObject();
        JSONObject purchase = new JSONObject();
        purchase.put("districtCode",purchasePlanRequestModel.getDistrictCode());
        purchase.put("applyYear",purchasePlanRequestModel.getApplyYear());
        purchase.put("financialPurchasePlanId",purchasePlanRequestModel.getFinancialPurchasePlanId());
        purchase.put("financialBudgetId",purchasePlanRequestModel.getFinancialBudgetId());
        purchase.put("financialBudgetIndexId",purchasePlanRequestModel.getFinancialBudgetIndexId());
        purchase.put("organBudgetCode",purchasePlanRequestModel.getOrganBudgetCode());
        purchase.put("organName",purchasePlanRequestModel.getOrganName());
        purchase.put("gpcatalogCode",purchasePlanRequestModel.getGpcatalogCode());
        purchase.put("gpcatalogName",purchasePlanRequestModel.getGpcatalogName());
        purchase.put("functionalSubjectCode",purchasePlanRequestModel.getFunctionalSubjectCode());
        purchase.put("functionalSubjectName",purchasePlanRequestModel.getFunctionalSubjectName());
        purchase.put("economicSubjectCode",purchasePlanRequestModel.getEconomicSubjectCode());
        purchase.put("economicSubjectName",purchasePlanRequestModel.getEconomicSubjectName());
        purchase.put("financialProjectNo",purchasePlanRequestModel.getFinancialProjectNo());
        purchase.put("projectName",purchasePlanRequestModel.getProjectName());
        purchase.put("procurementContent",purchasePlanRequestModel.getProcurementContent());
        purchase.put("paymentMethodCode",purchasePlanRequestModel.getPaymentMethodCode());
        purchase.put("paymentMethodName",purchasePlanRequestModel.getPaymentMethodName());
        purchase.put("unitPrice",purchasePlanRequestModel.getUnitPrice());
        purchase.put("quantity",purchasePlanRequestModel.getQuantity());
        purchase.put("procurementMethodCode",purchasePlanRequestModel.getProcurementMethodCode());
        purchase.put("procurementMethodName",purchasePlanRequestModel.getProcurementMethodName());
        purchase.put("procurementTypeName",purchasePlanRequestModel.getProcurementTypeName());
        purchase.put("procurementTypeCode",purchasePlanRequestModel.getProcurementTypeCode());
        purchase.put("purchaseplanDOCNO",purchasePlanRequestModel.getPurchaseplanDOCNO());
        purchase.put("implementStart",purchasePlanRequestModel.getImplementStart());
        purchase.put("timeReleased",purchasePlanRequestModel.getTimeReleased());
        purchase.put("isTemporary",purchasePlanRequestModel.getIsTemporary());
        purchase.put("capitalTypeList",purchasePlanRequestModel.getCapitalTypeList());
        purchase.put("capitalTypeCount",purchasePlanRequestModel.getCapitalTypeCount());
        purchase.put("contactPerson",purchasePlanRequestModel.getContactPerson());
        purchase.put("contactTelephone",purchasePlanRequestModel.getContactTelephone());
        purchase.put("endDate",purchasePlanRequestModel.getEndDate());
        purchase.put("isOverSea",purchasePlanRequestModel.getIsOverSea());
        purchase.put("isUrgent",purchasePlanRequestModel.getIsUrgent());
        purchase.put("recordType",purchasePlanRequestModel.getRecordType());
        purchase.put("isGovernmentPurchaseService",purchasePlanRequestModel.getIsGovernmentPurchaseService());

        purchase.put("organSuperiorCode","");
        purchase.put("organSuperiorName","");
        purchase.put("technicalParameter","");
        purchase.put("measurementUnit","");
        purchase.put("director","");
        purchase.put("directorTelephone","");
        purchase.put("hasFinished","");
        purchase.put("remark","");
        purchase.put("targetOrganBudgetCode","");
        purchase.put("targetOrganName","");
        purchase.put("informalFinancialPlanId","");
        Map<String, Object> bodyMap = new HashMap<>();
        jsonObject.put("data",purchase);
        bodyMap.put("_data_", jsonObject.toString());
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        headers.put("X-Ca-Key", Config.PURCHASE_APP_KEY);
        headers.put("X-Ca-Format", "json2");
        String uri=Config.API_GATEWAY+Config.PURCHASE_PLAN_CREATE;
        String stringToSign= SignUtil.buildStringToSign(Config.PURCHASE_PLAN_CREATE, headers, bodyMap, "POST");
        Signer signer = new ShaHmac256();
        String signature = signer.sign(Config.PURCHASE_SECRET, stringToSign, "utf-8");
        headers.put("X-Ca-Signature", signature);
        param.setUri(uri);
        param.setBodyMap(bodyMap);
        param.setHeaders(headers);
        return param;
    }


}

package com.example.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.demo.common.RequestUtil;
import com.example.demo.common.zcy.*;
import com.example.demo.dao.AccountRespository;
import com.example.demo.model.Account;
import com.example.demo.model.car.vehicleQueryResp.VehicleQueryResponse;
import com.example.demo.model.purchase.response.OldPurchaseResponse;
import com.example.demo.model.purchase.response.PurchaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import com.example.demo.common.ApiResult;
import com.example.demo.dao.PurchaseRespository;
import com.example.demo.dao.RelationRespository;
import com.example.demo.model.Purchase;
import com.example.demo.model.Relation;
import com.example.demo.service.PurchaseService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Slf4j
@Service
public class PurchaseServiceImpl implements PurchaseService {
    @Autowired
    private PurchaseRespository purchaseRespository;
    @Autowired
    private RelationRespository relationRespository ;
    @Autowired
    private AccountRespository accountRespository;

    @Override
    public ApiResult addPurchase(RelationInfo relationInfo) {
        log.info("vue入参："+JSON.toJSONString(relationInfo));
        Purchase purchase=new Purchase();
        Params params;
        Params params2=null;
        if(StringUtils.isNotBlank(relationInfo.getOperator_id())&&relationInfo.getRequest_type()!=0){
            boolean ac=accountRespository.exists(relationInfo.getOperator_id());
            if (ac) {
                Relation relation;
                if(StringUtils.isNotBlank(relationInfo.getLocal_relation_id())){
                    relation = relationRespository.findOne(relationInfo.getLocal_relation_id());
                }else{
                    relation = relationRespository.findByRelation(relationInfo.getRelation_type());
                }
                Account account = accountRespository.findOne(relationInfo.getOperator_id());
                relationInfo.setRelation_code(relation.getRelation_code());
                relationInfo.setRelation_type(relation.getRelation());
                if (relationInfo.getRequest_type() == Config.RELATION_ADD || relationInfo.getRequest_type() == Config.RELATION_UPDATE) {
                    params = buildParamsOfRelationAddOrUpdate(relationInfo);
                } else if (relationInfo.getRequest_type() == Config.RELATION_DELETE || relationInfo.getRequest_type() == Config.REVOKE_SETTLEDMONEY) {
                    params = buildParamsOfRelationRevokeOrDelete(relationInfo);
                }else if(relationInfo.getRequest_type() == Config.FREEZE ){
                    params = buildParamsOfRelationRevokeOrDelete(relationInfo);
                    params2 = buildParamsOfRelationAddOrUpdate(relationInfo);
                }else if(relationInfo.getRequest_type() == Config.DEBUG_SETTLEMENT) {
                    params=buildParamsOfDebugSettlement(relationInfo);
                }else{
                    return ApiResult.isFailure(null, JSON.toJSONString(relationInfo), "执行方式不存在,请联系管理员配置!");
                }
                purchase.setGmt_create(formatTime());
                purchase.setOwner(relationInfo.getOperator_id());
                purchase.setOwner_name(account.getNick_name());
                if(relationInfo.getRequest_type()==Config.FREEZE){
                    StringBuffer sb=new StringBuffer(params.getUri());
                    StringBuffer sb3=new StringBuffer(JSON.toJSONString(params));
                    sb.append("\n").append(params2.getUri());
                    purchase.setExecute_method(sb.toString());
                    sb3.append("\n").append(JSON.toJSONString(params2));
                    purchase.setRequest_data(sb3.toString());
                }else{
                    purchase.setExecute_method(params.getUri());
                    purchase.setRequest_data(JSON.toJSONString(params));
                }
                purchase.setExecute(String.valueOf(relationInfo.getRequest_type()));
                purchase.setExecute_name(allotType(relationInfo.getRequest_type()));
                purchase.setRelation(String.valueOf(relation.getRelation()));
                purchase.setRelation_name(relation.getRelation_name());
                purchase.setPurchase_id(relationInfo.getPurchase_id());
                purchase.setBiz_content(JSON.toJSONString(relationInfo));
                HttpClient httpClient = new HttpClient();
                httpClient.setAllowedRetry(false);
                httpClient.setConnTimeOutMilSeconds(30000);
                httpClient.setTimeOutMilSeconds(30000);
                httpClient.start();
                try {
                    String result;
                    if(relationInfo.getRequest_type()==Config.FREEZE){
                        StringBuffer sb2=new StringBuffer();
                        String firstly=httpClient.httpPost(params.getUri(), Config.CHARSET, params.getHeaders(), params.getBodyMap());
                        String sconds=httpClient.httpPost(params2.getUri(), Config.CHARSET, params2.getHeaders(), params2.getBodyMap());
                        result=sb2.append(firstly).append("\n").append(sconds).toString();
                    }else{
                       result  = httpClient.httpPost(params.getUri(), Config.CHARSET, params.getHeaders(), params.getBodyMap());
                    }
                    log.info("请求参数："+JSON.toJSONString(params));
                    log.info("接口返回："+result);
                    purchase.setResponse_data(result);
                    String respString;
                    if(relationInfo.getRequest_type()==4||relationInfo.getRequest_type()==6){
                        respString=RequestUtil.getPosition(result);
                    }else{
                        respString=RequestUtil.getPosition(result,1);
                    }
                    if(result.contains("200")&&result.contains("success")&&result.contains("true")){
                        purchase.setExecute_result("SUCCESS");
                        purchaseRespository.save(purchase);
                        return ApiResult.isSuccess(params.getUri(), JSON.toJSONString(params),respString);

//                        String resp;
//                        boolean flag;
//                        if(result.contains("\"result\"")){
//                            if(result.contains("true")){
//                                OldPurchaseResponse oldPurchaseResponse=JSON.parseObject(RequestUtil.getPosition(result,0),OldPurchaseResponse.class);
//                                flag=oldPurchaseResponse.getResult().isSuccess();
//                                resp=JSON.toJSONString(oldPurchaseResponse);
//                            }else{
//                                flag=false;
//                                resp=RequestUtil.getPosition(result,1);
//                            }
//
//                        }else {
//                            PurchaseResponse purchaseResponse=JSON.parseObject(RequestUtil.getPosition(result),PurchaseResponse.class);
//                            flag=purchaseResponse.isSuccess();
//                            resp=JSON.toJSONString(purchaseResponse);
//
//                        }
//                        if (flag){
//                            purchase.setExecute_result("SUCCESS");
//                            purchaseRespository.save(purchase);
//                            return ApiResult.isSuccess(params.getUri(), JSON.toJSONString(params),resp );
//                        }else{
//                            purchase.setExecute_result("FAILURE");
//                            purchaseRespository.save(purchase);
//                            return ApiResult.isFailure(params.getUri(),"执行失败,请检查入参!",resp);
//                        }

                    }else{
                        purchase.setExecute_result("FAILURE");
                        purchaseRespository.save(purchase);
                        return ApiResult.isFailure(params.getUri(),"执行失败,请检查入参!",respString);
                    }

                } catch (Exception e) {
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    e.printStackTrace(pw);
                    return ApiResult.isExecError(params.getUri(), JSON.toJSONString(params), sw.toString());
                }

            }else if(ac==false){
                return ApiResult.isFailure(null,JSON.toJSONString(relationInfo),"鉴权失败，当前用户不存在或无此权限!");
            }else{
                return ApiResult.isFailure(null,JSON.toJSONString(relationInfo),"当前关联类型不存在，请联系管理员配置!");
            }

        }
        if(StringUtils.isBlank(relationInfo.getOperator_id())){
            return ApiResult.isBuildError(null,JSON.toJSONString(relationInfo),"用户信息读取异常!");
        }
        return ApiResult.isBuildError(null,JSON.toJSONString(relationInfo),"数据异常，执行方式或关联类型不能为空!");
    }

    @Override
    public List<Purchase> findAll() {
        return purchaseRespository.findAll(new Sort(new Sort.Order(Sort.Direction.DESC,"id")));
    }

    @Override
    public Purchase findById(String id) {
        return purchaseRespository.findOne(id);
    }

    @Override
    public boolean del(String id) {
        if(StringUtils.isNotBlank(id)){
            purchaseRespository.delete(id);
            return  true;
        }
        return false;
    }

    @Override
    public List<Purchase> query(String owner, String execute, String relation) {
        boolean ow=StringUtils.isNotBlank(owner);
        boolean ex=StringUtils.isNotBlank(execute);
        boolean re=StringUtils.isNotBlank(relation);
        if(ow&&ex&&re){
            return  purchaseRespository.findByOwnerAndExecuteAndRelation(owner,execute,relation);
        }else if(ow&&!ex&&!re){
            return purchaseRespository.findByOwner(owner);
        }else if(!ow&ex&!re){
            return purchaseRespository.findByExecute(execute);
        }else if(!ow&&!ex&&re){
            return purchaseRespository.findByRelation(relation);
        }else if(ow&&ex&&!re){
            return purchaseRespository.findByOwnerAndExecute(owner,execute);
        }else if(ow&&!ex&&re){
            return purchaseRespository.findByRelationAndOwner(relation,owner);
        }else if(!ow&ex&&re){
            return purchaseRespository.findByExecuteAndRelation(execute,relation);
        }

        return purchaseRespository.findAll(new Sort(new Sort.Order(Sort.Direction.DESC,"id")));
    }


    private String formatTime(){
       return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    private Params buildParamsOfRelationAddOrUpdate(RelationInfo relationInfo){
        Params params=new Params();
        Map<String, Object> bodyMap = new HashMap<>();
        JSONArray relationList = new JSONArray();
        JSONObject relationParam = new JSONObject();
        relationParam.put("purchaseplanId",Long.parseLong(relationInfo.getPurchase_id()));
        relationParam.put("amount",Long.parseLong(relationInfo.getAmount()));
        relationParam.put("quantity",relationInfo.getQuantity());
        relationList.put(relationParam);
        JSONObject outObject = new JSONObject();
        outObject.put("userId",Long.parseLong(relationInfo.getUser_id()));
        outObject.put("relationType",relationInfo.getRelation_type());
        outObject.put("relationID",Long.parseLong(relationInfo.getRelation_id()));
        outObject.put("relationList", relationList);
        String strBody = outObject.toString();
        bodyMap.put("_data_", strBody);
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        headers.put("X-Ca-Key", Config.APP_KEY);
        headers.put("X-Ca-Format", "json2");
        String stringToSign;
        String url;
        if(relationInfo.getRequest_type()==Config.RELATION_UPDATE) {
            url=Config.API_GATEWAY+Config.URI_PPLAN_RELATION_UPDATE;
            stringToSign =SignUtil.buildStringToSign(Config.URI_PPLAN_RELATION_UPDATE, headers, bodyMap,"POST");
        }else {
            url=Config.API_GATEWAY+Config.URI_PPLAN_RELATION_ADD;
            stringToSign =SignUtil.buildStringToSign(Config.URI_PPLAN_RELATION_ADD, headers, bodyMap,"POST");

        }
        Signer signer = new ShaHmac256();
        String signature = signer.sign(Config.SECRET, stringToSign, Config.CHARSET);
        headers.put("X-Ca-Signature", signature);
        params.setUri(url);
        params.setBodyMap(bodyMap);
        params.setHeaders(headers);
        return params;
    }

    private Params buildParamsOfRelationRevokeOrDelete(RelationInfo relationInfo) {
        Params param=new Params();
        Map<String, Object> bodyMap = new HashMap<>();
        JSONObject outObject = new JSONObject();
        if(relationInfo.getRequest_type()==Config.RELATION_DELETE){
            outObject.put("userId",Long.parseLong(relationInfo.getUser_id()));
            outObject.put("relationType", relationInfo.getRelation_type());
            outObject.put("relationID",Long.parseLong(relationInfo.getRelation_id()));
        }else{
            outObject.put("userId", Long.parseLong(relationInfo.getUser_id()));
            outObject.put("relationType",relationInfo.getRelation_code());
            outObject.put("relationId",Long.parseLong(relationInfo.getRelation_id()));
        }
        String strBody = outObject.toString();
        bodyMap.put("_data_", strBody);
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        headers.put("X-Ca-Key", Config.APP_KEY);
        headers.put("X-Ca-Format", "json2");
        String stringToSign;
        String uri;
        if(relationInfo.getRequest_type()==Config.RELATION_DELETE){
            uri=Config.API_GATEWAY+Config.URI_PPLAN_RELATION_DELETE;
            stringToSign= SignUtil.buildStringToSign(Config.URI_PPLAN_RELATION_DELETE, headers, bodyMap, "POST");
        }else{
            uri=Config.API_GATEWAY+Config.URI_PPLAN_DEBUG_REVOKE_SETTLEDMONEY;
            stringToSign= SignUtil.buildStringToSign(Config.URI_PPLAN_DEBUG_REVOKE_SETTLEDMONEY, headers, bodyMap, "POST");
        }
        Signer signer = new ShaHmac256();
        String signature = signer.sign(Config.SECRET, stringToSign, "utf-8");
        headers.put("X-Ca-Signature", signature);
        param.setUri(uri);
        param.setBodyMap(bodyMap);
        param.setHeaders(headers);
        return param;
    }

    private Params buildParamsOfDebugSettlement(RelationInfo relationInfo){
        Params param=new Params();
        Map<String, Object> bodyMap = new HashMap<>();
        JSONObject outObject = new JSONObject();
        JSONObject sonObject = new JSONObject();
        outObject.put("relationType",relationInfo.getRelation_type());
//        outObject.put("settlementDto",sonObject);
        outObject.put("amount",relationInfo.getAmount());
        outObject.put("quantity",relationInfo.getQuantity());
        outObject.put("relationId",relationInfo.getRelation_id());
        outObject.put("planId",relationInfo.getPurchase_id());
        //sonObject.put("settlementId",0);
        String strBody = outObject.toString();
        bodyMap.put("_data_", strBody);
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        headers.put("X-Ca-Key", Config.APP_KEY);
        headers.put("X-Ca-Format", "json2");
        String uri=Config.API_GATEWAY+Config.URI_PPLAN_RELATION_SETTLEMENT;
        String stringToSign= SignUtil.buildStringToSign(Config.URI_PPLAN_RELATION_SETTLEMENT, headers, bodyMap, "POST");
        Signer signer = new ShaHmac256();
        String signature = signer.sign(Config.SECRET, stringToSign, "utf-8");
        headers.put("X-Ca-Signature", signature);
        param.setUri(uri);
        param.setBodyMap(bodyMap);
        param.setHeaders(headers);
        return param;
    }
    private  String allotType(int type){
        Map<Integer,String> m=new ConcurrentHashMap<>();
        m.put(1,"冻结：可用→冻结☆新增");
        m.put(2,"冻结：可用→冻结☆更新");
        m.put(3,"释放：冻结→可用");
        m.put(4,"释放：已用→可用");
        m.put(5,"冻结：已用→冻结");
        m.put(6,"结算：冻结→已用");
        return  m.get(type);
    }
}

package com.example.demo.common;

import com.alibaba.fastjson.JSON;
import com.example.demo.common.zcy.*;
import com.example.demo.model.car.InstitutionImportParams;
import com.example.demo.model.car.InstitutionQueryParams;
import com.example.demo.model.car.VehicleImportParams;
import com.example.demo.model.car.delResp.OrgDelResponse;
import com.example.demo.model.car.delResp.VehicleDelResponse;
import com.example.demo.model.car.importResp.ImportResponse;
import com.example.demo.model.car.orgQueryResp.InstitutionResponse;
import com.example.demo.model.car.vehicleQueryResp.VehicleQueryResponse;
import com.example.demo.model.purchase.response.DateResponse;
import com.example.demo.model.purchase.response.UserAuthResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
@Slf4j
public class RequestUtil {

    public static String execRequest(String json,String uri){
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("_data_", json);
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        headers.put("X-Ca-Timestamp", String.valueOf(new Date().getTime()));
        headers.put("X-Ca-Key", Config.APP_KEY);
        headers.put("X-Ca-Format", "json2");
        headers.put("Accept", "application/json");
        String stringToSign = SignUtil.buildStringToSign(uri, headers, bodyMap, "POST");
        Signer signer = new ShaHmac256();
        String signature = signer.sign(Config.SECRET, stringToSign, "utf-8");
        headers.put("X-Ca-Signature", signature);
        HttpClient httpClient = new HttpClient();
        httpClient.setAllowedRetry(false);
        httpClient.setConnTimeOutMilSeconds(100000);
        httpClient.setTimeOutMilSeconds(100000);
        httpClient.start();
        String result;
        try {
            result = httpClient.httpPost(Config.API_GATEWAY + uri, "utf-8", headers, bodyMap);
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            result=sw.toString();
        }
        return result;
    }
    public static String execRequest(String gateway,String key,String secret,String json,String uri){
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("_data_", json);
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        headers.put("X-Ca-Timestamp", String.valueOf(new Date().getTime()));
        headers.put("X-Ca-Key", key);
        headers.put("X-Ca-Format", "json2");
        headers.put("Accept", "application/json");
        String stringToSign = SignUtil.buildStringToSign(uri, headers, bodyMap, "POST");
        Signer signer = new ShaHmac256();
        String signature = signer.sign(secret, stringToSign, "utf-8");
        headers.put("X-Ca-Signature", signature);
        HttpClient httpClient = new HttpClient();
        httpClient.setAllowedRetry(false);
        httpClient.setConnTimeOutMilSeconds(100000);
        httpClient.setTimeOutMilSeconds(100000);
        httpClient.start();
        String result;
        try {
            result = httpClient.httpPost(gateway + uri, "utf-8", headers, bodyMap);
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            result=sw.toString();
        }
        return result;
    }
    public static String authRequest(String json,String uri){
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json;charset=utf-8");
//        headers.put("X-Ca-Timestamp", String.valueOf(new Date().getTime()));
        headers.put("X-Ca-Format", "json2");
        headers.put("Accept", "application/json");
        HttpClient httpClient = new HttpClient();
        httpClient.setAllowedRetry(false);
        httpClient.setConnTimeOutMilSeconds(100000);
        httpClient.setTimeOutMilSeconds(100000);
        httpClient.start();
        String response;
        try {
            response = httpClient.httpPost(uri, "utf-8",headers,json);
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            response=sw.toString();
        }
        return response;
    }
    public static void main(String[] args){
       //orgnQuery();
       //importOrg();
       //delOrg();
       // vehicleQuery();
        //vehicleRemove();
        //vehicleImport();
//        Long timestamp = Long.parseLong("-2035785600000");
//        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date(timestamp));
//        System.out.println(date);
//        System.out.println(secondToDate("-2035785600000","yyyy-MM-dd"));


    }

    private static void delOrg(){
        String url="/fiscal/zcy.car.org.delete";
        String orgId="1000175890";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",orgId);
        String result=execRequest(jsonObject.toString(),url);
        //52112 {"success":false,"code":"-1L","message":"删除车控机构扩展信息参数为空","result":null}
        // {"success":false,"code":"org.id.not.exist","message":"org.id.not.exist","result":null}
        //{"success":true,"code":null,"message":null,"result":true}
        if(isSuccess(result)){
            OrgDelResponse orgDelResponse =JSON.parseObject(getPosition(result),OrgDelResponse.class);
            if(orgDelResponse.isSuccess()&& orgDelResponse.isResult()){
                System.out.println("del success");
            }else{
                System.out.println(orgDelResponse.getCode()+ orgDelResponse.getMessage());
            }
        }else{
            System.out.println("exception");
        }
    }
    private static void importOrg(){
        String url="/fiscal/zcy.car.org.init.import";
        InstitutionImportParams institution=new InstitutionImportParams();
        institution.setLineNum("1");
        institution.setOrgName("平阳县南雁镇人民政府");
        institution.setOrgCode("0025354200");
        institution.setCarManager("曾善良");
        institution.setPhone("13868591588");

        InstitutionImportParams institution2=new InstitutionImportParams();
        institution2.setLineNum("2");
        institution2.setOrgName("平阳县南雁镇人民政府");
        institution2.setOrgCode("0025354200");
        institution2.setCarManager("曾善良");
        institution2.setPhone("13868591588");
        List<InstitutionImportParams> list=new ArrayList<>();
        list.add(institution);
        list.add(institution2);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("importInfo",list);
        System.out.println(jsonObject.toString());
        String result=execRequest(jsonObject.toString(),url);
        //{"success":false,"code":"-1L","message":"导入机构参数为空","result":null}
        //org.import.info.phone.required
        // org.import.org.code.duplicate 导入数据重复 org.import.info.org.code.invalid 组织机构代码错误 org.import.info.car.info.isInDB 机构已存在 org.import.info.org.name.not.same 单位名称错误
        //{"success":true,"code":null,"message":null,"result":{"total":2,"skipped":2,"succeed":0,"failed":2,"fails":[{"line":"2","name":"","error":"org.import.info.car.info.isInDB"},{"line":"3","name":"","error":"org.import.info.car.info.isInDB"}],"succeeds":[],"isRunning":false,"quotaCreateUpdateNum":null,"inQuotaLine":null,"backup":null}}
        //{"success":true,"code":null,"message":null,"result":{"total":2,"skipped":0,"succeed":1,"failed":1,"fails":[{"line":"3","name":"0025354200","error":"org.import.org.code.duplicate"}],"succeeds":[{"line":"2"}],"isRunning":false,"quotaCreateUpdateNum":null,"inQuotaLine":null,"backup":null}}
        if(isSuccess(result)){
            ImportResponse ImportResponse =JSON.parseObject(getPosition(result),ImportResponse.class);
            if(ImportResponse.isSuccess()){

                System.out.println("共"+ ImportResponse.getResult().getTotal()+"条，成功："+ ImportResponse.getResult().getSucceed()+"条，失败："+ ImportResponse.getResult().getFailed()+"条");

            }else{
                System.out.println("导入机构参数为空");
            }
            System.out.println(JSON.toJSONString(ImportResponse));
        }else {
            System.out.println("exception");
        }
    }
    private static void orgnQuery(){
        String url="/fiscal/zcy.car.org.list";
        InstitutionQueryParams institutionQueryParams=new InstitutionQueryParams();
        //institutionQueryParams.setOrgCode("12330000663920297D");
        institutionQueryParams.setOrgName("平阳县南雁镇人民政府");
        institutionQueryParams.setOrgRealName("平阳县南雁镇人民政府");
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("districtCode","339900");
//        jsonObject.put("orgName","浙江体育职业技术学院");
//        jsonObject.put("orgRealName","浙江体育职业技术学院");
//        jsonObject.put("orgCode","12330000663920297D");
        String result=execRequest(JSON.toJSON(institutionQueryParams).toString(),url);
        if(isSuccess(result)){
            InstitutionResponse model=JSON.parseObject(getPosition(result),InstitutionResponse.class);
            if(model.isSuccess()){
                if(model.getResult().isEmpty()||model.getResult().getTotal()==0){
                    System.out.println("查询无结果");
                }else{
                    System.out.println(JSON.toJSONString(model));
                }

            }else{
                System.out.println("查询失败");
            }
        }else {
            System.out.println("exception");
        }

    }

    private static void vehicleQuery(){
        String url="/fiscal/zcy.car.vehicle.list";
        String plantNO="浙AKF056";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("plateNo",plantNO);
        jsonObject.put("labelType",1);
        String result=execRequest(jsonObject.toString(),url);
        if(isSuccess(result)){
            VehicleQueryResponse vehicleQueryResponse =JSON.parseObject(getPosition(result),VehicleQueryResponse.class);
            if(vehicleQueryResponse.isSuccess()){
                System.out.println(JSON.toJSONString(vehicleQueryResponse));
            }else{
                System.out.println(JSON.toJSONString(vehicleQueryResponse));
            }
        }else{
            System.out.println("exception");
        }


    }
    private static void vehicleImport(){
        String uri="/fiscal/zcy.car.vehicle.init.import";
        List<VehicleImportParams> vehicles=new ArrayList<>();
        VehicleImportParams vehicleImportParams=new VehicleImportParams();
        vehicleImportParams.setLineNum("1");
        vehicleImportParams.setOrgCode("12332529472550131L");
        vehicleImportParams.setOrgName("景宁畲族自治县港航管理所");
        vehicleImportParams.setPlateNo("浙KC1012");
        vehicleImportParams.setCarClassName("轿车");
        vehicleImportParams.setCarUsageName("执法执勤用车");
        vehicleImportParams.setPrice("17.30");
        vehicleImportParams.setRegisterDate("2003-7-1");
        vehicleImportParams.setBrandName("猎豹");
        vehicleImportParams.setVolumeOut("2");
        vehicleImportParams.setVinNo("LL6652B063A010143");
        vehicleImportParams.setEngineNo("6224");
        vehicleImportParams.setIsImport("国产");

        VehicleImportParams vehicleImportParams2=new VehicleImportParams();
        vehicleImportParams2.setLineNum("2");
        vehicleImportParams2.setOrgCode("12332529472550131LA");
        vehicleImportParams2.setOrgName("景宁畲族自治县港航管理所");
        vehicleImportParams2.setPlateNo("浙KC1012");
        vehicleImportParams2.setCarClassName("轿车");
        vehicleImportParams2.setCarUsageName("执法执勤用车");
        vehicleImportParams2.setPrice("17.30");
        vehicleImportParams2.setRegisterDate("2003-7-1");
        vehicleImportParams2.setBrandName("猎豹");
        vehicleImportParams2.setVolumeOut("2");
        vehicleImportParams2.setVinNo("LL6652B063A010143A");
        vehicleImportParams2.setEngineNo("6224A");
        vehicleImportParams2.setIsImport("国产");
        vehicles.add(vehicleImportParams);
        vehicles.add(vehicleImportParams2);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("importInfo",vehicles);
        String result=execRequest(jsonObject.toString(),uri);
        // 单条车辆信息导入时， code错误 {"success":false,"code":"org.import.info.org.code.invalid","message":"org.import.info.org.code.invalid","result":null}
        // 多条车辆信息导入时 org.car.code.name.not.match
        // name {"success":true,"code":null,"message":null,"result":{"total":1,"skipped":0,"succeed":0,"failed":1,"fails":[{"line":"2","name":"景宁畲族自治县港航管理所A","error":"org.import.import.org.name.not.same.with.system.org.name"}],"succeeds":[],"isRunning":false,"quotaCreateUpdateNum":null,"inQuotaLine":null,"backup":null}}
        // 车辆类型 {"success":true,"code":null,"message":null,"result":{"total":1,"skipped":0,"succeed":0,"failed":1,"fails":[{"line":"2","name":"","error":"org.import.car.class.name.not.exist"}],
        // 性质 org.import.car.usage.name.not.exist
        // 价格 org.import.car.price.invalid
        // 品牌 org.import.car.import.brand.not.exists
        // 排量 org.import.car.volume.out.invalid
        // 车架号重复 org.import.car.vin.no.duplicate
        // 发动机 org.import.car.engine.no.duplicate
        // 进口 org.import.car.is.import.invalid
        // 车牌号 org.import.car.plate.no.duplicate
        // 日期格式错误 {"success":true,"code":null,"message":null,"result":{"total":1,"skipped":0,"succeed":0,"failed":1,"fails":[{"line":"2","name":"2003/7/1","error":"org.import.car.register.date.invalid"}],"succeeds":[],"isRunning":false,"quotaCreateUpdateNum":null,"inQuotaLine":null,"backup":null}}
        // success {"success":true,"code":null,"message":null,"result":{"total":1,"skipped":0,"succeed":1,"failed":0,"fails":[],"succeeds":[{"line":"2"}],"isRunning":false,"quotaCreateUpdateNum":null,"inQuotaLine":null,"backup":null}}
        if(isSuccess(result)){
            ImportResponse importResponse =JSON.parseObject(getPosition(result),ImportResponse.class);
            if(importResponse .isSuccess()){
                System.out.println(JSON.toJSONString(importResponse ));
            }else{
                System.out.println(JSON.toJSONString(importResponse ));
            }
        }else{
            System.out.println("exception");
        }
    }
    private static void vehicleRemove(){
        String url="/fiscal/zcy.car.vehicle.delete";
        List<String> stock=new ArrayList<>();
        stock.add("44430");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("stockIds",stock);
        String result=execRequest(jsonObject.toString(),url);
        //{"success":true,"code":null,"message":null,"result":true}
        //{"success":false,"code":"org.import.car.import.cannot.delete.not.normal.use.car","message":"org.import.car.import.cannot.delete.not.normal.use.car","result":null}
        //{"success":false,"code":"car.stock.delete.only.import","message":"car.stock.delete.only.import","result":null}
        if(isSuccess(result)){
            VehicleDelResponse vehicleDelResponse =JSON.parseObject(getPosition(result),VehicleDelResponse.class);
            if(vehicleDelResponse.isSuccess()&& vehicleDelResponse.isResult()){
                System.out.println("del success");
            }else{
                System.out.println(vehicleDelResponse.getCode()+ vehicleDelResponse.getMessage());
            }
        }else{
            System.out.println("exception");
        }

    }
    /**
     * 获取obj中的所有方法
     * @param obj
     * @return
     */
    public static List<Method> getAllMethods(Object obj){
        List<Method> methods = new ArrayList<Method>();
        Class<?> clazz = obj.getClass();
        while(!clazz.getName().equals("java.lang.Object")){
            methods.addAll(Arrays.asList(clazz.getDeclaredMethods()));
            clazz = clazz.getSuperclass();
        }
        return methods;
    }
    /**
     * 将一个类用属性名为Key，值为Value的方式存入map
     * @param obj
     * @return
     */
    public static Map<String,Object> convert2Map(Object obj){
        Map<String,Object> map = new HashMap<String,Object>();
        List<Method> methods = getAllMethods(obj);
        for(Method m:methods){
            String methodName = m.getName();
            if(methodName.startsWith("get")){
                //获取属性名
                String propertyName = toLowerCaseFirstOne(methodName.substring(3));
                try {
                    map.put(propertyName,m.invoke(obj));
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }

    private static String toLowerCaseFirstOne(String s){
        if(Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    public static List getBankListByExcel(InputStream in, String fileName) throws Exception {
        List<List<Object>> list = new ArrayList<>();
        //创建Excel工作薄
        Workbook work = getWorkbook(in, fileName);
        if (null == work) {
            throw new Exception("创建Excel工作薄为空！");
        }
        Sheet sheet;
        Row row;
        Cell cell;
        for (int i = 0; i < work.getNumberOfSheets(); i++) {
            sheet = work.getSheetAt(i);
            if (sheet == null) {
                continue;
            }
            for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) {
                row = sheet.getRow(j);
                if (row == null || row.getFirstCellNum() == j) {
                    continue;
                }
                List<Object> li = new ArrayList<>();
                for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                    cell = row.getCell(y);
//                    li.add(cell.toString());
//                    if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK){
//                        continue;
//                    }
                    li.add(getCellValue(cell));
//                    if(StringUtils.isNotBlank(values)&&values!=null){
//                        li.add(values);
//                    }
                    //li.removeAll(Collections.singleton(null));
                }
                if(li.get(i)!=null){
                    list.add(li);
                }
            }
        }
        work.close();
        System.out.println("list:"+JSON.toJSONString(list));
        return list;
    }

    /**
     * 判断文件格式
     * @return wb
     * @throws Exception
     */
    private static Workbook getWorkbook(InputStream inStr, String fileName) throws Exception {
        Workbook workbook;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if (".xls".equals(fileType)) {
            workbook = new HSSFWorkbook(inStr);
        } else if (".xlsx".equals(fileType)) {
            workbook = new XSSFWorkbook(inStr);
        } else {
            throw new Exception("请上传excel文件！");
        }
        return workbook;
    }

    public static String getPosition(String result){
        int pos=result.lastIndexOf("\"success\"")-1;
        return result.substring(pos,result.length());
    }
    public static String getPosition(String result,int flag){
        int pos=result.lastIndexOf("\"result\"")-1;
        if(flag==0){
            return result.substring(pos,result.length());
        }
        return result.substring(pos,result.length()-1);
    }
    public static boolean isSuccess(String result){
        if(result.contains("200")&&result.contains("success")){
            return true;
        }else{
            return false;
        }

    }
    private static String getCellValue(Cell cell){
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        }
        // 判断数据的类型
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC: // 数字
                //short s = cell.getCellStyle().getDataFormat();
                if (XSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
                    SimpleDateFormat sdf = null;
                    // 验证short值
                    if (cell.getCellStyle().getDataFormat() == 14) {
                        sdf = new SimpleDateFormat("yyyy-MM-dd");
                    } else if (cell.getCellStyle().getDataFormat() == 21) {
                        sdf = new SimpleDateFormat("HH:mm:ss");
                    } else if (cell.getCellStyle().getDataFormat() == 22) {
                        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    } else {
//                       return String.valueOf(cell.getDateCellValue()); v0
                        sdf = new SimpleDateFormat("yyyy-MM-dd");
                    }
                    Date date = cell.getDateCellValue();
                    cellValue = sdf.format(date);
                }else{
//                    cell.setCellType(Cell.CELL_TYPE_STRING); v1
//                    cellValue = String.valueOf(cell.getRichStringCellValue().getString()); v1
                    DecimalFormat df = new DecimalFormat("0.####");
                    cellValue = df.format(cell.getNumericCellValue());
                }
                break;
            case Cell.CELL_TYPE_STRING: // 字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN: // Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA: // 公式
               // cellValue = String.valueOf(cell.getCellFormula()); v1
                try {
                    cellValue = String.valueOf(cell.getStringCellValue());
                } catch (IllegalStateException e) {
                    cellValue = String.valueOf(cell.getNumericCellValue());
                }
                break;
            case Cell.CELL_TYPE_BLANK: // 空值
                cellValue = "";
                break;
            case Cell.CELL_TYPE_ERROR: // 故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = cell.getRichStringCellValue() == null ? null : cell.getRichStringCellValue().toString();
                break;
        }
        return cellValue;
    }

    public static String secondToDate(String seconds,String patten){
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis( Long.parseLong(seconds));
//        return new SimpleDateFormat(patten).format(calendar.getTime());
        return new SimpleDateFormat(patten).format(new Date(Long.parseLong(seconds)));
    }
    public static String pointToMilions(String amount){
        return BigDecimal.valueOf(Long.valueOf(amount)).divide(new BigDecimal(1000000)).toString();
    }
    public static void httpPost(String token,String msg)throws Exception{
        org.apache.http.client.HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(token);
        httppost.addHeader("Content-Type", "application/json; charset=utf-8");
        StringEntity se = new StringEntity(msg, "utf-8");
        httppost.setEntity(se);
        HttpResponse response = httpclient.execute(httppost);
        if (response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
            String result= EntityUtils.toString(response.getEntity(), "utf-8");
        }
    }

    public static DateResponse dateRequest(String httpArg) {
        BufferedReader reader = null;
        String result = null;
        DateResponse dateResponse = null;
        StringBuffer sbf = new StringBuffer();
        String httpUrl = "http://api.goseek.cn/Tools/holiday?date=" + httpArg;
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
            dateResponse =JSON.parseObject(result,DateResponse.class);//转为JSONObject对象
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateResponse;
    }
}

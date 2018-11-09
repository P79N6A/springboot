package com.example.demo.common;

import com.alibaba.fastjson.JSON;
import com.example.demo.common.zcy.*;
import com.example.demo.model.car.InstitutionImportParams;
import com.example.demo.model.car.InstitutionQueryParams;
import com.example.demo.model.car.orgDelResp.OrgDelResp;
import com.example.demo.model.car.orgImportResp.OrgImportResp;
import com.example.demo.model.car.orgQueryResp.InstitutionRespModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import springfox.documentation.spring.web.json.Json;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

    public static void main(String[] args){
       //orgnQuery();
       importOrg();
       //delOrg();
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
            OrgDelResp orgDelResp=JSON.parseObject(getPosition(result),OrgDelResp.class);
            if(orgDelResp.isSuccess()&&orgDelResp.isResult()){
                System.out.println("del success");
            }else{
                System.out.println(orgDelResp.getCode()+orgDelResp.getMessage());
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
            OrgImportResp orgImportResp=JSON.parseObject(getPosition(result),OrgImportResp.class);
            if(orgImportResp.isSuccess()){

                System.out.println("共"+orgImportResp.getResult().getTotal()+"条，成功："+orgImportResp.getResult().getSucceed()+"条，失败："+orgImportResp.getResult().getFailed()+"条");

            }else{
                System.out.println("导入机构参数为空");
            }
            System.out.println(JSON.toJSONString(orgImportResp));
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
        //System.out.println(JSON.toJSON(institutionQueryParams));
        String result=execRequest(JSON.toJSON(institutionQueryParams).toString(),url);
        if(isSuccess(result)){
            InstitutionRespModel model=JSON.parseObject(getPosition(result),InstitutionRespModel.class);
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
                        sdf = new SimpleDateFormat("yyyy/MM/dd");
                    } else if (cell.getCellStyle().getDataFormat() == 21) {
                        sdf = new SimpleDateFormat("HH:mm:ss");
                    } else if (cell.getCellStyle().getDataFormat() == 22) {
                        sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    } else {
                        throw new RuntimeException("日期格式错误!!!");
                    }
                    Date date = cell.getDateCellValue();
                    cellValue = sdf.format(date);
                } else if (cell.getCellStyle().getDataFormat() == 0) {//处理数值格式
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    cellValue = String.valueOf(cell.getRichStringCellValue().getString());
                }
                break;
            case Cell.CELL_TYPE_STRING: // 字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN: // Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA: // 公式
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case Cell.CELL_TYPE_BLANK: // 空值
                cellValue = "";
                break;
            case Cell.CELL_TYPE_ERROR: // 故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }
}

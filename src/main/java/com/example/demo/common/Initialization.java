package com.example.demo.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Initialization {
    public static  final int ACCOUNT_STATUS=10;
    public static  final String ACCOUNT_INIT_PWD="123456";
    public static  final int MENU_STATUS=10;
    public static  final int MENU_ROLE_ADMIN=0;
    public static  final int MENU_ROLE_OPERATOR=1;
    public static  final int MENU_ROLE_CUSTOMER=2;
    public static  final int PURCHASE_TYPE_CONFIG_STATUS=10;
    public static  final int PURCHASE_TYPE_CONFIG_TYPE=0;
    public static  final int SEQUEN_TYPE=1;
    public static  final int SEQUEN_ADD=1;
    public static  final int GPCATLOG_FLAG=1;
    public static  final int GPCATLOG_DEFAULT=0;
    public static  final int SUBJECT_DEFAULT=0;

    public static  final int PURCHASE_DEFAULT_TYPE=0;
    public static  final int PURCHASE_DEFAULT_CREATE=1;
    public static  final int PURCHASE_DEFAULT_SUBMIT=2;
    public static  final int PURCHASE_DEFAULT_UPDATE=3;
    public static  final int PURCHASE_DEFAULT_RESET=4;
    public static  final int PURCHASE_AUDIT_CREATE=0;
    public static  final int PURCHASE_AUDIT_CANCEL=1;
    public static  final int PURCHASE_AUDIT_SUBMIT=10;
    public static  final int PURCHASE_AUDIT_PASS=20;
    public static  final int PURCHASE_AUDIT_REFUSED=30;
    public static  final int PURCHASE_DEFAULT_RECORD_TYPE=2;
    public static  final int PURCHASE_DEFAULT_SOURCE=2;
    public static  final int PURCHASE_DEFAULT_CAPITAL_COUNT=1;
    public static  final int PURCHASE_EXECUTE_SUBMIT=10;
    public static  final int PURCHASE_EXECUTE_PASS=20;
    public static  final int PURCHASE_EXECUTE_REFUSED=30;
    public static  final String QUERY_ID_ERROR="执行id不能为空";
    public static  final String QUERY_PURCHASE_ERROR="该采购计划不存在,无法执行";





    public static  String formatTime(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public static String buildPurchasePlanId() {
        String result = new Random().nextInt(100) + "";
        if (result.length() == 1) {
            result = "0" + result;
        }
        StringBuffer outBiz =new StringBuffer("CGZX-");
        outBiz.append(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())).append("00").append(result);
        return outBiz.toString();
    }

    public static String buildPurchasePlanNO(int planNO) {
        if( planNO==0){
            planNO=new Random().nextInt(1000);
        }
        StringBuffer sb=new StringBuffer();
        if(planNO<100){
            if(planNO<10){
                sb.append("临[").append( new SimpleDateFormat("yyyy").format(new Date())).append("]").append("-CGZX-00").append(planNO).append("号");
            }else{
                sb.append("临[").append( new SimpleDateFormat("yyyy").format(new Date())).append("]").append("-CGZX-0").append(planNO).append("号");
            }
        }else{
            sb.append("临[").append( new SimpleDateFormat("yyyy").format(new Date())).append("]").append("-CGZX-").append(planNO).append("号");
        }
        return sb.toString();
    }

}

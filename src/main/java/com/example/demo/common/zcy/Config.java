package com.example.demo.common.zcy;

import com.alibaba.fastjson.JSON;

public class Config {
    public static final String CHARSET = "utf-8";
    /**密钥**/
    public static final String SECRET = "F4Cbc4nnKMJg";
    /**app_id**/
   public static final String APP_KEY = "354232";
    /**test**/
    public static final String PURCHASE_SECRET_TEST = "LIIyun0KNXpW";
    public static final String PURCHASE_APP_KEY_TEST= "805259";
    public static final String PURCHASE_SECRET = "TZ46nuPR2z";
    public static final String PURCHASE_APP_KEY= "816563";
    /**请求网关**/
   public static final String API_GATEWAY = "http://api.zcy.gov.cn";
   public static final String API_GATEWAY_TEST = "http://sandbox.zcy.gov.cn";
    /***冻结：新增>>全量*/
    public static final String URI_PPLAN_RELATION_ADD = "/test/pplan.relation.add";
    /***冻结：更新>>部分*/
    public static final String URI_PPLAN_RELATION_UPDATE = "/test/pplan.relation.update";
    /***释放：冻结>>可用*/
    public static final String URI_PPLAN_RELATION_DELETE = "/test/pplan.relation.delete";
    /***释放：已用>>可用*/
    public static final String URI_PPLAN_DEBUG_REVOKE_SETTLEDMONEY = "/test/pplan.debug.revoke.settledmoney";
    /**采购计划新增**/
    public static final String PURCHASE_PLAN_CREATE = "/integration/zcy.govsyncagency.pplan.add";
    /***释放：冻结>>已用*/
    public static final String URI_PPLAN_RELATION_SETTLEMENT = "/test/plan.debug.settlement";
    /**合同备案状态更新*/
    public static final String RECORD_REVOKE_UPDATE = "/integration/zcy.record.revoke.status.update";
    /**车辆控购接口名称列表**/
    /**机构导入**/
    public static final String ORG_INIT_IMPORT= "/fiscal/zcy.car.org.init.import";
    /**机构查询**/
    public static final String ORG_INIT_QUERY= "/fiscal/zcy.car.org.list";
    /**机构删除**/
    public static final String ORG_INIT_DELETE= "/fiscal/zcy.car.org.delete";
    /**车辆导入**/
    public static final String VEHICLE_INIT_IMPORT= "/fiscal/zcy.car.vehicle.init.import";
    /**车辆查询**/
    public static final String VEHICLE_INIT_QUERY= "/fiscal/zcy.car.vehicle.list";
    /**车辆删除**/
    public static final String VEHICLE_INIT_DELETE= "/fiscal/zcy.car.vehicle.delete";
    /**域账号 登陆验证 登出 token校验 **/
    public static final String  USER_AUTH_LOGIN= "http://ipaas.cai-inc.com/api/auth-service/auth/login";
    public static final String  USER_AUTH_OUT= "http://ipaas.cai-inc.com/api/auth-service/auth/logout";
    public static final String  USER_TOKEN_VERIFY= "http://ipaas.cai-inc.com/api/auth-service/auth/checkToken";
    public static final String WEBHOOK_TOKEN = "https://oapi.dingtalk.com/robot/send?access_token=be565b17b6fd1d5d61b2ab5c0719a534fc2dd22069d60c99a72698ad7122b952";
    public static final int RELATION_ADD = 1;
    public static final int RELATION_UPDATE = 2;
    public static final int RELATION_DELETE = 3;
    public static final int REVOKE_SETTLEDMONEY = 4;
    public static final int FREEZE = 5;
    public static final int DEBUG_SETTLEMENT = 6;


    public static void main(String[] str){
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        System.out.println(sdf.format(new Date()));
////        Calendar calendar = Calendar.getInstance();
////        calendar.add(Calendar.DATE, 3);
////        String three_days_after = sdf.format(calendar.getTime());
////        System.out.println(three_days_after);
//        Calendar cal = Calendar.getInstance();
//        //cal.setTime(cal.getTime());
//        if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
//            System.out.println("1");
//        } else{
//            System.out.println("2");
//        }

        String s="insert into db_settlement.purchaseplan_relation( purchaseplan_relation_id,  purchaseplan_id,  user_id,  relation_type,  relation_id,  amount,  quantity,  status,  create_time) values(1000000000000693192, 1000000000000676003, 1000436795, 32, 22280, 1500000, 1, 1, '2018-08-14 09:12:22');";
        String flag="values";
        int pos=s.indexOf(flag)+flag.length()+1;
        String[] params=s.substring(pos,s.length()-2).replaceAll("\\s*","").split(",");
        RelationInfo relationInfo=new RelationInfo();
        relationInfo.setPurchase_id(params[1]);
        relationInfo.setUser_id(params[2]);
        relationInfo.setRelation_type(Integer.parseInt(params[3]));
        relationInfo.setRelation_id(params[4]);
        relationInfo.setAmount(params[5]);
        relationInfo.setQuantity(Integer.parseInt(params[6]));
        System.out.println(JSON.toJSONString(relationInfo));

    }

}

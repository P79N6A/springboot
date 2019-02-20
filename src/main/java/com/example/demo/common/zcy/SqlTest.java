package com.example.demo.common.zcy;

import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SqlTest {
    public static void main(String[] args){
        String codeList="450000,450100,450102,450103,450104,450105,450106,450107,450108,450109,450110,450121,450123,450124,450125,450126,450127,450150,450199,450200,450202,450203,450204,450205,450206,450207,450208,450222,450223,450224,450225,450226,450299,450300,450302,450303,450304,450305,450311,450312,450321,450322,450323,450324,450325,450326,450327,450328,450329,450330,450331,450332,450399,450400,450403,450404,450405,450406,450421,450422,450423,450481,450499,450500,450502,450503,450512,450521,450599,450600,450602,450603,450621,450681,450699,450700,450702,450703,450721,450722,450799,450800,450802,450803,450804,450821,450881,450899,450900,450902,450903,450921,450922,450923,450924,450940,450981,450999,451000,451002,451021,451022,451023,451024,451025,451026,451027,451028,451029,451030,451031,451099,451100,451102,451103,451121,451122,451123,451199,451200,451202,451221,451222,451223,451224,451225,451226,451227,451228,451229,451281,451299,451300,451302,451321,451322,451323,451324,451381,451399,451400,451402,451421,451422,451423,451424,45";
        String code[]=codeList.split(",");
        StringBuffer stringBuffer2=new StringBuffer();
//        for (int i = 0; i <code.length ; i++) {
//            StringBuffer stringBuffer=new StringBuffer("insert into sm_protocol_qualification(pro_id,  supplier_name,  supplier_id,  from_org_id,  from_org_name,  approval_org_id,  approval_org_name,  approval_user_id,  approval_user_name,  bussiness_id,  deliver_id,  protocol_code,  protocol_name,  pur_cat_type_code,  pur_cat_type_name,  districtType,  type,  state,  impl_start_date,  impl_end_date,  application_comment,  status,  bid_content,  creator,  modifier,  gmt_create,  gmt_modified,  org_id,  source) \n" +
//                    "values('1000000000000010021', '测试零一有限公司', '100011231', '100011231', '测试零一有限公司', '1000111464', '演示省财政厅', null, null, '3', '");
//
//            String result=stringBuffer.append(code[i]).append("', '2016111901', '2016年入围协议', '386176735800983552', '二期专用              ', 'multi', 1, 'wait', '2016-11-19 00:00:00', '2017-11-30 23:59:59', null, null, null, '超级机构管理员', '超级机构管理员', '2016-11-19 14:17:40', '2016-11-19 14:17:40', 100010014, 0);").toString();
//            stringBuffer2.append(result);
//
//        }
//        String result2[]=stringBuffer2.toString().split(";");
        Map<String,String> dataMap=new HashMap<>();
        dataMap.put("2019-01-30","技术支持-当前值班人：早班(08.00-17.00):半土、容成,晚班(10.00-20.00):李尧、凌秋");
        dataMap.put("2019-02-01","技术支持-当前值班人：早班(08.00-17.00):半土、容成,晚班(10.00-20.00):李尧、凌秋");
        dataMap.put("2019-02-02","技术支持-当前值班人：早班(08.00-17.00):半土、容成,晚班(10.00-20.00):李尧、凌秋");
        dataMap.put("2019-02-03","技术支持-当前值班人：早班(08.00-17.00):半土、容成,晚班(10.00-20.00):李尧、凌秋");
        dataMap.put("2019-02-04","技术支持-当前值班人：容成(backup:泰山)");
        dataMap.put("2019-02-05","技术支持-当前值班人：泰山(backup:容成)");
        dataMap.put("2019-02-06","技术支持-当前值班人：容成(backup:泰山)");
        dataMap.put("2019-02-07","技术支持-当前值班人：金九(backup:范蠡)");
        dataMap.put("2019-02-08","技术支持-当前值班人：金九(backup:范蠡)");
        dataMap.put("2019-02-09","技术支持-当前值班人：范蠡(backup:金九)");
        dataMap.put("2019-02-10","技术支持-当前值班人：范蠡(backup:金九)");
        dataMap.put("2019-02-11","技术支持-当前值班人：早班(08.00-17.00):时升,晚班(10.00-20.00):微辰");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String result=dataMap.get(sdf.format(new Date()));
        System.out.println(StringUtils.isNotBlank(result) ?result:false);

        }
    }

package com.example.demo.Scheduling;

import com.example.demo.model.WorkDuty;
import com.example.demo.service.WorkDutyService;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Configuration
@EnableScheduling
public class SchedulingConfig {
    public static String WEBHOOK_TOKEN = "https://oapi.dingtalk.com/robot/send?access_token=be565b17b6fd1d5d61b2ab5c0719a534fc2dd22069d60c99a72698ad7122b952";
    public static String WEBHOOK_TOKEN_TEST = "https://oapi.dingtalk.com/robot/send?access_token=67eba905e3aef25a3ace347952063cb739ddb5654c050d26166cead10ccda2bd";
    public static String WEBHOOK_TOKEN_STOCK = "https://oapi.dingtalk.com/robot/send?access_token=a386c700f4b9bb55509326bee24692410f94f4b74f6e6472c56e1e0310d1a14d";
//    @Autowired
//    private PurchasePlanRequestService purchasePlanRequestService;
    @Autowired
    private WorkDutyService workDutyService;
//
//    @Scheduled(cron = "0 0/60 9-17 * * ?") // 工作日9-17每小时
//    public void query() throws Exception{
//        int count=purchasePlanRequestService.query();
//        List<PurchasePlanRequestModel> executeList=purchasePlanRequestService.queryByExecute();
//        if(count!=0){
//            String msg="{\n" +
//                    "     \"msgtype\": \"markdown\",\n" +
//                    "     \"markdown\": {\"title\":\"新的任务\",\n" +
//                    "\"text\":\"#### 消息提醒  \\n > 您好，@18814887123 有"+count+"条采购计划申请，需要您审核！\\n\\n > ######  [-----立即处理](http://10.110.2.31/purchase_audit) \"\n"+
//                    "     },\n" +
//                    "    \"at\": {\n" +
//                    "        \"atMobiles\": [\n" +
//                    "            \"18814887123\"\n" +
//                    "        ], \n" +
//                    "        \"isAtAll\": false\n" +
//                    "    }\n" +
//                    " }";
//            httpPost(WEBHOOK_TOKEN_TEST,msg);
//        }
//        if (executeList.size()!=0){
//            StringBuffer project=new StringBuffer();
//            for (int i = 0; i <executeList.size() ; i++) {
//                project.append(",").append(executeList.get(i).getPurchaseplanDOCNO());
//            }
//            String msg="{\n" +
//                    "     \"msgtype\": \"markdown\",\n" +
//                    "     \"markdown\": {\"title\":\"友情提示\",\n" +
//                    "\"text\":\"#### 审核完成  \\n > 您好，@13666683602 "+project.toString()+",已审核通过！\\n\\n > ######  [-----查看并执行](http://10.110.2.31/purchase_create) \"\n"+
//                    "     },\n" +
//                    "    \"at\": {\n" +
//                    "        \"atMobiles\": [\n" +
//                    "            \"13666683602\"\n" +
//                    "        ], \n" +
//                    "        \"isAtAll\": false\n" +
//                    "    }\n" +
//                    " }";
//            httpPost(WEBHOOK_TOKEN_TEST,msg);
//        }
//    }

    @Scheduled(cron = "0 0 10 * * FRI")
    public void dutyChatBot() throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 3);
        WorkDuty workDuty=workDutyService.findByStartTime(sdf.format(calendar.getTime()));
        if(workDuty!=null){
            String msg="{\n" +
                    "     \"msgtype\": \"markdown\",\n" +
                    "     \"markdown\": {\"title\":\"值班提醒\",\n" +
                    "\"text\":\"#### 温馨提示  \\n > 您好，@"+workDuty.getPhone()+",下周由你轮值，千万不要忘记哦(๑╹◡╹)ﾉ  \\n\\n  > 开始时间："+workDuty.getStartTime()+" \\n\\n  > 结束时间："+workDuty.getEndTime()+"  \\n\\n  > 时间段："+workDuty.getWorkLot()+" \\n\\n > ######  [-----------点击查看详情](http://10.110.2.31/technical/system) \"\n"+
                    "     },\n" +
                    "    \"at\": {\n" +
                    "        \"atMobiles\": [\n" +
                    "            \""+workDuty.getPhone()+"\"\n" +
                    "        ], \n" +
                    "        \"isAtAll\": false\n" +
                    "    }\n" +
                    " }";
            httpPost(WEBHOOK_TOKEN,msg);
        }else{
            String textMsg = "{ \"msgtype\": \"text\", \"text\": {\"content\": \"值班计划表暂未生成\"}}";
            httpPost(WEBHOOK_TOKEN,textMsg);
        }
    }
    @Scheduled(cron = "0 10 8 * * MON")
    public void dutyChatBotWithMon() throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date=sdf.format(new Date());
        WorkDuty workDuty=workDutyService.findByStartTime(date);
        if(workDuty!=null){
            String msg="{\n" +
                    "     \"msgtype\": \"markdown\",\n" +
                    "     \"markdown\": {\"title\":\"值班提醒\",\n" +
                    "\"text\":\"#### 温馨提示  \\n > 您好，@"+workDuty.getPhone()+","+date+"号,今天由你值班呢，记得本周是你的值班周\\n\\n到岗时间：8.30分\\n\\n  > 时间段："+workDuty.getWorkLot()+" \"\n"+
                    "     },\n" +
                    "    \"at\": {\n" +
                    "        \"atMobiles\": [\n" +
                    "            \""+workDuty.getPhone()+"\"\n" +
                    "        ], \n" +
                    "        \"isAtAll\": false\n" +
                    "    }\n" +
                    " }";
            httpPost(WEBHOOK_TOKEN,msg);
        }
    }

    @Scheduled(cron = "0 10 8 * * ?")
    public void dutyChatStock() throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        WorkDuty workDuty=workDutyService.findByStartTimeRange(sdf.format(new Date()));
        if(workDuty!=null){
            Calendar cal = Calendar.getInstance();
            String textMsg;
            if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                textMsg = "{ \"msgtype\": \"text\", \"text\": {\"content\": \"技术支持-周末值班人:"+workDuty.getUser_name()+"\"}}";
            } else{
                textMsg = "{ \"msgtype\": \"text\", \"text\": {\"content\": \"技术支持-当前值班人:"+workDuty.getUser_name()+"\"}}";
            }
            httpPost(WEBHOOK_TOKEN_STOCK,textMsg);
        }


    }

    private void httpPost(String token,String msg)throws Exception{
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(token);
        httppost.addHeader("Content-Type", "application/json; charset=utf-8");
        StringEntity se = new StringEntity(msg, "utf-8");
        httppost.setEntity(se);
        HttpResponse response = httpclient.execute(httppost);
        if (response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
            String result= EntityUtils.toString(response.getEntity(), "utf-8");
            System.out.println(result);
        }
    }
}

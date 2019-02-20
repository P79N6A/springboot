package com.example.demo.Scheduling;

import com.example.demo.common.RequestUtil;
import com.example.demo.model.Account;
import com.example.demo.model.WorkDuty;
import com.example.demo.service.AccountService;
import com.example.demo.service.WorkDutyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Configuration
@EnableScheduling
public class SchedulingConfig {
    public static String WEBHOOK_TOKEN = "https://oapi.dingtalk.com/robot/send?access_token=be565b17b6fd1d5d61b2ab5c0719a534fc2dd22069d60c99a72698ad7122b952";
    public static String WEBHOOK_TOKEN_TEST = "https://oapi.dingtalk.com/robot/send?access_token=e292dbf9b07bd91be1820a9c33aae8cae33f542762777d216d5a9ed254ccd513";
    public static String WEBHOOK_TOKEN_STOCK = "https://oapi.dingtalk.com/robot/send?access_token=a386c700f4b9bb55509326bee24692410f94f4b74f6e6472c56e1e0310d1a14d";

    @Autowired
    private WorkDutyService workDutyService;
    @Autowired
    private AccountService accountService;


    /**下周值班人选通知**/
    @Scheduled(cron = "0 0 10,17 * * FRI")
    public void dutyChatBot() throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
        String textMsg;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 3);
        WorkDuty workDuty=workDutyService.findByStartTime(sdf.format(calendar.getTime()));
        int holiday = RequestUtil.dateRequest(sdf2.format(calendar.getTime())).getData();
        if (workDuty != null) {
            if (workDuty.getType() == 0) {
                Account account = accountService.findById(workDuty.getUserId());
                textMsg = "{\n" +
                        "     \"msgtype\": \"markdown\",\n" +
                        "     \"markdown\": {\"title\":\"值班提醒\",\n" +
                        "\"text\":\"#### 温馨提示  \\n > 您好，@" + account.getPhone() + ",下周由你轮值，千万不要忘记哦(๑╹◡╹)ﾉ  \\n\\n  > 开始时间：" + workDuty.getStartTime() + " \\n\\n  > 结束时间：" + workDuty.getEndTime() + "  \\n\\n  > 时间段：" + workDuty.getWorkLot() + " \\n\\n > ######  [-----------点击查看详情](http://10.110.2.31/technical/system) \"\n" +
                        "     },\n" +
                        "    \"at\": {\n" +
                        "        \"atMobiles\": [\n" +
                        "            \"" + account.getPhone() + "\"\n" +
                        "        ], \n" +
                        "        \"isAtAll\": false\n" +
                        "    }\n" +
                        " }";
            } else {
                StringBuffer userName = new StringBuffer();
                userName.append(workDuty.getEarlyLot()).append("、").append(workDuty.getLateLot());
                textMsg=buildMsgWithFair(workDuty,userName.toString());
            }
        } else {
            if (holiday != 0) {
                Calendar calendars = Calendar.getInstance();
                calendars.add(Calendar.DATE, 5);
                WorkDuty workDutys = workDutyService.findByStartTimeRange(sdf.format(calendars.getTime()));
                if (workDutys != null) {
                    StringBuffer userNames = new StringBuffer();
                    userNames.append(workDutys.getEarlyLot()).append("、").append(workDutys.getLateLot());
                    textMsg=buildMsgWithFair(workDutys,userNames.toString());
                } else {
                    textMsg = "{ \"msgtype\": \"text\", \"text\": {\"content\": \"工作日值班计划表暂未生成\"}}";
                }
            } else {
                textMsg = "{ \"msgtype\": \"text\", \"text\": {\"content\": \"值班计划表暂未生成\"}}";
            }
        }
        RequestUtil.httpPost(WEBHOOK_TOKEN, textMsg);

    }
    /**周一值班通知**/
    @Scheduled(cron = "0 0 8 * * MON")
    public void dutyChatBotWithMon() throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
        String textMsg;
        WorkDuty workDuty=workDutyService.findByStartTime(sdf.format(new Date()));
        int holiday = RequestUtil.dateRequest(sdf2.format(new Date())).getData();
        if (workDuty != null) {
            if (workDuty.getType() == 0) {
                Account account = accountService.findById(workDuty.getUserId());
                textMsg = "{\n" +
                        "     \"msgtype\": \"markdown\",\n" +
                        "     \"markdown\": {\"title\":\"值班提醒\",\n" +
                        "\"text\":\"#### 温馨提示  \\n > 您好，@" + account.getPhone() + ",今天由你值班呢，记得本周是你的值班周(๑╹◡╹)ﾉ  \\n\\n  > 开始时间：" + workDuty.getStartTime() + " \\n\\n  > 结束时间：" + workDuty.getEndTime() + "  \\n\\n  > 时间段：" + workDuty.getWorkLot() + " \\n\\n > ######  [-----------点击查看详情](http://10.110.2.31/technical/system) \"\n" +
                        "     },\n" +
                        "    \"at\": {\n" +
                        "        \"atMobiles\": [\n" +
                        "            \"" + account.getPhone() + "\"\n" +
                        "        ], \n" +
                        "        \"isAtAll\": false\n" +
                        "    }\n" +
                        " }";
            } else {
                StringBuffer userName = new StringBuffer();
                userName.append(workDuty.getEarlyLot()).append("、").append(workDuty.getLateLot());
                textMsg=buildMsgWithMon(workDuty,userName.toString());
            }
        } else {
            if (holiday != 0) {
                Calendar calendars = Calendar.getInstance();
                calendars.add(Calendar.DATE, 2);
                WorkDuty workDutys = workDutyService.findByStartTimeRange(sdf.format(calendars.getTime()));
                if (workDutys != null) {
                    StringBuffer users = new StringBuffer();
                    users.append(workDutys.getEarlyLot()).append("、").append(workDutys.getLateLot());
                    textMsg=buildMsgWithMon(workDutys,users.toString());
                } else {
                    textMsg = "{ \"msgtype\": \"text\", \"text\": {\"content\": \"工作日值班计划表暂未生成\"}}";
                }
            } else {
                textMsg = "{ \"msgtype\": \"text\", \"text\": {\"content\": \"值班计划表暂未生成\"}}";
            }
        }
        RequestUtil.httpPost(WEBHOOK_TOKEN, textMsg);
    }
    /**定时每天值班通知**/
    @Scheduled(cron = "0 0 8 * * ?")
    public void dutyChatStock() throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        WorkDuty workDuty=workDutyService.findByStartTimeRange(sdf.format(new Date()));
        String textMsg;
        if(workDuty!=null){
            Calendar cal = Calendar.getInstance();
            if(workDuty.getType()==0){
                if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                    textMsg = "{ \"msgtype\": \"text\", \"text\": {\"content\": \"技术支持-周末值班人:"+workDuty.getUser_name()+"\"}}";
                } else{
                    textMsg = "{ \"msgtype\": \"text\", \"text\": {\"content\": \"技术支持-当前值班人:"+workDuty.getUser_name()+"\"}}";
                }
            }else{
                StringBuffer sb=new StringBuffer();
                sb.append("早班(08.00-17.00):").append(workDuty.getEarlyLot()).append(",晚班(10.00-20.00):").append(workDuty.getLateLot());
                textMsg = "{ \"msgtype\": \"text\", \"text\": {\"content\": \"技术支持-当前值班人："+sb.toString()+"\"}}";

            }
        }else{
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
            int holiday = RequestUtil.dateRequest(sdf2.format(new Date())).getData();
            if(holiday!=0){
                List<Account> users=accountService.getLockSequence(0,1);
                if(users.size()!=0){
                    if(holiday==1){
                        textMsg = "{ \"msgtype\": \"text\", \"text\": {\"content\": \"技术支持-周末值班人:"+users.get(0).getNick_name()+"\"}}";
                    }else{
                        textMsg = "{ \"msgtype\": \"text\", \"text\": {\"content\": \"技术支持-节假日值班人:"+users.get(0).getNick_name()+"\"}}";
                    }

                }else{
                    List<Account> userList=accountService.showMeetingList();
                    for (int i = 0; i <userList.size() ; i++) {
                        accountService.updateLock(userList.get(i).getId(),0);
                    }
                    List<Account> newUser=accountService.getLockSequence(0,1);
                    if(holiday==1){
                        textMsg = "{ \"msgtype\": \"text\", \"text\": {\"content\": \"技术支持-周末值班人:"+newUser.get(0).getNick_name()+"\"}}";
                    }else{
                        textMsg = "{ \"msgtype\": \"text\", \"text\": {\"content\": \"技术支持-节假日值班人:"+newUser.get(0).getNick_name()+"\"}}";
                    }
                }
            }else{
                textMsg = "{ \"msgtype\": \"text\", \"text\": {\"content\": \"暂无值班人员记录\"}}";
            }
        }
        RequestUtil.httpPost(WEBHOOK_TOKEN_STOCK,textMsg);

    }
    /**定时周末轮值通知**/
    @Scheduled(cron = "0 0 10,17 * * FRI")
    public void dutyChatBotWithHoliday() throws Exception{
        String msg;
        WorkDuty workDuty;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        List<Account> account=accountService.getLockSequence(0,1);
        if(account.size()!=0){
            workDuty=workDutyService.findByMothAndLockUser(simpleDateFormat.format(new Date()),account.get(0).getNick_name());
            String lot;
            if(workDuty==null){
                lot="休息日";
            }else{
                lot=workDuty.getLockLot();
            }
            msg=buildMsgWithLock(account.get(0),lot);
        }else{
            List<Account> accountList=accountService.showMeetingList();
            for (int i = 0; i <accountList.size() ; i++) {
                accountService.updateLock(accountList.get(i).getId(),0);
            }
            List<Account> newLock=accountService.getLockSequence(0,1);
            workDuty=workDutyService.findByMothAndLockUser(simpleDateFormat.format(new Date()),newLock.get(0).getNick_name());
            msg=buildMsgWithLock(newLock.get(0),workDuty.getLockLot());
            accountService.updateLock(newLock.get(0).getId(),1);

        }
        RequestUtil.httpPost(WEBHOOK_TOKEN,msg);

    }
    /**定时清洗轮值**/
    @Scheduled(cron = "0 0 18 * * WED")
    public void updateDutyWithHoliday(){
        List<Account> account=accountService.getLockSequence(0,1);
        if(account.size()!=0){
            accountService.updateLock(account.get(0).getId(),1);
        }
    }
    /**定时会议通知**/
    @Scheduled(cron = "0 30 16 * * ?")
    public void meetingChatBotWithDay() throws Exception{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        int holiday = RequestUtil.dateRequest(simpleDateFormat.format(new Date())).getData();
        if(holiday==0) {
            String msg;
            String userId;
            List<Account> accounts = accountService.getMeetingSequence(1, 0);
            if (accounts.size() != 0) {
                userId = accounts.get(0).getId();
                msg = buildMsg(accounts.get(0));
            } else {
                List<Account> accountList = accountService.showMeetingList();
                for (int i = 0; i < accountList.size(); i++) {
                    accountService.updateSequence(accountList.get(i).getId(), 0);
                }
                List<Account> newSequence = accountService.getMeetingSequence(1, 0);
                userId = newSequence.get(0).getId();
                msg = buildMsg(newSequence.get(0));

            }
            RequestUtil.httpPost(WEBHOOK_TOKEN, msg);
            accountService.updateSequence(userId, 1);
        }

    }

//    /**临时固定通知**/
//    @Scheduled(cron = "0 0 8 * * ?")
//    public void dutyChatStock() throws Exception {
//        Map<String,String> dataMap=new HashMap<>();
//        dataMap.put("2019-01-31","早班(08.00-17.00):半土、容成,晚班(10.00-20.00):李尧、凌秋");
//        dataMap.put("2019-02-01","早班(08.00-17.00):半土、容成,晚班(10.00-20.00):李尧、凌秋");
//        dataMap.put("2019-02-02","早班(08.00-17.00):半土、容成,晚班(10.00-20.00):李尧、凌秋");
//        dataMap.put("2019-02-03","早班(08.00-17.00):半土、容成,晚班(10.00-20.00):时升、凌秋");
//        dataMap.put("2019-02-04","容成(backup:泰山)");
//        dataMap.put("2019-02-05","泰山(backup:容成)");
//        dataMap.put("2019-02-06","容成(backup:泰山)");
//        dataMap.put("2019-02-07","金九(backup:范蠡)");
//        dataMap.put("2019-02-08","金九(backup:范蠡)");
//        dataMap.put("2019-02-09","范蠡(backup:金九)");
//        dataMap.put("2019-02-10","范蠡(backup:金九)");
//        dataMap.put("2019-02-11","早班(08.00-17.00):时升,晚班(10.00-20.00):微辰");
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String result=dataMap.get(sdf.format(new Date()));
//        if(StringUtils.isNotBlank(result)){
//            String textMsg = "{ \"msgtype\": \"text\", \"text\": {\"content\": \"技术支持-当前值班人："+result+"\"}}";
//            RequestUtil.httpPost(WEBHOOK_TOKEN_STOCK,textMsg);
//        }
//    }

    private static String buildMsg(Account account){
        String message="{\n" +
                "     \"msgtype\": \"markdown\",\n" +
                "     \"markdown\": {\"title\":\"会议邀请\",\n" +
                "\"text\":\"#### 会议提醒  \\n > @"+account.getPhone()+" Hi,"+account.getNick_name()+",来自客满的会议邀请，请准时赴邀\\n\\n  > 时间：今日下午五点\\n\\n  > 地点：4楼致远厅\"\n" +
                "     },\n" +
                "    \"at\": {\n" +
                "        \"atMobiles\": [\n" +
                "            \""+account.getPhone()+"\"\n" +
                "        ], \n" +
                "        \"isAtAll\": false\n" +
                "    }\n" +
                " }";
        return  message;
    }

    private static String buildMsgWithFair(WorkDuty workDuty,String user){
        String textMsg= "{\n" +
                "     \"msgtype\": \"markdown\",\n" +
                "     \"markdown\": {\"title\":\"值班提醒\",\n" +
                "\"text\":\"#### 轮值提示  \\n > 您好,"+user+",下周由你们轮值(๑╹◡╹)ﾉ  \\n\\n > 早班(08.00-17.00)：" + workDuty.getEarlyLot() + " \\n\\n > 晚班(10.00-20.00)：" + workDuty.getLateLot() + " \\n\\n  > 开始时间：" + workDuty.getStartTime() + " \\n\\n  > 结束时间：" + workDuty.getEndTime() + "  \\n\\n  > 时间段：" + workDuty.getWorkLot() + " \\n\\n > ######  [-----------点击查看详情](http://10.110.2.31/technical/system) \"\n" +
                "     },\n" +
                "    \"at\": {\n" +
                "        \"isAtAll\": true\n" +
                "    }\n" +
                " }";
        return textMsg;
    }
    private static String buildMsgWithMon(WorkDuty workDuty,String user){
        String textMsg= "{\n" +
                "     \"msgtype\": \"markdown\",\n" +
                "     \"markdown\": {\"title\":\"值班提醒\",\n" +
                "\"text\":\"#### 值班提醒  \\n > 您好,"+user+",本周是你們的值班周,请谨记! \\n\\n > 早班(08.00-17.00)：" + workDuty.getEarlyLot() + " \\n\\n > 晚班(10.00-20.00)：" + workDuty.getLateLot() + " \\n\\n  > 开始时间：" + workDuty.getStartTime() + " \\n\\n  > 结束时间：" + workDuty.getEndTime() + "  \\n\\n  > 时间段：" + workDuty.getWorkLot() + " \\n\\n > ######  [-----------点击查看详情](http://10.110.2.31/technical/system) \"\n" +
                "     },\n" +
                "    \"at\": {\n" +
                "        \"isAtAll\": true\n" +
                "    }\n" +
                " }";
        return textMsg;
    }

    private static String buildMsgWithLock(Account account,String lockLot){
        String message="{\n" +
                "     \"msgtype\": \"markdown\",\n" +
                "     \"markdown\": {\"title\":\"值班提醒\",\n" +
                "\"text\":\"#### 周末onCall提醒  \\n > @"+account.getPhone()+" Hi,"+account.getNick_name()+",本周非工作日由你值班！\\n\\n  > 时间："+lockLot+"(含法定节假日)\"\n" +
                "     },\n" +
                "    \"at\": {\n" +
                "        \"atMobiles\": [\n" +
                "            \""+account.getPhone()+"\"\n" +
                "        ], \n" +
                "        \"isAtAll\": false\n" +
                "    }\n" +
                " }";
        return  message;
    }
}

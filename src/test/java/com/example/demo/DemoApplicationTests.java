package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.dingtalk.chatbot.DingtalkChatbotClient;
import com.dingtalk.chatbot.SendResult;
import com.dingtalk.chatbot.message.MarkdownMessage;
import com.example.demo.common.RequestUtil;
import com.example.demo.model.Account;
import com.example.demo.model.Relation;
import com.example.demo.model.WorkDuty;
import com.example.demo.service.AccountService;
import com.example.demo.service.RelationService;
import com.example.demo.service.WorkDutyService;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
	private DingtalkChatbotClient client = new DingtalkChatbotClient();
	public static String WEBHOOK_TOKEN = "https://oapi.dingtalk.com/robot/send?access_token=be565b17b6fd1d5d61b2ab5c0719a534fc2dd22069d60c99a72698ad7122b952";
	public static String WEBHOOK_TOKEN_TEST = "https://oapi.dingtalk.com/robot/send?access_token=e292dbf9b07bd91be1820a9c33aae8cae33f542762777d216d5a9ed254ccd513";
	public static String WEBHOOK_TOKEN_STOCK = "https://oapi.dingtalk.com/robot/send?access_token=a386c700f4b9bb55509326bee24692410f94f4b74f6e6472c56e1e0310d1a14d";
	//    @Autowired
//    private PurchasePlanRequestService purchasePlanRequestService;
	@Autowired
	private WorkDutyService workDutyService;
	@Autowired
	private AccountService accountService;

	@Test
	public void contextLoads() throws Exception {
//		List<Account> accountList = accountService.showMeetingList();
//		for (int i = 0; i < accountList.size(); i++) {
//			accountService.updateSequence(accountList.get(i).getId(), 0);
//		}

	}

//		MarkdownMessage message = new MarkdownMessage();
//		message.setTitle("值班提醒");
//		message.add(MarkdownMessage.getReferenceText("温馨提示"));
//		message.add("\n\n");
//		String name="凌秋";
//		String phone="+86-13666683602";
//		String st="2018-09-03";
//		String end="2018-09-09";
//		StringBuffer sb=new StringBuffer();
//		sb.append("您好，@").append(name).append("值班时间:2018-09-03··2018-09-09");
//		message.add(MarkdownMessage.getBoldText(sb.toString()));
//		message.add("\n\n");
//		message.add("开始时间:"+st);
//		message.add("\n\n");
//		message.add("结束时间:"+end);
//		message.add("\n\n");
//
//		message.add(MarkdownMessage.getLinkText("OK", "dtmd://dingtalkclient/sendMessage?content=linkmessage"));
//		message.add(MarkdownMessage.getLinkText("查看详情", "https://www.baidu.com"));
//
//		SendResult result = this.client.send("https://oapi.dingtalk.com/robot/send?access_token=67eba905e3aef25a3ace347952063cb739ddb5654c050d26166cead10ccda2bd", message);
//		System.out.println(result);
			public static String buildMsgWithFair(WorkDuty workDuty,String user){
			String textMsg= "{\n" +
					"     \"msgtype\": \"markdown\",\n" +
					"     \"markdown\": {\"title\":\"值班提醒\",\n" +
					"\"text\":\"#### 值班提醒测试  \\n > 您好,"+user+",下周由你们轮值(๑╹◡╹)ﾉ  \\n\\n > 早班(08.00-17.00)：" + workDuty.getEarlyLot() + " \\n\\n > 晚班(10.00-20.00)：" + workDuty.getLateLot() + " \\n\\n  > 开始时间：" + workDuty.getStartTime() + " \\n\\n  > 结束时间：" + workDuty.getEndTime() + "  \\n\\n  > 时间段：" + workDuty.getWorkLot() + " \\n\\n > ######  [-----------点击查看详情](http://10.110.2.31/technical/system) \"\n" +
					"     },\n" +
					"    \"at\": {\n" +
					"        \"isAtAll\": true\n" +
					"    }\n" +
					" }";
			return textMsg;
		}


}

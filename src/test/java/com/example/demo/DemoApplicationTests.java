package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.dingtalk.chatbot.DingtalkChatbotClient;
import com.dingtalk.chatbot.SendResult;
import com.dingtalk.chatbot.message.MarkdownMessage;
import com.example.demo.model.Relation;
import com.example.demo.model.WorkDuty;
import com.example.demo.service.RelationService;
import com.example.demo.service.WorkDutyService;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
	private DingtalkChatbotClient client = new DingtalkChatbotClient();
	@Autowired
	private WorkDutyService workDutyService;
	@Autowired
	private RelationService relationService;

	@Test
	public void contextLoads() throws Exception{
		List<Relation> relations=relationService.findAll();
		for (int i = 0; i <relations.size() ; i++) {
			System.out.println(relationService.update(relations.get(i).getId()));
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
	}

}

package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.common.Initialization;
import com.example.demo.common.RequestUtil;
import com.example.demo.common.ResultBean;
import com.example.demo.common.zcy.Config;
import com.example.demo.common.zcy.RecordRevoke;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping(value = "/common")
@Slf4j
public class CommonController {

    @PostMapping(value = "record/revoke")
    public ResultBean recordRevoke(RecordRevoke recordRevoke){
        if(recordRevoke.getRevokeId()==0||StringUtils.isBlank(recordRevoke.getStatus())){
            log.info("revokeId or status is not null!");
            return new ResultBean("revokeId or status is not null!");
        }
        recordRevoke.setTimeUpdate(Initialization.formatTime());
        log.info(JSON.toJSONString(recordRevoke)+"="+RequestUtil.execRequest(JSON.toJSONString(recordRevoke),Config.RECORD_REVOKE_UPDATE));
        return new ResultBean(RequestUtil.execRequest(JSON.toJSONString(recordRevoke),Config.RECORD_REVOKE_UPDATE));

    }

    public static void changeInt(int x){
        x=55;
    }

    public static void main(String[] args) {
        int x = 8;
        changeInt(x);
        System.out.println(x);

    }
}

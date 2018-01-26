package com.example.demo.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayMarketingCardOpenModel;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.CardUserInfo;
import com.alipay.api.domain.MerchantCard;
import com.alipay.api.request.AlipayMarketingCardOpenRequest;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.response.AlipayMarketingCardOpenResponse;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.example.demo.common.ResultBean;
import com.example.demo.model.Notice;
import com.example.demo.model.Question;
import com.example.demo.service.NoticeService;
import com.example.demo.service.QuestionService;
import com.example.demo.service.impl.NoticeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "notice")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    @RequestMapping(value = "query")
    public ResultBean<List<Notice>> queryNotice() {
        return new ResultBean<>(noticeService.findAll());
    }

    /**
     * findone by id
     * @param id
     * @return
     */
    @RequestMapping(value = "query_by_id")
    public ResultBean<Notice> findById(@RequestParam(value = "id") String id) {
        return new ResultBean<>(noticeService.findById(id));
    }


    /**
     * del by id
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "del")
    public ResultBean<Boolean> del(String id) {
        return new ResultBean<>(noticeService.delNotice(id));
    }

    @RequestMapping(value = "count")
    public ResultBean findById() {
        return new ResultBean<>(noticeService.countAll());
    }


}

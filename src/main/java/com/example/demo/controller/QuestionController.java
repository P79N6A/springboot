package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alipay.api.*;
import com.alipay.api.domain.AlipayMarketingCardOpenModel;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.domain.CardUserInfo;
import com.alipay.api.domain.MerchantCard;
import com.alipay.api.request.AlipayMarketingCardOpenRequest;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayMarketingCardOpenResponse;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.example.demo.common.ConfigConstants;
import com.example.demo.common.ExecuteHelper;
import com.example.demo.common.ResultBean;
import com.example.demo.model.Question;
import com.example.demo.service.QuestionService;
import io.swagger.annotations.Api;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping(value = "question")
@Api(value = "question", description = "QA接口")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    /**
     * add qa
     *
     * @param question
     * @return
     */
    @RequestMapping(value = "add")
    public ResultBean<Boolean> add(Question question) {
        return new ResultBean<>(questionService.addQuestion(question));
    }

    /**
     * search by q
     *
     * @param question
     * @return
     */
    @RequestMapping(value = "search")
    public ResultBean<List<Question>> search(@RequestParam(value = "question") String question) {
        return new ResultBean<>(questionService.queryByQuestion(question));
    }

    /**
     * search and page
     *
     * @param page
     * @param rows
     * @param question
     * @return
     */
    @RequestMapping(value = "search_by_page")
    public ResultBean searchByPage(@RequestParam(value = "page") int page, @RequestParam(value = "rows") int rows, @RequestParam(value = "question") String question) {
        return new ResultBean<>(questionService.queryByQuestionAndPage(page, rows, question));
    }

    /**
     * find one by id
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "find_by_id")
    public ResultBean<Question> findById(@RequestParam(value = "id") String id) {
        return new ResultBean<>(questionService.findById(id));
    }

    /**
     * test
     *
     * @param auth_code
     * @param template_id
     * @param request_id
     * @return
     */
    @GetMapping(value = "open")
    public ResultBean open(HttpServletRequest request, @RequestParam(value = "auth_code", required = false, defaultValue = "none") String auth_code, @RequestParam(value = "template_id", required = false, defaultValue = "none") String template_id, @RequestParam(value = "request_id", required = false, defaultValue = "none") String request_id) throws AlipayApiException {
        Enumeration enu = request.getParameterNames();
        StringBuffer aa = new StringBuffer("urldata=");
        while (enu.hasMoreElements()) {
            String paraName = (String) enu.nextElement();
            //System.out.println(paraName+": "+request.getParameter(paraName));
            aa.append(paraName + ": " + request.getParameter(paraName));
        }
       /* Question question = new Question();
        question.setQuestion(auth_code);
        question.setAnswer(request_id);
        questionService.addQuestion(question);*/
        String appid2 = "2016101902241073";
        String privatekey2 = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANSe1tYHjTEBfgfl8I2b8F/hlUeBNUaNnVseEEwTISZlYCwjNl0X48W54y5zaZRpbLWPVVH6WYvMYH0DGZuBUJUXfwV4KN2iMSMR+/PcGoQaEH+3h6fEi8g+8G2Y6oxkqLINdeqyCDsjO6aiGjZdZVQVy6OGy6xINZOKq5ecFaitAgMBAAECgYByLeJ9CY0e9ggyQZ8OzOEm/ENoJNDxVHdeSSTDVbqFngcpbLdzArNEqXCAr2XRV1QTpCdTYLfZxSVDvPhxc95LV0cmXQJQgGIH7cFFJUDp8vlwCWnkQAV+yqnevTY9jXwMMhZFUilEyAlwSqZ2aiFlimcwFDXD5l2hRfQdCSq9wQJBAPgkSO1fACqx3gdKDH386+JTzfpLYl+h/z/UYXTnSEC/3nO1kRlLuJw0qcXCTRRanWgWGvxbNieBRPhxIYTBJRUCQQDbWpQ+Qu/DVOdiPFXa+udQzsFmsLcWQSABrgLdoyR09XxP36Hh1d/msWKjLdhvO0boga5XN5e/QEouRiLWqos5AkEAtQib3/nQQFXV21GNvZj5avyjKLk4wvaIJ0RF+akG0J5qp9ZOTrssq2HMfofb/j6B2j9OXtAYuUeZTvwSbS0QZQJAFOwX1bR2wA/aHhGZMtDZvWhrJAtY+0Ns9RwO4+sKsCk2GTxAaZUHzS5ANUZLLZje05CC+4iu7awJJ07DRexwaQJAO+nQVYpwAXKGr+IYk+X3xBEh4pStipSJRMFvK2DyQB/oWl22sA9PsFRbO4+POXbTQzhmodhklOErgn7ekKkQGw==";
        String publickey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
                appid2, privatekey2, "json", "utf-8", publickey);
        AlipaySystemOauthTokenRequest alipaySystemOauthTokenRequest = new AlipaySystemOauthTokenRequest();
        alipaySystemOauthTokenRequest.setCode(auth_code);
        alipaySystemOauthTokenRequest.setGrantType("authorization_code");
        AlipaySystemOauthTokenResponse alipaySystemOauthTokenResponse = alipayClient.execute(alipaySystemOauthTokenRequest);
        if (alipaySystemOauthTokenResponse.isSuccess()) {
            AlipayMarketingCardOpenRequest alipayMarketingCardOpenRequest = new AlipayMarketingCardOpenRequest();
            AlipayMarketingCardOpenModel alipayMarketingCardOpenModel = new AlipayMarketingCardOpenModel();
            alipayMarketingCardOpenModel.setCardTemplateId(template_id);
            alipayMarketingCardOpenModel.setOutSerialNo(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            CardUserInfo cardUserInfo = new CardUserInfo();
            cardUserInfo.setUserUniIdType("UID");
            cardUserInfo.setUserUniId(alipaySystemOauthTokenResponse.getUserId());
            alipayMarketingCardOpenModel.setCardUserInfo(cardUserInfo);
            MerchantCard merchantCard = new MerchantCard();
            merchantCard.setBalance("100");
            merchantCard.setPoint("100");
            merchantCard.setLevel("VIP2");
            merchantCard.setExternalCardNo(ConfigConstants.outBiz());
            merchantCard.setOpenDate(new Date());
            merchantCard.setValidDate("2025-01-01 23:59:59");
            alipayMarketingCardOpenModel.setCardExtInfo(merchantCard);
            alipayMarketingCardOpenRequest.setBizModel(alipayMarketingCardOpenModel);
            AlipayMarketingCardOpenResponse alipayMarketingCardOpenResponse = alipayClient.execute(alipayMarketingCardOpenRequest, alipaySystemOauthTokenResponse.getAccessToken());
            return new ResultBean<>(aa.append(alipayMarketingCardOpenResponse.getBody()).toString());
        }

        return new ResultBean<>(alipaySystemOauthTokenResponse.getBody());
    }

    /**
     * del by id
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "del")
    public ResultBean<Boolean> del(String id) {
        return new ResultBean<>(questionService.delQuestion(id));
    }

    @RequestMapping(value = "ask")
    public ResultBean loadData(HttpServletRequest request) throws Exception {
        if (StringUtils.isBlank(request.getParameter(ConfigConstants.METHOD))) {
            return new ResultBean().isFailure("request method is not null");
        }
        return new ResultBean<>(ExecuteHelper.buildRequest(request));
    }

}

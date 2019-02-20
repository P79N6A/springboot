package com.example.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.demo.common.Initialization;
import com.example.demo.common.RequestUtil;
import com.example.demo.common.zcy.Config;
import com.example.demo.dao.AccountRespository;
import com.example.demo.dao.ApplyRoleRespository;
import com.example.demo.dao.MenuRespository;
import com.example.demo.model.Account;
import com.example.demo.model.ApplyRole;
import com.example.demo.model.Menu;
import com.example.demo.service.ApplyRoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class ApplyRoleServiceImpl implements ApplyRoleService {
    @Autowired
    private ApplyRoleRespository applyRoleRespository;
    @Autowired
    private MenuRespository menuRespository;
    @Autowired
    private AccountRespository accountRespository;

    @Override
    public boolean createApply(ApplyRole applyRole) {
        if (StringUtils.isNotBlank(applyRole.getApplyUser()) && StringUtils.isNotBlank(applyRole.getApplyCode())) {
            applyRole.setGmt_create(Initialization.formatTime());
            List<Integer> roleCodeList =new ArrayList<>();
            String[] roleCode=applyRole.getApplyCode().split(",");
            for (int i = 0; i <roleCode.length; i++) {
                roleCodeList.add(Integer.valueOf(roleCode[i]));
            }
            List<Menu> menuList=menuRespository.findByRoleCodeWithin(roleCodeList,new Sort(new Sort.Order(Sort.Direction.ASC,"sort_code")));
            StringBuffer stringBuffer=new StringBuffer();
            for (int i = 0; i <menuList.size() ; i++) {
                stringBuffer.append(menuList.get(i).getNav_name()).append(",");
            }
            applyRole.setApplyList(stringBuffer.toString());
            applyRoleRespository.save(applyRole);

            String message=buildMsg(applyRole);
            try {
                RequestUtil.httpPost(Config.WEBHOOK_TOKEN,message);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    @Override
    public List<ApplyRole> findAll() {
        return applyRoleRespository.findAll(new Sort(new Sort.Order(Sort.Direction.DESC,"gmt_create")));}

    @Override
    public List<ApplyRole> findByStatus(int status) {
        return applyRoleRespository.findByStatus(status,new Sort(new Sort.Order(Sort.Direction.DESC,"gmt_create")));
    }

    @Override
    public boolean audit(String id, int status,String operator) {
        ApplyRole applyRole=applyRoleRespository.findOne(id);
        if(applyRole!=null){
            applyRole.setGmt_modify(Initialization.formatTime());
            applyRole.setOperator(operator);
            applyRole.setStatus(status);
            applyRoleRespository.save(applyRole);
            if(status==Initialization.ACCOUNT_STATUS){
                //配置权限
                Account account=accountRespository.findOne(applyRole.getUserId());
                Set<String> set=new HashSet<>();
                if(StringUtils.isNotBlank(account.getRoleLot())){
                    String[] oleCode=account.getRoleLot().split(",");
                    for (int i = 0; i <oleCode.length ; i++) {
                        set.add(oleCode[i]);
                    }
                }

                log.info(JSON.toJSONString(applyRole.getApplyCode()));
                String[] newCode=applyRole.getApplyCode().split(",");
                for (int j = 0; j <newCode.length ; j++) {
                    set.add(newCode[j]);
                }
                log.info(JSON.toJSONString(set));
                String currentCode=StringUtils.join(set.toArray(),",");
                log.info("result role code list:"+currentCode);
                account.setRoleLot(currentCode);
                account.setGmt_modify(Initialization.formatTime());
                accountRespository.save(account);
            }
            return true;
        }
        return false;
    }

    @Override
    public ApplyRole queryApply(String userId) {
        if(StringUtils.isNotBlank(userId)){
            return applyRoleRespository.findByUserId(userId,new Sort(new Sort.Order(Sort.Direction.DESC,"gmt_create")));
        }
        return null;
    }

    private static String buildMsg(ApplyRole applyRole){
        String message="{\n" +
                "     \"msgtype\": \"markdown\",\n" +
                "     \"markdown\": {\"title\":\"工作提示\",\n" +
                "\"text\":\"#### 审批提醒  \\n > @18814887123 Hi,金九,有新的权限申请需要审批!\\n\\n  > 申请人："+applyRole.getApplyUser()+"\\n\\n > 申请内容："+applyRole.getApplyList()+"\\n\\n > 申请理由："+applyRole.getReason()+"\\n\\n > ######  [-----立即审批](http://10.110.2.31/technical/apply_role) \"\n"+
                "     },\n" +
                "    \"at\": {\n" +
                "        \"atMobiles\": [\n" +
                "            \"18814887123\"\n" +
                "        ], \n" +
                "        \"isAtAll\": false\n" +
                "    }\n" +
                " }";
        return  message;
    }

}

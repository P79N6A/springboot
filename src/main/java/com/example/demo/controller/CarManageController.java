package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.common.RequestUtil;
import com.example.demo.common.ResultBean;
import com.example.demo.model.car.Institution;
import com.example.demo.model.car.InstitutionImportParams;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "car")
@Slf4j
public class CarManageController {

    @PostMapping(value = "init")
     public ResultBean stock(@RequestParam("file") MultipartFile file) {
        if(file.isEmpty()){
            log.info("文件不能为空");
            return new ResultBean("文件不能为空");
        }
        try {
            List<List<Object>> list = RequestUtil.getBankListByExcel(file.getInputStream(),file.getOriginalFilename());
//            List<InstitutionImportParams> institutions = new ArrayList<>();
//            InstitutionImportParams institutionImportParams;
//            for (int i = 0; i < list.size(); i++) {
//                institutionImportParams = new InstitutionImportParams();
//                if(StringUtils.isNotBlank(list.get(i).get(0).toString())&&StringUtils.isNotBlank(list.get(i).get(1).toString())){
//                    institutionImportParams.setLineNum(String.valueOf(i+1));
//                    institutionImportParams.setOrgCode(list.get(i).get(0).toString());
//                    institutionImportParams.setOrgName(list.get(i).get(1).toString());
//                    institutionImportParams.setCarManager(list.get(i).get(2).toString());
//                    institutionImportParams.setPhone(list.get(i).get(3).toString());
//                    institutions.add(institutionImportParams);
//                }
//            }
//            log.info(JSON.toJSONString(institutions));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}

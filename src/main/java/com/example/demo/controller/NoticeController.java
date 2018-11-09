package com.example.demo.controller;

import com.example.demo.common.ResultBean;
import com.example.demo.model.Notice;
import com.example.demo.service.NoticeService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "notice")
@Api(value = "notice", description = "后台通告接口")
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

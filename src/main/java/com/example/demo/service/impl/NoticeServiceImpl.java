package com.example.demo.service.impl;

import com.example.demo.dao.NoticeRespository;
import com.example.demo.model.Notice;
import com.example.demo.service.NoticeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wb-lwc235565 on 2018/1/17.
 */
@Service
public class NoticeServiceImpl implements NoticeService{
    @Autowired
    private NoticeRespository noticeRespository;

    @Override
    public List<Notice> findAll() {
        return noticeRespository.findAll(new Sort(new Sort.Order(Sort.Direction.DESC, "id")) );
    }

    @Override
    public Notice findById(String id) {
        return noticeRespository.findOne(id);
    }

    @Override
    public boolean delNotice(String id) {
        if(StringUtils.isNotBlank(id)){
            noticeRespository.delete(id);
            return  true;
        }
        return false;
    }

    @Override
    public long countAll() {
        return noticeRespository.count();
    }
}

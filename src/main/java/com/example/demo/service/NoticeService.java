package com.example.demo.service;

import com.example.demo.model.Notice;

import java.util.List;

public interface NoticeService {

    List<Notice> findAll();

    Notice findById(String id);

    boolean delNotice(String id);

    long countAll();

}
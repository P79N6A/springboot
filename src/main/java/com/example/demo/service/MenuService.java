package com.example.demo.service;

import com.example.demo.model.Menu;

import java.util.List;

public interface MenuService {
    boolean addMenu(Menu menu);
    List<Menu> findAll();
    Menu findById(String id);
    boolean del(String id);
    List<Menu> findMenuByRole(int role);
    boolean updateMenu(Menu menu);
    List<Menu> queryMenuListWithCode(String id);
}

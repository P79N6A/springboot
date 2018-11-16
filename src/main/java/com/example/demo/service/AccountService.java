package com.example.demo.service;

import com.example.demo.model.Account;

import java.util.List;

public interface AccountService {
    boolean addAccount(Account account);
    List<Account> findAll();
    Account findById(String id);
    boolean del(String id);
    Account findByAccountAndPassword(String account,String password);
    boolean updateAccount(Account account);
    List<Account> showMeetingList();
    List<Account> getMeetingSequence(int meeting,int review);
    boolean updateSequence(String id,int review);
}

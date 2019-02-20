package com.example.demo.dao;

import com.example.demo.model.Account;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRespository extends MongoRepository<Account,String> {

    Account findByAccountAndPasswordAndStatus(String account,String password,int status);
    List<Account> findByMeeting(int meeting, Sort sort);
    List<Account> findByMeetingAndReview(int meeting,int review,Sort sort);
    List<Account> findByLockAndMeeting(int lock,int meeting,Sort sort);
    Account findByAccountAndRealNameAndStatus(String account,String realName,int status);
}

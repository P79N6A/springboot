package com.example.demo.dao;

import com.example.demo.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRespository extends MongoRepository<Account,String> {

    Account findByAccountAndPasswordAndStatus(String account,String password,int status);
}

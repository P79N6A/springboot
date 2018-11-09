package com.example.demo.dao;


import com.example.demo.model.Menu;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRespository extends MongoRepository<Menu,String> {

  List<Menu>  findByRoleNot(int role, Sort sort);
  List<Menu>  findByRole(int role,Sort sort);
}

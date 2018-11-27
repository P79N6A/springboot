package com.example.demo.dao;


import com.example.demo.model.Menu;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRespository extends MongoRepository<Menu,String> {

  List<Menu>  findByRoleNot(int role, Sort sort);
  List<Menu>  findByRole(int role,Sort sort);
  Menu  findByRoleCode(int roleCode);
  @Query(value = "{'roleCode':{'$in':?0} }")
  List<Menu> findByRoleCodeWithin(List<Integer> roleCode,Sort sort);
  @Query(value = "{'status':?0}",fields = "{'roleCode':1}")
  List<Menu> findByStatus(int status);
}

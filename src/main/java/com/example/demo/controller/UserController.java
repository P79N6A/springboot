package com.example.demo.controller;

import com.example.demo.common.ResultBean;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * add user
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "add")
    public ResultBean<Boolean> add(User user) {
        return new ResultBean<>(userService.addUser(user));
    }


    /**
     * find one by id
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "find_by_id")
    public ResultBean<User> findById(@RequestParam(value = "id") String id) {
        return new ResultBean<>(userService.findById(id));
    }

    @RequestMapping(value = "findAll")
    public ResultBean<List<User>> findAll() {
        return new ResultBean<>(userService.findAll());
    }

    @RequestMapping(value = "del")
    public  ResultBean<Boolean> del(String id) {
        return new ResultBean<>(userService.delUser(id));
    }

}

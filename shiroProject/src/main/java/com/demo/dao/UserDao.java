package com.demo.dao;


import java.util.List;

import org.springframework.stereotype.Component;

import com.demo.entity.User;

public interface UserDao {

	User getPasswordByUserName(String userName);

    List<String> getRolesByUserName(String userName);

	List<String> getPermissionByUserName(String userName);

}

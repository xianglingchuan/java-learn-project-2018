package com.demo.dao;


import com.demo.entity.User;

import java.util.List;

/**
 *
 * UserDao
 * 使用自定义数据表管理用户信息
 *
 */
public interface UserDao {

	User getPasswordByUserName(String userName);

    List<String> getRolesByUserName(String userName);

	List<String> getPermissionByUserName(String userName);

}

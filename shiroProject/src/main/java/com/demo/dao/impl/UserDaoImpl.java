package com.demo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;


import com.demo.dao.UserDao;
import com.demo.entity.User;

@Component
public class UserDaoImpl implements UserDao {
	
	@Resource
	private JdbcTemplate jdbcTemplate;

	@Override
	public User getPasswordByUserName(String userName) {
		String sql = "select user_name as username, password from test_users where user_name = ?";
		List<User> list = jdbcTemplate.query(sql, new String[]{userName}, new RowMapper<User>(){
			@Override
			public User mapRow(ResultSet resultSet, int i) throws SQLException {
				User user = new User();
				user.setUsername(resultSet.getString("username"));
				user.setPassword(resultSet.getString("password"));
				return user;
			}	
		});
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		return list.get(0);
	}

	@Override
	public List<String> getRolesByUserName(String userName) {
		String sql = "select roleName from test_users_roles where user_name = ?";

		String sql2 = "select * from users where username = ?";

		List<String> list = jdbcTemplate.query(sql, new String[]{userName}, new RowMapper<String>(){
			@Override
			public String mapRow(ResultSet resultSet, int i) throws SQLException {
				return resultSet.getString("roleName");
			}	
		});
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		return list;		
	}

	@Override
	public List<String> getPermissionByUserName(String userName) {
		String sql = "select A.roles_permissions from test_roles_permissions as A, test_users_roles as B "
				+ "where A.roleName= B.roleName AND B.user_name = ?";
		List<String> list = jdbcTemplate.query(sql, new String[]{userName}, new RowMapper<String>(){
			@Override
			public String mapRow(ResultSet resultSet, int i) throws SQLException {
				return resultSet.getString("roles_permissions");
			}	
		});
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		return list;
	}

}

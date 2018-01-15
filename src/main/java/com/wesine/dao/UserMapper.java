package com.wesine.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.wesine.model.User;

@Mapper
public interface UserMapper {

	
	@Select("select * from users where id=#{id}")
	User findUserById(@Param("id")String id);
	
	
	@Select("select * from users")
	List<User> getAllUsers();

	int insert(Map<String, Object> conditionMap);

	@Select("select count(id) from users where id=#{usrID}")
	int count(String usrID);

	@Select("select count(counterId) from user_counter where userId=#{usrID}")
	int isRelateCounter(String usrID);
		
		
		
	
	
}

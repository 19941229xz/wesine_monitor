package com.wesine.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CounterMapper {

	
	
	@Select("select id,name, shopId ,typeId from counters where shopId=#{shopID}")
	List<Map<String,Object>> getCounterListByShopId(String shopID);
	
	//查询防损员关联的款台
	@Select("select userId,counterId from user_counter where userId=#{userId}")
	List<Map<String,Object>> getCounterUserByUserId(String userId);
	
	@Select("select count(*) from user_counter where userId=#{userId}")
	int countUserCounter(String userId);


	int insertUserCounter(List<Map<String, Object>> listMap);

	@Delete("delete from user_counter where userId=#{userID}")
	int deleteOldRelate(String userID);
	
	
}

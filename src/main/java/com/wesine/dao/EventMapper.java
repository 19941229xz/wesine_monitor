package com.wesine.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface EventMapper {

	
	
	int insertEventForMany(List<Map<String, Object>> listMap);

	List<Map<String,Object>> selectEventThisMonth(Map<String, Object> conditionMap);

	List<Map<String,Object>> selectEventThisWeek(Map<String, Object> conditionMap);

	List<Map<String,Object>> selectEventThisDay(Map<String, Object> conditionMap);

	List<Map<String,Object>> selectEventByStatus(Map<String, Object> conditionMap);

	List<Map<String,Object>> selectEventThisMonth2(Map<String, Object> conditionMap);

	List<Map<String,Object>> selectEventThisWeek2(Map<String, Object> conditionMap);

	List<Map<String,Object>> selectEventThisDay2(Map<String, Object> conditionMap);

	List<Map<String,Object>> selectEventByStatus2(Map<String, Object> conditionMap);

	List<Map<String,Object>> selectEventThisWeek3(Map<String, Object> conditionMap);

	List<Map<String,Object>> selectEventThisMonth3(Map<String, Object> conditionMap);

	List<Map<String,Object>> selectEventThisDay3(Map<String, Object> conditionMap);

	List<Map<String,Object>> selectEventByStatus3(Map<String, Object> conditionMap);

	
	int updateById(Map<String, Object> conditionMap);

	
	
	
	
}

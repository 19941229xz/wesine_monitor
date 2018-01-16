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

	List<Map<String, Object>> selectEventThisMonth(Map<String, Object> conditionMap);

	List<Map<String, Object>> selectEventThisWeek(Map<String, Object> conditionMap);

	List<Map<String, Object>> selectEventThisDay(Map<String, Object> conditionMap);

	List<Map<String, Object>> selectEventByStatus(Map<String, Object> conditionMap);

	List<Map<String, Object>> selectEventThisMonth2(Map<String, Object> conditionMap);

	List<Map<String, Object>> selectEventThisWeek2(Map<String, Object> conditionMap);

	List<Map<String, Object>> selectEventThisDay2(Map<String, Object> conditionMap);

	List<Map<String, Object>> selectEventByStatus2(Map<String, Object> conditionMap);

	List<Map<String, Object>> selectEventThisWeek3(Map<String, Object> conditionMap);

	List<Map<String, Object>> selectEventThisMonth3(Map<String, Object> conditionMap);

	List<Map<String, Object>> selectEventThisDay3(Map<String, Object> conditionMap);

	List<Map<String, Object>> selectEventByStatus3(Map<String, Object> conditionMap);

	int updateById(Map<String, Object> conditionMap);
	
	int countEventThisDayForFsjl(Map<String, Object> conditionMap);//for 防损经理 

	int countEventThisDay(Map<String, Object> conditionMap);//for 区域经理

	int countEventThisDay2(Map<String, Object> conditionMap);//for 防损经理
	
	int countEventThisDay3(Map<String, Object> conditionMap);//for  防损员

	int countEventThisHour(Map<String, Object> conditionMap);
	
	@Select("select max(allEventNum) from eventAnalysi")
	int getAllEventNum();//查询最大事件数
	
	@Insert("insert into eventAnalysi (allEventNum,unNormalEventNum,incorrectPercent)values(#{i},#{j},#{parseDouble})")
	void insertNewEventAnlysidata(Map<String, Object> eventMapForAnalysi);
	
	@Select("select max(unNormalEventNum) from eventAnalysi")
	int getAllEventNumIncorrect();//查询最大不支持事件数

	@Select("select count(allEventNum) from eventAnalysi")
	int countEvenPercent();
	@Select("select allEventNum,incorrectPercent from eventAnalysi")
	List<Map<String, Object>> selectEvenPercentList();
	
	
	@Insert("insert into eventTAT (event_id,startTime,actionTime,endTime,shop_id)values(#{id},#{startTime},#{actionTime},#{endTime},#{shop_id})")
	int insertEventTAT(Map<String,Object> map);
	
	
	int updateEventTAT(Map<String,Object> map);
	
	
	@Select("select * from events where id=#{event_id}")
	List<Map<String,Object>> getEventById(String event_id);
	
	@Select("select * from eventTAT where event_id=#{event_id}")
	List<Map<String,Object>> getEventTATById(String event_id);
	
	
	

}

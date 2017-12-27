package com.wesine.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface BillMapper {

	
	
	@Insert("insert into bills(id,transId,shopId,ts,counterId,scriptVer,productId,price,quantity,amount,cashierId,customerId,eventFlag,eventId)values(#{id},#{transId},#{shopId},#{ts},#{counterId},#{scriptVer},#{productId},#{price},#{quantity},#{amount},#{cashierId},#{customerId},#{eventFlag},#{eventId})")
	int insertBills(Map<String, Object> billMap);
	
	int insertBillsForMany(List<Map<String,Object>> billList);
	
	
	
}

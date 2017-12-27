package com.wesine.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TransactionMapper {

	 int insert(Map<String, Object> dataMap) ;
	
	
	

}

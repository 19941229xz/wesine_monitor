package com.wesine.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FsyMapper {

	@Insert("insert into fsyInfo (userId,shopId,roleId,userName)values(#{usrID},#{shopId},#{roleId},#{usrName})")
	int insertFsy(Map<String,Object> map);//新增防损员信息
	
	
}

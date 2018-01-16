package com.wesine.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FsyMapper {

	@Insert("insert into fsyInfo (userId,shopId,roleId,userName)values(#{usrID},#{shopId},#{roleId},#{usrName})")
	int insertFsy(Map<String,Object> map);//新增防损员信息
	
	
	int updateFsy(Map<String,Object> map);//修改防损员信息
	
	@Select("select * from fsyInfo where userId=#{userId}")
	List<Map<String,Object>> getFsyById(String userId);
	
	
}

package com.wesine.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CashierMapper {

	@Select("select count(c_id) from cashiers where c_id=#{cid}")
	int countCashierID(String cid);
	
	@Select("select s_num from cashiers where c_id=#{cid}")
	int getSnumByCashierID(String cid);//根据收银员id 查询 收银次数
	
	@Select("select e_num from cashiers where c_id=#{cid}")
	int getEnumByCashierID(String cid);//根据收银员id查询 收银出错次数
	
	@Update("update cashiers set s_num=#{s_num},e_num=#{e_num} where c_id=#{c_id}")
	int updateByCashierID(Map<String,Object> map);
	
	@Insert("insert into cashiers (id,c_id,shop_id,shop_name)values(#{id},#{c_id},#{shop_id},#{shop_name})")
	int insertCashier(Map<String,Object> map);

	List<Map<String, Object>> getAllCashiers(Map<String, Object> params);
	
	
}

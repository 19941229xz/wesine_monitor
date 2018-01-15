package com.wesine.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ShopMapper {
	
	@Select("select * from shops where areaId=#{areaID}")
	List<Map<String,Object>> getShopListByAreaId(String areaID);
	
	@Select("select name from shops where id=#{id}")
	String getShopNameByID(String id);
	
	@Insert("insert into shops (id, areaId,name)values(#{shop_id},#{areaId},#{shop_name})")
	int insertShop(Map<String,Object> map);
	

}

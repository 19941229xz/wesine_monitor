package com.wesine.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wesine.dao.CounterMapper;

@Controller
public class CounterController {

	private Map<String, Object> resultMap;
	
	private Map<String, Object> conditionMap;
	
	private List<Map<String, Object>> listMap;
	
	@Autowired
	CounterMapper counterMapper;
	
	@RequestMapping(value="getCounterListByShopId/{shopID}/{userID}")
	@ResponseBody
	public Map<String,Object> getCounterListByShopId(@PathVariable String shopID,@PathVariable String userID){
		
		resultMap = new HashMap<String, Object>();
		listMap = new ArrayList<Map<String, Object>>();
		
		resultMap.put("status", "0");// 0 success 1失败
		resultMap.put("tip", "成功！");
		
		
		//根据商店id获取款台列表 
		resultMap.put("counterList", counterMapper.getCounterListByShopId(shopID)!=null?counterMapper.getCounterListByShopId(shopID):null);
		//根据用户id获取用户关联款台信息
		resultMap.put("userCounter", counterMapper.getCounterUserByUserId(userID)!=null?counterMapper.getCounterUserByUserId(userID):null);
		
		return resultMap;
		
		
	}
	
	//saveUserCounter
	@RequestMapping(value="saveUserCounter/{userID}")
	@ResponseBody
	public Map<String,Object> saveUserCounter(@RequestParam("counterId")String counterId,@PathVariable String userID){
		
		resultMap = new HashMap<String, Object>();
		listMap = new ArrayList<Map<String, Object>>();
		
		resultMap.put("status", 0);// 0 success 1失败
		resultMap.put("tip", "成功！");
		
		String[] cidArr=counterId.split(",");
		
		for (int i = 0; i < cidArr.length; i++) {
			conditionMap = new HashMap<String, Object>();
			conditionMap.put("userID", userID);
			conditionMap.put("counterId", cidArr[i]);
			listMap.add(conditionMap);
		}
		
		int num2=counterMapper.deleteOldRelate(userID);
		
		int num=counterMapper.insertUserCounter(listMap);
		
		if(num2>=1&&num>=1){
			resultMap.put("status", "0");// 0 success 1失败
			resultMap.put("tip", "成功！");
		}else{
			resultMap.put("status", "1");// 0 success 1失败
			resultMap.put("tip", "失败！");
		}
		
		
		return resultMap;
		
		
	}
	
	
}

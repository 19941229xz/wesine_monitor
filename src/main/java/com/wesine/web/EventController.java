package com.wesine.web;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wesine.dao.EventMapper;
import com.wesine.dao.UserMapper;

@Controller
public class EventController {

	private Map<String, Object> resultMap;

	private Map<String, Object> eventData;
	
	private Map<String,Object> conditionMap;

	@Autowired
	EventMapper eventMapper;
	@Autowired
	UserMapper userMapper;

	@RequestMapping(value = "/getEventData")//,method = RequestMethod.POST
	@ResponseBody
	public Map<String, Object> getEventData(String areaID, String usrName, String usrID, String shopID, String roleId) {
		resultMap = new HashMap<String, Object>();
		eventData = new HashMap<String, Object>();
		conditionMap = new HashMap<String, Object>();

		resultMap.put("status", "0");// 0 success 1 fail 2防损员未关联款台
		resultMap.put("tip", "");


		switch (roleId) {
		case "1":
			
			if(userMapper.isRelateCounter(usrID)<1){
				
				resultMap.put("status", "2");// 0 success 1 fail 2防损员未关联款台
				resultMap.put("tip", "未关联款台！");
				
				break;
			}
			
			conditionMap.put("usrID", usrID);
			resultMap.put("eventsForThisMonth", eventMapper.selectEventThisMonth3(conditionMap));
			resultMap.put("eventsForThisWeek", eventMapper.selectEventThisWeek3(conditionMap));
			resultMap.put("eventsForThisDay", eventMapper.selectEventThisDay3(conditionMap));
			//status  0 未处理  1暂存  2完成
			conditionMap.put("status", "未处理");
			resultMap.put("eventsStatus0", eventMapper.selectEventByStatus3(conditionMap));
			conditionMap.put("status", "暂存");
			resultMap.put("eventsStatus1", eventMapper.selectEventByStatus3(conditionMap));
			conditionMap.put("status", "完成");
			resultMap.put("eventsStatus2", eventMapper.selectEventByStatus3(conditionMap));
			
			break;
		case "2":
			// 判读 这是防损经理
			conditionMap.put("shopID", shopID);
			resultMap.put("eventsForThisMonth", eventMapper.selectEventThisMonth2(conditionMap));
			resultMap.put("eventsForThisWeek", eventMapper.selectEventThisWeek2(conditionMap));
			resultMap.put("eventsForThisDay", eventMapper.selectEventThisDay2(conditionMap));
			//status  0 未处理  1暂存  2完成
			conditionMap.put("status", "未处理");
			resultMap.put("eventsStatus0", eventMapper.selectEventByStatus2(conditionMap));
			conditionMap.put("status", "暂存");
			resultMap.put("eventsStatus1", eventMapper.selectEventByStatus2(conditionMap));
			conditionMap.put("status", "完成");
			resultMap.put("eventsStatus2", eventMapper.selectEventByStatus2(conditionMap));

			break;
		case "3":
			// 判读 这是区域经理
			conditionMap.put("areaID", areaID);
			resultMap.put("eventsForThisMonth", eventMapper.selectEventThisMonth(conditionMap));
			resultMap.put("eventsForThisWeek", eventMapper.selectEventThisWeek(conditionMap));
			resultMap.put("eventsForThisDay", eventMapper.selectEventThisDay(conditionMap));
			//status  0 未处理  1暂存  2完成
			conditionMap.put("status", "未处理");
			resultMap.put("eventsStatus0", eventMapper.selectEventByStatus(conditionMap));
			conditionMap.put("status", "暂存");
			resultMap.put("eventsStatus1", eventMapper.selectEventByStatus(conditionMap));
			conditionMap.put("status", "完成");
			resultMap.put("eventsStatus2", eventMapper.selectEventByStatus(conditionMap));
			break;

		default:
			break;
		}

		return resultMap;
	}
	
	
	@RequestMapping(value="saveEditResult",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> saveEditResult(String id,String comment,String productName,String money,String result,String status){
		resultMap = new HashMap<String, Object>();
		
		conditionMap = new HashMap<String, Object>();
		
		conditionMap.put("id", id);
		conditionMap.put("comment", comment==null?"":comment);
		conditionMap.put("productName", productName==null?"":productName);
		//System.out.println(money);
		conditionMap.put("money", money==null||money.equals("")?0:new BigDecimal(money));
		conditionMap.put("result", result);
		conditionMap.put("status", status);
		
		
		int num=eventMapper.updateById(conditionMap);
		
		if(num>=1){
			resultMap.put("status", 0);
			resultMap.put("tip", "成功");
		}else{
			resultMap.put("status", 1);
			resultMap.put("tip", "失败");
		}
		
		
		return resultMap;
		
		
		
	}
	
	

}

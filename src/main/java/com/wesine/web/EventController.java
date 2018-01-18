package com.wesine.web;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wesine.dao.EventMapper;
import com.wesine.dao.FsyMapper;
import com.wesine.dao.UserMapper;
import com.wesine.service.EventService;
import com.wesine.util.TimeUtil;

@Controller
public class EventController {

	private Map<String, Object> resultMap;

	private Map<String, Object> eventData;
	
	private Map<String,Object> conditionMap;

	@Autowired
	EventMapper eventMapper;
	@Autowired
	UserMapper userMapper;
	@Autowired
	FsyMapper fsyMapper;
	
	@Autowired
	EventService eventService;
	

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
			//resultMap.put("eventsForThisMonth", eventMapper.selectEventThisMonth3(conditionMap));
			resultMap.put("eventsForThisWeek", eventService.getEvenArrThisWeek3(usrID));//
			//resultMap.put("eventsForThisDay", eventMapper.selectEventThisDay3(conditionMap));
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
			resultMap.put("eventsForThisMonth", eventService.selectEventThisMonthFsjl(shopID));
			//resultMap.put("eventsForThisWeek", eventService.getEvenArrThisWeek2(areaID));//
			//resultMap.put("eventsForThisDay", eventMapper.selectEventThisDay2(conditionMap));
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
			resultMap.put("eventsForThisMonth", eventService.getEvenArrThisMonth(areaID));
			resultMap.put("eventsForThisWeek", eventService.getEvenArrThisWeek(areaID));//
			resultMap.put("eventsForThisDay", eventService.getEvenArrThisDay(areaID));
			//差错图数据
			resultMap.put("eventPercentGraphData", eventService.getEvenPercentArr(areaID));
			
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
	
	
	@RequestMapping(value="/createActionTime")
	@ResponseBody
	public Map<String,Object> createActionTime(@RequestParam Map<String,Object> params){//包含参数 shop_id process_person_id event_id 
		
		//判断该事件  是否有人处理  和是否存在eventTAT数据
		List<Map<String,Object>> eventList=eventMapper.getEventById(params.get("event_id")+"");
		List<Map<String,Object>> eventTATList=eventMapper.getEventTATById(params.get("event_id")+"");
		
		boolean isNewTAT=false;
		
		if(eventTATList.size()==0){//不存在事件TAT的情况
			//先生成TAT数据（正常情况下 TAT数据是绝对存在的）
			params.put("startTime",TimeUtil.dateToStamp((Date)(eventList.get(0).get("date"))));
			params.put("actionTime", 0);
			params.put("endTime", 0);
			params.put("id", params.get("event_id"));
			eventMapper.insertEventTAT(params);
			System.out.println("不存在事件"+params.get("event_id")+"的TAT数据！已经重新生成！");
			isNewTAT=true;
		}
		
		
		resultMap=new HashMap<String,Object>();
		
		resultMap.put("status", "1");//1  代表已经被接管  0  未被接管 且重新生成actionTime
		
		if(isNewTAT||Long.parseLong(eventTATList.get(0).get("actionTime")+"")==0){//为被防损员接管 修改actionTime
			
			long actionTime=System.currentTimeMillis();
			
			params.put("actionTime", actionTime);
			
			resultMap.put("actionTime", TimeUtil.stampToDateString(actionTime+""));
			
			resultMap.put("status",eventMapper.updateEventTAT(params)!=0?"0":"1");
		}
		
		return resultMap;
		
	}
	
	
	
	@RequestMapping(value="createEndTime")
	@ResponseBody
	public Map<String,Object> createEndTime(@RequestParam Map<String,Object> params){
		resultMap=new HashMap<String,Object>();
		
		resultMap.put("status", "1");//默认失败
		
		long time=0;
		
		List<Map<String ,Object>> listEventTAT=eventMapper.getEventTATById(params.get("event_id")+"");
		
		if(listEventTAT.size()!=0&&Long.parseLong(listEventTAT.get(0).get("actionTime")+"")!=0){
			//如果事件tat数据存在  且actiontime不为0  说明这是一个可以提交的事件  进行update操作
			long start=Long.parseLong(listEventTAT.get(0).get("actionTime")+"");//以开始执行微时间起点
			long end=System.currentTimeMillis();
			
			time=end-start;
			
			resultMap.put("eventTime", TimeUtil.longTime2StringTime(time));
			
			params.put("endTime", end);
			
			resultMap.put("status", eventMapper.updateEventTAT(params)!=0?"0":"1");
		}else{
			//重新生成TAT数据  正常情况下不会出现这种情况
			params.put("startTime",System.currentTimeMillis());
			params.put("actionTime", 0);
			params.put("endTime", 0);
			eventMapper.insertEventTAT(params);
		}
		
		
		
		
		if((resultMap.get("status")+"").equals("0")){//成功  该防损员处理事件次数加1
			List<Map<String,Object>> fsyList=fsyMapper.getFsyById(params.get("usrID")+"");
			
			if(fsyList.size()==0){//不存在  该用户的fsy信息  需要重新生成
				fsyMapper.insertFsy(params);
			}else{//存在的情况
				long num=Long.parseLong(fsyList.get(0).get("actionEventNum")+"");
				params.put("actionEventNum", ++num);//处理事件数加1 
				
				long num2=Long.parseLong(fsyList.get(0).get("avgActionTime")+"");
				
				long newAvgTime=((num*num2)+time)/(num+1);//重新计算平均处理时间
				
				params.put("avgActionTime", newAvgTime);
				
				fsyMapper.updateFsy(params);
			}
			
			//计算该事件处理的时间  返回给前台  后台重新刷新处理事件平均事件
			
			
		}
		
		
		
		return resultMap;
		
		
	}
	
	
	

}

package com.wesine.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wesine.dao.EventMapper;

@Service
public class EventService {

	@Autowired
	private EventMapper eventMapper;

	private Map<String, Object> conditionMap;
	
	//按天统计24小时的时间次数  区域经理
	public int[][] getEvenPercentArr(String areaID) {
		
		List<Map<String,Object>> EvenPercentList=  eventMapper.selectEvenPercentList();
		
		int arrOutLength=eventMapper.countEvenPercent();//获取列表总数
		
		int [] [] temp;
		temp=new int[arrOutLength][];
		
		for (int i = 0; i < temp.length; i++) {
			temp[i]=new int[2];
			temp[i][0]=(int)(EvenPercentList.get(i).get("allEventNum"));
			temp[i][1]=(int)(EvenPercentList.get(i).get("incorrectPercent"));
		}
		return temp;
	}
	
	//按月统计每天的事件次数  防损经理
	public int[] selectEventThisMonthFsjl(String shopId){
		//创建一个map村放查询所需条件
		conditionMap = new HashMap<String, Object>();
		conditionMap.put("shopId", shopId);
		
		// 获取当前月份
		SimpleDateFormat simdf = new SimpleDateFormat("yyyy-MM");
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		
		//int month = cal.get(Calendar.MONTH )+1;
		
		int day=cal.getActualMaximum(Calendar.DATE);
		
		int[] arr=new int[day];
		
		
		for (int i = 0; i < arr.length; i++) {
			String date = simdf.format(cal.getTime());
			date=date+"-"+(i+1);
			conditionMap.put("weekDate", date);
			
			arr[i]=eventMapper.countEventThisDayForFsjl(conditionMap);
		}


		
		
		return arr;
	}
	
	//按月统计每天的事件次数  区域经理
		public int[] getEvenArrThisMonth(String areaID) {
			//创建一个map村放查询所需条件
			conditionMap = new HashMap<String, Object>();
			conditionMap.put("areaID", areaID);
			
			// 获取当前月份
			SimpleDateFormat simdf = new SimpleDateFormat("yyyy-MM");
			Calendar cal = Calendar.getInstance(Locale.CHINA);
			
			//int month = cal.get(Calendar.MONTH )+1;
			
			int day=cal.getActualMaximum(Calendar.DATE);
			
			int[] arr=new int[day];
			
			
			for (int i = 0; i < arr.length; i++) {
				String date = simdf.format(cal.getTime());
				date=date+"-"+(i+1);
				conditionMap.put("weekDate", date);
				
				arr[i]=eventMapper.countEventThisDay(conditionMap);
			}


			
			
			return arr;
	
		}
	
	//按天统计24小时的时间次数  区域经理
	public int[] getEvenArrThisDay(String areaID) {
		//创建一个map村放查询所需条件
		conditionMap = new HashMap<String, Object>();
		conditionMap.put("areaID", areaID);
		

		int[] arr = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0 };// 存放一天24小时事件次数

		// 获取当前星期
		SimpleDateFormat simdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();

		String date = simdf.format(cal.getTime());
		
		for (int i = 0; i < arr.length; i++) {
			String hour=date+" "+i+":00:00";
			
			conditionMap.put("weekHour", hour);
			
			arr[i]=eventMapper.countEventThisHour(conditionMap);
		}
		return arr;
	}

	public int[] getEvenArrThisWeek(String areaID) {

		int[] arr = new int[] { 0, 0, 0, 0, 0, 0, 0 };// 存放周一到周日的事件次数

		// 获取当前星期
		SimpleDateFormat simdf = new SimpleDateFormat("yyyy/MM/dd");
		Calendar cal = Calendar.getInstance();
		// 获取当前周 周一的日期
		cal.set(cal.DAY_OF_WEEK, cal.MONDAY);
		String week1 = simdf.format(cal.getTime());
		// 获取当前周 周2的日期
		cal.set(Calendar.DATE, cal.get(cal.DATE) + 1);
		String week2 = simdf.format(cal.getTime());
		// 获取当前周 周3的日期
		cal.set(Calendar.DATE, cal.get(cal.DATE) + 1);
		String week3 = simdf.format(cal.getTime());
		// 获取当前周 周4的日期
		cal.set(Calendar.DATE, cal.get(cal.DATE) + 1);
		String week4 = simdf.format(cal.getTime());
		// 获取当前周 周5的日期
		cal.set(Calendar.DATE, cal.get(cal.DATE) + 1);
		String week5 = simdf.format(cal.getTime());
		// 获取当前周 周6的日期
		cal.set(Calendar.DATE, cal.get(cal.DATE) + 1);
		String week6 = simdf.format(cal.getTime());
		// 获取当前周 周日的日期
		cal.set(Calendar.DATE, cal.get(cal.DATE) + 1);
		String week7 = simdf.format(cal.getTime());

		arr[0] = getEventNumByWeekDay(week1, areaID);
		arr[1] = getEventNumByWeekDay(week2, areaID);
		arr[2] = getEventNumByWeekDay(week3, areaID);
		arr[3] = getEventNumByWeekDay(week4, areaID);
		arr[4] = getEventNumByWeekDay(week5, areaID);
		arr[5] = getEventNumByWeekDay(week6, areaID);
		arr[6] = getEventNumByWeekDay(week7, areaID);

		// conditionMap.put("weekDate", "2018/01/01");
		// conditionMap.put("areaID", areaID);
		// eventMapper.countEventThisDay(conditionMap);

		// System.out.println(eventMapper.countEventThisDay(conditionMap));

		return arr;
	}
	@SuppressWarnings("all")
	public int[] getEvenArrThisWeek2(String shopID) {// 2 防损经理 需传入shopId

		int[] arr = new int[] { 0, 0, 0, 0, 0, 0, 0 };// 存放周一到周日的事件次数

		// 获取当前星期
		SimpleDateFormat simdf = new SimpleDateFormat("yyyy/MM/dd");
		Calendar cal = Calendar.getInstance();
		// 获取当前周 周一的日期
		cal.set(cal.DAY_OF_WEEK, cal.MONDAY);
		String week1 = simdf.format(cal.getTime());
		//System.out.println("当前时间所在周周一日期：" + week1);
		// 获取当前周 周2的日期
		cal.set(Calendar.DATE, cal.get(cal.DATE) + 1);
		String week2 = simdf.format(cal.getTime());
//		System.out.println("当前时间所在周周日日期：" + week2);
		// 获取当前周 周3的日期
		cal.set(Calendar.DATE, cal.get(cal.DATE) + 1);
		String week3 = simdf.format(cal.getTime());
//		System.out.println("当前时间所在周周日日期：" + week3);
		// 获取当前周 周4的日期
		cal.set(Calendar.DATE, cal.get(cal.DATE) + 1);
		String week4 = simdf.format(cal.getTime());
//		System.out.println("当前时间所在周周日日期：" + week4);
		// 获取当前周 周5的日期
		cal.set(Calendar.DATE, cal.get(cal.DATE) + 1);
		String week5 = simdf.format(cal.getTime());
//		System.out.println("当前时间所在周周日日期：" + week5);
		// 获取当前周 周6的日期
		cal.set(Calendar.DATE, cal.get(cal.DATE) + 1);
		String week6 = simdf.format(cal.getTime());
//		System.out.println("当前时间所在周周日日期：" + week6);
		// 获取当前周 周日的日期
		cal.set(Calendar.DATE, cal.get(cal.DATE) + 1);
		String week7 = simdf.format(cal.getTime());
//		System.out.println("当前时间所在周周日日期：" + week7);

		arr[0] = getEventNumByWeekDay2(week1, shopID);
		arr[1] = getEventNumByWeekDay2(week2, shopID);
		arr[2] = getEventNumByWeekDay2(week3, shopID);
		arr[3] = getEventNumByWeekDay2(week4, shopID);
		arr[4] = getEventNumByWeekDay2(week5, shopID);
		arr[5] = getEventNumByWeekDay2(week6, shopID);
		arr[6] = getEventNumByWeekDay2(week7, shopID);

		return arr;
	}
	@SuppressWarnings("all")
	public int[] getEvenArrThisWeek3(String usrID) {// 3防损员 需传入usrID

		int[] arr = new int[] { 0, 0, 0, 0, 0, 0, 0 };// 存放周一到周日的事件次数

		// 获取当前星期
		SimpleDateFormat simdf = new SimpleDateFormat("yyyy/MM/dd");
		Calendar cal = Calendar.getInstance();
		// 获取当前周 周一的日期
		cal.set(cal.DAY_OF_WEEK, cal.MONDAY);
		String week1 = simdf.format(cal.getTime());
//		System.out.println("当前时间所在周周一日期：" + week1);
		// 获取当前周 周2的日期
		cal.set(Calendar.DATE, cal.get(cal.DATE) + 1);
		String week2 = simdf.format(cal.getTime());
//		System.out.println("当前时间所在周周日日期：" + week2);
		// 获取当前周 周3的日期
		cal.set(Calendar.DATE, cal.get(cal.DATE) + 1);
		String week3 = simdf.format(cal.getTime());
//		System.out.println("当前时间所在周周日日期：" + week3);
		// 获取当前周 周4的日期
		cal.set(Calendar.DATE, cal.get(cal.DATE) + 1);
		String week4 = simdf.format(cal.getTime());
//		System.out.println("当前时间所在周周日日期：" + week4);
		// 获取当前周 周5的日期
		cal.set(Calendar.DATE, cal.get(cal.DATE) + 1);
		String week5 = simdf.format(cal.getTime());
//		System.out.println("当前时间所在周周日日期：" + week5);
		// 获取当前周 周6的日期
		cal.set(Calendar.DATE, cal.get(cal.DATE) + 1);
		String week6 = simdf.format(cal.getTime());
//		System.out.println("当前时间所在周周日日期：" + week6);
		// 获取当前周 周日的日期
		cal.set(Calendar.DATE, cal.get(cal.DATE) + 1);
		String week7 = simdf.format(cal.getTime());
//		System.out.println("当前时间所在周周日日期：" + week7);

		arr[0] = getEventNumByWeekDay3(week1, usrID);
		arr[1] = getEventNumByWeekDay3(week2, usrID);
		arr[2] = getEventNumByWeekDay3(week3, usrID);
		arr[3] = getEventNumByWeekDay3(week4, usrID);
		arr[4] = getEventNumByWeekDay3(week5, usrID);
		arr[5] = getEventNumByWeekDay3(week6, usrID);
		arr[6] = getEventNumByWeekDay3(week7, usrID);

		return arr;
	}

	// 复用
	private int getEventNumByWeekDay(String weekDay, String areaID) {
		conditionMap = new HashMap<String, Object>();
		conditionMap.put("areaID", areaID);
		conditionMap.put("weekDate", weekDay);

		return eventMapper.countEventThisDay(conditionMap);
	}

	// 复用
	private int getEventNumByWeekDay2(String weekDay, String shopID) {// 2 for
																		// 防损经理
		conditionMap = new HashMap<String, Object>();
		conditionMap.put("shopID", shopID);
		conditionMap.put("weekDate", weekDay);

		return eventMapper.countEventThisDay2(conditionMap);// 2 for 防损经理
	}

	// 复用
	private int getEventNumByWeekDay3(String weekDay, String usrID) {// 2 for
																		// 防损经理
		conditionMap = new HashMap<String, Object>();
		conditionMap.put("usrID", usrID);
		conditionMap.put("weekDate", weekDay);

		return eventMapper.countEventThisDay3(conditionMap);// 2 for 防损员
	}

}

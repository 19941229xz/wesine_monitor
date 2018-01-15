package com.wesine.web;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wesine.dao.BillMapper;
import com.wesine.dao.CashierMapper;
import com.wesine.dao.EventMapper;
import com.wesine.dao.ShopMapper;
import com.wesine.dao.TransactionMapper;
import com.wesine.model.TransData;
import com.wesine.service.RedisService;
import com.wesine.util.TimeUtil;

@Controller
public class TransDataController {

	@Autowired
	RedisService redisService;

	@Autowired
	BillMapper billMapper;
	@Autowired
	TransactionMapper transactionMapper;
	@Autowired
	EventMapper eventMapper;
	@Autowired
	CashierMapper cashierMapper;
	@Autowired
	ShopMapper shopMapper;

	private Map<String, Object> resultMap;

	private List<Map<String, Object>> listMap;

	private Map<String, Object> eventMap;
	
	private Map<String, Object> eventMapForAnalysi;

	@RequestMapping(value = "/TransData", method = RequestMethod.PUT)
	@ResponseBody
	public Map<String, Object> insertTransData(@RequestBody Map<String, Object> dataMap) {

		listMap = new ArrayList<Map<String, Object>>();

		String uuid = UUID.randomUUID().toString();

		dataMap.put("id", uuid);

		resultMap = new HashMap<String, Object>();

		List<Map<String, Object>> billList = new ArrayList<Map<String, Object>>();

		billList = (List<Map<String, Object>>) dataMap.get("Bills");

		for (int i = 0; i < billList.size(); i++) {
			billList.get(i).put("transId", uuid);
			
			
			
			//重新创建一个map村放条件
			Map<String,Object> cashierMap=new HashMap<String,Object>();
			cashierMap.put("c_id", dataMap.get("CashierID"));
			
			if(cashierMapper.countCashierID((String)dataMap.get("CashierID"))!=0&&billList.get(i).get("Type").equals("Normal")){//判断该cashierID是否存在  且为正常事件
				//存在的情况  且为正常事件
				int s_num=cashierMapper.getSnumByCashierID((String)dataMap.get("CashierID"));
				int e_num=cashierMapper.getEnumByCashierID((String)dataMap.get("CashierID"));
				
				
				cashierMap.put("s_num", (++s_num));//收银次数加1
				cashierMap.put("e_num", e_num);
				
				System.out.println(cashierMapper.updateByCashierID(cashierMap)==1?"收银员id:"+dataMap.get("CashierID")+"收银次数加1":"error");
				
				
			}else if(cashierMapper.countCashierID((String)dataMap.get("CashierID"))!=0&&!billList.get(i).get("Type").equals("Normal")){
				//存在的情况  且为正常事件
				int s_num=cashierMapper.getSnumByCashierID((String)dataMap.get("CashierID"));
				int e_num=cashierMapper.getEnumByCashierID((String)dataMap.get("CashierID"));
				
				//存在的情况  且为防损事件
				cashierMap.put("s_num", (++s_num));//收银次数加1
				cashierMap.put("e_num", (++e_num));//事件次数也加1
				
				System.out.println(cashierMapper.updateByCashierID(cashierMap)==1?"收银员id:"+dataMap.get("CashierID")+"\n收银次数加1\n"+"事件次数加1":"error");
				
			}else{
				//没有该收银员数据  需重新生成
				cashierMap.put("id", UUID.randomUUID()+"");//uuid  要转成string
				cashierMap.put("shop_id", dataMap.get("ShopID"));
				//根据shopid查询shopname
				String shopName=shopMapper.getShopNameByID((String)dataMap.get("ShopID"));
				if(shopName==null||shopName.equals("")){
					//如果查询不到该店面  就需要在数据库中重新生成
					cashierMap.put("shop_name", dataMap.get("ShopID")+"号店");
					cashierMap.put("areaId", "");
					shopMapper.insertShop(cashierMap);
					
					cashierMapper.insertCashier(cashierMap);
				}else{//查询到了  就直接添加
					cashierMap.put("shop_name", shopName);
					
					cashierMapper.insertCashier(cashierMap);
				}
				
				
			}

			if (!billList.get(i).get("Type").equals("Normal")) {// bills中type不正常的为事件
																// event
				
				eventMap = new HashMap<String, Object>();
				eventMap.put("id", UUID.randomUUID().toString());
				eventMap.put("status", "未处理");
				eventMap.put("result", "");
				eventMap.put("TransID", dataMap.get("TransID"));
				eventMap.put("RegID", dataMap.get("RegID"));
				eventMap.put("CashierID", dataMap.get("CashierID"));
				eventMap.put("PictureUrl0", billList.get(i).get("PictureUrl0"));
				eventMap.put("VideoUrl", billList.get(i).get("VideoUrl"));
				// 时间戳 处理为date时间
				
				eventMap.put("date", TimeUtil.stampToDate(dataMap.get("TsStart")+""));
				eventMap.put("startTime", dataMap.get("TsStart"));
				eventMap.put("actionTime", 0);
				eventMap.put("endTime", 0);
				eventMap.put("shop_id", dataMap.get("ShopID"));
				//System.out.println(TimeUtil.stampToDate(dataMap.get("TsStart")+""));
				
				//生成eventTAT数据
				eventMapper.insertEventTAT(eventMap);
				System.out.println("生成eventTAT数据！");

				listMap.add(eventMap);
			}
		}

		eventMapper.insertEventForMany(listMap);
		
		
		System.out.println("-----"+TimeUtil.stampToDateString(dataMap.get("TsStart") + "")+"-----\n本次交易单号："+dataMap.get("TransID")+"\n本次购物共扫描"+billList.size()+"商品！\n"+"不正常扫描事件次数："+listMap.size()+"\n----end----");
		
		int allEventNum=eventMapper.getAllEventNum();//数据库中获取事件总次数
		int allEventNumIncorrect=eventMapper.getAllEventNumIncorrect();//数据库中获取不正常事件总次数
		
		//临时增加的总事件次数  达到一千就累加到数据库中  并计算差错率
		if(redisService.exists("tempAddEventNum")&&redisService.exists("tempAddEventNumIncorrect")){
			System.out.println("---事务：累积计算事件差错率-start----");
			int tempNum=(int)redisService.get("tempAddEventNum");
			int tempNumIncorrect=(int)redisService.get("tempAddEventNumIncorrect");
			System.out.println("旧的临时事件次数："+tempNum);
			redisService.remove("tempAddEventNum");//删除旧的临时数据
			redisService.remove("tempAddEventNumIncorrect");//删除旧的临时数据
			redisService.set("tempAddEventNum", tempNum+billList.size());
			redisService.set("tempAddEventNumIncorrect", tempNumIncorrect+listMap.size());
			System.out.println("此次数据处理后："+"临时事件总数："+(tempNum+billList.size())+",临时错误事件总数："+(tempNumIncorrect+listMap.size())
			+"\n redis缓存已刷新！\n-----end-----");
			if(tempNum+billList.size()>1000){
				//计算差错率  获取百分数
				NumberFormat numberFormat = NumberFormat.getInstance(); 
				numberFormat.setMaximumFractionDigits(2);
				
				String result = numberFormat.format((float) (allEventNumIncorrect+tempNumIncorrect+listMap.size()) / (float) (allEventNum+tempNum+billList.size()) * 100);
				
				//再次清空临时值 并执行数据库  计算差错率  和持久化
				eventMapForAnalysi = new HashMap<String, Object>();
				eventMapForAnalysi.put("i", allEventNum+tempNum+billList.size());
				eventMapForAnalysi.put("j", allEventNumIncorrect+tempNumIncorrect+listMap.size());
				eventMapForAnalysi.put("parseDouble", Double.parseDouble(result));
				eventMapper.insertNewEventAnlysidata(eventMapForAnalysi);
				System.out.println("临时事件累计到一千次  已持久化到数据库中\n"+(allEventNumIncorrect+tempNumIncorrect+listMap.size())+"/"+(allEventNum+tempNum+billList.size())+
						"="+result+"%");
				redisService.set("tempAddEventNum", 0);
				redisService.set("tempAddEventNumIncorrect", 0);
				System.out.println("redis 事件缓存数据已经清零！\n-----end-----");
			}
		}else {
			if(!redisService.exists("tempAddEventNum")){//判断tempAddEventNum是否redis中存在
				System.out.println("无临时事件数据，重新生成:tempAddEventNum="+billList.size());
				redisService.set("tempAddEventNum", billList.size());//写入缓存用set方法  
			}
			
			if(!redisService.exists("tempAddEventNumIncorrect")){//判断tempAddEventNum是否redis中存在
				System.out.println("无临时不正常事件数据，重新生成:tempAddEventNumIncorrect="+listMap.size());
				redisService.set("tempAddEventNumIncorrect", listMap.size());
			}
			
			
		}
		
		
		// redisService.set("testkey", "121111");//测试redis

		// transactionMapper.insert(dataMap); //插入transaction 交易信息

		// billMapper.insertBillsForMany(billList);//批量插入bills 一次交易中每个商品的扫描信息

		if (billMapper.insertBillsForMany(billList) >= 1 && transactionMapper.insert(dataMap) >= 1) {
			resultMap.put("status", "0");// 0 success
			resultMap.put("tip", "插入成功！");// 0 success
		} else {
			resultMap.put("status", "1");// 0 fail
			resultMap.put("tip", "插入失败！");// 0 success
		}

		// System.out.println(billList.size());
		//
		// System.out.println(billList.get(0).get("PictureUrl0"));

		return resultMap;

	}

}

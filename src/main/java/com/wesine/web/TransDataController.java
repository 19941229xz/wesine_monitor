package com.wesine.web;

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
import com.wesine.dao.EventMapper;
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

	private Map<String, Object> resultMap;

	private List<Map<String, Object>> listMap;

	private Map<String, Object> eventMap;

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
				eventMap.put("date", TimeUtil.stampToDate(dataMap.get("TsStart") + ""));

				listMap.add(eventMap);
			}
		}

		eventMapper.insertEventForMany(listMap);
		
		System.out.println("-----"+TimeUtil.stampToDateString(dataMap.get("TsStart") + "")+"-----\n本次交易单号："+dataMap.get("TransID")+"\n本次购物共扫描"+billList.size()+"商品！\n"+"不正常扫描事件次数："+listMap.size()+"\n----end----");

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

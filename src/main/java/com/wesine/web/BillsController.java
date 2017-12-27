package com.wesine.web;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wesine.dao.BillMapper;
import com.wesine.dao.EventMapper;

@Controller
public class BillsController {

	/*private Map<String, String[]> conditionMap;

	private Map<String, Object> billMap;

	private Map<String, Object> eventMap;
	
	private Map<String,String> resultMap;

	@Autowired
	BillMapper billMapper;
	@Autowired
	EventMapper eventMapper;

	@RequestMapping(value = "/bills", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> insertBills(HttpServletRequest request, HttpServletResponse response) {
		//处理成功标志 true  成功  false  失败(默认成功)
		boolean status=true;
		
		
		conditionMap = new HashMap<String, String[]>();
		billMap = new HashMap<String, Object>();
		eventMap = new HashMap<String, Object>();
		resultMap=new HashMap<String,String>();

		conditionMap = request.getParameterMap();
		Set keSet = conditionMap.entrySet();

		for (Iterator itr = keSet.iterator(); itr.hasNext();) {
			Map.Entry me = (Map.Entry) itr.next();
			Object ok = me.getKey();
			Object ov = me.getValue();
			String[] value = new String[1];
			if (ov instanceof String[]) {
				value = (String[]) ov;
			} else {
				value[0] = ov.toString();
			}

			for (int k = 0; k < value.length; k++) {
				System.out.println(ok + "=" + value[k]);

				switch (ok.toString()) {
				case "ts":
				case "quantity":
				case "eventFlag":
					billMap.put(ok.toString(), Integer.parseInt(value[k]));
					break;
				case "price":
					BigDecimal bd = new BigDecimal(value[k]);
					billMap.put(ok.toString(), bd);

					break;
				case "amount":
					billMap.put(ok.toString(), Double.parseDouble(value[k]));
					
					break;
				default:
					billMap.put(ok.toString(), value[k]);
					break;
				}

			}
		}

		if ((int)billMap.get("eventFlag")==1) {
			System.out.println("这是错误event！");
			billMap.put("eventId", UUID.randomUUID().toString().replace("_", ""));

			eventMap.put("eventId", billMap.get("eventId"));
			eventMap.put("transId", billMap.get("transId"));
			eventMap.put("cashierId", billMap.get("cashierId"));
			eventMap.put("customerId", billMap.get("customerId"));

			eventMap.put("status", "0");
			eventMap.put("comments", "");
			eventMap.put("auditResult", "3");

			int num=eventMapper.insertEvent(eventMap);
			status=num==1?true:false;

		}

		billMap.put("id", UUID.randomUUID().toString().replace("_", ""));

		int num=billMapper.insertBills(billMap);
		status=num==1?true:false;
		
		resultMap.put("status", "0");//0  成功  1  失败
		resultMap.put("tip", status?"数据成功插入！":"数据插入失败！");

		return resultMap;

	}*/

}

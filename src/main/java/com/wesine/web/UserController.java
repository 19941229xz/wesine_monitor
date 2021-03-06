package com.wesine.web;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.wesine.dao.CashierMapper;
import com.wesine.dao.CounterMapper;
import com.wesine.dao.FsyMapper;
import com.wesine.dao.ShopMapper;
import com.wesine.dao.UserMapper;
import com.wesine.model.User;
import com.wesine.util.TimeUtil;

@Controller
public class UserController {
	
	
	private Map<String,Object> conditionMap;
	
	private Map<String,Object> resultMap;
	
	private List<Map<String,Object>> listMap;

	@Autowired
	UserMapper userMapper;
	@Autowired
	CounterMapper counterMapper;
	@Autowired
	FsyMapper fsyMapper;
	@Autowired
	CashierMapper cashierMapper;
	@Autowired
	ShopMapper shopMapper;

	@RequestMapping(value = "/users")
	@ResponseBody
	public List<User> getAllUsers() {

		List<User> userList = userMapper.getAllUsers();

		return userList;
	}

	@RequestMapping(value = "/users/{id}")
	@ResponseBody
	public User getUserById(@PathVariable String id) {

		User u = userMapper.findUserById(id);
		return u;
	}

	
	
	
	
	
	
	
	// http://121.201.13.217:27002/api/v1/wmHomePage/3？usrID=0100201&usrName=李四&shopID=1001

	// 物美登陆
	@RequestMapping(value = "api/v1/wmHomePage/{judgeNum}") 
	public void indexHtml(HttpServletResponse response,HttpServletRequest request,Map<String, Object> map, @PathVariable String judgeNum, String usrID, String usrName,
			String shopID,String areaID) throws IOException {
//		System.out.println(usrID);
		conditionMap=new HashMap<String,Object>();
		
		map.put("status", "0");//正常登陆
		
		String roleId="";

		switch (judgeNum) {
		case "2":
			// 防损员
			map.put("roleId", 1);
			roleId="1";
			conditionMap.put("roleId", "1");
			map.put("shopID", shopID);
			System.out.println("----登陆操作----\n"+"usrID："+usrID+"\n身份:防损员"+"\n姓名:"+usrName+"\nshopID:"+shopID+"\n----end----");
			break;
		case "3":
			// 防损经理
			map.put("roleId", 2);
			roleId="2";
			conditionMap.put("roleId", "2");
			map.put("shopID", shopID);
			System.out.println("----登陆操作----\n"+"usrID："+usrID+"\n身份:防损经理"+"\n姓名:"+usrName+"\nshopID:"+shopID+"\n----end----");
			break;
		case "4":
			// 区域经理
			map.put("roleId", 3);
			roleId="3";
			conditionMap.put("roleId", "3");
			map.put("areaID", areaID);
			System.out.println("----登陆操作----\n"+"usrID："+usrID+"\n身份:区域经理"+"\n姓名:"+usrName+"\nareaID:"+areaID+"\n----end----");
			break;

		default:
			//接口url错误
			
			break;
		}
		

		//判断该usrID在user表中是否存在
		if(userMapper.count(usrID)<1){
			conditionMap.put("usrID", usrID);
			conditionMap.put("usrName", usrName);
			conditionMap.put("companyId", "1");
			conditionMap.put("shopId", shopID);
			conditionMap.put("roleId", map.get("roleId"));//roleid  ==1   为防损员
			
			//users表中生成user数据  自动注册
			userMapper.insert(conditionMap);
			
			if(judgeNum.equals("2")){//如果新注册角色为防损员  生成防损员基本信息  主键还是userid
				conditionMap.put("shopName", shopMapper.getShopNameByID(conditionMap.get("shopId")+""));
				conditionMap.put("actionEventNum", 0);
				//conditionMap.put("avgActionTime", 999999999);//设置到很大
				fsyMapper.insertFsy(conditionMap);
				System.out.println("新增加一防损员信息！");
			}
			
			/*switch(judgeNum){
			case "2"://如果新注册角色为防损员  生成防损员基本信息  主键还是userid
				fsyMapper.insertFsy(conditionMap);
				System.out.println("新增加一防损员信息！");
				break;
			case "3"://注册防损经理
				
				
				System.out.println("新增加一防损经理信息！");
				break;
			case "4":
				
				break;
			default:
				
				break;
			
			}*/
		}
		
		if(roleId.equals("1")&&counterMapper.countUserCounter(usrID)==0){//if roleId =0 is 防损员  查询是否关联款台
			map.put("status", "2");//2 防损员登陆  未关联款台
		}
		
		
		map.put("usrID", usrID);
		map.put("usrName", usrName);
		
		
		System.out.println(request.getServerName()+":"+request.getServerPort());
		
		String serverPath="http://"+request.getServerName()+":"+request.getServerPort();
		
		
		if(map.get("roleId").equals(3)){//通过roleId判断角色  为3时为区域经理  跳转到index2
			response.sendRedirect("http://"+request.getServerName()+":"+request.getServerPort()+"/static/index2.html?usrID="+map.get("usrID")+"&usrName="+URLEncoder.encode(map.get("usrName")+"", "utf-8")
			+"&roleId="+map.get("roleId")+"&areaID="+map.get("areaID")+"&shopID="+map.get("shopID")
			+"&serverPath="+serverPath);//给usrName进行编码  中文乱码
		}else{
			response.sendRedirect("http://"+request.getServerName()+":"+request.getServerPort()+"/static/index.html?usrID="+map.get("usrID")+"&usrName="+URLEncoder.encode(map.get("usrName")+"", "utf-8")
			+"&roleId="+map.get("roleId")+"&areaID="+map.get("areaID")+"&shopID="+map.get("shopID")
			+"&serverPath="+serverPath+"&status="+map.get("status"));
		}
		
		
	}
	
	
	
	@RequestMapping(value="/getFsyAndSyyList")
	@ResponseBody
	public Map<String,Object> getFsyAndSyyList(@RequestParam Map<String,Object> params){/* roleId : that.roleId, areaID : that.areaID, usrName : that.usrName, usrID: that.usrID */
	
		resultMap=new HashMap<String,Object>();
		
		resultMap.put("status", "0");
		
		//listMap=new ArrayList<Map<String,Object>>();//村放防损员list
		
		listMap=fsyMapper.getAllFsy(params);
		
		if(listMap==null||listMap.size()==0){
			resultMap.put("status", "1");//防损员数据为空  或请求失败
		}else{
			for (Map<String, Object> map : listMap) {//long ms  转换微  时分秒
				map.put("avgActionTime", TimeUtil.longTime2StringTime(Long.parseLong(map.get("avgActionTime")+"")));
			}
			resultMap.put("fsyList", listMap);
		}
		
		listMap=cashierMapper.getAllCashiers(params);
		
		if(listMap==null||listMap.size()==0){
			resultMap.put("status", "2");//收银员数据为空  或请求失败
		}else{
			resultMap.put("syyList", listMap);
		}
		
		
		return resultMap;
	}
	
	
	
	
	
	
	
	

}

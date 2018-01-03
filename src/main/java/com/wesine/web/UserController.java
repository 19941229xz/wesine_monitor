package com.wesine.web;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.wesine.dao.UserMapper;
import com.wesine.model.User;

@Controller
public class UserController {
	
	
	private Map<String,Object> conditionMap;

	@Autowired
	UserMapper userMapper;

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

		switch (judgeNum) {
		case "2":
			// 防损员
			map.put("roleId", 1);
			conditionMap.put("roleId", "1");
			map.put("shopID", shopID);
			System.out.println("----登陆操作----\n"+"usrID："+usrID+"\n身份:防损员"+"\n姓名:"+usrName+"\nshopID:"+shopID+"\n----end----");
			break;
		case "3":
			// 防损经理
			map.put("roleId", 2);
			conditionMap.put("roleId", "2");
			map.put("shopID", shopID);
			System.out.println("----登陆操作----\n"+"usrID："+usrID+"\n身份:防损经理"+"\n姓名:"+usrName+"\nshopID:"+shopID+"\n----end----");
			break;
		case "4":
			// 区域经理
			map.put("roleId", 3);
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
			//将获取到的user信息注册到users数据表中
			userMapper.insert(conditionMap);
			System.out.println("------新注册一名防损人员：usrID:"+usrID+"\tusrName:"
			+usrName+"\tcompanyId:"+conditionMap.get("companyId")+"-----");
		}
		
		map.put("usrID", usrID);
		map.put("usrName", usrName);
		
		
		response.sendRedirect("http://localhost:8080/static/index.html?usrID="+map.get("usrID")+"&usrName="+map.get("usrName")
		+"&roleId="+map.get("roleId")+"&areaID="+map.get("areaID")+"&shopID="+map.get("shopID"));
	}
	
	
	
	
	
	
	
	
	
	
	
	

}

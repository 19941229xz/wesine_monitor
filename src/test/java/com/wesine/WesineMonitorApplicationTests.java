package com.wesine;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.wesine.service.EventService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WesineMonitorApplicationTests {
	
	@Autowired
	EventService eventService;

	@Test
	public void testEventService() {
		
		int[] arr=eventService.getEvenArrThisWeek("1");//传入一个areaid  后台自动获取当前周日期
		for (int i : arr) {
			System.out.println(i);
		}
		
	}
	
	@Test
	public void testGetEvenArrThisDay() {
		
		int[] arr=eventService.getEvenArrThisDay("1");//传入一个areaid  后台自动获取当前周日期
		for (int i : arr) {
			System.out.println(i);
		}
		
	}
	
	@Test
	public void testgetEvenArrThisMonth() {
		
		int[] arr=eventService.getEvenArrThisMonth("1");//传入一个areaid  后台自动获取当前周日期
		for (int i : arr) {
			System.out.println(i);
		}
		
	}

}

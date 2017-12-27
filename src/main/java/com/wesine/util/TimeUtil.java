package com.wesine.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

	/*
	 * 将时间戳转换为时间 date类型
	 */
	public static Date stampToDate(String s) {
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long lt = new Long(s);
		Date date = new Date(lt);
//		res = simpleDateFormat.format(date);
		return date;
	}
	
	/*
	 * 将时间戳转换为时间 格式化字符串类型
	 */
	public static String stampToDateString(String s) {
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long lt = new Long(s);
		Date date = new Date(lt);
		res = simpleDateFormat.format(date);
		return res;
	}

}

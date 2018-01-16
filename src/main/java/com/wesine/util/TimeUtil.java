package com.wesine.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

	/*
	 * 将时间戳转换为时间 date类型
	 */
	public static Date stampToDate(String s) {
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Long lt = Long.parseLong(s);
		//System.out.println(lt);
		Date date = new Date(lt);
		//System.out.println(date);
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
	
	/* 
     * 将Date转换为时间戳(long)
     */    
    public static long dateToStamp(Date date) {
        long ts =0;
		ts = date.getTime();
        return ts;
    }
    
    /* 
     * 毫秒转化时分秒 
     */  
    public static String longTime2StringTime(Long ms) {  
        Integer ss = 1000;  
        Integer mi = ss * 60;  
        Integer hh = mi * 60;  
        Integer dd = hh * 24;  
      
        Long day = ms / dd;  
        Long hour = (ms - day * dd) / hh;  
        Long minute = (ms - day * dd - hour * hh) / mi;  
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;  
        Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;  
          
        StringBuffer sb = new StringBuffer();  
        if(day > 0) {  
            sb.append(day+"天");  
        }  
        if(hour > 0) {  
            sb.append(hour+"小时");  
        }  
        if(minute > 0) {  
            sb.append(minute+"分");  
        }  
        if(second > 0) {  
            sb.append(second+"秒");  
        }  
       /* if(milliSecond > 0) {  
            sb.append(milliSecond+"毫秒");  
        }*/  
        return sb.toString();  
    }  
    

}

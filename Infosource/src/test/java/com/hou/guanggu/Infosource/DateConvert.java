package com.hou.guanggu.Infosource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author houweitao
 * @date 2016年1月6日 下午5:28:00
 */

public class DateConvert {

	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		String str = "2015-09-23 12:57:58.603 ERROR 28465 --- [crawler-13] c.peony.crawler.helper.BasicCrawlHelper  : exception raises:";
		String[] tmp = str.split(" ");
		String out = tmp[0] + " " + tmp[1];
		System.out.println(out);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = (Date) formatter.parse(" 2008-07-10 19:20:00 ");
		
		System.out.println(date);
		
		out="safasfsaf";
		System.out.println(out.toUpperCase());
	}

}

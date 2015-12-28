package com.hou.guanggu.Infosource.checkWebsite;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author houweitao
 * @date 2015年12月28日 下午2:42:26
 */

public class AnalyseLog {

	public static void main(String[] args) {
		AnalyseLog analyseLog = new AnalyseLog();
		analyseLog.testReadLog();
	}

	void testReadLog() {
		List<String> list = new ArrayList<String>();
		try {
			FileInputStream is = new FileInputStream("recources/log.log");
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line;
			try {
				while ((line = br.readLine()) != null) {
					if (line.equals(""))
						continue;
					else {
						if (line.contains("out of")) {
							list.add(line);
							Info info = getInfo(line.split(" : ")[1]);
							System.out.println("info: " + info.getStatus() + ";  source: " + info.getInfomation());
						} else if (line.contains("Failed to access")) {
							Info info = new Info(InfoStatus.NOTEXIST, getBadSearch(line));
							System.out.println("info: " + info.getStatus() + ";  source: " + info.getInfomation());
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("读取一行数据时出错");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("文件读取路径错误FileNotFoundException");
		}

		System.out.println(list.size());

//		return list;
	}

	//获取爬取失败的组合
	String getBadSearch(String str) {
		String infomation = "";
		Pattern p = Pattern.compile("\\[.*?\\]");// 查找规则公式中大括号以内的字符
		Matcher m = p.matcher(str);
		while (m.find()) {// 遍历找到的所有大括号
			String param = m.group().replaceAll("\\[\\]", "");// 去掉括号
//			System.out.println(param.substring(1, param.length() - 1));
			infomation = param.substring(1, param.length() - 1);
		}

		return infomation;
	}

	//获取正常爬取的组合
	Info getInfo(String str) {
		String infomation = "";
		Pattern p = Pattern.compile("\\[.*?\\]");// 查找规则公式中大括号以内的字符
		Matcher m = p.matcher(str);
		while (m.find()) {// 遍历找到的所有大括号
			String param = m.group().replaceAll("\\[\\]", "");// 去掉括号
//			System.out.println(param.substring(1, param.length() - 1));
			infomation = param.substring(1, param.length() - 1);
		}

		return new Info(getNum(str), infomation);
	}

	InfoStatus getNum(String str) {
		Pattern p = Pattern.compile("[0-9\\.]+");
		Matcher m = p.matcher(str);

		int first = 0, second = 0;

		if (m.find()) {
//			System.out.print(m.group() + ",");
			first = Integer.valueOf(m.group());
		}
		if (m.find()) {
//			System.out.print(m.group() + ",");
			second = Integer.valueOf(m.group());
		}

		if (second == 0)
			return InfoStatus.BAD;
		else {
			double res = (double) (first / second);
			if (res < 0.2)
				return InfoStatus.HIGH;
			else if (res > 0.618)
				return InfoStatus.LOW;
			else
				return InfoStatus.NICE;
		}
	}
}

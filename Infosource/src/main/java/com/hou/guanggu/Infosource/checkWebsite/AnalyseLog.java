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

import com.hou.guanggu.Infosource.checkWebsite.model.Info;
import com.hou.guanggu.Infosource.checkWebsite.model.InfoStatus;

/**
 * @author houweitao
 * @date 2015年12月28日 下午2:42:26
 */

public class AnalyseLog {
	public static void main(String[] args) {
		AnalyseLog analyseLog = new AnalyseLog();
		OperateDB operate = new OperateDB();
		List<Info> infoList = new ArrayList<Info>();
		List<Info> normaiList = new ArrayList<Info>();
		List<Info> abnormaiList = new ArrayList<Info>();
		analyseLog.readLog(normaiList, abnormaiList);

		System.out.println("abnormal: " + abnormaiList.size());
		System.out.println("normal: " + normaiList.size());

		infoList.addAll(abnormaiList);
		infoList.addAll(normaiList);

		for (Info info : normaiList) {
			operate.insertDB(info);
		}

		for (Info info : abnormaiList) {
			operate.insertDB(info);
		}

//		for (Info info : infoList) {
//			System.out.println("before： " + info.getInfomation() + "  " + info.getNewDocNum() + "," + info.getDocNum());
//			operate.dealDB(info);
//		}
	}

	void readLog(List<Info> normaiList, List<Info> abnormaiList) {
		try {
			FileInputStream is = new FileInputStream("recources/spring.log");
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line;
			try {
				while ((line = br.readLine()) != null) {
					if (line.equals(""))
						continue;
					else {
						if (line.contains("out of")) {
							Info info = getNormal(line.split(" : ")[1]);
							normaiList.add(info);
							System.out.println("info: " + info.getInfomation() + "," + info.getStatus() + ";  source: "
									+ info.getInfomation() + "  doc " + info.getDocNum());
						} else if (line.contains("Failed to access")) {
							String[] tmp = line.split(" ");
							String time = tmp[0] + " " + tmp[1];
							Info info = new Info(InfoStatus.NOTEXIST, getAbnormal(line), 0, 0, time);
							abnormaiList.add(info);
							System.out.println("info: " + info.getStatus() + ";  source: " + info.getInfomation()
									+ "  doc " + info.getDocNum());
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

//		return list;
	}

	//获取爬取失败的组合
	String getAbnormal(String str) {
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
	Info getNormal(String str) {
		String infomation = "";
		Pattern p = Pattern.compile("\\[.*?\\]");// 查找规则公式中大括号以内的字符
		Matcher m = p.matcher(str);
		while (m.find()) {// 遍历找到的所有大括号
			String param = m.group().replaceAll("\\[\\]", "");// 去掉括号
//			System.out.println(param.substring(1, param.length() - 1));
			infomation = param.substring(1, param.length() - 1);
		}

		return getInfo(str, infomation);
	}

	Info getInfo(String str, String infomation) {
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

		InfoStatus status = null;

		if (second == 0)
			status = InfoStatus.BAD;
		else {
			double res = (double) (first / second);
			if (res < 0.2)
				status = InfoStatus.HIGH;
			else if (res > 0.618)
				status = InfoStatus.LOW;
			else
				status = InfoStatus.NICE;
		}

		String[] tmp = str.split(" ");
		String time = tmp[0] + " " + tmp[1];

		return new Info(status, infomation, second, first, time);
	}
}

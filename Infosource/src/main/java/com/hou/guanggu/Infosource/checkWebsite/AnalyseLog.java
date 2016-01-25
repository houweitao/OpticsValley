package com.hou.guanggu.Infosource.checkWebsite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hou.guanggu.Infosource.checkWebsite.model.Info;
import com.hou.guanggu.Infosource.checkWebsite.model.InfoStatus;
import com.hou.guanggu.Infosource.checkWebsite.util.HashMap2Excel;
import com.hou.guanggu.Infosource.checkWebsite.util.JedisFactory;
import com.hou.guanggu.Infosource.checkWebsite.util.JedisPoolFactory;

import redis.clients.jedis.Jedis;

/**
 * @author houweitao
 * @date 2015年12月28日 下午2:42:26
 * mysql数据库：43分钟；redis：11分钟。本地10秒。
 */

public class AnalyseLog implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(AnalyseLog.class);
	private static String saveInfosource = "LOG$SAVE$INFOSOURCE";
	private static String saveKeyword = "LOG$SAVE$KEYWORD";

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		Jedis jedis = new JedisPoolFactory().getInstance().getResource();
		AnalyseLog analyseLog = new AnalyseLog();
		HashMap2Excel excel = new HashMap2Excel();
		OperateDB operate = new OperateDB();
		RedisDataManager manager = new RedisDataManager();
		manager.del();

		String path = "recources/";
		List<Info> normaiList = new ArrayList<Info>();
		List<Info> abnormaiList = new ArrayList<Info>();

		LinkedList<String> logs = analyseLog.getLogFiles(path);

		for (String fileName : logs) {
			analyseLog.readLog(normaiList, abnormaiList, fileName);
		}

		System.out.println("abnormal: " + abnormaiList.size());
		System.out.println("normal: " + normaiList.size());

//		operate.cleanRedis();

		for (Info info : normaiList) {
			if (info != null)
				if (info.getStatus() == InfoStatus.BAD)
					operate.insertByRedis(info);
		}

		for (Info info : abnormaiList) {
			if (info != null)
				operate.insertByRedis(info);
		}

		Map<String, String> reportInfosource = jedis.hgetAll(saveInfosource);
		Map<String, String> reportkeyword = jedis.hgetAll(saveKeyword);
		excel.makeInfosourceExcelStr(reportInfosource);
		excel.makeKeywordExcelStr(reportkeyword);

		LOGGER.info("耗时： " + (System.currentTimeMillis() - start) / 1000 + " 秒");
	}

	LinkedList<String> getLogFiles(String path) {
		LinkedList<String> logs = new LinkedList<String>();
		File dir = new File(path);
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isFile()) {
				logs.add(path + file.getName());
			} else {
				logs.addAll(getLogFiles(path + file.getName() + "/"));
			}
		}

		return logs;
	}

	void readLog(List<Info> normaiList, List<Info> abnormaiList, String filename) {
		try {
			FileInputStream is = new FileInputStream(filename);
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line;
			try {
				while ((line = br.readLine()) != null) {
					if (line.equals(""))
						continue;
					else {
						if (line.contains("out of")) {
							Info info = getNormal(line);
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
	Info getNormal(String line) {
		String infomation = "";
		String str = line.split(" : ")[1];
		Pattern p = Pattern.compile("\\[.*?\\]");// 查找规则公式中大括号以内的字符
		Matcher m = p.matcher(str);
		while (m.find()) {// 遍历找到的所有大括号
			String param = m.group().replaceAll("\\[\\]", "");// 去掉括号
//			System.out.println(param.substring(1, param.length() - 1));
			infomation = param.substring(1, param.length() - 1);
		}
		return getInfo(line, infomation);
	}

	Info getInfo(String line, String infomation) {
		Pattern p = Pattern.compile("[0-9\\.]+");
		Matcher m = p.matcher(line.split(" : ")[1]);

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

		String[] tmp = line.split(" ");
		String time = tmp[0] + " " + tmp[1];

		return new Info(status, infomation, second, first, time);
	}

	public void run() {
		// TODO Auto-generated method stub

		long start = System.currentTimeMillis();
		Jedis jedis = new JedisFactory().getInstance();
		HashMap2Excel excel = new HashMap2Excel();
		OperateDB operate = new OperateDB();
//		RedisDataManager manager = new RedisDataManager();
//		manager.del();

		dealNohup();

		String path = "/home/cruser/v4/logs/";

		List<Info> normaiList = new ArrayList<Info>();
		List<Info> abnormaiList = new ArrayList<Info>();

		LinkedList<String> logs = getLogFiles(path);

		for (String fileName : logs) {
			readLog(normaiList, abnormaiList, fileName);
		}

		System.out.println("abnormal: " + abnormaiList.size());
		System.out.println("normal: " + normaiList.size());

//		operate.cleanRedis();

		for (Info info : normaiList) {
			if (info != null)
				if (info.getStatus() == InfoStatus.BAD)
					operate.insertByRedis(info);
		}

		for (Info info : abnormaiList) {
			if (info != null)
				operate.insertByRedis(info);
		}

		Map<String, String> reportInfosource = jedis.hgetAll(saveInfosource);
		Map<String, String> reportkeyword = jedis.hgetAll(saveKeyword);
		excel.makeInfosourceExcelStr(reportInfosource);
		excel.makeKeywordExcelStr(reportkeyword);

		LOGGER.info("耗时： " + (System.currentTimeMillis() - start) / 1000 + " 秒");

	}

	private void dealNohup() {
		String fileName = "nohup.out";
		File file = new File(fileName);
		FileChannel fc = null;
		if (file.exists() && file.isFile()) {
			LOGGER.info("nohup.out 存在..");
			FileInputStream fis;
			try {
				fis = new FileInputStream(file);
				fc = fis.getChannel();
				if (fc.size() > 500 * 1000000) {//500M
					fis.close();
					fc.close();
					boolean d = file.delete();
					System.out.println(d);
					file.createNewFile();
				}
				fis.close();
				fc.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}

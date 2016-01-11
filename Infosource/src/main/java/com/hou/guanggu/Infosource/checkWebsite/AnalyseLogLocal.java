package com.hou.guanggu.Infosource.checkWebsite;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mortbay.log.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.hou.guanggu.Infosource.checkWebsite.dao.InfosourceDao;
import com.hou.guanggu.Infosource.checkWebsite.dao.KeywordDao;
import com.hou.guanggu.Infosource.checkWebsite.model.Engine;
import com.hou.guanggu.Infosource.checkWebsite.model.Info;
import com.hou.guanggu.Infosource.checkWebsite.model.InfoStatus;
import com.hou.guanggu.Infosource.checkWebsite.model.Infosource;
import com.hou.guanggu.Infosource.checkWebsite.model.Keyword;
import com.hou.guanggu.Infosource.checkWebsite.util.HashMap2Excel;
import com.hou.guanggu.Infosource.checkWebsite.util.JedisOperater;

/**
 * @author houweitao
 * @date 2016年1月11日 上午10:17:06
 */

public class AnalyseLogLocal {
	private static final Logger log = LoggerFactory.getLogger(AnalyseLogLocal.class);

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		AnalyseLogLocal analyseLog = new AnalyseLogLocal();
		HashMap2Excel excel = new HashMap2Excel();
		OperateDB operate = new OperateDB();
		List<Info> infoList = new ArrayList<Info>();
		List<Info> normaiList = new ArrayList<Info>();
		List<Info> abnormaiList = new ArrayList<Info>();

		JedisOperater op = new JedisOperater();
		log.info("begin");
		HashMap<String, String> infosourceDB = op.getAllinfosource();
		log.info("begin of keywordDB");
		HashMap<String, String> keywordDB = op.getAllkeyword();
		log.info("begin of engineDB");
		HashMap<String, String> engineDB = op.getAllengine();
		log.info("end of engineDB");

		HashMap<String, Infosource> infosourceReport = new HashMap<String, Infosource>();
		HashMap<String, Keyword> keywordReport = new HashMap<String, Keyword>();

		analyseLog.readLog(normaiList, abnormaiList);

		System.out.println("abnormal: " + abnormaiList.size());
		System.out.println("normal: " + normaiList.size());

//		infoList.addAll(abnormaiList);
//		infoList.addAll(normaiList);

//		operate.cleanRedis();

		log.info("begin of deal");
		for (Info info : normaiList) {
			if (info.getInfomation().contains("i")) {
				Infosource infosource = JSON.parseObject(infosourceDB.get(info.getInfomation()), Infosource.class);
				infosource.setDocNum(info.getDocNum());
				infosource.setNewDocNum(info.getNewDocNum());
				infosource.setTime(info.getTime());
				infosource.setStatus(info.getStatus());
				infosource.setSearchNum(1);

				Infosource already = infosourceReport.get(info.getInfomation());

				if (already != null) {
					infosource.setDocNum(infosource.getDocNum() + already.getDocNum());
					infosource.setNewDocNum(infosource.getNewDocNum() + already.getNewDocNum());
					infosource.setSearchNum(already.getSearchNum() + 1);
				}
				infosourceReport.put(info.getInfomation(), infosource);
			} else {
				Keyword keyword = JSON.parseObject(keywordDB.get(info.getInfomation()), Keyword.class);
				Engine engine = JSON.parseObject(engineDB.get(keyword.getEngine()), Engine.class);
				keyword.setDocNum(info.getDocNum());
				keyword.setNewDocNum(info.getNewDocNum());
				keyword.setTime(info.getTime());
				keyword.setStatus(info.getStatus());
				keyword.setSearchNum(1);
				keyword.setName(engine.getName());
				keyword.setUrl(engine.getUrl());

				Keyword already = keywordReport.get(info.getInfomation());
				if (already != null) {
					keyword.setDocNum(keyword.getDocNum() + already.getDocNum());
					keyword.setNewDocNum(keyword.getNewDocNum() + already.getNewDocNum());
					keyword.setSearchNum(1 + already.getSearchNum());
				}

				keywordReport.put(info.getInfomation(), keyword);
			}
		}

		for (Info info : abnormaiList) {
			if (info.getInfomation().contains("i")) {
				Infosource infosource = JSON.parseObject(infosourceDB.get(info.getInfomation()), Infosource.class);
				infosource.setDocNum(info.getDocNum());
				infosource.setNewDocNum(info.getNewDocNum());
				infosource.setTime(info.getTime());
				infosource.setStatus(info.getStatus());
				infosource.setSearchNum(1);

				Infosource already = infosourceReport.get(info.getInfomation());

				if (already != null) {
					infosource.setDocNum(infosource.getDocNum() + already.getDocNum());
					infosource.setNewDocNum(infosource.getNewDocNum() + already.getNewDocNum());
					infosource.setSearchNum(already.getSearchNum() + 1);
				}
				infosourceReport.put(info.getInfomation(), infosource);
			} else {
				Keyword keyword = JSON.parseObject(keywordDB.get(info.getInfomation()), Keyword.class);
				Engine engine = JSON.parseObject(engineDB.get(keyword.getEngine()), Engine.class);
				keyword.setDocNum(info.getDocNum());
				keyword.setNewDocNum(info.getNewDocNum());
				keyword.setTime(info.getTime());
				keyword.setStatus(info.getStatus());
				keyword.setSearchNum(1);
				keyword.setName(engine.getName());
				keyword.setUrl(engine.getUrl());

				Keyword already = keywordReport.get(info.getInfomation());
				if (already != null) {
					keyword.setDocNum(keyword.getDocNum() + already.getDocNum());
					keyword.setNewDocNum(keyword.getNewDocNum() + already.getNewDocNum());
					keyword.setSearchNum(1 + already.getSearchNum());
				}

				keywordReport.put(info.getInfomation(), keyword);
			}
		}

		for (Entry<String, String> entry : infosourceDB.entrySet()) {
			System.out.println(entry.getValue());
		}

		excel.makeInfosourceExcel(infosourceReport);
		excel.makeKeywordExcel(keywordReport);

//		ExcelUtil excelUtil = new ExcelUtil();
//		excelUtil.makeExcel();

		Log.info("耗时： " + (System.currentTimeMillis() - start) / 1000 + " 秒");

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
		String str = line.split(" : ")[1];
		String infomation = "";
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
		Matcher m = p.matcher(line);

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

	HashMap<String, Infosource> getInfosourceDB() throws SQLException {
		InfosourceDao dao = new InfosourceDao();
		ResultSet rs = dao.getAll();
		HashMap<String, Infosource> infosourceMap = new HashMap<String, Infosource>();

		if (rs != null) {
			while (rs.next()) {
				Infosource in = new Infosource(rs.getInt("id"), rs.getInt("freq"), rs.getString("url"),
						rs.getString("website"), 0, 0, null, null);
				infosourceMap.put("i+" + rs.getInt("id"), in);
			}
		}

		return infosourceMap;
	}

	HashMap<String, Keyword> getKeywordDB() throws SQLException {
		KeywordDao dao = new KeywordDao();
		ResultSet rs = dao.getAll();
		HashMap<String, Keyword> keywordMap = new HashMap<String, Keyword>();

		if (rs != null) {
			while (rs.next()) {
				Keyword ke = new Keyword(rs.getInt("id"), rs.getString("keyword"), rs.getInt("engine"), null, null,
						rs.getInt("freq"), 0, 0);
				keywordMap.put("i+" + rs.getInt("id"), ke);
			}
		}

		return keywordMap;
	}

	HashMap<String, String> getEngineDB() throws SQLException {
		JedisOperater op = new JedisOperater();
		return op.getAllengine();
	}

}

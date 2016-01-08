package com.hou.guanggu.Infosource.checkWebsite.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;
import com.hou.guanggu.Infosource.checkWebsite.ConnectionFactory;
import com.hou.guanggu.Infosource.checkWebsite.dao.InfosourceDao;
import com.hou.guanggu.Infosource.checkWebsite.model.Engine;
import com.hou.guanggu.Infosource.checkWebsite.model.Infosource;
import com.hou.guanggu.Infosource.checkWebsite.model.Keyword;
import com.mysql.jdbc.Statement;

import redis.clients.jedis.Jedis;

/**
 * @author houweitao
 * @date 2016年1月8日 上午11:38:01
 */

public class JedisOperater {
	private static Jedis jedis;

	static {
		jedis = new JedisFactory().getInstance();
	}

	public void refresh() {
		del();

		HashMap<String, String> infosourceList = getAllinfosource();
		for (Entry<String, String> entry : infosourceList.entrySet()) {
			jedis.hset("LOG$INFOSOURCE", entry.getKey(), entry.getValue());
		}

		HashMap<String, String> keywordList = getAllkeyword();
		for (Entry<String, String> entry : keywordList.entrySet()) {
			jedis.hset("LOG$KEYWORD", entry.getKey(), entry.getValue());
		}

		HashMap<String, String> engineList = getAllengine();
		for (Entry<String, String> entry : engineList.entrySet()) {
			jedis.hset("LOG$ENGINE", entry.getKey(), entry.getValue());
		}
	}

	void del() {
		jedis.del("LOG$INFOSOURCE");
		jedis.del("LOG$KEYWORD");
		jedis.del("LOG$ENGINE");
	}

	HashMap<String, String> getAllinfosource() {
		Connection conn = ConnectionFactory.getInstance().makeConnection();
		Statement st;
		try {
			st = (Statement) conn.createStatement();
			ResultSet rs = st.executeQuery("select * from wdyq_infosource");

			HashMap<String, String> hm = new HashMap<String, String>();

			while (rs.next()) {
				int id = rs.getInt("id");
				String url = rs.getString("url");
				String website = rs.getString("website");
				int freq = rs.getInt("freq");
				Infosource infosource = new Infosource(id);
				infosource.setFreq(freq);
				infosource.setUrl(url);
				infosource.setWebsite(website);
				hm.put("i-" + id, JSON.toJSONString(infosource));
			}

			return hm;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	HashMap<String, String> getAllkeyword() {
		Connection conn = ConnectionFactory.getInstance().makeConnection();
		Statement st;
		try {
			HashMap<String, String> hm = new HashMap<String, String>();
			st = (Statement) conn.createStatement();
			ResultSet rs = st.executeQuery("select * from wdyq_keyword");
			while (rs.next()) {
				int id = rs.getInt("id");
				String keyword = rs.getString("keyword");
				int freq = rs.getInt("freq");
				Keyword key = new Keyword(id);
				key.setFreq(freq);
				key.setKeyword(keyword);
				hm.put("s-" + id, JSON.toJSONString(key));
			}
			return hm;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	HashMap<String, String> getAllengine() {
		Connection conn = ConnectionFactory.getInstance().makeConnection();
		Statement st;
		try {
			HashMap<String, String> hm = new HashMap<String, String>();
			st = (Statement) conn.createStatement();
			ResultSet rs = st.executeQuery("select * from wdyq_searchengine");
			while (rs.next()) {
				int engineId = rs.getInt("id");
				String name = rs.getString("name");
				String url = rs.getString("url");
				Engine engine = new Engine(engineId, name, url);
				hm.put("e-" + engineId, JSON.toJSONString(engine));
			}
			return hm;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}

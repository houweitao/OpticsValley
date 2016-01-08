package com.hou.guanggu.Infosource.checkWebsite.util;

import java.net.SocketTimeoutException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.hou.guanggu.Infosource.checkWebsite.ConnectionFactory;
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
	private static final Logger log = LoggerFactory.getLogger(JedisOperater.class);

	private static Jedis jedis;

	static {
		jedis = new JedisFactory().getInstance();
	}

	public void refresh() {
		del();
		init();
	}

	public void init() {
		log.info("beigin init..");

		log.info("fetch engine..");
		HashMap<String, String> engineList = getAllengine();
		jedis.hmset("LOG$ENGINE", engineList);
		log.info("end of fetch engine..");

		log.info("fetch infosoure..");
		HashMap<String, String> infosourceList = getAllinfosource();
		jedis.hmset("LOG$INFOSOURCE", infosourceList);
		log.info("end of fetch infosource..");

		log.info("fetch keyword..");
		HashMap<String, String> keywordList = getAllkeyword();
		jedis.hmset("LOG$KEYWORD", keywordList);
		log.info("end of fetch keyword..");

		log.info("end of init");
	}

	public void del() {
		log.info("begin of del");
		jedis.del("LOG$INFOSOURCE");
		jedis.del("LOG$KEYWORD");
		jedis.del("LOG$ENGINE");
		log.info("end of del");
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
			conn.close();
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
			ResultSet rs = st.executeQuery("select * from wdyq_keywords");
			while (rs.next()) {
				int id = rs.getInt("id");
				String keyword = rs.getString("keyword");
				int freq = rs.getInt("freq");
				Keyword key = new Keyword(id);
				key.setFreq(freq);
				key.setKeyword(keyword);
				hm.put("s-" + id, JSON.toJSONString(key));
			}
			conn.close();
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
			conn.close();
			return hm;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}

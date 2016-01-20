package com.hou.guanggu.Infosource.checkWebsite.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.codec.digest.DigestUtils;
import org.fastdb.DB;
import org.fastdb.DBQuery;
import org.fastdb.DBRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.hou.guanggu.Infosource.checkWebsite.ConnectionFactory;
import com.hou.guanggu.Infosource.checkWebsite.model.Engine;
import com.hou.guanggu.Infosource.checkWebsite.model.Info;
import com.hou.guanggu.Infosource.checkWebsite.model.Keyword;
import com.hou.guanggu.Infosource.checkWebsite.util.JedisFactory;
import com.mysql.jdbc.Statement;

import redis.clients.jedis.Jedis;

/**
 * @author houweitao
 * @date 2015年12月29日 上午11:02:12
 */

public class KeywordDao {
	private static final Logger log = LoggerFactory.getLogger(KeywordDao.class);
//	private Jedis jedis = new JedisFactory().getInstance();
	private String keyKeyword = "LOG$KEYWORD";
	private String keyEngine = "LOG$ENGINE";
	private String save = "LOG$SAVE$KEYWORD";
	DBQuery nativeQuery = DB.createNativeQuery("select * from `wdyq_keywords_copy` where `id`=?");
	DBQuery updateQuery = DB.createNativeQuery("update `wdyq_keywords_copy` set `freq` =? where `id`=?");

	public void updateFreqFastDB(Info info, int changeNum) {
		int id = Integer.valueOf(info.getInfomation().split("-")[2]);
		nativeQuery.setParameter(1, id);
		int p = 1;
		int nowFreq = nativeQuery.findUnique().getInt("freq");
		System.out.println(nowFreq);
		if (nowFreq + changeNum > 0)
			nowFreq = nowFreq + changeNum;

		updateQuery.setParameter(p++, nowFreq);
		updateQuery.setParameter(p++, id);
		updateQuery.executeUpdate();
//		log.info("插入成功！");
	}

	public void persist(Info info) {
		int id = Integer.valueOf(info.getInfomation().split("-")[2]);
		int engineId = Integer.valueOf(info.getInfomation().split("-")[1]);

		DBRow key = getKeyword(id);
		DBRow engine = getEngine(engineId);

		Keyword keyword = new Keyword(id, key.getString("keyword"), engineId, engine.getString("name"),
				engine.getString("url"), key.getInt("freq"), info.getNewDocNum(), info.getDocNum());

		DBRow find = isNew(keyword);
		if (find != null) {
//			update
			DBQuery update = DB.createNativeQuery(
					"update `wdyq_report_keywords` set `searchNum`=?,`newDocNum`=?,`docNum`=?,`status`=? where `md5`=?");
			log.info(id + "," + keyword.getEngine() + "," + keyword.getKeyword() + " 不是新的");
			int p = 1;
			update.setParameter(p++, find.getInt("searchNum") + 1);
			update.setParameter(p++, find.getInt("newDocNum") + info.getNewDocNum());
			update.setParameter(p++, find.getInt("docNum") + info.getDocNum());
			update.setParameter(p++, info.getStatus().toString());
			update.setParameter(p++, DigestUtils.md5Hex(engineId + "-" + id).toUpperCase());
			update.executeUpdate();
		} else {
			log.info(id + "," + keyword.getEngine() + "," + keyword.getKeyword() + " 是新的");
			DBQuery insert = DB.createNativeQuery(
					"INSERT INTO wdyq_report_keywords(id,keyword,engine,name,url,searchNum,newDocNum,docNum,freq,status,md5) VALUES(?,?,?,?,?,?,?,?,?,?,?)");
			int p = 1;
			insert.setParameter(p++, keyword.getId());
			insert.setParameter(p++, keyword.getKeyword());
			insert.setParameter(p++, keyword.getEngine());
			insert.setParameter(p++, keyword.getName());
			insert.setParameter(p++, keyword.getUrl());
			insert.setParameter(p++, 1);
			insert.setParameter(p++, info.getNewDocNum());
			insert.setParameter(p++, info.getDocNum());
			insert.setParameter(p++, keyword.getFreq());
			insert.setParameter(p++, info.getStatus().toString());
			insert.setParameter(p++, DigestUtils.md5Hex(engineId + "-" + id).toUpperCase());

			insert.executeUpdate();
		}
	}

	public void persistByRedis(Info info,Jedis jedis) {
		int id = Integer.valueOf(info.getInfomation().split("-")[2]);
		int engineId = Integer.valueOf(info.getInfomation().split("-")[1]);

		String jsonKeyword = jedis.hget(keyKeyword, "s-" + id);
		String jsonEngine = jedis.hget(keyEngine, "e-" + engineId);

		Keyword keyword = JSON.parseObject(jsonKeyword, Keyword.class);
		Engine engine = JSON.parseObject(jsonEngine, Engine.class);

		keyword.setDocNum(info.getDocNum());
		keyword.setNewDocNum(info.getNewDocNum());
		keyword.setStatus(info.getStatus());
		keyword.setName(engine.getName());
		keyword.setUrl(engine.getUrl());
		keyword.setEngine(engineId);

		DBRow find = isNew(keyword);
		if (find != null) {
//			update
			DBQuery update = DB.createNativeQuery(
					"update `wdyq_report_keywords` set `searchNum`=?,`newDocNum`=?,`docNum`=?,`status`=? where `md5`=?");
			log.info(id + "," + keyword.getEngine() + "," + keyword.getKeyword() + " 不是新的");
			int p = 1;
			update.setParameter(p++, find.getInt("searchNum") + 1);
			update.setParameter(p++, find.getInt("newDocNum") + info.getNewDocNum());
			update.setParameter(p++, find.getInt("docNum") + info.getDocNum());
			update.setParameter(p++, info.getStatus().toString());
			update.setParameter(p++, DigestUtils.md5Hex(engineId + "-" + id).toUpperCase());
			update.executeUpdate();
		} else {
			log.info(id + "," + keyword.getEngine() + "," + keyword.getKeyword() + " 是新的");
			DBQuery insert = DB.createNativeQuery(
					"INSERT INTO wdyq_report_keywords(id,keyword,engine,name,url,searchNum,newDocNum,docNum,freq,status,md5) VALUES(?,?,?,?,?,?,?,?,?,?,?)");
			int p = 1;
			insert.setParameter(p++, keyword.getId());
			insert.setParameter(p++, keyword.getKeyword());
			insert.setParameter(p++, keyword.getEngine());
			insert.setParameter(p++, keyword.getName());
			insert.setParameter(p++, keyword.getUrl());
			insert.setParameter(p++, 1);
			insert.setParameter(p++, info.getNewDocNum());
			insert.setParameter(p++, info.getDocNum());
			insert.setParameter(p++, keyword.getFreq());
			insert.setParameter(p++, info.getStatus().toString());
			insert.setParameter(p++, DigestUtils.md5Hex(engineId + "-" + id).toUpperCase());

			insert.executeUpdate();
		}
	}

	public void persistTotalyByRedis(Info info,Jedis jedis) throws Exception {
		int id = Integer.valueOf(info.getInfomation().split("-")[2]);
		int engineId = Integer.valueOf(info.getInfomation().split("-")[1]);

		String jsonKeyword = jedis.hget(keyKeyword, "s-" + id);
		String jsonEngine = jedis.hget(keyEngine, "e-" + engineId);

		Keyword keyword = JSON.parseObject(jsonKeyword, Keyword.class);
		Engine engine = JSON.parseObject(jsonEngine, Engine.class);

		keyword.setDocNum(info.getDocNum());
		keyword.setNewDocNum(info.getNewDocNum());
		keyword.setStatus(info.getStatus());
		keyword.setName(engine.getName());
		keyword.setUrl(engine.getUrl().replace("[keyword]", keyword.getKeyword()));
		keyword.setEngine(engineId);
		keyword.setSearchNum(1);
		keyword.setTime(info.getTime());

//		String old = jedis.hget(save, jsonKeyword);
		String old = null;
		try {
			old = jedis.hget(save, jsonKeyword);
		} catch (Exception e) {
			old = null;
		}

		if (old == null || old.length() == 0) {
			log.info(keyword.getName() + "-" + keyword.getName() + "is new");
			jedis.hset(save, jsonKeyword, JSON.toJSONString(keyword));
		} else {
			log.info("old keyword: " + old);
			log.info(keyword.getName() + "-" + keyword.getName() + " is not new");
			Keyword oldKeyword = JSON.parseObject(old, Keyword.class);
			keyword.setDocNum(keyword.getDocNum() + oldKeyword.getDocNum());
			keyword.setNewDocNum(keyword.getNewDocNum() + oldKeyword.getNewDocNum());
			keyword.setSearchNum(oldKeyword.getSearchNum() + 1);
			jedis.hset(save, jsonKeyword, JSON.toJSONString(keyword));
		}

		jedis.quit();
		jedis.disconnect();
	}

	private DBRow isNew(Keyword keyword) {
		// TODO Auto-generated method stub
		String md5 = DigestUtils.md5Hex(keyword.getEngine() + "-" + keyword.getId()).toUpperCase();
		DBQuery inq = DB.createNativeQuery("SELECT * from wdyq_report_keywords WHERE md5  = ?");
		inq.setParameter(1, md5);
		DBRow row = inq.findUnique();
		return row;
	}

	private DBRow getEngine(int engineId) {
		DBQuery query = DB.createNativeQuery("select * from `wdyq_searchengine` where `id`=?");
		query.setParameter(1, engineId);
		DBRow row = query.findUnique();
		return row;
	}

	private DBRow getKeyword(int id) {
		DBQuery query = DB.createNativeQuery("select * from `wdyq_keywords_copy` where `id`=?");
		query.setParameter(1, id);
		DBRow row = query.findUnique();
		return row;
	}

	public ResultSet getAll() {
		Connection conn = ConnectionFactory.getInstance().makeConnection();
		Statement st;
		try {
			st = (Statement) conn.createStatement();
			ResultSet rs = st.executeQuery("select * from wdyq_report_keywords");
			return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}

package com.hou.guanggu.Infosource.checkWebsite.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.fastdb.DB;
import org.fastdb.DBQuery;
import org.fastdb.DBRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.hou.guanggu.Infosource.checkWebsite.ConnectionFactory;
import com.hou.guanggu.Infosource.checkWebsite.model.Info;
import com.hou.guanggu.Infosource.checkWebsite.model.Infosource;
import com.hou.guanggu.Infosource.checkWebsite.util.AppendFile;
import com.hou.guanggu.Infosource.checkWebsite.util.JedisFactory;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.sql.Connection;
import com.mysql.jdbc.Statement;

import redis.clients.jedis.Jedis;

/**
 * @author houweitao
 * @date 2015年12月29日 上午11:01:55
 */

public class InfosourceDao {
	private static final Logger log = LoggerFactory.getLogger(InfosourceDao.class);
//	private Jedis jedis = new JedisFactory().getInstance();
	private String key = "LOG$INFOSOURCE";
	private String save = "LOG$SAVE$INFOSOURCE";
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss EE");
	private DBQuery nativeQuery = DB.createNativeQuery("select * from `wdyq_infosource_copy` where `id`=?");
	private DBQuery updateQuery = DB.createNativeQuery("update `wdyq_infosource_copy` set `freq` =? where `id`=?");

	public void updateFreqFastDB(Info info, int changeNum) {
		int id = Integer.valueOf(info.getInfomation().split("-")[1]);
		int p = 1;

		int nowFreq = getInfoSource(id).getInt("freq");
		System.out.println(nowFreq);
		if (nowFreq + changeNum > 0)
			nowFreq = nowFreq + changeNum;

		updateQuery.setParameter(p++, nowFreq);
		updateQuery.setParameter(p++, id);
		updateQuery.executeUpdate();
//		log.info("插入成功！");
	}

	private DBRow getInfoSource(int id) {
		nativeQuery.setParameter(1, id);
		DBRow row = nativeQuery.findUnique();
		return row;
	}

	public void persist(Info info) {
		int id = Integer.valueOf(info.getInfomation().split("-")[1]);
		DBRow row = getInfoSource(id);

		Infosource infosource = new Infosource(id, row.getInt("freq"), row.getString("url"), row.getString("website"),
				info.getNewDocNum(), info.getDocNum(), info.getStatus(), info.getTime());

		DBRow find = isNew(infosource);

		if (find != null) {
			DBQuery update = DB.createNativeQuery(
					"update `wdyq_report_infosource` set `searchNum`=?,`newDocNum`=?,`docNum`=?,`status`=? where `md5`=?");
			log.info(id + "," + infosource.getWebsite() + " 不是新的");
			int p = 1;
			update.setParameter(p++, find.getInt("searchNum") + 1);
			update.setParameter(p++, find.getInt("newDocNum") + info.getNewDocNum());
			update.setParameter(p++, find.getInt("docNum") + info.getDocNum());
			update.setParameter(p++, info.getStatus().toString());
			update.setParameter(p++, DigestUtils.md5Hex(id + "md5").toUpperCase());
			update.executeUpdate();
		} else {
			log.info(id + "," + row.getString("website") + " 是新的");
			DBQuery insert = DB.createNativeQuery(
					"INSERT INTO wdyq_report_infosource(id,url,website,searchNum,newDocNum,docNum,freq,status,md5) VALUES(?,?,?,?,?,?,?,?,?)");
			int p = 1;
			insert.setParameter(p++, row.getInt("id"));
			insert.setParameter(p++, row.getString("url"));
			insert.setParameter(p++, row.getString("website"));
			insert.setParameter(p++, 1);
			insert.setParameter(p++, info.getNewDocNum());
			insert.setParameter(p++, info.getDocNum());
			insert.setParameter(p++, infosource.getFreq());
			insert.setParameter(p++, info.getStatus().toString());
			insert.setParameter(p++, DigestUtils.md5Hex(id + "md5").toUpperCase());

			insert.executeUpdate();
		}

	}

	public void persistByRedis(Info info, Jedis jedis) {
		int id = Integer.valueOf(info.getInfomation().split("-")[1]);

		String json = jedis.hget(key, info.getInfomation());
		Infosource infosource = JSON.parseObject(json, Infosource.class);

		infosource.setNewDocNum(info.getNewDocNum());
		infosource.setDocNum(info.getDocNum());
		infosource.setTime(info.getTime());
		infosource.setStatus(info.getStatus());

		DBRow find = isNew(infosource);

		if (find != null) {
			DBQuery update = DB.createNativeQuery(
					"update `wdyq_report_infosource` set `searchNum`=?,`newDocNum`=?,`docNum`=?,`status`=? where `md5`=?");
			log.info(id + "," + infosource.getWebsite() + " 不是新的");
			int p = 1;
			update.setParameter(p++, find.getInt("searchNum") + 1);
			update.setParameter(p++, find.getInt("newDocNum") + info.getNewDocNum());
			update.setParameter(p++, find.getInt("docNum") + info.getDocNum());
			update.setParameter(p++, info.getStatus().toString());
			update.setParameter(p++, DigestUtils.md5Hex(id + "md5").toUpperCase());
			update.executeUpdate();
		} else {
			log.info(id + "," + infosource.getWebsite() + " 是新的");
			DBQuery insert = DB.createNativeQuery(
					"INSERT INTO wdyq_report_infosource(id,url,website,searchNum,newDocNum,docNum,freq,status,md5) VALUES(?,?,?,?,?,?,?,?,?)");
			int p = 1;
			insert.setParameter(p++, infosource.getId());
			insert.setParameter(p++, infosource.getUrl());
			insert.setParameter(p++, infosource.getWebsite());
			insert.setParameter(p++, 1);
			insert.setParameter(p++, info.getNewDocNum());
			insert.setParameter(p++, info.getDocNum());
			insert.setParameter(p++, infosource.getFreq());
			insert.setParameter(p++, info.getStatus().toString());
			insert.setParameter(p++, DigestUtils.md5Hex(id + "md5").toUpperCase());

			insert.executeUpdate();
		}

	}

	public void persistTotalyByRedis(Info info, Jedis jedis) throws Exception {
//		int id = Integer.valueOf(info.getInfomation().split("-")[1]);
		String json = jedis.hget(key, info.getInfomation());
		Infosource infosource = JSON.parseObject(json, Infosource.class);

		infosource.setNewDocNum(info.getNewDocNum());
		infosource.setDocNum(info.getDocNum());
		infosource.setTime(info.getTime());
		infosource.setStatus(info.getStatus());
		String old = null;
		try {
			old = jedis.hget(save, info.getInfomation());
		} catch (Exception e) {
			old = null;
		}

		if (old == null || old.length() == 0) {
			log.info(infosource.getWebsite() + " is new");
			infosource.setSearchNum(1);
			jedis.hset(save, info.getInfomation(), JSON.toJSONString(infosource));
		} else {
			log.info("old infosource: " + old);
			log.info(infosource.getWebsite() + " is not new");
			Infosource oldInfosource = JSON.parseObject(old, Infosource.class);
			infosource.setDocNum(infosource.getDocNum() + oldInfosource.getDocNum());
			infosource.setNewDocNum(infosource.getNewDocNum() + oldInfosource.getNewDocNum());
			infosource.setSearchNum(oldInfosource.getSearchNum() + 1);
			jedis.hset(save, info.getInfomation(), JSON.toJSONString(infosource));
		}
	}

	public void delInfosource(Info info, Jedis jedis) {
//		jedis.hdel(save, info.getInfomation());

		if (jedis.hdel(save, info.getInfomation()) == 1) {
			Infosource infosource = JSON.parseObject(jedis.hget(key, info.getInfomation()), Infosource.class);
			fixNormal(info.getInfomation() + ", " + infosource.getWebsite() + ", " + infosource.getUrl());
			log.info("del " + info.getInfomation());
		}
	}

	private void fixNormal(String info) {
		String recordPath = "fixNormal.log";
		String conent = "修正 @" + df.format(new Date()) + "  " + info + "\r\n";
		File file = new File(recordPath);
		try {
			if (!file.exists() && file.createNewFile()) {
				log.info("Create file successed");
			}

			AppendFile app = new AppendFile();
			app.method1(recordPath, conent);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public DBRow isNew(Infosource infosource) {
		String md5 = DigestUtils.md5Hex(infosource.getId() + "md5").toUpperCase();
		DBQuery inq = DB.createNativeQuery("SELECT * from wdyq_report_infosource WHERE md5  = ?");
		inq.setParameter(1, md5);
		DBRow row = inq.findUnique();
		System.out.println(row);

		if (row == null)
			return null;
		else
			return row;
	}

	public ResultSet getAll() {
		Connection conn = ConnectionFactory.getInstance().makeConnection();
		Statement st;
		try {
			st = (Statement) conn.createStatement();
			ResultSet rs = st.executeQuery("select * from wdyq_report_infosource");
			return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}

package com.hou.guanggu.Infosource.checkWebsite.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import org.fastdb.DB;
import org.fastdb.DBQuery;
import org.fastdb.DBRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hou.guanggu.Infosource.checkWebsite.ConnectionFactory;
import com.hou.guanggu.Infosource.checkWebsite.model.Info;
import com.hou.guanggu.Infosource.checkWebsite.model.Infosource;
import com.hou.guanggu.Infosource.checkWebsite.model.Keyword;

/**
 * @author houweitao
 * @date 2015年12月29日 上午11:01:55
 */

public class InfosourceDao {
	private static final Logger log = LoggerFactory.getLogger(InfosourceDao.class);
	DBQuery nativeQuery = DB.createNativeQuery("select * from `wdyq_infosource_copy` where `id`=?");
	DBQuery updateQuery = DB.createNativeQuery("update `wdyq_infosource_copy` set `freq` =? where `id`=?");

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

	//暂时没用？
//	public List<Infosource> findList(Info info) {
//		List<Infosource> list = new LinkedList<Infosource>();
//
//		int id = Integer.valueOf(info.getInfomation().split("-")[1]);
//		nativeQuery.setParameter(1, id);
//
//		DBRow row = nativeQuery.findUnique();
//
////		Infosource in = new Infosource(2);
//
//		return list;
//	}

	public void update(Infosource infosource) {
		int id = Integer.valueOf(infosource.getId());
		DBQuery update = DB.createNativeQuery("update `wdyq_report_infosource` set set `url`=?,`freq` =? where `id`=?");
		DBQuery insert = DB.createNativeQuery("INSERT INTO wdyq_report_infosource(id,url,website,searchNum,newDocNum,docNum,freq) VALUES(?,?,?,?,?,?,?)");
		if (getInfoSource(id) != null) {
//			update
		} else {
//			insert;
		}

	}
}

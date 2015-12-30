package com.hou.guanggu.Infosource.checkWebsite.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.fastdb.DB;
import org.fastdb.DBQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hou.guanggu.Infosource.checkWebsite.ConnectionFactory;
import com.hou.guanggu.Infosource.checkWebsite.Info;
import com.hou.guanggu.Infosource.checkWebsite.model.Infosource;

/**
 * @author houweitao
 * @date 2015年12月30日 上午9:40:39
 */

public class InfosourceDaoBackup {

	private static final Logger log = LoggerFactory.getLogger(InfosourceDao.class);
	private static Connection conn = ConnectionFactory.getInstance().makeConnection();
	DBQuery nativeQuery = DB.createNativeQuery("select * from `wdyq_infosource_copy` where `id`=?");
	DBQuery updateQuery = DB.createNativeQuery("update `wdyq_infosource_copy` set `freq` =? where `id`=?");

	public void updateFreqFastDB(Info info, int changeNum) {
		int id = Integer.valueOf(info.getInfomation().split("-")[1]);
		nativeQuery.setParameter(1, id);
		int p = 1;
		int nowFreq = nativeQuery.findUnique().getInt("freq");
//				.executeUpdate();
		System.out.println(nowFreq);
		if (nowFreq + changeNum > 0)
			nowFreq = nowFreq + changeNum;

		updateQuery.setParameter(p++, nowFreq);
		updateQuery.setParameter(p++, id);
		updateQuery.executeUpdate();
//		log.info("插入成功！");
	}

	public void updateFreq(Infosource info, int changeNum) {
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("select * from wdyq_infosource_copy where id=?");
			ps.setInt(1, info.getId());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int freq = rs.getInt("freq");
				System.out.println("freq: " + freq);
				if (freq + changeNum > 0)
					freq = freq + changeNum;

				ps = conn.prepareStatement("update wdyq_infosource_copy set freq =? where id=?");
				ps.setInt(1, freq);
				ps.setInt(2, info.getId());
				ps.execute();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateFreq(Info info, int changeNum) {
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("select * from wdyq_infosource_copy where id=?");
			ps.setInt(1, Integer.valueOf(info.getInfomation().split("-")[1]));
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int freq = rs.getInt("freq");
				System.out.println("freq: " + freq);
				if (freq + changeNum > 0)
					freq = freq + changeNum;

				ps = conn.prepareStatement("update wdyq_infosource_copy set freq =? where id=?");
				ps.setInt(1, freq);
				ps.setInt(2, Integer.valueOf(info.getInfomation().split("-")[1]));
				ps.execute();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

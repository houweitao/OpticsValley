package com.hou.guanggu.Infosource.checkWebsite.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.fastdb.DB;
import org.fastdb.DBQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hou.guanggu.Infosource.checkWebsite.ConnectionFactory;
import com.hou.guanggu.Infosource.checkWebsite.Info;
import com.hou.guanggu.Infosource.checkWebsite.model.Infosource;

/**
 * @author houweitao
 * @date 2015年12月29日 上午11:01:55
 */

public class InfosourceDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(InfosourceDao.class);
//	DBQuery nativeQuery = DB.createNativeQuery("select freq from `wdyq_infosource_copy` where `id`=?");
//	DBQuery updateQuery = DB.createNativeQuery("update `wdyq_infosource_copy` Set `freq` =? where `id`=?");
	private static Connection conn = ConnectionFactory.getInstance().makeConnection();

	public void updateFreq(Infosource info, int changeNum) {
//		nativeQuery.setParameter(1, info.getId());
//		int p = 1;
//		int nowFreq = nativeQuery.executeUpdate();
//		if (nowFreq + changeNum > 0)
//			nowFreq = nowFreq + changeNum;
//
//		updateQuery.setParameter(p++, nowFreq);
//		updateQuery.setParameter(p++, info.getId());

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
	
	
	public void updateFreq(int id, int changeNum) {
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("select * from wdyq_infosource_copy where id=?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int freq = rs.getInt("freq");
				System.out.println("freq: " + freq);
				if (freq + changeNum > 0)
					freq = freq + changeNum;

				ps = conn.prepareStatement("update wdyq_infosource_copy set freq =? where id=?");
				ps.setInt(1, freq);
				ps.setInt(2, id);
				ps.execute();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

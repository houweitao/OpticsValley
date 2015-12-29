package com.hou.guanggu.Infosource.checkWebsite.dao;

import org.fastdb.DB;
import org.fastdb.DBQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hou.guanggu.Infosource.checkWebsite.Info;

/**
 * @author houweitao
 * @date 2015年12月29日 上午11:02:12
 */

public class KeywordDao {
	private static final Logger log = LoggerFactory.getLogger(KeywordDao.class);
	DBQuery nativeQuery = DB.createNativeQuery("select * from `wdyq_keywords_copy` where `id`=?");
	DBQuery updateQuery = DB.createNativeQuery("update `wdyq_keywords_copy` set `freq` =? where `id`=?");

	public void updateFreqFastDB(Info info, int changeNum) {
		int id = Integer.valueOf(info.getInfomation().split("-")[2]);
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

}

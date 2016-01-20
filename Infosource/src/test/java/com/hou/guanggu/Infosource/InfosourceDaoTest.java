package com.hou.guanggu.Infosource;

import org.fastdb.DB;
import org.fastdb.DBQuery;

import com.hou.guanggu.Infosource.checkWebsite.dao.InfosourceDao;
import com.hou.guanggu.Infosource.checkWebsite.model.Info;
import com.hou.guanggu.Infosource.checkWebsite.model.InfoStatus;
import com.hou.guanggu.Infosource.checkWebsite.model.Infosource;
import com.hou.guanggu.Infosource.checkWebsite.util.JedisPoolFactory;

import redis.clients.jedis.Jedis;

/**
 * @author houweitao
 * @date 2015年12月29日 上午11:33:05
 */

public class InfosourceDaoTest {

	public static void main(String[] args) {
		InfosourceDao dao = new InfosourceDao();
		Infosource infosource = new Infosource(0);
		infosource.setTime("2015-09-23 12:57:58.503");
		System.out.println(dao.isNew(infosource));

		InfosourceDaoTest test = new InfosourceDaoTest();
//		test.testUpdate();
		test.persistByRedis();

	}

	void persistByRedis() {
		Info info = new Info(InfoStatus.BAD, "i-1416", 0, 0, "2015-09-23 12:57:58.50");
		InfosourceDao dao = new InfosourceDao();
		Jedis jedis=new JedisPoolFactory().getInstance().getResource();
		dao.persistByRedis(info, jedis);
	}

	void testUpdate() {
		DBQuery update = DB
				.createNativeQuery("update `wdyq_report_infosource` set `searchNum`=?,`url`=? where `md5`=?");
		int p = 1;
		String tt = "hou";
		update.setParameter(p++, 2222);
		update.setParameter(p++, tt + "hahahh");
		update.setParameter(p, "3CD6F7ABD80C0506A6E3707C1F9941E9");

		System.out.println(update.toString());

		update.executeUpdate();
	}

//	@Test
//	void testFastDB() {
//		System.out.println("test");
//		Info info = new Info(InfoStatus.LOW, "i-1416");
//		InfosourceDao infosourceDao = new InfosourceDao();
//		infosourceDao.updateFreqFastDB(info, 100);
//	}
}

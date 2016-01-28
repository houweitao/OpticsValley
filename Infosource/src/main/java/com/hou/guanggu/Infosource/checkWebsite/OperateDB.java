package com.hou.guanggu.Infosource.checkWebsite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hou.guanggu.Infosource.checkWebsite.dao.InfosourceDao;
import com.hou.guanggu.Infosource.checkWebsite.dao.KeywordDao;
import com.hou.guanggu.Infosource.checkWebsite.model.Info;
import com.hou.guanggu.Infosource.checkWebsite.util.JedisFactory;
import com.hou.guanggu.Infosource.checkWebsite.util.JedisPoolFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author houweitao
 * @date 2015年12月28日 下午4:16:02
 */

public class OperateDB {
	private static final Logger log = LoggerFactory.getLogger(OperateDB.class);
	private InfosourceDao infosourceDao = new InfosourceDao();
	private KeywordDao keywordDao = new KeywordDao();
	private final String saveInfosource = "LOG$SAVE$INFOSOURCE";
	private final String saveKeyword = "LOG$SAVE$KEYWORD";
	private JedisPool pool = new JedisPoolFactory().getInstance();

	//插入数据库。
	public void insertDB(Info info) {
		if (info.getInfomation().charAt(0) == 'i') {
			InfosourceDao dao = new InfosourceDao();
			dao.persist(info);
		} else if (info.getInfomation().charAt(0) == 's') {
			KeywordDao dao = new KeywordDao();
			dao.persist(info);
		}
	}

	//插入数据库。用redis。
	public void insertByRedis(Info info) {
		Jedis jedis = pool.getResource();

		if (info.getInfomation().charAt(0) == 'i') {
			try {
				infosourceDao.persistTotalyByRedis(info, jedis);
				pool.returnResource(jedis);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				pool.returnBrokenResource(jedis);
			}
		} else if (info.getInfomation().charAt(0) == 's') {
			try {
				keywordDao.persistTotalyByRedis(info, jedis);
				pool.returnResource(jedis);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				pool.returnBrokenResource(jedis);
			}
		}
	}

	public void delNormal(Info info) {
		Jedis jedis = pool.getResource();
		if (info.getInfomation().charAt(0) == 'i') {
			try {
				infosourceDao.delInfosource(info, jedis);
				pool.returnResource(jedis);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				pool.returnBrokenResource(jedis);
			}
		} else if (info.getInfomation().charAt(0) == 's') {
			try {
				keywordDao.delKeyword(info, jedis);
				pool.returnResource(jedis);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				pool.returnBrokenResource(jedis);
			}
		}
	}

	//修改爬取的频率
	public void dealDB(Info info) {
		if (info.getInfomation().charAt(0) == 'i') {
			//信息源
			InfosourceDao dao = new InfosourceDao();

			switch (info.getStatus()) {
			case BAD:
				dao.updateFreqFastDB(info, 5);
				break;
			case LOW:
				dao.updateFreqFastDB(info, -1);
				break;
			case HIGH:
				dao.updateFreqFastDB(info, 1);
				break;
			case NICE:
				break;
			case NOTEXIST://+10
				dao.updateFreqFastDB(info, 10);
				break;
			case UNKNOWN:
				break;

			default:
				break;

			}
		} else if (info.getInfomation().charAt(0) == 's') {
			//搜索源
			KeywordDao dao = new KeywordDao();
			switch (info.getStatus()) {
			case BAD:
				dao.updateFreqFastDB(info, 5);
				break;
			case LOW:
				dao.updateFreqFastDB(info, -1);
				break;
			case HIGH:
				dao.updateFreqFastDB(info, 1);
				break;
			case NICE:
				break;
			case NOTEXIST://+10
				dao.updateFreqFastDB(info, 10);
				break;
			case UNKNOWN:
				break;

			default:
				break;

			}
		} else {
			log.info("not unified input..");
		}
	}

	public void cleanRedis() {
		// TODO Auto-generated method stub
		Jedis jedis = new JedisFactory().getInstance();
		jedis.del(saveKeyword);
		jedis.del(saveInfosource);
	}
}

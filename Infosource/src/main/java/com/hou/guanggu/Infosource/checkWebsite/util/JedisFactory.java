package com.hou.guanggu.Infosource.checkWebsite.util;

import java.io.InputStream;
import java.util.Properties;

import redis.clients.jedis.Jedis;

/**
 * @author houweitao
 * @date 2016年1月8日 上午10:54:18
 */

public class JedisFactory {
	private static Properties redisConf = new Properties();
	private static Jedis jedis;

	static {
		try {
			InputStream in = ConnectionFactory.class.getClassLoader().getResourceAsStream("redis.properties");
//			InputStream in = ConnectionFactory.class.getClassLoader().getResourceAsStream("localRedis.properties");
			redisConf.load(in);

			jedis = new Jedis(redisConf.getProperty("redis.host"), Integer.valueOf(redisConf.getProperty("redis.port")),
					10000);
		} catch (Exception e) {
			System.out.println("=====无配置文件======");
		}
	}

	public Jedis getInstance() {
		return jedis;
	}
}

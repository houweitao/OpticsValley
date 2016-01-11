package com.hou.guanggu.Infosource.util;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.hou.guanggu.Infosource.checkWebsite.util.JedisFactory;

import redis.clients.jedis.Jedis;

/**
 * @author houweitao
 * @date 2016年1月8日 上午10:03:09
 */

public class RedisTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JedisFactory factory = new JedisFactory();
		Jedis jedis = factory.getInstance();
		jedis.rpush("TEST$JEDIS", JSON.toJSONString("1022"));
		System.out.println(jedis.lpop("TEST$JEDIS"));
		jedis.del("TEST$JEDIS");

		System.out.println(jedis.llen("crawler.targets.queue"));
		System.out.println(jedis.llen("WEIXIN$WORD"));

		RedisTest test = new RedisTest();
//		test.testList(jedis);

		System.out.println("get size: " + jedis.hget("LOG$KEYWORD", "s-322861"));
		System.out.println("get size: " + jedis.hlen("LOG$KEYWORD"));

//		System.out.println(jedis.exists("TEST$JEDIS"));

//		for (int i = 4444; i < 4500; i++) {
//			System.out.println("get size: " + jedis.hget("LOG$KEYWORD", "s-" + i));
//		}
		
		System.out.println("get all: " + jedis.hgetAll("LOG$SAVE$INFOSOURCE"));
	}

	public void testList(Jedis redis) {
		//hset key field value将哈希表key中的域field的值设为value。   
		String key = "$LOGS";

		redis.hset(key, "i-2222", "value1");
		redis.hset(key, "s-4242", "value2");
		redis.hset(key, "i-5555", "value3");

		System.out.println(redis.keys(key));
		//返回哈希表key中，一个或多个给定域的值。   
		List<String> list = redis.hmget(key, "i-2222");
		for (String tmp : list) {
			System.out.println("get: " + tmp);
		}
		System.out.println("size : " + redis.hmget(key, "s-4242").get(0));
		System.out.println("size : " + redis.hmget(key, "s-4242"));
		System.out.println("size : " + redis.hget(key, "s-4242"));//nice
		System.out.println("size : " + redis.hvals(key));

//		System.out.println(redis.lrange("WEIXIN$WORD",1, 100));

		System.out.println(redis.exists(key));
		System.out.println("delete: " + redis.del(key));
		System.out.println("delete: " + redis.del(key));
		System.out.println(redis.exists(key));
	}
}

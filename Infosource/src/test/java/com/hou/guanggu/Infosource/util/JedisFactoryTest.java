package com.hou.guanggu.Infosource.util;

import org.junit.Test;

import com.hou.guanggu.Infosource.checkWebsite.util.JedisFactory;

import redis.clients.jedis.Jedis;

/**
 * @author houweitao
 * @date 2016年1月25日 下午12:29:16
 */

public class JedisFactoryTest {
	public static void main(String[] args) {
		getjedis();
	}
	
	private static void getjedis() {
		Jedis jedis=new JedisFactory().getInstance();
		jedis.lpush("test", "s");
		System.out.println(jedis.rpop("test"));
	}

	@Test
	public void getJedis() {
		Jedis jedis=new JedisFactory().getInstance();
		jedis.lpush("test", "s");
		System.out.println(jedis.rpop("test"));
	}
}

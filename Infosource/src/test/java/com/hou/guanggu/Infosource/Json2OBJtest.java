package com.hou.guanggu.Infosource;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.hou.guanggu.Infosource.checkWebsite.model.Infosource;
import com.hou.guanggu.Infosource.checkWebsite.util.JedisFactory;

import redis.clients.jedis.Jedis;

/**
 * @author houweitao
 * @date 2016年1月8日 下午1:43:23
 */

public class Json2OBJtest {

	@Test
	public void test() {
		Infosource inf = new Infosource(22);
		inf.setDocNum(222);
		inf.setUrl("hhahahaha");
		String json = JSON.toJSONString(inf);
		System.out.println(json);
		Infosource after = JSON.parseObject(json, Infosource.class);
		System.out.println(after.getId());
		System.out.println(after.getUrl());
	}

	@Test
	public void obj2Str() {
		Infosource inf = new Infosource(22);
		inf.setDocNum(222);
		inf.setUrl("hhahahaha");
		inf.setTime("2015-09-23 12:57:57.90");
		inf.setNewDocNum(4);
		inf.setDocNum(100);

		String str = JSON.toJSONString(inf);
		System.out.println(str);
		Infosource after = JSON.parseObject(str, Infosource.class);
		System.out.println(after.toString());

		JedisFactory factory = new JedisFactory();
		Jedis jedis = factory.getInstance();

		String json = jedis.hget("LOG$SAVE$INFOSOURCE", "i-36570");
		System.out.println(json);
		after = JSON.parseObject(json, Infosource.class);
		System.out.println(after.toString());
	}
}

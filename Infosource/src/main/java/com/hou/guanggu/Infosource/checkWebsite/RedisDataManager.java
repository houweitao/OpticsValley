package com.hou.guanggu.Infosource.checkWebsite;

import com.hou.guanggu.Infosource.checkWebsite.util.JedisOperater;

/**
 * @author houweitao
 * @date 2016年1月8日 下午2:22:36
 */

public class RedisDataManager {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RedisDataManager manager = new RedisDataManager();
//		manager.init();
		manager.del();
	}

	void init() {
		long start = System.currentTimeMillis();
		JedisOperater operater = new JedisOperater();
		operater.init();
		long end = System.currentTimeMillis();
		System.out.println((end - start) / 60 + " 秒");
	}

	void del() {
		JedisOperater operater = new JedisOperater();
		operater.del();
	}

}

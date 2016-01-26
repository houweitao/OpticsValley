package com.hou.guanggu.Infosource.checkWebsite;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.hou.guanggu.Infosource.checkWebsite.util.JedisOperater;
import com.hou.guanggu.Infosource.checkWebsite.util.JedisPoolFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author houweitao
 * @date 2016年1月20日 上午11:27:07
 * http://www.oschina.net/question/129540_28053
 */

public class AnalyseLogScheduler {
	private static String engine = "LOG$ENGINE";
	private static String keyword = "LOG$KEYWORD";
	private static String infosource = "LOG$INFOSOURCE";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JedisPool pool = new JedisPoolFactory().getInstance();
		Jedis jedis = pool.getResource();

		if (jedis.hlen(infosource) == null || jedis.hlen(infosource) == 0) {
			JedisOperater operater = new JedisOperater();
			operater.init();
		}

		ScheduledExecutorService service = Executors.newScheduledThreadPool(10);

		//ScheduleAtFixedRate 是基于固定时间间隔进行任务调度，
		//ScheduleWithFixedDelay 取决于每次任务执行的时间长短，是基于不固定时间间隔进行任务调度。

		long initialDelay1 = 1;
		long period1 = 40 * 60;
		// 从现在开始1秒钟之后，每隔 period1 秒钟执行一次job1/这里是40分钟。
		service.scheduleWithFixedDelay(new AnalyseLog(), initialDelay1, period1, TimeUnit.SECONDS);

//		long initialDelay2 = 2;
//		long delay2 = 2;
//		// 从现在开始2秒钟之后，每隔2秒钟执行一次job2
//		service.scheduleWithFixedDelay(new AnalyseLog(), initialDelay2, delay2, TimeUnit.SECONDS);
	}

}

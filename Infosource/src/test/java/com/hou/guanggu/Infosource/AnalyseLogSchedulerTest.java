package com.hou.guanggu.Infosource;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.hou.guanggu.Infosource.checkWebsite.AnalyseLog;
import com.hou.guanggu.Infosource.checkWebsite.util.JedisOperater;
import com.hou.guanggu.Infosource.checkWebsite.util.JedisPoolFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author houweitao
 * @date 2016年1月26日 下午4:13:15
 */

public class AnalyseLogSchedulerTest {
	public static void main(String[] args) {
		ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
		long initialDelay1 = 1;
		long period1 = 2;
		// 从现在开始1秒钟之后，每隔 period1 秒钟执行一次job1/这里是40分钟。
		service.scheduleAtFixedRate(new test(), initialDelay1, period1, TimeUnit.SECONDS);
	}

}

class test implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("sss");
//		System.exit(0);
		return;
	}

}

package com.hou.guanggu.Infosource.util;

import com.hou.guanggu.Infosource.checkWebsite.util.JedisOperater;

/**
 * @author houweitao
 * @date 2016年1月8日 上午11:44:28
 */

public class JedisOperaterTest {
	public static void main(String[] args) {
		JedisOperaterTest test=new JedisOperaterTest();
		test.getAll();
	}
	
	void getAll() {
		JedisOperater op = new JedisOperater();
		op.refresh();
	}
}

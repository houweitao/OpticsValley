package com.hou.guanggu.Infosource.checkWebsite.model;

/**
 * @author houweitao
 * @date 2015年12月29日 上午10:58:55
 */

public class Keyword {
	private int id;
	private int engine;
	private int freq;

	Keyword(int id, int engine, int freq) {
		this.id = id;
		this.engine = engine;
		this.freq = freq;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEngine() {
		return engine;
	}

	public void setEngine(int engine) {
		this.engine = engine;
	}

	public int getFreq() {
		return freq;
	}

	public void setFreq(int freq) {
		this.freq = freq;
	}
}

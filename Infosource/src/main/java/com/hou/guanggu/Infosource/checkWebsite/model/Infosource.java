package com.hou.guanggu.Infosource.checkWebsite.model;

/**
 * @author houweitao
 * @date 2015年12月29日 上午10:53:16
 */

public class Infosource {
	private int id;
	private int freq;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFreq() {
		return freq;
	}

	public void setFreq(int freq) {
		this.freq = freq;
	}

	public Infosource(int id, int freq) {
		this.id = id;
		this.freq = freq;
	}

	public Infosource(int id) {
		this.id = id;
	}
}

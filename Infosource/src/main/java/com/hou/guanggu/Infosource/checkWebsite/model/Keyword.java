package com.hou.guanggu.Infosource.checkWebsite.model;

/**
 * @author houweitao
 * @date 2015年12月29日 上午10:58:55
 */

public class Keyword {
	private int id;
	private String keyword;
	private int engine;
	private String name;
	private String url;
	private int freq;

	private int searchNum;
	private int newDocNum;
	private int docNum;

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

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getSearchNum() {
		return searchNum;
	}

	public void setSearchNum(int searchNum) {
		this.searchNum = searchNum;
	}

	public int getNewDocNum() {
		return newDocNum;
	}

	public void setNewDocNum(int newDocNum) {
		this.newDocNum = newDocNum;
	}

	public int getDocNum() {
		return docNum;
	}

	public void setDocNum(int docNum) {
		this.docNum = docNum;
	}
}

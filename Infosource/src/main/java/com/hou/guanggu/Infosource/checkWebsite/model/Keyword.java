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
	private InfoStatus status;
	private String time;

	public Keyword(int id, String keyword, int engine, String name, String url, int freq, int newDocNum, int docNum) {
		this.id = id;
		this.keyword = keyword;
		this.engine = engine;
		this.name = name;
		this.url = url;
		this.freq = freq;
		this.newDocNum = newDocNum;
		this.docNum = docNum;
	}

	public Keyword(int id, int engine, int freq) {
		this.id = id;
		this.engine = engine;
		this.freq = freq;
	}

	public InfoStatus getStatus() {
		return status;
	}

	public void setStatus(InfoStatus status) {
		this.status = status;
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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}

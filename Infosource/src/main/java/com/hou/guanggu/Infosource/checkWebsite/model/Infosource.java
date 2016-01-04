package com.hou.guanggu.Infosource.checkWebsite.model;

/**
 * @author houweitao
 * @date 2015年12月29日 上午10:53:16
 */

public class Infosource {
	private int id;
	private int freq;
	private String url;
	private String website;

	private int searchNum;
	private int newDocNum;
	private int docNum;

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public Infosource(int id, int freq) {
		this.id = id;
		this.freq = freq;
	}

	public Infosource(int id) {
		this.id = id;
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

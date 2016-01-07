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
	private InfoStatus status;

	private String time;

	public Infosource(int id, int freq) {
		this.id = id;
		this.freq = freq;
	}

	public Infosource(int id) {
		this.id = id;
	}

	public Infosource(int id, int freq, String url, String website, int newDocNum, int docNum, InfoStatus status,
			String time) {
		this.id = id;
		this.freq = freq;
		this.freq = freq;
		this.url = url;
		this.website = website;
		this.newDocNum = newDocNum;
		this.docNum = docNum;
		this.status = status;
		this.time = time;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

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

	public InfoStatus getStatus() {
		return status;
	}

	public void setStatus(InfoStatus status) {
		this.status = status;
	}
}

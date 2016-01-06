package com.hou.guanggu.Infosource.checkWebsite.model;

/**
 * @author houweitao
 * @date 2015年12月28日 下午3:45:39
 */

public class Info {
	private InfoStatus status;
	private String infomation;
	private int docNum;
	private int newDocNum;
	private String time;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public InfoStatus getStatus() {
		return status;
	}

	public void setStatus(InfoStatus status) {
		this.status = status;
	}

	public String getInfomation() {
		return infomation;
	}

	public void setInfomation(String infomation) {
		this.infomation = infomation;
	}

	public int getDocNum() {
		return docNum;
	}

	public void setDocNum(int docNum) {
		this.docNum = docNum;
	}

	public int getNewDocNum() {
		return newDocNum;
	}

	public void setNewDocNum(int newDocNum) {
		this.newDocNum = newDocNum;
	}

	public Info(InfoStatus status, String infomation, int docNum, int newDocNum,String time) {
		this.status = status;
		this.infomation = infomation;
		this.docNum = docNum;
		this.newDocNum = newDocNum;
		this.time=time;
	}

}

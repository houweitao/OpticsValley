package com.hou.guanggu.Infosource.checkWebsite.model;

/**
 * @author houweitao
 * @date 2015年12月28日 下午3:45:39
 */

public class Info {
	private InfoStatus status;
	private String infomation;

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

	public Info(InfoStatus status, String infomation) {
		this.status = status;
		this.infomation = infomation;
	}

}

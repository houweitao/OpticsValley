package com.hou.guanggu.Infosource.checkWebsite.model;

/**
 * @author houweitao
 * @date 2016年1月8日 下午1:31:21
 */

public class Engine {
	private int engineId;
	private String name;
	private String url;

	public Engine() {
	}

	public Engine(int engineId, String name, String url) {
		this.engineId = engineId;
		this.name = name;
		this.url = url;
	}

	public int getEngineId() {
		return engineId;
	}

	public void setEngineId(int engineId) {
		this.engineId = engineId;
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
}

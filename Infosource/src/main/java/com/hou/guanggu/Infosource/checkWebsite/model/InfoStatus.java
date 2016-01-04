package com.hou.guanggu.Infosource.checkWebsite.model;

/**
 * @author houweitao
 * @date 2015年12月28日 下午3:42:51
 */

public enum InfoStatus {
	UNKNOWN, //未定义
	NICE, //正常
	HIGH, //爬取频率过高
	LOW, //爬取频率过低
	BAD, //网页存在，但无搜索结果
	NOTEXIST//网页不存在
}

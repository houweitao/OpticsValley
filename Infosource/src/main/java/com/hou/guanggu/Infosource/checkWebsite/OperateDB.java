package com.hou.guanggu.Infosource.checkWebsite;

import com.hou.guanggu.Infosource.checkWebsite.dao.InfosourceDao;

/**
 * @author houweitao
 * @date 2015年12月28日 下午4:16:02
 */

public class OperateDB {
	public void dealDB(Info info) {
		if (info.getInfomation().charAt(0) == 'i') {
			//信息源

			InfosourceDao dao = new InfosourceDao();

			switch (info.getStatus()) {
			case BAD:
				dao.updateFreq(Integer.valueOf(info.getInfomation().split("-")[1]), 5);
				break;
			case LOW:
				dao.updateFreq(Integer.valueOf(info.getInfomation().split("-")[1]), -1);
				break;
			case HIGH:
				dao.updateFreq(Integer.valueOf(info.getInfomation().split("-")[1]), 1);
				break;
			case NICE:
				break;
			case NOTEXIST://+10
				dao.updateFreq(Integer.valueOf(info.getInfomation().split("-")[1]), 10);
				break;
			case UNKNOWN:
				break;

			default:
				break;

			}
		} else {
			//搜索源
			switch (info.getStatus()) {
			case BAD:
			case LOW:
			case HIGH:
			case NICE:
			case NOTEXIST:
			case UNKNOWN:

			default:
				break;

			}
		}
	}
}

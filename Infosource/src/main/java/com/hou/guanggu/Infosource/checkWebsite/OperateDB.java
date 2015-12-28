package com.hou.guanggu.Infosource.checkWebsite;

/**
 * @author houweitao
 * @date 2015年12月28日 下午4:16:02
 * jdbc:mysql://119.254.110.32:3001/docdb ，用户名peony，密码UdenTan#23851
 */

public class OperateDB {
	void dealDB(Info info) {
		if (info.getInfomation().charAt(0) == 'i') {
			//信息源
			switch (info.getStatus()) {
			case BAD:
			case LOW://+1
			case HIGH://-1
			case NICE:
			case NOTEXIST://+10
			case UNKNOWN:

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

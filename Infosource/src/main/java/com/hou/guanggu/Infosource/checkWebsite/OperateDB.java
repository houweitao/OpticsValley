package com.hou.guanggu.Infosource.checkWebsite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hou.guanggu.Infosource.checkWebsite.dao.InfosourceDao;
import com.hou.guanggu.Infosource.checkWebsite.dao.KeywordDao;
import com.hou.guanggu.Infosource.checkWebsite.model.Info;

/**
 * @author houweitao
 * @date 2015年12月28日 下午4:16:02
 */

public class OperateDB {
	private static final Logger log = LoggerFactory.getLogger(OperateDB.class);

	//插入数据库。
	public void insertDB(Info info) {
		if (info.getInfomation().charAt(0) == 'i') {
			InfosourceDao dao = new InfosourceDao();
			
			
			
		} else if (info.getInfomation().charAt(0) == 's') {
			KeywordDao dao = new KeywordDao();
		}
	}

	//修改爬取的频率
	public void dealDB(Info info) {
		if (info.getInfomation().charAt(0) == 'i') {
			//信息源
			InfosourceDao dao = new InfosourceDao();

			switch (info.getStatus()) {
			case BAD:
				dao.updateFreqFastDB(info, 5);
				break;
			case LOW:
				dao.updateFreqFastDB(info, -1);
				break;
			case HIGH:
				dao.updateFreqFastDB(info, 1);
				break;
			case NICE:
				break;
			case NOTEXIST://+10
				dao.updateFreqFastDB(info, 10);
				break;
			case UNKNOWN:
				break;

			default:
				break;

			}
		} else if (info.getInfomation().charAt(0) == 's') {
			//搜索源
			KeywordDao dao = new KeywordDao();
			switch (info.getStatus()) {
			case BAD:
				dao.updateFreqFastDB(info, 5);
				break;
			case LOW:
				dao.updateFreqFastDB(info, -1);
				break;
			case HIGH:
				dao.updateFreqFastDB(info, 1);
				break;
			case NICE:
				break;
			case NOTEXIST://+10
				dao.updateFreqFastDB(info, 10);
				break;
			case UNKNOWN:
				break;

			default:
				break;

			}
		} else {
			log.info("not unified input..");
		}
	}
}

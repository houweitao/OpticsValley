package com.hou.guanggu.Infosource;

import com.hou.guanggu.Infosource.checkWebsite.dao.KeywordDao;
import com.hou.guanggu.Infosource.checkWebsite.model.Info;
import com.hou.guanggu.Infosource.checkWebsite.model.InfoStatus;

/**
 * @author houweitao
 * @date 2015年12月29日 下午1:18:51
 */

public class KeywordDaoTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String infomation = "s-1-496";
		KeywordDao dao = new KeywordDao();
		Info info = new Info(InfoStatus.HIGH, infomation);
		dao.updateFreqFastDB(info, 100);
	}

}

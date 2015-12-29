package com.hou.guanggu.Infosource;

import org.junit.Test;

import com.hou.guanggu.Infosource.checkWebsite.Info;
import com.hou.guanggu.Infosource.checkWebsite.InfoStatus;
import com.hou.guanggu.Infosource.checkWebsite.OperateDB;
import com.hou.guanggu.Infosource.checkWebsite.dao.InfosourceDao;
import com.hou.guanggu.Infosource.checkWebsite.model.Infosource;

/**
 * @author houweitao
 * @date 2015年12月29日 上午11:33:05
 */

public class InfosourceDaoTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Info info = new Info(InfoStatus.LOW, "i-149471");
		Infosource infosource = new Infosource(1416);

		InfosourceDao infosourceDao = new InfosourceDao();
//		infosourceDao.updateFreq(infosource, 1);

		info = new Info(InfoStatus.LOW, "i-1416");
		infosourceDao.updateFreqFastDB(info, 100);
	}

	@Test
	void testFastDB() {
		System.out.println("test");
		Info info = new Info(InfoStatus.LOW, "i-1416");
		InfosourceDao infosourceDao = new InfosourceDao();
		infosourceDao.updateFreqFastDB(info, 100);
	}
}

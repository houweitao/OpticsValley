package com.hou.guanggu.Infosource;

import org.junit.Test;

import com.hou.guanggu.Infosource.checkWebsite.OperateDB;
import com.hou.guanggu.Infosource.checkWebsite.dao.InfosourceDao;
import com.hou.guanggu.Infosource.checkWebsite.model.Info;
import com.hou.guanggu.Infosource.checkWebsite.model.InfoStatus;
import com.hou.guanggu.Infosource.checkWebsite.model.Infosource;

/**
 * @author houweitao
 * @date 2015年12月29日 上午11:33:05
 */

public class InfosourceDaoTest {

	public static void main(String[] args) {
		InfosourceDao dao=new InfosourceDao();
		Infosource infosource=new Infosource(0);
		infosource.setTime("2015-09-23 12:57:58.50");
		System.out.println(dao.isNew(infosource));
		
		
	}

//	@Test
//	void testFastDB() {
//		System.out.println("test");
//		Info info = new Info(InfoStatus.LOW, "i-1416");
//		InfosourceDao infosourceDao = new InfosourceDao();
//		infosourceDao.updateFreqFastDB(info, 100);
//	}
}

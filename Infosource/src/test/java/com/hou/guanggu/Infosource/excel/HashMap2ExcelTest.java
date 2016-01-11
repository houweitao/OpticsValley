package com.hou.guanggu.Infosource.excel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.hou.guanggu.Infosource.checkWebsite.dao.InfosourceDao;
import com.hou.guanggu.Infosource.checkWebsite.model.Infosource;
import com.hou.guanggu.Infosource.checkWebsite.util.HashMap2Excel;

/**
 * @author houweitao
 * @date 2016年1月11日 上午10:12:24
 */

public class HashMap2ExcelTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InfosourceDao dao = new InfosourceDao();
		ResultSet rs = dao.getAll();
		HashMap<String, Infosource> infosourceMap = new HashMap<String, Infosource>();
		infosourceMap.put("s", new Infosource(3));
		infosourceMap.put("ss", new Infosource(1));
		infosourceMap.put("sfsa", new Infosource(4));

		HashMap2Excel excel = new HashMap2Excel();
		excel.makeInfosourceExcel(infosourceMap);

	}

}

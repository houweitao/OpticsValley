package com.hou.guanggu.Infosource.checkWebsite.util;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hou.guanggu.Infosource.checkWebsite.dao.InfosourceDao;
import com.hou.guanggu.Infosource.checkWebsite.dao.KeywordDao;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * @author houweitao
 * @date 2016年1月7日 下午2:52:43
 * http://www.oschina.net/code/snippet_201433_11013
 */

public class ExcelUtil {

	private static Log log = LogFactory.getLog(ExcelUtil.class);

	public boolean DB2Excel(ResultSet rs, String str) {
		boolean flag = false;
		WritableWorkbook workbook = null;
		WritableSheet sheet = null;
		Label label = null;

		// 创建Excel表
		try {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH时mm分");
			String name = sdf.format(new Date());

			workbook = Workbook.createWorkbook(new File("f:/hou/" + str + " " + name + ".xls"));
			//workbook = Workbook.createWorkbook(os);

			// 创建Excel表中的sheet
			sheet = workbook.createSheet("First Sheet", 0);

			// 向Excel中添加数据
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			String colName = null;
			int row = 0;
			// 添加标题
			for (int i = 0; i < columnCount; i++) {
				colName = rsmd.getColumnName(i + 1);
				label = new Label(i, row, colName);
				// log.debug("标题:"+i+"---"+row +"---"+ colName);
				sheet.addCell(label);
			}
			row++;
			log.debug("写入标题成功");
			while (rs.next()) {
				for (int i = 0; i < columnCount; i++) {
					label = new Label(i, row, rs.getString(i + 1));
					log.debug("行:" + i + "---" + row + "---" + rs.getString(i + 1));
					sheet.addCell(label);
				}
				row++;
			}
			log.debug("写入内容成功");

			// 关闭文件
			workbook.write();
			workbook.close();
			log.info("数据成功写入Excel");
			flag = true;
		} catch (SQLException e) {
			log.debug(e.getMessage());
		} catch (RowsExceededException e) {
			log.debug(e.getMessage());
		} catch (WriteException e) {
			log.debug(e.getMessage());
		} catch (IOException e) {
			log.debug(e.getMessage());
		} finally {
			try {
				workbook.close();
			} catch (Exception e) {
			}
		}
		return flag;
	}

	public void makeExcel() {
		// TODO Auto-generated method stub
		InfosourceDao iDao = new InfosourceDao();
		KeywordDao kDao = new KeywordDao();

		DB2Excel(iDao.getAll(), "infosource");
		DB2Excel(kDao.getAll(), "searchsource");
	}

}
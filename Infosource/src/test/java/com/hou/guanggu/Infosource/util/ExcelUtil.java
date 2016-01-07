package com.hou.guanggu.Infosource.util;

import java.io.File;
import java.io.IOException;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

/**
 * @author houweitao
 * @date 2016年1月7日 下午2:44:39
 * http://www.oschina.net/code/snippet_201433_11013
 */

public class ExcelUtil {

	private static Log log = LogFactory.getLog(ExcelUtil.class);

	public ExcelUtil() {

	}

	public boolean DB2Excel(ResultSet rs) {
		boolean flag = false;
		WritableWorkbook workbook = null;
		WritableSheet sheet = null;
		Label label = null;

		// 创建Excel表
		try {
			workbook = Workbook.createWorkbook(new File("f:/hou/output.xls"));
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

	/**
	 * 测试方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// write your code
		try {

			Class.forName("org.gjt.mm.mysql.Driver").newInstance();
			Connection conn = (Connection) DriverManager.getConnection(
					"jdbc:mysql://119.254.102.70:3306/mdyq_info?characterEncoding=UTF-8", "root", "123456");
			Statement st = (Statement) conn.createStatement();
			ResultSet rs = st.executeQuery("select * from wdyq_report_infosource");
			if (!new ExcelUtil().DB2Excel(rs)) {
				log.info("数据写入失败");
			}
			rs.close();
			st.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
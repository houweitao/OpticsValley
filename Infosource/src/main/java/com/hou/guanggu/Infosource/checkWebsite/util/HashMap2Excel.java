package com.hou.guanggu.Infosource.checkWebsite.util;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.alibaba.fastjson.JSON;
import com.hou.guanggu.Infosource.checkWebsite.model.Infosource;
import com.hou.guanggu.Infosource.checkWebsite.model.Keyword;

/**
 * @author houweitao
 * @date 2016年1月11日 上午9:54:23
 */

public class HashMap2Excel {

	String getTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH时mm分");
		String time = sdf.format(new Date());

		return time;
	}

	public void makeInfosourceExcelStr(Map<String, String> hashMap) {
		// 第一步，创建一个webbook，对应一个Excel文件  
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
		HSSFSheet sheet = wb.createSheet("信息源");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
		HSSFRow row = sheet.createRow((int) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中  
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  

		String[] name = { "id", "website", "url", "searchNum", "newDocNum", "docNum", "freq", "status", "time" };
		HSSFCell cell = row.createCell(0);
		cell.setCellValue(name[0]);
		cell.setCellStyle(style);
		for (int i = 1; i < name.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(name[i]);
			cell.setCellStyle(style);
		}

		// 第五步，写入实体数据 实际应用中这些数据从数据库得到，
		int i = 1;
		for (Entry<String, String> entry : hashMap.entrySet()) {
			row = sheet.createRow(i++);
			Infosource infosource = JSON.parseObject(entry.getValue(), Infosource.class);
			row.createCell(0).setCellValue(infosource.getId());
			row.createCell(1).setCellValue(infosource.getWebsite());
			row.createCell(2).setCellValue(infosource.getUrl());
			row.createCell(3).setCellValue(infosource.getSearchNum());
			row.createCell(4).setCellValue(infosource.getNewDocNum());
			row.createCell(5).setCellValue(infosource.getDocNum());
			row.createCell(6).setCellValue(infosource.getFreq());
			row.createCell(7).setCellValue(infosource.getStatus().toString());
//			row.createCell(8).setCellValue(infosource.getTime());
			cell = row.createCell(8);
			cell.setCellValue(infosource.getTime());
		}

		// 第六步，将文件存到指定位置  
		try {
			FileOutputStream fout = new FileOutputStream("result/信息源 " + getTime() + ".xls");
			wb.write(fout);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void makeInfosourceExcel(HashMap<String, Infosource> hashMap) {

		// 第一步，创建一个webbook，对应一个Excel文件  
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
		HSSFSheet sheet = wb.createSheet("信息源");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
		HSSFRow row = sheet.createRow((int) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中  
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  

		String[] name = { "id", "website", "url", "searchNum", "newDocNum", "docNum", "freq", "status", "time" };
		HSSFCell cell = null;

		for (int i = 0; i < name.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(name[i]);
			cell.setCellStyle(style);
		}

		// 第五步，写入实体数据 实际应用中这些数据从数据库得到，
		int i = 1;
		for (Entry<String, Infosource> entry : hashMap.entrySet()) {
			row = sheet.createRow(i++);
			row.createCell(0).setCellValue(entry.getValue().getId());
			row.createCell(1).setCellValue(entry.getValue().getWebsite());
			row.createCell(2).setCellValue(entry.getValue().getUrl());
			row.createCell(3).setCellValue(entry.getValue().getSearchNum());
			row.createCell(4).setCellValue(entry.getValue().getNewDocNum());
			row.createCell(5).setCellValue(entry.getValue().getDocNum());
			row.createCell(6).setCellValue(entry.getValue().getFreq());
			row.createCell(7).setCellValue(entry.getValue().getStatus().toString());
			row.createCell(8)
					.setCellValue(new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").format(entry.getValue().getTime()));
		}

		// 第六步，将文件存到指定位置  
		try {
			FileOutputStream fout = new FileOutputStream("result/信息源  " + getTime() + ".xls");
			wb.write(fout);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void makeKeywordExcel(HashMap<String, Keyword> hashMap) {

		// 第一步，创建一个webbook，对应一个Excel文件  
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
		HSSFSheet sheet = wb.createSheet("搜索源");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
		HSSFRow row = sheet.createRow((int) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中  
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  

		String[] name = { "id", "keyword", "engine", "name", "url", "searchNum", "newDocNum", "docNum", "freq",
				"status", "time" };
		HSSFCell cell = null;

		for (int i = 0; i < name.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(name[i]);
			cell.setCellStyle(style);
		}

		// 第五步，写入实体数据 实际应用中这些数据从数据库得到，
		int i = 1;
		for (Entry<String, Keyword> entry : hashMap.entrySet()) {
			row = sheet.createRow(i++);
			row.createCell(0).setCellValue(entry.getValue().getId());
			row.createCell(1).setCellValue(entry.getValue().getKeyword());
			row.createCell(2).setCellValue(entry.getValue().getEngine());
			row.createCell(3).setCellValue(entry.getValue().getName());
			row.createCell(4).setCellValue(entry.getValue().getUrl());
			row.createCell(5).setCellValue(entry.getValue().getSearchNum());
			row.createCell(6).setCellValue(entry.getValue().getNewDocNum());
			row.createCell(7).setCellValue(entry.getValue().getDocNum());
			row.createCell(8).setCellValue(entry.getValue().getFreq());
			row.createCell(9).setCellValue(entry.getValue().getStatus().toString());
			row.createCell(10)
					.setCellValue(new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").format(entry.getValue().getTime()));
		}

		// 第六步，将文件存到指定位置  
		try {
			FileOutputStream fout = new FileOutputStream("result/搜索源  " + getTime() + ".xls");
			wb.write(fout);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void makeKeywordExcelStr(Map<String, String> hashMap) {

		// 第一步，创建一个webbook，对应一个Excel文件  
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
		HSSFSheet sheet = wb.createSheet("搜索源");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
		HSSFRow row = sheet.createRow((int) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中  
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  

		String[] name = { "id", "keyword", "engine", "name", "url", "searchNum", "newDocNum", "docNum", "freq",
				"status", "time" };
		HSSFCell cell = null;

		for (int i = 0; i < name.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(name[i]);
			cell.setCellStyle(style);
		}

		// 第五步，写入实体数据 实际应用中这些数据从数据库得到，
		int i = 1;
		for (Entry<String, String> entry : hashMap.entrySet()) {
			row = sheet.createRow(i++);
			Keyword keyword = JSON.parseObject(entry.getValue(), Keyword.class);
			row.createCell(0).setCellValue(keyword.getId());
			row.createCell(1).setCellValue(keyword.getKeyword());
			row.createCell(2).setCellValue(keyword.getEngine());
			row.createCell(3).setCellValue(keyword.getName());
			row.createCell(4).setCellValue(keyword.getUrl());
			row.createCell(5).setCellValue(keyword.getSearchNum());
			row.createCell(6).setCellValue(keyword.getNewDocNum());
			row.createCell(7).setCellValue(keyword.getDocNum());
			row.createCell(8).setCellValue(keyword.getFreq());
			row.createCell(9).setCellValue(keyword.getStatus().toString());
			row.createCell(10).setCellValue(keyword.getTime());
		}

		// 第六步，将文件存到指定位置  
		try {
			FileOutputStream fout = new FileOutputStream("result/搜索源  " + getTime() + ".xls");
			wb.write(fout);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

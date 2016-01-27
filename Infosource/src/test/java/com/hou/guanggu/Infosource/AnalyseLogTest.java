package com.hou.guanggu.Infosource;

import java.util.LinkedList;

import org.junit.Test;

import com.hou.guanggu.Infosource.checkWebsite.AnalyseLog;
import com.hou.guanggu.Infosource.checkWebsite.util.CopyFileUtil;

/**
 * @author houweitao
 * @date 2016年1月27日 下午2:58:01
 */

public class AnalyseLogTest {
	AnalyseLog analyseLog = new AnalyseLog();

	@Test
	public void getFileName() {
		LinkedList<String> files = analyseLog.getLogFiles("recources/");

		for (String s : files) {
			if (s.contains("og."))
				System.out.println(s);
		}

		CopyFileUtil util = new CopyFileUtil();
		util.copyDirectory("recources/", "hahah", true);
	}
}

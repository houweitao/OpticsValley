package com.hou.guanggu.Infosource;

import java.io.File;
import java.util.LinkedList;

/**
 * @author houweitao
 * @date 2016年1月20日 上午10:09:53
 */

public class GetFileNames {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String path = "recources/";
		GetFileNames get = new GetFileNames();
		LinkedList<String> logs = get.getNames(path);

		for (String s : logs) {
			System.out.println(s);
		}
	}

	LinkedList<String> getNames(String path) {
		LinkedList<String> logs = new LinkedList<String>();
		File dir = new File(path);
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isFile()) {
				logs.add(path + file.getName());
			} else {
				logs.addAll(getNames(path + file.getName() + "/"));
			}
		}

		return logs;
	}
}

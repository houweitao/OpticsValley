package com.hou.guanggu.Infosource.checkWebsite.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author houweitao
 * @date 2016年1月27日 下午3:50:07
 * http://blog.csdn.net/malik76/article/details/6408726
 */

public class AppendFile {
	public void method1(String file, String conent) {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(file, true));
			out.write(conent);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**  
	 * 追加文件：使用FileWriter  
	 *   
	 * @param fileName  
	 * @param content  
	 */
	public void method2(String fileName, String content) {
		FileWriter writer = null;
		try {
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件   
			writer = new FileWriter(fileName, true);
			writer.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**  
	 * 追加文件：使用RandomAccessFile  
	 *   
	 * @param fileName 文件名  
	 * @param content 追加的内容  
	 */
	public void method3(String fileName, String content) {
		RandomAccessFile randomFile = null;
		try {
			// 打开一个随机访问文件流，按读写方式   
			randomFile = new RandomAccessFile(fileName, "rw");
			// 文件长度，字节数   
			long fileLength = randomFile.length();
			// 将写文件指针移到文件尾。   
			randomFile.seek(fileLength);
			randomFile.writeBytes(content);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (randomFile != null) {
				try {
					randomFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

//	public static void main(String[] args) {
//		try {
//			String path="f://text.log";
//			File file = new File(path);
//			if (!file.exists() && file.createNewFile()) {
//				System.out.println("Create file successed");
//			}
//			
//			AppendFile app=new AppendFile();
//			
//			app.method1(path, "1" + "\r\n");
//			app.method2(path, "2" + "\r\n");
//			app.method3(path, "3" + "\r\n");
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//	}
}

package com.hou.guanggu.Infosource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hou.guanggu.Infosource.checkWebsite.AnalyseLog;

/**
 * @author houweitao
 * @date 2016年1月25日 下午3:37:57
 */

public class FileSizeTest {
	private static final Logger logger = LoggerFactory.getLogger(FileSizeTest.class);

	public static void main(String[] args) {
		FileChannel fc = null;
		try {
			File f = new File("F:\\git\\OpticsValley\\Infosource\\target\\Infosource-0.0.1-SNAPSHOT.jar");
			if (f.exists() && f.isFile()) {
				FileInputStream fis = new FileInputStream(f);
				fc = fis.getChannel();
				logger.info(fc.size() + "");
				if (fc.size() > 100) {
					fis.close();
					fc.close();
					boolean d = f.delete();
					System.out.println(d);
					f.createNewFile();
				}
				fis.close();
				fc.close();
			} else {
				logger.info("file doesn't exist or is not a file");
			}
		} catch (FileNotFoundException e) {
			logger.error(e.toString());
		} catch (IOException e) {
			logger.error(e.toString());
		} finally {
			if (null != fc) {
				try {
					fc.close();
				} catch (IOException e) {
					logger.error(e.toString());
				}
			}
		}
	}
}

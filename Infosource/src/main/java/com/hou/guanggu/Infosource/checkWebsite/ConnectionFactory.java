package com.hou.guanggu.Infosource.checkWebsite;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * @author houweitao
 * @date 2015年12月29日 上午11:51:50
 */

public class ConnectionFactory {

	private static String driver;
	private static String dburl;
	private static String user;
	private static String password;
	
	private static final ConnectionFactory factory = new ConnectionFactory();
	
	private Connection conn;
	
	static{ 
		Properties prop = new Properties();
		try {
			InputStream in = ConnectionFactory.class.getClassLoader()
					.getResourceAsStream("wdyq.properties");
			prop.load(in);
		} catch (Exception e) {
			System.out.println("=====无配置文件======");
		}
		
		driver = prop.getProperty("driver");
		dburl = prop.getProperty("dburl");
		user = prop.getProperty("user");
		password = prop.getProperty("password");
	}
	
	private ConnectionFactory(){
		
	}
	
	public static ConnectionFactory getInstance(){
		return factory;
	}
	
	public Connection makeConnection(){
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(dburl, user, password);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return conn;
	}

}

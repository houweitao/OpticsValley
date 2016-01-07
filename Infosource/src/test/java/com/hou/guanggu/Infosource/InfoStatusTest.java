package com.hou.guanggu.Infosource;

import com.hou.guanggu.Infosource.checkWebsite.model.Info;
import com.hou.guanggu.Infosource.checkWebsite.model.InfoStatus;

/**
 * @author houweitao
 * @date 2016年1月7日 下午2:23:53
 */

public class InfoStatusTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Info info=new Info(InfoStatus.BAD, "i-888", 0, 0, "ssss");
		System.out.println(info.getStatus().toString());
	}

}

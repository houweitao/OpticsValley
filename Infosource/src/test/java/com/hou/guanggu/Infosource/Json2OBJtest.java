package com.hou.guanggu.Infosource;

import com.alibaba.fastjson.JSON;
import com.hou.guanggu.Infosource.checkWebsite.model.Infosource;

/**
 * @author houweitao
 * @date 2016年1月8日 下午1:43:23
 */

public class Json2OBJtest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Infosource inf=new Infosource(22);
		inf.setDocNum(222);
		String json=JSON.toJSONString(inf);
		System.out.println(json);
		Infosource after=JSON.parseObject(json,Infosource.class);
		System.out.println(after.getId());
	}

}

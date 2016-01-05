package com.hou.guanggu.Infosource.hadoop;

/**
 * @author houweitao
 * @date 2016年1月5日 上午10:23:46
 */

public class HadoopTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HadoopTest hadoop=new HadoopTest();
		hadoop.test();
	}

	
	void test(){
		String line = "222.68.172.190 - - [18/Sep/2013:06:49:57 +0000] \"GET /images/my.jpg HTTP/1.1\" 200 19939 \"http://www.angularjs.cn/A00n\" \"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36\"";
        String[] elementList = line.split(" ");
        for(int i=0;i<elementList.length;i++){
            System.out.println(i+" : "+elementList[i]);
        }
	}
}

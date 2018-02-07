package com.md.utils;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.md.utils.model.MsgBean;

import props.ApkProps;



public class ZSendThred extends Thread{

	private String type;				/*操作类型0-短信；1-消息；2-短信和消息*/
	private MsgBean msgBean;			/*等待操作的信息*/
	
	private static final Logger log = Logger.getLogger(ZSendThred.class);
	private static final String suffix = "【蒙豆】";
	
	
	public ZSendThred(String type,MsgBean msgBean){
		this.type = type;
		this.msgBean = msgBean;
	}
	
	@Override
	public void run() {
		super.run();
		if("0".equals(type)){
			this.sendMsg(msgBean);
			
		}else if("1".equals(type)){
			//this.pushMsg(msgBean);
			
		}else if("2".equals(type)){
			this.sendMsg(msgBean);
			//this.pushMsg(msgBean);
		}
	}
	
	/**单条发送短信*/
	public void sendMsg(MsgBean msgBean) {
		this.SMS(msgBean);
	}
	
	//百分通联短信发送
	public void SMS(MsgBean msgBean) {
        try {
        	String postData = "account=100100&pswd=Xw100100&mobile="+msgBean.getPhoneNo()+"&msg="+java.net.URLEncoder.encode(msgBean.getMsg(),"utf-8");
            //out.println(PostData);
            String postUrl = "http://116.62.64.214/msg/HttpBatchSendSM";
            //发送POST请求
            URL url = new URL(postUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setUseCaches(false);
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Length", "" + postData.length());
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            out.write(postData);
            out.flush();
            out.close();

            //获取响应状态
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println("connect failed!");
            }
            System.out.println("短信发送链接是:============================"+"http://116.62.64.214/msg/HttpBatchSendSM"+postData);;
            //获取响应内容体
            String line, result = "";
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            while ((line = in.readLine()) != null) {
                result += line + "\n";
            }
            in.close();
           // String respcode = result.substring(result.indexOf("<MsgState>"), result.indexOf("</MsgState>"));
          //  respcode = respcode.substring(respcode.indexOf(">")+1);
           
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
       
    }
	
//	/**推送单条信息*/
//	public void pushMsg(MsgBean msgBean){
//		ApkUser user = apkUserServiceImpl.getApkUser(msgBean.getPhoneNo());
//		if(user.accessable4Pusg()){
//			PushMsg msg = new PushMsg();
//			msg.setDescription(msgBean.getMsg());
//			PushUtil.pushToSingle(user,msg);
//			log.info(GlobalPara.LOG_FIX+"推送消息："+msgBean.toString());
//		}
//	}
	
}

package com.md.utils;



import java.util.List;

import com.md.utils.model.MsgBean;



/**
 * 短信发送客户端 
 * @author cyan
 * */
public class ZMsgClient {

	/**单条发送短信*/
	public static void sendMsg(MsgBean msgBean){
		new ZSendThred("0",msgBean).start();
	}
	
	/**批量推送消息
	 * @throws InterruptedException */
	public static void deliveryMsgsOnly(List<MsgBean> msgBeans) throws InterruptedException{
		for(MsgBean bean:msgBeans){
			new ZSendThred("0",bean).start();
		}
	}
	
	/**批量推送消息
	 * @throws InterruptedException */
	public static void deliveryMsgs(List<MsgBean> msgBeans) throws InterruptedException{
		for(MsgBean bean:msgBeans){
			new ZSendThred("2",bean).start();
		}
	}
	
	public static void main(String[] args) {
		MsgBean bean = new MsgBean("15313110831", "hello聚爱财");
		ZMsgClient.sendMsg(bean);
		System.out.println(bean.toString());
	}
}

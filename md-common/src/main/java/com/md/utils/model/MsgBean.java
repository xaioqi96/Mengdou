package com.md.utils.model;



import java.util.Date;


/**
 * 短信bean 
 * @author cyan
 * */
public class MsgBean {

	private String phoneNo;
	private String msg;
	private String respCode;
	
	public MsgBean() {
		super();
	}
	public MsgBean(String phoneNo, String msg) {
		super();
		this.phoneNo = phoneNo;
		this.msg = msg;
	}
	public MsgBean(String phoneNo, String msg,String respCode) {
		super();
		this.phoneNo = phoneNo;
		this.msg = msg;
		this.respCode = respCode;
	}
	
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	@Override
	public String toString() {
		return "MsgBean [phoneNo=" + phoneNo + ", msg=" + msg + ", respCode="
				+ respCode + "]";
	}
	
//	/**生成短信实体数据*/
//	public TtOptimusMsg toMsgBean(){
//		TtOptimusMsg msg = new TtOptimusMsg();
//		msg.setId(CUID.geneUUID("msg"));
//		msg.setPhone(this.getPhoneNo());
//		msg.setContent(this.getMsg());
//		msg.setSendtime(new Date());
//		msg.setStatus("0");
//		return msg;
//	}
}
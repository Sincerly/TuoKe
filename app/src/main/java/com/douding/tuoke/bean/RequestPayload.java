package com.douding.tuoke.bean;

/**
 * Created by Sincerly on 2017/4/23 0023.
 * Version: 1.0
 * QQ:1475590636
 * 邮箱：tgl1475590636@163.com
 * 应用类型：建材
 */

public class RequestPayload {


	/**
	 * Uin : 2030848144
	 * Sid : ybByfS7EQgNbpwD/
	 * Skey : @crypt_34bcc840_b06737e87a00267465940bae222c9d97
	 * DeviceID : e767417338405260
	 */

	private BaseRequestBean BaseRequest;
	/**
	 * Type : 1
	 * Content : 11
	 * FromUserName : @421cfbaa030e7b8c5037b049974710172eeb61325c74bde5132572bb52f1656e
	 * ToUserName : @efb10c4153bf67af14fd2166a6b0a704a94988303e2574e874d6c99f860bc23c
	 * LocalID : 14929139788750230
	 * ClientMsgId : 14929139788750230
	 */

	private MsgBean Msg;
	/**
	 * BaseRequest : {"Uin":2030848144,"Sid":"ybByfS7EQgNbpwD/","Skey":"@crypt_34bcc840_b06737e87a00267465940bae222c9d97","DeviceID":"e767417338405260"}
	 * Msg : {"Type":1,"Content":"11","FromUserName":"@421cfbaa030e7b8c5037b049974710172eeb61325c74bde5132572bb52f1656e","ToUserName":"@efb10c4153bf67af14fd2166a6b0a704a94988303e2574e874d6c99f860bc23c","LocalID":"14929139788750230","ClientMsgId":"14929139788750230"}
	 * Scene : 0
	 */

	private int Scene;

	public BaseRequestBean getBaseRequest() {
		return BaseRequest;
	}

	public void setBaseRequest(BaseRequestBean BaseRequest) {
		this.BaseRequest = BaseRequest;
	}

	public MsgBean getMsg() {
		return Msg;
	}

	public void setMsg(MsgBean Msg) {
		this.Msg = Msg;
	}

	public int getScene() {
		return Scene;
	}

	public void setScene(int Scene) {
		this.Scene = Scene;
	}

	public static class BaseRequestBean {
		private String Uin;
		private String Sid;
		private String Skey;
		private String DeviceID;

		public String getUin() {
			return Uin;
		}

		public void setUin(String Uin) {
			this.Uin = Uin;
		}

		public String getSid() {
			return Sid;
		}

		public void setSid(String Sid) {
			this.Sid = Sid;
		}

		public String getSkey() {
			return Skey;
		}

		public void setSkey(String Skey) {
			this.Skey = Skey;
		}

		public String getDeviceID() {
			return DeviceID;
		}

		public void setDeviceID(String DeviceID) {
			this.DeviceID = DeviceID;
		}
	}

	public static class MsgBean {
		private int Type;
		private String Content;
		private String FromUserName;
		private String ToUserName;
		private String LocalID;
		private String ClientMsgId;

		public int getType() {
			return Type;
		}

		public void setType(int Type) {
			this.Type = Type;
		}

		public String getContent() {
			return Content;
		}

		public void setContent(String Content) {
			this.Content = Content;
		}

		public String getFromUserName() {
			return FromUserName;
		}

		public void setFromUserName(String FromUserName) {
			this.FromUserName = FromUserName;
		}

		public String getToUserName() {
			return ToUserName;
		}

		public void setToUserName(String ToUserName) {
			this.ToUserName = ToUserName;
		}

		public String getLocalID() {
			return LocalID;
		}

		public void setLocalID(String LocalID) {
			this.LocalID = LocalID;
		}

		public String getClientMsgId() {
			return ClientMsgId;
		}

		public void setClientMsgId(String ClientMsgId) {
			this.ClientMsgId = ClientMsgId;
		}
	}
}

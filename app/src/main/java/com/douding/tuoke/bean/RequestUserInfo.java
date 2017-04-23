package com.douding.tuoke.bean;

/**
 * Created by Sincerly on 2017/4/23 0023.
 * Version: 1.0
 * QQ:1475590636
 * 邮箱：tgl1475590636@163.com
 * 应用类型：建材
 */

public class RequestUserInfo {

	/**
	 * Uin : 566148615
	 * Sid : jSsRlGGPyY7U8det
	 * Skey : @crypt_14ae1b12_b73ba2673448154847d7007a2de3c53b
	 * DeviceID : e119795675188164
	 */

	private BaseRequestBean BaseRequest;

	public BaseRequestBean getBaseRequest() {
		return BaseRequest;
	}

	public void setBaseRequest(BaseRequestBean BaseRequest) {
		this.BaseRequest = BaseRequest;
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
}

package com.douding.tuoke.bean;

import java.util.List;

/**
 * Created by Sincerly on 2017/4/23 0023.
 * Version: 1.0
 * QQ:1475590636
 * 邮箱：tgl1475590636@163.com
 * 应用类型：建材
 */
//用户信息
public class UserInfo {

	/**
	 * Ret : 1
	 * ErrMsg :
	 */

	private BaseResponseBean BaseResponse;
	/**
	 * BaseResponse : {"Ret":1,"ErrMsg":""}
	 * Count : 0
	 * ContactList : []
	 * SyncKey : {"Count":0,"List":[]}
	 * User : {"Uin":0,"UserName":"","NickName":"","HeadImgUrl":"","RemarkName":"","PYInitial":"","PYQuanPin":"","RemarkPYInitial":"","RemarkPYQuanPin":"","HideInputBarFlag":0,"StarFriend":0,"Sex":0,"Signature":"","AppAccountFlag":0,"VerifyFlag":0,"ContactFlag":0,"WebWxPluginSwitch":0,"HeadImgFlag":0,"SnsFlag":0}
	 * ChatSet :
	 * SKey :
	 * ClientVersion : 0
	 * SystemTime : 0
	 * GrayScale : 0
	 * InviteStartCount : 0
	 * MPSubscribeMsgCount : 0
	 * MPSubscribeMsgList : []
	 * ClickReportInterval : 0
	 */

	private int Count;
	/**
	 * Count : 0
	 * List : []
	 */

	private SyncKeyBean SyncKey;
	/**
	 * Uin : 0
	 * UserName :
	 * NickName :
	 * HeadImgUrl :
	 * RemarkName :
	 * PYInitial :
	 * PYQuanPin :
	 * RemarkPYInitial :
	 * RemarkPYQuanPin :
	 * HideInputBarFlag : 0
	 * StarFriend : 0
	 * Sex : 0
	 * Signature :
	 * AppAccountFlag : 0
	 * VerifyFlag : 0
	 * ContactFlag : 0
	 * WebWxPluginSwitch : 0
	 * HeadImgFlag : 0
	 * SnsFlag : 0
	 */

	private UserBean User;
	private String ChatSet;
	private String SKey;
	private int ClientVersion;
	private int SystemTime;
	private int GrayScale;
	private int InviteStartCount;
	private int MPSubscribeMsgCount;
	private int ClickReportInterval;
	private List<?> ContactList;
	private List<?> MPSubscribeMsgList;

	public BaseResponseBean getBaseResponse() {
		return BaseResponse;
	}

	public void setBaseResponse(BaseResponseBean BaseResponse) {
		this.BaseResponse = BaseResponse;
	}

	public int getCount() {
		return Count;
	}

	public void setCount(int Count) {
		this.Count = Count;
	}

	public SyncKeyBean getSyncKey() {
		return SyncKey;
	}

	public void setSyncKey(SyncKeyBean SyncKey) {
		this.SyncKey = SyncKey;
	}

	public UserBean getUser() {
		return User;
	}

	public void setUser(UserBean User) {
		this.User = User;
	}

	public String getChatSet() {
		return ChatSet;
	}

	public void setChatSet(String ChatSet) {
		this.ChatSet = ChatSet;
	}

	public String getSKey() {
		return SKey;
	}

	public void setSKey(String SKey) {
		this.SKey = SKey;
	}

	public int getClientVersion() {
		return ClientVersion;
	}

	public void setClientVersion(int ClientVersion) {
		this.ClientVersion = ClientVersion;
	}

	public int getSystemTime() {
		return SystemTime;
	}

	public void setSystemTime(int SystemTime) {
		this.SystemTime = SystemTime;
	}

	public int getGrayScale() {
		return GrayScale;
	}

	public void setGrayScale(int GrayScale) {
		this.GrayScale = GrayScale;
	}

	public int getInviteStartCount() {
		return InviteStartCount;
	}

	public void setInviteStartCount(int InviteStartCount) {
		this.InviteStartCount = InviteStartCount;
	}

	public int getMPSubscribeMsgCount() {
		return MPSubscribeMsgCount;
	}

	public void setMPSubscribeMsgCount(int MPSubscribeMsgCount) {
		this.MPSubscribeMsgCount = MPSubscribeMsgCount;
	}

	public int getClickReportInterval() {
		return ClickReportInterval;
	}

	public void setClickReportInterval(int ClickReportInterval) {
		this.ClickReportInterval = ClickReportInterval;
	}

	public List<?> getContactList() {
		return ContactList;
	}

	public void setContactList(List<?> ContactList) {
		this.ContactList = ContactList;
	}

	public List<?> getMPSubscribeMsgList() {
		return MPSubscribeMsgList;
	}

	public void setMPSubscribeMsgList(List<?> MPSubscribeMsgList) {
		this.MPSubscribeMsgList = MPSubscribeMsgList;
	}

	public static class BaseResponseBean {
		private int Ret;
		private String ErrMsg;

		public int getRet() {
			return Ret;
		}

		public void setRet(int Ret) {
			this.Ret = Ret;
		}

		public String getErrMsg() {
			return ErrMsg;
		}

		public void setErrMsg(String ErrMsg) {
			this.ErrMsg = ErrMsg;
		}
	}

	public static class SyncKeyBean {
		private int Count;
		private java.util.List<?> List;

		public int getCount() {
			return Count;
		}

		public void setCount(int Count) {
			this.Count = Count;
		}

		public List<?> getList() {
			return List;
		}

		public void setList(List<?> List) {
			this.List = List;
		}
	}

	public static class UserBean {
		private int Uin;
		private String UserName;
		private String NickName;
		private String HeadImgUrl;
		private String RemarkName;
		private String PYInitial;
		private String PYQuanPin;
		private String RemarkPYInitial;
		private String RemarkPYQuanPin;
		private int HideInputBarFlag;
		private int StarFriend;
		private int Sex;
		private String Signature;
		private int AppAccountFlag;
		private int VerifyFlag;
		private int ContactFlag;
		private int WebWxPluginSwitch;
		private int HeadImgFlag;
		private int SnsFlag;

		public int getUin() {
			return Uin;
		}

		public void setUin(int Uin) {
			this.Uin = Uin;
		}

		public String getUserName() {
			return UserName;
		}

		public void setUserName(String UserName) {
			this.UserName = UserName;
		}

		public String getNickName() {
			return NickName;
		}

		public void setNickName(String NickName) {
			this.NickName = NickName;
		}

		public String getHeadImgUrl() {
			return HeadImgUrl;
		}

		public void setHeadImgUrl(String HeadImgUrl) {
			this.HeadImgUrl = HeadImgUrl;
		}

		public String getRemarkName() {
			return RemarkName;
		}

		public void setRemarkName(String RemarkName) {
			this.RemarkName = RemarkName;
		}

		public String getPYInitial() {
			return PYInitial;
		}

		public void setPYInitial(String PYInitial) {
			this.PYInitial = PYInitial;
		}

		public String getPYQuanPin() {
			return PYQuanPin;
		}

		public void setPYQuanPin(String PYQuanPin) {
			this.PYQuanPin = PYQuanPin;
		}

		public String getRemarkPYInitial() {
			return RemarkPYInitial;
		}

		public void setRemarkPYInitial(String RemarkPYInitial) {
			this.RemarkPYInitial = RemarkPYInitial;
		}

		public String getRemarkPYQuanPin() {
			return RemarkPYQuanPin;
		}

		public void setRemarkPYQuanPin(String RemarkPYQuanPin) {
			this.RemarkPYQuanPin = RemarkPYQuanPin;
		}

		public int getHideInputBarFlag() {
			return HideInputBarFlag;
		}

		public void setHideInputBarFlag(int HideInputBarFlag) {
			this.HideInputBarFlag = HideInputBarFlag;
		}

		public int getStarFriend() {
			return StarFriend;
		}

		public void setStarFriend(int StarFriend) {
			this.StarFriend = StarFriend;
		}

		public int getSex() {
			return Sex;
		}

		public void setSex(int Sex) {
			this.Sex = Sex;
		}

		public String getSignature() {
			return Signature;
		}

		public void setSignature(String Signature) {
			this.Signature = Signature;
		}

		public int getAppAccountFlag() {
			return AppAccountFlag;
		}

		public void setAppAccountFlag(int AppAccountFlag) {
			this.AppAccountFlag = AppAccountFlag;
		}

		public int getVerifyFlag() {
			return VerifyFlag;
		}

		public void setVerifyFlag(int VerifyFlag) {
			this.VerifyFlag = VerifyFlag;
		}

		public int getContactFlag() {
			return ContactFlag;
		}

		public void setContactFlag(int ContactFlag) {
			this.ContactFlag = ContactFlag;
		}

		public int getWebWxPluginSwitch() {
			return WebWxPluginSwitch;
		}

		public void setWebWxPluginSwitch(int WebWxPluginSwitch) {
			this.WebWxPluginSwitch = WebWxPluginSwitch;
		}

		public int getHeadImgFlag() {
			return HeadImgFlag;
		}

		public void setHeadImgFlag(int HeadImgFlag) {
			this.HeadImgFlag = HeadImgFlag;
		}

		public int getSnsFlag() {
			return SnsFlag;
		}

		public void setSnsFlag(int SnsFlag) {
			this.SnsFlag = SnsFlag;
		}
	}
}

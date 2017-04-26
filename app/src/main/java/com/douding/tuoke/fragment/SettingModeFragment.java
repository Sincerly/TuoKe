package com.douding.tuoke.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.douding.tuoke.R;
import com.douding.tuoke.bean.UserBean;
import com.douding.tuoke.bean.UserInfo;
import com.douding.tuoke.common.Common;
import com.douding.tuoke.util.AuthUtil;
import com.google.gson.Gson;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Sincerly on 2017/4/23 0023.
 * Version: 1.0
 * QQ:1475590636
 * 邮箱：tgl1475590636@163.com
 * 应用类型：建材
 */

public class SettingModeFragment extends Fragment {

	@BindView(R.id.code)
	ImageView code;
	@BindView(R.id.update)
	Button updateBtn;
	private int step = 0;
	private UserBean userBean;
	private String time = "";
	//1
	private String uid;
	//2
	private String ticket;
	private String scan;
	//3
	private String message;
	private String skey;
	private String wxsid;
	private String wxuin;
	private String passTicket;
	private String isgrayscale;
	private UserInfo userinfo;
	private boolean isNeedSendTask = false;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_setting_mode, container, false);
		ButterKnife.bind(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		//new RequestTask("").execute();
	}

	@OnClick(R.id.update)
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.update:
				step=0;
				new RequestTask("").execute();
				break;
		}
	}

	class RequestTask extends AsyncTask {
		private String url = "";
		private String result = "";

		public RequestTask(String url) {
			this.url = url;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Object doInBackground(Object[] objects) {
			try {
				switch (step) {
					case 0: //获取uuid
						result = AuthUtil.login();
						getUid(result);
						break;
					case 2://扫码结果
						if ("".equals(time)) {
							time = System.currentTimeMillis() + "";
						}
						result = AuthUtil.getScanState(uid, time);
						break;
					case 3:
						result = AuthUtil.getLoginState(uid);
						break;
					case 4:
						result = AuthUtil.getPassTicket(ticket, uid, scan);
						break;
					case 5:
						result = AuthUtil.getFriendsList(ticket, skey);
						break;
					case 6://获取个人信息
						////模拟发送消息 result = AuthUtil.sendMessage(wxuin, wxsid, skey, "内容测试", "fromA", "fromB", passTicket);
						result = AuthUtil.getUserInfo(wxsid, skey, wxuin, passTicket);
						break;
					default:
						break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return result == null ? "" : result;
		}

		@Override
		protected void onPostExecute(Object o) {
			super.onPostExecute(o);
			if (o != null) {
				String e = (String) o;
				switch (step) {
					case 0://获取uid 并加载二维码
						step += 2;
						Glide.with(getActivity()).load("https://login.weixin.qq.com/qrcode/" + uid).into(code);
						startScan();//发起扫描请求
						break;
					case 2:
						if (e.contains("window.code=408")) {
							startScan();
						} else {
							step += 1;
							getLoginState();//判断是否登录
						}
						break;
					case 3://轮循 判断是否点击登录按钮
						if (e.contains("window.code=408")) {
							getLoginState();
						} else {//返回201 登录成功
							step += 1;
							if (parseTicketAndScan(result)) {//如果解析完毕就获取cookie
								getPassTicketTask();
							}
						}
						break;
					case 4://获取cookie信息以及skey、wxsid、wxuin、pass_ticket
						try {
							if (parseXml(e)) {
								step += 1;
								getFriends();
							}
						} catch (IOException e1) {
							e1.printStackTrace();
						} catch (XmlPullParserException e1) {
							e1.printStackTrace();
						}
						break;
					case 5:
						userBean = new Gson().fromJson(result, UserBean.class);
						Log.e("tag", "成员数量:" + userBean.getMemberList().size());
//						fillListView();
						step += 1;
						break;
					case 6:
						userinfo = new Gson().fromJson(result, UserInfo.class);
						if (userinfo != null && userinfo.getUser() != null) {
							Log.e("tag",userinfo.getUser().getNickName()+"名称:"+userinfo.getUser().getUserName());
							Common.NickName = userinfo.getUser().getNickName() == null ? "" : userinfo.getUser().getNickName();
							Common.Name = userinfo.getUser().getUserName();
						}
						break;
					default:
						break;
				}
			}
		}
	}

	/**
	 * 处理Uid
	 *
	 * @param responseResult
	 */
	private void getUid(String responseResult) {
		if (responseResult.contains("==")) {
			int index = responseResult.indexOf("\"");
			//System.out.println("key:"+responseResult.subSequence(index+1,13));
			uid = responseResult.substring(index + 1, index + 13);
		} else {
			Toast.makeText(getActivity(), "二维码初始化失败!", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 扫描二维码
	 */
	private void startScan() {
		if (!isNeedSendTask) {
			new RequestTask("").execute();
		}
	}

	/**
	 * 手机是否点登录按钮
	 */
	private void getLoginState() {
		if (!isNeedSendTask) {
			new RequestTask("").execute();
		}
	}

	/**
	 * 获取passticket
	 */
	private void getPassTicketTask() {
		if (!isNeedSendTask) {
			new RequestTask("").execute();
		}
	}

	/*
	 * 获取好友列表
	 */
	private void getFriends() {
		if (!isNeedSendTask) {
			new RequestTask("").execute();
		}
	}

	/*
	 * 发送消息
	 */
	private void sendMessage() {
		if (!isNeedSendTask) {
			new RequestTask("").execute();
		}
	}

	/*
	 * 发送消息
	 */
	private void getUserInfo() {
		if (!isNeedSendTask) {
			new RequestTask("").execute();
		}
	}

	/**
	 * 从字符串中获取字段
	 */
	private boolean parseTicketAndScan(String resultStr) {
		String[] resultParams = resultStr.split("&");
		for (int i = 0; i < resultParams.length; i++) {
			String str = resultParams[i];
			int lastIndex = str.lastIndexOf("=") + 1;
			if (i == 0) {
				ticket = str.substring(lastIndex, str.length());
			} else if (i == (resultParams.length - 1)) {
				int index = str.lastIndexOf("\"");
				scan = str.substring(lastIndex, index);
			}
		}
		return true;
	}

	private boolean parseXml(String e) throws IOException, XmlPullParserException {
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(new StringReader(e.trim()));
		int event = parser.getEventType();
		while (event != XmlPullParser.END_DOCUMENT) {
			switch (event) {
				case XmlPullParser.START_DOCUMENT:
					break;
				case XmlPullParser.START_TAG:
					String key = parser.getName();
					int i = 0;
					if ("error".equals(parser.getName())) {
					} else if ("ret".equals(parser.getName())) {
						Log.e("tag", 1 + "");
					} else if ("message".equals(key)) {
						//message=parser.getAttributeValue(i);
					} else if ("skey".equals(key)) {
						skey = parser.nextText();
					} else if ("wxsid".equals(key)) {
						wxsid = parser.nextText();
					} else if ("wxuin".equals(key)) {
						wxuin = parser.nextText();
					} else if ("pass_ticket".equals(key)) {
						passTicket = parser.nextText();
					} else if ("isgrayscale".equals(key)) {
						isgrayscale = parser.nextText();
					}
					break;
				case XmlPullParser.END_TAG:
					break;
			}
			event = parser.next();
		}
		//Log.e("tag","msg"+message+"skey"+skey+"wxsid"+wxsid+"wxuin"+wxuin+"passTicket"+passTicket+"isgrayscale"+isgrayscale);
		return true;
	}
}

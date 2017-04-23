package com.douding.tuoke;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.douding.tuoke.adapter.BaseAdapter;
import com.douding.tuoke.adapter.ViewHolder;
import com.douding.tuoke.bean.UserBean;
import com.douding.tuoke.bean.UserInfo;
import com.douding.tuoke.common.Common;
import com.douding.tuoke.util.AuthUtil;
import com.google.gson.Gson;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;

public class MainActivity extends Activity implements View.OnClickListener {

	private Button login;
	private EditText content;
	private ImageView code;
	public ListView listView;

	public static final int LOGIN = 1;
	private int step = 0;
	private UserBean userBean;

	private Handler handler = new Handler() {
		@Override
		public void dispatchMessage(Message msg) {
			super.dispatchMessage(msg);
			switch (msg.what) {
				case LOGIN:
					login();
					break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView() {
		login = (Button) findViewById(R.id.btn);
		code = (ImageView) findViewById(R.id.imageView);
		listView = (ListView) findViewById(R.id.listView);
		content= (EditText) findViewById(R.id.editText);
		login.setOnClickListener(this);
	}

	private void fillListView() {
		listView.setAdapter(new BaseAdapter<UserBean.MemberListBean>(userBean.getMemberList(), MainActivity.this, R.layout.item_user) {
			@Override
			protected void fillItem(ViewHolder holder, UserBean.MemberListBean data, int position) {
//				holder.setText(data.getNickName(),R.id.nick);
				View view = holder.getConvertView();
				TextView v = (TextView) view.findViewById(R.id.nick);
				v.setText("呵呵");
//				Glide.with(MainActivity.this).load("https://wx.qq.com" + data.getHeadImgUrl()).into(
//						(ImageView) holder.getView(R.id.imageView));
			}
		});
		//listView.setOnItemClickListener(new ListViewItemClick());
	}

	class ListViewItemClick implements AdapterView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Log.e("tag","点击了");
//			UserBean.MemberListBean bean=userBean.getMemberList().get(position);
//			String toUserName=bean.getUserName();
//			new SendMessageTask(toUserName).execute();
			Toast.makeText(MainActivity.this, "hehe", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btn:
				step = 0;
				new RequestTask("").execute();
				break;
		}
	}


	private void login() {
		try {
			AuthUtil.login();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String time = "";

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
						result=AuthUtil.getUserInfo(wxsid,skey,wxuin,passTicket);
						break;
					default:
						break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return result==null?"":result;
		}

		@Override
		protected void onPostExecute(Object o) {
			super.onPostExecute(o);
			if (o != null) {
				String e = (String) o;
				switch (step) {
					case 0://获取uid 并加载二维码
						step += 2;
						Glide.with(MainActivity.this).load("https://login.weixin.qq.com/qrcode/" + uid).into(code);
						startScan();//发起扫描请求
						break;
					case 2:
						if (e.contains("window.code=400")) {
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
						h.sendEmptyMessage(1);
						break;
					case 6:
						userinfo=new Gson().fromJson(result,UserInfo.class);
						if(userinfo!=null&&userinfo.getUser()!=null){
//							Log.e("tag",userinfo.getUser().getNickName()+"名称:"+userinfo.getUser().getUserName());
							Common.NickName=userinfo.getUser().getNickName()==null?"":userinfo.getUser().getNickName();
							Common.Name=userinfo.getUser().getUserName();
						}
						break;
					default:
						break;
				}
			}
		}
	}

	private UserInfo userinfo;
	class SendMessageTask extends AsyncTask {
		private String result;
		private String msg;
		private String toUserName;
		public SendMessageTask(String toUserName,String msg){
			this.toUserName=toUserName;
			this.msg=msg;
		}
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Log.e("MainActivity SendTask","开始发送****************************************8");
		}

		@Override
		protected Object doInBackground(Object[] objects) {
			try {
				result = AuthUtil.sendMessage(wxuin, wxsid, skey, msg, "fromA", toUserName, passTicket);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(Object o) {
			super.onPostExecute(o);
			String e = (String) o;
			Log.e("tag",e+"");
			Log.e("MainActivity SendTask","发送结束****************************************8");
		}
	}

	private Handler h = new Handler() {
		@Override
		public void dispatchMessage(Message msg) {
			super.dispatchMessage(msg);
			switch (msg.what) {
				case 1:
					listView.setAdapter(new MyAdapter());
					//MainActivity.this.sendMessage();//模拟发送消息
					getUserInfo();//获取个人信息
					break;
			}
		}
	};

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
			Toast.makeText(this, "二维码初始化失败!", Toast.LENGTH_SHORT).show();
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


	private String uid;

	private String ticket;
	private String scan;

	private String message;
	private String skey;
	private String wxsid;
	private String wxuin;
	private String passTicket;
	private String isgrayscale;


	private boolean isNeedSendTask = false;


	class MyAdapter extends android.widget.BaseAdapter {

		@Override
		public int getCount() {
			return userBean.getMemberList().size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = View.inflate(MainActivity.this, R.layout.item_user, null);
				holder = new ViewHolder(convertView);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.tv.setText(userBean.getMemberList().get(position).getNickName());
//			Glide.with(MainActivity.this).load("https://wx.qq.com" + userBean.getMemberList().get(position).getHeadImgUrl()).into(holder.iv);

			convertView.setOnClickListener(new ItemClick(position));
			//convertView.setOnClickListener(new ItemClick(position));
			return convertView;
		}

		class ViewHolder {
			TextView tv;
			ImageView iv;

			public ViewHolder(View v) {
				tv = (TextView) v.findViewById(R.id.nick);
				iv = (ImageView) v.findViewById(R.id.imageview);
			}
		}
	}

	private class ItemClick implements View.OnClickListener {
		private int position;
		UserBean.MemberListBean bean;
		public ItemClick(int position){
			this.position=position;
			bean=userBean.getMemberList().get(position);
		}

		@Override
		public void onClick(View v) {
			Toast.makeText(MainActivity.this, "hehe", Toast.LENGTH_SHORT).show();
			String toUserName=bean.getUserName();
			new SendMessageTask(toUserName,content.getText().toString()).execute();
		}
	}

}

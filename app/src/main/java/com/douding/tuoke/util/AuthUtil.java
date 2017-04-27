package com.douding.tuoke.util;

import android.util.Log;

import com.douding.tuoke.bean.RequestPayload;
import com.douding.tuoke.bean.RequestUserInfo;
import com.douding.tuoke.common.Common;
import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.DefaultedHttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;


/**
 * Created by Sincerly on 2017/4/22.
 */

public class AuthUtil {
	public static final String APP_ID = "wx782c26e4c19acffb";
//	    public static final String APP_ID="wx2fa0744e25727d1e";
	public static final String redirect_uri = "https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxnewloginpage";
	private static CookieStore cookieStore;

	public static String doGet(String url) throws IOException {
		String result = "";
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		HttpResponse response = client.execute(httpGet);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			result = EntityUtils.toString(entity, "utf-8");
			//Log.e("tag", client.getCookieStore() + "");
		}
		return result;
	}

	public static String doGet2(String url) throws IOException {
		String result = "";
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		//设置cookie
		client.setCookieStore(cookieStore);
		HttpResponse response = client.execute(httpGet);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			result = EntityUtils.toString(entity, "utf-8");
			Log.e("tag", client.getCookieStore() + "");
			Log.e("reponse Body:", result);
		}
		return result;
	}

	/**
	 * 获取getPassTicket
	 * @param url
	 * @return
	 * @throws IOException
     */
	public static String doGetByPassTicket(String url) throws IOException {
		String result = "";
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);

		HttpResponse response = client.execute(httpGet);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			result = EntityUtils.toString(entity, "utf-8");
			cookieStore = client.getCookieStore();
			Log.e("reponse Cookie", client.getCookieStore() + "");
			Log.e("reponse Body:", result);
		}
		return result;
	}

	//Step1.获取uuid
	public static String login() throws IOException {
		long time = System.currentTimeMillis();
		String url = "https://login.wx2.qq.com/jslogin?appid=" + APP_ID + "&redirect_uri=" + redirect_uri + "&fun=new&lang=zh_CN&_=" + time;
		Log.e("login url:", url);
		return doGet(url);
	}

	//Step3.轮循获取二维码状态
	public static String getScanState(String uid, String t) throws IOException {
//        long time=System.currentTimeMillis();
		String url = "https://login.wx2.qq.com/cgi-bin/mmwebwx-bin/login?loginicon=true&uuid=" + uid + "&tip=1&r=1824897301&_=" + t;
		Log.e("scanState:", url);
		return doGet(url);
	}

	//Step4.手机点击登录 调用接口
	public static String getLoginState(String uid) throws IOException {
		long time = System.currentTimeMillis();
		String url = "https://login.wx2.qq.com/cgi-bin/mmwebwx-bin/login?loginicon=true&uuid=" + uid + "&tip=0&r=1824872217&_=" + time;
		Log.e("updateState:", url);
		return doGet(url);
	}

	//Step5.获取cookie信息以及skey、wxsid、wxuin、pass_ticket
	public static String getPassTicket2(String ticket, String uid, String scan) throws IOException {
		String url = "https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxnewloginpage?ticket=" + ticket + "&uuid=" + uid + "&lang=zh_CN" + "&scan=" + scan + "&fun=new&version=v2&lang=zh_CN";
		Log.e("getPassTicket:", url);
		return doGetByPassTicket(url);
	}

	//Step5.获取cookie信息以及skey、wxsid、wxuin、pass_ticket
	public static String getPassTicket(String ticket, String uid, String scan) throws IOException {
		String url = "https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxnewloginpage?ticket=" + ticket + "&uuid=" + uid + "&lang=zh_CN" + "&scan=" + scan + "&fun=new&version=v2&lang=zh_CN";
		Log.e("getPassTicket:", url);
		return doGetByPassTicket(url);
	}

	/**
	 * Step6.获取好友列表
	 */
	public static String getFriendsList2(String passTicket, String skey) throws IOException {
		long time = System.currentTimeMillis();
		String url = "https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxgetcontact?lang=zh_CN&pass_ticket=" + passTicket + "&r=" + time + "&seq=0&skey=" + skey;
		Log.e("getFrientds List:", url);
		return doGet2(url);
	}

	/**
	 * Step6.获取好友列表
	 */
	public static String getFriendsList(String passTicket, String skey) throws IOException {
		long time = System.currentTimeMillis();
		String url = "https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxgetcontact?lang=zh_CN&pass_ticket=" + passTicket + "&r=" + time + "&seq=0&skey=" + skey;
		Log.e("getFrientds List:", url);
		return doGet2(url);
	}

	public static String sendMessage2(String uin, String sid, String skey, String msgContent, String msgFrom, String msgTo, String passTicket) {
		/*{
            "BaseRequest":{
                "Uin":2030848144,
                "Sid":"ybByfS7EQgNbpwD/",
                "Skey":"@crypt_34bcc840_b06737e87a00267465940bae222c9d97",
                "DeviceID":"e767417338405260"},
            "Msg":{
                "Type":1,
                "Content":"11",
                "FromUserName":"@421cfbaa030e7b8c5037b049974710172eeb61325c74bde5132572bb52f1656e",
                "ToUserName":"@efb10c4153bf67af14fd2166a6b0a704a94988303e2574e874d6c99f860bc23c",
                "LocalID":"14929139788750230",
                "ClientMsgId":"14929139788750230"},
            "Scene":0
        }*/
		RequestPayload rp = new RequestPayload();
		RequestPayload.BaseRequestBean baseRequestBean = new RequestPayload.BaseRequestBean();
		baseRequestBean.setDeviceID("e6666666666666");
		baseRequestBean.setSid(sid);
		baseRequestBean.setUin(uin);
		baseRequestBean.setSkey(skey);
		rp.setBaseRequest(baseRequestBean);

		long time=System.currentTimeMillis()*10000;
		int num=Math.round(9999);
		RequestPayload.MsgBean msg = new RequestPayload.MsgBean();
		msg.setContent(msgContent);
		msg.setClientMsgId(String.valueOf(time+num));
		msg.setFromUserName(Common.Name);//自己id
		msg.setToUserName(msgTo);//发送给谁
		msg.setLocalID(String.valueOf(time+num));
		msg.setType(1);//文字消息
		rp.setMsg(msg);
		rp.setScene(0);

		String result = "";
		String url = "https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxsendmsg?lang=zh_CN&pass_ticket=" + passTicket;
		Log.e("sendMessage HeaderJson:", new Gson().toJson(rp));
		Log.e("sendMessage Url:", url);
		result = doPost2(url,new Gson().toJson(rp));
		Log.e("tag", "返回的结果" + result);
		return result;
	}

	public static String sendMessage(String uin, String sid, String skey, String msgContent, String msgFrom, String msgTo, String passTicket) {
		/*{
            "BaseRequest":{
                "Uin":2030848144,
                "Sid":"ybByfS7EQgNbpwD/",
                "Skey":"@crypt_34bcc840_b06737e87a00267465940bae222c9d97",
                "DeviceID":"e767417338405260"},
            "Msg":{
                "Type":1,
                "Content":"11",
                "FromUserName":"@421cfbaa030e7b8c5037b049974710172eeb61325c74bde5132572bb52f1656e",
                "ToUserName":"@efb10c4153bf67af14fd2166a6b0a704a94988303e2574e874d6c99f860bc23c",
                "LocalID":"14929139788750230",
                "ClientMsgId":"14929139788750230"},
            "Scene":0
        }*/
		RequestPayload rp = new RequestPayload();
		RequestPayload.BaseRequestBean baseRequestBean = new RequestPayload.BaseRequestBean();
		baseRequestBean.setDeviceID("e6666666666666");
		baseRequestBean.setSid(sid);
		baseRequestBean.setUin(uin);
		baseRequestBean.setSkey(skey);
		rp.setBaseRequest(baseRequestBean);

		long time=System.currentTimeMillis()*10000;
		int num=Math.round(9999);
		RequestPayload.MsgBean msg = new RequestPayload.MsgBean();
		msg.setContent(msgContent);
		msg.setClientMsgId(String.valueOf(time+num));
		msg.setFromUserName(Common.Name);//自己id
		msg.setToUserName(msgTo);//发送给谁
		msg.setLocalID(String.valueOf(time+num));
		msg.setType(1);//文字消息
		rp.setMsg(msg);
		rp.setScene(0);

		String result = "";
		String url = "https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxsendmsg?lang=zh_CN&pass_ticket=" + passTicket;
		Log.e("sendMessage HeaderJson:", new Gson().toJson(rp));
		Log.e("sendMessage Url:", url);
		result = doPost2(url,new Gson().toJson(rp));
		Log.e("tag", "返回的结果" + result);
		return result;
	}

	private static String doPost2(String url, String strJson) {
		DefaultHttpClient client = new DefaultHttpClient();
		if(cookieStore!=null){
			client.setCookieStore(cookieStore);
		}
		HttpPost post = new HttpPost(url);
		String result = null;
		try {
			StringEntity s = new StringEntity(strJson, "UTF-8");
//			s.setContentType("application/json;charset=utf-8");
			s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
					"application/json;charset=utf-8"));
			post.setEntity(s);
			post.setHeader("Content_Type","application/json;charset=UTF-8");

			HttpResponse res = client.execute(post);
			System.out.println(res.getStatusLine().getStatusCode());
			// 如果状态码为200，接收正常
			if (res.getStatusLine().getStatusCode() == 200) {
				// 取出回应字串
				result=EntityUtils.toString(res.getEntity(), "utf-8");
				Log.e("Post response Body:",result);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
}

	/**
	 * 获取个人信息
	 * @param sid
	 * @param skey
	 * @param uin
	 * @param passTicket
	 * @return
	 */
	public static String getUserInfo2(String sid,String skey,String uin,String passTicket){
		String url="https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxinit?r=862039733&lang=zh_CN&pass_ticket="+passTicket;
		RequestUserInfo info=new RequestUserInfo();
		RequestUserInfo.BaseRequestBean rb=new RequestUserInfo.BaseRequestBean();
		rb.setDeviceID("e6666666666666666666");
		rb.setSid(sid);
		rb.setSkey(skey);
		rb.setUin(uin);
		info.setBaseRequest(rb);
		Log.e("getUserInfo url:",url);
		Log.e("getUserInfo",new Gson().toJson(info));
		String result=doPost2(url,new Gson().toJson(info));
		return result;
	};

	/**
	 * 获取个人信息
	 * @param sid
	 * @param skey
	 * @param uin
	 * @param passTicket
	 * @return
	 */
	public static String getUserInfo(String sid,String skey,String uin,String passTicket){
		String url="https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxinit?r=862039733&lang=zh_CN&pass_ticket="+passTicket;
		RequestUserInfo info=new RequestUserInfo();
		RequestUserInfo.BaseRequestBean rb=new RequestUserInfo.BaseRequestBean();
		rb.setDeviceID("e6666666666666666666");
		rb.setSid(sid);
		rb.setSkey(skey);
		rb.setUin(uin);
		info.setBaseRequest(rb);
		Log.e("getUserInfo url:",url);
		Log.e("getUserInfo",new Gson().toJson(info));
		String result=doPost2(url,new Gson().toJson(info));
		return result;
	};
}

//	public static String doPost(String requestPayload, String actionUrl, Map<String, String> params) {
//		try {
//			URL url = new URL(actionUrl);
//			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//			conn.setDoInput(true);// 允许输入
//			conn.setDoOutput(true);// 允许输出
//			conn.setUseCaches(false);// 不使用Cache
//			conn.setRequestMethod("POST");
//			conn.setRequestProperty("Cookie", cookieStore.toString());
//			conn.setRequestProperty("Connection", "Keep-Alive");
//			conn.setRequestProperty(
//					"Accept", "application/json,text/plain,application/json, */*");
//			conn.setRequestProperty("Charset", "UTF-8");
//			conn.setRequestProperty("Accept-Encoding", "gzip, deflate,br");
//
//			//设置requestPayload
//			DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
//			outStream.write(requestPayload.getBytes());
//
//			StringBuilder sb = new StringBuilder();
//			if (params != null) {
//				for (Map.Entry<String, String> entry : params.entrySet()) {// 构建表单字段内容
//					sb.append("\r\n");
//					sb.append("Content-Disposition: form-data; name=\""
//							+ entry.getKey());
//					sb.append(entry.getValue());
//					sb.append("\r\n");
//				}
//				outStream.write(sb.toString().getBytes());// 发送表单字段数据
//			}
//
//			outStream.flush();
//			int cah = conn.getResponseCode();
//			if (cah != 200) throw new RuntimeException("请求url失败");
//			InputStream is = conn.getInputStream();
//			int ch;
//			StringBuilder b = new StringBuilder();
//			while ((ch = is.read()) != -1) {
//				b.append((char) ch);
//			}
//			outStream.close();
//			conn.disconnect();
//			return b.toString();
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}


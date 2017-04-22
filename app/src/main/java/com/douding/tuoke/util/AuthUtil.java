package com.douding.tuoke.util;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParamsNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Sincerly on 2017/4/22.
 */

public class AuthUtil {
    public static final String APP_ID="wx782c26e4c19acffb";
//    public static final String APP_ID="wx2fa0744e25727d1e";
    public static final String redirect_uri="https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxnewloginpage";

    public static String doGet(String url) throws IOException {
        String result="";
        DefaultHttpClient client=new DefaultHttpClient();
        HttpGet httpGet=new HttpGet(url);
        HttpResponse response=client.execute(httpGet);
        HttpEntity entity=response.getEntity();
        if(entity!=null){
            result= EntityUtils.toString(entity,"utf-8");
            Log.e("reponse Body:",result);
        }
        return result;
    }

    //获取uuid
    public static String login() throws IOException {
        long time=System.currentTimeMillis();
        String url="https://login.wx.qq.com/jslogin?appid="+APP_ID+"&redirect_uri="+redirect_uri+"&fun=new&lang=zh_CN&_="+time;
        Log.e("login url:",url);
        return doGet(url);
    }

    //轮循获取二维码状态
    public static String getScanState(String uid,String t) throws IOException {
//        long time=System.currentTimeMillis();
        String url="https://login.wx.qq.com/cgi-bin/mmwebwx-bin/login?loginicon=true&uuid="+uid+"tip=1&r=1824897301&_="+t;
        Log.e("scanStete:",url);
        return doGet(url);
    }




    public static String ScopAfter() throws IOException {
        long time=System.currentTimeMillis();
        String url="https://login.wx.qq.com/jslogin?appid="+APP_ID+"&redirect_uri="+redirect_uri+"&fun=new&lang=zh_CN&_="+time;
        return doGet(url);
    }
}

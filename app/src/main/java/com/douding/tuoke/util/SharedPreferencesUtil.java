package com.douding.tuoke.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.douding.tuoke.bean.list.LoginBean;

public class SharedPreferencesUtil{
	
	/**
	 * 加密方法
	 * @param s 原字符串
	 * @return 加密后的字符串
	 */
	private static String encryption(String s){
		char[] ch=s.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			ch[i] = (char) (ch[i] ^ 6 );
		}
		return new String(ch);
	}

	private static void setParam(Context context,String key,String value){
		//创建SharedPreferences.Editor对象
		SharedPreferences.Editor sped=context.getSharedPreferences("tk", 0).edit();
		//放数据
		sped.putString(key, encryption(value));
		//提交
		sped.commit();
	}
	private static String getParam(Context context,String key){
		SharedPreferences  sp = context.getSharedPreferences("tk", 0);
		//获取数据
		return encryption(sp.getString(key, ""));
	}
	private static void setBooleanParam(Context context,String key,boolean value){
		//创建SharedPreferences.Editor对象
		SharedPreferences.Editor sped=context.getSharedPreferences("tk", 0).edit();
		//放数据
		sped.putBoolean(key, value);
		//提交
		sped.commit();
	}
	private static boolean getBooleanParam(Context context,String key){
		SharedPreferences  sp = context.getSharedPreferences("tk", 0);
		return sp.getBoolean(key, false);
	}

	public static void setLoginState(Context context,boolean state){
		setBooleanParam(context,"loginState",state);
	}

	public static Boolean getLoginState(Context context){
		return getBooleanParam(context,"loginState");
	}

	public static void setUserId(Context context,String userId){
		setParam(context,"userId",userId);
	}

	public static String getUserId(Context context){
		return getParam(context,"userId");
	}

	public static void setUserName(Context context,String userName){
		setParam(context,"userName",userName);
	}

	public static String getUserName(Context context){
		return getParam(context,"userName");
	}
}

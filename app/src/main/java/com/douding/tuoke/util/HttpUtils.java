package com.douding.tuoke.util;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by Sincerly on 2017/4/22.
 */

public class HttpUtils {

	public static void doPost(String url, Map<String, String> params, Map<String, String> headers) {
		url = "";
		try {
			URL u = new URL(url);
			HttpURLConnection httpUrlConnection = (HttpURLConnection) u.openConnection();
			// 设定请求的方法为"POST"，默认是GET
			httpUrlConnection.setRequestMethod("POST");

			StringBuilder buf = new StringBuilder(url);
			Set<Map.Entry<String, String>> entrys = null;
			// 如果是GET请求，则请求参数在URL中
			if (params != null && !params.isEmpty()) {
				buf.append("?");
				entrys = params.entrySet();
				for (Map.Entry<String, String> entry : entrys) {
					buf.append(entry.getKey()).append("=")
							.append(URLEncoder.encode(entry.getValue(), "UTF-8"))
							.append("&");
				}
				buf.deleteCharAt(buf.length() - 1);
			}
			httpUrlConnection.setDoOutput(true);
			httpUrlConnection.setDoInput(true);
			httpUrlConnection.setUseCaches(false);
			httpUrlConnection.setRequestProperty("Content-type", "application/x-java-serialized-object");
// 连接，从上述url.openConnection()至此的配置必须要在connect之前完成，
			httpUrlConnection.connect();
			DataOutputStream ds = (DataOutputStream) httpUrlConnection.getOutputStream();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	//普通字符串数据
	private void writeStringParams(DataOutputStream ds, Map<String, String> textParams) throws Exception {
		Set<String> keySet = textParams.keySet();
		for (Iterator<String> it = keySet.iterator(); it.hasNext(); ) {
			String name = it.next();
			String value = textParams.get(name);
//            ds.writeBytes("--" + boundary + "\r\n");
			ds.writeBytes("Content-Disposition: form-data; name=\"" + name
					+ "\"\r\n");
			ds.writeBytes("\r\n");
		}
	}

}

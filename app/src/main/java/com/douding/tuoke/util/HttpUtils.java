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
// 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在
// http正文内，因此需要设为true, 默认情况下是false;
            httpUrlConnection.setDoOutput(true);

// 设置是否从httpUrlConnection读入，默认情况下是true;
            httpUrlConnection.setDoInput(true);

// Post 请求不能使用缓存
            httpUrlConnection.setUseCaches(false);

// 设定传送的内容类型是可序列化的java对象
// (如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
            httpUrlConnection.setRequestProperty("Content-type", "application/x-java-serialized-object");

// 连接，从上述url.openConnection()至此的配置必须要在connect之前完成，
            httpUrlConnection.connect();
            DataOutputStream ds= (DataOutputStream) httpUrlConnection.getOutputStream();

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

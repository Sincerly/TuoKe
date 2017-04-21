package com.douding.tuoke.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.douding.tuoke.R;
import com.douding.tuoke.web.JavaScriptInterface;

/**
 * Created by Sincerly on 2017/4/21.
 */

public class WebActivity  extends AppCompatActivity{
    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        webView= (WebView) findViewById(R.id.web);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/index.html"); //HTML文件存放在assets文件夹中
        webView.addJavascriptInterface(new JavaScriptInterface(), "js");
    }
}

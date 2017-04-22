package com.douding.tuoke.web;

import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Created by Sincerly on 2017/4/21.
 */

public class JavaScriptInterface {

    //个人账号信息
    @JavascriptInterface
    public void setUserInfo(String text){
        Log.e("tag",text);
    }

}

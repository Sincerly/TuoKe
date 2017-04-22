package com.douding.tuoke;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.douding.tuoke.common.Common;
import com.douding.tuoke.util.AuthUtil;

import org.apache.http.HttpEntity;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button login;
    private ImageView code;

    public static final int LOGIN = 1;
    private int step=0;

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
        code= (ImageView) findViewById(R.id.imageView);

        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn:
                new RequestTask("").execute();
                break;
        }
    }


    private void login(){
        try {
            AuthUtil.login();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String time="";
    class RequestTask extends AsyncTask{
        private String url="";
        private String result="";

        public RequestTask(String url){
            this.url=url;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                switch (step){
                    case 0: //获取uuid
                        result=AuthUtil.login();
                        getUid(result);
                        break;
                    case 2:
                        if("".equals(time)){
                            time=System.currentTimeMillis()+"";
                        }
                        result=AuthUtil.getScanState(uid,time);
                        break;
                    default:
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if(o!=null){
                String e= (String) o;
                switch(step){
                    case 0://获取uid 并加载二维码
                        step+=2;
                        Glide.with(MainActivity.this).load("https://login.weixin.qq.com/qrcode/"+uid).into(code);
                        startScan();//发起扫描请求
                        break;
                    case 2:
                        if(e.contains("window.code=400")){
                            startScan();
                        }
                        break;
                    case 3:
                        break;
                    default:
                        break;
                }
            }
        }
    }

    /**
     * 处理Uid
     * @param responseResult
     */
    private void getUid(String responseResult){
        if(responseResult.contains("==")){
            int index=responseResult.indexOf("\"");
            //System.out.println("key:"+responseResult.subSequence(index+1,13));
            uid=responseResult.substring(index+1,index+13);
        }else{
            Toast.makeText(this, "二维码初始化失败!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     *  扫描二维码
     */
    private void startScan(){
        if(!isNeedSendTask){
            new RequestTask("").execute();
        }
    }

    Runnable run=new Runnable(){

        @Override
        public void run() {

        }
    };

    private String uid;
    private boolean isNeedSendTask=false;
}

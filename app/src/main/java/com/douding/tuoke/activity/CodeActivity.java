package com.douding.tuoke.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.douding.tuoke.R;
import com.douding.tuoke.application.TKApplication;
import com.douding.tuoke.bean.UserBean;
import com.douding.tuoke.bean.UserInfo;
import com.douding.tuoke.bean.list.GroupBean;
import com.douding.tuoke.bean.list.MessageBean;
import com.douding.tuoke.common.Common;
import com.douding.tuoke.fragment.SettingModeFragment;
import com.douding.tuoke.greendao.MessageBeanDao;
import com.douding.tuoke.util.AuthUtil;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingDeque;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Sincerly on 2017/4/25.
 */

public class CodeActivity extends Activity {

    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.codeImage)
    ImageView mCodeImage;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new RequestTask("").execute();
    }

    @OnClick({R.id.back, R.id.title})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
//                new RequestTask("").execute();
                finish();
                break;
            case R.id.title:
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
                        Glide.with(CodeActivity.this).load("https://login.weixin.qq.com/qrcode/" + uid).into(mCodeImage);
                        startScan();//发起扫描请求
                        break;
                    case 2:
                        if (e.contains("window.code=408") || e.contains("window.code=400")|| e.contains("window.code=200")) {
                            startScan();
                        } else {//201
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
                        if (userBean != null) {
                            Log.e("tag", "成员数量:" + userBean.getMemberList().size());
                            Common.userBean=userBean;//用户所有信息bean
                            step += 1;
                            getUserInfo();
                        } else {
                            Log.e("tag", "UserBean 为空");
                        }
                        break;
                    case 6:
                        Log.e("tag", "*************************Step6");
                        userinfo = new Gson().fromJson(result, UserInfo.class);
                        groupBeans= (List<LinkedTreeMap>) userinfo.getContactList();
                        if (userinfo != null && userinfo.getUser() != null) {
                            Log.e("tag", userinfo.getUser().getNickName() + "名称:" + userinfo.getUser().getUserName());
                            Common.NickName =userinfo.getUser().getNickName() == null ? "" : userinfo.getUser().getNickName();
                            Common.Name = userinfo.getUser().getUserName();
                        }
                        handler.sendEmptyMessage(FINISH);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private List<LinkedTreeMap> groupBeans;

    MessageBeanDao dao;
    private Handler handler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case FINISH:
                    dao = TKApplication.getApplication().getDaoSession().getMessageBeanDao();
                    Log.e("tag", "Content Content:" + Common.Content + "end");
                    Log.e("tag", "Content Type:" + Common.type + "end");
                    Log.e("tag", "Content NickName:" + Common.NickName + "end");
                    MessageBean messageBean = new MessageBean();
                    messageBean.setContent(Common.Content);
                    messageBean.setObject(getObjectStr(Common.type));
                    messageBean.setPeople(Common.NickName);
//                    messageBean.setTotalPeople(userBean.getMemberList().size());
                    messageBean.setTotalPeople(getCount());
                    messageBean.setImage("");
                    SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
                    messageBean.setTime(format.format(new Date()));
                    messageBean.setName("Sincerly");
                    messageBean.setSendNum(0);
                    dao.save(messageBean);

                    Common.needSend = true;
                    Intent intent = new Intent();
                    intent.setAction("com.sincerly.message");
                    intent.putExtra("uin",wxuin);
                    intent.putExtra("sid",wxsid);
                    intent.putExtra("skey",skey);
                    intent.putExtra("passTicket",passTicket);

                    sendBroadcast(intent);
                    finish();
                    break;
            }
        }
    };

    private int getCount(){
        int count=0;
        int type=Common.type;

        if(type==3){//群组
            List<String> groups=new ArrayList<>();
            if(groupBeans!=null){
                for (int i = 0; i <groupBeans.size() ; i++) {
                    LinkedTreeMap map=groupBeans.get(i);
                    if(map.containsKey("MemberCount")){
                        double c= (double) map.get("MemberCount");
                        if(c>0.0){
                            groups.add((String) map.get("UserName"));
                        }
                    }
                }
                count=groups.size();//群组个数
                Common.groupBeanList=groups;//赋给全局变量
            }else{
                Toast.makeText(this, "您暂时还没群组", Toast.LENGTH_SHORT).show();
            }
        }else {
            if (userBean != null && userBean.getMemberList() != null) {
                List<UserBean.MemberListBean> list = userBean.getMemberList();
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    UserBean.MemberListBean bean = list.get(i);
                    if (type == 0 && bean.getVerifyFlag() == 0 && bean.getContactFlag() == 3) {//全部
                        count += 1;
                    } else if (type == 1 && bean.getSex() == 1 && !"".equals(bean.getAlias())) {//男
                        count += 1;
                    } else if (type == 2 && bean.getSex() == 2) {//女
                        count += 1;
                    } else if (type == 4 && (bean.getSex() == 1 || bean.getSex() == 2)) {//男女
                        count += 1;
                    }
                }
            }
        }
        return count;
    }

    private String getObjectStr(int type) {
        String result = "";
        switch (type) {
            case 0:
                result="全部";
                break;
            case 1:
                result="男";
                break;
            case 2:
                result="女";
                break;
            case 3:
                result="群组";
                break;
            case 4:
                result="男女";
                break;
        }
        return result;
    }

    public static final int FINISH = 0x01;

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
            Log.e("tag", "uid:" + uid);
        } else {
            Toast.makeText(CodeActivity.this, "二维码初始化失败!", Toast.LENGTH_SHORT).show();
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

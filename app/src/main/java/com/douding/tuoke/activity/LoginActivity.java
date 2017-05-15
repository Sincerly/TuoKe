package com.douding.tuoke.activity;

import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.douding.tuoke.R;
import com.douding.tuoke.application.TKApplication;
import com.douding.tuoke.base.BaseActivity;
import com.douding.tuoke.bean.entity.UserLoginEntity;
import com.douding.tuoke.bean.list.LoginBean;
import com.douding.tuoke.common.Common;
import com.douding.tuoke.greendao.LoginBeanDao;
import com.douding.tuoke.greendao.UserLoginEntityDao;
import com.douding.tuoke.util.AuthUtil;
import com.douding.tuoke.util.SharedPreferencesUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Sincerly on 2017/4/28.
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.image)
    ImageView mImage;
    @BindView(R.id.name)
    TextInputEditText mName;
    @BindView(R.id.pwd)
    TextInputEditText mPwd;
    @BindView(R.id.login)
    Button mLogin;
    @BindView(R.id.forgetPwd)
    Button mForgetPwd;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void setEvent() {
        dao= TKApplication.getApplication().getDaoSession().getLoginBeanDao();
        userEntityDao=TKApplication.getApplication().getDaoSession().getUserLoginEntityDao();
        if(SharedPreferencesUtil.getLoginState(LoginActivity.this)){

        }else{
            List<UserLoginEntity> list=new ArrayList<>();
            list.addAll(userEntityDao.queryBuilder().orderDesc(UserLoginEntityDao.Properties.Id).list());
            if(list!=null&&list.size()>0){
                UserLoginEntity u=list.get(0);
                mName.setText(u.getName().toString());
                mPwd.setText(u.getPwd().toString());
            }
        }
    }

    @OnClick({R.id.image, R.id.login, R.id.forgetPwd, R.id.swipeRefreshLayout})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image:
                break;
            case R.id.login:
                mSwipeRefreshLayout.setVisibility(View.VISIBLE);//这里暂时用刷新控件
                mLogin.setEnabled(false);
                login();
                break;
            case R.id.forgetPwd:
                //goToActivity();
                goToActivity(UpdatePwdActivity.class);
                break;
            case R.id.swipeRefreshLayout:
                break;
        }
    }

    /**
     * 登陆接口
     */
    private void login() {
        String name = mName.getText().toString();
        String pwd = mPwd.getText().toString();
        if (!"".equals(name) && !"".equals(pwd)) {
            String url = Common.NET.URL + "fangshi=user_app_login&pwd=" + pwd + "&telphone=" + name;
            new LoginTask().execute(url);
        }else{
            showToast("用户名或密码不能为空");
        }
    }

    class LoginTask extends AsyncTask<String,String,String> {
        private String result;
        private String msg;
        private String toUserName;

        @Override
        protected String doInBackground(String... params) {
            try {
                result = AuthUtil.doGet(params[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mSwipeRefreshLayout.setRefreshing(true);
            mLogin.setEnabled(true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            mSwipeRefreshLayout.setRefreshing(false);
            mSwipeRefreshLayout.setVisibility(View.GONE);
            loginBean=new Gson().fromJson(result,LoginBean.class);
            checkLoginState(loginBean);
        }
    }

    private void checkLoginState(LoginBean loginBean){
        if(loginBean==null){
            showToast("登录失败！服务器无返回值");
            return;
        }
        if("1".equals(loginBean.getResult())){
            showToast(loginBean.getDescribing()==null?"登录失败,请确认用户名密码!":loginBean.getDescribing());
        }else if("0".equals(loginBean.getResult())){//登录成功
            dao.deleteAll();
            dao.save(loginBean);//保存id

            /**
             * 保存用户信息
             */
            userEntityDao.deleteAll();
            UserLoginEntity u=new UserLoginEntity();
            u.setName(mName.getText().toString());
            u.setPwd(mPwd.getText().toString());
            u.setUserId(loginBean.getUser_ID());
            userEntityDao.save(u);

            SharedPreferencesUtil.setLoginState(LoginActivity.this,true);
            goToActivity(IndexActivity.class);
        }
    }

    private LoginBeanDao dao;
    private UserLoginEntityDao userEntityDao;
    private LoginBean loginBean;
}

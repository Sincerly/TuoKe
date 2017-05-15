package com.douding.tuoke.activity;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.douding.tuoke.R;
import com.douding.tuoke.application.TKApplication;
import com.douding.tuoke.base.BaseActivity;
import com.douding.tuoke.bean.entity.UserLoginEntity;
import com.douding.tuoke.bean.list.ResponseBean;
import com.douding.tuoke.common.Common;
import com.douding.tuoke.greendao.UserLoginEntityDao;
import com.douding.tuoke.util.AuthUtil;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Sincerly on 2017/5/15.
 */

public class UpdatePwdActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.back)
    LinearLayout mBack;
    @BindView(R.id.titleGroup)
    RelativeLayout mTitleGroup;
    @BindView(R.id.oldPwd)
    TextView mOldPwd;
    @BindView(R.id.editText2)
    EditText mEditText2;
    @BindView(R.id.newPwd)
    EditText mNewPwd;
    @BindView(R.id.confirmPwd)
    EditText mConfirmPwd;
    @BindView(R.id.commitUpdate)
    Button mCommitUpdate;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_pwd;
    }

    @OnClick({R.id.title, R.id.back, R.id.titleGroup, R.id.oldPwd, R.id.editText2, R.id.newPwd, R.id.confirmPwd, R.id.commitUpdate})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title:
                break;
            case R.id.back:
                finish();
                break;
            case R.id.titleGroup:
                break;
            case R.id.oldPwd:
                break;
            case R.id.editText2:
                break;
            case R.id.newPwd:
                break;
            case R.id.confirmPwd:
                break;
            case R.id.commitUpdate:
                mCommitUpdate.setEnabled(false);
                update();
                break;
        }
    }

    @Override
    public void setEvent() {
        //dao= TKApplication.getApplication().getDaoSession().getLoginBeanDao();
        userDao= TKApplication.getApplication().getDaoSession().getUserLoginEntityDao();
    }

    class UpdatePwdTask extends AsyncTask<String, String, String> {
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
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            mCommitUpdate.setEnabled(true);
            mSwipeRefreshLayout.setRefreshing(false);
            mSwipeRefreshLayout.setVisibility(View.GONE);
            bean=new Gson().fromJson(result,ResponseBean.class);
            checkUpdateState(bean);
        }
    }
    private ResponseBean bean;
    private UserLoginEntityDao userDao;
    private String userID;

    private void checkUpdateState(ResponseBean bean){
        if(bean==null){
            showToast("密码修改异常,请稍后再试!");
            return;
        }
        if("1".equals(bean.getResult())){
            showToast(bean.getDescribing());
        }else if("0".equals(bean.getResult())){//成功
            showToast(bean.getDescribing());
            goToActivity(LoginActivity.class);
        }
    }

    private void update(){
        String oldPwd=mNewPwd.getText().toString();
        String newPwd=mNewPwd.getText().toString();
        String confirmPwd=mConfirmPwd.getText().toString();
        if("".equals(oldPwd)||"".equals(newPwd)||"".equals(confirmPwd)){
            showToast("请完整填写！");
        }
        if(!"".equals(newPwd)&&newPwd.equals(confirmPwd)){
            List<UserLoginEntity> list=userDao.loadAll();
            if(list!=null&&list.size()>0){
                userID=list.get(0).getUserId();
                String url= Common.NET.URL+"fangshi=upchagepwd&userId="+userID+"&pwd="+confirmPwd; //http://www.webchat.guangzhiyi58.com/app/app.ashx?fangshi=upchagepwd&userID=1&pwd=123456
                new UpdatePwdTask().execute(url);
            }else{
                showToast("请登录！");
            }
        }else{
            showToast("两次输入密码不一样！");
        }
    }
}

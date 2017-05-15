package com.douding.tuoke.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.ButterKnife;

/**
 * Created by Sincerly on 2017/5/15.
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        setEvent();
    }

    @Override
    public void setEvent(){

    }

    protected void goToActivity(Class<?> c){
        Intent intent=new Intent(this,c);
        startActivity(intent);
    }

    protected void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected abstract int getLayoutId();
}

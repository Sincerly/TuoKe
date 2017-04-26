package com.douding.tuoke.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.douding.tuoke.R;
import com.douding.tuoke.common.Common;
import com.douding.tuoke.fragment.MessageFragment;
import com.douding.tuoke.fragment.ModeListFragment;
import com.douding.tuoke.fragment.SettingModeFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sincerly on 2017/4/23 0023.
 * Version: 1.0
 * QQ:1475590636
 * 邮箱：tgl1475590636@163.com
 * 应用类型：建材
 */

public class IndexActivity extends FragmentActivity implements View.OnClickListener {


	/**
	 * 消息模版
	 */
	private TextView mTitle;
	private FrameLayout mContent;
	private ImageView mMenu1Image;
	private RelativeLayout mMGroup1;
	private ImageView mMenu2Image;
	private RelativeLayout mMGroup2;
	private ImageView mMenu3Image;
	private RelativeLayout mMGroup3;
	private LinearLayout add;

	private int currentTabIndex = 0;

	private List<Fragment> fragmentList = new ArrayList<>();
	private TextView[] menus;
	private RelativeLayout[] groups;

	private ModeListFragment fragment;
	private MessageFragment fragment2;
	private SettingModeFragment fragment3;
	private FragmentTransaction ft;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e("tag",this.getClass().getSimpleName()+"onCreate");
		setContentView(R.layout.activity_index);
		initView();
		initFragment();
		registerReceiver(sendBroadCastReceiver,new IntentFilter("com.sincerly.send"));
	}

	private BroadcastReceiver sendBroadCastReceiver=new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if("com.sincerly.send".equals(intent.getAction())){
				changeColor(1);
			}
		}
	};

	private void initFragment() {
		fragmentList.add(new ModeListFragment());
		fragmentList.add(new MessageFragment());
		fragmentList.add(new SettingModeFragment());
		//打开界面后显示此Fragment
		ft = getSupportFragmentManager().beginTransaction();
		if (fragment == null) {
			fragment = new ModeListFragment();
			ft.add(R.id.content, fragment);
		} else {
			ft.show(fragment);
		}
		ft.commit();
		menus[0].setTextColor(Color.parseColor("#42518b"));
		groups[0].setSelected(true);
	}

	private void initView() {
		mTitle = (TextView) findViewById(R.id.title);
		mContent = (FrameLayout) findViewById(R.id.content);
		mMenu1Image = (ImageView) findViewById(R.id.menu1Image);
		mMGroup1 = (RelativeLayout) findViewById(R.id.mGroup1);
		mMenu2Image = (ImageView) findViewById(R.id.menu2Image);
		mMGroup2 = (RelativeLayout) findViewById(R.id.mGroup2);
		mMenu3Image = (ImageView) findViewById(R.id.menu3Image);
		mMGroup3 = (RelativeLayout) findViewById(R.id.mGroup3);
		add= (LinearLayout) findViewById(R.id.add);
		menus = new TextView[]{(TextView) findViewById(R.id.menu1), (TextView) findViewById(R.id.menu2), (TextView) findViewById(R.id.menu3)};
		groups = new RelativeLayout[]{mMGroup1, mMGroup2, mMGroup3};

		mMGroup1.setOnClickListener(this);
		mMGroup2.setOnClickListener(this);
		mMGroup3.setOnClickListener(this);
		add.setOnClickListener(this);
	}

	private void switchFragment(int position){
		ft = getSupportFragmentManager().beginTransaction();
		hidenFragment(ft);

		switch (position){
			case 0:
				if(fragment == null){
					fragment = new ModeListFragment();
					ft.add(R.id.content, fragment);
				}else{
					ft.show(fragment);
				}
				break;
			case 1:
				if(fragment2 == null){
					fragment2 = new MessageFragment();
					ft.add(R.id.content, fragment2);
				}else{
//					ft.show(fragment2);
					ft.show(fragment2);
				}
				break;
			case 2:
				if(fragment3 == null){
					fragment3 = new SettingModeFragment();
					ft.add(R.id.content, fragment3);
				}else{
//					ft.show(fragment3);
					ft.show(fragment3);
				}
				break;
		}
		ft.commit();
	}

	private void hidenFragment(FragmentTransaction ft){
		if(fragment!=null){
			ft.hide(fragment);
		}
		if(fragment2!=null){
			ft.hide(fragment2);
		}
		if(fragment3!=null){
			ft.hide(fragment3);
		}
	}

	private void changeColor(int position) {
		for (int i = 0; i < menus.length; i++) {
			if (position == i) {
				menus[i].setTextColor(Color.parseColor("#42518b"));
				groups[i].setSelected(true);
			} else {
				menus[i].setTextColor(Color.parseColor("#8a8a8a"));
				groups[i].setSelected(false);
			}
		}
		switchFragment(position);
		changeTitle(position);
	}

	private void changeTitle(int position) {
		String text = "";
		switch (position) {
			case 0:
				text = "消息模板";
				break;
			case 1:
				text = "群发进度";
				break;
			case 2:
				text = "个人中心";
				break;
		}
		mTitle.setText(text);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.mGroup1:
				changeColor(0);
				break;
			case R.id.mGroup2:
				changeColor(1);
				break;
			case R.id.mGroup3:
				changeColor(2);
				break;
			case R.id.add:
				if(currentTabIndex==0){//添加模板
					Intent intent=new Intent(this,AddMessageActivity.class);
					startActivity(intent);
				}
				break;
			default:
				break;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.e("tag",this.getClass().getSimpleName()+"OnResume()");
		if(Common.needSend){
			changeColor(1);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(sendBroadCastReceiver);
	}
}

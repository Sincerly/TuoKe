package com.douding.tuoke.fragment;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.douding.tuoke.R;
import com.douding.tuoke.activity.AddMessageActivity;
import com.douding.tuoke.activity.CodeActivity;
import com.douding.tuoke.adapter.RBaseAdapter;
import com.douding.tuoke.adapter.RViewHolder;
import com.douding.tuoke.application.TKApplication;
import com.douding.tuoke.bean.UserBean;
import com.douding.tuoke.bean.UserInfo;
import com.douding.tuoke.bean.list.ModeBean;
import com.douding.tuoke.common.Common;
import com.douding.tuoke.greendao.ModeBeanDao;
import com.douding.tuoke.util.AuthUtil;
import com.google.gson.Gson;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

public class ModeListFragment extends Fragment {
	@BindView(R.id.modeRecyclerView)
	RecyclerView recyclerView;
	private RBaseAdapter adapter;
	private List<ModeBean> list = new ArrayList<>();

	ModeBeanDao dao;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_mode_list, container, false);
		ButterKnife.bind(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		dao = TKApplication.getApplication().getDaoSession().getModeBeanDao();
		registerBroadCast();
		initList();
	}

	private BroadcastReceiver changedReceiver=null;
	private void registerBroadCast() {
		changedReceiver=new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				if("com.sincerly.needload".equals(intent.getAction())){
					reload();
				}
			}
		};
		getActivity().registerReceiver(changedReceiver,new IntentFilter("com.sincerly.needload"));
	}

	private void reload(){
		list.clear();
		list.addAll(dao.queryBuilder().orderDesc(ModeBeanDao.Properties.Id).list());
		recyclerView.getAdapter().notifyDataSetChanged();
	}

	private void initList() {
//		ModeBean messageBean = new ModeBean();
//		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
//		messageBean.setTime(format.format(new Date()));
//		messageBean.setContent("清粉");
//		messageBean.setObject("全部");
//		messageBean.setImage("11");
//		messageBean.setTime("2017-04-24");
//		messageBean.setName("WEIKUO");
//		list.add(messageBean);

		list.addAll(dao.queryBuilder().orderDesc(ModeBeanDao.Properties.Id).list());

		adapter = new RBaseAdapter<ModeBean>(getActivity(), R.layout.item_mode, list) {

			@Override
			protected void fillItem(RViewHolder holder, ModeBean item, int position) {
				holder.setText(R.id.time, item.getTime());
				holder.setText(R.id.name, item.getName());
				holder.setText(R.id.content, item.getContent());
				holder.setText(R.id.object, "发送人群：" + item.getObject());

				holder.getView(R.id.delete).setOnClickListener(new OnActionClickListener(position));
				holder.getView(R.id.edit).setOnClickListener(new OnActionClickListener(position));
				holder.getView(R.id.send).setOnClickListener(new OnActionClickListener(position));
			}

			@Override
			protected int getViewType(ModeBean item, int position) {
				return 0;
			}
		};
		recyclerView.setFocusable(false);
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		recyclerView.setAdapter(adapter);
	}

	class OnActionClickListener implements View.OnClickListener{
		private int position;

		public OnActionClickListener(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()){
				case R.id.delete:
					p=position;
					showAlertDialog();
					break;
				case R.id.edit:
					p=position;
					goToDetail(list.get(p).getId());
					break;
				case R.id.send:
					p=position;
					Common.id=list.get(p).getId();
					Common.Content=list.get(p).getContent();
					String type=list.get(p).getObject();//发送对象
					if("全部".equals(type)){
						Common.type=0;
					}else if("男".equals(type)){
						Common.type=1;
					}else if("女".equals(type)){
						Common.type=2;
					}else if("群组".equals(type)){
						Common.type=3;
					}else if("男女".equals(type)){
						Common.type=4;
					}
					send();//跳转到二维码扫描页面
					break;
			}
		}
	}

	/**
	 * 群发
	 */
	private void send() {
		Intent intent=new Intent(getActivity(), CodeActivity.class);
		startActivity(intent);
	}

	private int p;
	private static final int EDIT=2;

	private void goToDetail(Long id){
		Intent intent=new Intent(getActivity(),AddMessageActivity.class);
		intent.putExtra("id",id);
		startActivityForResult(intent,EDIT);
	}
	private void showAlertDialog(){
		AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
		builder.setTitle("温馨提示");
		builder.setMessage("确定要删除吗?");
		builder.setPositiveButton("确定", new PositiveButtonClick());
		builder.setNegativeButton("取消",null);
		builder.create().show();
	}

	/**
	 * 删除
	 */
	class PositiveButtonClick implements DialogInterface.OnClickListener{

		@Override
		public void onClick(DialogInterface dialog, int which) {
			removeItem();
		}
	}

	private void removeItem(){
		deleteData(list.get(p).getId());
		list.remove(p);
		adapter.notifyDataSetChanged();
	}

	//删
	private void deleteData(Long id){
		dao.deleteByKey(id);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		getActivity().unregisterReceiver(changedReceiver);
	}
}


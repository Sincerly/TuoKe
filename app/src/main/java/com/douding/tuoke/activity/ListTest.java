package com.douding.tuoke.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.douding.tuoke.R;
import com.douding.tuoke.adapter.RBaseAdapter;
import com.douding.tuoke.adapter.RViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sincerly on 2017/4/24 0024.
 * Version: 1.0
 * QQ:1475590636
 * 邮箱：tgl1475590636@163.com
 * 应用类型：建材
 */

public class ListTest extends Activity {
	@BindView(R.id.msgRecyclerView)
	RecyclerView recyclerView;
	private List<String> list=new ArrayList<>();
	private RBaseAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_test);
		ButterKnife.bind(this);

		list.add("hehehe");
		list.add("hehehe");
		list.add("hehehe");
		list.add("hehehe");
		list.add("hehehe");
		list.add("hehehe");
		list.add("hehehe");
		list.add("hehehe");
		list.add("hehehe");
		adapter = new RBaseAdapter<String>(this, R.layout.item_msg, list) {

			@Override
			protected void fillItem(RViewHolder holder, String item, int position) {
				Log.e("tag", "hehe");
//				holder.setText(R.id.time,item.getTime());
//				holder.setText(R.id.name,item.getName());
//				holder.setText(R.id.content, item.getContent());
//				holder.setText(R.id.object, "发送人群："+item.getObject());
//				holder.setText(R.id.people, "发送者:"+item.getPeople());
//				holder.setText(R.id.total, "总人数:"+item.getTotalPeople()+ "");
//				holder.setText(R.id.send,"已发送:"+item.getSendNum()+"");
			}

			@Override
			protected int getViewType(String item, int position) {
				return 0;
			}
		};
		recyclerView.setFocusable(false);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recyclerView.setAdapter(adapter);
	}


}

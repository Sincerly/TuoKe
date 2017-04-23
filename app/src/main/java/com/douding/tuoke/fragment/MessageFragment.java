package com.douding.tuoke.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.douding.tuoke.R;

/**
 * Created by Sincerly on 2017/4/23 0023.
 * Version: 1.0
 * QQ:1475590636
 * 邮箱：tgl1475590636@163.com
 * 应用类型：建材
 */

public class MessageFragment extends Fragment {

	private ListView listView;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view=View.inflate(getActivity(), R.layout.fragment_message,container);
		initView(view);
		return view;
	}

	private void initView(View v){
		listView= (ListView) v.findViewById(R.id.listView);
	}

}

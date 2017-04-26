package com.douding.tuoke.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Sincerly on 2017/4/24 0024.
 * Version: 1.0
 * QQ:1475590636
 * 邮箱：tgl1475590636@163.com
 * 应用类型：建材
 */

public class FragmentA extends Fragment {

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		TextView t=new TextView(getActivity());
		t.setText("ggggg");
		return t;
	}
}

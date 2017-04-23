package com.douding.tuoke.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

/**
 * Created by Administrator on 2016/10/26 0026.
 */

public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {

	private List<T> data;
	private Context context;
	private int layoutId;

	public BaseAdapter(List<T> data, Context context, int layoutId) {
		this.data = data;
		this.context = context;
		this.layoutId = layoutId;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=ViewHolder.get(context,convertView,layoutId,position,parent);
		fillItem(holder,data.get(position),position);
		return holder.getConvertView();
	}

	protected abstract void fillItem(ViewHolder holder,T data,int position);
}

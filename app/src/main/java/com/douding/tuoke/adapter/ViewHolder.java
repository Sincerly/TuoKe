package com.douding.tuoke.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/10/26 0026.
 */

public class ViewHolder {

	private SparseArray<View> views;
	private View convertView;

	public ViewHolder(Context context,View convertView,int layoutId,int position,ViewGroup parent){
		views=new SparseArray<>();
		convertView= LayoutInflater.from(context).inflate(layoutId,parent,false);
		convertView.setTag(this);
	}

	public static ViewHolder get(Context context,View convertView,int layoutId,int position,ViewGroup parent){
		if(convertView==null){
			return new ViewHolder(context,convertView,layoutId,position,parent);
		}
		return (ViewHolder) convertView.getTag();
	}

	/**
	 * 	返回组件视图
	 */
	public <T extends View>  T getView(int viewId){
		View view=views.get(viewId);
		if(view==null){
			view=convertView.findViewById(viewId);
			views.put(viewId,view);
		}
		return (T) view;
	}

	public <T extends View>  ViewHolder setText(String str,int viewId){
		T view=getView(viewId);
		if(view!=null){
			if(view instanceof TextView){
				((TextView)view).setText(str);
			}else if(view instanceof Button){
				((Button)view).setText(str);
			}
		}
		return this;
	}

	public View  getConvertView(){
		return convertView;
	}
}

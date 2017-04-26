package com.douding.tuoke.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Sincerly on 2016/9/9 0009.
 */
public class RViewHolder extends RecyclerView.ViewHolder {
	private SparseArray<View> mViews;
	private View convertView;//item整个布局
	private int type;

	public RViewHolder(View itemView) {
		super(itemView);
		convertView=itemView;
		mViews = new SparseArray<View>();
	}

	//通过viewId获取控件
	public <T extends View> T getView(int viewId) {
		View view = mViews.get(viewId);
		if (view == null) {
			view = itemView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T) view;
	}

	/**
	 * 设置TextView的值
	 */
	public RViewHolder setText(int viewId, String text) {
		TextView tv = getView(viewId);
		tv.setText(text==null?"":text);
		return this;
	}

	public RViewHolder setImageResource(int viewId, int resId) {
		ImageView view = getView(viewId);
		view.setImageResource(resId);
		return this;
	}

	public RViewHolder setImageBitamp(int viewId, Bitmap bitmap) {
		ImageView view = getView(viewId);
		view.setImageBitmap(bitmap);
		return this;
	}

	public RViewHolder setImageURI(int viewId, String uri) {
		ImageView view = getView(viewId);
//        Imageloader.getInstance().loadImg(view,uri);
		return this;
	}

	public View getConvertView(){
		return convertView;
	}

	public void setType(int type){
		this.type=type;
	}

	public int getType(){
		return this.type;
	}
}
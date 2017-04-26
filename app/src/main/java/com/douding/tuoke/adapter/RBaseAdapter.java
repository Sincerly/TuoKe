package com.douding.tuoke.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.douding.tuoke.R;

import java.util.List;

/**
 * Created by Sincerly on 2016/9/9 0009.
 */
public abstract class RBaseAdapter<T> extends RecyclerView.Adapter<RViewHolder> {

	private List<T> data;
	private Context context;
	private int layoutId;
	private LayoutInflater inflater;

	public RBaseAdapter(Context c, int layoutId, List<T> data) {
		this.context = c;
		this.layoutId = layoutId;
		this.data = data;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public RViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		RViewHolder holder=null;
		switch (viewType) {
			case 0://正常情况下
				holder = new RViewHolder(inflater.inflate(layoutId, parent, false));
				break;
			case 1://广告
				//holder=new RViewHolder(inflater.inflate(R.layout.recycle_ad,parent,false));
				break;
			default:
				break;
		}
		holder.setType(viewType);
		return holder;
	}

	@Override
	public void onBindViewHolder(RViewHolder holder, int position) {
		T item = getItem(position);
		if (item == null) {
			return;
		}
		fillItem(holder, item, position);
		if(onItemClickListener!=null){
			setOnItemClick(holder,holder.getConvertView(),position);
		}
		if(onItemLongClickListener!=null){
			setOnItemLongClick(holder,holder.getConvertView(),position);
		}
	}

	@Override
	public int getItemViewType(int position) {
		T d = data.get(position);
		return getViewType(d, position);
	}

	private void setOnItemClick(final RViewHolder holder, final View view, final int position) {
		holder.getConvertView().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onItemClickListener.onItemClick(holder,v,position);
			}
		});
	}

	private void setOnItemLongClick(final RViewHolder holder,final  View view,final int position){
		holder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				return onItemLongClickListener.onItemLongClick(holder,view,position);
			}
		});
	}

	@Override
	public int getItemCount() {
		return data.size();
	}

	private T getItem(int position) {
		if (data != null || data.isEmpty() == false) {
			return data.get(position);
		}
		return null;
	}

	protected abstract void fillItem(RViewHolder holder, T item, int position);
	protected abstract int getViewType(T item, int position);


	public interface OnItemClickListener {
		void onItemClick(RViewHolder holder, View view, int position);
	}

	public interface  onItemLongClickListener{
		boolean onItemLongClick(RViewHolder holder, View view, int position);
	}

	private OnItemClickListener onItemClickListener;
	private onItemLongClickListener onItemLongClickListener;

	public void setOnItemLongClickListener(RBaseAdapter.onItemLongClickListener onItemLongClickListener) {
		this.onItemLongClickListener = onItemLongClickListener;
	}

	public void setOnItemClickListener(OnItemClickListener listener) {
		this.onItemClickListener = listener;
	}
}
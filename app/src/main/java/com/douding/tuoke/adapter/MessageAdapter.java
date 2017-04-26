package com.douding.tuoke.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.douding.tuoke.R;
import com.douding.tuoke.bean.list.MessageBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sincerly on 2017/4/24.
 */

public class MessageAdapter extends android.widget.BaseAdapter {
    private List<MessageBean> list = new ArrayList<>();
    private Context mContext;

    public MessageAdapter(List<MessageBean> list, Context context) {
        this.list = list;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_msg, null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        MessageBean bean=list.get(position);
        holder.mTime.setText(bean.getTime());
        holder.mContent.setText(bean.getContent());
        holder.mObject.setText("发送人群："+bean.getObject());
        holder.mPeople.setText("发送者:"+bean.getPeople());
        holder.total.setText("总人数:"+bean.getTotalPeople());
        holder.send.setText("已发送:"+bean.getSendNum());
        return convertView;
    }

    static class ViewHolder {
        View view;
        ImageView mImageView2;
        TextView mName;
        TextView mTime;
        TextView mContent;
        TextView mObject;
        TextView mPeople;
        TextView mDelete;
        TextView total;
        TextView send;

       public ViewHolder(View view) {
            this.view = view;
            this.mImageView2 = (ImageView) view.findViewById(R.id.imageView2);
            this.mName = (TextView) view.findViewById(R.id.name);
            this.mTime = (TextView) view.findViewById(R.id.time);
            this.mContent = (TextView) view.findViewById(R.id.content);
            this.mObject = (TextView) view.findViewById(R.id.object);
            this.mPeople = (TextView) view.findViewById(R.id.people);
            this.mDelete = (TextView) view.findViewById(R.id.delete);
            this.total = (TextView) view.findViewById(R.id.total);
            this.send = (TextView) view.findViewById(R.id.send);
        }
    }
}

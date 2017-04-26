package com.douding.tuoke.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.douding.tuoke.R;
import com.douding.tuoke.bean.list.ModeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sincerly on 2017/4/24.
 */

public class ModeAdapter extends android.widget.BaseAdapter {
    private List<ModeBean> list = new ArrayList<>();
    private Context mContext;

    public ModeAdapter(List<ModeBean> list, Context context) {
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
            convertView = View.inflate(mContext, R.layout.item_mode, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ModeBean bean = list.get(position);
        holder.mTime.setText(bean.getTime());
        holder.mContent.setText(bean.getContent());
        holder.mObject.setText("发送人群：" + bean.getObject());
        holder.mDelete.setOnClickListener(new Delete(position));
        holder.mEdit.setOnClickListener(new Edit(position));
        holder.mSend.setOnClickListener(new Send(position));
        return convertView;
    }

    class ViewHolder {
        View view;
        ImageView mImageView2;
        TextView mName;
        TextView mTime;
        TextView mContent;
        TextView mObject;
        TextView mDelete;
        TextView mEdit;
        TextView mSend;

        ViewHolder(View view) {
            this.view = view;
            this.mImageView2 = (ImageView) view.findViewById(R.id.imageView2);
            this.mName = (TextView) view.findViewById(R.id.name);
            this.mTime = (TextView) view.findViewById(R.id.time);
            this.mContent = (TextView) view.findViewById(R.id.content);
            this.mObject = (TextView) view.findViewById(R.id.object);
            this.mDelete = (TextView) view.findViewById(R.id.delete);
            this.mEdit = (TextView) view.findViewById(R.id.edit);
            this.mSend = (TextView) view.findViewById(R.id.send);
        }
    }

    class Delete implements View.OnClickListener{
        private int position;

        public Delete(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View view) {

        }
    }

    class Edit implements View.OnClickListener{
        private int position;

        public Edit(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View view) {

        }
    }

    class Send implements View.OnClickListener{
        private int position;

        public Send(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View view) {

        }
    }
}
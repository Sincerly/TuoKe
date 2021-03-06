package com.douding.tuoke.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.douding.tuoke.R;
import com.douding.tuoke.adapter.RBaseAdapter;
import com.douding.tuoke.adapter.RViewHolder;
import com.douding.tuoke.application.TKApplication;
import com.douding.tuoke.bean.UserBean;
import com.douding.tuoke.bean.list.GroupBean;
import com.douding.tuoke.bean.list.MessageBean;
import com.douding.tuoke.common.Common;
import com.douding.tuoke.greendao.MessageBeanDao;
import com.douding.tuoke.greendao.ModeBeanDao;
import com.douding.tuoke.util.AuthUtil;
import com.douding.tuoke.util.Utils;

import java.util.ArrayList;
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

public class MessageFragment extends Fragment {


    @BindView(R.id.msgRecyclerView)
    RecyclerView recyclerView;
    private RBaseAdapter adapter;
    private List<MessageBean> list = new ArrayList<>();
    MessageBeanDao dao;
    UserBean bean;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    private void reload() {
        list.clear();
        list.addAll(dao.queryBuilder()
                .orderDesc(MessageBeanDao.Properties.Id)
                .list());
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Common.needSend) {
            isWxNew = Common.isWxNew;
            Common.needSend = false;
            startSendTask();//开始发送信息
        }
    }

    private void startSendTask() {
        bean = Common.userBean;
        final int type = Common.type;
        if (type == 3) {//群组
            final List<String> groupBean = Common.groupBeanList;
            if (groupBean != null) {
                if (Utils.isOpenNetwork(getActivity())) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                for (int i = 0; i < groupBean.size(); i++) {
                                    String name = groupBean.get(i);
                                    Thread.sleep(900);
                                    new SendMessageTask(name, Common.Content).execute();//发送群消息
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();

                }
            }
        } else if (type == 0) {//全部  成员加群组
            final List<String> groupBean = Common.groupBeanList;
            if (groupBean != null) {
                if (Utils.isOpenNetwork(getActivity())) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                for (int i = 0; i < groupBean.size(); i++) {
                                    String name = groupBean.get(i);
                                    Thread.sleep(900);
                                    new SendMessageTask(name, Common.Content).execute();//发送消息
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        } else {//好友
            if (bean != null) {
                if (Utils.isOpenNetwork(getActivity())) {
                    final int t = type;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                for (int i = 0; i < bean.getMemberCount(); i++) {
                                    UserBean.MemberListBean item = bean.getMemberList().get(i);
                                    //if (t == 0) {//0全部 1男 2女 3群组 4男女
//                                        if (item.getVerifyFlag() == 0 && item.getContactFlag() == 3) {//
                                    //if (item.getVerifyFlag() == 0) {//
                                    //new SendMessageTask(item.getUserName(), Common.Content).execute();
                                    //}
                                    //} else
                                    if (t == 1) {//男
                                        if (item.getSex() == 1) {
                                            new SendMessageTask(item.getUserName(), Common.Content).execute();
                                        }
                                    } else if (t == 2) {//女
                                        if (item.getSex() == 2) {
                                            new SendMessageTask(item.getUserName(), Common.Content).execute();
                                        }
                                    } else if (t == 4) {//男女
                                        if (item.getSex() == 2 || item.getSex() == 1) {
                                            new SendMessageTask(item.getUserName(), Common.Content).execute();
                                        }
                                    }
                                    Thread.sleep(900);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            } else {
                Toast.makeText(getActivity(), "初始化成员失败！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String wxuin, wxsid, skey, passTicket;
    private boolean isWxNew = false;

    private int needSendCount = 0;

    class SendMessageTask extends AsyncTask {
        private String result;
        private String msg;
        private String toUserName;

        public SendMessageTask(String toUserName, String msg) {
            this.toUserName = toUserName;
            this.msg = msg;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e("MainActivity SendTask", "开始发送****************************************8");
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                if (isWxNew) {
                    result = AuthUtil.sendMessage2(wxuin, wxsid, skey, msg, "fromA", toUserName, passTicket);
                } else {//v2
                    result = AuthUtil.sendMessage(wxuin, wxsid, skey, msg, "fromA", toUserName, passTicket);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            String e = (String) o;
            Log.e("MainActivity SendTask", "发送结束****************************************8");
            MessageBean messageBean = list.get(0);
            int num = messageBean.getSendNum() + 1;
            messageBean.setSendNum(num);
            dao.update(messageBean);
            recyclerView.getAdapter().notifyItemChanged(0, messageBean);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initList();
        registerBroadCast();
    }

    private BroadcastReceiver changedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("com.sincerly.message".equals(intent.getAction())) {
                wxuin = intent.getStringExtra("uin");
                wxsid = intent.getStringExtra("sid");
                skey = intent.getStringExtra("skey");
                passTicket = intent.getStringExtra("passTicket");
                reload();
            }
        }
    };

    private void registerBroadCast() {
        getActivity().registerReceiver(changedReceiver, new IntentFilter("com.sincerly.message"));
    }

    private void initList() {
        dao = TKApplication.getApplication().getDaoSession().getMessageBeanDao();
        list.clear();
        list.addAll(dao.queryBuilder().orderDesc(MessageBeanDao.Properties.Id).list());
//		MessageBean messageBean = new MessageBean();
//		messageBean.setContent("清粉");
//		messageBean.setObject("全部");
//		messageBean.setPeople("Sincerly");
//		messageBean.setTotalPeople(1030);
//		messageBean.setImage("11");
//		messageBean.setTime("2017-04-24");
//		messageBean.setName("WEIKUO");
//		messageBean.setSendNum(103);
//
//		list.add(messageBean);
        adapter = new RBaseAdapter<MessageBean>(getActivity(), R.layout.item_msg, list) {

            @Override
            protected void fillItem(RViewHolder holder, MessageBean item, int position) {
                holder.setText(R.id.time, item.getTime());
                holder.setText(R.id.name, item.getName());
                holder.setText(R.id.content, item.getContent());
                holder.setText(R.id.object, "发送人群：" + item.getObject());
                holder.setText(R.id.people, "发送者:" + item.getPeople());
                holder.setText(R.id.total, "总人数:" + item.getTotalPeople() + "");
                holder.setText(R.id.send, "已发送:" + item.getSendNum() + "");

                holder.getView(R.id.delete).setOnClickListener(new OnActionClickListener(position));
            }

            @Override
            protected int getViewType(MessageBean item, int position) {
                return 0;
            }
        };
        recyclerView.setFocusable(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    class OnActionClickListener implements View.OnClickListener {
        private int position;

        public OnActionClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            deleteData(list.get(position).getId());
            list.remove(position);
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    //删
    private void deleteData(Long id) {
        dao.deleteByKey(id);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(changedReceiver);
    }
}

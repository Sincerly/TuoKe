package com.douding.tuoke.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.douding.tuoke.R;
import com.douding.tuoke.adapter.RBaseAdapter;
import com.douding.tuoke.adapter.RViewHolder;
import com.douding.tuoke.application.TKApplication;
import com.douding.tuoke.bean.entity.UserLoginEntity;
import com.douding.tuoke.bean.list.ImageBean;
import com.douding.tuoke.bean.list.MessageBean;
import com.douding.tuoke.bean.list.ModeBean;
import com.douding.tuoke.bean.list.ResponseBean;
import com.douding.tuoke.fragment.MessageFragment;
import com.douding.tuoke.greendao.ModeBeanDao;
import com.douding.tuoke.greendao.UserLoginEntityDao;
import com.douding.tuoke.util.AuthUtil;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Sincerly on 2017/4/24 0024.
 * Version: 1.0
 * QQ:1475590636
 * 邮箱：tgl1475590636@163.com
 * 应用类型：建材
 */

public class AddMessageActivity extends FragmentActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.msgTitle)
    EditText msgTitle;
    @BindView(R.id.msgContent)
    EditText msgContent;
    @BindView(R.id.objectSelector)
    TextView objectContent;
    @BindView(R.id.save)
    Button save;

    ModeBean bean;
    ModeBeanDao dao;
    Long id;
    @BindView(R.id.biaoqing)
    RecyclerView recyclerView;

    private List<ImageBean> images=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_message);
        ButterKnife.bind(this);
        dao = TKApplication.getApplication().getDaoSession().getModeBeanDao();
        userEntityDao=TKApplication.getApplication().getDaoSession().getUserLoginEntityDao();
        id = getIntent().getLongExtra("id", 0);
        if (id != null && id != 0) {
            bean = dao.load(id);
            if (bean != null) {
                msgTitle.setText(bean.getName());
                msgContent.setText(bean.getContent());
                objectContent.setText(bean.getObject());
            }
        }
        initBiaoQing();
    }

    private void initBiaoQing(){
        String[] array=getResources().getStringArray(R.array.biaoqing);
        for (int i=0;i<array.length;i++){
            ImageBean imageBean=new ImageBean();
            imageBean.setContent("["+array[i]+"]");
            imageBean.setImageId(getResources().getIdentifier("image_"+(i + 1), "drawable", getPackageName()));
            images.add(imageBean);
        }

        RBaseAdapter adapter = new RBaseAdapter<ImageBean>(AddMessageActivity.this, R.layout.item_image, images) {

            @Override
            protected void fillItem(RViewHolder holder, ImageBean item, int position) {
//                holder.getView(R.id.delete).setOnClickListener(new MessageFragment.OnActionClickListener(position));
//                holder.getView(R.id.bq).setOnClickListener(new Oncli);
                ImageView imageView=holder.getView(R.id.bq);
                imageView.setImageResource(images.get(position).getImageId());
                imageView.setOnClickListener(new ImageOnClick(position) );
            }

            @Override
            protected int getViewType(ImageBean item, int position) {
                return 0;
            }
        };
        recyclerView.setFocusable(false);
        recyclerView.setLayoutManager(new GridLayoutManager(AddMessageActivity.this,7));
        recyclerView.setAdapter(adapter);
    }

    class ImageOnClick implements View.OnClickListener{
        private int position;

        public ImageOnClick(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            String text=msgContent.getText().toString();
            StringBuilder sb=new StringBuilder();
            sb.append(text);
            sb.append(images.get(position).getContent());
            msgContent.setText(sb.toString());
        }
    }

    //查
    private ModeBean queryData(Long id) {
        List<ModeBean> modes = dao.loadAll();
        String userName = "";
        ModeBean b = null;
        for (int i = 0; i < modes.size(); i++) {
            if (modes.get(i).getId() == id) {
                b = modes.get(i);
                break;
            }
        }
        return b;
//		Toast.makeText(this, "查询全部数据==>" + userName, Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.back, R.id.save, R.id.objectSelector, R.id.biaoqing})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.save:
                if (checkNotNull()) {
                    save.setEnabled(false);
                    Intent intent = new Intent();
                    if (id != null && id != 0) {//修改
                        updateData();
                        intent.setAction("com.sincerly.needload");
                        sendBroadcast(intent);
                        finish();
                    } else {//新增
//                        insertData();
                        sendTask();
                    }

                    //setResult(RESULT_OK);
                }
                break;
            case R.id.objectSelector:
                showDialog();
                break;
            case R.id.biaoqing:
                break;
        }
    }

    private boolean checkNotNull() {
        if ("".equals(msgTitle.getText().toString())) {
            Toast.makeText(this, "模版标题不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if ("".equals(msgContent.getText().toString())) {
            Toast.makeText(this, "模版内容不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private Dialog dialog;
    private String object;

    private void showDialog() {
        if (dialog == null) {
            dialog = new Dialog(this, R.style.dialog);
        }
        View v = View.inflate(this, R.layout.dialog, null);
        RadioGroup rg = (RadioGroup) v.findViewById(R.id.group);
        rg.setOnCheckedChangeListener(new OnCheckChange());
        dialog.setContentView(setChecked(v));
        dialog.show();
    }

    private class OnCheckChange implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.radioButton:
                    object = "全部";
                    break;
                case R.id.radioButton2:
                    object = "男";
                    break;
                case R.id.radioButton3:
                    object = "女";
                    break;
                case R.id.radioButton4:
                    object = "群组";
                    break;
                case R.id.radioButton5:
                    object = "男女";
                    break;
            }
            if (dialog.isShowing()) dialog.dismiss();
            objectContent.setText(object);
        }
    }

    private View setChecked(View v) {
        String text = objectContent.getText().toString();
        switch (text) {
            case "全部":
                RadioButton rb = (RadioButton) v.findViewById(R.id.radioButton);
                rb.setChecked(true);
                break;
            case "男":
                RadioButton rb2 = (RadioButton) v.findViewById(R.id.radioButton2);
                rb2.setChecked(true);
                break;
            case "女":
                RadioButton rb3 = (RadioButton) v.findViewById(R.id.radioButton3);
                rb3.setChecked(true);
                break;
            case "群组":
                RadioButton rb4 = (RadioButton) v.findViewById(R.id.radioButton4);
                rb4.setChecked(true);
                break;
            case "男女":
                RadioButton rb5 = (RadioButton) v.findViewById(R.id.radioButton5);
                rb5.setChecked(true);
                break;
        }
        return v;
    }

    //增
    private void insertData() {
//		private String image;
//		private String name;
//		private String time;
//		private String content;
//		private String object;
        ModeBean bean = new ModeBean();
        bean.setImage("");
        bean.setName(msgTitle.getText().toString());
        bean.setObject(objectContent.getText().toString());
        bean.setContent(msgContent.getText().toString());
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
        bean.setTime(format.format(new Date()));
        dao.insert(bean);
        save.setEnabled(true);

        Intent intent=new Intent();
        intent.setAction("com.sincerly.needload");
        sendBroadcast(intent);
        finish();
    }

    //改
    private void updateData() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
        updateData = new ModeBean(id, "", msgTitle.getText().toString(), format.format(new Date()), msgContent.getText().toString(), objectContent.getText().toString());
        dao.update(updateData);
        save.setEnabled(true);
    }

    private void sendTask(){
        List<UserLoginEntity> list=new ArrayList<>();
        list.addAll(userEntityDao.queryBuilder().orderDesc(UserLoginEntityDao.Properties.Id).list());
        String id="";
        if(list!=null&&list.size()>0){
            UserLoginEntity u=list.get(0);
            id=u.getUserId();
        }
        if(!"".equals(id)){
            String url="http://www.webchat.guangzhiyi58.com/app/app.ashx?fangshi=fabu&user_ID="+8+"&title="+msgTitle.getText().toString()+"&content="+objectContent.getText().toString()+"&mID=1";
            new AddModeTask().execute(url);
        }else{
            Toast.makeText(AddMessageActivity.this,"存储数据被破坏,请重新登录!",Toast.LENGTH_SHORT).show();
        }
    }

    class AddModeTask extends AsyncTask<String,String,String> {
        private String result;

        @Override
        protected String doInBackground(String... params) {
            try {
                result = AuthUtil.doGet(params[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ResponseBean bean=new Gson().fromJson(result,ResponseBean.class);
            if(bean!=null&&"0".equals(bean.getResult())){
                insertData();//添加模板至本地
            }else{
                if(bean!=null){
                    Toast.makeText(AddMessageActivity.this,bean.getDescribing(), Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(AddMessageActivity.this,"添加失败，请重试！", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    ModeBean updateData;
    private UserLoginEntityDao userEntityDao;
}

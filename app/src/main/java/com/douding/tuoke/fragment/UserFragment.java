package com.douding.tuoke.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.douding.tuoke.R;
import com.douding.tuoke.activity.LoginActivity;
import com.douding.tuoke.activity.UpdatePwdActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Sincerly on 2017/4/28.
 */

public class UserFragment extends Fragment {

    @BindView(R.id.login)
    Button mLogin;
    @BindView(R.id.updatePwd)
    Button mUpdatePwd;
    @BindView(R.id.exit)
    Button mExit;
    private View view;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.login, R.id.updatePwd, R.id.exit})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                Intent intent=new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);

                break;
            case R.id.updatePwd:
                Intent intent2=new Intent(getActivity(), UpdatePwdActivity.class);
                startActivity(intent2);

                break;
            case R.id.exit:
                Intent intent3=new Intent(getActivity(),LoginActivity.class);
                startActivity(intent3);

                break;
        }
    }
}

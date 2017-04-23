package com.douding.tuoke.application;

import android.app.Application;
import android.content.Context;
import android.telephony.TelephonyManager;

import com.douding.tuoke.common.Common;

/**
 * Created by Sincerly on 2017/4/23 0023.
 * Version: 1.0
 * QQ:1475590636
 * 邮箱：tgl1475590636@163.com
 * 应用类型：建材
 */

public class TKApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();

//		TelephonyManager manager= (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
//		Common.DeivceId=manager.getDeviceId();
	}
}

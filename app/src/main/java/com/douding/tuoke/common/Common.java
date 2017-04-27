package com.douding.tuoke.common;

import com.douding.tuoke.bean.UserBean;
import com.douding.tuoke.bean.list.GroupBean;

import java.util.List;

/**
 * Created by Sincerly on 2017/4/22.
 */

public class Common {
    //public static String stepCode=HttpEnum.A.name();
	public static String DeivceId="";
	public static String NickName="";//个人昵称
	public static String Name="";//个人昵称Id

	public static Long id;
	public static String Content="";
	public static int type=0;//0全部 1男 2女 3群组 4男女
	public static boolean needSend=false;
	public static UserBean userBean;
	public static UserBean userInfo;//包含群组
	public static List<String> groupBeanList;//群组Name  @1231456456464
	public static boolean isWxNew=false;
}

package com.douding.tuoke.util;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author caodong 常用的工具类：判断是否是手机号 判断网络连接状态进行 判断WIFI是否连接 判断3G是否连接 判断GPS是否开启
 */
public class Utils {

	/**
	 * 把yyyy-MM-dd HH:mm 转化成几小时发前
	 * 
	 * @param time
	 * @return
	 */
	public static String getTimeMonthAndDay(String time, String format,
			String toFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		SimpleDateFormat toSdf = new SimpleDateFormat(toFormat);
		try {
			Date date = sdf.parse(time); 
			return toSdf.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
	}
	
	/**
	 * 
	 * @param fileS
	 *            格式化成 m
	 * @return
	 */
	public static String formetFileSize(long fileS) {// 转换文件大小
		if (fileS <= 0) {
			return "0.0";
		}
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "K";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}

	/**
	 * 返回当前屏幕是否为竖屏。
	 * 
	 * @param context
	 * @return 当且仅当当前屏幕为竖屏时返回true,否则返回false。
	 */
	public static boolean isScreenOriatationPortrait(Context context) {
		return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		if(context!=null){
			final float scale = context.getResources().getDisplayMetrics().density;
			return (int) (dpValue * scale + 0.5f);
		}
		return 0;
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 根据路径得到 文件名
	 * 
	 * @return
	 */
	public static String getFileName(String path) {

		String filename = path.substring(path.lastIndexOf("/") + 1);
		if (filename == null) {
			return null;
		}

		return filename;
	}

	/**
	 * 获取分辨率宽度
	 * 
	 * @param activity
	 * @return
	 */
	public static float getScreenWidth(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;// 获取分辨率宽度
	}

	/**
	 * 随机产生颜色
	 * 
	 * @return
	 */
	public static int getRandomColor() {// 分别产生RBG数值
		Random random = new Random();
		int R = random.nextInt(255);
		int G = random.nextInt(255);
		int B = random.nextInt(255);
		return Color.rgb(R, G, B);
	}

	/**
	 * 把yyyy-MM-dd HH:mm 转化成几小时发前
	 * 
	 * @param time
	 * @return
	 */
	public static String getTimeStr1(String time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timeStr = "";
		try {
			Date date = format.parse(time);
			Date date2 = new Date();

			long delta = (date2.getTime() - date.getTime()) / 1000;

			if (delta < 60) { // 1分钟内
				timeStr = "刚刚";
			} else if (delta < 60 * 60) { // 1小时内
				timeStr = delta / 60 + "分钟前";
			} else if (delta < 60 * 60 * 24) { // 1天内
				timeStr = delta / 60 / 60 + "小时前";
			} else {
				timeStr = time;
			}
			return timeStr;
		} catch (ParseException e) {
			e.printStackTrace();
			return time;
		}
	}

	/**
	 * yyyy-MM-dd HH:mm:ss
	 * 
	 * @param timeStr
	 * @return
	 */
	public static Date getTimeStr6(String timeStr) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			return format.parse(timeStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date();
		}

	}

	/**
	 * 把毫秒转有00：00：00
	 * 
	 * @param time
	 * @return
	 */
	public static String getTimeStr(int time) {
		String timeStr;
		if (time <= 0) {
			return "00:00:00";
		}
		time /= 1000;
		int minute = time / 60;
		int hour = minute / 60;
		int second = time % 60;
		minute %= 60;
		if (hour > 0) {
			timeStr = String.format("%02d:%02d:%02d", hour, minute, second);
		} else {
			timeStr = String.format("00:%02d:%02d", minute, second);
		}

		return timeStr;
	}

	/**
	 * 把毫秒转有00：00
	 * 
	 * @param time
	 * @return
	 */
	public static String getTimeStr2(int time) {
		String timeStr;
		if (time <= 0) {
			return "00:00";
		}
		time /= 1000;
		int minute = time / 60;
		int hour = minute / 60;
		int second = time % 60;
		minute %= 60;
		if (hour > 0) {
			timeStr = String.format("%02d:%02d:%02d", hour, minute, second);
		} else {
			timeStr = String.format("%02d:%02d", minute, second);
		}

		return timeStr;
	}

	/**
	 * 把毫秒转有00：00 没有小时 63:09
	 * 
	 * @param time
	 * @return
	 */
	public static String getTimeStr3(long time) {
		String timeStr;
		if (time <= 0) {
			return "00:00";
		}
		time /= 1000;
		long minute = time / 60;
		long second = time % 60;

		timeStr = String.format("%02d:%02d", minute, second);

		return timeStr;
	}

	/**
	 * 把毫秒转有 3'2"
	 * 
	 * @param time
	 * @return
	 */
	public static String getTimeStr4(int time) {
		String timeStr;
		if (time <= 0) {
			return "0\"";
		}
		time /= 1000;
		int minute = time / 60;
		int hour = minute / 60;
		int second = time % 60;
		minute %= 60;
		if (hour > 0) {
			timeStr = String.format("%02d:%02d:%02d", hour, minute, second);
		} else {
			timeStr = String.format("%d' %d\''", minute, second);
		}

		return timeStr;
	}

	/**
	 * 得到当前时间 yyyy-MM-dd HH:mm
	 * 
	 * @return
	 */
	public static String getTimeStr3() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		return format.format(new Date());
	}

	/**
	 * 得到当前时间 yyyy-MM-dd HH:mm
	 * 
	 * @return
	 */
	public static String getTimeStr3_1() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		return format.format(new Date());
	}

	/**
	 * 得到当前时间 yyyyMMdd
	 * 
	 * @param time
	 *            为 0得到当前时间
	 * @return
	 */
	public static String getTimeStr3_2(long time) {

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

		if (time == 0) {
			return format.format(new Date());
		} else {
			return format.format(new Date(time));
		}

	}

	/**
	 * 得到当前时间 yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String getTimeStr5() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		return format.format(new Date());
	}

	/**
	 * 得到当前时间 yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String getTimeStr5(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		return format.format(date);
	}

	/**
	 * 得到当前时间 yyyy-MM-dd HH:mm
	 * 
	 * @return
	 */
	public static String getTimeStr3(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		return format.format(date);
	}

	/**
	 * 获取分辨率高度
	 * 
	 * @param activity
	 * @return
	 */
	public static float getScreenHeight(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;// 获取分辨率宽度
	}

	/**
	 * 对网络连接状态进行判断
	 * 
	 * @param context
	 *            上下文
	 * @return true, 可用； false， 不可用
	 * 
	 * 
	 */
	public static boolean isOpenNetwork(Context context) {
		if(context!=null){
			ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connManager.getActiveNetworkInfo() != null) {
				return connManager.getActiveNetworkInfo().isAvailable();
			}else{
				Toast.makeText(context, "网络已断开", Toast.LENGTH_SHORT).show();
			}
		}
		return false;
	}

	/**
	 * 检测wifi是否连接
	 * 
	 * @param context
	 *            上下文
	 * @return true 连接 false 未连接
	 */
	public static boolean isWifiConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm != null) {
			NetworkInfo networkInfo = cm.getActiveNetworkInfo();
			if (networkInfo != null
					&& networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 检测3G是否连接
	 * 
	 * @param context
	 *            上下文
	 * @return true 连接 false 未连接
	 */
	public static boolean is3gConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm != null) {
			NetworkInfo networkInfo = cm.getActiveNetworkInfo();
			if (networkInfo != null
					&& networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 检测GPS是否开启
	 * 
	 * @param context
	 *            上下文
	 * @return true 开启 false 关闭
	 */
	public static boolean isGpsEnabled(Context context) {
		LocationManager lm = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		List<String> accessibleProviders = lm.getProviders(true);
		for (String name : accessibleProviders) {
			if ("gps".equals(name)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 得到设备的UUID
	 */
	protected static final String PREFS_FILE = "gank_device_id.xml";
	protected static final String PREFS_DEVICE_ID = "gank_device_id";
	protected static String uuid;

	static public String getUDID(Activity s_instance) {
		if (uuid == null) {

			if (uuid == null) {
				final SharedPreferences prefs = s_instance
						.getSharedPreferences(PREFS_FILE, 0);
				final String id = prefs.getString(PREFS_DEVICE_ID, null);
				if (id != null) {
					// Use the ids previously computed and stored in the prefs
					// file
					uuid = id;
				} else {

					final String androidId = Secure.getString(
							s_instance.getContentResolver(), Secure.ANDROID_ID);
					// Use the Android ID unless it's broken, in which case
					// fallback on deviceId,
					// unless it's not available, then fallback on a random
					// number which we store
					// to a prefs file
					try {
						if (!"9774d56d682e549c".equals(androidId)) {
							uuid = UUID.nameUUIDFromBytes(
									androidId.getBytes("utf8")).toString();
						} else {
							final String deviceId = ((TelephonyManager) s_instance
									.getSystemService(Context.TELEPHONY_SERVICE))
									.getDeviceId();
							uuid = deviceId != null ? UUID.nameUUIDFromBytes(
									deviceId.getBytes("utf8")).toString()
									: UUID.randomUUID().toString();
						}
					} catch (UnsupportedEncodingException e) {
						throw new RuntimeException(e);
					}

					// Write the value out to the prefs file
					prefs.edit().putString(PREFS_DEVICE_ID, uuid).commit();
				}
			}
		}
		return uuid;
	}

	// /**
	// * 根据文件名得到文件类型资源
	// * @param fileName
	// * @return
	// */
	// public static int getFileType(String fileName)
	// {
	// String suffix = fileName.substring(fileName.lastIndexOf(".") +
	// 1,fileName.length());
	// if ("doc".equals(suffix) || "docx".equals(suffix))
	// {// word文档；
	// return R.drawable.icon_item_word;
	// } else if ("xls".equals(suffix) || "xlsx".equals(suffix))
	// {// excel文档；
	// return R.drawable.icon_item_excel;
	// } else if ("txt".equals(suffix)) {// txt文档；
	// return R.drawable.icon_item_txt;
	// } else if ("apk".equals(suffix)) {// APK文件；
	// return R.drawable.icon_item_apk;
	// } else if ("rar".equals(suffix) || "zip".equals(suffix)||
	// "gz".equals(suffix))
	// {// RAR文件；
	// return R.drawable.icon_item_rar;
	// }
	// else if
	// ("avi,rmvb,rm,mp4,wmv,mkv,vcd,dvd,flv,3gp,3g3,3gpp2,mpeg4,ts,tp,mts,m2ts,mod,tod,sdp,yuv,dat,vob,divx,xvid,svcd,mpg,asf,f4v,mov,qt"
	// .contains(suffix))
	// {// 视频文件；
	// return R.drawable.icon_item_video;
	// } else if ("mp3,wma,wav,cda,mid,ra,rm,rmx,vqf,ogg".contains(suffix))
	// {// 音乐文件；
	// return R.drawable.icon_item_music;
	// } else if ("jpg,jpeg,gif,png,bmp,pict,tiff".contains(suffix))
	// {// 图片文件；
	// return R.drawable.icon_item_img;
	// } else {
	// return R.drawable.icon_item_file;
	// }
	// }
	//
	/**
	 * 得到文件夹的大小
	 * 
	 * @param file
	 * @return
	 */
	public static double getDirSize(File file) {
		// 判断文件是否存在
		if (file.exists()) {
			// 如果是目录则递归计算其内容的总大小
			if (file.isDirectory()) {
				File[] children = file.listFiles();
				double size = 0;
				for (File f : children)
					size += getDirSize(f);
				return size;
			} else {// 如果是文件则直接返回其大小,以“兆”为单位
				double size = file.length();
				return size;
			}
		} else {
			return 0.0;
		}
	}

	/**
	 * 删除某个文件夹下的所有文件夹和文件
	 * 
	 * @param delpath
	 *            String
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @return boolean
	 */
	public static boolean deletefile(String delpath) throws Exception {

		File file = new File(delpath);
		if (file.exists()) {
			// 当且仅当此抽象路径名表示的文件存在且 是一个目录时，返回 true
			if (!file.isDirectory()) {
				file.delete();

			} else if (file.isDirectory()) {
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++) {
					File delfile = new File(delpath + "/" + filelist[i]);
					if (!delfile.isDirectory()) {
						delfile.delete();
					} else if (delfile.isDirectory()) {
						deletefile(delpath + "/" + filelist[i]);
					}
				}
				file.delete();
			}
		}

		return true;
	}

	/**
	 * 没有网络
	 */
	public static void showNoneNetNotice(final Activity activity) {
		final Builder builder = new Builder(activity);
		builder.setMessage("你当前没有网络,可以在设置中开启网络");
		builder.setPositiveButton("设置",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (android.os.Build.VERSION.SDK_INT > 10) {
							// 3.0以上打开设置界面，也可以直接用ACTION_WIRELESS_SETTINGS打开到wifi界面
							activity.startActivity(new Intent(
									android.provider.Settings.ACTION_SETTINGS));
						} else {
							activity.startActivity(new Intent(
									android.provider.Settings.ACTION_WIRELESS_SETTINGS));
						}
						dialog.dismiss();

					}

				});
		builder.setNegativeButton("知道了",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}

				});

		builder.create().show();
	}

	/**
	 * 查看有没有网络
	 * 
	 * @param activity
	 * @param isclose
	 *            点击取消关闭当前页面
	 */
	public static void showNoneNetNotice(final Activity activity,
			final boolean isclose) {
		final Builder builder = new Builder(activity);
		builder.setMessage("你当前没有网络,可以在设置中开启网络");
		builder.setPositiveButton("设置",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (android.os.Build.VERSION.SDK_INT > 10) {
							// 3.0以上打开设置界面，也可以直接用ACTION_WIRELESS_SETTINGS打开到wifi界面
							activity.startActivity(new Intent(
									android.provider.Settings.ACTION_SETTINGS));
						} else {
							activity.startActivity(new Intent(
									android.provider.Settings.ACTION_WIRELESS_SETTINGS));
						}
						dialog.dismiss();
						activity.finish();

					}

				});
		builder.setNegativeButton("知道了",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						if (isclose) {
							activity.finish();
						}
					}

				});

		builder.create().show();
	}

	/**
	 * 判断是否是合法用户名 5-12位数字、英文或下划线】
	 * 
	 * @return
	 */
	public static boolean isVaildUsername(String username) {
		Pattern p = Pattern.compile("^[0-9a-zA-Z_]{5,12}$");
		Matcher m = p.matcher(username);
		return m.matches();
	}

	/**
	 * 判断是否是手机号
	 * 
	 * @param mobiles
	 *            判断的手机号
	 * @return true 是 false 不是
	 */
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(170)|(15[^4,\\D])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * 判断是否是邮箱
	 * 
	 * @return true 是 false 不是
	 */
	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	//

	/**
	 * 判断日期格式:yyyy-mm-dd
	 * 
	 * @return true 是 false 不是
	 */
	public static boolean isValidDate(String sDate) {
		String datePattern1 = "\\d{4}-\\d{2}-\\d{2}";
		String datePattern2 = "^((\\d{2}(([02468][048])|([13579][26]))"
				+ "[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|"
				+ "(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?"
				+ "((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?("
				+ "(((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?"
				+ "((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
		if ((sDate != null)) {
			Pattern pattern = Pattern.compile(datePattern1);
			Matcher match = pattern.matcher(sDate);
			if (match.matches()) {
				pattern = Pattern.compile(datePattern2);
				match = pattern.matcher(sDate);
				return match.matches();
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * 验证金额
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		Pattern pattern = Pattern
				.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后一位的数字的正则表达式
		Matcher match = pattern.matcher(str);
		if (match.matches() == false) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * @param distance
	 * @return
	 */
	public static String formetDistance(double distance) {
		String str = "";
		DecimalFormat df = new DecimalFormat("#.00");
		if (distance < 1000) { // 1分钟内
			str = df.format(distance) + "米";
		} else if (distance > 1000) { // 1小时内
			str = df.format(distance / 1000) + "千米";
		}
		return str;
	}

	/**
	 * @param name
	 */
	public static String showName(String name) {
		if (TextUtils.isEmpty(name)) {
			return "***";
		}

		String str = "";
		for (int i = 0; i < name.length(); i++) {
			if (i == (name.length() - 1)) {
				str += name.substring(name.length() - 1);
			} else {
				str += "*";
			}
		}

		return str;
	}

	/**
	 * 身份证 ＝》123456789123456789 =》1*****************9
	 * 
	 * @param card
	 */
	public static String showCard(String card) {
		if (TextUtils.isEmpty(card)) {
			return "***";
		}

		String str = "";
		for (int i = 0; i < card.length(); i++) {
			if (i == (card.length() - 1)) {
				str += card.substring(card.length() - 1);
			} else if (i == 0) {
				str += card.substring(0, 1);
			} else {
				str += "*";
			}
		}
		return str;
	}

	/**
	 * 手机号 ＝》18615652489 =》186****489
	 * 
	 * @param number
	 */
	public static String showNumber(String number) {
		if (TextUtils.isEmpty(number)) {
			return "***";
		}

		String str = "";
		for (int i = 0; i < number.length(); i++) {
			if (i < 3 || i > 6) {
				str += number.substring(i, i + 1);
			} else {
				str += "*";
			}
		}
		return str;
	}

	public static String getSurplusTime(Date startDate, Date endDate) {
		if (endDate.before(startDate)) {
			return new SimpleDateFormat("yyyy-MM-dd").format(endDate);
		}
		StringBuilder sb=new StringBuilder();
		int year=endDate.getYear()-startDate.getYear();
		int month=endDate.getMonth()-startDate.getMonth();
		int day=endDate.getDate()-startDate.getDate();
		int hours=endDate.getHours()-startDate.getHours();
		if(year!=0){
			sb.append(year+"年");
		}
		if(month!=0){
			sb.append(Math.abs(month)+"月");
			if(year!=0){
				return sb.toString();
			}
		}
		if(day!=0){
			sb.append(Math.abs(day)+"天");
			if(month!=0){
				return sb.toString();
			}
		}
		if(hours!=0){
			sb.append(Math.abs(hours)+"小时");
		}
		return sb.toString();
	}

	public static String getLastTime(Date date, Date date2) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String timeStr = "";
		try {
			long delta = (date2.getTime() - date.getTime()) / 1000;

			if (delta < 60) { // 1分钟内
				timeStr = "刚刚";
			} else if (delta < 60 * 60) { // 1小时内
				timeStr = delta / 60 + "分钟前";
			} else if (delta < 60 * 60 * 24) { // 1天内
				timeStr = delta / 60 / 60 + "小时前";
			} else {
				timeStr = format.format(date);
			}
			return timeStr;
		} catch (Exception e) {
			e.printStackTrace();
			return "获取时间失败";
		}
	}

	public static Bitmap getimage(String srcPath) {  
		BitmapFactory.Options newOpts = new BitmapFactory.Options();  
		//开始读入图片，此时把options.inJustDecodeBounds 设回true了  
		newOpts.inJustDecodeBounds = true;  
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//此时返回bm为空  
		    
		newOpts.inJustDecodeBounds = false;  
		int w = newOpts.outWidth;  
		int h = newOpts.outHeight;  
		//现在主流手机比较多是800*480分辨率，所以高和宽我们设置为  
		float hh = 800f;//这里设置高度为800f  
		float ww = 480f;//这里设置宽度为480f  
		//缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可  
		int be = 1;//be=1表示不缩放  
		if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放  
		    be = (int) (newOpts.outWidth / ww);  
		} else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放  
		    be = (int) (newOpts.outHeight / hh);  
		}  
		if (be <= 0)  
		    be = 1;  
			newOpts.inSampleSize = be;//设置缩放比例  
			//重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了  
		    bitmap = BitmapFactory.decodeFile(srcPath, newOpts);  
		    return compressImage(bitmap);//压缩好比例大小后再进行质量压缩  
		}  
	
	public static Bitmap compressImage(Bitmap image) {
	  
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();  
	    image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中  
	    int options = 100;  
	    while ( baos.toByteArray().length / 1024>100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩         
	    	baos.reset();//重置baos即清空baos  
		    image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中  
		    options -= 10;//每次都减少10  
		}  
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中  
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片  
		return bitmap;  
	}

	public static String formatDate(String string){
		String date=string.substring(0, 10);
		return date.replace("-", "/");
	}
	public static void hindKey(Context context,View view){
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		
//		下面这种隐藏键盘的方法，不需要传View：
//		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);  
	}
	public static void showKey(Context context,View view){
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(0, InputMethodManager.RESULT_SHOWN);
		view.requestFocus();
		view.requestFocusFromTouch();
	}
	/**
	 * 将数字0123，转换为字母ABCD
	 * @param position
	 * @return
	 */
	public static String NumberToString(int position){
		String [] str={"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
		return str[position];
	}
	
//	/**
//	 * 获取版本号
//	 * 
//	 * @return 当前应用的版本号
//	 */
//	public static String getVersion() {
//		try {
//			PackageManager manager = MyApplication.applicationContext.getPackageManager();
//			PackageInfo info = manager.getPackageInfo(MyApplication.applicationContext.getPackageName(), 0);
//			String version = "" + info.versionCode;
//			return version;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "1";
//	}

	/**
	 * 随机获取五位数字，作为验证码
	 * @return
	 */
	public static String getCodeNum(){
		String temp="";
		for(int i=0;i<5;i++){
			temp+=new Random().nextInt(10);
		}
		return temp;
	}
	
//	/**
//	 * 发送验证码
//	 * @author Administrator
//	 *
//	 */
//	public static class sendMsg extends AsyncTask<String, String, String>{
//
//		private boolean flag;
//		private String NET_WORK="network";
//		
//		private int Ret;
//		private String msg="";
//		
//		private Context context;
//		
//		public sendMsg(Context context){
//			this.context=context;
//		}
//
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//			if(!Utils.isOpenNetwork(context)){
//				flag=true;
//			}
//		}
//		
//		@Override
//		protected String doInBackground(String... params) {
//			if(flag){
//				return NET_WORK;
//			}
//			HashMap<String, String> map=new HashMap<String, String>();
//			map.put("action", "SendCode");
//			map.put("Mobile", params[0]);
//			try {
//				String json=HttpTool.postHttp("/App/UserLoginEntity/UserLoginEntity.aspx", map);
//				LogUtil.i(json);
//				JSONObject ob=new JSONObject(json);
//				Ret=ob.getInt("ret");
//				if(Ret==0){
//					JSONObject ob2=ob.getJSONObject("result");
//					Common.SMSNUM=ob2.getString("MobileCode");
//					Common.SMSOVERTIME=ob2.getString("OverTime");
//					return "y";
//				}else {
//					msg=ob.getString("Message");
//					return "n";
//				}
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			return "y";
//		}
//		@Override
//		protected void onPostExecute(String result) {
//			super.onPostExecute(result);
//			if(NET_WORK.equals(result)){
//				MyToast.show(context, "当前网络不可用", 0);
//			}else if("y".equals(result)){
//			}else if("n".equals(result)){
//				MyToast.show(context, msg, 0);
//			}else if("s".equals(result)){
//				MyToast.show(context, "获取验证码失败", 0);
//			}
//		}
//	}
	/**
	 * 获取时间差
	 */
	public static String getShiJianCha(Date date){
		long time=new Date().getTime()-date.getTime();
		long totalTime=7*24*60*60*1000;
		if(totalTime-time<=0){
			return "已经超过限定时间";
		}
		long timecha=(totalTime-time)/1000;
		long day=timecha/(24*60*60);
		long hour=(timecha%(60*60*24))/(60*60);
		long min=(timecha%(60*60))/60;
		long miao=timecha%60;
		String str="";
		if(day!=0){
			str+=day+"天";
			if(hour==0){
				str+="0小时";
			}
		}
		if(hour!=0){
			str+=hour+"小时";
			if(min==0){
				str+="0分钟";
			}
		}
		if(min!=0){
			str+=min+"分钟";
			if(miao==0){
				str+="0秒";
			}
		}
		if(miao!=0){
			str+=miao+"秒";
		}
		
		return str;
	}
	
}

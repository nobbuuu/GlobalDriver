package com.sy.globaltake_driver.jpush;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.sy.globaltake_driver.Activity.MainActivity;
import com.sy.globaltake_driver.Bean.JpushBean;
import com.sy.globaltake_driver.Bean.JpushCancleOrderBean;
import com.sy.globaltake_driver.Bean.MessageBean;
import com.sy.globaltake_driver.Bean.RegisterBean;
import com.sy.globaltake_driver.MyApplication;
import com.sy.globaltake_driver.utils.LogUtils;
import com.sy.globaltake_driver.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.data.JPushLocalNotification;

/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.e("action", "adction=" + intent.getAction());
        Log.e("action", "通知或消息的响应");
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            RegisterBean registerBean = new RegisterBean();
            registerBean.setRegisterId(regId);
            registerBean.setEventStr("checkRegisterId");
            EventBus.getDefault().post(registerBean);
            Log.e(TAG, "Registration Id=" + regId);
            //send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));


        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {

            Log.e("tag", "收到订单");
            String content = bundle.getString(JPushInterface.EXTRA_ALERT);
            Log.e("tag","content="+content);
            if (content != null) {
                if (content.contains("您有一个新的订单") || content.contains("new order")) {
                    String jpushData = printBundle(bundle);
                    Gson gson = new Gson();
                    JpushBean jpushBean = gson.fromJson(jpushData, JpushBean.class);
                    if (jpushBean != null) {
                        jpushBean.setEventStr("收到订单");
                        EventBus.getDefault().post(jpushBean);
                    }
                } else if (content.contains("系统消息") || content.contains("System message")) {
                    String dataStr = bundle.getString(JPushInterface.EXTRA_EXTRA);
                    Log.e("tag","dataStr="+dataStr);
                    Gson gson = new Gson();
                        MessageBean jpushBean = gson.fromJson(dataStr, MessageBean.class);
                    if (jpushBean != null) {
                        jpushBean.setEventStr("系统消息");
                        EventBus.getDefault().post(jpushBean);
                    }
                } else if (content.contains("取消行程") || content.contains("Trip Cancellation!")) {
                    String jpushData = bundle.getString(JPushInterface.EXTRA_EXTRA);
                    Gson gson = new Gson();
                    JpushCancleOrderBean jpushBean = gson.fromJson(jpushData, JpushCancleOrderBean.class);
                    if (jpushBean != null) {
                        jpushBean.setEventStr("取消行程");
                        EventBus.getDefault().post(jpushBean);
                    }
                }
            }
//            JPushInterface.clearAllNotifications(context);


        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");

            //恢复应用到前台显示
            Intent mIntent = new Intent(Intent.ACTION_MAIN);
            mIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            mIntent.setComponent(new ComponentName(context.getPackageName(), MainActivity.class.getCanonicalName()));
            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            context.startActivity(mIntent);
        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        String jpushData = "";
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
                LogUtils.Loge("abc", "bundle.getInt(key)=" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
                LogUtils.Loge("abc", "bundle.getBoolean(key)=" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    Log.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    jpushData = bundle.getString(JPushInterface.EXTRA_EXTRA);
                    LogUtils.Loge("jpushResult", "jpushResult=" + jpushData);
                    JSONObject json = new JSONObject(jpushData);
                    Iterator<String> it = json.keys();
                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        LogUtils.Loge("jpush", "content=" + sb.toString());
        return jpushData;
    }

    //send msg to JpushMain
   /* private void processCustomMessage(Context context, Bundle bundle) {
        if (MainActivity.isForeground) {
			String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
			String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
			Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
			msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
			if (!ExampleUtil.isEmpty(extras)) {
				try {
					JSONObject extraJson = new JSONObject(extras);
					if (extraJson.length() > 0) {
						msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
					}
				} catch (JSONException e) {

				}

			}
			context.sendBroadcast(msgIntent);
		}
	}*/
}

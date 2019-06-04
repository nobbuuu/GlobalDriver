package com.sy.globaltake_driver;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import org.xutils.x;

import cn.jpush.android.api.JPushInterface;
import cn.smssdk.SMSSDK;

/**
 * Created by sunnetdesign3 on 2017/2/10.
 */
public class MyApplication extends Application {

    private static MyApplication instance;

    public static MyApplication getInstance() {

        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(true);
        JPushInterface.setDebugMode(true);//如果时正式版就改成false
        JPushInterface.init(this);
        instance = MyApplication.this;
        SMSSDK.initSDK(this, "1c0c5be134f28", "36d3434f23c234d8f1f4fef237bcac4a");
        Log.e("tag","MyApplication");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}

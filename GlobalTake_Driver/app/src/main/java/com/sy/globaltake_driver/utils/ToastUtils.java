package com.sy.globaltake_driver.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.sy.globaltake_driver.MyApplication;

/**
 * Created by sunnetdesign3 on 2017/2/10.
 */
public class ToastUtils {
    public static void Toast_short(Context context,String content){
        Toast.makeText(context,content,Toast.LENGTH_SHORT).show();
    }
    public static void Toast_long(final Activity context, final String content){
        Toast.makeText(context,content,Toast.LENGTH_LONG).show();
        if("main".equals(Thread.currentThread().getName())){
            Toast.makeText(context, content, Toast.LENGTH_LONG).show();
        }else {
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, content, Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    public static void Toast_short(final Activity context, final String content){
        Toast.makeText(context,content,Toast.LENGTH_LONG).show();
        if("main".equals(Thread.currentThread().getName())){
            Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
        }else {
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    public static void Toast_short1(String content){
        Toast.makeText(MyApplication.getInstance(),content,Toast.LENGTH_SHORT).show();
    }
}

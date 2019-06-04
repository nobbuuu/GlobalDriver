package com.sy.globaltake_driver.Activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.sy.globaltake_driver.Const;
import com.sy.globaltake_driver.R;
import com.sy.globaltake_driver.utils.SharePreferenceUtils;

import cn.jpush.android.api.JPushInterface;

public class WelcomeActivity extends AppCompatActivity {

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Const.GO_GUIDE:
                    mIntent.setClass(WelcomeActivity.this,GuideActivity.class);
                    startActivity(mIntent);
                    finish();
                    break;
                case Const.GO_MAIN:
                    mIntent.setClass(WelcomeActivity.this,MainActivity.class);
                    startActivity(mIntent);
                    finish();
                    break;
                default:
                    break;
            }
        }
    };
    private ImageView mImageView;
    public Intent mIntent=new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
//            window.setNavigationBarColor(Color.TRANSPARENT);
//            | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
//            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        }
        setContentView(R.layout.activity_welcome);
//        XuniKeyWord.assistActivity(findViewById(android.R.id.content));

        mImageView= (ImageView) findViewById(R.id.welcome_pic);
        mImageView.startAnimation(AnimationUtils.loadAnimation(WelcomeActivity.this, R.anim.alpha));
        toNextActivity();
//        getHomeData();

    }
    @TargetApi(23)
    private void checkPermission(){
        if(!Settings.System.canWrite(this)){
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, 133);
        }
    }


    @Override
    protected void onDestroy() {
        if(null != mHandler) {
            mHandler.removeMessages(Const.GO_GUIDE);
            mHandler.removeMessages(Const.GO_MAIN);
            mHandler = null;
            Log.d("tag", "release Handler success");
        }
        super.onDestroy();
    }

    public void toNextActivity(){
        boolean is_first =  (Boolean) SharePreferenceUtils.getParam(getApplicationContext(), "is_first", false);
        if (!is_first) {
            //第一次启动进入引导页
            mHandler.sendEmptyMessageDelayed(Const.GO_GUIDE, Const.SPLASH_DELAY_TIME);

        }  else {
            //不是第一次启动直接进入首页
            mHandler.sendEmptyMessageDelayed(Const.GO_MAIN, Const.SPLASH_DELAY_TIME);
        }

    }

    /**
     * 首页数据(预加载)
     */

   /* private void getHomeData() {
        RequestParams entity = new RequestParams(Const.home);
        x.http().post(entity, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("首页数据", result);



            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }*/

}

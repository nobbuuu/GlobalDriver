package com.sy.globaltake_driver.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sy.globaltake_driver.Const;
import com.sy.globaltake_driver.R;
import com.sy.globaltake_driver.utils.CheckForAllUtils;
import com.sy.globaltake_driver.utils.DialogUtils;
import com.sy.globaltake_driver.utils.LanguageUtil;
import com.sy.globaltake_driver.utils.LogUtils;
import com.sy.globaltake_driver.utils.SharePreferenceUtils;
import com.sy.globaltake_driver.utils.ToastUtils;
import com.sy.globaltake_driver.utils.XuniKeyWord;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.OnSendMessageHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.CountryPage;
import cn.smssdk.gui.RegisterPage;

import static com.mob.tools.utils.R.getStringRes;

/**
 * Created by sunnetdesign3 on 2017/3/8.
 */
public class ChangeUserPhoneSecond extends AppCompatActivity {

    @Bind(R.id.changpwd_rootlay)
    LinearLayout changpwd_rootlay;
    @Bind(R.id.back_relay)
    RelativeLayout back_relay;
    @Bind(R.id.changpwd_yanzm)
    TextView yanzm_tv;
    @Bind(R.id.contry_tv)
    TextView contry_tv;
    @Bind(R.id.changpwd_phoneedt)
    EditText changpwd_phoneedt;
    @Bind(R.id.changpwd_pwdedt)
    EditText changpwd_pwdedt;
    @Bind(R.id.pwd_one)
    EditText pwd_one;
    @Bind(R.id.queren_btn)
    Button queren_btn;

    private Context context;
    private Dialog dialog;
    private String currentId = "42";

    private TimeCount time;
    protected static final int START_COUNT = 2;

    private String codeStr;
    // 短信注册，随机产生头像
    private static final String[] AVATARS = {
            "http://tupian.qqjay.com/u/2011/0729/e755c434c91fed9f6f73152731788cb3.jpg",
            "http://99touxiang.com/public/upload/nvsheng/125/27-011820_433.jpg",
            "http://img1.touxiang.cn/uploads/allimg/111029/2330264224-36.png",
            "http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339485237265.jpg",
            "http://diy.qqjay.com/u/files/2012/0523/f466c38e1c6c99ee2d6cd7746207a97a.jpg",
            "http://img1.touxiang.cn/uploads/20121224/24-054837_708.jpg",
            "http://img1.touxiang.cn/uploads/20121212/12-060125_658.jpg",
            "http://img1.touxiang.cn/uploads/20130608/08-054059_703.jpg",
            "http://diy.qqjay.com/u2/2013/0422/fadc08459b1ef5fc1ea6b5b8d22e44b4.jpg",
            "http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339510584349.jpg",
            "http://img1.touxiang.cn/uploads/20130515/15-080722_514.jpg",
            "http://diy.qqjay.com/u2/2013/0401/4355c29b30d295b26da6f242a65bcaad.jpg"
    };

    private Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case START_COUNT:
                    time = new TimeCount(60000, 1000); // 构造CountDownTimer对象
                    time.start();
                    break;

                default:
                    break;
            }
        };
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changphonesecond);
        ButterKnife.bind(this);
        XuniKeyWord.setShiPei(this,changpwd_rootlay);
        context = this;
        dialog = DialogUtils.initDialog(this);
        XuniKeyWord.setStatusBarBgColor(this,R.color.color666);
    }

    private void resetCellPhone(final String phone){
        RequestParams params = new RequestParams(Const.changeCellPhone);
        params.addBodyParameter("user_id", (String) SharePreferenceUtils.getParam(context,Const.UserId,""));
        params.addBodyParameter("phone",phone);
        LanguageUtil.addLanguage(params);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getString(Const.Code).equals(Const.reques_success)){
                        ToastUtils.Toast_short(context, LanguageUtil.getResText(R.string.toast_xiugaicg));
                        SharePreferenceUtils.setParam(context,Const.USER_PHONE,phone);
                        EventBus.getDefault().post("修改手机号");
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                if (dialog.isShowing()){
                    dialog.dismiss();
                }
            }
        });
    }

    /**
     * 发送验证码计时器
     *
     * @author wdp
     *
     */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            if (yanzm_tv!=null){
                yanzm_tv.setText(LanguageUtil.getResText(R.string.tv_sendagain));
                yanzm_tv.setClickable(true);
                yanzm_tv.setEnabled(true);
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            if (yanzm_tv!=null){
                yanzm_tv.setClickable(false);
                yanzm_tv.setEnabled(false);
                yanzm_tv.setText(millisUntilFinished / 1000 + LanguageUtil.getResText(R.string.tv_second));
            }
        }
    }

    @OnClick({R.id.changpwd_yanzm,R.id.queren_btn,R.id.back_relay,R.id.contry_tv})
    public void Onclick(View view){
        final String phone_num = changpwd_phoneedt.getText().toString();
        switch (view.getId()){
            case R.id.changpwd_yanzm:
                if (phone_num.isEmpty()){
                    ToastUtils.Toast_short(context,LanguageUtil.getResText(R.string.toast_inputphone));
                }else  {
                    SMSSDK.getVerificationCode(contry_tv.getText().toString(), phone_num, new OnSendMessageHandler() {
                        @Override
                        public boolean onSendMessage(String s, String s1) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtils.Toast_short(context,LanguageUtil.getResText(R.string.toast_sendsucess));
                                }
                            });
                            return false;
                        }
                    });
                    //计时开始
                    Message msg = Message.obtain();
                    msg.what = START_COUNT;
                    handler.sendMessage(msg);
                } /*else {
                    //提示手机号码错误
                    changpwd_phoneedt.setError(LanguageUtil.getResText(R.string.toast_inputrightphone));
                }*/
                break;
            case R.id.queren_btn:

                if (phone_num.isEmpty()){
                    ToastUtils.Toast_short(context,LanguageUtil.getResText(R.string.hint_inputphone));
                }else if (codeStr==null){
                    ToastUtils.Toast_short(context,LanguageUtil.getResText(R.string.toast_beforgetyzm));
                }else if (!changpwd_pwdedt.getText().toString().equals(codeStr)){
                    ToastUtils.Toast_short(context,LanguageUtil.getResText(R.string.toast_erroyzm));
                }else if (pwd_one.getText().toString().isEmpty()||!pwd_one.getText().toString().equals((String)SharePreferenceUtils.getParam(context,Const.USER_PWD,""))){
                    ToastUtils.Toast_short(context,LanguageUtil.getResText(R.string.toast_erropwd));
                }else {
                    if (!dialog.isShowing()){
                        dialog.show();
                    }
                    resetCellPhone(phone_num);
                }
                break;
            case R.id.back_relay:
                finish();
                break;
            case R.id.contry_tv://区号
                /*RegisterPage registerPage = new RegisterPage();
                registerPage.setRegisterCallback(new EventHandler() {
                    public void afterEvent(int event, int result, Object data) {
                        // 解析注册结果
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            @SuppressWarnings("unchecked")
                            HashMap<String,Object> phoneMap = (HashMap<String, Object>) data;
                            String country = (String) phoneMap.get("country");
                            String phone = (String) phoneMap.get("phone");
                            // 提交用户信息
                            registerUser(country, phone);
                        }
                    }
                });
                registerPage.show(this);*/
                // 国家列表
//                CountryPage countryPage = new CountryPage();
//                countryPage.setCountryId(currentId);
//                countryPage.showForResult(activity, null, this);
                Intent intent = new Intent(ChangeUserPhoneSecond.this,CountryActivity.class);
                startActivityForResult(intent,310);
                break;
        }
    }
    // 提交用户信息
    private void registerUser(String country, String phone) {
        Random rnd = new Random();
        int id = Math.abs(rnd.nextInt());
        String uid = String.valueOf(id);
        String nickName = "SmsSDK_User_" + uid;
        String avatar = AVATARS[id % 12];
        SMSSDK.submitUserInfo(uid, nickName, avatar, country, phone);
    }

    @SuppressWarnings("unchecked")
    public void onResult(HashMap<String, Object> data) {
        if (data != null) {
            int page = (Integer) data.get("page");
            if (page == 1) {
                // 国家列表返回
                currentId = (String) data.get("id");
                String[] country = SMSSDK.getCountry(currentId);
                if (country != null) {
//                    currentCode = country[1];
//                    tvCountryNum.setText("+" + currentCode);
//                    tvCountry.setText(country[0]);
                }
            } else if (page == 2) {
                // 验证码校验返回
                Object res = data.get("res");
                //Object smart = data.get("smart");

                HashMap<String, Object> phoneMap = (HashMap<String, Object>) data.get("phone");
                if (res != null && phoneMap != null) {
                    /*int resId = getStringRes(activity, "smssdk_your_ccount_is_verified");
                    if (resId > 0) {
                        Toast.makeText(activity, resId, Toast.LENGTH_SHORT).show();
                    }

                    if (callback != null) {
                        callback.afterEvent(
                                SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE,
                                SMSSDK.RESULT_COMPLETE, phoneMap);
                    }*/
                    finish();
                }
            }
        }
    }
    private void sendCode(String phone_num){
        RequestParams params = new RequestParams(Const.getCode);
        params.addBodyParameter("phone",phone_num);
        params.addBodyParameter("type","3");
        LanguageUtil.addLanguage(params);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject root = new JSONObject(result);
                    if (root.getString(Const.Code).equals(Const.reques_success)){
                        String result1 = root.getString("result");
                        if (result1!=null){
                            ToastUtils.Toast_short(context,LanguageUtil.getResText(R.string.toast_sendsucess));
                            ToastUtils.Toast_short(context, result1);
                            LogUtils.Loge("code","code="+ result1);
                            codeStr = result1;
                        }

                    }else {
                        ToastUtils.Toast_short(context,LanguageUtil.getResText(R.string.toast_getyzmfail));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtils.Toast_short(context,LanguageUtil.getResText(R.string.toast_getyzmfail));
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==310&&resultCode==310&&data!=null){
            String[] countries = data.getStringArrayExtra("country");
            if (countries[1]!=null){
                contry_tv.setText("+"+countries[1]);
            }
        }
    }
}

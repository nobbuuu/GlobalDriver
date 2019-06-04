package com.sy.globaltake_driver.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sy.globaltake_driver.Bean.LoginBean;
import com.sy.globaltake_driver.Bean.PriceRuleBean;
import com.sy.globaltake_driver.Const;
import com.sy.globaltake_driver.R;
import com.sy.globaltake_driver.utils.CheckForAllUtils;
import com.sy.globaltake_driver.utils.DialogUtils;
import com.sy.globaltake_driver.utils.LanguageUtil;
import com.sy.globaltake_driver.utils.LogUtils;
import com.sy.globaltake_driver.utils.SharePreferenceUtils;
import com.sy.globaltake_driver.utils.ToastUtils;
import com.sy.globaltake_driver.utils.XuniKeyWord;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by sunnetdesign3 on 2017/2/10.
 */
public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.phone_edt)
    EditText phone_edt;
    @Bind(R.id.pwd_edt)
    EditText pwd_edt;
    @Bind(R.id.login_btn)
    Button login_btn;
    @Bind(R.id.forgetpwd_tv)
    TextView forgetpwd_tv;
    @Bind(R.id.register_tv)
    TextView register_tv;
    @Bind(R.id.login_rootlay)
    LinearLayout login_rootlay;

    private Context context;
    private Dialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        XuniKeyWord.setShiPei(this,login_rootlay);
        context = this;
        dialog = DialogUtils.initDialog(this);
        setdate();

        setListener();
    }

    private void setListener() {
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (phone_edt.getText().toString().isEmpty()) {
                    ToastUtils.Toast_short(context, LanguageUtil.getResText(R.string.hint_inputphone));
                } else if (pwd_edt.getText().toString().isEmpty()) {
                    ToastUtils.Toast_short(context, LanguageUtil.getResText(R.string.hint_inputpwd));
                } else {

                    String MD5password = CheckForAllUtils.getMD5(pwd_edt.getText().toString());
                    String phonenum = phone_edt.getText().toString();
                    /*
                    *
                    *缓存用户信息
                    * */
                    SharePreferenceUtils.setParam(context, Const.USER_PHONE,phonenum);
                    SharePreferenceUtils.setParam(context,Const.USER_PWD,pwd_edt.getText().toString());
                    LogUtils.Loge("login", phonenum);
                    LogUtils.Loge("login", MD5password);
                    login(MD5password);

                }
            }
        });

        forgetpwd_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone_num = phone_edt.getText().toString();
                Intent intent = new Intent(context,ChangPasswordActivity.class);
                if (!phone_num.isEmpty()){
                    intent.putExtra("phone_num",phone_num);
                }else {
                    intent.putExtra("phone_num","");
                }
                startActivityForResult(intent,444);
            }
        });

        register_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    //初始化
    private void setdate(){
        String phone = (String) SharePreferenceUtils.getParam(context,Const.USER_PHONE,"");
        String pwd = (String) SharePreferenceUtils.getParam(context,Const.USER_PWD,"");

        if (!phone.isEmpty()){
            phone_edt.setText(phone);
        }
        if (!pwd.isEmpty()){
            pwd_edt.setText(pwd);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void login(String pwd) {

        if (!dialog.isShowing()){
            dialog.show();
        }
        RequestParams params = new RequestParams(Const.user_login);
        params.addBodyParameter("phone", phone_edt.getText().toString().trim());
        params.addBodyParameter("password", pwd.trim());
        params.addBodyParameter("pushCode", JPushInterface.getRegistrationID(this));//极光推送的token
        params.addBodyParameter("pushId", "1");//平台识别码，1：Android，0：ios
        params.addBodyParameter("type", "2");//用户类型；1：乘客，2：司机
        LanguageUtil.addLanguage(params);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                try {
                    JSONObject root = new JSONObject(result);
                    if (root.getString(Const.Code).equals(Const.reques_success)) {
                        Gson gs = new Gson();
                        LoginBean loginBean = gs.fromJson(result,LoginBean.class);
                        List<LoginBean.ResultBean> result1 = loginBean.getResult();
                        LoginBean.ResultBean.DriverBean resultBean = result1.get(0).getDriver();
                        if (resultBean!=null){
                            ToastUtils.Toast_short(context, root.getString("message"));
                            //司机email
                            if (resultBean.getFdEmail()!=null){
                                SharePreferenceUtils.setParam(context,Const.User_Email,resultBean.getFdEmail());
                            }
                            //userID
                            if (resultBean.getFdId()!=null){
                                SharePreferenceUtils.setParam(context,Const.UserId,resultBean.getFdId());

                            }
                            Log.e("tag","userId="+resultBean.getFdId());
                            //用户头像的缓存，若没上传过头像，则无该字段
                            if (resultBean.getFdIconUrl()!=null){
                                SharePreferenceUtils.setParam(context,Const.User_HeadIcon,resultBean.getFdIconUrl());
                            }
                            savaUserInfo(Const.User_Name,resultBean.getFdUserName());
                            savaUserInfo(Const.User_NickName,resultBean.getFdNickName());
                            savaUserInfo(Const.User_Stauts,resultBean.getFdDriveStatus());//出车与收车的状态
                            savaUserInfo(Const.fdPassengerCode,resultBean.getFdDriverCode());//司机编号
                            savaUserInfo(Const.fdPassengerSex,resultBean.getFdDriverSex());//司机性别
                            savaUserInfo(Const.fdPassowrd,resultBean.getFdPassowrd());//加密后的密码
                            savaUserInfo(Const.USER_PHONE,resultBean.getFdPhone());//司机手机号
                            savaUserInfo(Const.User_Email,resultBean.getFdEmail());//司机邮箱
                            savaUserInfo(Const.isLogin,true);
                            savaUserInfo(Const.driverType,resultBean.getVehicleType_fdName());//司机类型 1：出租车司机，2：摩托车司机
                            savaUserInfo(Const.driverCarNumber,resultBean.getFdVehicleTypeNum());//车牌号
                            //根据司机类型请求不同的加价规则
                            getPriceRule(Const.taxiPriceRule,"taxi");
                            getPriceRule(Const.motoPriceRule,"moto");
                            getKefuPhone(Const.getWaiterPhone);
                        }

                    } else {
                        ToastUtils.Toast_short(context, root.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtils.Toast_short(context, LanguageUtil.getResText(R.string.toast_serverexception));
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
    public void savaUserInfo(String key,Object info){
        if (info!=null){
            SharePreferenceUtils.setParam(context,key,info);
        }
    }
    public void savaPriceRule(Object info,String key){
        if (info!=null){
            SharePreferenceUtils.setParam(context,key,info);
        }
    }

    private void getPriceRule(String url, final String tag){
        RequestParams params = new RequestParams(url);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                PriceRuleBean ruleBean = gson.fromJson(result,PriceRuleBean.class);
                if (ruleBean!=null){
                    if (ruleBean.getCode().equals(Const.reques_success)){
                        PriceRuleBean.ResultBean priceBean = ruleBean.getResult();
                        if (tag.equals("taxi")){
                            savaPriceRule(priceBean.getJourney(),Const.journey);
                            savaPriceRule(priceBean.getMin(),Const.meter);
                            savaPriceRule(priceBean.getMoneybymin(),Const.moneybymeter);
                            savaPriceRule(priceBean.getMoneybytime(),Const.moneybyminute);
                            savaPriceRule(priceBean.getStartingfare(),Const.startingfare);
                        }else {
                            savaPriceRule(priceBean.getJourney(),Const.journey_moto);
                            savaPriceRule(priceBean.getMin(),Const.meter_moto);
                            savaPriceRule(priceBean.getMoneybymin(),Const.moneybymeter_moto);
                            savaPriceRule(priceBean.getMoneybytime(),Const.moneybyminute_moto);
                            savaPriceRule(priceBean.getStartingfare(),Const.startingfare_moto);
                        }

//                        Intent intent = new Intent(context,MainActivity.class);
//                        startActivity(intent);
                        setResult(222);
                        finish();
                    }
                }else {
                    ToastUtils.Toast_short(context,LanguageUtil.getResText(R.string.toast_parsingexception));
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
//                ToastUtils.Toast_short(context,"服务器异常异常");
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
    private void getKefuPhone(String url){
        RequestParams params = new RequestParams(url);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getString(Const.Code).equals(Const.reques_success)){
                        String phoneNumber = object.getString("result");
                        savaUserInfo(Const.waiterPhone,phoneNumber);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
//                ToastUtils.Toast_short(context,"服务器异常异常");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==444&&resultCode==444&&data!=null){
            String pwd = data.getStringExtra("pwd");
            if (pwd!=null){
                pwd_edt.setText(pwd);
            }
        }
    }
}

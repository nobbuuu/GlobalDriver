package com.sy.globaltake_driver.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sy.globaltake_driver.Bean.OrderInfoBean;
import com.sy.globaltake_driver.Bean.TripDetailBean;
import com.sy.globaltake_driver.Const;
import com.sy.globaltake_driver.R;
import com.sy.globaltake_driver.utils.CallPhoneutil;
import com.sy.globaltake_driver.utils.LanguageUtil;
import com.sy.globaltake_driver.utils.SharePreferenceUtils;
import com.sy.globaltake_driver.utils.ToastUtils;
import com.sy.globaltake_driver.utils.XuniKeyWord;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sunnetdesign3 on 2017/2/24.
 */
public class EndOrderActivity extends AppCompatActivity {

    @Bind(R.id.next_ordertv)
    TextView next_ordertv;
    @Bind(R.id.endorder_billingrule)
    TextView endorder_billingrule;
    @Bind(R.id.mileage_twotv)
    TextView mileage_twotv;
    @Bind(R.id.mileage_moneytwo)
    TextView mileage_moneytwo;
    @Bind(R.id.mileage_onetv)
    TextView mileage_onetv;
    @Bind(R.id.mileage_moneyone)
    TextView mileage_moneyone;
    @Bind(R.id.order_money)
    TextView order_money;
    @Bind(R.id.taker_endplace)
    TextView taker_endplace;
    @Bind(R.id.taker_startplace)
    TextView taker_startplace;
    @Bind(R.id.tip_tv)
    TextView tip_tv;
    @Bind(R.id.titlebar_shouche)
    TextView titlebar_shouche;
    @Bind(R.id.titlebar_centertv)
    TextView titlebar_centertv;
    @Bind(R.id.taker_headicon)
    ImageView taker_headicon;
    @Bind(R.id.taker_phone)
    ImageView taker_phone;
    @Bind(R.id.root_lay)
    LinearLayout root_lay;
    @Bind(R.id.jiajia_rulelay)
    LinearLayout jiajia_rulelay;
    @Bind(R.id.licheng_lay)
    LinearLayout licheng_lay;
    @Bind(R.id.time_lay)
    LinearLayout time_lay;
    private String tag;
    private Context context;
    private String phoneNumber;
    private String trip_id,order_id,order_status;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endorder);
        ButterKnife.bind(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//始终是竖屏
        context = this;
        XuniKeyWord.setShiPei(this,root_lay);
        XuniKeyWord.setStatusBarBgColor(this,R.color.statecolor);
        order_id = getIntent().getStringExtra("order_id");
        tag = getIntent().getStringExtra("tag");
        if (tag.equals("MainActivity")){
            String totalJourney = getIntent().getStringExtra("totalJourney");
            next_ordertv.setVisibility(View.VISIBLE);
            tip_tv.setVisibility(View.VISIBLE);
            titlebar_shouche.setText(LanguageUtil.getResText(R.string.tv_lianxiservice));
            titlebar_centertv.setText(LanguageUtil.getResText(R.string.tv_endorder));
            if (order_id!=null){
                Log.e("orderid",order_id);
                getOrderInfo(order_id,Double.parseDouble(totalJourney));
            }
        }else if (tag.equals("MyTripActivity")){
            trip_id = getIntent().getStringExtra("trip_id");
            next_ordertv.setVisibility(View.GONE);
            tip_tv.setVisibility(View.GONE);
            titlebar_shouche.setText(LanguageUtil.getResText(R.string.tv_more));
            titlebar_centertv.setText(LanguageUtil.getResText(R.string.tv_mytrip));
            if (order_id!=null){
                Log.e("trip_id",order_id);
                getTripDetail(trip_id);
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if (!tag.equals("MyTripActivity")){
                setResult(123);
            }
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void getOrderInfo(String order_id, final double totalJourney){
        RequestParams params = new RequestParams(Const.getOrderInfo);
        params.addBodyParameter("order_id",order_id);
        LanguageUtil.addLanguage(params);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                OrderInfoBean orderInfoBean = gson.fromJson(result,OrderInfoBean.class);
                if (orderInfoBean.getCode().equals(Const.reques_success)){
                    final OrderInfoBean.ResultBean.TripBean tripBean = orderInfoBean.getResult().get(0).getTrip();
                    final OrderInfoBean.ResultBean.OrderBean orderBean = orderInfoBean.getResult().get(0).getOrder();
                    if (tripBean!=null){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ImageOptions options = new ImageOptions.Builder().setFailureDrawableId(R.mipmap.tanchuang_touxiang)
                                        .setCircular(true)
                                        .setUseMemCache(true)
                                        .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                                        .setFailureDrawableId(R.mipmap.tanchuang_touxiang)
                                        .build();
                                if (tripBean.getPassenger_fdIconUrl()!=null){
                                    x.image().bind(taker_headicon,Const.root_url+tripBean.getPassenger_fdIconUrl(),options);
                                }

                                if (tripBean.getFdTripStartLocation()!=null){
                                    taker_startplace.setText(tripBean.getFdTripStartLocation());
                                }

                                if (tripBean.getFdTripEndLocation()!=null){
                                    taker_endplace.setText(tripBean.getFdTripEndLocation());
                                }
                                phoneNumber = tripBean.getPassenger_fdPhone();
                                Log.e("price","totaljJourney="+totalJourney);
                                long fdTripStartTime = tripBean.getFdTripStartTime();
                                long fdTripEndTime = tripBean.getFdTripEndTime();
                                String type= (String) SharePreferenceUtils.getParam(context,Const.driverType,"-1");
                                double journeyPrice = 0;
                                double totalMoney = 0;
                                int startPrice = 0;
                                int moneybymeter = 0;
                                int journey = 0;
                                int meter = 0;
                                //参数说明详见登录页面
                                long totalTime = (fdTripEndTime - fdTripStartTime) / 60000;//单位：分钟
                                Log.e("price","totalTime="+totalTime);
                                int minPrice = (int) SharePreferenceUtils.getParam(context, Const.moneybyminute, -1);
                                long timePrice = totalTime * minPrice;

                                if (type.equals(Const.driverType_taxi)){
                                    startPrice = (int) SharePreferenceUtils.getParam(context, Const.startingfare, -1);
                                    moneybymeter = (int) SharePreferenceUtils.getParam(context, Const.moneybymeter, -1);
                                    journey = (int) SharePreferenceUtils.getParam(context, Const.journey, -1);
                                    meter = (int) SharePreferenceUtils.getParam(context, Const.meter, -1);
                                }else {
                                    startPrice = (int) SharePreferenceUtils.getParam(context, Const.startingfare_moto, -1);
                                    moneybymeter = (int) SharePreferenceUtils.getParam(context, Const.moneybymeter_moto, -1);
                                    journey = (int) SharePreferenceUtils.getParam(context, Const.journey_moto, -1);
                                    meter = (int) SharePreferenceUtils.getParam(context, Const.meter_moto, -1);
                                }

                                if (totalJourney <= journey) {//路程小于等于起步价的里程
                                    journeyPrice = startPrice;
                                } else {
                                    journeyPrice = startPrice + (totalJourney - journey) * (moneybymeter / meter);
                                }
//                                totalMoney = journeyPrice+timePrice;
                                totalMoney = Double.parseDouble(orderBean.getFdOrderPrice());
                                order_money.setText(new DecimalFormat("0.00").format(totalMoney));

                                if (type.equals(Const.driverType_taxi)){
                                    licheng_lay.setVisibility(View.VISIBLE);
                                    time_lay.setVisibility(View.VISIBLE);
                                    mileage_onetv.setText(LanguageUtil.getResText(R.string.tv_journeymoney) + new DecimalFormat("0.00").format(totalJourney / 1000) + LanguageUtil.getResText(R.string.unit_km));
                                    mileage_twotv.setText(LanguageUtil.getResText(R.string.tv_timemoney) + new DecimalFormat("0").format(totalTime) + LanguageUtil.getResText(R.string.unit_min));
                                    mileage_moneyone.setText(new DecimalFormat("0.00").format(journeyPrice) + LanguageUtil.getResText(R.string.unit_money));
                                    mileage_moneytwo.setText(new DecimalFormat("0.00").format(timePrice) + LanguageUtil.getResText(R.string.unit_money));
                                }else {
                                    licheng_lay.setVisibility(View.GONE);
                                    time_lay.setVisibility(View.GONE);
                                }

                            }
                        });
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {
                ToastUtils.Toast_short(context,LanguageUtil.getResText(R.string.toast_serverexception));
            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void getTripDetail(String trip_id){
        RequestParams params = new RequestParams(Const.getTripInfo);
        params.addBodyParameter("trip_id",trip_id);
        LanguageUtil.addLanguage(params);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                try {
                    TripDetailBean tripDetailBean = gson.fromJson(result,TripDetailBean.class);
                    if (tripDetailBean!=null){
                        final TripDetailBean.ResultBean tripBean = tripDetailBean.getResult();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ImageOptions options = new ImageOptions.Builder().setFailureDrawableId(R.mipmap.tanchuang_touxiang)
                                        .setCircular(true)
                                        .setUseMemCache(true)
                                        .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                                        .build();
                                if (tripBean.getPassenger_fdIconUrl()!=null){
                                    x.image().bind(taker_headicon,Const.root_url+tripBean.getPassenger_fdIconUrl(),options);
                                }

                                if (tripBean.getFdTripStartLocation()!=null){
                                    taker_startplace.setText(tripBean.getFdTripStartLocation());
                                }

                                if (tripBean.getFdTripEndLocation()!=null){
                                    taker_endplace.setText(tripBean.getFdTripEndLocation());
                                }
                                phoneNumber = tripBean.getPassenger_fdPhone();
                                order_status = tripBean.getFdStatus();
                                long fdTripStartTime = tripBean.getFdTripStartTime();
                                long fdTripEndTime = tripBean.getFdTripEndTime();

                                double journeyPrice = 0;
                                double totalMoney = 0;
                                double totalJourney = Double.parseDouble(tripBean.getFdTripMileage());

                                //参数说明详见登录页面
                                long totalTime = (fdTripEndTime - fdTripStartTime) / 60000;//单位：分钟
                                Log.e("price","totalTime="+totalTime);
                                int minPrice = (int) SharePreferenceUtils.getParam(context, Const.moneybyminute, -1);
                                long timePrice = totalTime * minPrice;

                                int startPrice = (int) SharePreferenceUtils.getParam(context, Const.startingfare, -1);
                                int moneybymeter = (int) SharePreferenceUtils.getParam(context, Const.moneybymeter, -1);
                                int journey = (int) SharePreferenceUtils.getParam(context, Const.journey, -1);
                                int meter = (int) SharePreferenceUtils.getParam(context, Const.meter, -1);

                                if (totalJourney <= journey) {//路程小于等于起步价的里程
                                    journeyPrice = startPrice;
                                } else {
                                    journeyPrice = startPrice + (totalJourney - journey) * (moneybymeter / meter);
                                }
                                totalMoney = journeyPrice+timePrice;
                                order_money.setText(new DecimalFormat("0.00").format(totalMoney));

                                mileage_onetv.setText(LanguageUtil.getResText(R.string.tv_journeymoney)+totalJourney/1000+LanguageUtil.getResText(R.string.unit_km));
                                mileage_twotv.setText(LanguageUtil.getResText(R.string.tv_timemoney)+totalTime+LanguageUtil.getResText(R.string.unit_min));
                                mileage_moneyone.setText(journeyPrice+LanguageUtil.getResText(R.string.unit_money));
                                mileage_moneytwo.setText(timePrice+LanguageUtil.getResText(R.string.unit_money));
                            }
                        });
                    }
                }catch (Exception e){
                    ToastUtils.Toast_short(context,LanguageUtil.getResText(R.string.toast_parsingexception));
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

            }
        });
    }


    @OnClick({R.id.endorder_backimg,R.id.next_ordertv,R.id.titlebar_shouche,R.id.taker_phone,R.id.jiajia_rulelay})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.endorder_backimg:
                if (!tag.equals("MyTripActivity")){
                    setResult(123);
                }
                finish();
                break;
            case R.id.next_ordertv:
                setResult(123);
                finish();
                break;
            case R.id.titlebar_shouche:
                String tvtext = titlebar_shouche.getText().toString();
                if (tvtext.equals(LanguageUtil.getResText(R.string.tv_more))||tvtext.equals("More")){
                    onPingJiaYiZhouClick(order_id,order_status);
                }else if (tvtext.equals(LanguageUtil.getResText(R.string.tv_lianxiservice))){
                    String phoneNumber = (String) SharePreferenceUtils.getParam(context,Const.waiterPhone,"");
                    CallPhoneutil.callPhone(context,phoneNumber);
                }
                break;
            case R.id.taker_phone:
                if (phoneNumber!=null){
                    CallPhoneutil.callPhone(context,phoneNumber);
                }
                break;
            case R.id.jiajia_rulelay:
                Intent intent = new Intent(context,FareRuleActivity.class);
                startActivity(intent);
                break;
        }
    }

    private boolean isExpand;
    private Dialog dialog;

    private void onPingJiaYiZhouClick(final String order_id, final String order_status) {
        if (!isExpand) {
            dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.cancelorder_poplay);
            final LinearLayout cancel_order = (LinearLayout) dialog.findViewById(R.id.cancel_order);
            LinearLayout contact_service = (LinearLayout) dialog.findViewById(R.id.contact_service);
            final TextView poptop_tv = (TextView) dialog.findViewById(R.id.poptop_tv);
            if (order_status.equals("2")||order_status.equals("3")||order_status.equals("4")){//订单仍在进行中
                //取消订单
                poptop_tv.setText(LanguageUtil.getResText(R.string.tv_cancelorder));

            }else {//订单可删除（没有在进行中）
                poptop_tv.setText(LanguageUtil.getResText(R.string.tv_deleteorder));
            }
            //根据行程状态做相关处理
            cancel_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String tvName = poptop_tv.getText().toString();
                    if (tvName.equals(LanguageUtil.getResText(R.string.tv_cancelorder))){
                        if (trip_id!=null){
                            cancelOrder(trip_id);
                        }
                    }else {
                        deleteOrder(order_id);
                    }
                }
            });

            //联系客服
            contact_service.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String phoneNumber = (String) SharePreferenceUtils.getParam(context,Const.waiterPhone,"");
                    CallPhoneutil.callPhone(context,phoneNumber);
                    if (dialog.isShowing()){
                        dialog.dismiss();
                    }
                }
            });
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialog) {
                    // TODO Auto-generated method stub
                    isExpand = false;
                }
            });
            Window dialogWindow = dialog.getWindow();
//            dialogWindow.setBackgroundDrawable(new ColorDrawable(Color.RED));
            dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//            lp.dimAmount = 0f;
            int[] location = new int[2];
            titlebar_shouche.getLocationOnScreen(location);
            Rect out = new Rect();
            titlebar_shouche.getWindowVisibleDisplayFrame(out);
            lp.x = location[0];
            lp.y = location[1] - out.top + titlebar_shouche.getHeight();
            lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            dialogWindow.setAttributes(lp);
            dialog.show();
            isExpand = true;
        } else {
            if (dialog != null) {
                dialog.dismiss();
                dialog = null;
            }
        }
    }

    //取消订单就是传行程ID
    private void cancelOrder(String trip_id) {
        RequestParams params = new RequestParams(Const.cancleOrder);
        params.addBodyParameter("trip_id", trip_id);
        LanguageUtil.addLanguage(params);
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getString(Const.Code).equals(Const.reques_success) && obj.getString("message").equals(LanguageUtil.getResText(R.string.request_success))) {
                        ToastUtils.Toast_short(context, LanguageUtil.getResText(R.string.tv_canceledorder));
                        setResult(520);
                        finish();

                    } else {
                        ToastUtils.Toast_short(context, obj.getString("message"));
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
    private void deleteOrder(String order_id) {
        RequestParams params = new RequestParams(Const.deleteOrder);
        params.addBodyParameter("order_id", order_id);
        params.addBodyParameter("type","2");//用户类型；1：乘客，2：司机
        LanguageUtil.addLanguage(params);
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getString(Const.Code).equals(Const.reques_success) && obj.getString("message").equals(LanguageUtil.getResText(R.string.request_success))) {
                        ToastUtils.Toast_short(context, LanguageUtil.getResText(R.string.tv_canceledorder));
                        setResult(520);
                        finish();

                    } else {
                        ToastUtils.Toast_short(context, obj.getString("message"));
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
}

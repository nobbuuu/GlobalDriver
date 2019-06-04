package com.sy.globaltake_driver.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.sy.globaltake_driver.Bean.CountryBean;
import com.sy.globaltake_driver.Bean.JpushBean;
import com.sy.globaltake_driver.Bean.JpushCancleOrderBean;
import com.sy.globaltake_driver.Bean.LatlngBean;
import com.sy.globaltake_driver.Bean.MessageBean;
import com.sy.globaltake_driver.Bean.MsgitemBean;
import com.sy.globaltake_driver.Bean.OrderBean;
import com.sy.globaltake_driver.Bean.PolyLinesBean;
import com.sy.globaltake_driver.Bean.QiangDanBean;
import com.sy.globaltake_driver.Bean.RegisterBean;
import com.sy.globaltake_driver.Bean.TripDetailBean;
import com.sy.globaltake_driver.Const;
import com.sy.globaltake_driver.CustomView.TakerPhoneItemView;
import com.sy.globaltake_driver.CustomView.XTmsgItemView;
import com.sy.globaltake_driver.CustomView.order_itemView;
import com.sy.globaltake_driver.R;
import com.sy.globaltake_driver.utils.AppManager;
import com.sy.globaltake_driver.utils.DialogUtils;
import com.sy.globaltake_driver.utils.FileUtil;
import com.sy.globaltake_driver.utils.LanguageUtil;
import com.sy.globaltake_driver.utils.LogUtils;
import com.sy.globaltake_driver.utils.SharePreferenceUtils;
import com.sy.globaltake_driver.utils.StringUtils;
import com.sy.globaltake_driver.utils.ToastUtils;
import com.sy.globaltake_driver.utils.WGSTOGCJ02;
import com.sy.globaltake_driver.utils.XuniKeyWord;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    /**
     * 地图展示
     * 有时，设置网络也不行，还得到手机设置获取位置的方式：WLAN,GPS,网络（巨坑啊）
     */
    private Marker currentMarker;
    @Bind(R.id.main_drawerlayout)
    DrawerLayout main_drawerlay;
    @Bind(R.id.main_menu_layout)
    LinearLayout main_menu_layout;

    @Bind(R.id.main_head_icon)
    ImageView main_head_icon;
    @Bind(R.id.head_icon)
    ImageView head_icon;
    @Bind(R.id.left_iv)
    ImageView left_iv;
    @Bind(R.id.mid_iv)
    ImageView mid_iv;
    @Bind(R.id.right_iv)
    ImageView right_iv;
    @Bind(R.id.main_backimg)
    ImageView main_backimg;
    @Bind(R.id.mid_tv)
    TextView mid_tv;
    @Bind(R.id.titlebar_shouche)
    TextView titlebar_shouche;
    @Bind(R.id.ordernum_tv)
    TextView ordernum_tv;
    @Bind(R.id.daohang_tiptv)
    TextView daohang_tiptv;
    @Bind(R.id.ordering_distance)
    TextView ordering_distance;
    @Bind(R.id.ordering_time)
    TextView ordering_time;
    @Bind(R.id.user_name)
    TextView user_name;
    @Bind(R.id.titlebar_centertv)
    TextView titlebar_centertv;
    @Bind(R.id.order_bottom_tv)
    TextView order_bottom_tv;
    @Bind(R.id.timenum_tv)
    TextView timenum_tv;
    @Bind(R.id.my_trip_tv)
    TextView my_trip_tv;
    @Bind(R.id.msgcount)
    TextView msgcount;

    @Bind(R.id.root_relay)
    LinearLayout root_relay;
    @Bind(R.id.user_head_lay)
    LinearLayout user_head_lay;
    @Bind(R.id.left_lay)
    LinearLayout chuche_lay;
    @Bind(R.id.mid_lay)
    LinearLayout mid_lay;
    @Bind(R.id.right_lay)
    LinearLayout shouche_lay;
    @Bind(R.id.order_lay)
    LinearLayout order_lay;
    @Bind(R.id.ordernum_lay)
    LinearLayout ordernum_lay;
    @Bind(R.id.ordering_bottomlay)
    LinearLayout ordering_bottomlay;
    @Bind(R.id.waittime_lay)
    LinearLayout waittime_lay;
    @Bind(R.id.help_layout)
    LinearLayout help_layout;
    @Bind(R.id.about_layout)
    LinearLayout about_layout;
    @Bind(R.id.message_relay)
    RelativeLayout message_relay;
    @Bind(R.id.order_jiedao)
    RelativeLayout order_jiedao;

    private LocationManager locationManager;
    private GoogleMap mGoogleMap;
    private Location mLocation;

    private Activity context;
    private GoogleApiClient mGoogleApiClient;
    private String logTag = "driver_main";
    private String addressString = "";
    private String firstAddressString = "";
    private String firstShortAddressString = "";
    private double latitude = 0;
    private double longitude = 0;
    private double templatitude = 0;
    private double templongitude = 0;
    private double takerlatitude = 0;//乘客起点坐标纬度
    private double takerlongitude = 0;//乘客起点坐标金度
    private Marker myLocation, startPlace, endPlace;
    private String startPlace_key = "startPlace";
    private String endPlace_key = "endPlace";
    private String myLocation_key = "myLocation";
    double start_latitude = 0;
    double start_longitude = 0;
    double end_latitude = 0;
    double end_longitude = 0;

    private String userId;
    public static boolean isStart = false;
    public static boolean isLogin;


    private Map<String, order_itemView> orderItemViewMap = new HashMap<>();

    private LatLngBounds bounds;
    private Dialog dialog;

    private String TotalMileage;
    private String orderStatus = "-1";
    private String trip_id;
    private String order_id;
    private String oderNum;
    private Dialog alertDialog;
    private boolean isCancel;
    private boolean isEndTrip;
    private boolean isReceive;//是否接到乘客
    private Location currentLocation;

    private long startTime, endTime;
    private int minute = 0, second = 10;

    private List<String> journey_list = new ArrayList<>();
    private boolean isJifei;
    private MediaPlayer mediaPlayer;
    private boolean isChina = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);
        XuniKeyWord.setShiPei(this, root_relay);
        context = this;
        EventBus.getDefault().register(this);
        isLogin = (boolean) SharePreferenceUtils.getParam(context, Const.isLogin, false);
        userId = (String) SharePreferenceUtils.getParam(context, Const.UserId, "");
        SharePreferenceUtils.setParam(context, Const.orderStauts, "-1");
        dialog = DialogUtils.initDialog(this);
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        //初始化
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addApi(LocationServices.API)
                .build();
        try {
            MapsInitializer.initialize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int errorCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        Log.e("code", "errorCode=" + errorCode);

        if (ConnectionResult.SUCCESS != errorCode&&ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED!=errorCode) {
//            GooglePlayServicesUtil.getErrorDialog(errorCode,
//                    this.getActivity(), 0).show();
            //提示用户安装谷歌安装器
            showDialog(LanguageUtil.getResText(R.string.tv_ggtip),"gg");
        } else {
            mapFragment.getMapAsync(this);

        }
        LoginSet();
        message_relay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MyMessageActivity.class);
                startActivity(intent);
            }
        });

        mediaPlayer = createLocalMp3();
        String regId = JPushInterface.getRegistrationID(this);
        Log.e("tag", "Registration Id=" + regId);
    }

    private void LoginSet() {
        String userIcon_url = (String) SharePreferenceUtils.getParam(context, Const.User_HeadIcon, "");
        String userStatus = (String) SharePreferenceUtils.getParam(context, Const.User_Stauts, "-1");
        LogUtils.Loge("tag", "userStatus=" + userStatus);
        String userName = (String) SharePreferenceUtils.getParam(context, Const.User_NickName, "");
        ImageOptions options = new ImageOptions.Builder()
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setCircular(true)
                .setUseMemCache(true)
                .setFailureDrawableId(R.mipmap.tanchuang_touxiang)
                .build();
        if (!userIcon_url.isEmpty()) {
            x.image().bind(head_icon, Const.root_url + userIcon_url, options);
        } else {
            head_icon.setImageResource(R.mipmap.tanchuang_touxiang);
        }
        boolean isLogin = (boolean) SharePreferenceUtils.getParam(context, Const.isLogin, false);
        if (isLogin) {
            user_name.setText(userName);
        } else {
            user_name.setText(LanguageUtil.getResText(R.string.tv_notlogin));
        }

        if (String.valueOf(userStatus).equals("1")) {//收车状态
            Message message = Message.obtain();
            message.what = 77;
            mHandler.sendMessage(message);
        } else if (String.valueOf(userStatus).equals("2")) {//出车状态
            Message message = Message.obtain();
            message.what = 78;
            mHandler.sendMessage(message);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        String userStatus = (String) SharePreferenceUtils.getParam(context, Const.User_Stauts, "-1");

        if (String.valueOf(userStatus).equals(Const.carStatus_in)) {//收车状态
            Message message = Message.obtain();
            message.what = 77;
            mHandler.sendMessage(message);
        } else if (String.valueOf(userStatus).equals(Const.carStatus_out)) {//出车状态
            Message message = Message.obtain();
            message.what = 78;
            mHandler.sendMessage(message);
        }

    }

    private void shoucheRefreshUi() {
        left_iv.setImageResource(R.mipmap.shouye_chuche);
        right_iv.setImageResource(R.mipmap.shouye_shouche_sel);
        mid_iv.setImageResource(R.mipmap.shouye_xiuxizhong);
        mid_tv.setText(LanguageUtil.getResText(R.string.tv_xiuxi));
        right_iv.setClickable(false);
        left_iv.setClickable(true);
        shouche_lay.setClickable(false);
        chuche_lay.setClickable(true);
//        ToastUtils.Toast_short(context, "收车了~~");
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (mGoogleMap == null) {
            mGoogleMap = googleMap;
        }
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        /**设置地图类型**/
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        /*mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
//                        ToastUtils.showLong(HouseStepTwoActivity.this,getString(R.string.house_add_step2_locationing));
                        double lat = latLng.latitude;
                        double lon = latLng.longitude;

                        String address = getAddress(context, lat, lon);

                        LatLng ll = new LatLng(lat, lon);
                        mGoogleMap.clear();//先清除，已经有的锚点
                        mGoogleMap.addMarker(new MarkerOptions().position(ll).title(addressString));
                        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ll, 15));
                        LogUtils.Loge(logTag,address);
//                        tv_address_detail.setText(address);


                    }
                });
            }
        });*/
        mGoogleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                LatLng ll = new LatLng(latitude, longitude);
               /* mGoogleMap.clear();//先清除，已经有的锚点
                mGoogleMap.addMarker(new MarkerOptions().position(ll)
                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.shouye_gensui))
                        .title(firstShortAddressString));*/
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ll, 13));

//                tv_address_detail.setText(firstAddressString);

                return false;
            }
        });

        UiSettings uiSettings = mGoogleMap.getUiSettings();
        //右下角的状态栏
        uiSettings.setMapToolbarEnabled(false);
        //缩放比例控件
        uiSettings.setZoomControlsEnabled(false);
        //指南针(无法强制始终显示指南针)
        uiSettings.setCompassEnabled(false);
        //支持所有手势
        uiSettings.setAllGesturesEnabled(true);
        //My Location 按钮(调用了mMap.setMyLocationEnabled(true)，才会显示)
        uiSettings.setMyLocationButtonEnabled(true);
        //设置权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    13);
        } else {
            if (isOPen(this)) {
                doWork();
            } else {
                // 转到手机设置界面，用户设置GPS
                Intent intent = new Intent(
                        Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent, 0); // 设置完成后返回到原来的界面
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
        if (mHandler!=null){
            mHandler.removeCallbacks(cfRunnable);
            mHandler.removeCallbacks(timeRun);
            mHandler.removeMessages(007);
            mHandler.removeMessages(73);
            mHandler.removeMessages(74);
            mHandler.removeMessages(75);
            mHandler.removeMessages(76);
            mHandler.removeMessages(77);
            mHandler.removeMessages(78);
            mHandler.removeMessages(79);
            mHandler = null;
        }
    }

    private void doWork() {
        mGoogleMap.setMyLocationEnabled(true);//小蓝点的显示
        //权限检测
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

      /*  Criteria criteria = new Criteria();
        // 获得最好的定位效果
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);//模糊查询：ACCURACY_COARSE；高精度：ACCURACY_FINE
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(false);
        // 使用省电模式
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        // 获得当前的位置提供者
        String provider = locationManager.getBestProvider(criteria, true);

        Log.e("location","locationManager="+locationManager);*/
        // 获得当前的位置
        //在室外使用GPS定位

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 0, this);
        mLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);//获取定位对象
        if (mLocation == null) {
            //在室内使用net定位
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 0, this);
            mLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);//获取定位对象
        }
        if (mLocation != null) {
            //定位成功 初始化变量
            mGoogleMap.clear();
            String locationStr = mLocation.getLatitude() + "," + mLocation.getLongitude();
            getCountryName(locationStr);
//            addOnlyoneMarker(myLocation_key, sydney, R.mipmap.shouye_gensui, 1);
        } else {
            ToastUtils.Toast_short(context, LanguageUtil.getResText(R.string.toast_getlocationfail));
        }
    }

    //实时跟新当前位置（发送司机当前位置）
    private void postMyLocation(double latitude, double longitude) {
        String userId = (String) SharePreferenceUtils.getParam(context,Const.UserId,"");
        RequestParams params = new RequestParams(Const.sendMyLocation);
        if (!userId.isEmpty()) {
            params.addBodyParameter("user_id", userId);
        }else {
            ToastUtils.Toast_short(context,LanguageUtil.getResText(R.string.toast_userid));
        }
        //经纬度
        params.addBodyParameter("pointj", String.valueOf(longitude));
        params.addBodyParameter("pointw", String.valueOf(latitude));
        LanguageUtil.addLanguage(params);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getString(Const.Code).equals(Const.reques_success)) {
//                        ToastUtils.Toast_short(context, "位置发送成功");
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

            }
        });
    }

    @OnClick({R.id.tousu_layout, R.id.user_head_lay, R.id.my_trip_tv, R.id.main_head_icon, R.id.left_iv, R.id.mid_lay, R.id.right_iv, R.id.titlebar_shouche, R.id.main_backimg, R.id.ordering_bottomlay, R.id.order_bottom_tv, R.id.help_layout, R.id.about_layout})
    public void onClick(View view) {
        Intent intent_web = new Intent(MainActivity.this, WebAvtivity.class);
        switch (view.getId()) {
            case R.id.user_head_lay:
                if (isLogin) {
                    Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
                    startActivityForResult(intent, 333);
                } else {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivityForResult(intent, 222);
                }
                break;
            case R.id.main_head_icon:
                main_drawerlay.openDrawer(main_menu_layout);
                break;
            case R.id.left_iv:

                //司机出车
                if (!isLogin) {
                    ToastUtils.Toast_short(context, LanguageUtil.getResText(R.string.toast_notlogin));
                } else {
                    chuche();
                }
                break;
            case R.id.mid_iv:

                break;
            case R.id.my_trip_tv:
                Intent intent = new Intent(MainActivity.this, MyTripActivity.class);
                startActivity(intent);
                break;
            case R.id.right_iv:
                //司机收车
                if (!isLogin) {
                    ToastUtils.Toast_short(context, LanguageUtil.getResText(R.string.toast_notlogin));
                } else {

                    shouche("right_iv");
                }
                break;

            case R.id.titlebar_shouche://收车
                String titlerightStr = titlebar_shouche.getText().toString();
                switch (titlerightStr) {
                    case "收车"://中文判断条件
                        if (!isLogin) {
                            ToastUtils.Toast_short(context, LanguageUtil.getResText(R.string.toast_notlogin));
                        } else {
                            shouche("titlebar_shouche");
                        }
                        break;
                    case "collect"://英文判断条件
                        if (!isLogin) {
                            ToastUtils.Toast_short(context, LanguageUtil.getResText(R.string.toast_notlogin));
                        } else {
                            shouche("titlebar_shouche");
                        }
                        break;
                    case "取消订单":
                        if (trip_id != null) {
                            //弹窗提示
                            showDialog(LanguageUtil.getResText(R.string.dialog_tip),"cancelOrder");
                        }
                        break;
                    case "Cancel":
                        if (trip_id != null) {
                            //弹窗提示
                            showDialog(LanguageUtil.getResText(R.string.dialog_tip),"cancelOrder");
                        }
                        break;
                }
                break;
            case R.id.order_bottom_tv://开始计费
                String tvtext = order_bottom_tv.getText().toString();
                if (tvtext.equals("开始计费") || tvtext.equals("Start billing")) {
                    if (order_id != null) {
                        LogUtils.Loge("startBilling", "order_id=" + order_id);
                        startBilling();
                    }
                } else if (tvtext.equals("结束订单") || tvtext.equals("End order")) {
                    order_bottom_tv.setText(LanguageUtil.getResText(R.string.tv_starbilling));//还原状态
                    caculatePrice("1");
                }
                break;
            case R.id.ordering_bottomlay://接到乘客
                ordering_bottomlay.setVisibility(View.GONE);
                order_lay.removeAllViews();
                order_bottom_tv.setVisibility(View.VISIBLE);
                titlebar_shouche.setVisibility(View.GONE);
                ordernum_lay.setVisibility(View.GONE);
                titlebar_centertv.setText(LanguageUtil.getResText(R.string.tv_jiedaotaker));
                isReceive = false;//还原标志位，否则重新定位后还会去请求路线数据
                Log.e("tag", "isReceive=" + isReceive);
                break;
            case R.id.main_backimg://返回键
                Log.e("or", "orderStatus=" + orderStatus);
                switch (orderStatus) {//订单状态

                    case "-1"://默认状态
                        main_backimg.setVisibility(View.GONE);
                        main_head_icon.setVisibility(View.VISIBLE);
                        titlebar_centertv.setText("Global Take");
                        message_relay.setVisibility(View.VISIBLE);
                        titlebar_shouche.setVisibility(View.GONE);
                        order_lay.removeAllViews();
                        ordering_bottomlay.setVisibility(View.GONE);
                        chuche_lay.setVisibility(View.VISIBLE);
                        mid_lay.setVisibility(View.VISIBLE);
                        shouche_lay.setVisibility(View.VISIBLE);
                        mGoogleMap.clear();
                        addMylocationMarker();
                        break;

                    case "0"://删除订单（取消）

                        break;

                    case "1"://已完成

                        break;

                    case "2"://正在拉客
                        showDialog(LanguageUtil.getResText(R.string.dialog_tip),"cancelOrder");

                        break;
                }
                break;

            case R.id.help_layout:

                intent_web.putExtra("webUrl", Const.webRootUrl + Const.KeyHelp);
                intent_web.putExtra("tag", "help");
                startActivity(intent_web);

                break;
            case R.id.about_layout:

                intent_web.putExtra("webUrl", Const.webRootUrl + Const.KeyAbout);
                intent_web.putExtra("tag", "about");
                startActivity(intent_web);

                break;
            case R.id.tousu_layout:
                Intent intent_tousu = new Intent(MainActivity.this, TousuActivity.class);
                startActivity(intent_tousu);
                break;
        }
    }

    private void showDialog(String content, final String tag) {
        //弹窗提示
        alertDialog = new Dialog(context, R.style.progress_dialog);
        alertDialog.setContentView(R.layout.dialog_itemview);
        alertDialog.setCancelable(true);
        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                DialogUtils.backgroundAlpaha(MainActivity.this, 1.0f);

            }
        });
        final TextView yes_tv = (TextView) alertDialog.findViewById(R.id.cancleOrder_yes);
        final TextView no_tv = (TextView) alertDialog.findViewById(R.id.cancleOrder_no);
        TextView content_tv = (TextView) alertDialog.findViewById(R.id.dialog_contenttv);
        if (tag.equals("gg")){
            yes_tv.setText(LanguageUtil.getResText(R.string.tv_nowinstall));
            no_tv.setText(LanguageUtil.getResText(R.string.tv_yhinstall));
        }
        content_tv.setText(content);
        yes_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tag.equals("gg")){
                    //取消订单
                    if (alertDialog.isShowing()){
                        alertDialog.dismiss();
                    }
                    if (!trip_id.isEmpty()) {
                        cancelOrder(trip_id);
                    } else {
                        ToastUtils.Toast_short(context, LanguageUtil.getResText(R.string.toast_nojiedan));
                    }
                }else {
                    alertDialog.dismiss();
                    if (!dialog.isShowing()){
                        dialog.show();
                    }
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
//                            FileUtil.downloadFile(Const.googlesevice, FileUtil.DIR_APK,"google安装器.apk", context);
                            OkHttpUtils//
                                    .get()//
                                    .url(Const.googlesevice)//
                                    .build()//
                                    .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "googleazq.apk")//
                                    {
                                        @Override
                                        public void inProgress(float progress)
                                        {
                                            Log.e("tag","progress="+progress);
                                        }

                                        @Override
                                        public void onError(Request request, Exception e)
                                        {
                                            Log.e("tag", "onError :" + e.getMessage());
                                        }

                                        @Override
                                        public void onResponse(File file)
                                        {
                                            Log.e("tag", "onResponse :" + file.getAbsolutePath());
                                            Log.e("tag", "文件下载成功");
                                            Log.e("tag", "file="+file.length());
                                            Message msg = Message.obtain();
                                            msg.what = 007;
                                            mHandler.sendMessage(msg);
                                            if (file!=null){
                                              FileUtil.installApk (context, file);
                                            }
                                        }
                                    });
                        }
                    }).start();
                }
            }
        });
        no_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //不取消
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
        DialogUtils.backgroundAlpaha(MainActivity.this, 0.5f);
    }

    private void getSmallJourney(final String startStr, String endStr, String mode) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("https://maps.googleapis.com/maps/api/directions/json?origin=");
        buffer.append(startStr + "&destination=");
        buffer.append(endStr + "&sensor=false&mode=");
        buffer.append(mode);
        Log.e("url", "url=" + buffer.toString());
        RequestParams params = new RequestParams(buffer.toString());
//        RequestParams params = new RequestParams("https://maps.googleapis.com/maps/api/directions/json?origin=22.495562546852,113.9195796929&destination=22.535006648941,113.97465188433&sensor=false&mode=driving");
//        params.addBodyParameter("origin",startStr);
//        params.addBodyParameter("destination",endStr);
//        params.addBodyParameter("sensor","false");
//        params.addBodyParameter("mode",mode);
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
//                Log.e("tag","result="+result);
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getString("status").equals("OK")) {
                        Gson gson = new Gson();
                        PolyLinesBean polyLinesBean = gson.fromJson(result, PolyLinesBean.class);
                        double mileage = polyLinesBean.getRoutes().get(0).getLegs().get(0).getDistance().getValue();
                        String smallJourney = String.valueOf(mileage);
                        journey_list.add(smallJourney);
                        templatitude = latitude;
                        templongitude = longitude;

                        caculatePrice("0");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

                ToastUtils.Toast_short(context, LanguageUtil.getResText(R.string.toast_dataexception));
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
    }

    private void caculatePrice(String isEnd) {
        double journeyPrice = 0;
        double totalMoney = 0;
        int startPrice = 0;
        int moneybymeter = 0;
        int journey = 0;
        int meter = 0;
        //参数说明详见登录页面
        endTime = getCurrentTime();
        long totalTime = (endTime - startTime) / 60000;//单位：分钟
        int minPrice = (int) SharePreferenceUtils.getParam(context, Const.moneybyminute, -1);
        long timePrice = totalTime * minPrice;

        String driverType = (String) SharePreferenceUtils.getParam(context, Const.driverType, "-1");
        Log.e("tag", "driverType=" + driverType);
        if (driverType.equals(Const.driverType_taxi)) {
            startPrice = (int) SharePreferenceUtils.getParam(context, Const.startingfare, -1);
            moneybymeter = (int) SharePreferenceUtils.getParam(context, Const.moneybymeter, -1);
            journey = (int) SharePreferenceUtils.getParam(context, Const.journey, -1);
            meter = (int) SharePreferenceUtils.getParam(context, Const.meter, -1);
        } else {
            startPrice = (int) SharePreferenceUtils.getParam(context, Const.startingfare_moto, -1);
            moneybymeter = (int) SharePreferenceUtils.getParam(context, Const.moneybymeter_moto, -1);
            journey = (int) SharePreferenceUtils.getParam(context, Const.journey_moto, -1);
            meter = (int) SharePreferenceUtils.getParam(context, Const.meter_moto, -1);
        }


        double totaljJourney = 0;
        //已经走过的路程（实时）
        for (int i = 0; i < journey_list.size(); i++) {
            totaljJourney += Double.parseDouble(journey_list.get(i));
        }

        if (totaljJourney <= journey) {//路程小于等于起步价的里程
            journeyPrice = startPrice;
        } else {
            journeyPrice = startPrice + (totaljJourney - journey) * (moneybymeter / meter);
        }
        totalMoney = journeyPrice + timePrice;
        Log.e("money", "totalMoney=" + totalMoney);
        if (!dialog.isShowing()) {
            dialog.show();
        }
        sendOrderPrice(String.valueOf(totalMoney), isEnd, totaljJourney);
    }


    //上传订单价格
    private void sendOrderPrice(final String fdTripPrice, final String isEnd, final double totaljJourney) {

        RequestParams params = new RequestParams(Const.sendOrderPrice);
        params.addBodyParameter("trip_id", trip_id);
        params.addBodyParameter("fdTripPrice", fdTripPrice);
        params.addBodyParameter("Pointw", String.valueOf(latitude));
        params.addBodyParameter("Pointj", String.valueOf(longitude));
        params.addBodyParameter("fdTripMileage", String.valueOf(totaljJourney));
        params.addBodyParameter("isEnd", isEnd);//1：行程已结束，0：未结束
        LanguageUtil.addLanguage(params);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getString(Const.Code).equals(Const.reques_success)) {
                        Log.e("tag", "fdTripMileage=" + totaljJourney);
                        Log.e("tag", "fdTripPrice=" + fdTripPrice);
                        Log.e("tag", "isEnd=" + isEnd);
                        LogUtils.Loge("sendOrderPrice", "order_id=" + order_id);
                        Log.e("tag", "订单价格发送成功");

                        if (isEnd.equals("1")) {
                            Intent intent = new Intent(MainActivity.this, EndOrderActivity.class);
                            intent.putExtra("order_id", order_id);
                            intent.putExtra("tag", "MainActivity");

                            double totalJourney = 0;
                            for (int i = 0; i < journey_list.size(); i++) {
                                totalJourney += Double.parseDouble(journey_list.get(i));
                            }
                            intent.putExtra("totalJourney", String.valueOf(totalJourney));
                            startActivityForResult(intent, 123);
                            SharePreferenceUtils.setParam(context, Const.orderStauts, "1");
                        }
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
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
    }

    private void cancelOrder(final String trip_id) {
        RequestParams params = new RequestParams(Const.cancleOrder);
        params.addBodyParameter("trip_id", trip_id);
        LanguageUtil.addLanguage(params);
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getString(Const.Code).equals(Const.reques_success) && obj.getString("message").equals(LanguageUtil.getResText(R.string.request_success))) {
                        SharePreferenceUtils.setParam(context, Const.User_Stauts, Const.carStatus_out);
                        isCancel = true;
                        isStart = true;
                        Message msg = new Message();
                        msg.what = 73;
                        mHandler.sendMessage(msg);

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
                if (alertDialog.isShowing()) {
                    alertDialog.dismiss();
                }
            }
        });
    }


    private void addMylocationMarker() {
        addOnlyoneMarker(myLocation_key, new LatLng(latitude, longitude), R.mipmap.shouye_gensui, 1);

    }

    @Override
    public void onLocationChanged(Location location) {
//        updateToNewLocation(location);
//        ToastUtils.Toast_short(context, "位置变化了~~");
        if (location != null) {
            mLocation = location;
            LatlngBean transform = WGSTOGCJ02.transform(location.getLongitude(), location.getLatitude());
            if (isChina) {
                longitude = transform.getLon();//当前经度
                latitude = transform.getLat();//当前纬度
            } else {
                longitude = mLocation.getLongitude();//当前经度
                latitude = mLocation.getLatitude();//当前纬度
            }

            /*获取详细地址*/
            String address = getAddress(context, latitude, longitude);
                        /*做标记,展示简单信息,例如：中国-北京*/
            firstAddressString = address;
            firstShortAddressString = addressString;
            LatLng sydney = new LatLng(latitude, longitude);
//            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));
            addOnlyoneMarker(myLocation_key, sydney, R.mipmap.shouye_gensui, 1);
            if (!isLogin) {
                ToastUtils.Toast_short(context, LanguageUtil.getResText(R.string.toast_notlogin));
            } else {
                postMyLocation(latitude, longitude);
            }
        }
        if (isJifei) {
            String startStr = templatitude + "," + templongitude;
            String endStr = latitude + "," + longitude;
            String driverType = (String) SharePreferenceUtils.getParam(context, Const.driverType, "1");
            synchronized ("abc") {
                if (driverType.equals("1")) {
                    getSmallJourney(startStr, endStr, Const.Driving);
                } else {
                    getSmallJourney(startStr, endStr, Const.Moto);
                }
            }
        }

//        Log.e("tag", "isReceive=" + isReceive);
        if (isReceive) {
            String myLocation = latitude + "," + longitude;
            String startStr = takerlatitude + "," + takerlongitude;
            if (takerlatitude != 0 && takerlongitude != 0 && latitude != 0) {
                getLuxianJson(myLocation, startStr, "ms");//司机起点到乘客起点
            } else {
                ToastUtils.Toast_short(context, LanguageUtil.getResText(R.string.toast_dataexception));
            }
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {
    }

    @Override
    public void onProviderDisabled(String s) {
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    private long exitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            LogUtils.Loge("tag", "orderStatus=" + orderStatus);
            switch (orderStatus) {//订单状态
               /* case "-1"://默认状态
                    main_backimg.setVisibility(View.GONE);
                    main_head_icon.setVisibility(View.VISIBLE);
                    titlebar_centertv.setText("Global Take");
                    message_relay.setVisibility(View.VISIBLE);
                    titlebar_shouche.setVisibility(View.GONE);
                    order_lay.removeAllViews();
                    ordering_bottomlay.setVisibility(View.GONE);
                    chuche_lay.setVisibility(View.VISIBLE);
                    mid_lay.setVisibility(View.VISIBLE);
                    shouche_lay.setVisibility(View.VISIBLE);
                    mGoogleMap.clear();
                    addMylocationMarker();
                    break;*/

                case "0"://删除订单（取消）

                    break;

                case "1"://已完成

                    break;

                case "2"://正在拉客
                    //弹窗提示
                    showDialog(LanguageUtil.getResText(R.string.dialog_tip),"cancelOrder");
                    break;
                case "-1":
                    if ((System.currentTimeMillis() - exitTime) > 2000) {
                        Toast.makeText(MainActivity.this, LanguageUtil.getResText(R.string.toast_exitapp), Toast.LENGTH_SHORT).show();
                        exitTime = System.currentTimeMillis();
                    } else {
                        finish();
                        AppManager.getAppManager().AppExit(this);
                    }
                    break;
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    /**
     * <通过经纬度，获取当前位置。>
     *
     * @param context
     * @param latitude
     * @param longitude
     * @return
     */
    public String getAddress(Context context, double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            String addressStr = "";
          /*  if (!StringUtils.isEmpty(addresses.get(0).getCountryName())) {//"国家："
                addressStr = addressStr + addresses.get(0).getCountryName();
                addressString = addresses.get(0).getCountryName();
            }
            if (!StringUtils.isEmpty(addresses.get(0).getAdminArea())) {//"省：" +
                addressStr = addressStr + addresses.get(0).getAdminArea();
                addressString = addressString + "-" + addresses.get(0).getAdminArea();
            }
            if (!StringUtils.isEmpty(addresses.get(0).getLocality())) {//"城市：" +
                addressStr = addressStr + addresses.get(0).getLocality();
            }*/
            if (!StringUtils.isEmpty(addresses.get(0).getAddressLine(1))) {//"区：" +
                addressStr = addressStr + addresses.get(0).getAddressLine(1);
            }
            /*if (!StringUtils.isEmpty(addresses.get(0).getAddressLine(2))) {//"街道：" +
                addressStr = addressStr + addresses.get(0).getAddressLine(2);
            }*/

            return addressStr;
        } catch (Exception e) {
            e.printStackTrace();
//            return getResources().getString(R.string.house_add_step2_location_failed);
            return "";
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 13) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    if (isOPen(this)) {
                        doWork();
                    } else {
                        // 转到手机设置界面，用户设置GPS
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(intent, 0); // 设置完成后返回到原来的界面
                    }
                }
            } else {
                doWork();
            }
        }
//        ProgressBarUtils.dismiss();
    }

    public boolean isOPen(final Context context) {
        LocationManager locationManager
                = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }

        return false;
    }

    private void addOnlyoneMarker(String markerTag, LatLng sydney, int mipmapId, float tbnum) {
        switch (markerTag) {
            case "startPlace":
                if (startPlace != null) {
                    startPlace.remove();
                    startPlace = addMarker(markerTag, sydney, mipmapId, tbnum);
                } else {
                    startPlace = addMarker(markerTag, sydney, mipmapId, tbnum);
                }
                break;
            case "endPlace":
                if (endPlace != null) {
                    endPlace.remove();
                    endPlace = addMarker(markerTag, sydney, mipmapId, tbnum);
                } else {
                    endPlace = addMarker(markerTag, sydney, mipmapId, tbnum);
                }
                break;
            case "myLocation":
                if (myLocation != null) {
                    myLocation.remove();
                    myLocation = addMarker(markerTag, sydney, mipmapId, tbnum);
                } else {
                    myLocation = addMarker(markerTag, sydney, mipmapId, tbnum);
                }
                break;
        }
    }

    private final Map<String, MarkerOptions> mMarkers = new ConcurrentHashMap<String, MarkerOptions>();

    private Marker addMarker(String name, LatLng ll, int mipmapId, float tbnum) {
        final MarkerOptions marker = new MarkerOptions().position(ll).title(name)
                .zIndex(tbnum)
                .icon(BitmapDescriptorFactory.fromResource(mipmapId));//覆盖物的图标
        mMarkers.put(name, marker);
        Marker marker1 = mGoogleMap.addMarker(marker);
        return marker1;
    }


    //输入终点后的操作
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(JpushBean jpushBean) {
        if (jpushBean.getEventStr().equals("收到订单")) {
//            ToastUtils.Toast_short(context, "收到订单！");
            titlebar_shouche.setVisibility(View.VISIBLE);
            titlebar_shouche.setText(LanguageUtil.getResText(R.string.tv_incar));
            message_relay.setVisibility(View.GONE);
            chuche_lay.setVisibility(View.GONE);
            mid_lay.setVisibility(View.GONE);
            shouche_lay.setVisibility(View.GONE);
            ordernum_lay.setVisibility(View.VISIBLE);
            SystemClock.sleep(3000);//防止行程信息还未产生
            playMp3();
            getTripInfo(jpushBean.getTrip_id());
            Log.e("tag","wtf?");
        }
    }
    //输入终点后的操作
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(RegisterBean jpushBean) {
        if (jpushBean.getEventStr().equals("checkRegisterId")) {
            String registerId = jpushBean.getRegisterId();
            if (null==registerId||registerId.isEmpty()){
                ToastUtils.Toast_short1("网络异常，检查网络后重启试试");
            }else {
                ToastUtils.Toast_short1("可以正常抢单了");
            }
        }
    }

    //系统消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEventxt(MessageBean jpushBean) {
        if (jpushBean.getEventStr().equals("系统消息")) {
            String message_id = jpushBean.getMessage_id();
            getXtmsg(message_id);
        }
    }

    //用户取消形成后的操作后的操作
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent1(JpushCancleOrderBean jpushBean) {
        if (jpushBean.getEventStr().equals("取消行程")) {
            ToastUtils.Toast_short(context, LanguageUtil.getResText(R.string.toast_canceledtrip));
            chushihuaView();
        }
    }

    private void chushihuaView() {
        mGoogleMap.clear();
        journey_list.clear();
        isJifei = false;
        order_lay.removeAllViews();
        titlebar_shouche.setVisibility(View.GONE);
        message_relay.setVisibility(View.VISIBLE);
        chuche_lay.setVisibility(View.VISIBLE);
        mid_lay.setVisibility(View.VISIBLE);
        shouche_lay.setVisibility(View.VISIBLE);
        ordernum_lay.setVisibility(View.GONE);
        main_head_icon.setVisibility(View.VISIBLE);
        main_backimg.setVisibility(View.GONE);
        titlebar_centertv.setText("Global Take");
        ordering_bottomlay.setVisibility(View.GONE);
        order_bottom_tv.setVisibility(View.GONE);
        timenum_tv.setVisibility(View.GONE);
        waittime_lay.setVisibility(View.GONE);
        if (!isLogin) {
            ToastUtils.Toast_short(context, LanguageUtil.getResText(R.string.toast_notlogin));
        } else {
            addMylocationMarker();
            chuche();
        }
    }

    private void getTripInfo(String trip_id) {
        RequestParams params = new RequestParams(Const.getTripInfo);
        params.addBodyParameter("trip_id",trip_id);
        LanguageUtil.addLanguage(params);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getString(Const.Code).equals(Const.reques_success)&&obj.getString("message").equals("执行成功!")) {
                        Gson gson = new Gson();
                        final TripDetailBean orderBean = gson.fromJson(result, TripDetailBean.class);
                        if (orderBean != null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    final order_itemView orderItemView = new order_itemView(context);
                                    final TripDetailBean.ResultBean resultBean = orderBean.getResult();
                                    orderItemView.setData(resultBean);
                                    order_lay.setVisibility(View.VISIBLE);
                                    order_lay.addView(orderItemView);
                                    orderItemViewMap.put(resultBean.getFdId(), orderItemView);
                                    int childCount = order_lay.getChildCount();
                                    ordernum_tv.setText(childCount + LanguageUtil.getResText(R.string.tv_sumorder));
                                    orderItemView.setOnQiangLayListener(new order_itemView.IreborOrder() {
                                        @Override
                                        public void onClick(String trip_id) {
                                            LogUtils.Loge("trip_id", "trip_id=" + trip_id);
                                            if (!isLogin) {
                                                ToastUtils.Toast_short(context, LanguageUtil.getResText(R.string.toast_notlogin));
                                            } else {
                                                qiangDan(trip_id, resultBean);
                                            }
                                        }
                                    });
                                    orderItemView.setOnRemoListener(new order_itemView.ITimeCountDown() {
                                        @Override
                                        public void timeEnd(String trip_id) {
                                            order_lay.removeView(orderItemView);
                                            int childCount = order_lay.getChildCount();
                                            ordernum_tv.setText(childCount + LanguageUtil.getResText(R.string.tv_sumorder));
                                        }
                                    });
                                }
                            });
                        }
                    } else {
                        ToastUtils.Toast_short(context, LanguageUtil.getResText(R.string.toast_dataexception));
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

            }
        });
    }

    //司机抢单
    private void qiangDan(final String tripid, final TripDetailBean.ResultBean resultBean) {
        String userId = (String) SharePreferenceUtils.getParam(context,Const.UserId,"");
        RequestParams params = new RequestParams(Const.qiangOrder);
        params.addBodyParameter(Const.UserId, userId);
        params.addBodyParameter("trip_id", tripid);
        LanguageUtil.addLanguage(params);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtils.Logd("qiangdan", "result=" + result);
                Gson gson = new Gson();
                QiangDanBean qiangDanBean = gson.fromJson(result, QiangDanBean.class);
                if (qiangDanBean != null) {
                    if (qiangDanBean.getCode().equals(Const.reques_success)) {
//                        ToastUtils.Toast_short(context, LanguageUtil.getResText(R.string.toast_grabordersucess));
                        SharePreferenceUtils.setParam(context, Const.User_Stauts, Const.carStatus_in);//缓存收车状态
                        //隐藏所有订单
                        order_lay.removeAllViews();
                        orderItemViewMap.clear();
                        TakerPhoneItemView takerPhoneItemView = new TakerPhoneItemView(context);
                        takerPhoneItemView.setData(resultBean);
                        order_lay.addView(takerPhoneItemView);
                        //实时导航（乘客的起点坐标）
                        takerlatitude = resultBean.getFdTripStartCoordinates();
                        takerlongitude = resultBean.getFdTripStartCoordinateslongitude();

                        titlebar_shouche.setText(LanguageUtil.getResText(R.string.tv_cancelorder));
                        titlebar_centertv.setText(LanguageUtil.getResText(R.string.tv_gotaker));

                        ordernum_lay.setVisibility(View.GONE);
                        ordering_bottomlay.setVisibility(View.VISIBLE);
                        main_backimg.setVisibility(View.VISIBLE);
                        main_head_icon.setVisibility(View.GONE);
                        int fdOrderStatus = qiangDanBean.getResult().getFdOrderStatus();
                        if (fdOrderStatus + "" != null) {
                            orderStatus = fdOrderStatus + "";
                            Log.e("tag", "orderStatus=" + orderStatus);
                            SharePreferenceUtils.setParam(context, Const.orderStauts, String.valueOf(orderStatus));//本地保存下订单状态
                        }
                        if (qiangDanBean.getResult().getFdId() != null) {

                            trip_id = resultBean.getFdId();//行程Id
                            order_id = qiangDanBean.getResult().getFdId();
                            Log.e("trip_id", "trip_id=" + trip_id);
                            Log.e("order_id", "order_id=" + order_id);
                        }
                        if (qiangDanBean.getResult().getFdOrderNum() != null) {

                            oderNum = qiangDanBean.getResult().getFdOrderNum();//行程Id
                        }
                        //路径规划

                        //终点的经纬度
                        double fdTripEndCoordinateslongitude = resultBean.getFdTripEndCoordinateslongitude();
                        double fdTripEndCoordinates = resultBean.getFdTripEndCoordinates();
                        String myLocation = latitude + "," + longitude;
                        String startStr = takerlatitude + "," + takerlongitude;
                        String endStr = fdTripEndCoordinates + "," + fdTripEndCoordinateslongitude;

                        addOnlyoneMarker(startPlace_key, new LatLng(takerlatitude, takerlongitude), R.mipmap.xiandan_bulepositioning, 2);
                        addOnlyoneMarker(endPlace_key, new LatLng(fdTripEndCoordinates, fdTripEndCoordinateslongitude), R.mipmap.xiandan_redpositioning, 3);

                        //画路线图
                        if (!dialog.isShowing()) {
                            dialog.show();
                        }
                        isReceive = true;
                        getLuxianJson(startStr, endStr, "se");//乘客起点到终点

                        getLuxianJson(myLocation, startStr, "ms");//司机起点到乘客起点

                    } else {
                        ToastUtils.Toast_short(context, qiangDanBean.getMessage());
                        if (orderItemViewMap != null) {
                            for (Map.Entry<String, order_itemView> entry : orderItemViewMap.entrySet()) {
                                if (entry.getKey().equals(tripid)) {
                                    order_lay.removeView(entry.getValue());
                                    ordernum_tv.setText(order_lay.getChildCount() + LanguageUtil.getResText(R.string.tv_sumorder));
                                }
                            }
                        }
                    }
                } else {
                    ToastUtils.Toast_short(context, LanguageUtil.getResText(R.string.toast_parsingexception));
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

            }
        });
    }

    //司机出车
    private void chuche() {
        userId = (String) SharePreferenceUtils.getParam(context, Const.UserId, "");
        RequestParams params = new RequestParams(Const.Chuche);
        params.addBodyParameter(Const.UserId, userId);
        params.addBodyParameter("Pointw", String.valueOf(latitude));
        params.addBodyParameter("Pointj", String.valueOf(longitude));
        LanguageUtil.addLanguage(params);
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getString(Const.Code).equals(Const.reques_success)) {
                        Message message = Message.obtain();
                        message.what = 78;
                        mHandler.sendMessage(message);
                        SharePreferenceUtils.setParam(context, Const.User_Stauts, Const.carStatus_out);
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

            }
        });
    }

    private void chucheRefreshUi() {
        left_iv.setImageResource(R.mipmap.shouye_chuche_sel);
        mid_iv.setImageResource(R.mipmap.shouye_jiedanzhong);
        right_iv.setImageResource(R.mipmap.shouye_shouche);
        mid_tv.setText(LanguageUtil.getResText(R.string.tv_ordering));
        left_iv.setClickable(false);
        right_iv.setClickable(true);
        chuche_lay.setClickable(false);
        shouche_lay.setClickable(true);
//        ToastUtils.Toast_short(context, "出车了~~");
    }

    //司机收车
    private void shouche(final String tag) {
        userId = (String) SharePreferenceUtils.getParam(context, Const.UserId, "");
        RequestParams params = new RequestParams(Const.Shouche);
        params.addBodyParameter(Const.UserId, userId);
        LanguageUtil.addLanguage(params);
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getString(Const.Code).equals(Const.reques_success)) {
                        if (tag.equals("titlebar_shouche")) {//点击右上角的收车
//                            chushihuaView();
                            Message message = Message.obtain();
                            message.what = 76;
                            mHandler.sendMessage(message);
                        }
                        Message message = Message.obtain();
                        message.what = 77;
                        mHandler.sendMessage(message);
                        SharePreferenceUtils.setParam(context, Const.User_Stauts, Const.carStatus_in);
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

            }
        });
    }

    // TODO: 2017/2/17 该接口用xutils很不稳定，建议后期得换个框架
    private void getLuxianJson(final String startStr, String endStr, final String tag) {
        String driverType = (String) SharePreferenceUtils.getParam(context, Const.driverType, "-1");
        StringBuffer buffer = new StringBuffer();
        buffer.append("https://maps.googleapis.com/maps/api/directions/json?origin=");
        buffer.append(startStr + "&destination=");
        buffer.append(endStr + "&sensor=false&mode=");
        if (driverType.equals(Const.driverType_taxi)) {
            buffer.append(Const.Driving);
        } else {
            buffer.append(Const.Moto);
        }
        if (LanguageUtil.isZh()) {
            buffer.append("&" + Const.Language + "=" + Const.zh);
        } else {
            buffer.append("&" + Const.Language + "=" + Const.en);
        }
        Log.e("url", "url=" + buffer.toString());
        RequestParams params = new RequestParams(buffer.toString());
//        RequestParams params = new RequestParams("https://maps.googleapis.com/maps/api/directions/json?origin=22.495562546852,113.9195796929&destination=22.535006648941,113.97465188433&sensor=false&mode=driving");
//        params.addBodyParameter("origin",startStr);
//        params.addBodyParameter("destination",endStr);
//        params.addBodyParameter("sensor","false");
//        params.addBodyParameter("mode",mode);
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getString("status").equals("OK")) {
                        Gson gson = new Gson();
                        PolyLinesBean polyLinesBean = gson.fromJson(result, PolyLinesBean.class);
                        if (polyLinesBean != null) {
                            List<PolyLinesBean.RoutesBean.LegsBean.StepsBean> steps = polyLinesBean.getRoutes().get(0).getLegs().get(0).getSteps();
                            double mileage = polyLinesBean.getRoutes().get(0).getLegs().get(0).getDistance().getValue();
                            String mileageText = polyLinesBean.getRoutes().get(0).getLegs().get(0).getDistance().getText();
                            String timesText = polyLinesBean.getRoutes().get(0).getLegs().get(0).getDuration().getText();

                            if (tag.equals("se")) {
                                PolylineOptions lineOptions = new PolylineOptions();
                                lineOptions.color(getResources().getColor(R.color.statecolor));
                                if (steps != null) {
                                    for (int i = 0; i < steps.size(); i++) {
//                                LatLng latLng_mapstart = new LatLng(steps.get(i).getStart_location().getLat(),steps.get(i).getStart_location().getLng());
//                                LatLng latLng_end = new LatLng(steps.get(i).getEnd_location().getLat(),steps.get(i).getEnd_location().getLng());
//                                lineOptions.add(latLng_end);
                                        List<LatLng> latLngs = decodePoly(steps.get(i).getPolyline().getPoints());
                                        lineOptions.addAll(latLngs);
                                    }
                                }

                                double southWest_latitude = polyLinesBean.getRoutes().get(0).getBounds().getSouthwest().getLat();
                                double southWest_longitude = polyLinesBean.getRoutes().get(0).getBounds().getSouthwest().getLng();
                                double northEast_latitude = polyLinesBean.getRoutes().get(0).getBounds().getNortheast().getLat();
                                double northEast_longitude = polyLinesBean.getRoutes().get(0).getBounds().getNortheast().getLng();
                                LatLng southWest = new LatLng(southWest_latitude, southWest_longitude);
                                LatLng northEast = new LatLng(northEast_latitude, northEast_longitude);
                                bounds = new LatLngBounds(southWest, northEast);

//                                mGoogleMap.clear();//移除之前已经画好的路线图
//                                addOnlyoneMarker(myLocation_key, new LatLng(latitude, longitude), R.mipmap.shouye_gensui, 1);
//                                addOnlyoneMarker(startPlace_key, new LatLng(start_latitude, start_longitude), R.mipmap.xiandan_bulepositioning, 2);
//                                addOnlyoneMarker(endPlace_key, new LatLng(end_latitude, end_longitude), R.mipmap.xiandan_redpositioning, 2);
                                mGoogleMap.addPolyline(lineOptions);
                                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
                                TotalMileage = mileage + "";
                            } else {//设置底部view数据
                                ordering_distance.setText(LanguageUtil.getResText(R.string.tv_distanceu) + mileageText);
                                if (steps.size() >= 1) {
                                    String html_instructions = steps.get(0).getHtml_instructions();
                                    String smallMileage = steps.get(0).getDistance().getText();
                                    Log.e("tag", "html_instructions=" + html_instructions);
                                    if (html_instructions.contains("<b>")) {
                                        html_instructions = html_instructions.replaceAll("<b>", "");
                                    }
                                    if (html_instructions.contains("</b>")) {
                                        html_instructions = html_instructions.replaceAll("</b>", "");
                                    }
                                    if (LanguageUtil.isZh()) {
                                        daohang_tiptv.setText(smallMileage + "后" + html_instructions);
                                    } else {
                                        daohang_tiptv.setText("After " + smallMileage + html_instructions);
                                    }
                                }
                                ordering_time.setText(timesText);

                            }
                        } else {
                            ToastUtils.Toast_short(context, LanguageUtil.getResText(R.string.toast_parsingexception));
                        }
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

                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
    }

    /**
     * 返回的json数据，google的points数据使用了其它格式的编码，需要我们解析出来。
     *
     * @param encoded
     * @return List<LatLng>
     */
    private List<LatLng> decodePoly(String encoded) {
        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;
        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;
            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;
            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }
        return poly;
    }

    //开始计费
    private void startBilling() {
        RequestParams params = new RequestParams(Const.startBilling);
        params.addBodyParameter("order_id", order_id);
        LanguageUtil.addLanguage(params);
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {

                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getString(Const.Code).equals(Const.reques_success)) {
                        Message message = Message.obtain();
                        message.what = 79;
                        mHandler.sendMessage(message);
                        //默认发送一次价格到服务器
                        caculatePrice("0");
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

            }
        });
    }

    private Location getMyLocation() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 0, MainActivity.this);
        Location mLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);//获取定位对象
        if (mLocation == null) {
            //在室内使用net定位
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 0, MainActivity.this);
            mLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);//获取定位对象
        }
        return mLocation;
    }

    private String tempStr = "";
    private Runnable timeRun = new Runnable() {
        @Override
        public void run() {

            timenum_tv.setText(tempStr);
        }
    };
    private void refreshTime(){

        runOnUiThread(timeRun);
    }
    private void refreshUi() {
        timenum_tv.setVisibility(View.VISIBLE);
        if (minute == 0) {
            if (second == 0) {
                mHandler.removeMessages(73);
//                mHandler=null; 有内存泄露的风险
                mHandler.removeCallbacks(cfRunnable);
                mHandler.removeCallbacks(timeRun);
                isCancel = false;
                chushihuaView();

            } else {
                second--;
                if (second >= 10) {
                    tempStr = "0" + minute + ":" + second;
                    refreshTime();
                } else {
                    tempStr = "0" + minute + ":0" + second;
                    refreshTime();
                }
            }
        } else {
            if (second == 0) {
                second = 59;
                minute--;
                if (minute >= 10) {
                    tempStr = minute + ":" + second;
                    refreshTime();
                } else {
                    tempStr = "0" + minute + ":" + second;
                    refreshTime();
                }
            } else {
                second--;
                if (second >= 10) {
                    if (minute >= 10) {
                        tempStr = minute + ":" + second;
                        refreshTime();
                    } else {
                        tempStr = "0" + minute + ":" + second;
                        refreshTime();
                    }
                } else {
                    if (minute >= 10) {
                        tempStr = minute + ":0" + second;
                        refreshTime();
                    } else {
                        tempStr = "0" + minute + ":0" + second;
                        refreshTime();
                    }
                }
            }
        }
    }

    //获取时间戳
    public Long getCurrentTime() {
        long time = System.currentTimeMillis() / 1000;//获取系统时间的10位的时间戳
        return time;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123 && resultCode == 123) {//结束订单页的返回
            orderStatus = "-1";
            SharePreferenceUtils.setParam(context, Const.orderStauts, "-1");
            chushihuaView();
        }
        //登陆成功后的操作
        if (requestCode == 222 && resultCode == 222) {
            isLogin = (boolean) SharePreferenceUtils.getParam(context, Const.isLogin, false);
            LoginSet();
        }
        //退出登录后的操作
        if (requestCode == 333 && resultCode == 333) {
            isLogin = (boolean) SharePreferenceUtils.getParam(context, Const.isLogin, false);
            LoginSet();
        }
    }


    private Runnable cfRunnable = new Runnable() {
        @Override
        public void run() {
            if (mHandler != null && isCancel) {
                if (timenum_tv != null) {
                    refreshUi();
                }
                mHandler.postDelayed(this, 1000);
            }
        }
    };
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 007:
                    if (dialog.isShowing()){
                        dialog.dismiss();
                    }
                    break;
                case 73://进入惩罚状态
                    order_lay.removeAllViews();
                    waittime_lay.setVisibility(View.VISIBLE);
                    ordernum_lay.setVisibility(View.GONE);
                    mid_lay.setVisibility(View.GONE);
                    chuche_lay.setVisibility(View.GONE);
                    shouche_lay.setVisibility(View.GONE);
                    mGoogleMap.clear();
                    addMylocationMarker();
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15));
                    ordering_bottomlay.setVisibility(View.GONE);
                    titlebar_shouche.setVisibility(View.GONE);
                    main_backimg.setVisibility(View.GONE);
                    main_head_icon.setVisibility(View.VISIBLE);
                    message_relay.setVisibility(View.VISIBLE);
                    order_bottom_tv.setVisibility(View.GONE);
                    titlebar_centertv.setText("Global Take");
                    orderStatus = "-1";
                    SharePreferenceUtils.setParam(context, Const.orderStauts, "-1");
                    if (mHandler != null&&isStart) {
                        minute = 0;
                        second = 10;
                        mHandler.post(cfRunnable);
                        isStart = false;
                    }

                    break;

                case 74://开始计费，开启计时器(计算实时候车费，同理可以计算实时里程费，但这样担心内存消耗的问题)
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (mHandler != null && isEndTrip) {
                                mHandler.postDelayed(this, 2000);
                                Location lastLocation = getMyLocation();
                                LogUtils.Loge(logTag, "lastLocation=" + lastLocation);
                                if (lastLocation != null) {
                                    //判断是否处于停留状态

                                }
                            } else {
                                //释放handler 防止造成内存泄露
                            }
                        }
                    });

                    break;

                case 75://计算参考价格


                    break;
                case 76://右上角收车
                    message_relay.setVisibility(View.VISIBLE);
                    titlebar_shouche.setVisibility(View.GONE);
                    ordernum_lay.setVisibility(View.GONE);
                    shouche_lay.setVisibility(View.VISIBLE);
                    mid_lay.setVisibility(View.VISIBLE);
                    chuche_lay.setVisibility(View.VISIBLE);
                    shoucheRefreshUi();
                    break;
                case 77://右下角收车
                    shoucheRefreshUi();
                    break;
                case 78://出车
                    chucheRefreshUi();
                    break;
                case 79://开始计费
                    ToastUtils.Toast_short(context, LanguageUtil.getResText(R.string.toast_startedjifei));
                    order_bottom_tv.setText(LanguageUtil.getResText(R.string.tv_endorder));
                    titlebar_centertv.setText(LanguageUtil.getResText(R.string.title_destination));
                    main_backimg.setVisibility(View.GONE);
                    //获取起始位置（也就是点击开始计费时的位置）
                               /* Location startLocation = getMyLocation();
                                LogUtils.Loge(logTag,"myLocation="+startLocation);
                                if (startLocation!=null){
                                    currentLocation = startLocation;
                                    Message message = Message.obtain();
                                    message.what = 74;
                                    mHandler.sendMessage(message);
                                }*/

                    //记录时间点
                    startTime = getCurrentTime();
                    isJifei = true;
                    break;
            }
        }
    };

    /**
     * 创建本地MP3
     *
     * @return
     */
    public MediaPlayer createLocalMp3() {
        /**
         * 创建音频文件的方法：
         * 1、播放资源目录的文件：MediaPlayer.create(MainActivity.this,R.raw.beatit);//播放res/raw 资源目录下的MP3文件
         * 2:播放sdcard卡的文件：mediaPlayer=new MediaPlayer();
         *   mediaPlayer.setDataSource("/sdcard/beatit.mp3");//前提是sdcard卡要先导入音频文件
         */
        MediaPlayer mp = MediaPlayer.create(this, R.raw.neworder);
        return mp;
    }

    public void playMp3() {
        mediaPlayer = createLocalMp3();
        //当播放完音频资源时，会触发onCompletion事件，可以在该事件中释放音频资源，
        //以便其他应用程序可以使用该资源:
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();//释放音频资源
            }
        });
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                //开始播放音频
                mediaPlayer.start();
                Log.e("tag", "ccccc");
            }
        });
       /* try {
            //在播放音频资源之前，必须调用Prepare方法完成些准备工作
            mediaPlayer.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    private void getCountryName(String latlngStr) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("http://maps.google.com/maps/api/geocode/json?sensor=false&language=en&latlng=");
        buffer.append(latlngStr);
        RequestParams params = new RequestParams(buffer.toString());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                CountryBean countryBean = gson.fromJson(result, CountryBean.class);
                if (countryBean.getStatus().equals("OK")) {
                    if (countryBean != null) {
                        String formatted_address = countryBean.getResults().get(0).getFormatted_address();
                        if (formatted_address.contains("China")) {
                            isChina = true;
                        } else {
                            isChina = false;
                        }
                        Log.e("tag", "isChina=" + isChina);
                        //初始化参数
                        LatlngBean transform = WGSTOGCJ02.transform(mLocation.getLongitude(), mLocation.getLatitude());
                        if (isChina) {
                            latitude = transform.getLat();
                            longitude = transform.getLon();
                        } else {
                            latitude = mLocation.getLatitude();
                            longitude = mLocation.getLongitude();
                        }
                        postMyLocation(latitude, longitude);
                        templatitude = latitude;
                        templongitude = longitude;
            /*获取详细地址*/
                        String address = getAddress(context, latitude, longitude);
                        firstAddressString = address;
                        firstShortAddressString = addressString;
                        LatLng sydney = new LatLng(latitude, longitude);
                        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));
//            addOnlyoneMarker(myLocation_key,sydney,R.mipmap.shouye_gensui,1);
                        addOnlyoneMarker(startPlace_key, sydney, R.mipmap.xiandan_bulepositioning, 2);
                    }
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

    private void getXtmsg(final String msgId) {
        RequestParams params = new RequestParams(Const.xtMessage);
        params.addBodyParameter("message_id", msgId);
        LanguageUtil.addLanguage(params);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                MsgitemBean msgitemBean = gson.fromJson(result, MsgitemBean.class);
                if (msgitemBean.getCode().equals(Const.reques_success)) ;
                {
                    final MsgitemBean.ResultBean resultBean = msgitemBean.getResult();
                    if (resultBean != null) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                final XTmsgItemView itemView = new XTmsgItemView(context);
                                itemView.setData(resultBean);
                                order_lay.setVisibility(View.VISIBLE);
                                order_lay.addView(itemView);
                                itemView.removeItem(new XTmsgItemView.DeleteItemListener() {
                                    @Override
                                    public void onClick(View view, String msgId) {
                                        order_lay.removeView(itemView);
                                        if (order_lay.getChildCount()!=0){
                                            msgcount.setVisibility(View.VISIBLE);
                                            msgcount.setText(order_lay.getChildCount()+"");
                                        }else {
                                            msgcount.setVisibility(View.GONE);
                                        }
                                    }
                                });
                                if (order_lay.getChildCount()!=0){
                                    msgcount.setVisibility(View.VISIBLE);
                                    msgcount.setText(order_lay.getChildCount()+"");
                                }else {
                                    msgcount.setVisibility(View.GONE);
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

            }

            @Override
            public void onFinished() {

            }
        });
    }
}

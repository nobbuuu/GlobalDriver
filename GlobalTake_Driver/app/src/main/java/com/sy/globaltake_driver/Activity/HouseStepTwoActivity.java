package com.sy.globaltake_driver.Activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sy.globaltake_driver.R;
import com.sy.globaltake_driver.utils.LogUtils;
import com.sy.globaltake_driver.utils.StringUtils;

import java.util.List;
import java.util.Locale;

/**
 * 发布房源第一步 - 房源类型 房源出租方式
 * Created by niushibin on 2016/12/13 0013.
 */
public class HouseStepTwoActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private int REQUEST_CODE = 10;
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;

    private TextView tv_address_detail;
//    private HouseInfo houseInfo;

    private String addressString = "";
    private String firstAddressString = "";
    private String firstShortAddressString = "";
    private double latitude = 0;
    private double longitude = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_housesteptwo);
        initView();
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

   

    private void initView() {
//        ProgressBarUtils.show(this);
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
        mapFragment.getMapAsync(this);
//        tv_address_detail = (TextView) findViewById(R.id.tv_address_detail);
//        RelativeLayout rl_address = (RelativeLayout) findViewById(R.id.rl_address);
//        rl_address.setOnClickListener(this);

//        ProgressBarUtils.show(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    if(isOPen(this)){
                        requestLocation();
                    }else{
                        // 转到手机设置界面，用户设置GPS
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(intent, 0); // 设置完成后返回到原来的界面
                    }
                }
            } else {
                requestLocation();
            }
        }
//        ProgressBarUtils.dismiss();
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {//定位变化时在此设置
            if (location != null) {
                mLocation = location;
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
    };

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
            if (!StringUtils.isEmpty(addresses.get(0).getCountryName())) {//"国家："
                addressStr = addressStr + addresses.get(0).getCountryName();
                addressString = addresses.get(0).getCountryName();
            }
            if (!StringUtils.isEmpty(addresses.get(0).getAdminArea())) {//"省：" +
                addressStr = addressStr + addresses.get(0).getAdminArea();
                addressString = addressString + "-" + addresses.get(0).getAdminArea();
            }
            if (!StringUtils.isEmpty(addresses.get(0).getLocality())) {//"城市：" +
                addressStr = addressStr + addresses.get(0).getLocality();
            }
            if (!StringUtils.isEmpty(addresses.get(0).getAddressLine(1))) {//"区：" +
                addressStr = addressStr + addresses.get(0).getAddressLine(1);
            }
            if (!StringUtils.isEmpty(addresses.get(0).getAddressLine(2))) {//"街道：" +
                addressStr = addressStr + addresses.get(0).getAddressLine(2);
            }

            return addressStr;
        } catch (Exception e) {
            e.printStackTrace();
//            return getResources().getString(R.string.house_add_step2_location_failed);
            return "";
        }
    }

    /**
     * 连接成功
     *
     * @param bundle
     */
    @Override
    public void onConnected(@Nullable Bundle bundle) {
//        LogUtils.d("connected  success");
//        ProgressBarUtils.dismiss();
    }

    /**
     * 暂停
     *
     * @param i
     */
    @Override
    public void onConnectionSuspended(int i) {
//        LogUtils.Loge("connected  Suspended");
    }

    /**
     * 连接失败
     *
     * @param connectionResult
     */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//        LogUtils.d("connected  Failed");
//        ProgressBarUtils.dismiss();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        /**设置地图类型**/
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
//                        ToastUtils.showLong(HouseStepTwoActivity.this,getString(R.string.house_add_step2_locationing));
                        double lat = latLng.latitude;
                        double lon = latLng.longitude;

                        String address = getAddress(HouseStepTwoActivity.this, lat, lon);

                        LatLng ll = new LatLng(lat, lon);
                        mMap.clear();//先清除，已经有的锚点
                        mMap.addMarker(new MarkerOptions().position(ll).title(addressString));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ll, 15));

                        tv_address_detail.setText(address);
                       

                    }
                });
            }
        });
        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                LatLng ll = new LatLng(latitude, longitude);
                mMap.clear();//先清除，已经有的锚点
                mMap.addMarker(new MarkerOptions().position(ll).title(firstShortAddressString));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ll, 15));

//                tv_address_detail.setText(firstAddressString);
               
                return false;
            }
        });

        UiSettings uiSettings = mMap.getUiSettings();
        //右下角的状态栏
        uiSettings.setMapToolbarEnabled(false);
        //缩放比例控件
        uiSettings.setZoomControlsEnabled(true);
        //指南针(无法强制始终显示指南针)
        uiSettings.setCompassEnabled(true);
        //支持所有手势
        uiSettings.setAllGesturesEnabled(true);
        //My Location 按钮(调用了mMap.setMyLocationEnabled(true)，才会显示)
        uiSettings.setMyLocationButtonEnabled(true);
        //设置权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE);
        } else {
            if(isOPen(this)){
                requestLocation();
            }else{
                // 转到手机设置界面，用户设置GPS
                Intent intent = new Intent(
                        Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent, 0); // 设置完成后返回到原来的界面
            }
        }
    }

    private void requestLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
             /*设置定位*/
            mMap.setMyLocationEnabled(true);
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    /*在室外使用GPS*/
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 1, locationListener);
            mLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            //有时，设置网络也不行，还得到手机设置获取位置的方式：WLAN,GPS,网络
            if (mLocation == null) {
                        /*在室内使用网络*/
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 6000, 1, locationListener);
                mLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }

            LogUtils.Loge("tag","mLocation="+mLocation);
            if (mLocation != null) {
                latitude = mLocation.getLatitude();
                longitude = mLocation.getLongitude();

                LatLng ll = new LatLng(latitude, longitude);
                        /*获取详细地址*/
                String address = getAddress(this, latitude, longitude);
                        /*做标记,展示简单信息,例如：中国-北京*/
                firstAddressString = address;
                firstShortAddressString = addressString;
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(ll).title(addressString));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ll, 15));

//                tv_address_detail.setText(address);
                       
            } else {
                //
            }
        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        requestLocation();
    }

    @Override
    public void onClick(View view) {

    }
}

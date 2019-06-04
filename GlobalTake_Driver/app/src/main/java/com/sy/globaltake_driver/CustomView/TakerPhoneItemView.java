package com.sy.globaltake_driver.CustomView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sy.globaltake_driver.Bean.OrderBean;
import com.sy.globaltake_driver.Bean.TripDetailBean;
import com.sy.globaltake_driver.Const;
import com.sy.globaltake_driver.R;
import com.sy.globaltake_driver.utils.LanguageUtil;
import com.sy.globaltake_driver.utils.ToastUtils;

import org.xutils.image.ImageOptions;
import org.xutils.x;

/**
 * Created by sunnetdesign3 on 2017/2/22.
 */
public class TakerPhoneItemView extends LinearLayout {

    private ImageView taker_headicon, taker_phone;
    private TextView taker_startplace, taker_endplace;
    private String phoneNumber;

    public TakerPhoneItemView(Context context) {
        super(context);
        initView(context);
        setListener(context);
    }


    public TakerPhoneItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        setListener(context);
    }

    private void initView(Context context) {

        inflate(context, R.layout.smallitem_peoplelay, this);
        taker_headicon = (ImageView) findViewById(R.id.taker_headicon);
        taker_phone = (ImageView) findViewById(R.id.taker_phone);
        taker_startplace = (TextView) findViewById(R.id.taker_startplace);
        taker_endplace = (TextView) findViewById(R.id.taker_endplace);

    }

    public void setData(TripDetailBean.ResultBean resultBean) {

        ImageOptions options = new ImageOptions.Builder()
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setUseMemCache(true)
                .setCircular(true)
                .setFailureDrawableId(R.mipmap.tanchuang_touxiang)
                .build();
        x.image().bind(taker_headicon, Const.root_url + resultBean.getPassenger_fdIconUrl(), options);
        taker_startplace.setText(resultBean.getFdTripStartLocation());
        taker_endplace.setText(resultBean.getFdTripEndLocation());
        phoneNumber = resultBean.getPassenger_fdPhone();

    }

    private void setListener(final Context context) {
        taker_phone.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phoneNumber != null) {
                    Intent intent = new Intent(
                            Intent.ACTION_CALL, Uri
                            .parse("tel:" + phoneNumber));
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    context.startActivity(intent);
                }else {
                    ToastUtils.Toast_short(context, LanguageUtil.getResText(R.string.toast_nophone));
                }
            }
        });
    }
}

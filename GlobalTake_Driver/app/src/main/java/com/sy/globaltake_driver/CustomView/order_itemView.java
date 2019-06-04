package com.sy.globaltake_driver.CustomView;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sy.globaltake_driver.Bean.OrderBean;
import com.sy.globaltake_driver.Bean.TripDetailBean;
import com.sy.globaltake_driver.Const;
import com.sy.globaltake_driver.R;
import com.sy.globaltake_driver.utils.LanguageUtil;
import com.sy.globaltake_driver.utils.LogUtils;
import com.sy.globaltake_driver.utils.SharePreferenceUtils;
import com.sy.globaltake_driver.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by sunnetdesign3 on 2017/2/21.
 */
public class order_itemView extends LinearLayout {

    private TextView startPlace_tv,endPlace_tv,distance_tv,order_time,order_qiangdan;
    private LinearLayout creditvalue_lay,qiangdan_lay,star_lay;

    private TimeCount time;
    protected static final int START_COUNT = 2;
    private IreborOrder ireborOrder;
    private ITimeCountDown itimeCountDown;
    private String tripid;
    private boolean isClicked;

    private String codeStr;

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
    public order_itemView(Context context) {
        super(context);
        initView(context);
        //接到订单后就开始倒计时
        Message msg = new Message();
        msg.what = START_COUNT;
        handler.sendMessage(msg);
        setListener(context);
    }

    private void setListener(final Context context) {
        qiangdan_lay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                order_time.setVisibility(GONE);
                order_qiangdan.setText(LanguageUtil.getResText(R.string.tv_grabing));
                ireborOrder.onClick(tripid);
                isClicked = true;
            }
        });
    }



    public order_itemView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initView(context);
    }

    private void initView(Context context){
       inflate(context,R.layout.order_item,this);
        startPlace_tv = (TextView) findViewById(R.id.order_qidian);
        endPlace_tv = (TextView) findViewById(R.id.order_zhongdian);
        distance_tv = (TextView) findViewById(R.id.order_distance);
        creditvalue_lay = (LinearLayout) findViewById(R.id.creditvalue_lay);
        order_time = (TextView) findViewById(R.id.order_time);
        order_qiangdan = (TextView) findViewById(R.id.order_qiangdan);
        qiangdan_lay = (LinearLayout) findViewById(R.id.qiangdan_lay);
        star_lay = (LinearLayout) findViewById(R.id.star_lay);
    }

    public void setData(TripDetailBean.ResultBean resultBean){

        startPlace_tv.setText(resultBean.getFdTripStartLocation());
        endPlace_tv.setText(resultBean.getFdTripEndLocation());
        tripid = resultBean.getFdId();
//        distance_tv.setText(resultBean.getFdTotalMileage());
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

//            order_time.setText("结束");
//            order_time.setClickable(true);
//            order_time.setEnabled(true);
            if (!isClicked){//如果没有点击过抢单
                itimeCountDown.timeEnd(tripid);
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            order_time.setClickable(false);
            order_time.setEnabled(false);
            order_time.setText(millisUntilFinished / 1000 + LanguageUtil.getResText(R.string.tv_second));
        }
    }

    public interface IreborOrder{
        void onClick(String trip_id);
    }

    public void setOnQiangLayListener(IreborOrder ireborOrder){
        this.ireborOrder = ireborOrder;
    }
    public void setOnRemoListener(ITimeCountDown itimeCountDown){
        this.itimeCountDown = itimeCountDown;
    }

    public interface  ITimeCountDown{
        void timeEnd(String trip_id);
    }
}

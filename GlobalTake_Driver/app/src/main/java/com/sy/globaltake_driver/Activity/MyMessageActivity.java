package com.sy.globaltake_driver.Activity;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.sy.globaltake_driver.Adapter.ListViewMsgAdapter;
import com.sy.globaltake_driver.Bean.MyMessageBean;
import com.sy.globaltake_driver.Const;
import com.sy.globaltake_driver.R;
import com.sy.globaltake_driver.utils.LanguageUtil;
import com.sy.globaltake_driver.utils.LogUtils;
import com.sy.globaltake_driver.utils.SharePreferenceUtils;
import com.sy.globaltake_driver.utils.ToastUtils;
import com.sy.globaltake_driver.utils.XuniKeyWord;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sunnetdesign3 on 2017/3/2.
 */
public class MyMessageActivity extends AppCompatActivity {

    private Context context;
    private List<MyMessageBean.ResultBean> dataList = new ArrayList<>();
    private ListViewMsgAdapter adpter;
    @Bind(R.id.back_relay)
    RelativeLayout back_relay;
    @Bind(R.id.root_lay)
    LinearLayout root_lay;
    @Bind(R.id.mymessage_lv)
    ListView mymessage_lv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mymessage);
        ButterKnife.bind(this);
        context = this;
        XuniKeyWord.setShiPei(this,root_lay);
        XuniKeyWord.setStatusBarBgColor(this,R.color.statecolor);
        adpter = new ListViewMsgAdapter(this,dataList);
        mymessage_lv.setAdapter(adpter);
        getMsgData();
        setEvent();
    }

    private void setEvent() {
        back_relay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getMsgData(){
        String userId = (String) SharePreferenceUtils.getParam(MyMessageActivity.this,Const.UserId,"");
        RequestParams params = new RequestParams(Const.getMyMsg);
        params.addBodyParameter("user_id",userId);
        LanguageUtil.addLanguage(params);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject objece = new JSONObject(result);
                    if (objece.getString(Const.Code).equals(Const.reques_success)){
                        Gson gson = new Gson();
                        MyMessageBean messagBean = gson.fromJson(result,MyMessageBean.class);
                        if (messagBean!=null){
                            List<MyMessageBean.ResultBean> resultBeanList = messagBean.getResult();
                            if (resultBeanList!=null){
                                dataList.clear();
                                dataList.addAll(resultBeanList);
                                LogUtils.Loge("tag","dataList.size()="+dataList.size());
                                adpter.notifyDataSetChanged();
                            }
                        }else {
                            ToastUtils.Toast_short(context, LanguageUtil.getResText(R.string.toast_parsingexception));
                        }
                    }else {
                        ToastUtils.Toast_short(context,LanguageUtil.getResText(R.string.toast_dataexception));
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
}

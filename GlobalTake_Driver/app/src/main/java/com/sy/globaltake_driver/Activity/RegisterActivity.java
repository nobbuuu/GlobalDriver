package com.sy.globaltake_driver.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sy.globaltake_driver.Const;
import com.sy.globaltake_driver.R;
import com.sy.globaltake_driver.utils.XuniKeyWord;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sunnetdesign3 on 2017/3/2.
 */
public class RegisterActivity extends AppCompatActivity {

   /* @Bind(R.id.back_relay)
    RelativeLayout back_relay;
    @Bind(R.id.changpwd_phoneedt)
    EditText changpwd_phoneedt;
    @Bind(R.id.changpwd_pwdedt)
    EditText changpwd_pwdedt;
    @Bind(R.id.changpwd_yanzm)
    TextView changpwd_yanzm;*/

    @Bind(R.id.root_relay)
    RelativeLayout root_relay;
    @Bind(R.id.back_relay)
    RelativeLayout back_relay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rengzheng);
        ButterKnife.bind(this);
        XuniKeyWord.setShiPei(this,root_relay);
        XuniKeyWord.setStatusBarBgColor(this,R.color.statecolor);
        WebView webview = (WebView) findViewById(R.id.register_webview);
        webview.loadUrl(Const.webRootUrl+Const.KeyRegister);
        back_relay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}

package com.sy.globaltake_driver.CustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sy.globaltake_driver.Bean.MsgitemBean;
import com.sy.globaltake_driver.R;
import com.sy.globaltake_driver.utils.TimeUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sunnetdesign3 on 2017/3/21.
 */
public class XTmsgItemView extends LinearLayout {

    @Bind(R.id.msg_title)
    TextView msg_title;
    @Bind(R.id.msg_time)
    TextView msg_time;
    @Bind(R.id.msg_content)
    TextView msg_content;
    @Bind(R.id.msg_close)
    ImageView msg_close;

    private String msg_id;
    private DeleteItemListener listener;

    public XTmsgItemView(Context context) {
        super(context);
        initView(context);
    }

    public XTmsgItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context){
        View itemview = inflate(context, R.layout.jiameng_item, this);
        ButterKnife.bind(this,itemview);
        msg_close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(view,msg_id);
            }
        });
    }

    public void setData(MsgitemBean.ResultBean dataBean){

        if (dataBean.getFdTitle()!=null){
            msg_title.setText(dataBean.getFdTitle());
        }

        if (dataBean.getFdCreateTime()+""!=null){
            String strTime = TimeUtils.getStrTime(String.valueOf(dataBean.getFdCreateTime()), "MM-dd  hh:mm");
            msg_time.setText(strTime);
        }
        if (dataBean.getFdContent()!=null){
            msg_content.setText(dataBean.getFdContent());
        }

        if (dataBean.getFdId()!=null){
            msg_id = dataBean.getFdId();
        }
    }

    public interface DeleteItemListener{
        void onClick(View view,String msgId);
    }

    public void removeItem(DeleteItemListener deletelistener){
        this.listener = deletelistener;
    }
}

package com.sy.globaltake_driver.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sy.globaltake_driver.Bean.MyMessageBean;
import com.sy.globaltake_driver.R;
import com.sy.globaltake_driver.utils.LanguageUtil;
import com.sy.globaltake_driver.utils.LogUtils;
import com.sy.globaltake_driver.utils.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by sunnetdesign3 on 2016/10/19.
 */
public class ListViewMsgAdapter extends BaseAdapter {

    private Context context;
    private List<MyMessageBean.ResultBean> noticeBeans;
    private Drawable downDraw, upDraw;
    int maxDescripLine = 2; //TextView默认最大展示行数

    public ListViewMsgAdapter(Context context, List<MyMessageBean.ResultBean> noticeBeans) {
        this.context = context;
        this.noticeBeans = noticeBeans;
        downDraw = context.getResources().getDrawable(R.drawable.xiangxia);
        upDraw = context.getResources().getDrawable(R.drawable.xiangshang);
    }

    @Override
    public int getCount() {
        return noticeBeans == null ? 0 : noticeBeans.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_msg, null);
            viewHolder.tvTitle = (TextView) view.findViewById(R.id.msg_tvtitle);
            viewHolder.tvSummary = (TextView) view.findViewById(R.id.msg_tvsummary);
            viewHolder.tvTime = (TextView) view.findViewById(R.id.msg_tvtime);
            viewHolder.ivAction = (ImageButton) view.findViewById(R.id.msg_spand);
            viewHolder.msgimg_layout = (RelativeLayout) view.findViewById(R.id.msg_expand_layout);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (LanguageUtil.isZh()){

            viewHolder.tvTitle.setText(noticeBeans.get(i).getFdTitle());
            viewHolder.tvSummary.setText(noticeBeans.get(i).getFdContent());
        }else {
            viewHolder.tvTitle.setText(noticeBeans.get(i).getFdEnTitle());
            viewHolder.tvSummary.setText(noticeBeans.get(i).getFdEnContent());
        }
        final ViewHolder finalViewHolder1 = viewHolder;
        viewHolder.tvSummary.post(new Runnable() {
            @Override
            public void run() {
                int lineCount = finalViewHolder1.tvSummary.getLineCount();
                if (lineCount<maxDescripLine){
                    finalViewHolder1.msgimg_layout.setVisibility(View.GONE);
                }else {
                    finalViewHolder1.msgimg_layout.setVisibility(View.VISIBLE);
                }
            }
        });
        viewHolder.tvSummary.setMaxLines(maxDescripLine);
        viewHolder.tvSummary.setEllipsize(TextUtils.TruncateAt.END);
        long fdCreateTime = noticeBeans.get(i).getFdCreateTime();
        viewHolder.tvTime.setText(TimeUtils.getStrTime(String.valueOf(fdCreateTime)));
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.msgimg_layout.setOnClickListener(new View.OnClickListener() {
            boolean more;

            @Override
            public void onClick(View view) {
                if (!more) {
                    finalViewHolder.tvSummary.setSingleLine(false);//实现多行，TextView 伸展开
                    finalViewHolder.tvSummary.setEllipsize(TextUtils.TruncateAt.END);//省略号在后面
                    finalViewHolder.ivAction.setBackground(upDraw);
                    more = true;
                } else {
                    finalViewHolder.tvSummary.setLines(maxDescripLine);   //TextView 显示的行数为2
                    finalViewHolder.tvSummary.setEllipsize(TextUtils.TruncateAt.END);//省略号在后面
//                   id_liners.setSingleLine(true);//加上这个会只展现一行
                    finalViewHolder.ivAction.setBackground(downDraw);
                    more = false;
                }
            }
        });
        viewHolder.ivAction.setOnClickListener(new View.OnClickListener() {
            boolean more;

            @Override
            public void onClick(View view) {
                if (!more) {
                    finalViewHolder.tvSummary.setSingleLine(false);//实现多行，TextView 伸展开
                    finalViewHolder.tvSummary.setEllipsize(TextUtils.TruncateAt.END);//省略号在后面
                    finalViewHolder.ivAction.setBackground(upDraw);
                    more = true;
                } else {
                    finalViewHolder.tvSummary.setLines(maxDescripLine);   //TextView 显示的行数为2
                    finalViewHolder.tvSummary.setEllipsize(TextUtils.TruncateAt.END);//省略号在后面
//                   id_liners.setSingleLine(true);//加上这个会只展现一行
                    finalViewHolder.ivAction.setBackground(downDraw);
                    more = false;
                }
            }
        });
        return view;
    }

    class ViewHolder {
        private TextView tvTitle, tvSummary, tvTime;
        private ImageView ivAction;
        private RelativeLayout msgimg_layout;
    }
}

package com.liuyang.com.mobilesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liuyang.com.mobilesafe.R;

/**
 * Created by liuyang on 2016/8/6.
 */
public class SettingItemView extends RelativeLayout {
    TextView tv_title;
    TextView tv_desc;
    CheckBox cb_box;
    /**
     * 重写了构造方法，调用两个参数的方法
     * @param context
     */
    public SettingItemView(Context context) {
        this(context,null);
    }

    /**
     *重写了构造方法，调用三个参数的方法
     * @param context
     * @param attrs
     */
    public SettingItemView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }


    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        View.inflate(context, R.layout.setting_item_view,this);

        /**
         View view = View.inflate(context, R.layout.setting_item_view, this);
         this.addView(view);
         */
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_desc = (TextView) findViewById(R.id.tv_desc);
        cb_box = (CheckBox) findViewById(R.id.cb_box);
    }

    /**
     * 判断是否开启的方法
     * @return
     */
    public boolean isCheck(){
        return cb_box.isChecked();
    }

    /**
     * 设置条目中明细的状态
     * @param isCheck
     */
    public void setCheck(boolean isCheck){
        cb_box.setChecked(isCheck);
        if(isCheck){
            tv_desc.setText("自动更新已开启");
        } else{
            tv_desc.setText("自动更新已关闭");
        }
    }
}

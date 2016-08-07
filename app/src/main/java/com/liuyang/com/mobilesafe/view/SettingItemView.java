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

        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        TextView tv_desc = (TextView) findViewById(R.id.tv_desc);
        CheckBox cb_box = (CheckBox) findViewById(R.id.cb_box);
    }
}

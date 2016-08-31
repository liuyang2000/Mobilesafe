package com.liuyang.com.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.liuyang.com.mobilesafe.R;
import com.liuyang.com.mobilesafe.util.ContantValue;
import com.liuyang.com.mobilesafe.util.SpUtil;

/**
 * Created by liuyang on 2016/8/29.
 */
public class SetupOverActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean setup_over = SpUtil.getBoolean(this, ContantValue.SET_UP_OVER, false);
        if(setup_over){
            // 密码输入成功，并且四个导航界面设置完成，。停留在设置完成功能界面上
            setContentView(R.layout.activity_setup_over);
        } else {
            // 密码输入成功，四个导航界面没有设置完成，跳转至导航界面 1
            Intent intent = new Intent(this, Setup1Activity.class);
            startActivity(intent);
            // 开启了一个新的界面，关闭功能列表
            finish();
        }
    }
}

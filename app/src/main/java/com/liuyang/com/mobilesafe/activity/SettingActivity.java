package com.liuyang.com.mobilesafe.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.liuyang.com.mobilesafe.R;
import com.liuyang.com.mobilesafe.util.ContantValue;
import com.liuyang.com.mobilesafe.util.SpUtil;
import com.liuyang.com.mobilesafe.view.SettingItemView;

/**
 * Created by liuyang on 2016/8/6.
 */
public class SettingActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_setting);

        initUpdate();
    }

    private void initUpdate() {
        final SettingItemView siv_update = (SettingItemView) findViewById(R.id.siv_update);

        // 获取保存的更新的开关状态
        boolean open_update = SpUtil.getBoolean(this, ContantValue.OPEN_UPDATE, false);

        // 是否选中，根据上次存储的结果设置
        siv_update.setCheck(open_update);

        siv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取之前的选中状态
                boolean isCheck = siv_update.isCheck();
                // 设置相反的状态
                siv_update.setCheck(!isCheck);
                // 将更新的状态存放入SP
                SpUtil.putBoolean(getApplicationContext(), ContantValue.OPEN_UPDATE, !isCheck);
            }
        });
    }
}

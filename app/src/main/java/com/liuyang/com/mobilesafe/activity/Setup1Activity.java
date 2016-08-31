package com.liuyang.com.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.liuyang.com.mobilesafe.R;

/**
 * Created by liuyang on 2016/8/31.
 */
public class Setup1Activity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup1);
    }

    public void nextPage(View view){
        Intent intent = new Intent(getApplicationContext(), Setup2Activity.class);
        startActivity(intent);
    }
}

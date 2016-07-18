package com.liuyang.com.mobilesafe.util;

import android.content.Context;
import android.os.Message;
import android.widget.Toast;

import com.liuyang.com.mobilesafe.SplashActivity;

/**
 * Created by liuyang on 2016/6/5.
 */
public class ToastUtil {
    public static void show(Context ctx, String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
    }
}

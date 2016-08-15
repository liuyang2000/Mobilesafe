package com.liuyang.com.mobilesafe.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by liuyang on 2016/8/7.
 */
public class SpUtil {

    private static SharedPreferences sp;

    /**
     * 保存Boolean类型的值
     * @param ctx 上下文环境
     * @param key
     * @param value
     */
    public static void putBoolean(Context ctx, String key, boolean value) {
        if (sp == null) {
            sp = ctx.getSharedPreferences("config", ctx.MODE_PRIVATE);
        }

        sp.edit().putBoolean(key, value).commit();
    }

    /**
     * 获取boolean 类型的值
     * @param ctx 上下文环境
     * @param key
     * @param defValue 默认值， 如果获取不到 key 对应的value ,则返回 defValue
     * @return
     */
    public static boolean getBoolean(Context ctx, String key, boolean defValue){
        if(sp == null){
            sp = ctx.getSharedPreferences("config", ctx.MODE_PRIVATE);
        }

        return sp.getBoolean(key, defValue);
    }

    /**
     * 保存String类型的值
     * @param ctx 上下文环境
     * @param key
     * @param value
     */
    public static void putString(Context ctx, String key, String value) {
        if (sp == null) {
            sp = ctx.getSharedPreferences("config", ctx.MODE_PRIVATE);
        }

        sp.edit().putString(key, value).commit();
    }

    /**
     * 获取String 类型的值
     * @param ctx 上下文环境
     * @param key
     * @param defValue 默认值， 如果获取不到 key 对应的value ,则返回 defValue
     * @return
     */
    public static String getString(Context ctx, String key, String defValue){
        if(sp == null){
            sp = ctx.getSharedPreferences("config", ctx.MODE_PRIVATE);
        }

        return sp.getString(key, defValue);
    }
}

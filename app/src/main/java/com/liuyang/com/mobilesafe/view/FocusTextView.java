package com.liuyang.com.mobilesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by liuyang on 2016/8/5.
 * 能够获取焦点的TextView
 */
public class FocusTextView extends TextView{
    /**
     * 使用场景： 使用java代码创建控件的情况
     * @param context
     */
    public FocusTextView(Context context) {
        super(context);
    }

    /**
     * 有系统调用（带属性 + 上下文环境的构造方法）
     * @param context
     * @param attrs
     */
    public FocusTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 有系统调用（带属性 + 上下文环境的构造方法 + 样式）
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public FocusTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}

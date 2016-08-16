package com.liuyang.com.mobilesafe;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.liuyang.com.mobilesafe.activity.SettingActivity;
import com.liuyang.com.mobilesafe.activity.TestActivity;
import com.liuyang.com.mobilesafe.util.ContantValue;
import com.liuyang.com.mobilesafe.util.SpUtil;
import com.liuyang.com.mobilesafe.util.ToastUtil;

/**
 * 主界面
 */
public class HomeActivity extends AppCompatActivity {

    private GridView gv_home;
    private String[] mTitleStrs;
    private int[] mDrawableIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initUI();

        // 初始化数据
        initData();
    }

    private void initData() {
        //准备数据(文字(9组),图片(9张))
        mTitleStrs = new String[]{
                "手机防盗","通信卫士","软件管理","进程管理","流量统计","手机杀毒","缓存清理","高级工具","设置中心"
        };

        mDrawableIds = new int[]{
                R.drawable.home_safe,R.drawable.home_callmsgsafe,
                R.drawable.home_apps,R.drawable.home_taskmanager,
                R.drawable.home_netmanager,R.drawable.home_trojan,
                R.drawable.home_sysoptimize,R.drawable.home_tools,R.drawable.home_settings
        };

        // 九宫格控件设置数据适配器
        gv_home.setAdapter(new MyAdapter());

        gv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0 : 
                        showDialog();
                        break;
                    case 8 :
                        Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    /**
     *
     */
    private void showDialog() {
        /**
         * 获取是否保存了密码
         */
        String psd = SpUtil.getString(this, ContantValue.MOBILE_SAVE_PSD, "");
        // 显示不同的对话框
        if(TextUtils.isEmpty(psd)){
            // 显示初始设置密码的对话框
            showSetPsdDialog();
        } else {
            // 显示确认密码的对话框
            showConfirmPsdDialog();
        }
    }

    private void showConfirmPsdDialog() {{
        /**
         * 需要自定义对话的样式，根据layout文件确定
         * 为dialog设置一个VIEW dialog.setView(view);
         */
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();

        final View view = View.inflate(this, R.layout.dialog_confirm_psd, null);
        dialog.setView(view,0,0,0,0);

        dialog.show();

        Button btn_submit = (Button) view.findViewById(R.id.btn_submit);
        Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String savedPsd = SpUtil.getString(getApplication(), ContantValue.MOBILE_SAVE_PSD, "");
                EditText et_set_psd = (EditText) view.findViewById(R.id.et_set_psd);

                String psd = et_set_psd.getText().toString();

                if(!TextUtils.isEmpty(psd) && !TextUtils.isEmpty(savedPsd)){
                    if(psd.equals(savedPsd)){
                        // 进入手机防盗模块,开启一个新的Activity
                        Intent intent = new Intent(getApplicationContext(), TestActivity.class);
                        startActivity(intent);
                        //进入新的界面后,隐藏对话框
                        dialog.dismiss();

                    } else {
                        ToastUtil.show(getApplicationContext(),"确认密码错误");
                    }
                } else {
                    ToastUtil.show(getApplicationContext(),"请输入密码");
                }

            }
        });
    }

    }

    private void showSetPsdDialog() {
        /**
         * 需要自定义对话的样式，根据layout文件确定
         * 为dialog设置一个VIEW dialog.setView(view);
         */
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();

        final View view = View.inflate(this, R.layout.dialog_set_psd, null);
        dialog.setView(view,0,0,0,0);

        dialog.show();

        Button btn_submit = (Button) view.findViewById(R.id.btn_submit);
        Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et_set_psd = (EditText) view.findViewById(R.id.et_set_psd);
                EditText et_confirm_psd = (EditText) view.findViewById(R.id.et_confirm_psd);

                String psd = et_set_psd.getText().toString();
                String confirmPsd = et_confirm_psd.getText().toString();

                if(!TextUtils.isEmpty(psd) && !TextUtils.isEmpty(confirmPsd)){
                    if(psd.equals(confirmPsd)){
                    // 进入手机防盗模块,开启一个新的Activity
                        Intent intent = new Intent(getApplicationContext(), TestActivity.class);
                        startActivity(intent);

                        dialog.dismiss();

                        // 存储设置的密码；
                        SpUtil.putString(getApplication(),ContantValue.MOBILE_SAVE_PSD,psd);
                    } else {
                        ToastUtil.show(getApplicationContext(),"确认密码错误");
                    }
                } else {
                    ToastUtil.show(getApplicationContext(),"请输入密码");
                }

            }
        });
    }

    private void initUI() {
        gv_home = (GridView) findViewById(R.id.gv_home);

    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mTitleStrs.length;
        }

        @Override
        public Object getItem(int position) {
            return mTitleStrs[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(getApplicationContext(), R.layout.gridview_item, null);
            TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
            ImageView iv_icon = (ImageView) view.findViewById(R.id.iv_icon);

            tv_title.setText(mTitleStrs[position]);
            iv_icon.setBackgroundResource(mDrawableIds[position]);
            return view;
        }
    }
}

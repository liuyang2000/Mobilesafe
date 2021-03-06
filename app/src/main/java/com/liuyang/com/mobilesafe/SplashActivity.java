package com.liuyang.com.mobilesafe;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.liuyang.com.mobilesafe.util.ContantValue;
import com.liuyang.com.mobilesafe.util.SpUtil;
import com.liuyang.com.mobilesafe.util.StreamUtil;
import com.liuyang.com.mobilesafe.util.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";
    private static final int UPDATE_VERSION = 100;
    private static final int ENTER_HOME = 101;
    private static final int URL_ERROR = 102;
    private static final int IO_ERROR = 103;
    private static final int JSON_ERROR = 104;
    TextView tv_version_name;
    int mLocalVersionCode;

    String mVersionName ;
    String mVersionCode ;
    String mVersionDes ;
    String mDownloadUrl ;

    RelativeLayout rl_root;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_VERSION:
                    showUpdateDialog();
                    break;
                case ENTER_HOME:
                    enterHome();
                    break;
                case URL_ERROR:
                    ToastUtil.show(SplashActivity.this, "url_error");
                    enterHome();
                    break;
                case IO_ERROR:
                    ToastUtil.show(SplashActivity.this, "io_error");
                    enterHome();
                    break;
                case JSON_ERROR:
                    ToastUtil.show(SplashActivity.this, "json_error");
                    enterHome();
                    break;
            }
        }
    };

    private void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("版本更新");
        builder.setMessage(mVersionDes);
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                downloadApk();
            }
        });

        builder.setNegativeButton("取消",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                enterHome();
            }
        });
        builder.show();
    }

    private void downloadApk() {
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            Log.i(TAG,"外部设备正常");

            String path = Environment.getExternalStorageDirectory() + File.separator + "mobileSage74.apk";

            HttpUtils httpUtils = new HttpUtils();

            httpUtils.download(mDownloadUrl, path, new RequestCallBack<File>() {
                @Override
                public void onSuccess(ResponseInfo<File> responseInfo) {
                    Log.i(TAG,"下载成功");
                    File file = responseInfo.result;
                    Log.i(TAG,"File getname : " + file.getName());
                }

                @Override
                public void onFailure(HttpException e, String s) {
                    Log.i(TAG,"下载成功");
                }

                @Override
                public void onStart() {
                    Log.i(TAG,"开始下载");
                    super.onStart();
                }

                @Override
                public void onLoading(long total, long current, boolean isUploading) {
                    Log.i(TAG,"下载中。。。。" + " total = " + total + " current = " + current);
                    super.onLoading(total, current, isUploading);
                }
            });
        }
    }

    private void enterHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);

        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //testSession();

        initUI();

        initData();

        initAnimation();
    }

    /**
     * 添加淡入的动画效果
     */
    private void initAnimation() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);

        alphaAnimation.setDuration(3000);

        rl_root.startAnimation(alphaAnimation);
    }

    private void testSession() {
        System.out.println("-------------");
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection urlConnection = null;
                try {
                    System.out.println("++++++++++++++++++++++==");
                    URL url = new URL("http://192.168.10.48/jfinal_jetty/hello/test?lm=0&rn=10&pn=0&fr=search&ie=gbk&word=sdfa");

                    urlConnection = (HttpURLConnection) url.openConnection();

                    urlConnection.setReadTimeout(50000);
                    String sessionid = "100";
                    if(sessionid != null) {
                        urlConnection.setRequestProperty("MySessionId", "MySessionId : " +sessionid);
                    }

                    System.out.println("getResponseMessage" + urlConnection.getResponseMessage());

                    System.out.println("getHeaderFields -------");
                    Map<String, List<String>> headerFields = urlConnection.getHeaderFields();
                    for(String key : headerFields.keySet()){
                        List<String> list = headerFields.get(key);
                        for(String str : list){
                            System.out.print(str + " , ");
                        }
                        System.out.println();
                    }

                    List<String> cookieList = headerFields.get("Set-Cookie");
                    String[] item = null;
                    String key = null;
                    String value = null;
                    for(String cookieItem : cookieList){
                        item = cookieItem.split("=");
                        key = item[0];
                        value = item[1];
                        if("sessionId".equals(key)){
                            System.out.println("从服务器端传送回的数据； " + key + "=" + value);
                        }
                    }

                    System.out.println("---------------------------------------");

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    urlConnection.disconnect();
                }
            }
        }).start();
    }

    /**
     * 初始化UI
     */
    public void initUI() {
        tv_version_name = (TextView) findViewById(R.id.tv_version_name);

        rl_root = (RelativeLayout) findViewById(R.id.rl_root);

    }


    public void initData() {
        tv_version_name.setText("版本名称：" + getVersionName());

        mLocalVersionCode = getVersionCode();

        if(SpUtil.getBoolean(this, ContantValue.OPEN_UPDATE, false)){
            checkVersion();
        } else {
            /**
             * 不能直接调用enterHome();进入主界面
             * 方法： 发送消息  4 秒后执行
             */
            mHandler.sendEmptyMessageDelayed(ENTER_HOME, 4000);
        }
    }

    private void checkVersion() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                Message msg = Message.obtain();
                long startTime = System.currentTimeMillis();
                try {
                    System.out.println("-----------------------");
                    URL url = new URL("http://10.0.2.2:8080/update74.json");

                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                    urlConnection.setReadTimeout(2000);
                    urlConnection.setConnectTimeout(2000);
                    urlConnection.setRequestMethod("GET");
                    System.out.println("++++++++++++++++++++++++++++++");

                    if (urlConnection.getResponseCode() == 200) {
                        InputStream inputStream = urlConnection.getInputStream();

                        String json = StreamUtil.stream2String(inputStream);
                        Log.i(TAG, "run: " + json);

                        JSONObject jsonObject = new JSONObject(json);
                        mVersionName = jsonObject.getString("versionName");
                        mVersionCode = jsonObject.getString("versionCode");
                        mVersionDes = jsonObject.getString("versionDes");
                        mDownloadUrl = jsonObject.getString("downloadUrl");

                        System.out.println("");

                        if (mLocalVersionCode < Integer.parseInt(mVersionCode)) {
                            msg.what = UPDATE_VERSION;
                        } else {
                            msg.what = ENTER_HOME;
                        }
                    } else {
                        System.out.println("读取服务器apk版本有问题");
                    }
                } catch (MalformedURLException e) {
                    msg.what = URL_ERROR;
                    e.printStackTrace();
                } catch (IOException e) {
                    msg.what = IO_ERROR;
                    e.printStackTrace();
                } catch (JSONException e) {
                    msg.what = JSON_ERROR;
                    e.printStackTrace();
                } finally {
                    long endTime = System.currentTimeMillis();
                    if (endTime - startTime < 4000) {
                        try {
                            Thread.sleep(4000 - (endTime - startTime));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    mHandler.sendMessage(msg);
                }
            }
        }).start();
    }

    private int getVersionCode() {
        try {
            return getPackageManager().getPackageInfo(this.getPackageName(), PackageManager.GET_ACTIVITIES).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private String getVersionName() {
        try {
            return getPackageManager().getPackageInfo(this.getPackageName(), PackageManager.GET_ACTIVITIES).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


}

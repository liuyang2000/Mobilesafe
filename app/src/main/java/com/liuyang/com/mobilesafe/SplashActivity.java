package com.liuyang.com.mobilesafe;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.liuyang.com.mobilesafe.util.StreamUtil;
import com.liuyang.com.mobilesafe.util.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

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

    public void initUI() {
        tv_version_name = (TextView) findViewById(R.id.tv_version_name);

    }


    public void initData() {
        tv_version_name.setText("版本名称：" + getVersionName());

        mLocalVersionCode = getVersionCode();

        checkVersion();
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

package com.top.prozoom.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.top.proutils.AppInfoUtils;
import com.top.prozoom.Constants;
import com.top.prozoom.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/*
* 作者：ProZoom
* 时间：2017/7/29-下午7:46
* 描述：欢迎界面
*/

public class SplashActivity extends Activity {

    private View mContentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        TextView tv_splash=(TextView) findViewById(R.id.tv_splash);
        tv_splash.setText("ProZoom出品 v"+ AppInfoUtils.getAppVersionName(this));
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mContentView = findViewById(R.id.fullscreen_content);

        new Thread(new Runnable() {
            @Override
            public void run() {
                getPlugins();
            }
        }).start();
    }



    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        mContentView.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(-1,android.R.anim.slide_out_right);
            }
        },3000);
    }

    /**
     * 获取插件的名称及包名
     */
    private void getPlugins() {

        String url="https://raw.githubusercontent.com/ProZoom/Blog/master/Blog/Other/Android.json";
        String urlPath="https://github.com/ProZoom/Blog/raw/master/Blog/Other/";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            String json = response.body().string();
            Log.i("APK",json);
            //Log.i("dd", "A---"+json.length());
            //把json字符串封装成json数据
            JSONArray jsonArray=new JSONArray(json);

            Log.i("APK",""+jsonArray.length()); //
            Constants.plugins2=new String[jsonArray.length()][2];
            Constants.apkUrlPath = new String[jsonArray.length()];

            for (int i=0; i<jsonArray.length(); i++) {
                JSONObject jsonObject=new JSONObject(jsonArray.get(i).toString());
                String name = jsonObject.getString("name");
                String className = jsonObject.getString("class");

                final String path = urlPath+jsonObject.getString("name").toLowerCase()+"-release.apk";

                Constants.apkUrlPath[i]=path;
                Constants.plugins2[i][0]=name;//插件别名
                Constants.plugins2[i][1]=className;//插件包名
                Log.i("APK","\n\nname: "+name+"\nclassName: "+className+"\npath: "+path);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}

package com.top.prozoom;
import com.qihoo360.replugin.RePluginApplication;
import com.tencent.stat.StatConfig;

import cn.bmob.v3.Bmob;

/*
* 作者：ProZoom
* 时间：2017/8/1 - 上午7:33
* 描述：
*/
public class MainApplication extends RePluginApplication {


    final private String BuglyAPPID="0d9b8cbbdc";
    final private String BmobAPPID="ab4709ce59076a48fbdfebb64f9f9c8e";

    final private String QQAPPID="1106290418";
    final private String QQAPPKEY="xZa8DJzBvrDBgf9c";



    @Override
    public void onCreate() {
        super.onCreate();
        //第一：默认初始化
        Bmob.initialize(this, BmobAPPID);
        //CrashReport.initCrashReport(this,BuglyAPPID,true);
        StatConfig.setAppKey(this, "Aqc" + QQAPPKEY);
    }

}

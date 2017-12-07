package com.top.prozoom;

/*
* 作者：ProZoom
* 时间：2017/8/23 - 下午10:27
* 描述：
*/
public class Constants {


    public static StringBuffer[] uiItemtitle2 = new StringBuffer[]{};
    public static String[][] plugins2 = new String[][]{};
    public static String[] apkUrlPath ;


    public static String[] uiItemTitle = new String[]{
            "Splash引导动画",
            "SurfaceTextView",
            "Permission",
            "Loading加载动画",
            "标签云",
            "ListView效果集合",
            "图片缓存",
            "三级菜单",
            "下拉菜单",
            "优酷菜单",
            "广告轮播图",
            "消息气泡",
            "定期更新！"};

    public static String[][] plugin = new String[][]{{
            "splash",
            "surpertextview",
            "permission",
            "loading",
            "tagcloud",
            "",
            "",
            "threelevelmenu",
            "downmenu",
            "youkumenu",
            "advlunbo",
            "newsbubble"
    }, {
            "com.top.splash.MainActivity",
            "com.top.surpertextview.MainActivity",
            "com.top.permission.MainActivity",
            "com.top.loading.MainActivity",
            "com.top.tagcloud.SearchFlyActivity",
            "",
            "",
            "com.zoom.thirdlevelmenu.MainActivity",
            "com.example.spinner.MainActivity",
            "com.example.menu.MainActivity",
            "com.example.viewpager.MainActivity",
            "com.zoom.newbubble.BubbleListActivity"
    }};
}

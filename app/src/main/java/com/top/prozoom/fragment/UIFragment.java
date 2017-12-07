package com.top.prozoom.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.qihoo360.replugin.RePlugin;
import com.top.prozoom.R;

/*
* 作者：ProZoom
* 时间：2017/8/5 - 下午10:12
* 描述：
*/
public class UIFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView lv_ui;

    public static String[] uiItemTitle=new String[]{
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

    private String[][] plugin=new String[][]{{
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
    },{
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.i("RRRRR",""+plugin[0].length);
        View view=inflater.inflate(R.layout.fragment_ui, container, false);

        lv_ui= (ListView) view.findViewById(R.id.frag_ui_list);

        //装备数据
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.listview_item,
                uiItemTitle);
        lv_ui.setAdapter(adapter);

        lv_ui.setOnItemClickListener(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                preLoadPlugin();
            }
        }).start();
        return view;
    }

    private void preLoadPlugin() {
        for (int i=0; i<plugin[0].length; i++) {
            RePlugin.preload(plugin[0][i]);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.i("FFF",""+i);
        if (i==uiItemTitle.length-1){
            Toast.makeText(getActivity(),"更多控件，敬请期待！",Toast.LENGTH_LONG).show();
        }else {
            RePlugin.startActivity(getActivity(), RePlugin.createIntent(plugin[0][i], plugin[1][i]));
        }
    }
}

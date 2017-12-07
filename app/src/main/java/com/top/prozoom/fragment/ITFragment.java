package com.top.prozoom.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.top.prozoom.R;
import com.top.prozoom.activity.QuestionActivity;
import com.top.prozoom.bean.AiQuestionBean;
import com.top.prozoom.bean.AndroidQuestionBean;
import com.top.prozoom.bean.H5QuestionBean;
import com.top.prozoom.bean.KotlinQuestionBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/*
* 作者：ProZoom
* 时间：2017/8/5 - 下午10:12
* 描述：
*/
public class ITFragment extends Fragment implements AdapterView.OnItemClickListener {


    private GridView gv;
    private List<Map<String, Object>> data;
    // 图片封装为一个数组
    private int[] icon = {
            R.drawable.ic_kotlin,
            R.drawable.ic_android,
            R.drawable.ic_html5,
            R.drawable.ic_kotlin
    };
    private String[] iconName = {"Kotlin","Android","H5","AI"};

    private int[] itemnum=new int[4];


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getDataNumFromBmob();
        View view=inflater.inflate(R.layout.fragment_it, container, false);

        gv=view.findViewById(R.id.frag_it_grid);

        data = new ArrayList<>();

        data=getData();

        String[] from=new String[]{"image","text"};

        int[] to=new int[]{R.id.iv_icon, R.id.tv_title};

        ListAdapter simpleAdapter=new SimpleAdapter(getActivity(),
                data,
                R.layout.gridview_item,
                from,
                to);
        gv.setAdapter(simpleAdapter);
        gv.setOnItemClickListener(this);
        return view;
    }

    public List<Map<String, Object>> getData(){
        //cion和iconName的长度是相同的，这里任选其一都可以
        for(int i=0;i<icon.length;i++){
            Map<String, Object> map = new HashMap<>();
            map.put("image", icon[i]);
            map.put("text", iconName[i]);
            data.add(map);
        }

        return data;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent=new Intent();
        intent.setClass(getActivity(), QuestionActivity.class);

        switch (i){
            case 0:

                if(itemnum[0]==0){
                    Toast.makeText(getActivity(), "暂无面试题，敬请期待！", Toast.LENGTH_SHORT).show();
                }else {
                    intent.putExtra("title","Kotlin面试");
                    startActivity(intent);
                }

                //Toast.makeText(getActivity(), "0", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                if(itemnum[1]==0){
                    Toast.makeText(getActivity(), "暂无面试题，敬请期待！", Toast.LENGTH_SHORT).show();
                }else {
                    intent.putExtra("title", "Android面试");
                    startActivity(intent);
                    //Toast.makeText(getActivity(), "1", Toast.LENGTH_SHORT).show();
                }
                break;

            case 2:
                if(itemnum[2]==0){
                    Toast.makeText(getActivity(), "暂无面试题，敬请期待！", Toast.LENGTH_SHORT).show();
                }else {
                    intent.putExtra("title", "H5面试");
                    startActivity(intent);
                    //Toast.makeText(getActivity(), "2", Toast.LENGTH_SHORT).show();
                }
                break;

            case 3:
                if(itemnum[3]==0){
                    Toast.makeText(getActivity(), "暂无面试题，敬请期待！", Toast.LENGTH_SHORT).show();
                }else {
                    intent.putExtra("title", "AI面试");
                    startActivity(intent);
                    //Toast.makeText(getActivity(), "3", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    private void getDataNumFromBmob(){

        BmobQuery<KotlinQuestionBean> kotlinQuery = new BmobQuery<>();
        BmobQuery<AndroidQuestionBean> androidQuery = new BmobQuery<>();
        BmobQuery<H5QuestionBean> h5Query = new BmobQuery<>();
        BmobQuery<AiQuestionBean> aiQuery = new BmobQuery<>();

        kotlinQuery.order("-createdAt");
        androidQuery.order("-createdAt");
        h5Query.order("-createdAt");
        aiQuery.order("-createdAt");

        kotlinQuery.findObjects(new FindListener<KotlinQuestionBean>() {
            @Override
            public void done(List<KotlinQuestionBean> object, BmobException e) {
                if(e==null){
                    Log.i("bmob","查询成功：共"+object.size()+"条数据。");
                    itemnum[0] = object.size();
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
        androidQuery.findObjects(new FindListener<AndroidQuestionBean>() {
            @Override
            public void done(List<AndroidQuestionBean> object, BmobException e) {
                if(e==null){
                    Log.i("bmob","查询成功：共"+object.size()+"条数据。");
                    itemnum[1] = object.size();
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
        h5Query.findObjects(new FindListener<H5QuestionBean>() {
            @Override
            public void done(List<H5QuestionBean> object, BmobException e) {
                if(e==null){
                    Log.i("bmob","查询成功：共"+object.size()+"条数据。");
                    itemnum[2] = object.size();
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
        aiQuery.findObjects(new FindListener<AiQuestionBean>() {
            @Override
            public void done(List<AiQuestionBean> object, BmobException e) {
                if(e==null){
                    Log.i("bmob","查询成功：共"+object.size()+"条数据。");
                    itemnum[3] = object.size();
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }
}

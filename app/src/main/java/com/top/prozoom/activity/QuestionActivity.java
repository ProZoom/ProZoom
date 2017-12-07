package com.top.prozoom.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.top.proutils.StatusBarUtils;
import com.top.prozoom.R;
import com.top.prozoom.bean.AiQuestionBean;
import com.top.prozoom.bean.AndroidQuestionBean;
import com.top.prozoom.bean.H5QuestionBean;
import com.top.prozoom.bean.KotlinQuestionBean;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;



/*
* 作者：ProZoom
* 时间：2017/7/23-上午9:42
* 描述：
*/

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener {



    private Toolbar mToolbar;
    private TextView tv_title,tv_sum;

    private ViewPager vp_question;

    private List<View> viewList=new ArrayList<>();
    private viewpagerAdapter ad;
    private String str;//getString()返回指定key的值



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);


        initView();

        initBottomBar();

        getData();

        initData();
    }
    private void initView() {
        vp_question=(ViewPager) findViewById(R.id.vp_question);
        tv_sum=(TextView) findViewById(R.id.tv_sum);
        tv_title=(TextView) findViewById(R.id.tv_title);

        mToolbar= (Toolbar) findViewById(R.id.tl_question);
        StatusBarUtils.setColor(this,getResources().getColor(R.color.colorAccent),StatusBarUtils.DEFAULT_STATUS_BAR_ALPHA);


        Intent intent=getIntent();//getIntent将该项目中包含的原始intent检索出来，将检索出来的intent赋值给一个Intent类型的变量intent
        Bundle bundle=intent.getExtras();//.getExtras()得到intent所附带的额外数据
        str = bundle.getString("title");
        mToolbar.setTitle(str);

        // 设置toolbar
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       }
    }
    private void initBottomBar() {
        //iv_cellection= (ImageView) findViewById(R.id.iv_collection);

        //iv_cellection.setOnClickListener(this);

    }

    private void initData() {
        ad = new viewpagerAdapter();
        vp_question.setAdapter(ad);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /*
   * 描述：查询数据
   * @param
   * @return
   */
    public  void getData(){

        switch (str){
            case "Kotlin面试":
                Log.i("Join","kotlin");
                getKotlinDataFromBmob();
                break;
            case "Android面试":
                Log.i("Join","Android");
                getAndroidDataFromBmob();
                break;
            case "H5面试":
                Log.i("Join","h5");
                getH5DataFromBmob();
                break;
            case "AI面试":
                Log.i("Join","ai");
                getAiDataFromBmob();
                break;
        }




    }

    private void getKotlinDataFromBmob(){

        BmobQuery<KotlinQuestionBean> query = new BmobQuery<>();
        query.order("-createdAt");
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(1500);
        //执行查询方法

        query.findObjects(new FindListener<KotlinQuestionBean>() {
            @Override
            public void done(List<KotlinQuestionBean> object, BmobException e) {
                if(e==null){
                    Log.i("bmob","查询成功：共"+object.size()+"条数据。");

                    for (int i=0; i<object.size(); i++) {
                        View view=View.inflate(QuestionActivity.this,R.layout.question_layout,null);

                        TextView tv_question=  view.findViewById(R.id.tv_question);
                        TextView tv_anwser= view.findViewById(R.id.tv_anwser);

                        tv_question.setText(object.get(i).getQuestion());
                        tv_anwser.setMovementMethod(ScrollingMovementMethod.getInstance());
                        tv_anwser.setText(object.get(i).getAnswer());


                        viewList.add(view);
                    }
                    ad.notifyDataSetChanged();

                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }


    private void getAndroidDataFromBmob(){

        BmobQuery<AndroidQuestionBean> query = new BmobQuery<>();
        query.order("-createdAt");
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(1500);
        //执行查询方法

        query.findObjects(new FindListener<AndroidQuestionBean>() {
            @Override
            public void done(List<AndroidQuestionBean> object, BmobException e) {
                if(e==null){
                    Log.i("bmob","查询成功：共"+object.size()+"条数据。");

                    for (int i=0; i<object.size(); i++) {
                        View view=View.inflate(QuestionActivity.this,R.layout.question_layout,null);

                        TextView tv_question=  view.findViewById(R.id.tv_question);
                        TextView tv_anwser= view.findViewById(R.id.tv_anwser);

                        tv_question.setText(object.get(i).getQuestion());
                        tv_anwser.setMovementMethod(ScrollingMovementMethod.getInstance());
                        tv_anwser.setText(object.get(i).getAnswer());


                        viewList.add(view);
                    }
                    ad.notifyDataSetChanged();

                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }
    private void getH5DataFromBmob(){

        BmobQuery<H5QuestionBean> query = new BmobQuery<>();
        query.order("-createdAt");
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        //query.setLimit(50);
        //执行查询方法
        query.findObjects(new FindListener<H5QuestionBean>() {
            @Override
            public void done(List<H5QuestionBean> object, BmobException e) {
                if(e==null){
                    Log.i("bmob","查询成功：共"+object.size()+"条数据。");

                    for (int i=0; i<object.size(); i++) {
                        View view=View.inflate(QuestionActivity.this,R.layout.question_layout,null);

                        TextView tv_question=  view.findViewById(R.id.tv_question);
                        TextView tv_anwser= view.findViewById(R.id.tv_anwser);

                        tv_question.setText(object.get(i).getQuestion());
                        tv_anwser.setMovementMethod(ScrollingMovementMethod.getInstance());

                        tv_anwser.setText(object.get(i).getAnswer());

                        viewList.add(view);
                    }
                    ad.notifyDataSetChanged();

                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }
    private void getAiDataFromBmob(){

        BmobQuery<AiQuestionBean> query = new BmobQuery<>();
        query.order("-createdAt");
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        //query.setLimit(50);
        //执行查询方法
        query.findObjects(new FindListener<AiQuestionBean>() {
            @Override
            public void done(List<AiQuestionBean> object, BmobException e) {
                if(e==null){
                    Log.i("bmob","查询成功：共"+object.size()+"条数据。");

                    for (int i=0; i<object.size(); i++) {
                        View view=View.inflate(QuestionActivity.this,R.layout.question_layout,null);

                        TextView tv_question=  view.findViewById(R.id.tv_question);
                        TextView tv_anwser= view.findViewById(R.id.tv_anwser);

                        tv_question.setText(object.get(i).getQuestion());
                        tv_anwser.setMovementMethod(ScrollingMovementMethod.getInstance());

                        tv_anwser.setText(object.get(i).getAnswer());

                        viewList.add(view);
                    }
                    ad.notifyDataSetChanged();

                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

    private class viewpagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            tv_sum.setText((vp_question.getCurrentItem()+1)+"/"+viewList.size());

            return view==object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position));
            return viewList.get(position);
        }
    }
}

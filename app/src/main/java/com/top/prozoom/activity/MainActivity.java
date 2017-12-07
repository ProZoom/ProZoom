package com.top.prozoom.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.top.proutils.AppInfoUtils;
import com.top.proutils.StatusBarUtils;
import com.top.prozoom.R;
import com.top.prozoom.fragment.ITFragment;
import com.top.prozoom.fragment.UIFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.top.prozoom.R.id.updateWidgets;

public class MainActivity extends AppCompatActivity {


    private DrawerLayout mDrawerLayout;
    private Toolbar mToolBar;
    private ViewPager vp_main;
    private BottomNavigationView bottomNavigationView;
    private NavigationView navigationView;
    private View headerLayout;
    private ImageView head;
    private TextView myName;
    private ArrayList<Fragment> mFragmentList = new ArrayList<>();

    private static Tencent mTencent;
    private BaseUiListener mIUiListener;
    final private String QQAPPID="1106290418";
    private UserInfo mInfo;


    private String[][] plugins;
    //private Map<String,String> plugins=new LinkedHashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTencent = Tencent.createInstance(QQAPPID,MainActivity.this);

        initView();
        initData();
        initEvent();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mTencent.onActivityResultData(requestCode, resultCode, data, mIUiListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTencent.logout(MainActivity.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case updateWidgets:
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        mDrawerLayout=(DrawerLayout) findViewById(R.id.mDrawerLayout);
        mToolBar=(Toolbar) findViewById(R.id.toolbar);
        vp_main=(ViewPager) findViewById(R.id.vp_main);
        bottomNavigationView=(BottomNavigationView) findViewById(R.id.main_navigation);
        navigationView=(NavigationView) findViewById(R.id.navigation);
        headerLayout = navigationView.inflateHeaderView(R.layout.nav_header);
        head = headerLayout.findViewById(R.id.nav_header_img);
        myName = headerLayout.findViewById(R.id.nav_header_name);

        StatusBarUtils.setColorForDrawerLayout(this,mDrawerLayout,getResources().getColor(R.color.colorAccent),StatusBarUtils.DEFAULT_STATUS_BAR_ALPHA);
        mToolBar.setTitle("UI");
        setSupportActionBar(mToolBar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolBar, R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void initData() {

        mFragmentList.add(new UIFragment());
        mFragmentList.add(new ITFragment());

    }

    private void initEvent() {
        vp_main.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ui:
                        vp_main.setCurrentItem(0);
                        mToolBar.setTitle("UI");
                        break;
                    case R.id.mianshi:
                        vp_main.setCurrentItem(1);
                        mToolBar.setTitle("IT面试");
                        break;
                }
                return true;
            }
        });

        vp_main.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        bottomNavigationView.setSelectedItemId(R.id.ui);
                        break;
                    case 1:
                        bottomNavigationView.setSelectedItemId(R.id.mianshi);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

              navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.github:
                        //Toast.makeText(MainActivity.this, "Github", Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse("https://github.com/ProZoom/Blog/blob/master/Blog/Other/Plugins.md");
                        intent.setData(content_url);
                        startActivity(intent);
                        break;
                    case R.id.play:
                        //支付宝捐赠
                        if (AppInfoUtils.isApkInstalled(MainActivity.this, "com.eg.android.AlipayGphone")) {
                            Intent intent2 = new Intent();
                            intent2.setAction("android.intent.action.VIEW");
                            String alipayUrl = "HTTPS://QR.ALIPAY.COM/FKX004526F5IDY1I6VVA5F";
                            intent2.setData(Uri.parse("alipayqr://platformapi/startapp?saId=10000007&clientVersion=3.7.0.0718&qrcode=" + alipayUrl));
                            if (intent2.resolveActivity(MainActivity.this.getPackageManager()) != null) {
                                startActivity(intent2);
                            } else {
                                intent2.setData(Uri.parse(alipayUrl));
                                startActivity(intent2);
                            }
                        } else {
                            Toast.makeText(MainActivity.this,"手机上未安装支付宝!",Toast.LENGTH_LONG).show();
                        }
                        break;

                    case R.id.feedback:
                        Toast.makeText(MainActivity.this,""+AppInfoUtils.getAppVersion(MainActivity.this), Toast.LENGTH_SHORT).show();

                        Intent intent3=new Intent(MainActivity.this,FeedbackActivity.class);
                        startActivity(intent3);
                        break;

                    case R.id.share:
                        Intent intent1=new Intent(Intent.ACTION_SEND);
                        String ShareMessage="https://github.com/ProZoom/Blog";
                        intent1.putExtra(Intent.EXTRA_TEXT,ShareMessage);
                        intent1.setType("text/plain");
                        startActivity(Intent.createChooser(intent1,"share"));
                        break;
                }
                return true;
            }
        });

        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MainActivity.this, "img", Toast.LENGTH_SHORT).show();
                /**通过这句代码，SDK实现了QQ的登录，这个方法有三个参数，第一个参数是context上下文，第二个参数SCOPO 是一个String类型的字符串，表示一些权限
                 官方文档中的说明：应用需要获得哪些API的权限，由“，”分隔。例如：SCOPE = “get_user_info,add_t”；所有权限用“all”
                 第三个参数，是一个事件监听器，IUiListener接口的实例，这里用的是该接口的实现类 */
                mIUiListener = new BaseUiListener();

                if (!mTencent.isSessionValid()) {
                    mTencent.login(MainActivity.this,"all", mIUiListener);
                }else {
                    Toast.makeText(MainActivity.this,"已经登陆",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    /********** 双击退出程序 ************/
    private long exitTime=0;
    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        //判断是否是在1秒内连续点击返回键，是则退出，不是不退出
        if(System.currentTimeMillis()-exitTime>800){
            Toast.makeText(this,"再按一次退出程序",Toast.LENGTH_LONG).show();
            exitTime=System.currentTimeMillis();
            if(mDrawerLayout.isDrawerOpen(Gravity.LEFT))
                mDrawerLayout.closeDrawer(Gravity.LEFT);
        }else {
            super.onBackPressed();
        }
    }

    private class BaseUiListener implements IUiListener {
        @Override
        public void onComplete(Object response) {
            Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
            Log.i("tag", "response:" + response);

            //获取openid和token
            initOpenIdAndToken(response);
            //获取用户信息
            getUserInfo();
        }

        @Override
        public void onError(UiError uiError) {
            Toast.makeText(getApplicationContext(), "登录失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel() {
            Toast.makeText(getApplicationContext(), "登录取消", Toast.LENGTH_SHORT).show();
        }
    }

    private void initOpenIdAndToken(Object object) {
        JSONObject jb = (JSONObject) object;
        try {
            String openID = jb.getString("openid");  //openid用户唯一标识
            String access_token = jb.getString("access_token");
            String expires = jb.getString("expires_in");

            mTencent.setOpenId(openID);
            mTencent.setAccessToken(access_token, expires);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getUserInfo() {
        QQToken token = mTencent.getQQToken();
        mInfo = new UserInfo(MainActivity.this, token);
        mInfo.getUserInfo(new IUiListener() {
            @Override
            public void onComplete(Object object) {
                JSONObject jo = (JSONObject) object;
                Log.i("Login","登录成功："+object.toString());

                try {
                    String head_title = jo.getString("nickname");
                    String figureurl = jo.getString("figureurl_qq_2");

                    myName.setText(head_title);
                    Picasso.with(getApplicationContext()).load(figureurl).into(head);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i("Login","head_title");
                }
            }

            @Override
            public void onError(UiError uiError) {
                Log.i("Login","获取信息失败"+uiError.errorDetail);
            }

            @Override
            public void onCancel() {
            }
        });
    }
}

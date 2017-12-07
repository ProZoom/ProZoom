package com.top.prozoom.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.top.prozoom.R;
import com.top.prozoom.bean.userBean;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


public class FeedbackActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_feedback,et_phone;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        initView();

        initHeadBar();
    }

    private void initView() {
        et_feedback= (EditText) findViewById(R.id.et_feedback);
        et_phone= (EditText) findViewById(R.id.tv_phone);

        et_feedback.setOnClickListener(this);
        et_phone.setOnClickListener(this);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("反馈建议");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initHeadBar() {
        ImageView iv_add= (ImageView) findViewById(R.id.iv_add);
        Button btn_commit= (Button) findViewById(R.id.btn_commit);

        iv_add.setOnClickListener(this);
        btn_commit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_add:
                Toast.makeText(this,"尚未支持，敬请期待",Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_commit:
             if (TextUtils.isEmpty(et_feedback.getText())){
                 Toast.makeText(FeedbackActivity.this, "请输入你的反馈建议！", Toast.LENGTH_SHORT).show();
             }else {
                 bombInsertData();
             }
                break;
        }
    }

    private void bombInsertData() {
        userBean p = new userBean();
        p.setPhoneNumber(et_phone.getText().toString());
        p.setAnnex("附件");
        p.setFeedback(et_feedback.getText().toString());
        p.save(new SaveListener<String>() {
            @Override
            public void done(String objectId,BmobException e) {
                if(e==null){
                    Toast.makeText(FeedbackActivity.this,"添加数据成功",Toast.LENGTH_LONG).show();
                    //Toast.makeText(feedbackActivity.this,"添加数据成功，返回objectId为："+objectId,Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(FeedbackActivity.this,"创建数据失败：" + e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

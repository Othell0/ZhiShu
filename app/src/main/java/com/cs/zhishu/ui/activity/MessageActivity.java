package com.cs.zhishu.ui.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cs.zhishu.R;
import com.cs.zhishu.base.AbsBaseActivity;
import com.cs.zhishu.base.Bmob;
import com.cs.zhishu.model.zhishuMessage;
import com.facebook.stetho.common.LogUtil;

import butterknife.BindView;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Othell0 on 2016/7/2.
 */
public class MessageActivity extends AbsBaseActivity implements View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.feed_edit)
    EditText mFeedBack;
    @BindView(R.id.tip)
    TextView mTip;
    @BindView(R.id.btn_submit)
    Button mSubmit;


    @Override
    public int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
    /*初始化Bmob*/
        cn.bmob.v3.Bmob.initialize(this, Bmob.BMBO_KEY);
        mSubmit.setOnClickListener(this);
        mFeedBack.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTip.setText(String.valueOf(160 - s.length()));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle("意见反馈");
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

    }



    @Override
    public void initData() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_submit) {
            String text = mFeedBack.getText().toString().trim();
            if (TextUtils.isEmpty(text)) {
                Snackbar.make(mFeedBack, "假装看不到你的意见", Snackbar.LENGTH_SHORT).show();
                return;


            }
            sendFeedBackText(text);

        }

    }

    private void sendFeedBackText(String text) {
        zhishuMessage mMessage = new zhishuMessage();
        mMessage.setContent(text);
        mMessage.save(this, new SaveListener() {
            @Override
            public void onSuccess() {
                Snackbar.make(mFeedBack, "提交成功", Snackbar.LENGTH_SHORT).show();
                mFeedBack.setText("");
            }

            @Override
            public void onFailure(int errorCode, String errorMsg) {
                Snackbar.make(mFeedBack, "提交失败", Snackbar.LENGTH_SHORT).show();
                LogUtil.e(errorMsg);


            }
        });
    }
}




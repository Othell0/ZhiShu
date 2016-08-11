package com.cs.zhishu.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.alibaba.mobileim.FeedbackAPI;
import com.cs.zhishu.R;
import com.cs.zhishu.base.AbsBaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Othell0 on 2016/6/30.
 */
public class MoreActivity extends AbsBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    public int getLayoutId() {

        return R.layout.activity_more;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {


    }


    @Override
    public void initToolBar() {

        mToolbar.setTitle("更多选项");
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.more_btn_info)
    void startOthell0Info() {

        startActivity(new Intent(MoreActivity.this, Othell0InfoActivity.class));
    }


    @OnClick(R.id.more_btn_feed_back)
    void startFeedBack() {

        Intent intent = FeedbackAPI.getFeedbackActivityIntent();
        if (intent != null) {
            startActivity(intent);
        }

    }

    @OnClick(R.id.more_btn_setting)
    void startSetting() {

    }

    @OnClick(R.id.more_btn_about_app)
    void startAboutApp() {
        startActivity(new Intent(MoreActivity.this, AboutAppActivity.class));
    }


    @OnClick(R.id.more_btn_answer)
    void startTuling() {
        startActivity(new Intent(MoreActivity.this, TRClientActivity.class));
    }
}

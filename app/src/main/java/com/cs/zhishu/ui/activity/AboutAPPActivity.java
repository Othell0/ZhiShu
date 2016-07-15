package com.cs.zhishu.ui.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.cs.zhishu.R;
import com.cs.zhishu.base.AbsBaseActivity;
import com.github.lazylibrary.util.AppUtils;

import butterknife.BindView;

public class AboutAPPActivity extends AbsBaseActivity {


    @BindView(R.id.tv_version)
    TextView mVersionTv;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;


    @Override
    public int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {


    }


    @Override
    public void initToolBar() {
        setSupportActionBar(mToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null)
            supportActionBar.setDisplayHomeAsUpEnabled(true);

        mCollapsingToolbarLayout.setTitle("关于知书");


        String version = AppUtils.getVerName(this);
        mVersionTv.setText("版本号:" + "V" + "" + version);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}

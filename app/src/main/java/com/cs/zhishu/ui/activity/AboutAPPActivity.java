package com.cs.zhishu.ui.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cs.zhishu.R;
import com.cs.zhishu.base.AbsBaseActivity;
import com.github.lazylibrary.util.AppUtils;

import butterknife.BindView;

public class AboutAppActivity extends AbsBaseActivity {


    @BindView(R.id.tv_version)
    TextView mVersionTv;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.about_qrcode)
    ImageView aboutQrcode;


    @Override
    public int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        Glide.with(this)
                .load(R.drawable.qr_code)
                .into(aboutQrcode);
    }


    @Override
    public void initToolBar() {
        setSupportActionBar(mToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null)
            supportActionBar.setDisplayHomeAsUpEnabled(true);

        mCollapsingToolbarLayout.setTitle("关于知书");

        String version = AppUtils.getVerName(this);
        mVersionTv.setText(String.format("版本号:V%s", version));

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

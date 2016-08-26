package com.cs.zhishu.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.cs.zhishu.R;
import com.cs.zhishu.base.AbsBaseActivity;

import org.lzh.framework.updatepluginlib.UpdateBuilder;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends AbsBaseActivity {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;



    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle("通用设置");
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

    @OnClick(R.id.check_update)
    void check_update() {
         /*进行更新检查*/
        UpdateBuilder.create().check(this);
    }


}

package com.cs.zhishu.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.cs.zhishu.base.AbsBaseActivity;

/**
 * Created by Othell0 on 2016/7/21.
 */
public class ThemesDailyDetailsActivity extends AbsBaseActivity {
    private static final String EXTRA_TYPE = "extra_type";

    public static void Luanch(Activity activity, int id) {

        Intent mIntent = new Intent(activity, ThemesDailyDetailsActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mIntent.putExtra(EXTRA_TYPE, id);
        activity.startActivity(mIntent);
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void initToolBar() {

    }
}

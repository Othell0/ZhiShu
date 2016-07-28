package com.cs.zhishu.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.cs.zhishu.base.AbsBaseActivity;

/**
 * Created by Othell0 on 7/28/2016.
 */
public class DailyRecommendEditorsActivity extends AbsBaseActivity {
    private static final String EXTRA_ID = "extra_id";
    public static void luancher(Activity activity, int id) {
        Intent mIntent = new Intent(activity, DailyRecommendEditorsActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mIntent.putExtra(EXTRA_ID, id);
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

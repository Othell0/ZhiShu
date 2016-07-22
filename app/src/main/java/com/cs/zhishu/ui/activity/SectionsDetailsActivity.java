package com.cs.zhishu.ui.activity;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by exbbefl on 7/22/2016.
 */
public class SectionsDetailsActivity {
    private static final String EXTRA_ID = "extra_id";

    public static void Luanch(Activity activity, int id) {
        Intent mIntent = new Intent(activity, SectionsDetailsActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mIntent.putExtra(EXTRA_ID, id);
        activity.startActivity(mIntent);
    }
}

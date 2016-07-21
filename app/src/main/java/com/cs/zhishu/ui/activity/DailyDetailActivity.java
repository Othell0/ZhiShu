package com.cs.zhishu.ui.activity;

import android.content.Context;
import android.content.Intent;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by exbbefl on 7/21/2016.
 */
public class DailyDetailActivity extends SwipeBackActivity {
    private static final String EXTRA_ID = "extra_id";
    public static void lanuch(Context context, int id) {
        Intent mIntent = new Intent(context, DailyDetailActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mIntent.putExtra(EXTRA_ID, id);
        context.startActivity(mIntent);
    }
}

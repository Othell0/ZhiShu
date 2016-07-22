package com.cs.zhishu.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import com.cs.zhishu.model.DailyBean;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by exbbefl on 7/21/2016.
 */
public class DailyDetailActivity extends SwipeBackActivity {
    private static final String EXTRA_ID = "extra_id";
    private static final String EXTRA_DETAIL = "extra_detail";
    public static void lanuch(Context context, int id) {
        Intent mIntent = new Intent(context, DailyDetailActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mIntent.putExtra(EXTRA_ID, id);
        context.startActivity(mIntent);
    }
    public static void lanuch(Context context, DailyBean dailyBean)
    {

        Intent mIntent = new Intent(context, DailyDetailActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mIntent.putExtra(EXTRA_DETAIL, (Parcelable) dailyBean);
        context.startActivity(mIntent);
    }
}

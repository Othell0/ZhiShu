package com.cs.zhishu.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.cs.zhishu.base.AbsBaseActivity;

/**
 * Created by Othell0 on 7/28/2016.
 */
public class DailyCommentActivity extends AbsBaseActivity {
    private static final String EXTRA_ID = "comment_id";

    private static final String EXTRA_COMMENT_NUM = "comment_num";

    private static final String EXTRA_LONG_COMMENT_NUM = "long_comment_num";

    private static final String EXTRA_SHORT_COMMENT_NUM = "short_comment_num";
    public static void luancher(Activity activity, int id, int num, int longCommentNum, int shortCommentNum)
    {

        Intent mIntent = new Intent(activity, DailyCommentActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mIntent.putExtra(EXTRA_ID, id);
        mIntent.putExtra(EXTRA_COMMENT_NUM, num);
        mIntent.putExtra(EXTRA_LONG_COMMENT_NUM, longCommentNum);
        mIntent.putExtra(EXTRA_SHORT_COMMENT_NUM, shortCommentNum);
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

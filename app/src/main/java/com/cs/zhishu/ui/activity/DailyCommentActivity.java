package com.cs.zhishu.ui.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.cs.zhishu.R;
import com.cs.zhishu.base.AbsBaseActivity;
import com.cs.zhishu.ui.fragment.LongCommentFragment;
import com.cs.zhishu.ui.fragment.ShortCommentFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Othell0 on 7/28/2016.
 */
public class DailyCommentActivity extends AbsBaseActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.sliding_tabs)
    TabLayout mSlidingTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private List<String> titles = new ArrayList<>();

    private List<Fragment> fragmentList = new ArrayList<>();

    private static final String EXTRA_ID = "comment_id";

    private static final String EXTRA_COMMENT_NUM = "comment_num";

    private static final String EXTRA_LONG_COMMENT_NUM = "long_comment_num";

    private static final String EXTRA_SHORT_COMMENT_NUM = "short_comment_num";

    private int id;

    private int commentNum;

    private int longCommentNum;

    private int shortCommentNum;

    @Override
    public int getLayoutId() {
        return R.layout.activity_daily_comment;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getIntExtra(EXTRA_ID, -1);
            commentNum = intent.getIntExtra(EXTRA_COMMENT_NUM, 0);
            longCommentNum = intent.getIntExtra(EXTRA_LONG_COMMENT_NUM, 0);
            shortCommentNum = intent.getIntExtra(EXTRA_SHORT_COMMENT_NUM, 0);

            Log.e("initViews:", " id + \"~~~\" + commentNum + \"~~~\" + longCommentNum + \"~~~\" + shortCommentNum");
        }

        titles.add("长评论" + " (" + longCommentNum + ")");
        titles.add("短评论" + " (" + shortCommentNum + ")");

        LongCommentFragment longCommentFragment = LongCommentFragment.newInstance(id);
        ShortCommentFragment shortCommentFragment = ShortCommentFragment.newInstance(id);
        fragmentList.add(longCommentFragment);
        fragmentList.add(shortCommentFragment);

        CommentPagerAdapter mAdapter = new CommentPagerAdapter(getFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mSlidingTabLayout.setupWithViewPager(mViewPager);


    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle(commentNum + "  条点评");
        setSupportActionBar(mToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null)
            supportActionBar.setDisplayHomeAsUpEnabled(true);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public static void launcher(Activity activity, int id, int num, int longCommentNum, int shortCommentNum) {

        Intent mIntent = new Intent(activity, DailyCommentActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mIntent.putExtra(EXTRA_ID, id);
        mIntent.putExtra(EXTRA_COMMENT_NUM, num);
        mIntent.putExtra(EXTRA_LONG_COMMENT_NUM, longCommentNum);
        mIntent.putExtra(EXTRA_SHORT_COMMENT_NUM, shortCommentNum);
        activity.startActivity(mIntent);
    }

    public class CommentPagerAdapter extends FragmentStatePagerAdapter {


        public CommentPagerAdapter(FragmentManager fm) {

            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return fragmentList.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return titles.get(position);
        }

        @Override
        public int getCount() {

            return titles.size();
        }
    }


}

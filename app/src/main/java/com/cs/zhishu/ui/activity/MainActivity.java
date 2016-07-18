package com.cs.zhishu.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.cs.zhishu.R;
import com.cs.zhishu.base.AbsBaseActivity;
import com.cs.zhishu.ui.fragment.DailyListFragment;
import com.cs.zhishu.ui.fragment.HotNewsFragment;
import com.cs.zhishu.ui.fragment.SectionsFragment;
import com.cs.zhishu.ui.fragment.ThemesDailyFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/*知书主界面*/

public class MainActivity extends AbsBaseActivity {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private BottomBar mBottomBar;

    private List<Fragment> fragments = new ArrayList<>();


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

        fragments.add(DailyListFragment.newInstance());
        fragments.add(ThemesDailyFragment.newInstance());
        fragments.add(SectionsFragment.newInstance());
        fragments.add(HotNewsFragment.newInstance());
        mBottomBar = BottomBar.attach(this, savedInstanceState);


        initBottomBar();
     /*   initViewPager();*/

    }

    private void initBottomBar() {

        mBottomBar.setItems(R.menu.bottombar_menu);
        mBottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {


            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                switch (menuItemId) {
                    case R.id.bb_menu_daily:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.bb_menu_theme:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.bb_menu_expert:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.bb_menu_article:
                        viewPager.setCurrentItem(3);
                        break;

                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {

            }
        });
        // Setting colors for different tabs when there's more than three of them.
        // You can set colors for tabs in three different ways as shown below.
        mBottomBar.mapColorForTab(0, ContextCompat.getColor(this, R.color.colorAccent));
        mBottomBar.mapColorForTab(1, 0xFF5D4037);
        mBottomBar.mapColorForTab(2, "#7B1FA2");
        mBottomBar.mapColorForTab(3, ContextCompat.getColor(this, R.color.colord630d984));
    }


    /*private void initViewPager() {
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mBottomBar.selectTabAtPosition(position, true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
*/

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Necessary to restore the BottomBar's state, otherwise we would
        // lose the current tab on orientation change.
        mBottomBar.onSaveInstanceState(outState);
    }


    @Override
    public void initToolBar() {

        mToolbar.setTitle("知书");
        setSupportActionBar(mToolbar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_mode:
                //切换日夜间模式
                mNightModeHelper.toggle();
                return true;

            case R.id.action_settings:
                //设置
                startActivity(new Intent(this, MoreActivity.class));
                return true;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}



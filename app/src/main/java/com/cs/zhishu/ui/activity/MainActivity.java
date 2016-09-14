package com.cs.zhishu.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.cs.zhishu.R;
import com.cs.zhishu.base.AbsBaseActivity;
import com.cs.zhishu.ui.fragment.DailyListFragment;
import com.cs.zhishu.ui.fragment.HotNewsFragment;
import com.cs.zhishu.ui.fragment.SectionsFragment;
import com.cs.zhishu.ui.fragment.ThemesDailyFragment;
import com.cs.zhishu.ui.fragment.WeatherFragment;
import com.cs.zhishu.util.DayNight;
import com.cs.zhishu.util.DayNightHelper;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;

import static java.lang.System.currentTimeMillis;


/*知书主界面*/

public class MainActivity extends AbsBaseActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.bottom_navigation)
    AHBottomNavigation mAhBottomNavigation;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;
    private List<Fragment> fragments = new LinkedList<>();
    private int currentTabIndex;
    private long exitTime = 0;
    private ActionBarDrawerToggle mDrawerToggle;


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        initDrawerLayout();

        fragments.add(DailyListFragment.newInstance());
        fragments.add(ThemesDailyFragment.newInstance());
        fragments.add(SectionsFragment.newInstance());
        fragments.add(HotNewsFragment.newInstance());

        showFragment(fragments.get(0));
        initBottomNav();
    }

    private void initDrawerLayout() {
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, mToolbar, R.string.drawer_open,
                R.string.drawer_close);
        mDrawerToggle.syncState();
        drawerLayout.addDrawerListener(mDrawerToggle);
        setupDrawerContent(mNavigationView);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.navigation_item_weather:
                                getSupportFragmentManager().beginTransaction().replace(R.id.content, new WeatherFragment()).commit();
                                mToolbar.setTitle(R.string.navigation_weather);
                                break;

                            case R.id.navigation_item_news:
                                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                                startActivity(intent);
                                mToolbar.setTitle(R.string.find_news);
                                break;
                        }

                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    private void showFragment(Fragment fragment) {
        getFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
    }

    private void initBottomNav() {
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("日报", R.drawable.ic_profile_answer, R.color.colorPrimary);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("主题", R.drawable.ic_profile_article, R.color.colorPrimary);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("专栏", R.drawable.ic_profile_column, R.color.colorPrimary);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem("文章", R.drawable.ic_profile_favorite, R.color.colorPrimary);

        mAhBottomNavigation.addItem(item1);
        mAhBottomNavigation.addItem(item2);
        mAhBottomNavigation.addItem(item3);
        mAhBottomNavigation.addItem(item4);

        mAhBottomNavigation.setBehaviorTranslationEnabled(true);
        mAhBottomNavigation.setAccentColor(getResources().getColor(R.color.colorPrimary));
        mAhBottomNavigation.setInactiveColor(getResources().getColor(R.color.nav_text_color_mormal));
        mAhBottomNavigation.setCurrentItem(0);

        mAhBottomNavigation.setBehaviorTranslationEnabled(true);
        mAhBottomNavigation.setDefaultBackgroundColor(getResources().getColor(R.color.bg_color));


        mAhBottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {

            @Override
            public void onTabSelected(int position, boolean wasSelected) {

                if (currentTabIndex != position) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.hide(fragments.get(currentTabIndex));
                    if (!fragments.get(position).isAdded()) {
                        ft.add(R.id.content, fragments.get(position));
                    }
                    ft.show(fragments.get(position)).commit();
                }
                currentTabIndex = position;
            }
        });
    }


    @Override
    public void initToolBar() {
        mToolbar.setTitle("知书");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);  //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                //设置
                startActivity(new Intent(this, MoreActivity.class));
                return true;

//            切换日夜间模式
/*            case R.id.action_mode:
                showAnimation();
                toggleThemeSetting();
                refreshUI();
                return true;*/
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void refreshUI() {
        TypedValue background = new TypedValue();//背景色
        TypedValue textColor = new TypedValue();//字体颜色
        Resources.Theme theme = getTheme();
        theme.resolveAttribute(R.attr.clockBackground, background, true);
        theme.resolveAttribute(R.attr.clockTextColor, textColor, true);


        refreshStatusBar();
    }

    private void refreshStatusBar() {
        if (Build.VERSION.SDK_INT >= 21) {
            TypedValue typedValue = new TypedValue();
            Resources.Theme theme = getTheme();
            theme.resolveAttribute(R.attr.colorPrimary, typedValue, true);
            getWindow().setStatusBarColor(getResources().getColor(typedValue.resourceId));
        }
    }

    private void toggleThemeSetting() {
        DayNightHelper mDayNightHelper = new DayNightHelper(this);
        if (mDayNightHelper.isDay()) {
            mDayNightHelper.setMode(DayNight.NIGHT);
            setTheme(R.style.NightTheme);
        } else {
            mDayNightHelper.setMode(DayNight.DAY);
            setTheme(R.style.DayTheme);
        }
    }

    private Bitmap getCacheBitmapFromView(View view) {
        final boolean drawingCacheEnabled = true;
        view.setDrawingCacheEnabled(drawingCacheEnabled);
        view.buildDrawingCache(drawingCacheEnabled);
        final Bitmap drawingCache = view.getDrawingCache();
        Bitmap bitmap;
        if (drawingCache != null) {
            bitmap = Bitmap.createBitmap(drawingCache);
            view.setDrawingCacheEnabled(false);
        } else {
            bitmap = null;
        }
        return bitmap;
    }

    private void showAnimation() {
        final View decorView = getWindow().getDecorView();
        Bitmap cacheBitmap = getCacheBitmapFromView(decorView);
        if (decorView instanceof ViewGroup && cacheBitmap != null) {
            final View view = new View(this);
            view.setBackgroundDrawable(new BitmapDrawable(getResources(), cacheBitmap));
            ViewGroup.LayoutParams layoutParam = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            ((ViewGroup) decorView).addView(view, layoutParam);
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);
            objectAnimator.setDuration(300);
            objectAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    ((ViewGroup) decorView).removeView(view);
                }
            });
            objectAnimator.start();
        }
    }


    @Override
    public void onBackPressed() {
        if (currentTimeMillis() - exitTime > 2000) {
            Snackbar.make(drawerLayout, "再按一次退出程序", Snackbar.LENGTH_SHORT).show();
            exitTime = currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }


}



package com.cs.zhishu.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cs.zhishu.R;
import com.cs.zhishu.util.DayNightHelper;
import com.cs.zhishu.util.StatusBarCompat;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Othell0 on 2016/6/29.
 */
public abstract class AbsBaseActivity extends AppCompatActivity {

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        initTheme();
        //设置布局内容
        setContentView(getLayoutId());
        //初始化黄油刀控件绑定框架
        ButterKnife.bind(this);
        //适配4.4状态栏
        StatusBarCompat.compat(this);
        //初始化控件
        initViews(savedInstanceState);
        //初始化ToolBar
        initToolBar();
    }

    private void initTheme() {
        DayNightHelper mDayNightHelper = new DayNightHelper(this);
        if (mDayNightHelper.isDay()) {
            setTheme(R.style.DayTheme);
        } else {
            setTheme(R.style.NightTheme);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder = ButterKnife.bind(this);
        unbinder.unbind();

    }


    public abstract int getLayoutId();

    public abstract void initViews(Bundle savedInstanceState);

    public abstract void initToolBar();


}
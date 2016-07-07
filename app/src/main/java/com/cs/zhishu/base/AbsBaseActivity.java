package com.cs.zhishu.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cs.zhishu.util.NightModeHelper;
import com.cs.zhishu.util.StatusBarCompat;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Othell0 on 2016/6/29.
 */
public abstract class AbsBaseActivity extends AppCompatActivity {

    public NightModeHelper mNightModeHelper;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
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
        //初始化日夜间模式切换帮助类
        mNightModeHelper = new NightModeHelper(this);

        //加载数据
        initData();
    }

    @Override
    protected void onDestroy() {
        unbinder = ButterKnife.bind(this);
        super.onDestroy();
        unbinder.unbind();

    }


    public abstract int getLayoutId();


    public abstract void initViews(Bundle savedInstanceState);

    public abstract void initToolBar();

    public abstract void initData();
}
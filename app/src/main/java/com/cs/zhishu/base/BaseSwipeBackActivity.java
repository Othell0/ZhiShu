package com.cs.zhishu.base;

import android.os.Bundle;

import com.cs.zhishu.widget.SwipeBackActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.imid.swipebacklayout.lib.SwipeBackLayout;


/**
 * Created by Othell0 on 7/28/2016.
 */
public abstract class BaseSwipeBackActivity extends SwipeBackActivity {
    public SwipeBackLayout mSwipeBackLayout;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置布局内容
        setContentView(getLayoutId());
        //初始化黄油刀控件绑定框架
        ButterKnife.bind(this);
        //初始化侧滑返回layout
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        //初始化控件
        initViews(savedInstanceState);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder = ButterKnife.bind(this);
        unbinder.unbind();
    }

    public abstract int getLayoutId();

    public abstract void initViews(Bundle savedInstanceState);
}

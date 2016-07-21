package com.cs.zhishu.ui.fragment;

import com.cs.zhishu.R;
import com.cs.zhishu.base.LazyFragment;

/**
 * Created by Othell0 on 2016/7/18.
 */
public class ThemesDailyFragment extends LazyFragment {
    public static ThemesDailyFragment newInstance()
    {

        ThemesDailyFragment mThemesDailyFragment = new ThemesDailyFragment();
        return mThemesDailyFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_themes_daily;
    }

    @Override
    public void initViews() {

    }
}

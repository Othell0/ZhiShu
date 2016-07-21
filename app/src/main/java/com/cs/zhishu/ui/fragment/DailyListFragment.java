package com.cs.zhishu.ui.fragment;

import com.cs.zhishu.R;
import com.cs.zhishu.base.LazyFragment;

/**
 * Created by Othell0 on 2016/7/18.
 */
public class DailyListFragment extends LazyFragment {
    public static DailyListFragment newInstance()
    {

        return new DailyListFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_daily_list;
    }

    @Override
    public void initViews() {

    }
}

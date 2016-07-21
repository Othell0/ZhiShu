package com.cs.zhishu.ui.fragment;

import com.cs.zhishu.R;
import com.cs.zhishu.base.LazyFragment;

/**
 * Created by Othell0 on 2016/7/18.
 */
public class SectionsFragment extends LazyFragment {
    public static SectionsFragment newInstance()
    {

        SectionsFragment mSectionsFragment = new SectionsFragment();
        return mSectionsFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_sections;
    }

    @Override
    public void initViews() {

    }
}

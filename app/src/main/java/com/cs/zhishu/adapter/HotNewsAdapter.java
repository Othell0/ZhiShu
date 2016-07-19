package com.cs.zhishu.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cs.zhishu.R;

/**
 * Created by exbbefl on 7/19/2016.
 */
public class HotNewsAdapter extends BaseQuickAdapter<Status> {
    public QuickAdapter() {
        super(R.layout.tweet, DataServer.getSampleData());
    }


    @Override
    protected void convert(BaseViewHolder baseViewHolder, Status item) {

    }
}

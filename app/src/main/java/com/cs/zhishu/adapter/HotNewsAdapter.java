package com.cs.zhishu.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cs.zhishu.R;
import com.cs.zhishu.model.HotNews;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by exbbefl on 7/19/2016.
 */
public class HotNewsAdapter extends BaseQuickAdapter<HotNews.HotNewsInfo> {

    private List<HotNews.HotNewsInfo> hotNewsInfos = new ArrayList<>();

    public HotNewsAdapter(List<HotNews.HotNewsInfo> hotNewsInfos) {
        super(R.layout.item_hot_news, hotNewsInfos);
    }


    @Override
    protected void convert(BaseViewHolder baseViewHolder, HotNews.HotNewsInfo item) {
        baseViewHolder.setText(R.id.item_des, item.title);
        Glide.with(mContext).load(item.thumbnail).crossFade().placeholder(R.drawable.account_avatar).into((ImageView) baseViewHolder.getView(R.id.item_img));

    }


}

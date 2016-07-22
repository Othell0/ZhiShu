package com.cs.zhishu.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cs.zhishu.R;
import com.cs.zhishu.model.DailySections;

import java.util.List;

/**
 * Created by exbbefl on 7/22/2016.
 */
public class SectionsAdapter extends BaseQuickAdapter<DailySections.DailySectionsInfo> {




    public SectionsAdapter(List<DailySections.DailySectionsInfo> sectionsInfos) {
        super(R.layout.item_sections, sectionsInfos);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, DailySections.DailySectionsInfo dailySectionsInfo) {
        Glide.with(mContext)
                .load(dailySectionsInfo.thumbnail)
                .placeholder(R.drawable.account_avatar)
                .into((ImageView) baseViewHolder.getView(R.id.item_img));
        baseViewHolder.setText(R.id.item_des,dailySectionsInfo.description);
        baseViewHolder.setText(R.id.item_name,dailySectionsInfo.name);


    }
}

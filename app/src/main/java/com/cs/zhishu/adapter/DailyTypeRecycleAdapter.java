package com.cs.zhishu.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cs.zhishu.R;
import com.cs.zhishu.model.DailyTypeBean;

import java.util.List;

/**
 * Created by Othell0 on 2016/7/21.
 */
public class DailyTypeRecycleAdapter extends BaseQuickAdapter<DailyTypeBean.SubjectDaily> {
    public DailyTypeRecycleAdapter(List<DailyTypeBean.SubjectDaily> others) {
        super(R.layout.item_themes_daily, others);
    }
    @Override
    protected void convert(BaseViewHolder baseViewHolder, DailyTypeBean.SubjectDaily item) {

        baseViewHolder.setText(R.id.item_type_name, item.getName());

        Glide.with(mContext)
                .load(item.getThumbnail())
                .placeholder(R.drawable.account_avatar)
                .crossFade(3000)
                .into((ImageView) baseViewHolder.getView(R.id.item_type_img));

        baseViewHolder.setText(R.id.item_type_des, item.getDescription());



    }
}

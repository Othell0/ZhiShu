package com.cs.zhishu.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cs.zhishu.R;
import com.cs.zhishu.model.DailyRecommend;

import java.util.List;

/**
 * Created by Othell0 on 2016/7/28.
 */
public class RecommendEditorAdapter extends BaseQuickAdapter<DailyRecommend.Editor> {
    public RecommendEditorAdapter(List<DailyRecommend.Editor> editors) {
        super(R.layout.item_recommend_editors,editors);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, DailyRecommend.Editor editor) {

        Glide.with(mContext)
                .load(editor.avatar)
                .placeholder(R.drawable.account_avatar)
                .into((ImageView) baseViewHolder.getView(R.id.user_pic));

        baseViewHolder.setText(R.id.user_name, editor.name);
        baseViewHolder.setText(R.id.user_weibo, editor.wb);
        baseViewHolder.setText(R.id.user_title, editor.title);

    }
}

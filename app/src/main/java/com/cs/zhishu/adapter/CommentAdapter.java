package com.cs.zhishu.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cs.zhishu.R;
import com.cs.zhishu.model.DailyComment;
import com.cs.zhishu.util.DateUtil;

import java.util.List;

/**
 * Created by Othell0 on 7/29/2016.
 */
public class CommentAdapter extends BaseQuickAdapter<DailyComment.CommentInfo> {
    public CommentAdapter(List<DailyComment.CommentInfo> commentInfo) {
        super(R.layout.item_comment,commentInfo);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, DailyComment.CommentInfo commentInfo) {
        Glide.with(mContext)
                .load(commentInfo.avatar)
                .placeholder(R.drawable.account_avatar)
                .crossFade(3000)
                .into((ImageView) baseViewHolder.getView(R.id.user_pic));

        baseViewHolder.setText(R.id.user_name, commentInfo.author);
        baseViewHolder.setText(R.id.user_parise_num, commentInfo.likes+"");
        baseViewHolder.setText(R.id.user_content, commentInfo.content);
        baseViewHolder.setText(R.id.user_time, DateUtil.getTime(commentInfo.time));
    }
}

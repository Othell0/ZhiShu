package com.cs.zhishu.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.cs.zhishu.R;
import com.cs.zhishu.model.Stories;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Othell0 on 2016/7/31.
 */
public class ThemesDetailsStoriesAdapter extends AbsRecyclerViewAdapter
{

    private List<Stories> stories = new ArrayList<>();

    public ThemesDetailsStoriesAdapter(RecyclerView recyclerView, List<Stories> stories)
    {

        super(recyclerView);
        this.stories = stories;
    }

    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_themes_stories, parent, false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position)
    {

        super.onBindViewHolder(holder, position);
        if (holder instanceof ItemViewHolder)
        {
            ItemViewHolder mItemViewHolder = (ItemViewHolder) holder;
            mItemViewHolder.mTitle.setText(stories.get(position).getTitle());
            if(stories.get(position).getImages() != null)
            {
                Glide.with(getContext())
                        .load(stories.get(position).getImages().get(0))
                        .placeholder(R.drawable.account_avatar)
                        .crossFade(3000)
                        .into(mItemViewHolder.mImg);
            }
            else
            {
                mItemViewHolder.mImg.setVisibility(View.GONE);
            }


        }
    }

    @Override
    public int getItemCount()
    {

        return stories.size();
    }

    public class ItemViewHolder extends AbsRecyclerViewAdapter.ClickableViewHolder
    {

        public ImageView mImg;

        public TextView mTitle;

        public ItemViewHolder(View itemView)
        {

            super(itemView);
            mImg = $(R.id.item_image);
            mTitle = $(R.id.item_title);
        }
    }
}

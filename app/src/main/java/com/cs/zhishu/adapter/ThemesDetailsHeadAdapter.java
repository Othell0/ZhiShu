package com.cs.zhishu.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.cs.zhishu.R;
import com.cs.zhishu.model.Editors;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Othell0 on 2016/7/31.
 */
public class ThemesDetailsHeadAdapter extends AbsRecyclerViewAdapter
{

    private List<Editors> editors = new ArrayList<>();

    public ThemesDetailsHeadAdapter(RecyclerView recyclerView, List<Editors> editors)
    {

        super(recyclerView);
        this.editors = editors;
    }

    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_themes_details_editors, parent, false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position)
    {


        if (holder instanceof ItemViewHolder)
        {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            Glide.with(getContext())
                    .load(editors.get(position).getAvatar())
                    .placeholder(R.drawable.account_avatar)
                    .crossFade(3000)
                    .into(itemViewHolder.mPic);
        }

        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount()
    {

        return editors.size();
    }

    public class ItemViewHolder extends AbsRecyclerViewAdapter.ClickableViewHolder
    {

        public CircleImageView mPic;

        public ItemViewHolder(View itemView)
        {

            super(itemView);
            mPic = $(R.id.editor_pic);
        }
    }
}


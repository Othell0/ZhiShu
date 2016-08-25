package com.cs.zhishu.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cs.zhishu.R;
import com.cs.zhishu.model.TopDailies;
import com.cs.zhishu.ui.activity.DailyDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Othell0 on 7/26/2016.
 */
public class MainViewPagerAdapter extends PagerAdapter {

    private List<TopDailies> tops = new ArrayList<>();
    private Context context;

    public MainViewPagerAdapter(Context context, List<TopDailies> tops) {
        this.context = context;
        this.tops = tops;

    }

    @Override
    public int getCount() {
        return tops.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.pager_item, container, false);
        ImageView mImg = (ImageView) view.findViewById(R.id.pager_img);
        TextView mTitle = (TextView) view.findViewById(R.id.pager_title);
        TopDailies mTopDailies = tops.get(position);
        Glide.with(context)
                .load(mTopDailies.getImage())
                .into(mImg);
        mTitle.setText(mTopDailies.getTitle());
        final int id = mTopDailies.getId();
        view.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                DailyDetailActivity.launch(context, id);
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}

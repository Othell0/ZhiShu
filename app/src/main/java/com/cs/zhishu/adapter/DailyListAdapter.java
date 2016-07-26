package com.cs.zhishu.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cs.zhishu.R;
import com.cs.zhishu.db.DailyDao;
import com.cs.zhishu.model.DailyBean;
import com.cs.zhishu.ui.activity.DailyDetailActivity;
import com.cs.zhishu.util.WeekUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Othell0 on 7/22/2016.
 */
public class DailyListAdapter extends BaseMultiItemQuickAdapter<DailyBean> {
    private static final int ITEM_CONTENT = 0;
    private static final int ITEM_TIME = 1;
    private List<DailyBean> dailys = new ArrayList<>();
    private Context mContext;
    private DailyDao mDailyDao;

    public DailyListAdapter(Context context,List<DailyBean> dailys) {

        super(dailys);
        this.mContext = context;
        this.mDailyDao = new DailyDao(context);
        addItemType(ITEM_TIME, R.layout.item_daily_list_time);
        addItemType(ITEM_CONTENT, R.layout.item_daily_list);
    }

    @Override
    protected int getDefItemViewType(int position) {
        if (position == 0) {
            return ITEM_TIME;
        }
        String time = dailys.get(position).getDate();
        int index = position - 1;
        boolean isDifferent = !dailys.get(index).getDate().equals(time);
        int pos = isDifferent ? ITEM_TIME : ITEM_CONTENT;

        return pos;
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return super.onCreateDefViewHolder(parent, viewType);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, DailyBean dailyBean) {
        setDailyDate(baseViewHolder, dailyBean);
        String timeStr = "";
        switch (baseViewHolder.getItemViewType()) {
            case ITEM_TIME:
                timeStr = "今日热闻";
                break;
            case ITEM_CONTENT:
                timeStr = com.cs.zhishu.util.DateUtil.formatDate(dailyBean.getDate()) + "  " + WeekUtil.getWeek(dailyBean.getDate());
                break;

        }
        baseViewHolder.setText(R.id.item_time, timeStr);

    }


    /**
     * 设置数据给普通内容Item
     *
     * @param baseViewHolder
     * @param dailyBean
     */

    private void setDailyDate(final BaseViewHolder baseViewHolder, final DailyBean dailyBean) {
        List<String> images = dailyBean.getImages();

        baseViewHolder.setText(R.id.item_title, dailyBean.getTitle());
        if (images != null && images.size() > 0) {
            Glide.with(mContext)
                    .load(images.get(0))
                    .placeholder(R.drawable.account_avatar)
                    .into((ImageView) baseViewHolder.getView(R.id.item_img));
        }
        if (dailyBean.isMultipic()){
            baseViewHolder.getView(R.id.item_more_pic).setVisibility(View.VISIBLE);
        }else {
            baseViewHolder.getView(R.id.item_more_pic).setVisibility(View.GONE);

        }

        if (dailyBean.isRead()){
            baseViewHolder.setTextColor(R.id.item_title, ContextCompat.getColor(mContext,R.color.color_read));
        }else {
            baseViewHolder.setTextColor(R.id.item_title, ContextCompat.getColor(mContext,R.color.color_unread));

        }
        baseViewHolder.setOnClickListener(R.id.card_view, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: CardView");
                if (!dailyBean.isRead())
                {
                    dailyBean.setRead(true);
                    baseViewHolder.setTextColor(R.id.item_title, ContextCompat.getColor(mContext,R.color.color_read));
                    new Thread(new Runnable()
                    {

                        @Override
                        public void run()
                        {

                            mDailyDao.insertReadNew(dailyBean.getId() + "");
                        }
                    }).start();
                }
                //跳转到详情界面
                DailyDetailActivity.lanuch(mContext, dailyBean);

            }
        });

    }
    public void updateData(List<DailyBean> dailys)
    {

        this.dailys = dailys;
        notifyDataSetChanged();
    }



    public void addDatas(List<DailyBean> dailys)
    {

        if (this.dailys == null)
        {
            updateData(dailys);
        } else
        {
            this.dailys.addAll(dailys);
            notifyDataSetChanged();
        }
    }









}

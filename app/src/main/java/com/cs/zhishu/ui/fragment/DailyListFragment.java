package com.cs.zhishu.ui.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cs.zhishu.R;
import com.cs.zhishu.adapter.AutoLoadOnScrollListener;
import com.cs.zhishu.adapter.DailyListAdapter;
import com.cs.zhishu.base.LazyFragment;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

import butterknife.BindView;

/**
 * Created by Othell0 on 2016/7/18.
 * 日报列表界面
 */
public class DailyListFragment extends LazyFragment {
    @BindView(R.id.daily_recycle)
    RecyclerView mRecyclerView;
    @BindView(R.id.circle_progress)
    CircularProgressView mCircleProgressView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            super.handleMessage(msg);
            if (msg.what == 0) {
                getLatesDailys(true);
            } else if (msg.what == 1) {
                hideProgress();
                mSwipeRefreshLayout.setRefreshing(false);
                afterGetDaily();
            }
        }
    };
    private DailyListAdapter mAdapter;
    private AutoLoadOnScrollListener mAutoLoadOnScrollListener;

    private void afterGetDaily() {

    }

    private void hideProgress() {

    }

    private void getLatesDailys(boolean b) {
    }

    public static DailyListFragment newInstance() {

        return new DailyListFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_daily_list;
    }

    @Override
    public void initViews() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {

                mHandler.sendEmptyMessageDelayed(0, 1000);
            }
        });

        mAdapter = new DailyListAdapter(getActivity(), dailys);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAutoLoadOnScrollListener = new AutoLoadOnScrollListener(mLinearLayoutManager)
        {

            @Override
            public void onLoadMore(int currentPage)
            {

                loadMoreDaily(currentTime);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {

                super.onScrolled(recyclerView, dx, dy);
                //int firstPos = (recyclerView == null || recyclerView.getChildCount() == 0 ? 0 : recyclerView.getChildAt(0).getTop());

                mSwipeRefreshLayout.setEnabled(mLinearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0);
            }
        };

    }


}

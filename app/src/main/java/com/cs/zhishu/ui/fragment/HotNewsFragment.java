package com.cs.zhishu.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.cs.zhishu.R;
import com.cs.zhishu.adapter.HotNewsAdapter;
import com.cs.zhishu.base.LazyFragment;
import com.cs.zhishu.model.HotNews;
import com.cs.zhishu.network.RetrofitHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Othell0 on 2016/7/18.
 */
public class HotNewsFragment extends LazyFragment {
    @BindView(R.id.recycle)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private List<HotNews.HotNewsInfo> hotNewsInfos = new ArrayList<>();

    public static HotNewsFragment newInstance() {
        HotNewsFragment mHotNewsFragment = new HotNewsFragment();
        return mHotNewsFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_hot_news;
    }

    @Override
    public void initViews() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {

                getHotNews();
            }
        });
        getHotNews();

    }

    private void getHotNews() {
        RetrofitHelper.getLastZhiHuApi().getHotNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HotNews>() {
                    @Override
                    public void onCompleted() {
                        Log.e("666", "onCompleted");

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HotNews hotNews) {
                        List<HotNews.HotNewsInfo> recent = hotNews.recent;
                        hotNewsInfos.clear();
                        hotNewsInfos.addAll(recent);
                        afterGetHotNews();
                        mSwipeRefreshLayout.setRefreshing(false);

                    }
                });


    }

    private void afterGetHotNews() {
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        HotNewsAdapter mAdapter = new HotNewsAdapter(mRecyclerView, hotNewsInfos);

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener()
        {

            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder)
            {

                HotNews.HotNewsInfo hotNewsInfo = hotNewsInfos.get(position);
                DailyDetailActivity.lanuch(getActivity(), hotNewsInfo.newsId);
            }
        });
    }

    }
}

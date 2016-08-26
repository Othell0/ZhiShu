package com.cs.zhishu.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cs.zhishu.R;
import com.cs.zhishu.adapter.SectionsAdapter;
import com.cs.zhishu.base.LazyFragment;
import com.cs.zhishu.model.DailySections;
import com.cs.zhishu.network.RetrofitHelper;
import com.cs.zhishu.ui.activity.SectionsDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Othell0 on 2016/7/18.
 */
public class SectionsFragment extends LazyFragment {
    @BindView(R.id.recycle)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private List<DailySections.DailySectionsInfo> sectionsInfos = new ArrayList<>();

    public static SectionsFragment newInstance() {
        SectionsFragment mSectionsFragment = new SectionsFragment();
        return mSectionsFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_sections;
    }

    @Override
    public void initViews() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {

            @Override
            public void onRefresh()
            {

                getSections();
            }
        });
        getSections();
    }

    private void getSections() {
        RetrofitHelper.getLastZhiHuApi().getZhiHuSections()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DailySections>() {

                    @Override
                    public void onCompleted() {
                        Log.e("SectionsFragment", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("SectionsFragment", "onError");

                    }

                    @Override
                    public void onNext(DailySections dailySections) {
                        List<DailySections.DailySectionsInfo> data = dailySections.data;
                        sectionsInfos.clear();
                        sectionsInfos.addAll(data);
                        afterGetSections();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });


}

    private void afterGetSections() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        SectionsAdapter mAdapter = new SectionsAdapter(sectionsInfos);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                DailySections.DailySectionsInfo dailySectionsInfo = sectionsInfos.get(position);
                SectionsDetailsActivity.launch(getActivity(), dailySectionsInfo.id);

            }


        });

    }
}

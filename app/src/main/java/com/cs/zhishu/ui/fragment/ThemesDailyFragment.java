package com.cs.zhishu.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cs.zhishu.R;
import com.cs.zhishu.adapter.DailyTypeRecycleAdapter;
import com.cs.zhishu.base.LazyFragment;
import com.cs.zhishu.model.DailyTypeBean;
import com.cs.zhishu.network.RetrofitHelper;
import com.cs.zhishu.ui.activity.ThemesDailyDetailsActivity;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

import java.util.List;

import butterknife.BindView;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Othell0 on 2016/7/18.
 */
public class ThemesDailyFragment extends LazyFragment {

    @BindView(R.id.themes_recycle)
    RecyclerView mRecyclerView;
    @BindView(R.id.circle_progress)
    CircularProgressView mCircleProgressView;


    public static ThemesDailyFragment newInstance() {

        ThemesDailyFragment mThemesDailyFragment = new ThemesDailyFragment();
        return mThemesDailyFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_themes_daily;
    }

    @Override
    public void initViews() {
        showProgress();

        getDailyTypeData();

    }

    private void showProgress() {
        mCircleProgressView.setVisibility(View.VISIBLE);
        mCircleProgressView.startAnimation();


    }

    private void getDailyTypeData() {
        RetrofitHelper.builder().getDailyType()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DailyTypeBean>() {
                    @Override
                    public void onCompleted() {
                        Log.e("ThemesDailyFragment", "onCompleted");


                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(DailyTypeBean dailyTypeBean) {
                        List<DailyTypeBean.SubjectDaily> others = dailyTypeBean.getOthers();
                        afterGetDailyType(others);

                    }
                });

    }

    private void afterGetDailyType(final List<DailyTypeBean.SubjectDaily> others) {
        hideProgress();
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DailyTypeRecycleAdapter mAdapter = new DailyTypeRecycleAdapter(others);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                DailyTypeBean.SubjectDaily subjectDaily = others.get(position);
                ThemesDailyDetailsActivity.Luanch(getActivity(), subjectDaily.getId());

            }


        });
    }

    private void hideProgress() {
        mCircleProgressView.setVisibility(View.GONE);
        mCircleProgressView.stopAnimation();
        mRecyclerView.setVisibility(View.VISIBLE);


    }



}

package com.cs.zhishu.ui.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;

import com.cs.zhishu.R;
import com.cs.zhishu.adapter.AutoLoadOnScrollListener;
import com.cs.zhishu.adapter.DailyListAdapter;
import com.cs.zhishu.adapter.MainViewPagerAdapter;
import com.cs.zhishu.base.LazyFragment;
import com.cs.zhishu.db.DailyDao;
import com.cs.zhishu.model.DailyBean;
import com.cs.zhishu.model.DailyDetail;
import com.cs.zhishu.model.DailyListBean;
import com.cs.zhishu.model.TopDailys;
import com.cs.zhishu.network.RetrofitHelper;
import com.cs.zhishu.util.NetWorkUtil;
import com.cs.zhishu.util.refresh.HeaderViewRecyclerAdapter;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import me.relex.circleindicator.CircleIndicator;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Othell0 on 2016/7/18.
 * 日报列表界面
 */
public class DailyListFragment extends LazyFragment implements Runnable {
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

    private List<DailyBean> dailys = new ArrayList<>();

    private LinearLayoutManager mLinearLayoutManager;

    private String currentTime = "";

    private boolean mIsUserTouched = false;

    private int size;

    private MainViewPagerAdapter mMainViewPagerAdapter;

    private ViewPager mViewPager;

    private CircleIndicator mCircleIndicator;

    private HeaderViewRecyclerAdapter mHeaderViewRecyclerAdapter;

    private Timer mTimer;

    private BannerTask mTimerTask;

    private int mPagerPosition = 0;


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
        mAutoLoadOnScrollListener = new AutoLoadOnScrollListener(mLinearLayoutManager) {

            @Override
            public void onLoadMore(int currentPage) {

                loadMoreDaily(currentTime);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                super.onScrolled(recyclerView, dx, dy);
                //int firstPos = (recyclerView == null || recyclerView.getChildCount() == 0 ? 0 : recyclerView.getChildAt(0).getTop());

                mSwipeRefreshLayout.setEnabled(mLinearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0);
            }
        };
        mRecyclerView.addOnScrollListener(mAutoLoadOnScrollListener);
        mHeaderViewRecyclerAdapter = new HeaderViewRecyclerAdapter(mAdapter);
        View headView = LayoutInflater.from(getActivity()).inflate(R.layout.recycle_head_layout, mRecyclerView, false);
        mViewPager = (ViewPager) headView.findViewById(R.id.main_view_pager);
        mCircleIndicator = (CircleIndicator) headView.findViewById(R.id.pager_indicator);
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE) {
                    mIsUserTouched = true;
                    mSwipeRefreshLayout.setEnabled(false);
                } else if (action == MotionEvent.ACTION_UP) {
                    mIsUserTouched = false;
                } else if (action == MotionEvent.ACTION_CANCEL) {
                    mSwipeRefreshLayout.setEnabled(true);
                }
                return false;
            }
        });
        mHeaderViewRecyclerAdapter.addHeaderView(headView);
        getLatesDailys(false);


    }

    public void getLatesDailys(final boolean isDownRefresh) {
        RetrofitHelper.builder().getLatestNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (!isDownRefresh) {
                            showProgress();
                        }
                    }
                })
                .map(new Func1<DailyListBean, DailyListBean>() {

                    @Override
                    public DailyListBean call(DailyListBean dailyListBean) {
                        cacheAllDetail(dailyListBean.getStories());
                        return changeReadState(dailyListBean);
                    }
                })
                .subscribe(new Observer<DailyListBean>() {
                    @Override
                    public void onCompleted() {
                        Log.e("onCompleted", " 刷新加载成功");

                    }

                    @Override
                    public void onError(Throwable e) {
                        hideProgress();
                        Log.e(e.getMessage(), "onError: 加载失败");

                    }

                    @Override
                    public void onNext(DailyListBean dailyListBean) {
                        if (dailyListBean.getStories() == null) {
                            hideProgress();
                            mSwipeRefreshLayout.setRefreshing(false);
                            Log.e("getStories == null", "加载数据失败");
                        } else {
                            mAdapter.updateData(dailyListBean.getStories());
                            currentTime = dailyListBean.getDate();
                            if (dailyListBean.getStories().size() < 8) {
                                loadMoreDaily(DailyListFragment.this.currentTime);
                            }
                            List<TopDailys> tops = dailyListBean.getTop_stories();
                            mMainViewPagerAdapter = new MainViewPagerAdapter(getActivity(), tops);
                            mViewPager.setAdapter(mMainViewPagerAdapter);
                            mCircleIndicator.setViewPager(mViewPager);
                            size = tops.size();
                            mHandler.sendEmptyMessageDelayed(1, 2000);
                        }

                    }
                });


    }

    private void showProgress() {
        mCircleProgressView.setVisibility(View.VISIBLE);
        mCircleProgressView.startAnimation();
        mRecyclerView.setVisibility(View.GONE);


    }

    private void hideProgress() {
        mCircleProgressView.setVisibility(View.GONE);
        mCircleProgressView.stopAnimation();
        mRecyclerView.setVisibility(View.VISIBLE);

    }

    private void afterGetDaily() {
        mRecyclerView.setAdapter(mHeaderViewRecyclerAdapter);
        startViewPagerRun();

    }

    private void loadMoreDaily(final String currentTime) {
        RetrofitHelper.builder().getBeforeNews(currentTime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<DailyListBean, DailyListBean>() {

                    @Override
                    public DailyListBean call(DailyListBean dailyListBean) {

                        cacheAllDetail(dailyListBean.getStories());
                        return changeReadState(dailyListBean);
                    }
                })
                .subscribe(new Observer<DailyListBean>() {
                    @Override
                    public void onCompleted() {
                        Log.e("loadMoreDaily", "onCompleted: 加载更多数据成功");


                    }

                    @Override
                    public void onError(Throwable e) {
                        mAutoLoadOnScrollListener.setLoading(false);
                        Log.e("loadMoreDaily", "onError: 加载更多数据失败");

                    }

                    @Override
                    public void onNext(DailyListBean dailyListBean) {
                        mAutoLoadOnScrollListener.setLoading(false);
                        mAdapter.addDatas(dailyListBean.getStories());
                        DailyListFragment.this.currentTime = dailyListBean.getDate();

                    }
                });


    }

    /**
     * 改变点击已阅读状态
     *
     * @param dailyListBean
     * @return
     */
    public DailyListBean changeReadState(DailyListBean dailyListBean) {

        List<String> allReadId = new DailyDao(getActivity()).getAllReadNew();
        for (DailyBean daily : dailyListBean.getStories()) {
            daily.setDate(dailyListBean.getDate());
            for (String readId : allReadId) {
                if (readId.equals(daily.getId() + "")) {
                    daily.setRead(true);
                }
            }
        }
        return dailyListBean;
    }

    /**
     * 缓存数据
     *
     * @param dailys
     */
    private void cacheAllDetail(List<DailyBean> dailys) {
        if (NetWorkUtil.isWifiConnected()) {
            for (DailyBean daily : dailys) {
                cacheNewsDetail(daily.getId());
            }
        }

    }

    private void cacheNewsDetail(int newsId) {
        RetrofitHelper.builder().getNewsDetails(newsId)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Observer<DailyDetail>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(DailyDetail dailyDetail) {

                    }
                });

    }

    public void startViewPagerRun() {
        //执行ViewPager进行轮播
        mTimer = new Timer();
        mTimerTask = new BannerTask();
        mTimer.schedule(mTimerTask, 5000, 5000);
    }

    @Override
    public void run() {

        if (mPagerPosition == size - 1) {
            mViewPager.setCurrentItem(size - 1, false);
        } else {
            mViewPager.setCurrentItem(mPagerPosition);
        }
    }

    private class BannerTask extends TimerTask {


        @Override
        public void run() {
            if (!mIsUserTouched) {
                mPagerPosition = (mPagerPosition + 1) % size;
                if (getActivity() != null) {
                    getActivity().runOnUiThread(DailyListFragment.this);
                }
            }
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTimer != null)
        {
            mTimer.cancel();
            mTimer = null;
        }
        if (mTimerTask != null)
        {
            mTimerTask.cancel();
            mTimerTask = null;
        }
    }
}

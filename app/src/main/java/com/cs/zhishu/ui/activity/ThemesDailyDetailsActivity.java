package com.cs.zhishu.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cs.zhishu.R;
import com.cs.zhishu.adapter.AbsRecyclerViewAdapter;
import com.cs.zhishu.adapter.ThemesDetailsHeadAdapter;
import com.cs.zhishu.adapter.ThemesDetailsStoriesAdapter;
import com.cs.zhishu.base.AbsBaseActivity;
import com.cs.zhishu.model.Editors;
import com.cs.zhishu.model.Stories;
import com.cs.zhishu.model.ThemesDetails;
import com.cs.zhishu.network.RetrofitHelper;
import com.cs.zhishu.util.refresh.HeaderViewRecyclerAdapter;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Othell0 on 2016/7/21.
 */
public class ThemesDailyDetailsActivity extends AbsBaseActivity {
    //主题日报故事列表
    private List<Stories> stories = new ArrayList<>();

    //主题日报主编列表
    private List<Editors> editors = new ArrayList<>();

    private static final String EXTRA_TYPE = "extra_type";

    private int id;

    private HeaderViewRecyclerAdapter mHeaderViewRecyclerAdapter;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycle)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.circle_progress)
    CircularProgressView mCircleProgressView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_type_daily;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getIntExtra(EXTRA_TYPE, -1);
        }


        startGetThemesDetails();

    }

    private void startGetThemesDetails() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        mCircleProgressView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        getThemesDetails();
    }

    private void getThemesDetails() {
        RetrofitHelper.builder().getThemesDetailsById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ThemesDetails>() {
                    @Override
                    public void onCompleted() {
                        Log.e("getThemesDetails", "onCompleted");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("getThemesDetails", "onError");

                    }

                    @Override
                    public void onNext(ThemesDetails themesDetails) {

                        finishGetThemesDetails(themesDetails);

                    }
                });
    }

    private void finishGetThemesDetails(ThemesDetails themesDetails) {
        stories.addAll(themesDetails.getStories());
        editors.addAll(themesDetails.getEditors());

        mToolbar.setTitle(themesDetails.getName());

        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        ThemesDetailsStoriesAdapter mAdapter = new ThemesDetailsStoriesAdapter(mRecyclerView, stories);
        mHeaderViewRecyclerAdapter = new HeaderViewRecyclerAdapter(mAdapter);

        addHeadView(themesDetails);

        mRecyclerView.setAdapter(mHeaderViewRecyclerAdapter);
        mAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {

                Stories stories = ThemesDailyDetailsActivity.this.stories.get(position);
                DailyDetailActivity.launch(ThemesDailyDetailsActivity.this, stories.getId());
            }
        });


        mCircleProgressView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);


    }

    private void addHeadView(ThemesDetails themesDetails) {
        View headView = LayoutInflater.from(this)
                .inflate(R.layout.layout_themes_details_head, mRecyclerView, false);
        ImageView mThemesBg = (ImageView) headView.findViewById(R.id.type_image);
        TextView mThemesTitle = (TextView) headView.findViewById(R.id.type_title);

        Glide.with(this)
                .load(themesDetails.getBackground())
                .placeholder(R.drawable.account_avatar)
                .into(mThemesBg);
        mThemesTitle.setText(themesDetails.getDescription());

        View editorsHeadView = LayoutInflater.from(this)
                .inflate(R.layout.layout_themes_details_head1, mRecyclerView, false);
        RecyclerView mHeadRecycle = (RecyclerView) editorsHeadView.findViewById(R.id.head_recycle);

        mHeadRecycle.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mHeadRecycle.setLayoutManager(mLinearLayoutManager);

        ThemesDetailsHeadAdapter mHeadAdapter = new ThemesDetailsHeadAdapter(mHeadRecycle, editors);
        mHeadRecycle.setAdapter(mHeadAdapter);
        mHeadAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {

                Editors editor = ThemesDailyDetailsActivity.this.editors.get(position);
                int id = editor.getId();
                String name = editor.getName();
                EditorInfoActivity.luancher(ThemesDailyDetailsActivity.this, id, name);
            }
        });
        mHeaderViewRecyclerAdapter.addHeaderView(headView);
        mHeaderViewRecyclerAdapter.addHeaderView(editorsHeadView);
        mHeaderViewRecyclerAdapter.notifyDataSetChanged();
        mHeadAdapter.notifyDataSetChanged();
    }

    @Override
    public void initToolBar() {
        setSupportActionBar(mToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null)
            supportActionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public static void launch(Activity activity, int id) {

        Intent mIntent = new Intent(activity, ThemesDailyDetailsActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mIntent.putExtra(EXTRA_TYPE, id);
        activity.startActivity(mIntent);
    }


}

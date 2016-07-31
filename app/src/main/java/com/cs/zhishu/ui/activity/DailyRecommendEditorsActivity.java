package com.cs.zhishu.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cs.zhishu.R;
import com.cs.zhishu.adapter.RecommendEditorAdapter;
import com.cs.zhishu.base.AbsBaseActivity;
import com.cs.zhishu.model.DailyRecommend;
import com.cs.zhishu.network.RetrofitHelper;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Othell0 on 7/28/2016.
 */
public class DailyRecommendEditorsActivity extends AbsBaseActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.recycle)
    RecyclerView mRecyclerView;
    @BindView(R.id.empty_tv)
    TextView mTextView;
    @BindView(R.id.circle_progress)
    CircularProgressView mCircleProgressView;

    private static final String EXTRA_ID = "extra_id";

    private int id;

    private List<DailyRecommend.Editor> editorList = new ArrayList<>();

    private RecommendEditorAdapter mAdapter;

    public static void launcher(Activity activity, int id) {
        Intent mIntent = new Intent(activity, DailyRecommendEditorsActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mIntent.putExtra(EXTRA_ID, id);
        activity.startActivity(mIntent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_daily_recommend_editors;
    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle("日报推荐者");
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

    @Override
    public void initViews(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getIntExtra(EXTRA_ID, -1);
            Log.e("id", "Loading");
        }
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        startGetEditors();

    }

    private void startGetEditors() {
        mCircleProgressView.setVisibility(View.VISIBLE);
        mCircleProgressView.startAnimation();

        getEditors();
    }

    private void getEditors() {
        RetrofitHelper.builder().getDailyRecommendEditors(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DailyRecommend>() {
                    @Override
                    public void onCompleted() {
                        Log.e("getEditors", "onCompleted:getEditors ");
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.e("getEditors", "onError:getEditors ");

                    }

                    @Override
                    public void onNext(DailyRecommend dailyRecommend) {
                        if (dailyRecommend != null) {
                            Log.e("getEditors", dailyRecommend.toString());
                            List<DailyRecommend.Editor> editors = dailyRecommend.editors;
                            if (editors != null && editors.size() > 0) {
                                editorList.addAll(editors);
                                finishGetEditors();
                            } else {
                                hideProgress();
                            }

                            hideProgress();
                        }
                    }
                });
    }

    private void finishGetEditors() {
        mAdapter = new RecommendEditorAdapter(editorList);
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                DailyRecommend.Editor editor = editorList.get(i);
                int id = editor.id;
                String name = editor.name;
                EditorInfoActivity.luancher(DailyRecommendEditorsActivity.this, id, name);
            }
        });
    }

    private void hideProgress() {
        mCircleProgressView.setVisibility(View.GONE);
        mCircleProgressView.stopAnimation();

        mTextView.setVisibility(View.VISIBLE);
    }


}

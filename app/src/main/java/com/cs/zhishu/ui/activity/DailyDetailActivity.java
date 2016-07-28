package com.cs.zhishu.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cs.zhishu.R;
import com.cs.zhishu.base.AbsBaseActivity;
import com.cs.zhishu.model.DailyBean;
import com.cs.zhishu.model.DailyDetail;
import com.cs.zhishu.model.DailyExtraMessage;
import com.cs.zhishu.network.RetrofitHelper;
import com.cs.zhishu.util.HtmlUtil;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

import butterknife.BindView;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by exbbefl on 7/21/2016.
 */
public class DailyDetailActivity extends AbsBaseActivity {
    @BindView(R.id.detail_image)
    ImageView mDetailImage;
    @BindView(R.id.detail_title)
    TextView mDetailTitle;
    @BindView(R.id.detail_source)
    TextView mDetailSource;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.coll_toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.detail_web_view)
    WebView mWebView;
    @BindView(R.id.circle_progress)
    CircularProgressView mCircleProgressView;

    private DailyBean mDaily;

    private ActionBar mActionBar;

    private static final String EXTRA_DETAIL = "extra_detail";

    private static final String EXTRA_ID = "extra_id";

    private int id;

    private MenuItem itemCommentNum;

    private MenuItem itemPariseNum;

    private DailyExtraMessage mDailyExtraMessage;

    private MenuItem itemParise;

    private boolean isShowSnack = false;

    private int comments;

    private int popularity;


    @Override
    public int getLayoutId() {
        return R.layout.activity_daily_dateil;
    }


    @Override
    public void initViews(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            mDaily = intent.getParcelableExtra(EXTRA_DETAIL);
            id = intent.getIntExtra(EXTRA_ID, -1);
        }
    /*   设置侧滑返回触发范围*/
   /*     mSwipeBackLayout.setEdgeSize(120);*/

        /*初始化ToolBar*/
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();


        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }
        mCollapsingToolbarLayout.setTitleEnabled(true);
        mActionBar.setTitle("");
        mToolbar.setNavigationIcon(R.drawable.back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        startGetDailyDetail(mDaily == null ? id : mDaily.getId());

        //第一次进入提示用户可以左滑返回
        showSnackBarHint();


    }

    @Override
    public void initToolBar() {

    }

    private void showSnackBarHint() {

        Snackbar.make(mToolbar, "在左端右滑可以返回主页哦~", Snackbar.LENGTH_LONG).show();
        this.isShowSnack = true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        itemCommentNum = menu.findItem(R.id.menu_action_comment_num);
        itemPariseNum = menu.findItem(R.id.menu_action_parise_num);
        itemParise = menu.findItem(R.id.menu_action_parise);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_share:
                //分享新闻
                share();
                return true;

            case R.id.menu_action_fav:
                //查看新闻推荐者
                DailyRecommendEditorsActivity.luancher(DailyDetailActivity.this, mDaily == null ? id : mDaily.getId());
                return true;

            case R.id.menu_action_comment:
                // 查看新闻评论
                DailyCommentActivity.luancher(DailyDetailActivity.this, mDaily == null ? id : mDaily.getId(), mDailyExtraMessage.comments, mDailyExtraMessage.longComments, mDailyExtraMessage.shortComments);
                return true;

            case R.id.menu_action_parise:
                //执行点赞动画
                AnimationUtils.loadAnimation(DailyDetailActivity.this, R.anim.anim_small);
                itemParise.setIcon(R.drawable.praised);
                Snackbar.make(mToolbar, "点赞数:" + popularity, Snackbar.LENGTH_SHORT).show();
                return true;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void share() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share));
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_from) + mDaily.getTitle() + "，http://daily.zhihu.com/story/" + mDaily.getId());
        startActivity(Intent.createChooser(intent, mDaily.getTitle()));

    }

    private void startGetDailyDetail(int id) {
        RetrofitHelper.builder().getNewsDetails(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showProgress();
                    }
                })
                .subscribe(new Observer<DailyDetail>() {
                    @Override
                    public void onCompleted() {
                        Log.e("startGetDailyDetail", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideProgress();
                        Log.e("startGetDailyDetail", "onError 数据加载失败");

                    }

                    @Override
                    public void onNext(DailyDetail dailyDetail) {
                        hideProgress();
                        if (dailyDetail != null) {
                            /*设置图片*/
                            Glide.with(DailyDetailActivity.this)
                                    .load(dailyDetail.getImage())
                                    .placeholder(R.drawable.account_avatar)
                                    .into(mDetailImage);

                             /*设置标题*/
                            mDetailTitle.setText(dailyDetail.getTitle());
                             /*设置图片来源*/
                            mDetailSource.setText(dailyDetail.getImage_source());
                             /*设置web内容加载*/
                            String htmlData = HtmlUtil.createHtmlData(dailyDetail);
                            mWebView.loadData(htmlData, HtmlUtil.MIME_TYPE, HtmlUtil.ENCODING);

                            getDailyMessage(dailyDetail.getId());

                        }

                    }
                });

    }

    private void showProgress() {
        mCircleProgressView.setVisibility(View.VISIBLE);
        mCircleProgressView.startAnimation();
    }

    private void hideProgress() {
        mCircleProgressView.setVisibility(View.GONE);
        mCircleProgressView.stopAnimation();


    }


    /**
     * 设置日报的评论数跟点赞数
     *
     * @param id
     */

    private void getDailyMessage(int id) {
        RetrofitHelper.builder().getDailyExtraMessageById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DailyExtraMessage>() {
                    @Override
                    public void onCompleted() {
                        Log.e("getDailyMessage", "onCompleted");

                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.e("getDailyMessage", "onError");

                    }

                    @Override
                    public void onNext(DailyExtraMessage dailyExtraMessage) {
                        if (dailyExtraMessage != null) {
                            mDailyExtraMessage = dailyExtraMessage;

                            comments = dailyExtraMessage.comments;
                            popularity = dailyExtraMessage.popularity;

                            itemCommentNum.setTitle(comments + "");
                            itemPariseNum.setTitle(popularity + "");
                            DailyDetailActivity.this.getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
                        }

                    }
                });

    }

    public static void lanuch(Context context, DailyBean dailyBean) {

        Intent mIntent = new Intent(context, DailyDetailActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mIntent.putExtra(EXTRA_DETAIL, dailyBean);
        context.startActivity(mIntent);
    }

    public static void lanuch(Context context, int id) {

        Intent mIntent = new Intent(context, DailyDetailActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mIntent.putExtra(EXTRA_ID, id);
        context.startActivity(mIntent);
    }


}

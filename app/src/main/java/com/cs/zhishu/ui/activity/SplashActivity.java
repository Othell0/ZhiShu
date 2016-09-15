package com.cs.zhishu.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cs.zhishu.R;
import com.cs.zhishu.model.LaunchImageBean;
import com.cs.zhishu.network.RetrofitHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SplashActivity extends Activity {


    private static final String RESOLUTION = "1920*1080";

    private static final int ANIMATION_DURATION = 3000;

    private static final float SCALE_END = 1.15F;
    @BindView(R.id.iv_splash)
    ImageView ivSplash;
    @BindView(R.id.tv_from)
    TextView tvFrom;


    private final ThreadLocal<Handler> mHandler = new ThreadLocal<Handler>() {
        @Override
        protected Handler initialValue() {
            return new Handler() {

                @Override
                public void handleMessage(Message msg) {

                    super.handleMessage(msg);
                    if (msg.what == 0) {
                        animateImage();
                    }
                }
            };
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        getLaunchImage();
    }

    private void getLaunchImage() {
        RetrofitHelper.builder().getLaunchImage(RESOLUTION)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LaunchImageBean>() {


                    @Override
                    public void onCompleted() {
                        Log.e("666", "onCompleted");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("请求失败", "onError");
                        Glide.with(SplashActivity.this).load(R.drawable.default_splash).into(ivSplash);
                        mHandler.get().sendEmptyMessageDelayed(0, 1000);

                    }

                    @Override
                    public void onNext(LaunchImageBean launchImageBean) {
                        if (launchImageBean != null) {
                            String img = launchImageBean.getImg();

                            Glide.with(SplashActivity.this)
                                    .load(img)
                                    .crossFade(2000)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(ivSplash);

                            tvFrom.setText(launchImageBean.getText());
                            mHandler.get().sendEmptyMessageDelayed(0,1);
                        }
                    }
                });
    }


    private void animateImage() {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(ivSplash, "scaleX", 1f, SCALE_END);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(ivSplash, "scaleY", 1f, SCALE_END);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(ANIMATION_DURATION).play(animatorX).with(animatorY);
        set.start();

        set.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                SplashActivity.this.finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.get().removeCallbacksAndMessages(null);
    }

}

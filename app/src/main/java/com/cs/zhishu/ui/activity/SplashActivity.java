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
import com.cs.zhishu.model.LuanchImageBean;
import com.cs.zhishu.network.RetrofitHelper;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SplashActivity extends Activity {


    private static final String RESOLUTION = "1080*1776";

    private static final int ANIMATION_DURATION = 2000;

    private static final float SCALE_END = 1.13F;

    private ImageView ivSpalsh;

    private TextView tvFrom;


    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            super.handleMessage(msg);
            if (msg.what == 0) {
                animateImage();
            }
        }
    };

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);



    }

    @Override
    protected void onResume() {

        getLuanchImage();
        super.onResume();
    }

    private void getLuanchImage() {
        ivSpalsh = (ImageView) findViewById(R.id.iv_spalsh);
        tvFrom = (TextView) findViewById(R.id.tv_from);

        RetrofitHelper.builder().getLuanchImage(RESOLUTION)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LuanchImageBean>() {


                    @Override
                    public void onCompleted() {
                        Log.e("666", "onCompleted");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("请求失败", "onError");
                        Glide.with(SplashActivity.this).load(R.drawable.default_splash).into(ivSpalsh);
                        mHandler.sendEmptyMessageDelayed(0, 1000);

                    }

                    @Override
                    public void onNext(LuanchImageBean luanchImageBean) {
                        if (luanchImageBean != null) {
                            String img = luanchImageBean.getImg();
                            Glide.with(SplashActivity.this)
                                    .load(img)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(ivSpalsh);
                            tvFrom.setText(luanchImageBean.getText());
                            mHandler.sendEmptyMessageDelayed(0, 1000);
                        }


                    }
                });
    }


    private void animateImage() {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(ivSpalsh, "scaleX", 1f, SCALE_END);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(ivSpalsh, "scaleY", 1f, SCALE_END);

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


}

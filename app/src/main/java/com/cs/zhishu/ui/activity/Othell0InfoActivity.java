package com.cs.zhishu.ui.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.cs.zhishu.R;
import com.cs.zhishu.base.AbsBaseActivity;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Othell0 on 2016/7/2.
 */
public class Othell0InfoActivity extends AbsBaseActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.head_view)
    CircleImageView headView;
    @BindView(R.id.text1)
    TextView mText1;


    @Override
    public int getLayoutId() {
        return R.layout.acitvity_othell0info;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        animateImage();
        animateText();
        animateText1();
    }

    private void animateImage() {
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(headView, "scaleY", 0, 1);
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(headView, "scaleX", 0, 1);
        ObjectAnimator rotationXAnimator = ObjectAnimator.ofFloat(headView, "rotation", 0, 180, 180, 0);
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(headView, "alpha", 0, 1);

        AnimatorSet set = new AnimatorSet();
        /**让scaleYAnimator、scaleXAnimator、rotationXAnimator同时执行
         *执行完之后执行alphaAnimator*/
        set.play(scaleXAnimator).with(scaleYAnimator);
        set.play(scaleYAnimator).with(rotationXAnimator);
        set.play(alphaAnimator).with(rotationXAnimator);


        set.setDuration(3000);
        set.start();
    }

    private void animateText() {

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1, 100);
        valueAnimator.setDuration(6000);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                TextView text = (TextView) findViewById(R.id.text);
                float value = (Float) animation.getAnimatedValue();
                assert text != null;
                text.setAlpha(value);
            }
        });
        valueAnimator.start();
    }

    private void animateText1() {
        ObjectAnimator translationXAnimator = ObjectAnimator.ofFloat(mText1, "translationX", -333, 0);
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mText1, "alpha", 0, 1);
        AnimatorSet set = new AnimatorSet();

        set.play(translationXAnimator).with(alphaAnimator);
        set.setDuration(6000);
        set.start();
    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle("关于我");
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }



}

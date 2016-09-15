package com.cs.zhishu.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.cs.zhishu.R;

/**
 * Created by Othell0 on 7/29/2016.
 */
public class EmptyView extends LinearLayout {


    public EmptyView(Context context) {

        this(context, null);
    }

    public EmptyView(Context context, AttributeSet attrs) {

        super(context, attrs);
        init();
    }

    private void init() {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_empty_comment, this);
        addView(view);
    }
}


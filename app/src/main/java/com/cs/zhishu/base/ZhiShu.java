package com.cs.zhishu.base;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.alibaba.mobileim.FeedbackAPI;
import com.alibaba.mobileim.YWAPI;
import com.alibaba.wxlib.util.SysUtil;

/**
 * Created by Othell0 on 2016/7/6.
 */
public class ZhiShu extends Application {
    public static Context mAppContext;

    public final static String APP_KEY = "23425913";

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = this;

    }

    public static Context getContext() {
        return mAppContext;
    }
}

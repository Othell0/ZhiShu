package com.cs.zhishu.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by Othell0 on 2016/7/6.
 */
public class Bmob extends Application {
    public static Context mAppContext;

    public static String BMBO_KEY = "e7b343bf907638991c9712c7ed0c595f";
    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = this;
    }

    public static Context getContext() {
        return mAppContext;
    }
}

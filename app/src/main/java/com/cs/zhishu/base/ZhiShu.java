
package com.cs.zhishu.base;


import android.app.Application;
import android.content.Context;

import com.alibaba.mobileim.FeedbackAPI;
import com.alibaba.mobileim.YWAPI;
import com.alibaba.wxlib.util.SysUtil;

/**
 * Created by Othell0 on 2016/7/6.
 */

public class ZhiShu extends Application {
    private static Context sContext;

    public static Context getContext() {
        return sContext;
    }

    public final static String APP_KEY = "23425913";

    @Override
    public void onCreate() {
        SysUtil.setApplication(this);
        if (SysUtil.isTCMSServiceProcess(this)) {
            return;
        }


        YWAPI.init(this, APP_KEY);

        if (SysUtil.isMainProcess(this)) {
            FeedbackAPI.initFeedback(this, APP_KEY, "意见反馈", null);
        }
    }
}
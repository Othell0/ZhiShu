package com.cs.zhishu.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.alibaba.mobileim.FeedbackAPI;
import com.alibaba.mobileim.YWAPI;
import com.alibaba.wxlib.util.SysUtil;
import com.cs.zhishu.base.ZhiShu;

/**
 * Created by Othell0 on 2016/7/2.
 */
public class MessageActivity extends Activity {

    public final static String customFbTitleName = "意见反馈";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SysUtil.setApplication(this);
        if (SysUtil.isTCMSServiceProcess(this)) {
            return;  //特别注意：此处return是退出onCreate函数，因此不能封装到其他任何方法中!
        }

//第一个参数是Application Context，这里的APP_KEY即应用创建时申请的APP_KEY
        YWAPI.init(getApplication(), ZhiShu.APP_KEY);

//第三个参数是自自定义的反馈界面标题，第四个参数是初始化反馈接口的回调，异步通知成功或者失败
        if (SysUtil.isMainProcess(this)) {
            FeedbackAPI.initFeedback(this, ZhiShu.APP_KEY, customFbTitleName, null);
        }

        Intent intent = FeedbackAPI.getFeedbackActivityIntent();
        if (intent != null) {
            startActivity(intent);
        }
    }
}









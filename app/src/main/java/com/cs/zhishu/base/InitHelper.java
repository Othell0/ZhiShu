package com.cs.zhishu.base;

import android.app.Application;

import com.alibaba.mobileim.FeedbackAPI;
import com.alibaba.mobileim.YWAPI;
import com.alibaba.wxlib.util.SysUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Othell0 on 8/4/2016.
 */
public class InitHelper {
    public static void initYWSDK(Application application){
        //todo 只在主进程进行云旺SDK的初始化!!!
        if(SysUtil.isMainProcess(application)){
            //TODO 注意：--------------------------------------
            //  以下步骤调用顺序有严格要求，请按照示例的步骤（todo step）
            // 的顺序调用！
            YWAPI.enableSDKLogOutput(true);

        }
    }

    public static void initFeedBack(Application application) {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("loginTime","登录时间");
            jsonObject.put("visitPath","登陆，关于，反馈");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        FeedbackAPI.initFeedback(application, ZhiShu.APP_KEY,  "反馈", null);
        FeedbackAPI.setAppExtInfo(jsonObject);

        FeedbackAPI.setCustomContact("",false);
    }
}


package com.cs.zhishu.base;


import android.app.Application;
import android.content.Context;

import com.alibaba.mobileim.FeedbackAPI;
import com.alibaba.mobileim.YWAPI;
import com.alibaba.wxlib.util.SysUtil;
import com.cs.zhishu.R;
import com.github.lazylibrary.util.AppUtils;

import org.lzh.framework.updatepluginlib.UpdateConfig;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.model.UpdateParser;

/**
 * Created by Othell0 on 2016/7/6.
 */

public class ZhiShu extends Application {
    private static final String APK_FILE ="http://fir.im/Othell0" ;
    private static Context sContext;


    public static Context getContext() {
        return sContext;
    }

    public final static String APP_KEY = "23425913";

    @Override
    public void onCreate() {
        /*实时反馈模块*/
        SysUtil.setApplication(this);
        if (SysUtil.isTCMSServiceProcess(this)) {
            return;
        }

        YWAPI.init(this, APP_KEY);

        if (SysUtil.isMainProcess(this)) {
            FeedbackAPI.initFeedback(this, APP_KEY, "意见反馈", null);
        }
        super.onCreate();
        /*自动更新模块*/
        UpdateConfig.getConfig()
                // 必填：数据更新接口,url与checkEntity两种方式任选一种填写
                .url("http://www.baidu.com")
//                .checkEntity(new CheckEntity().setMethod(HttpMethod.GET).setUrl("http://www.baidu.com"))
                // 必填：用于从数据更新接口获取的数据response中。解析出Update实例。以便框架内部处理
                .jsonParser(new UpdateParser() {
                    @Override
                    public Update parse(String response) {
                        /* 此处根据上面url或者checkEntity设置的检查更新接口的返回数据response解析出
                         * 一个update对象返回即可。更新启动时框架内部即可根据update对象的数据进行处理
                         */
                        // 此处模拟一个Update对象
                        Update update = new Update(response);
                        // 此apk包的更新时间
                        update.setUpdateTime(System.currentTimeMillis());
                        // 此apk包的下载地址
                        update.setUpdateUrl(APK_FILE);
                        // 此apk包的版本号
                        Integer ver_code = AppUtils.getVerCode(getApplicationContext());
                        update.setVersionCode(ver_code);
                        // 此apk包的版本名称
                        String ver_name = AppUtils.getVerName(getApplicationContext());
                        update.setVersionName(ver_name);
                        // 此apk包的更新内容
                        update.setUpdateContent(getString(R.string.update_note));
                        // 此apk包是否为强制更新
                        update.setForced(false);
                        // 是否显示忽略此次版本更新按钮
                        update.setIgnore(true);
                        return update;
                    }
                });
    }
}
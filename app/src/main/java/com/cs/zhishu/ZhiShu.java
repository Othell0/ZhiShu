
package com.cs.zhishu;


import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;
import android.widget.Toast;

import com.alibaba.mobileim.FeedbackAPI;
import com.alibaba.mobileim.YWAPI;
import com.alibaba.wxlib.util.SysUtil;
import com.github.lazylibrary.util.AppUtils;

import org.lzh.framework.updatepluginlib.UpdateConfig;
import org.lzh.framework.updatepluginlib.callback.UpdateCheckCB;
import org.lzh.framework.updatepluginlib.callback.UpdateDownloadCB;
import org.lzh.framework.updatepluginlib.model.Update;
import org.lzh.framework.updatepluginlib.model.UpdateParser;

import java.io.File;

/**
 * Created by Othell0 on 2016/7/6.
 */

public class ZhiShu extends Application {
    private static final String APK_FILE = "http://fir.im/Othell0";
    private static Context sContext;


    public static Context getContext() {
        return sContext;
    }

    public final static String APP_KEY = "23425913";

    @Override
    public void onCreate() {
        /*设置自动夜间模式*/
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
        initFeedBack();
     /*   initUpdate();*/
        super.onCreate();


    }

    private void initFeedBack() {
        /*实时反馈模块*/
        SysUtil.setApplication(this);
        if (SysUtil.isTCMSServiceProcess(this)) {
            return;
        }

        YWAPI.init(this, APP_KEY);

        if (SysUtil.isMainProcess(this)) {
            FeedbackAPI.initFeedback(this, APP_KEY, "意见反馈", null);
        }

    }

    /* private void initUpdate()*/ {
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
                })
                .checkCB(new UpdateCheckCB() {

                    @Override
                    public void onCheckError(int code, String errorMsg) {
                        Toast.makeText(ZhiShu.this, "更新失败：code:" + code + ",errorMsg:" + errorMsg, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onUserCancel() {
                        Toast.makeText(ZhiShu.this, "取消更新", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCheckIgnore(Update update) {
                        Toast.makeText(ZhiShu.this, "忽略此版本更新", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void hasUpdate(Update update) {
                        Toast.makeText(ZhiShu.this, "检查到有更新", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void noUpdate() {
                        Toast.makeText(ZhiShu.this, "暂时没有更新", Toast.LENGTH_SHORT).show();
                    }
                })
                // apk下载的回调
                .downloadCB(new UpdateDownloadCB() {
                    @Override
                    public void onUpdateStart() {
                        Toast.makeText(ZhiShu.this, "下载开始", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onUpdateComplete(File file) {
                        Toast.makeText(ZhiShu.this, "下载完成", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onUpdateProgress(long current, long total) {
                    }

                    @Override
                    public void onUpdateError(int code, String errorMsg) {
                        Toast.makeText(ZhiShu.this, "下载失败：code:" + code + ",errorMsg:" + errorMsg, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
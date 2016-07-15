package com.cs.zhishu.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.cs.zhishu.base.ZhiShuAPP;

/**
 * Created by exbbefl on 7/15/2016.
 */
public class NetWorkUtil {
    public static boolean isNetworkConnected()
    {

        if (ZhiShuAPP.getContext() != null)
        {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) ZhiShuAPP.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null)
            {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static boolean isWifiConnected()
    {

        if (ZhiShuAPP.getContext() != null)
        {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) ZhiShuAPP.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null)
            {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }
}

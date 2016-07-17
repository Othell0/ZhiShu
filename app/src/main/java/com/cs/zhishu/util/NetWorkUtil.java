package com.cs.zhishu.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.cs.zhishu.base.ZhiShu;

/**
 * Created by exbbefl on 7/15/2016.
 */
public class NetWorkUtil
{

    private NetWorkUtil()
    {

    }

    public static boolean isNetworkConnected()
    {

        if (ZhiShu.getContext() != null)
        {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) ZhiShu.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
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

        if (ZhiShu.getContext() != null)
        {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) ZhiShu.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null)
            {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static boolean isMobileConnected()
    {

        if (ZhiShu.getContext() != null)
        {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) ZhiShu.getContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null)
            {
                return mMobileNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static int getConnectedType()
    {

        if (ZhiShu.getContext() != null)
        {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) ZhiShu.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable())
            {
                return mNetworkInfo.getType();
            }
        }
        return -1;
    }
}

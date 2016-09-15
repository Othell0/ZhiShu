package com.cs.zhishu.util;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Othell0 on 7/22/2016.
 */
public class DateUtil {
    private DateUtil() {

    }
    public static String formatDate(String date) {

        String dateFormat = null;
        try {
            dateFormat = date.substring(4, 6) + "月" + date.substring(6, 8) + "日";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateFormat;
    }

    public static String getTime(long date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss", Locale.CHINA);
        Long time = date;

        return simpleDateFormat.format(time);
    }
}

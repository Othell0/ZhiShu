package com.cs.zhishu.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Othell0 on 8/31/2016.
 */
public class DayNightHelper {
    private final static String FILE_NAME = "settings";
    private final static String MODE = "day_night_mode";

    private SharedPreferences mSharedPreferences;

    public DayNightHelper(Context context) {
        this.mSharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    /**
     * 保存模式设置
     *
     * @param mode
     * @return
     */
    public boolean setMode(DayNight mode) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(MODE, mode.getName());
        return editor.commit();
    }

    /**
     * 夜间模式
     *
     * @return
     */
    public boolean isNight() {
        String mode = mSharedPreferences.getString(MODE, DayNight.DAY.getName());
        return DayNight.NIGHT.getName().equals(mode);
    }

    /**
     * 日间模式
     *
     * @return
     */
    public boolean isDay() {
        String mode = mSharedPreferences.getString(MODE, DayNight.DAY.getName());
        return DayNight.DAY.getName().equals(mode);
    }
}

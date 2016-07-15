package com.cs.zhishu.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Othell0 on 2016/7/13.
 *  专栏详情数据查看
 */
public class SectionsDetails {
    public String name;

    public long timestamp;

    public List<SectionsDetailsInfo> stories;

    public class SectionsDetailsInfo
    {

        public String date;

        @SerializedName("display_date")
        public String displayDate;

        public int id;

        public List<String> images;

        public String title;
    }
}

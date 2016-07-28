package com.cs.zhishu.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Othell0 on 2016/7/13.
 * 查看日报推荐者信息
 */
public class DailyRecommend {
    @SerializedName("item_count")
    public int itemCount;

    public List<Editor> editors;

    @Override
    public String toString()
    {

        return "DailyRecommend{" +
                "itemCount=" + itemCount +
                ", editors=" + editors +
                '}';
    }

    public class Editor
    {

        public String avatar;

        public String wb;

        public int id;

        public String name;

        public String title;

        @Override
        public String toString()
        {

            return "Editor{" +
                    "avatar='" + avatar + '\'' +
                    ", bio='" + wb + '\'' +
                    ", id=" + id +
                    ", name='" + name + '\'' +
                    ", title='" + title + '\'' +
                    '}';
        }
    }
}

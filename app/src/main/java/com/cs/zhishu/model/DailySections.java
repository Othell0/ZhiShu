package com.cs.zhishu.model;

import java.util.List;

/**
 * Created by Othell0 on 2016/7/13.
 * 知乎专栏列表
 */
public class DailySections {

    public List<DailySectionsInfo> data;

    public class DailySectionsInfo
    {

        public String description;

        public int id;

        public String name;

        public String thumbnail;
    }
}


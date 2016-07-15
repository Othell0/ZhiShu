package com.cs.zhishu.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by exbbefl on 7/15/2016.
 */
public class FuliResult {
    public boolean error;

    @SerializedName("results")
    public List<FuliBean> fulis;


    public class FuliBean
    {

        public String createdAt;

        public String url;
    }
}

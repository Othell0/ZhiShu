package com.cs.zhishu.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by Othell0 on 2016/7/6.
 */
public class zhishuMessage extends BmobObject{
    private String content;

    public zhishuMessage(String content)
    {

        super();
        this.content = content;
    }

    public zhishuMessage() {
        super();
    }

    @Override
    public String toString() {
        return "FeedBckBean [content=" + content + "]";
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}

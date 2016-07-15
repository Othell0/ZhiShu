package com.cs.zhishu.model;

import java.util.List;

/**
 * Created by Othell0 on 2016/7/13.
 * comments : 评论列表，形式为数组（请注意，其长度可能为 0）
 * author : 评论作者
 * id : 评论者的唯一标识符
 * content : 评论的内容
 * likes : 评论所获『赞』的数量
 * time : 评论时间
 * avatar : 用户头像图片的地址
 */
public class DailyComment {
    public List<CommentInfo> comments;


    public class CommentInfo
    {

        public String author;

        public String avatar;

        public String content;

        public int id;

        public int likes;

        public long time;
    }
}

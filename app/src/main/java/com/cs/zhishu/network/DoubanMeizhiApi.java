package com.cs.zhishu.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by exbbefl on 7/15/2016.
 */
public interface DoubanMeizhiApi {

    /**
     * 根据cid查询不同类型的妹子图片
     * @param cid
     * @param pageNum
     * @return
     */
    @GET("show.htm")
    Call<ResponseBody> getDoubanMeizi(@Query("cid") int cid, @Query("pager_offset") int pageNum);
}


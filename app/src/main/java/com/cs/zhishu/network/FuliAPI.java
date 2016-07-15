package com.cs.zhishu.network;

import com.cs.zhishu.model.FuliResult;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by exbbefl on 7/15/2016.
 */
public interface FuliAPI {
    /**
     * 查询美图图片福利接口
     *
     * @param number
     * @param page
     * @return
     */
    @GET("data/福利/{number}/{page}")
    Observable<FuliResult> getFulis(@Path("number") int number, @Path("page") int page);
}

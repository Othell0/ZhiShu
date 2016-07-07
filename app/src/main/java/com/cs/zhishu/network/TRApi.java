package com.cs.zhishu.network;


import com.cs.zhishu.model.TREntity;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Othell0 on 2016/7/7.
 */
public interface TRApi {

    @FormUrlEncoded
    @POST("api")
    Call<TREntity> getTRResponse(@Field("key") String key, @Field("info") String info, @Field("userid") String userid);
}

package com.cs.zhishu.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Othell0 on 2016/7/7.
 */
public class TRService {
    public static final String BASE_TRC_URL = "http://www.tuling123.com/openapi/";

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_TRC_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private TRService() {
    }

    public static TRApi createTRService() {
        return retrofit.create(TRApi.class);
    }
}

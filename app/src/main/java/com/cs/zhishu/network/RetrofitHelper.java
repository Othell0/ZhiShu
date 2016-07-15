package com.cs.zhishu.network;

import com.cs.zhishu.base.ZhiShuAPP;
import com.cs.zhishu.model.DailyComment;
import com.cs.zhishu.model.DailyDetail;
import com.cs.zhishu.model.DailyExtraMessage;
import com.cs.zhishu.model.DailyListBean;
import com.cs.zhishu.model.DailyRecommend;
import com.cs.zhishu.model.DailyTypeBean;
import com.cs.zhishu.model.LuanchImageBean;
import com.cs.zhishu.model.ThemesDetails;
import com.cs.zhishu.util.NetWorkUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by Othell0 on 2016/7/13.
 */
public class RetrofitHelper {

    public static final String ZHIHU_DAILY_URL = "http://news-at.zhihu.com/api/4/";

    public static final String ZHIHU_LAST_URL = "http://news-at.zhihu.com/api/3/";

    public static final String BASE_FULI_URL = "http://gank.io/api/";

    public static final String DOUBAN_FULI_URL = "http://www.dbmeinv.com/dbgroup/";

    private static OkHttpClient mOkHttpClient;

    private final ZhiHuDailyAPI mZhiHuApi;

    public static final int CACHE_TIME_LONG = 60 * 60 * 24 * 7;


    public static RetrofitHelper builder() {

        return new RetrofitHelper();
    }

    private RetrofitHelper() {

        initOkHttpClient();

        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(ZHIHU_DAILY_URL)
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        mZhiHuApi = mRetrofit.create(ZhiHuDailyAPI.class);
    }

    public static FuliAPI getFuliApi() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_FULI_URL)
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit.create(FuliAPI.class);
    }

    public static ZhiHuDailyAPI getLastZhiHuApi() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ZHIHU_LAST_URL)
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit.create(ZhiHuDailyAPI.class);
    }

    public static DoubanMeizhiApi getDoubanMeiziApi() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DOUBAN_FULI_URL)
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit.create(DoubanMeizhiApi.class);
    }

    /**
     * 初始化OKHttpClient
     */
    private void initOkHttpClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (mOkHttpClient == null) {
            synchronized (RetrofitHelper.class) {
                if (mOkHttpClient == null) {

                    //设置Http缓存

                    File cacheFile = new File(ZhiShuAPP.getContext().getCacheDir(), "HttpCache");
                    Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb


                    mOkHttpClient = new OkHttpClient.Builder()
                            .cache(cache)
                            .addInterceptor(mRewriteCacheControlInterceptor)
                            .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                            .addInterceptor(interceptor)
                            .retryOnConnectionFailure(true)
                            .connectTimeout(15, TimeUnit.SECONDS)
                            .build();
                }
            }
        }
    }

    private Interceptor mRewriteCacheControlInterceptor = new Interceptor()
    {

        @Override
        public Response intercept(Chain chain) throws IOException
        {

            Request request = chain.request();
            if (!NetWorkUtil.isNetworkConnected())
            {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }
            Response originalResponse = chain.proceed(request);
            if (NetWorkUtil.isNetworkConnected())
            {
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder().header("Cache-Control", cacheControl).removeHeader("Pragma").build();
            } else
            {
                return originalResponse.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_TIME_LONG)
                        .removeHeader("Pragma").build();
            }
        }
    };


    /**
     * 知乎日报Api封装 方便直接调用
     **/

    public Observable<DailyListBean> getLatestNews() {

        return mZhiHuApi.getlatestNews();
    }

    public Observable<DailyListBean> getBeforeNews(String date) {

        return mZhiHuApi.getBeforeNews(date);
    }

    public Observable<DailyDetail> getNewsDetails(int id) {

        return mZhiHuApi.getNewsDetails(id);
    }

    public Observable<LuanchImageBean> getLuanchImage(String res) {

        return mZhiHuApi.getLuanchImage(res);
    }

    public Observable<DailyTypeBean> getDailyType() {

        return mZhiHuApi.getDailyType();
    }

    public Observable<ThemesDetails> getThemesDetailsById(int id) {

        return mZhiHuApi.getThemesDetailsById(id);
    }

    public Observable<DailyExtraMessage> getDailyExtraMessageById(int id) {

        return mZhiHuApi.getDailyExtraMessageById(id);
    }

    public Observable<DailyComment> getDailyLongCommentById(int id) {

        return mZhiHuApi.getDailyLongComment(id);
    }

    public Observable<DailyComment> getDailyShortCommentById(int id) {

        return mZhiHuApi.getDailyShortComment(id);
    }

    public Observable<DailyRecommend> getDailyRecommendEditors(int id) {

        return mZhiHuApi.getDailyRecommendEditors(id);
    }
}

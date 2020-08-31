package com.typedef.gam.di.module;

import com.typedef.gam.model.http.Api;
import com.typedef.gam.utils.Constants;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class HttpModule {

    @Provides
    @Singleton
    Api provideApi(Retrofit retrofit){
        return retrofit.create(Api.class);
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Retrofit.Builder builder,OkHttpClient okHttpClient){
        return builder
                .baseUrl(Api.HOST)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())      // 添加Rxjava2的Adapter
                .addConverterFactory(GsonConverterFactory.create())             // 添加gson解析
                .build();
    }

    @Provides
    @Singleton
    Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }

    /**
     * 在这里设置统一的okhttp缓存管理
     */
    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(OkHttpClient.Builder builder){
        File cacheFile = new File(Constants.PATH_CACHE);
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if(!Constants.isNetworkConnected()){
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if(Constants.isNetworkConnected()){
                    int maxAge = 0;
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Pragma")
                            .build();
                }else{
                    int maxStale = 60 * 60 * 24 * 3;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }
                return response;
            }
        };

        // 设置缓存
        builder.addNetworkInterceptor(cacheInterceptor);
        builder.addInterceptor(cacheInterceptor);
        builder.cache(cache);
        //设置超时
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        return builder.build();
    }

    @Provides
    @Singleton
    OkHttpClient.Builder provideOkHttpClientBuilder(){
        return new OkHttpClient.Builder();
    }
}

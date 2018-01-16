package com.tiange.mvp_retrofit_rxjava_demo.retrofit;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 徐大哈 on 2018/1/11.
 */

public class ApiManager {
    private static ApiManager apiManager;
    private static Retrofit retrofit;
    private static OkHttpClient okHttpClient;
    private static final int DEFAULT_TIME_OUT = 10;

    private ApiManager() {
    }


    public static ApiManager getInstance() {
        if (apiManager == null) {
            synchronized (ApiManager.class) {
                if (apiManager == null)
                    apiManager = new ApiManager();
            }
        }
        return apiManager;
    }

    public ApiService getService() {
        return getRetrofit().create(ApiService.class);
    }

    public Retrofit getRetrofit() {
        if (retrofit == null) {
            synchronized (Retrofit.class) {
                if (retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(ApiConstants.BASE_URL)
                            .client(getOkHttpClient())
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build();
                }
            }
        }
        return retrofit;
    }

    private OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            synchronized (OkHttpClient.class) {
                if (okHttpClient == null) {
                    okHttpClient = new OkHttpClient.Builder()
                            .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                            .writeTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                            .readTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                            .addInterceptor(initInterceptor())
                            .build();
                }
            }
        }
        return okHttpClient;
    }

    private HttpLoggingInterceptor initInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("xudaha", message);
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return httpLoggingInterceptor;
    }








    /**
     *跟文件的上传下载有关的
     */

    private <T> ApiService getRetrofitService(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiConstants.BASE_URL)
                .build();
        ApiService service = retrofit.create(ApiService.class);
        return service;
    }

    private <T> ApiService getRetrofitService(final RetrofitCallback<T> callback) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response proceed = chain.proceed(chain.request());
                return proceed.newBuilder().body(new FileResponseBody<T>(proceed.body(), callback)).build();
            }
        });
        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiConstants.BASE_URL)
                .build();
        ApiService service = retrofit.create(ApiService.class);
        return service;
    }


    /**
     * 文件上传
     *
     * @param baseFileUpload
     * @param callback
     */
    public <T> Call uploadFile(BaseFileUpload baseFileUpload, RetrofitCallback<T> callback) {
        Call call = baseFileUpload.getFileUploadCall(getRetrofitService());
        call.enqueue(callback);
        return call;
    }

    /**
     * 文件下载
     *
     * @param baseFileDownload
     * @param callback
     */
    public <T> Call downloadFile(BaseFileDownload baseFileDownload, RetrofitCallback<T> callback) {
        Call call = baseFileDownload.getFileDownloadCall(getRetrofitService(callback));
        call.enqueue(callback);
        return call;
    }
}

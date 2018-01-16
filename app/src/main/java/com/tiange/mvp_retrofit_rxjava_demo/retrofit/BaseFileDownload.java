package com.tiange.mvp_retrofit_rxjava_demo.retrofit;


import retrofit2.Call;

/**
 * 文件下载请求基类
 */
public abstract class BaseFileDownload {
    public abstract Call getFileDownloadCall(ApiService service);
}

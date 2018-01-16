package com.tiange.mvp_retrofit_rxjava_demo.retrofit;

import retrofit2.Call;

/**
 * 文件上传基类
 */
public abstract class BaseFileUpload {

    public abstract Call getFileUploadCall(ApiService service);
}

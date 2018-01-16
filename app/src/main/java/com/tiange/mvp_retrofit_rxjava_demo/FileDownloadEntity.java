package com.tiange.mvp_retrofit_rxjava_demo;

import com.tiange.mvp_retrofit_rxjava_demo.retrofit.ApiService;
import com.tiange.mvp_retrofit_rxjava_demo.retrofit.BaseFileDownload;

import retrofit2.Call;

/**
 * 测试下载文件请求实体类
 * 必须继承BaseFileDownload
 */
public class FileDownloadEntity extends BaseFileDownload {

    private String name;

    public FileDownloadEntity(String name) {
        this.name = name;
    }

    @Override
    public Call getFileDownloadCall(ApiService service) {
        return service.download(name);
    }
}

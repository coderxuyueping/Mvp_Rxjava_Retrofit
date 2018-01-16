package com.tiange.mvp_retrofit_rxjava_demo;

import com.tiange.mvp_retrofit_rxjava_demo.retrofit.ApiService;
import com.tiange.mvp_retrofit_rxjava_demo.retrofit.BaseFileUpload;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;

/**
 * 测试文件上传请求实体类,必须继承BaseFileUpload
 */
public class FileUploadEntity extends BaseFileUpload{

    private MultipartBody.Part file;
    private Map<String,Object> map;

    public FileUploadEntity(MultipartBody.Part file, Map<String,Object> map) {
        this.file = file;
        this.map = map;
    }

    @Override
    public Call getFileUploadCall(ApiService service) {
        return service.uploadFile(file,map);
    }
}

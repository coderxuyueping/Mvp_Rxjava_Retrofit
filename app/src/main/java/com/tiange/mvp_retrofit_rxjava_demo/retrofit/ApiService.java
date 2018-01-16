package com.tiange.mvp_retrofit_rxjava_demo.retrofit;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by 徐大哈 on 2018/1/11.
 */

public interface ApiService {

    //普通get获取数据
    @GET(ApiConstants.AD)
    Observable<ResponseBody> getAd();

    @GET
    Observable<ResponseBody> getStartAd(@Url String url, @Query("type") int type);

    //提交数据
    @FormUrlEncoded
    @POST("url")
    Observable<ResponseBody> post1(@FieldMap(encoded = true) Map<String,String> param);

    @FormUrlEncoded
    @POST("url")
    Observable<ResponseBody> post2(@Field("id") int id);


    @Multipart
    @POST("casuserroleapi/editUserInfo")
    Call<ResponseBody> uploadFile(@Part MultipartBody.Part file, @PartMap Map<String,Object> map);

    @Streaming
    @GET("edition/testDownload")
    Call<ResponseBody> download(@Query("name") String name);
}

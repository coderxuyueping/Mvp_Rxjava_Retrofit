package com.tiange.mvp_retrofit_rxjava_demo.presenter;

import android.util.Log;

import com.tiange.mvp_retrofit_rxjava_demo.FileDownloadEntity;
import com.tiange.mvp_retrofit_rxjava_demo.FileUploadEntity;
import com.tiange.mvp_retrofit_rxjava_demo.bean.User;
import com.tiange.mvp_retrofit_rxjava_demo.retrofit.ApiConstants;
import com.tiange.mvp_retrofit_rxjava_demo.retrofit.ApiManager;
import com.tiange.mvp_retrofit_rxjava_demo.retrofit.FileRequestBody;
import com.tiange.mvp_retrofit_rxjava_demo.retrofit.RetrofitCallback;
import com.tiange.mvp_retrofit_rxjava_demo.view.MainView;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by 徐大哈 on 2018/1/11.
 */

public class MainPresenterImp extends BasePresentImpl<MainView> implements MainPresenter{


    public MainPresenterImp(MainView baseView) {
        super(baseView);
    }

    @Override
    public void loadData1() {
        ApiManager.getInstance().getService().getAd()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                        view.showProgress();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        //Log.d("xudaha", new String(responseBody.bytes()));
                        view.dismissProgress();
                        //回传数据到视图层
                        view.setData1(responseBody);
                        view.showToast("成功");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.dismissProgress();
                    }
                });
    }

    @Override
    public void loadData2() {
        ApiManager.getInstance().getService().getStartAd(ApiConstants.SERVER+ApiConstants.START_ADS, 1)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                        view.showProgress();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        Log.d("xudaha", new String(responseBody.bytes()));
                        view.dismissProgress();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.dismissProgress();
                    }
                });
    }

    @Override
    public void loadData3() {
        ApiManager.getInstance().getService().getAd()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                        view.showProgress();
                    }
                })
                .flatMap(new Function<ResponseBody, ObservableSource<ResponseBody>>() {
                    @Override
                    public ObservableSource<ResponseBody> apply(ResponseBody responseBody) throws Exception {
                        Log.d("xudaha", new String(responseBody.bytes()));
                        return ApiManager.getInstance().getService().getStartAd(ApiConstants.SERVER+ApiConstants.START_ADS, 1);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        Log.d("xudaha", new String(responseBody.bytes()));
                        view.dismissProgress();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.dismissProgress();
                    }
                });
    }

    /**
     * 下载
     */
    @Override
    public void download() {
        FileDownloadEntity downloadEntity = new FileDownloadEntity("xudaha");
        ApiManager.getInstance().downloadFile(downloadEntity, new RetrofitCallback<ResponseBody>() {
            @Override
            public void onSuccess(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    InputStream is = response.body().byteStream();
                    String path = "/sdcard/retrofittest";
                    File f = new File(path);
                    if(!f.exists()){
                        f.mkdirs();
                    }
                    File file = new File(f, "download.apk");
                    FileOutputStream fos = new FileOutputStream(file);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = bis.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                    }
                    fos.flush();
                    fos.close();
                    bis.close();
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void onLoading(final long total, final long progress) {
            }
        });
    }

    /**
     * 上传
     */
    @Override
    public void upload() {
        Map<String,Object> map = new HashMap<>();
        map.put("address","");
        map.put("gender","");
        map.put("height",42);
        map.put("weight",21);
        map.put("realname","fsd");
        map.put("waist",43);
        map.put("userid",285);
        File file = new File("/sdcard/retrofittest/upload.png");
        RequestBody body1 = RequestBody.create(MediaType.parse("application/otcet-stream"), file);
        RetrofitCallback<User> callback = new RetrofitCallback<User>() {
            @Override
            public void onSuccess(Call<User> call, Response<User> response) {
                Log.d("debug","返回结果--》"+response.body().toString());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void onLoading(final long total, final long progress) {

            }
        };
        FileRequestBody body = new FileRequestBody(body1, callback);
        MultipartBody.Part part = MultipartBody.Part.createFormData("avatarByte",file.getName(),body);
        FileUploadEntity entity = new FileUploadEntity(part,map);
        ApiManager.getInstance().uploadFile(entity, callback);
    }
}

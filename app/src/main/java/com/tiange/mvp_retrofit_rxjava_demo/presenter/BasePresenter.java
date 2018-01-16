package com.tiange.mvp_retrofit_rxjava_demo.presenter;

import io.reactivex.disposables.Disposable;

/**
 * Created by 徐大哈 on 2018/1/13.
 * present的基础接口
 */

public interface BasePresenter {
    void addDisposable(Disposable subscription);
    void unDisposable();
    void destroy();
}

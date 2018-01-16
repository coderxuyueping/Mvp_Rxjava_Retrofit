package com.tiange.mvp_retrofit_rxjava_demo.view;

/**
 * Created by 徐大哈 on 2018/1/11.
 */

public interface BaseView {
    void showProgress();
    void dismissProgress();
    void showToast(String msg);
}

package com.tiange.mvp_retrofit_rxjava_demo.presenter;

/**
 * Created by 徐大哈 on 2018/1/11.
 * Presenter需要基础自基础Presenter,以后根据自己需要的进行扩展
 */

public interface MainPresenter extends BasePresenter{
    void loadData1();
    void loadData2();
    void loadData3();
    void download();
    void upload();
}

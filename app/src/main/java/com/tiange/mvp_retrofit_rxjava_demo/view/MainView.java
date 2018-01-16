package com.tiange.mvp_retrofit_rxjava_demo.view;

import okhttp3.ResponseBody;

/**
 * Created by 徐大哈 on 2018/1/11.
 * view需要基础自基础BaseView，根据自己需要进行扩展
 */

public interface MainView extends BaseView{
    void setData1(ResponseBody responseBody);
}

package com.tiange.mvp_retrofit_rxjava_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.tiange.mvp_retrofit_rxjava_demo.presenter.BasePresenter;
import com.tiange.mvp_retrofit_rxjava_demo.view.BaseView;

/**
 * Created by 徐大哈 on 2018/1/13.
 * 基础Activity，这里只对mvp进行封装
 * 一般需要有一个Presenter，一个view的接口
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView{
    protected P presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = initPresenter();
    }

    public abstract P initPresenter();

    @Override
    protected void onDestroy() {
        if(presenter != null){
            presenter.destroy();
            presenter = null;
        }
        super.onDestroy();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}

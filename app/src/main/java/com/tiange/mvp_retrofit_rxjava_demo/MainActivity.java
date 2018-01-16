package com.tiange.mvp_retrofit_rxjava_demo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import com.tiange.mvp_retrofit_rxjava_demo.presenter.MainPresenter;
import com.tiange.mvp_retrofit_rxjava_demo.presenter.MainPresenterImp;
import com.tiange.mvp_retrofit_rxjava_demo.view.MainView;
import java.io.IOException;
import okhttp3.ResponseBody;


public class MainActivity extends BaseActivity<MainPresenter> implements View.OnClickListener, MainView{

    private ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                presenter.loadData1();
                break;
            case R.id.button2:
                presenter.loadData2();
                break;
            case R.id.button3:
                presenter.loadData3();
                break;
        }
    }


    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public MainPresenter initPresenter() {
        return new MainPresenterImp(this);
    }

    @Override
    public void setData1(ResponseBody responseBody) {
        try {
            Log.d("xudaha", new String(responseBody.bytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

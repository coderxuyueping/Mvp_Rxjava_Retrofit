package com.tiange.mvp_retrofit_rxjava_demo.presenter;

import com.tiange.mvp_retrofit_rxjava_demo.view.BaseView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by 徐大哈 on 2018/1/13.
 * BasePresentImpl的基础实现类，一般都需要有个以view
 */

public abstract class BasePresentImpl<V extends BaseView> implements BasePresenter{

    protected V view;
    //将所有正在处理的Subscription都添加到CompositeSubscription中。统一退出的时候注销观察
    private CompositeDisposable mCompositeDisposable;

    public BasePresentImpl(V baseView){
       view = baseView;
    }

    @Override
    public void destroy() {
        view = null;
        unDisposable();
    }


    /**
     * 将Disposable添加
     *
     * @param subscription
     */
    @Override
    public void addDisposable(Disposable subscription) {
        //csb 如果解绑了的话添加 sb 需要新的实例否则绑定时无效的
        if (mCompositeDisposable == null || mCompositeDisposable.isDisposed()) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(subscription);
    }

    /**
     * 在界面退出等需要解绑观察者的情况下调用此方法统一解绑，防止Rx造成的内存泄漏
     */
    @Override
    public void unDisposable() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }
}

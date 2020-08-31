package com.typedef.gam.presenter.base;


import com.typedef.gam.model.DataManager;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 完成与View的关联，并使用一个CompositeDisposable对所有订阅者的生命周期进行管理
 * @param <T>
 */
public class BaseRxPresenter<T extends IView> implements IPresenter<T> {

    protected T mView;
    protected CompositeDisposable mCompositeDisposable;
    protected DataManager mDataManager;

    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
        clearSubscribe();
    }

    protected void addSubscribe(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    protected void clearSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }
}

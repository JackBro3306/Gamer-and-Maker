package com.typedef.gam.model.rx;

import android.text.TextUtils;

import com.typedef.gam.presenter.base.IView;

import io.reactivex.observers.ResourceObserver;
import retrofit2.HttpException;

/**
 * 统一观察者
 * @param <T>
 */
public abstract class CommonObserver<T> extends ResourceObserver<T> {

    String ErrorMsg;
    private IView mView;

    protected CommonObserver(IView view, String ErrorMsg) {
        this.mView = view;
        this.ErrorMsg = ErrorMsg;
    }

    protected CommonObserver(IView view) {
        this.mView = view;
    }

    @Override
    public void onError(Throwable t) {
        if (mView == null) {
            return;
        }
        if (ErrorMsg != null && !TextUtils.isEmpty(ErrorMsg)) {
            mView.showErrorMsg(ErrorMsg);
        } else if (t instanceof HttpException) {
            mView.showErrorMsg("数据加载失败~");
        } else {
            mView.showErrorMsg("未知错误= = ！");
        }
//        mView.showErrorView();
    }

    @Override
    public void onComplete() {

    }
}

package com.typedef.gam.presenter.base;

/**
 * 基础presenter，完成与View的绑定
 * @param <T>
 */
public interface IPresenter<T extends IView> {
    void attachView(T view);
    void detachView();
}

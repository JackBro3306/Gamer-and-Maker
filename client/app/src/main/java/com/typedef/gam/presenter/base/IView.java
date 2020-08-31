package com.typedef.gam.presenter.base;

/**
 * 基础View，显示通用的视图
 */
public interface IView {
    void showErrorMsg(String msg);

    void showContentView();
    void showLoadingView();
    void showErrorView();
}

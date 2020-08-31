package com.typedef.gam.ui.base;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.typedef.gam.R;
import com.typedef.gam.presenter.base.IPresenter;

public abstract class RootActivity<T extends IPresenter> extends BaseMvpActivity<T> {
    private static final int SHOW_MAIN = 0x00;
    private static final int SHOW_LOADING = 0x01;
    private static final int SHOW_ERROR = 0x02;

    private int currentState = SHOW_MAIN;

    private View errorView;

    private View loadingView;
    private ImageView ivLoading;
    private ViewGroup contentView;

    private ViewGroup mParent;

    private boolean isErrorViewAdded = false;

    @Override
    protected void initEventAndData() {

        contentView = findViewById(R.id.view_main);
        if (contentView == null) {
            throw new IllegalStateException(
                    "The subclass of RootFragment must contain a View named 'view_main'.");
        }
        if (!(contentView.getParent() instanceof ViewGroup)) {
            throw new IllegalStateException(
                    "view_main's ParentView should be a ViewGroup.");
        }

        mParent = (ViewGroup) contentView.getParent();
        loadingView = LayoutInflater.from(mContext).inflate(R.layout.view_loading, mParent,false);
        ivLoading =  loadingView.findViewById(R.id.iv_progress);
        mParent.addView(loadingView);

        loadingView.setVisibility(View.GONE);
        contentView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showContentView() {
        if(currentState == SHOW_MAIN)
            return;
        hideCurrentView();
        currentState = SHOW_MAIN;
        contentView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoadingView() {
        if(currentState == SHOW_LOADING){
            return;
        }
        hideCurrentView();
        currentState = SHOW_LOADING;
        loadingView.setVisibility(View.VISIBLE);
        Glide.with(this).asGif().load(R.drawable.buzhihuowu).into(ivLoading);
    }

    private void hideCurrentView() {
        switch (currentState) {
            case SHOW_MAIN:
                contentView.setVisibility(View.GONE);
                break;
            case SHOW_LOADING:
//                ivLoading.stop();
                loadingView.setVisibility(View.GONE);
                break;
            case SHOW_ERROR:
                if (errorView != null) {
                    errorView.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    public void showErrorView() {
        if (currentState == SHOW_ERROR)
            return;
        if (!isErrorViewAdded) {
            isErrorViewAdded = true;
            errorView = LayoutInflater.from(mContext).inflate(R.layout.view_error, mParent,false);
            mParent.addView(errorView);
            if (errorView == null) {
                throw new IllegalStateException(
                        "A View should be named 'view_error' in ErrorLayoutResource.");
            }
        }
        hideCurrentView();
        currentState = SHOW_ERROR;
        errorView.setVisibility(View.VISIBLE);

    }
}

package com.typedef.gam.ui.article;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.NestedScrollAgentWebView;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebViewClient;
import com.typedef.gam.R;
import com.typedef.gam.model.bean.ArticleBean;
import com.typedef.gam.presenter.article.ArticleContract;
import com.typedef.gam.ui.base.BaseMvpActivity;
import com.typedef.gam.utils.Constants;
import com.typedef.gam.utils.WebUtils;

import butterknife.BindView;

public class ActicleDetailActivity extends AppCompatActivity{
    private static final String[] HIDE_DOM_IDS = {"build-qrcode-btn"};
    private static final String[] HIDE_DOM_CLASSIES = {"navbar","footerdiv","zdy-btn"};

    private AgentWeb mAgentWeb;
    private Handler handler = new Handler(Looper.getMainLooper());

    @BindView(R.id.content_layout)
    CoordinatorLayout mContent;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private ArticleBean mArticle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acticle_detail);
        Bundle extras = getIntent().getExtras();
        setSupportActionBar(mToolbar);
        mArticle = (ArticleBean) extras.getSerializable(Constants.BUNDLE_ARTICLE);
        if(mArticle != null){
            CoordinatorLayout.LayoutParams layoutParams = new CoordinatorLayout.LayoutParams(-1, -1);
            layoutParams.setBehavior(new AppBarLayout.ScrollingViewBehavior());
            NestedScrollAgentWebView mNestedWebView = new NestedScrollAgentWebView(this);
            mAgentWeb = AgentWeb.with(this)
                    .setAgentWebParent( mContent, layoutParams)
                    .useDefaultIndicator()
                    .setWebView(mNestedWebView)
                    .setWebChromeClient(mWebChromeClient)
                    .setWebViewClient(mWebViewClient)
                    .createAgentWeb()
                    .ready()
                    .go(mArticle.getArticleUrl());
        }
    }

    private WebChromeClient mWebChromeClient = new WebChromeClient(){
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view,newProgress);
            if(newProgress == 100){
                mAgentWeb.getWebCreator().getWebView().loadUrl(WebUtils.removeDOMByClassName(HIDE_DOM_CLASSIES));
                mAgentWeb.getWebCreator().getWebView().loadUrl(WebUtils.removeDOMById(HIDE_DOM_IDS));
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAgentWeb.getWebCreator().getWebView().setVisibility(View.VISIBLE);
                    }
                },300);
            }
        }

    };

    private WebViewClient mWebViewClient = new WebViewClient(){
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            mAgentWeb.getWebCreator().getWebView().setVisibility(View.INVISIBLE);
            super.onPageStarted(view, url, favicon);
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mAgentWeb!=null&&mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        if(mAgentWeb!=null){
            mAgentWeb.getWebLifeCycle().onResume();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (mAgentWeb != null){
            mAgentWeb.getWebLifeCycle().onPause();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if(mAgentWeb != null){
            mAgentWeb.getWebLifeCycle().onDestroy();
        }
        super.onDestroy();
    }

}

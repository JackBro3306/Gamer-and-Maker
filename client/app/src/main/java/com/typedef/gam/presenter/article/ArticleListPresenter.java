package com.typedef.gam.presenter.article;

import com.typedef.gam.model.DataManager;
import com.typedef.gam.model.bean.ArticleBean;
import com.typedef.gam.model.http.Api;
import com.typedef.gam.model.http.info.ResponseInfo;
import com.typedef.gam.model.rx.CommonObserver;
import com.typedef.gam.presenter.base.BaseRxPresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ArticleListPresenter extends BaseRxPresenter<ArticleListContract.View>
        implements ArticleListContract.Presenter {
    private int page = 1;
    private int tid ;
    @Inject
    public ArticleListPresenter(DataManager dataManager){
        this.mDataManager = dataManager;
    }
    @Override
    public void loadData(int tid) {
        page = 1;
        this.tid = tid;
        requestData(tid,page,false);
    }

    @Override
    public void loadMoreData() {
        requestData(tid,++page,true);
    }

    private void requestData(int tid,int page,boolean isloadMore){
        addSubscribe(mDataManager.loadArticle(tid,page)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Function<ResponseInfo<List<ArticleBean>>, List<ArticleBean>>() {
                    @Override
                    public List<ArticleBean> apply(ResponseInfo<List<ArticleBean>> responseInfo) throws Exception {
                        if(responseInfo.getCode() != Api.CODE_OK){
                            return null;
                        }else{
                            return responseInfo.getData();
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CommonObserver<List<ArticleBean>>(mView) {
                    @Override
                    public void onNext(List<ArticleBean> articleBeans) {
                        if(articleBeans == null){
                            mView.showErrorMsg("加载数据失败");
                            return;
                        }
                        if(isloadMore){
                            mView.showMore(articleBeans);
                        }else{
                            mView.showData(articleBeans);
                        }

                    }
                }));
    }
}

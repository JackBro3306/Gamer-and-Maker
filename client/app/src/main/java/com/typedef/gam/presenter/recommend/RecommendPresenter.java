package com.typedef.gam.presenter.recommend;

import com.typedef.gam.model.DataManager;
import com.typedef.gam.model.bean.ArticleBean;
import com.typedef.gam.model.bean.RecommendBean;
import com.typedef.gam.model.http.Api;
import com.typedef.gam.model.http.info.ResponseInfo;
import com.typedef.gam.model.rx.CommonObserver;
import com.typedef.gam.presenter.base.BaseRxPresenter;
import com.typedef.gam.utils.RxUtil;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RecommendPresenter extends BaseRxPresenter<RecommendContract.View> implements RecommendContract.Presenter {
    @Inject
    public RecommendPresenter(DataManager dataManager){
        this.mDataManager = dataManager;
    }

    int page = 1;
    @Override
    public void loadData() {
        addSubscribe(mDataManager.loadRecommend()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Function<ResponseInfo<RecommendBean>, RecommendBean>() {
                    @Override
                    public RecommendBean apply(ResponseInfo<RecommendBean> responseInfo) throws Exception {
                        if(responseInfo.getCode() != Api.CODE_OK){
                            return null;
                        }else{
                            return responseInfo.getData();
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CommonObserver<RecommendBean>(mView) {
            @Override
            public void onNext(RecommendBean recommendBean) {
                if(recommendBean == null){
                    mView.showErrorMsg("加载数据失败");
                    return;
                }
                mView.showRecommend(recommendBean);
            }
        }));
    }

    @Override
    public void loadMoreData() {
        addSubscribe(mDataManager.loadArticle(4,++page)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Function<ResponseInfo<List<ArticleBean>>,List<ArticleBean>>() {
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
                        mView.showMore(articleBeans);
                    }
                }));
    }
}

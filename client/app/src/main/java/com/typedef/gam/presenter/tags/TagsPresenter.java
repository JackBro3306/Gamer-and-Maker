package com.typedef.gam.presenter.tags;

import com.typedef.gam.model.DataManager;
import com.typedef.gam.model.bean.NavTagBean;
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

public class TagsPresenter extends BaseRxPresenter<TagsContract.View> implements TagsContract.Presenter {
    @Inject
    public TagsPresenter(DataManager dataManager){
        this.mDataManager = dataManager;
    }
    @Override
    public void loadNavTags() {
        addSubscribe(mDataManager.loadNavTags()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Function<ResponseInfo<List<NavTagBean>>, List<NavTagBean>>() {
                    @Override
                    public List<NavTagBean> apply(ResponseInfo<List<NavTagBean>> responseInfo) throws Exception {
                        if(responseInfo.getCode() != Api.CODE_OK){
                            return null;
                        }else{
                            return responseInfo.getData();
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CommonObserver<List<NavTagBean>>(mView) {
                    @Override
                    public void onNext(List<NavTagBean> navTagBeans) {
                        if(navTagBeans == null){
                            mView.showErrorMsg("加载数据失败");
                            return;
                        }
                        mView.showNavTags(navTagBeans);
                    }
                }));
    }
}

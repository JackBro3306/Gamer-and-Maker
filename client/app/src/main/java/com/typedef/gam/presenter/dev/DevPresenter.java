package com.typedef.gam.presenter.dev;

import com.typedef.gam.model.DataManager;
import com.typedef.gam.presenter.base.BaseRxPresenter;

import javax.inject.Inject;

public class DevPresenter extends BaseRxPresenter<DevContract.View> implements DevContract.Presenter {
    @Inject
    public DevPresenter(DataManager dataManager){
        mDataManager = dataManager;
    }
}

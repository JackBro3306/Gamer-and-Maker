package com.typedef.gam.di.component;


import com.typedef.gam.di.module.AppModule;
import com.typedef.gam.di.module.HttpModule;
import com.typedef.gam.model.DataManager;
import com.typedef.gam.model.http.RetrofitHelper;

import javax.inject.Singleton;

import dagger.Component;

/**
 * 提供全局单例类的单例
 */
@Singleton
@Component(modules = {AppModule.class, HttpModule.class})
public interface AppComponent {
    RetrofitHelper retrofitHelper();
    DataManager getDataManager();
}

package com.typedef.gam.di.module;

import com.typedef.gam.App;
import com.typedef.gam.model.DataManager;
import com.typedef.gam.model.http.ApiHelper;
import com.typedef.gam.model.http.RetrofitHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private final App app;
    public AppModule(App app){
        this.app = app;
    }

    @Provides
    @Singleton
    App provideApp(){
        return app;
    }

    @Provides
    @Singleton
    ApiHelper provideHttpHelper(RetrofitHelper retrofitHelper){
        return retrofitHelper;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(ApiHelper apiHelper) {
        return new DataManager(apiHelper);
    }
}

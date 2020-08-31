package com.typedef.gam.di.component;


import com.typedef.gam.di.module.ActivityModule;
import com.typedef.gam.di.scope.ActivityScope;
import com.typedef.gam.ui.article.ArticleListActivity;
import com.typedef.gam.ui.main.MainActivity;
import com.typedef.gam.ui.main.SplashActivity;

import dagger.Component;

@ActivityScope
@Component(modules = ActivityModule.class,dependencies = AppComponent.class)
public interface ActivityComponent {
    void inject(SplashActivity splashActivity);
    void inject(MainActivity mainActivity);
    void inject(ArticleListActivity articleListActivity);
}

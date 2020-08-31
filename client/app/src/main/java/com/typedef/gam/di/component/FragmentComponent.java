package com.typedef.gam.di.component;



import com.typedef.gam.di.module.FragmentModule;
import com.typedef.gam.di.scope.FragmentScope;
import com.typedef.gam.ui.article.ArticleListFragment;
import com.typedef.gam.ui.dev.DevFragment;
import com.typedef.gam.ui.recommend.RecommendFragment;
import com.typedef.gam.ui.tags.TagsFragment;

import dagger.Component;

@FragmentScope
@Component(modules = FragmentModule.class,dependencies = AppComponent.class)
public interface FragmentComponent {
    void inject(RecommendFragment recommendFragment);
    void inject(TagsFragment tagsFragment);
    void inject(DevFragment devFragment);
    void inject(ArticleListFragment articleListFragment);
}

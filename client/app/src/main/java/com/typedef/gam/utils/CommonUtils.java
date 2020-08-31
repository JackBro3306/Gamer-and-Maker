package com.typedef.gam.utils;

import android.content.Context;
import android.content.Intent;

import com.typedef.gam.model.bean.ArticleBean;
import com.typedef.gam.model.bean.TagsBean;
import com.typedef.gam.ui.article.ActicleDetailActivity;
import com.typedef.gam.ui.article.ArticleListActivity;

public class CommonUtils {
    public static void startArticleDetailActivity(Context context,  String articleTitle,
                                                  String imgUrl,String articleLink) {
        Intent intent = new Intent(context, ActicleDetailActivity.class);
        intent.putExtra(Constants.BUNDLE_ARTICLE, new ArticleBean(articleTitle,imgUrl,articleLink));
        context.startActivity(intent);
    }

    public static void startArticleListActivity(Context context,
                                                String type,int tid){
        Intent intent = new Intent(context, ArticleListActivity.class);
        intent.putExtra(Constants.BUNDLE_ARTICLE_LIST, new TagsBean(type,tid));
        context.startActivity(intent);
    }
}

package com.typedef.gam.utils;

import com.typedef.gam.App;

import java.io.File;

public class Constants {
    public static final String PATH_DATA= App.getApplication().getCacheDir().getAbsolutePath()+ File.separator+"data";
    public static final String PATH_CACHE=PATH_DATA+"/NetCache";


    public static final String BUNDLE_ARTICLE = "bundle_article";

    public static final String BUNDLE_ARTICLE_LIST = "article_list";

    public static volatile NetType currentNetType = NetType.NONE;
    public static boolean isNetworkConnected(){
        return currentNetType != NetType.NONE;
    }
    public enum NetType{
        NONE,
        WIFI,
        CMWAP,
        AUTO
    }
}

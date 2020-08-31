package com.typedef.gam;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkRequest;

import com.typedef.gam.di.component.AppComponent;
import com.typedef.gam.di.component.DaggerAppComponent;
import com.typedef.gam.di.module.AppModule;
import com.typedef.gam.utils.NetworkCallbackImpl;

import java.util.HashSet;
import java.util.Set;

public class App extends Application {
    private static App application;
    public static App getApplication(){
        return application;
    }

    private static AppComponent appComponent;

    public static AppComponent getAppComponent(){
        if(appComponent == null){
            appComponent = DaggerAppComponent.builder().appModule(new AppModule(application)).build();
        }
        return appComponent;

    }

    private static Set<Activity> activities;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        registNetworkInfo();
    }

    /**
     * 监听网络状态
     */
    private void registNetworkInfo(){
        NetworkCallbackImpl networkCallback = new NetworkCallbackImpl();
        NetworkRequest.Builder builder = new NetworkRequest.Builder();
        NetworkRequest request = builder.build();
        ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connMgr != null) {
            connMgr.registerNetworkCallback(request, networkCallback);
        }
    }

    public void addActivity(Activity activity) {
        if (activities == null) {
            activities = new HashSet<>();
        }
        activities.add(activity);
    }

    public void removeActivity(Activity activity) {
        if (activities != null) {
            activities.remove(activity);
        }
    }

    public static void exitApp() {
        if (activities != null) {
            synchronized (activities) {
                for (Activity activity : activities) {
                    activity.finish();
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}

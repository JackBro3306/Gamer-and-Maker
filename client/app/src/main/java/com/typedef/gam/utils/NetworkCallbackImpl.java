package com.typedef.gam.utils;

import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.util.Log;

import androidx.annotation.NonNull;

public class NetworkCallbackImpl extends ConnectivityManager.NetworkCallback {
    @Override
    public void onAvailable(@NonNull Network network) {
        super.onAvailable(network);
        Log.d("网络状态", "onAvailable: 网络已连接");
        Constants.currentNetType = Constants.NetType.AUTO;
    }

    @Override
    public void onLost(@NonNull Network network) {
        super.onLost(network);
        Log.d("网络状态", "onLost: 网络已断开");
        Constants.currentNetType = Constants.NetType.NONE;
    }

    @Override
    public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities);
        if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
//                Log.d("网络状态", "onCapabilitiesChanged: 网络类型为wifi");
                Constants.currentNetType = Constants.NetType.WIFI;
            } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
//                Log.d("网络状态", "onCapabilitiesChanged: 蜂窝网络");
                Constants.currentNetType = Constants.NetType.CMWAP;
            } else {
//                Log.d("网络状态", "onCapabilitiesChanged: 其他网络");
                Constants.currentNetType = Constants.NetType.AUTO;
            }
        }
    }

    @Override
    public void onLinkPropertiesChanged(@NonNull Network network, @NonNull LinkProperties linkProperties) {
        super.onLinkPropertiesChanged(network, linkProperties);
    }
}

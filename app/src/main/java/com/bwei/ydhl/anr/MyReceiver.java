package com.bwei.ydhl.anr;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;

import java.sql.Connection;

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }

    // android:exported 属性详解 http://blog.csdn.net/watermusicyes/article/details/46460347

    // 网络切换广播  http://blog.csdn.net/lonely_fireworks/article/details/7373166
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {

            Log.e("tag","onReceive " + intent.getAction());

            ConnectivityManager  connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo.State wifiState =  connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
            NetworkInfo.State mobileState =  connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
            System.out.println("wifiState = " + wifiState);
            System.out.println("mobileState = " + mobileState);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo() ;
            if(networkInfo != null){
                //
                System.out.println(" connectivityManager.getActiveNetworkInfo().isAvailable() " + connectivityManager.getActiveNetworkInfo().isAvailable());

            }else {
                // 没有网络
            }
        }


//
//        if (wifiState != null && mobileState != null
//                && NetworkInfo.State.CONNECTED != wifiState
//                && NetworkInfo.State.CONNECTED == mobileState) {
//            // 手机网络连接成功
//        } else if (wifiState != null && mobileState != null
//                && NetworkInfo.State.CONNECTED != wifiState
//                && NetworkInfo.State.CONNECTED != mobileState) {
//            // 手机没有任何的网络
//        } else if (wifiState != null && NetworkInfo.State.CONNECTED == wifiState) {
//            // 无线网络连接成功
//        }



    }
}

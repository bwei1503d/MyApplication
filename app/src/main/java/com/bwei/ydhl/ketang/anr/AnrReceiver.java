package com.bwei.ydhl.ketang.anr;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.bwei.ydhl.anr.MyLog;

public class AnrReceiver extends BroadcastReceiver {
    public AnrReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving

        MyLog.e("AnrReceiver " , intent.getAction());


        //获取链接管理器
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取可用网络信息
        NetworkInfo info =  connectivityManager.getActiveNetworkInfo();

        // info 等于空 可以认为当前手机 没有网络链接

        //获取 wifi 链接的状态
        NetworkInfo.State wifiState =  connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
       //获取移动网络链接的状态
        NetworkInfo.State mobileState = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();



        if(info != null){
            MyLog.e("tag"," NetworkInfo " + info.toString());
        }
        MyLog.e("tag","NetworkInfo wifiState " + wifiState);
        MyLog.e("tag","NetworkInfo mobileState " + mobileState);





    }
}

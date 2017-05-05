package com.bwei.ydhl.ketang.anr;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bwei.ydhl.R;
import com.bwei.ydhl.anr.MyLog;
import com.bwei.ydhl.utils.NetUtil;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class AnrActivity extends Activity implements View.OnClickListener {

    String TAG = getClass().getSimpleName() ;


    //  1 课后作业 读取anr traces.txt
    // 2  获取服务wifi信息
    //3
    // 1 获取当前有没有可用的网络
//    2 如果当前网络不可用 跳转到网络设置界面
    // 3 当前网络可用 ， 获取当前网络的类型
    private Button buttonanr;

    private Button buttonout ;
    private Button buttonNetInfot;
    private Button btnstartwifi;
    private Button btnstopwifi;
    private WifiManager wifiManager;
    private Button btnUIThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anr);


        buttonanr = (Button) findViewById(R.id.click_anr);
        buttonanr.setOnClickListener(this);


        buttonout = (Button) findViewById(R.id.click_syout);
        buttonout.setOnClickListener(this);

        buttonNetInfot = (Button) findViewById(R.id.get_netinfor);
        buttonNetInfot.setOnClickListener(this);

        btnstartwifi = (Button) findViewById(R.id.start_wifi);
        btnstartwifi.setOnClickListener(this);
        btnstopwifi = (Button) findViewById(R.id.stop_wifi);
        btnstopwifi.setOnClickListener(this);

        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        btnUIThread = (Button) findViewById(R.id.unonuithread);
        btnUIThread.setOnClickListener(this);



    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.click_anr:

                sleepAnr();
                break;
            case R.id.click_syout:
                System.out.println(" = click_syout");

                break;
            case R.id.get_netinfor:
//                wifi();
                break;
            case R.id.start_wifi:
                wifi(true);
                break;
            case R.id.stop_wifi:
//                wifi(false);

//                getWifiResult(wifiManager);

//                Toast.makeText(this, ""+ NetUtil.GetNetype(this), Toast.LENGTH_SHORT).show();

                if(!NetUtil.isNetworkAvailable(this)){
                    NetUtil.toSystemSetting(this);
                }

                break;
            case R.id.unonuithread:
                tothread();
                break;
        }
    }

    private void tothread() {

//        main


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MyLog.e(TAG, "tothread run Thread.currentThread().getName() " + Thread.currentThread().getName());






            }
        });



        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(AnrActivity.this, "11", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }).start();

    }


    private void sleepAnr(){
        try {


            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void wifi(boolean type){

        //获取wifi 管理器 同样 可以获取   ConnectivityManager WifiP2pManager WifiManager

        //开启关闭wifi
        wifiManager.setWifiEnabled(type);

        getWifiResult(wifiManager);





    }

    private void getWifiResult(WifiManager wifiManager) {
        List<ScanResult> list =  wifiManager.getScanResults();

        for (ScanResult result : list) {

            System.out.println("result = " + result);

        }
    }
}

package com.bwei.ydhl.anr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bwei.ydhl.R;
import com.bwei.ydhl.utils.NetUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class AnrActivity extends Activity implements View.OnClickListener {
    private TextView textView;
    private Button buttonAnr;

//    提取全局变量：Ctrl+Alt+F
//    代码格式化：Ctrl+Alt+L

//    提取方法：Shit+Alt+M

    int count = 100 ;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 1:
                    count -- ;
                    textView.setText(count+"");
                    handler.sendEmptyMessageDelayed(1,1000);

                    break;
            }

        }
    } ;
    private Button button;
    private Button buttonStart;
    private Button buttonclose;
    private Button buttonreadanr;
    private Button buttonui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anr_activity_main2);

        textView = (TextView) findViewById(R.id.anr_showtv);
        buttonAnr = (Button) findViewById(R.id.anr_btn);
        buttonAnr.setOnClickListener(this);

        button = (Button) findViewById(R.id.anr_two_btn);
        button.setOnClickListener(this);


        buttonreadanr = (Button) findViewById(R.id.anr_read);
        buttonreadanr.setOnClickListener(this);


        buttonStart = (Button) findViewById(R.id.anr_startwifi);
        buttonStart.setOnClickListener(this);
        buttonclose = (Button) findViewById(R.id.anr_close);
        buttonclose.setOnClickListener(this);

        handler.sendEmptyMessageDelayed(1,1000);


        buttonui = (Button) findViewById(R.id.anr_unonuithread);
        buttonui.setOnClickListener(this);

        // 1  展示anr 读取anr  分析anr  activity BroadcastReceiver adb pull /data/anr/traces.txt /Users/muhanxi/Desktop/
//       2   网络切换监听 开启Wi-Fi 关闭wifi 列出附近的wifi04-10 20:28:45.550 30403-30403/com.bwei.ydhl I/System.out: result = SSID: 1061, BSSID: 30:fc:68:e1:7e:5f,
// capabilities: [WPA-PSK-CCMP][WPA2-PSK-CCMP], level: -49, frequency: 2437
//        BSSID 接入点的地址
//        SSID 网络的名字，唯一区别WIFI网络的名字
//        Capabilities 网络接入的性能
//        Frequency 当前WIFI设备附近热点的频率(MHz)
//                Level 所发现的WIFI网络信号强度
//3掌握网络是否连接以及网络类型的判断（3G,4G,WIFI等）
//4掌握无网络情况下，跳转设置网络设置界面（对话框实现）
//POST是没有大小限制的。HTTP协议规范也没有进行大小限制，起限制作用的是服务器的处理程序的处理能力。
//        如：在Tomcat下取消POST大小的限制（Tomcat默认2M
//5 http://blog.chinaunix.net/uid-26602509-id-4495786.html


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.anr_btn:

                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.anr_two_btn:

                System.out.println("  anr_two_btn click" );

                break;
            case R.id.anr_startwifi:
                changeWifi(true);
                break;
            case R.id.anr_close:
                changeWifi(false);
                break;
            case R.id.anr_read:
                readAnr();
                break;
            case R.id.anr_unonuithread:

//                touithread();

//                System.out.println("+ NetUtil.GetNetype(this) = " + NetUtil.GetNetype(this));
//                System.out.println("NetUtil.isNetworkAvailable(this) = " + NetUtil.isNetworkAvailable(this));

//                NetUtil.toSystemSetting(this);

                break;
        }
    }




    //切换网络
    private void changeWifi(boolean change){
        // 给予 权限
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        if(change){
            wifiManager.setWifiEnabled(true);
        }else {
            wifiManager.setWifiEnabled(false);
        }

        //获取当前附近的网络信息
        List<ScanResult> list =  wifiManager.getScanResults() ;
        if(list != null){

            for(ScanResult result : list){
                System.out.println("result = " + result);
            }
        }



    }


    private void readAnr(){
        String file = "/data/anr/traces.txt";

        try {
            FileInputStream fileInputStream = new FileInputStream(new File(file));
            ByteArrayOutputStream outPutStream = new ByteArrayOutputStream();
            byte [] buffer = new byte[2 * 1024];
            int length = 0 ;
            try {
                while ( (length = fileInputStream.read(buffer)) != -1 ) {
                    outPutStream.write(buffer,0,length);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }


            System.out.println("fileInputStream = " + new String(outPutStream.toByteArray()) );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }


    //
    private void touithread() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                toUI();
            }
        }).start();


    }

    private void toUI(){

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread = " + Thread.currentThread().getName());
//                Toast.makeText(AnrActivity.this, ""+Thread.currentThread().getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}

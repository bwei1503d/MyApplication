package com.bwei.ydhl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.LruCache;
import android.view.View;

import com.bwei.ydhl.anr.AnrActivity;
import com.bwei.ydhl.async.AsyncTask;
import com.bwei.ydhl.bluetootch.BluetoothActivity;
import com.bwei.ydhl.camera.CamearActivity;
import com.bwei.ydhl.gropulistview.GroupActivity;
import com.bwei.ydhl.httpclient.HttpClientActivity;
import com.bwei.ydhl.httpurlconnection.HttpUrlConnectionActivity;
import com.bwei.ydhl.js.JsActivity;
import com.bwei.ydhl.ketang.httpurlconnectiongetpost.ListActivity;
import com.bwei.ydhl.multithread.MultiActivity;
import com.bwei.ydhl.multithread.MultiThreadActivity;
import com.bwei.ydhl.sensor.SensorActivity;
import com.bwei.ydhl.socket.SocketActivity;
import com.bwei.ydhl.webservice.WebServiceActivity;

import org.apache.http.client.HttpClient;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.activity_main_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, ListActivity.class));

            }
        });






//       IApplication application = (IApplication)  getApplication() ;

    }





    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Object obj) {


    }



    }

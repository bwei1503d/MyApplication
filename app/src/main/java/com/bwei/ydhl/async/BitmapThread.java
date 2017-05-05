package com.bwei.ydhl.async;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;

import com.bwei.ydhl.imageasync.ImageAsync;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by muhanxi on 17/4/16.
 */

public class BitmapThread extends Thread {


    private String path = null ;
    private ImageAsync context;
    private Handler handler ;
    public BitmapThread(String url, ImageAsync context, Handler handler){
        this.path = url;
        this.context = context;
        this.handler = handler ;
    }


    @Override
    public void run() {
        super.run();
        HttpURLConnection connection = null;
        InputStream inputStream = null ;
        Bitmap bitmap = null ;

        try {
            URL url = new URL(path);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            if(connection.getResponseCode() == 200){

                inputStream = connection.getInputStream() ;

                bitmap = BitmapFactory.decodeStream(inputStream);
                context.addBitmapToMemory(path,bitmap);
//                handler
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(connection != null){
                    connection.disconnect();
                }
                if(inputStream != null){
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}

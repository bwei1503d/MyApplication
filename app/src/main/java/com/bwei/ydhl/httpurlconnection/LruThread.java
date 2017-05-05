package com.bwei.ydhl.httpurlconnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.LruCache;

import com.bwei.ydhl.disklrucache.DiskLruCache;
import com.bwei.ydhl.upload.FileUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by muhanxi on 17/4/16.
 */

public class LruThread extends Thread {

    LruCache<String,Bitmap> cache ;
    String path;
    Handler handler ;
    DiskLruCache disklurcache ;
    public LruThread(LruCache cache,String path, Handler handler,DiskLruCache disklrucache ){
        this.cache = cache;
        this.path = path;
        this.handler = handler;
        this.disklurcache = disklrucache;
    }


    @Override
    public void run() {
        super.run();

        InputStream inputStream = null ;
        HttpURLConnection connection = null ;

        try {
            URL url = new URL(path);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);

            if(connection.getResponseCode() == 200){
                System.out.println("LruThread  = 200 " );
                inputStream = connection.getInputStream() ;
                OutputStream outPutStream = null ;

                String key =  FileUtils.hashKeyForDisk(path);
                DiskLruCache.Editor edit =  disklurcache.edit(key);
                if(edit != null){
                    outPutStream =  edit.newOutputStream(0);
                    int length = 0 ;
                    byte [] buffer = new byte[1024];

                    while ((length = inputStream.read(buffer)) != -1) {
                        outPutStream.write(buffer,0,length);
                    }
                    if(edit != null){
                        edit.commit();
                    }else{
                        edit.abort();
                    }
                    disklurcache.flush();
                    DiskLruCache.Snapshot snapshot = (DiskLruCache.Snapshot) disklurcache.get(key) ;
                    if(snapshot != null){
                        cache.put(path,BitmapFactory.decodeStream(snapshot.getInputStream(0)));
                    }
                }else {
                    cache.put(path,BitmapFactory.decodeStream(inputStream));
                }

                handler.sendEmptyMessage(2);
            }
        }catch (Exception e){
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

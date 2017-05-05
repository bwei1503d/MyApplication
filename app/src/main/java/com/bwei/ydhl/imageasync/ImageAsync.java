package com.bwei.ydhl.imageasync;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.LruCache;

import com.bwei.ydhl.R;

import java.lang.ref.WeakReference;

/**
 * Created by muhanxi on 17/4/16.
 */

public class ImageAsync extends Activity{

    private LruCache<String, Bitmap> cache;
    private ActivityManager activityManager;

    public MyHandler handler = new MyHandler(this);

    static class MyHandler extends Handler {
        WeakReference<ImageAsync> weakReference = null ;
        public  MyHandler(ImageAsync activity){
            weakReference  = new WeakReference<ImageAsync>(activity);
        }


        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);


            if(weakReference == null){
                return;
            }


            switch (msg.what) {

                case 1:

                    break;

            }


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.lrucache);


        activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        int memory =  activityManager.getMemoryClass() * 1024 * 1024 / 8;

        // 重写此方法来衡量每张图片的大小，默认返回图片数量。
        cache = new LruCache<String, Bitmap>(memory){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                // 重写此方法来衡量每张图片的大小，默认返回图片数量。
                return  value.getByteCount() ;
            }
        };



    }


    /**
     * 将图片 添加的内存中去
     * @param key
     * @param bitmap
     */
    public void addBitmapToMemory(String key, Bitmap bitmap){


        if(getBitmapFromMemory(key) == null){
            cache.put(key,bitmap);
        }

    }


    /**
     * 在内存中获取 bitmap 对象
     * @param key
     * @return
     */
    public Bitmap getBitmapFromMemory(String key){
        return  cache.get(key);
    }



    public void removeBitmapFromMemory(String key){

        if(TextUtils.isEmpty(key)){
            cache.remove(key);
        }




    }



}

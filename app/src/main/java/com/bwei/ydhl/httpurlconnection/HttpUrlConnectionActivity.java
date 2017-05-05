package com.bwei.ydhl.httpurlconnection;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.bwei.ydhl.R;
import com.bwei.ydhl.adapter.HealthListAdapter;
import com.bwei.ydhl.bean.HealthBean;
import com.bwei.ydhl.bean.HealthList;
import com.bwei.ydhl.bean.Login;
import com.bwei.ydhl.disklrucache.DiskLruCache;
import com.bwei.ydhl.utils.StringUtils;
import com.google.gson.Gson;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HttpUrlConnectionActivity extends Activity {


    ImageView imageView;
    private ListView listView;
    public List<HealthList> list = new ArrayList<HealthList>();
    MyHandler handler = new MyHandler(this);
    public HealthListAdapter adapter;
    private DiskLruCache diskLruCache;


    static class MyHandler extends Handler {

        WeakReference<HttpUrlConnectionActivity> mActivity;

        MyHandler(HttpUrlConnectionActivity activity) {
            mActivity = new WeakReference<HttpUrlConnectionActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 1:
                    List<HealthList> temp = (List<HealthList>) msg.obj;
                    mActivity.get().list.addAll(temp);
                    mActivity.get().adapter.notifyDataSetChanged();

                    break;
                case 2:
                    mActivity.get().adapter.notifyDataSetChanged();
                    break;
            }

        }
    }

    LruCache<String,Bitmap> lruCache  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_url_connection);


        listView = (ListView) findViewById(R.id.httpurl_listview);
        adapter = new HealthListAdapter(this, list);
        listView.setAdapter(adapter);


        final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        lruCache = new LruCache<String, Bitmap>(activityManager.getMemoryClass() * 1024 * 1024 / 8){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                System.out.println("key sizeOf = " + value.getByteCount() + "  " + activityManager.getMemoryClass());
                return  value.getByteCount()  ;
            }
        };

        diskLruCache = openDiskLruCache(this);

        getData();




    }


    public void loadImage(String path ,  ImageView imageView){

        if(lruCache.get(path) == null){
            // 从网络加载
            try {
                String key = FileUtils.hashKeyForDisk(path) ;
                DiskLruCache.Snapshot snapshot = (DiskLruCache.Snapshot) diskLruCache.get(key);
                if(snapshot != null){
                   InputStream inputStream =  snapshot.getInputStream(0) ;
                    if(inputStream != null){
                        imageView.setImageBitmap(BitmapFactory.decodeStream(inputStream));
                    }
                }else{
                    new LruThread(lruCache,path,handler,diskLruCache).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }



        } else {
            System.out.println("LruCache  = memory " );

            imageView.setImageBitmap(lruCache.get(path));

        }
    }


    public DiskLruCache openDiskLruCache(Context context){
        DiskLruCache mDiskLruCache = null;
        try {
            File cacheDir = FileUtils.getDiskCacheDir(context, "image");
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            mDiskLruCache = DiskLruCache.open(cacheDir, FileUtils.getAppVersion(context), 1, 6 * 1024 * 1024);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  mDiskLruCache ;
    }



    // 获取健康咨询


//    聚盒子 医药健康健康资讯接口测试 (返回旧版本)
    private void getData() {

        new Thread(new Runnable() {
            @Override
            public void run() {


                String path = "http://op.juhe.cn/yi18/news/list?dtype=json&limit=5&page=1&id=1&key=a85a2e1395bd2bf329952e63dd48586b";

//String path = "http://qhb.2dyt.com/Bwei/login?username=11&password=12&postkey=bwei";
                URL geturl = null;
                InputStream inputStream = null;
                HttpURLConnection httpURLConnection = null;
                try {
                    geturl = new URL(path);
                    httpURLConnection = (HttpURLConnection) geturl.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setConnectTimeout(20000);
                    httpURLConnection.setReadTimeout(20000);
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setDoOutput(true);

                    System.out.println(httpURLConnection.getResponseCode());
                    if (httpURLConnection.getResponseCode() == HttpStatus.SC_OK) {
                        inputStream = httpURLConnection.getInputStream();
                        String result = StringUtils.inputStreamToString(inputStream);
                        System.out.println("result = " + result);

                        try {
                            com.alibaba.fastjson.JSONObject jsonObject = (com.alibaba.fastjson.JSONObject) JSON.parseObject(result);
                            String resultString = jsonObject.getString("result");
                            com.alibaba.fastjson.JSONObject objectResult =  JSON.parseObject(resultString);
                            String listString = objectResult.getString("list");

                            List<HealthList> resultList =  JSON.parseArray(listString,HealthList.class);
                            Message message = Message.obtain();
                            message.what = 1;
                            message.obj = resultList ;
                            handler.sendMessage(message);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();


    }


    public void showImage() throws Exception {

        String path = "http://img05.tooopen.com/images/20150202/sy_80219211654.jpg";
        URL url = new URL(path);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setConnectTimeout(5000);
        if (httpURLConnection.getResponseCode() == 200) {
            InputStream inputStream = httpURLConnection.getInputStream();

            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            showUIImage(bitmap);
        }

    }


    public void showUIImage(final Bitmap bitmap) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                imageView.setImageBitmap(bitmap);
            }
        });
    }


    // username=木&password=123456&  Map
    private void post(String path, String params) {

        URL url = null;
        try {
            url = new URL(path);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            // 设置 发起请求 连接的时间
            httpURLConnection.setConnectTimeout(20000);
            // 设置 读取服务器 返回数据的时间
            httpURLConnection.setReadTimeout(20000);

            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(params.getBytes());
            outputStream.flush();

            if (httpURLConnection.getResponseCode() == 200) {

                InputStream inputStream = httpURLConnection.getInputStream();
                String result = StringUtils.inputStreamToString(inputStream);
                System.out.println("post result = " + result);

            }




        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void get(String path) {

        try {
            // 创建URL
            URL url = new URL(path);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(true);
//            httpURLConnection.addRequestProperty();
            //获得链接 返回 响应码
            if (httpURLConnection.getResponseCode() == 200) {

                //获取服务器返回的数据
                InputStream inputStream = httpURLConnection.getInputStream();
                String result = StringUtils.inputStreamToString(inputStream);
                System.out.println("result = " + result);

                Login login = JSON.parseObject(result, Login.class);
                System.out.println("login = " + login.toString());
                Gson gson = new Gson();
                Login gsonlogin = gson.fromJson(result, Login.class);
                System.out.println("gsonlogin login = " + gsonlogin.toString());

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //  HttpURLConnection 注意事项
//    1 post请求，参数要放在 http正文内，因此需要设为true, 默认情况下是false;
//    httpUrlConnection.setDoOutput(true); 看源代码
    // 设置是否从httpUrlConnection读入，默认情况下是true;
//    httpUrlConnection.setDoInput(true);
//
//  HttpURLConnection的connect()函数，实际上只是建立了一个与服务器的tcp连接，并没有实际发送http请求。
//HttpURLConnection 结构关系
//    if(JSESSIONID != null &&  !"".equals(JSESSIONID)){
//        conn.setRequestProperty("Cookie", "JSESSIONID="+JSESSIONID);
//    }
    //                URLEncoder.encode()


}

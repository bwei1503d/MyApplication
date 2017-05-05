package com.bwei.ydhl.httpclient;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import com.bwei.ydhl.R;
import com.bwei.ydhl.upload.FileUtil;
import com.bwei.ydhl.upload.FormFile;
import com.bwei.ydhl.utils.StringUtils;
import com.bwei.ydhl.utils.VideoServiceTest;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;


import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.xutils.common.Callback;
import org.xutils.x;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;


public class HttpClientActivity extends Activity {

    public static String photoCacheDir = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Bwei";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_client);

//
//new Thread(new Runnable() {
//    @Override
//    public void run() {
//        httpclientPost();
//
//    }
//}).start();


        toPic();


//        new CAsyncTask().execute("http://qhb.2dyt.com/Bwei/login?username=qq&&password=1234&&postkey=bwei");



//        asyncHttpClient();

//       System.out.println(" ip " +  new VideoServiceTest().getLocalIpAddress());
//
//
//        findViewById(R.id.httpclient_btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                toPic();
//
//            }
//        });



    }


    // asynctask 配合 httpclient
    class CAsyncTask extends AsyncTask<String,Integer,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(params[0]);

            try {
                HttpResponse response = client.execute(request);
                if(response.getStatusLine().getStatusCode() == 200){
                   InputStream inputStream =  response.getEntity().getContent();

                   String result =  StringUtils.inputStreamToString(inputStream);

                    return result;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            System.out.println(" s "+s);

        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);


        }
    }






    public void asyncHttpClient(){

        //网页源码查看器 用于网络请求
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://www.baidu.com", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                System.out.println(" AsyncHttpClient onSuccess" + new String(responseBody));

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println(" AsyncHttpClient onFailure " + new String(responseBody));

            }
        }) ;





    }


    public void toPic(){

        if(!new File(photoCacheDir).exists()){
            new File(photoCacheDir).mkdirs();
        }

        Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
        getAlbum.setType("image/*");
        startActivityForResult(getAlbum, 1);

    }

    // 调用相机 上传 path 你们可以换成 本地sdcark 图片路径
    public void uploadFile(String path){
//        String url = "http://10.0.2.2:8080/Bwei/upload" ;
        String url = "http://qhb.2dyt.com/Bwei/upload" ;

        /**
        AsyncHttpClient client = new AsyncHttpClient();



//        String url = "http://qhb.2dyt.com/Bwei/upload" ;

        RequestParams requestParams = new RequestParams();
        String [] arr = path.split("/");


        // 上传的参数
        requestParams.put("imageFileName",arr[arr.length-1]);
        System.out.println(" filename  "+arr[arr.length-1]);

        requestParams.put("username", "111");
        requestParams.put("pwd", "123456");
        requestParams.put("age", "23");
        try {
            // 上传的文件 key 和服务器协商  大部分是 "file"
            requestParams.put("image",new File(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        client.post(url, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                System.out.println(" uploadFile AsyncHttpClient onSuccess" + new String(responseBody));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println(" uploadFile AsyncHttpClient onFailure" + statusCode + "  " + new String(responseBody));
            }
        }) ;

         **/

        // xutils
        String [] arr = path.split("/");
        // 上传的参数
        org.xutils.http.RequestParams params = new org.xutils.http.RequestParams(url);
        params.addBodyParameter("image", new File(path));
        params.addBodyParameter("imageFileName",arr[arr.length-1]);
        params.addBodyParameter("username", "111");
        params.addBodyParameter("pwd", "123456");
        params.addBodyParameter("age", "23");

        x.http().post(params, new Callback.CommonCallback<ResponseEntity>() {

            @Override
            public void onSuccess(ResponseEntity result) {
                System.out.println("result = " + result.getResult());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onFinished() {
                System.out.println("onFinished = " );

            }
        }) ;



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            switch (requestCode) {

                case 1:

                    try {
                        // 相册

                        if (data == null)// 如果没有获取到数据
                            return;
                        Uri originalUri = data.getData();
                        //文件大小判断

                        if (originalUri != null) {
                            File file = null;
                            String[] proj = {MediaStore.Images.Media.DATA};
                            Cursor actualimagecursor = managedQuery(originalUri, proj, null, null, null);
                            if (null == actualimagecursor) {
                                if (originalUri.toString().startsWith("file:")) {
                                    file = new File(originalUri.toString().substring(7, originalUri.toString().length()));
                                    if(!file.exists()){
                                        //地址包含中文编码的地址做utf-8编码
                                        originalUri = Uri.parse(URLDecoder.decode(originalUri.toString(),"UTF-8"));
                                        file = new File(originalUri.toString().substring(7, originalUri.toString().length()));
                                    }
                                }
                            } else {
                                // 系统图库
                                int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                                actualimagecursor.moveToFirst();
                                String img_path = actualimagecursor.getString(actual_image_column_index);
                                if (img_path == null) {
                                    InputStream inputStrean = getContentResolver().openInputStream(originalUri);
                                    file = new File(photoCacheDir+"/aa.jpg");
                                    if(!file.exists()){
                                        file.createNewFile();
                                    }
                                    System.out.println(" - " + file.exists());
                                    FileOutputStream outputStream = new FileOutputStream(file);

                                    byte[] buffer = new byte[1024];
                                    int len = 0;
                                    while ((len = inputStrean.read(buffer)) != -1) {
                                        outputStream.write(buffer, 0, len);
                                    }
                                    outputStream.flush();

                                    if (inputStrean != null) {
                                        inputStrean.close();
                                        inputStrean = null;
                                    }

                                    if (outputStream != null) {
                                        outputStream.close();
                                        outputStream = null;
                                    }
                                } else {
                                    file = new File(img_path);
                                }
                            }
                            String camerFileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
                            File newfilenew = new File(photoCacheDir,camerFileName);
//                            if (!newfilenew.exists()) {
//                                newfilenew.createNewFile();
//                            }
                            FileInputStream inputStream = new FileInputStream(file);
                            FileOutputStream outStream = new FileOutputStream(newfilenew);

                            try {
                                byte[] buffer = new byte[1024];
                                int len = 0;
                                while ((len = inputStream.read(buffer)) != -1) {
                                    outStream.write(buffer, 0, len);
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }finally {
                                try {
                                    inputStream.close();
                                    outStream.close();
                                    inputStream = null;
                                    outStream = null;
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            uploadFile(newfilenew.toString());

//                            uploadFile(newfilenew);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;

            }


        }


        }


    public void httpclientPost(){

        Map map = new HashMap();
        map.put("username","hongjiang");
        map.put("password","1111");
        map.put("postkey","bwei");


        HttpClient client = new DefaultHttpClient();
//        请求超时
        client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
//        读取超时
        client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
        HttpPost post = new HttpPost("http://qhb.2dyt.com/Bwei/login");

        Set<String> set =  map.keySet() ;

        List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();

        for (String key : set){
            list.add(new BasicNameValuePair(key,map.get(key).toString()));
        }

        try {
            post.setEntity(new UrlEncodedFormEntity(list,"utf-8"));
            HttpResponse response =  client.execute(post);
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
               InputStream inputStream =  response.getEntity().getContent() ;

               BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = null ;
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                System.out.println("stringBuilder = " + stringBuilder.toString());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }



    }




    public void uploadFile(final File imageFile) {

            new Thread(new Runnable() {
                @Override
                public void run() {

                    try {

                    String requestUrl = "http://qhb.2dyt.com/UploadService/upload/execute.do" ;

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("username", "111");
                    params.put("pwd", "123456");
                    params.put("age", "23");
                    FormFile formfile = new FormFile(imageFile.getName(), imageFile, "image", "application/octet-stream");
                    boolean result = FileUtil.post(requestUrl, params, formfile);

                        System.out.println(" result "+ result);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();


    }



    // post 请求
    private void httpClientPost(String url , Map map){

        HttpClient httpClient = new DefaultHttpClient();

        HttpPost httpPost = new HttpPost(url);


        List<NameValuePair> list = new ArrayList<NameValuePair>();


        Set<String> set =  map.keySet();
        for (String key : set ) {
            list.add(new BasicNameValuePair(key,(String) map.get(key)));
        }
        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "UTF-8");//设置传递参数的编码
            httpPost.setEntity(entity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }


        try {
            HttpResponse response = httpClient.execute(httpPost);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

/// httpclient httpurlconnection 对比
//
//https://android-developers.googleblog.com/2011/09/androids-http-clients.html
//http://blog.csdn.net/guolin_blog/article/details/12452307
//
//6.0已经把httpclient
//
//    http://www.tuicool.com/articles/JVBja2
//


}

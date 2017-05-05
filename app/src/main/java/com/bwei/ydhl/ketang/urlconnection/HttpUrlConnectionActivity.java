package com.bwei.ydhl.ketang.urlconnection;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import com.alibaba.fastjson.JSON;
import com.bwei.ydhl.R;
import com.bwei.ydhl.bean.Login;
import com.bwei.ydhl.utils.StringUtils;
import com.google.gson.Gson;

import org.apache.http.HttpStatus;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class HttpUrlConnectionActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_url_connection2);

        new Thread(new Runnable() {
            @Override
            public void run() {
//                get("http://qhb.2dyt.com/Bwei/login?username=11&password=wwww&postkey=bwei");

                post("http://qhb.2dyt.com/Bwei/login","username=11&password=wwww&postkey=bwei");
            }
        }).start();

    }


    public void get(String path){

        try {

//            String encodeString =  URLEncoder.encode("木","utf-8");
//            http://www.aaa.com/login?usernmae=encodeString&password=111

            //通过 接口地址 封装成url对象
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection() ;
            connection.setRequestMethod("GET");
            //设置 连接 时间
            connection.setConnectTimeout(20000);
            //设置读取超时时间
            connection.setReadTimeout(20000);

            // 设置允许输入
            connection.setDoInput(true);
            //设置允许输出
            connection.setDoOutput(true);




            //服务器返回的 响应吗 200
            if(connection.getResponseCode() == HttpStatus.SC_OK) {


                //获取服务器 返回的数据
               InputStream inputStream =  connection.getInputStream() ;
               String string =  StringUtils.inputStreamToString(inputStream);

                System.out.println(string);
                Login login = JSON.parseObject(string, Login.class);
                System.out.println("login = " + login.toString());


            }










        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }


    }


    /**
     *
     * @param path 请求路径
     * @param string 请求参数
     */
    private void post(String path, String string){

        try  {

            URL url = new URL(path);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setConnectTimeout(20000);
            connection.setReadTimeout(2000);

            // key=value&key=value
            OutputStream outputStream =  connection.getOutputStream() ;
            outputStream.write(string.getBytes());
            outputStream.flush();
            outputStream.close();


            if(connection.getResponseCode() == HttpStatus.SC_OK){

               InputStream inputStream =   connection.getInputStream() ;
               String   result = StringUtils.inputStreamToString(inputStream);

                System.out.println("result = " + result);

            }


        } catch (Exception e){
            e.printStackTrace();
        }


    }


}

package com.bwei.ydhl.socket;

import android.app.Activity;
import android.os.Bundle;
import android.util.LruCache;
import android.view.View;
import android.widget.Button;

import com.bwei.ydhl.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.ref.PhantomReference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.net.Socket;

public class SocketActivity extends Activity {

    BufferedReader bufferedReader ;
    BufferedWriter bufferedWriter ;


    // xutils 实现下载
    // 上传图片
    //socket ServerSocket 之间通信， 客户端带有输入框 ， 发送什么 接受什么

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);


       Button button = (Button) findViewById(R.id.send_btn);







        initSocket();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });


    }

    private void initSocket() {

        new Thread(new Runnable() {
            @Override
            public void run() {


                try {
                    // localhost
                    // 127.0.0.1
                    Socket socket = new Socket("10.0.2.2",10010);
                    bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                    String line = null;

                    while ( (line = bufferedReader.readLine()) != null){

                        System.out.println(" socket activity reader line " + line );

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();



    }


    private void sendMessage(){

        new Thread(new Runnable() {
            @Override
            public void run() {

                if(bufferedWriter != null){
                    try {
                        bufferedWriter.write("111\n");
                        bufferedWriter.flush();
                        System.out.println("run client");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


    }


}

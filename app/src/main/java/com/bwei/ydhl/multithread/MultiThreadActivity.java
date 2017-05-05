package com.bwei.ydhl.multithread;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.bwei.ydhl.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

public class MultiThreadActivity extends Activity {

    int fileSize ;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {

                case 1 :


                   int currentSize = (int) msg.arg1 ;
                    System.out.println("size = " + currentSize);

                   float size =  (float) currentSize / (float) fileSize * 100 ;

                    progressBar.setProgress((int) size);



                    break;

            }

        }
    } ;

    private ProgressBar progressBar;

    boolean stop = false ;
    private Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_thread);


        Button button = (Button) findViewById(R.id.multi_btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MultiDownloadThread.stop = false ;
                        httpHead("http://qhb.2dyt.com/Bwei/wangyi.apk");

                    }
                }).start();

            }
        });

        progressBar = (ProgressBar) findViewById(R.id.multi_pb);
        progressBar.setMax(100);


        button1 = (Button) findViewById(R.id.multi_btn_stop);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(stop){
                    stop = false;
                    button1.setText("暂停");
                    MultiDownloadThread.stop = false ;





                } else {
                    stop = true;
                    button1.setText("开始");
                    MultiDownloadThread.stop = true ;

                }

            }
        });


    }
    int count = 0 ;

    private void  httpHead(String path){

        int downloadlength = 0 ;
        try {

            try {
//                if(new File(Environment.getExternalStorageDirectory(),"wangyi.apk").exists()){
//                    new File(Environment.getExternalStorageDirectory(),"wangyi.apk").delete();
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            int threadSize = 3 ;
            HttpClient client = new DefaultHttpClient();
            HttpHead httpHead = new HttpHead(path);
            HttpResponse response =  client.execute(httpHead);
            if(response.getStatusLine().getStatusCode() == 200){

                int fileLength = Integer.valueOf(response.getHeaders("Content-length")[0].getValue());
                fileSize = fileLength ;
                System.out.println("fileLength = " + fileLength);

                String [] split =  path.split("/");
                String fileName = split[split.length - 1];

                File file = new File(Environment.getExternalStorageDirectory(),fileName);
                RandomAccessFile randomAccessFile = new RandomAccessFile(file,"rwd");
                randomAccessFile.setLength(fileLength);
                randomAccessFile.close();


                int block = fileLength % threadSize == 0 ? fileLength / threadSize : fileLength / threadSize+ 1 ;

                for(int i = 0; i<3 ;i++){

                    SharedPreferences mShareConfig =
                            getSharedPreferences( i+"", Context.MODE_PRIVATE );

                    int result = mShareConfig.getInt("count",0);


                    File fileCount = new File(Environment.getExternalStorageDirectory(),i+".txt");
                    if(result != 0){

                        try {
                            count = count + result ;
                            MultiDownloadThread.total = count ;
                            Message message = new Message();
                            message.what = 1 ;
                            message.arg1 = count ;
                            handler.sendMessage(message);
                            downloadlength = result ;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    new MultiDownloadThread(this,path,block,i,file,handler,downloadlength).start();
                }





            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }



}

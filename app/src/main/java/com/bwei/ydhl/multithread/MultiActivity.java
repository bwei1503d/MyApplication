package com.bwei.ydhl.multithread;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.bwei.ydhl.R;
import com.bwei.ydhl.httpclient.HttpClientActivity;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class MultiActivity extends Activity {


    EditText editText;
    ProgressBar progressBar;

    int totalSize ;
    Button button ;
    boolean stop = true ;

    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:

                    int size =  msg.arg1 ;

                    int pro =    (int )((float)size / (float)totalSize  * 100)  ;
                    System.out.println("handler size " + pro);

                    progressBar.setProgress(pro);

                    break;
            }

        }
    } ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi);


        editText = (EditText) findViewById(R.id.edittext);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        progressBar.setMax(100);

        findViewById(R.id.multi_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String path = editText.getText().toString().trim();
//                if(!TextUtils.isEmpty(path)){
                    multiThread("");

//                }

            }
        });




    }


    public void multiThread(final String path){

        new Thread(new Runnable() {
            @Override
            public void run() {


                int threadsize = 3 ;
                String path = "http://10.0.2.2:8080/Bwei/wangyi.apk" ;

                try {

                    try {
                        if(new File(Environment.getExternalStorageDirectory(),"wangyi.apk").exists()){
                            new File(Environment.getExternalStorageDirectory(),"wangyi.apk").delete();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    HttpClient httpClient = new DefaultHttpClient();
                    HttpHead head = new HttpHead(path);

                    HttpResponse response = httpClient.execute(head);
                    if(response.getStatusLine().getStatusCode() == 200){
                        int filelength = Integer.valueOf(response.getHeaders("Content-length")[0].getValue());
                        String [] arr = path.split("/");
                        File file = new File(Environment.getExternalStorageDirectory(),arr[arr.length-1]);
                        RandomAccessFile randomAccessFile = new RandomAccessFile(file,"rwd");
                        randomAccessFile.setLength(filelength);
                        randomAccessFile.close();
                        totalSize = filelength;
                        int block = filelength % threadsize == 0 ? filelength / threadsize : (filelength/threadsize + 1) ;
                        for(int threadid = 0;threadid < threadsize;threadid++){
                            new DownloadThread(path,block,threadid,file,filelength,handler).start();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();


    }

}

package com.bwei.ydhl.async;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bwei.ydhl.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AsyncTask extends Activity {

    private Button button;

    private static final int CORE_POOL_SIZE = 3;
    private static final int MAXIMUM_POOL_SIZE = 128;
    private static final int KEEP_ALIVE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);
//        http://zmywly8866.github.io/2015/09/29/android-call-asynctask-excute-not-run.html

        Toast.makeText(this, "" + Runtime.getRuntime().availableProcessors(), Toast.LENGTH_SHORT).show();
        button = (Button) findViewById(R.id.asyn_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    LinkedBlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<Runnable>(128);
                    ExecutorService exec = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS, blockingQueue);

                    for(int i=0;i<10;i++){
//                        ExecutorService exe =  Executors.newFixedThreadPool(3);
                        new CAsyncTask().executeOnExecutor(exec,"11");

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }


                Map map = new HashMap();
                for(int i=0;i<10;i++){
//                    new CAsyncTask().execute("http://qhb.2dyt.com/Bwei/login",map.toString());
                }

            }
        });


    }


    class CAsyncTask extends android.os.AsyncTask<String,Integer,String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                Thread.sleep(2000);


                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                System.out.println("params = " + simpleDateFormat.format(new Date()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            System.out.println(" doInBackground = " + cancel(true));


            return "haha";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
//            System.out.println("button = onCancelled" );

        }
    }
}

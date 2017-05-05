package com.bwei.ydhl.multithread;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.RandomAccessFile;

/**
 * Created by muhanxi on 17/4/4.
 */

public class DownloadThread extends  Thread {

    private String path;
    private int block;
    private int threadid;
    private File file;
    private int start;
    private int end;
    private int total;
    private Handler handler ;

    public static int currentSize ;

    public DownloadThread(String path, int block, int threadid, File file, int total, Handler handler){
        super();
        this.total =total;
        this.handler = handler ;
        this.path = path;
        this.block = block;
        this.threadid = threadid;
        this.file = file;
        start = threadid * block ;
        end = (threadid + 1) * block -1 ;
    }


    @Override
    public void run() {
        super.run();

        try{


            HttpClient client = new DefaultHttpClient();
            System.setProperty("http.keepAlive", "false");
            HttpGet request = new HttpGet(path);
            request.addHeader("Range","bytes:"+start+"-"+end);

            HttpResponse response =  client.execute(request);
            System.out.println("第 " + threadid + "  " + response.getStatusLine().getStatusCode());

            if(response.getStatusLine().getStatusCode() == 206){

              InputStream inputStream =  response.getEntity().getContent() ;

                RandomAccessFile randomAccessFile = new RandomAccessFile(file,"rwd");

                randomAccessFile.seek(start);
                int count = 0;
                byte [] buffer = new byte[1024 * 10];
                int length = 0;
                while ((length = inputStream.read(buffer))!= -1){
                    count  = + length;
                    randomAccessFile.write(buffer,0,length);
                    currentSize = currentSize + length ;
                    System.out.println("第" + threadid + "线程下载中" + " currentsize " + currentSize + "total " + total );


                    Message message = new Message();
                    message.what = 1;
                    message.arg1 = currentSize ;
                    handler.sendMessage(message);
                }
                randomAccessFile.close();
                inputStream.close();
                System.out.println("第" + threadid + "线程下载完成");

            }

            client.getConnectionManager().closeExpiredConnections();


        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

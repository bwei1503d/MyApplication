package com.bwei.ydhl.multithread;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

/**
 * Created by muhanxi on 17/4/13.
 */

public class MultiDownloadThread extends Thread {


    public String path;
    public int block;
    public int threadid;
    public File file;
    public int start;
    public int end ;
    public Handler handler ;
    public int lastlength ;
    public Context context ;
    public  MultiDownloadThread(Context context ,String path, int block, int threadid, File file,Handler handler,int lastlength) {
        this.path = path;
        this.context = context ;
        this.block = block;
        this.threadid = threadid;
        this.file = file;

        this.lastlength = lastlength ;

        this.start = threadid * block + lastlength;
        this.end = (threadid + 1) * block - 1 ;
        this.handler = handler;
    }


    static int total ;

   public  static  boolean stop = false ;

    @Override
    public void run() {
        super.run();

        try {
            int count = 0 ;
            HttpClient client = new DefaultHttpClient();
            System.setProperty("http.keepAlive", "false");
            HttpGet get = new HttpGet(path);
            get.addHeader("Range","bytes:"+start+"-"+end);
            System.out.println("start = " + threadid + "   " + start + "  end  " + end);
//            System.out.println("start end = " + end);

            HttpResponse response =  client.execute(get);
//            System.out.println("response = " + response.getStatusLine().getStatusCode());
            if(response.getStatusLine().getStatusCode() == 206){

               InputStream inputStream =  response.getEntity().getContent() ;

                RandomAccessFile randomAccessFile = new RandomAccessFile(file,"rwd");
                randomAccessFile.seek(start);

                byte [] buf = new byte[1024];
                int length = 0 ;
                while ((length = inputStream.read(buf)) != -1) {
                    if(stop){
                        return;
                    }
                    count = count + length ;
                    total = total + length ;
                    randomAccessFile.write(buf,0,length);

                    SharedPreferences mShareConfig =
                            context.getSharedPreferences( threadid+"", Context.MODE_PRIVATE );
                    SharedPreferences.Editor conEdit = mShareConfig.edit();
                    conEdit.putInt("count",count);
                    conEdit.commit();

                    // 0.xml   key : count value : count // 当前线程下载的总和
                    Message message = new Message();
                    message.what = 1 ;
                    message.arg1 = total;
                    handler.sendMessage(message);

                    System.out.println("start count = " + " third_id " + threadid + "   " + count);


                }

                randomAccessFile.close();
                if(inputStream != null){
                    inputStream.close();
                }


            }



        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

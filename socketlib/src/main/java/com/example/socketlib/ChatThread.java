package com.example.socketlib;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Created by muhanxi on 17/4/5.
 */

public class ChatThread extends Thread {


    private Socket socket;
    private BufferedReader bufferedReader;

    public ChatThread(Socket socket){
        this.socket = socket;

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void run() {
        super.run();

        String line = null ;

        try {
            System.out.println("run server");
            while ((line = bufferedReader.readLine()) != null) {


                for(Socket s : ChatServer.list){


                    BufferedWriter bufferedWriter =  new BufferedWriter(new OutputStreamWriter(s.getOutputStream(),"utf-8"));
                    bufferedWriter.write(line+"\n");
                    bufferedWriter.flush();

                }



            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

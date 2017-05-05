package com.example.socketlib;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by muhanxi on 17/4/5.
 */

public class ChatServer {


    public static List<Socket> list = new ArrayList<Socket>();

    public static void main(String [] args) {

        try {
            ServerSocket serverSocket =  new ServerSocket(10010);
            while (true){
                Socket socket =  serverSocket.accept();
                list.add(socket);

                new ChatThread(socket).start();

                System.out.print("ChatServer list.size() :" + list.size());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }



}

package com.bwei.ydhl.bluetootch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.UUID;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;

public class SendThread extends Thread {

	static String SPP_UUID = "fa87c0d0-afac-11de-8a39-0800200c9a66";
	private static final UUID My_uuid = UUID.fromString(SPP_UUID);
	private BluetoothSocket tmp;
	private OutputStream outputStream;
	private BufferedWriter bufferedWriter;
	private BufferedReader bufferedReader;
	private Handler handler;
	private BluetoothDevice device;
	// 未链接状态
	public boolean connected = false;

	public SendThread(Handler handler, BluetoothDevice device) {
		try {
			this.handler = handler;
			this.device = device;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		super.run();
		try {
			try {
				//  通过 UUID 获取到一个 BluetoothSocket 对象
				tmp = device.createRfcommSocketToServiceRecord(My_uuid);
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				tmp.connect();
			} catch (IOException e) {
				e.printStackTrace();
			}
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(
					tmp.getOutputStream()));
			bufferedReader = new BufferedReader(new InputStreamReader(
					tmp.getInputStream()));
			connected = true;
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				handler.obtainMessage(2, line).sendToTarget();
			}
		} catch (IOException e) {
			connected = false;
			e.printStackTrace();
		}

	}

	public void send(String str) {
		if (bufferedWriter != null) {
			try {
				bufferedWriter.write(str + "\n");
				bufferedWriter.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
		}
	}


	public void closeAccept(){
		if(tmp != null){
			try {
				tmp.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

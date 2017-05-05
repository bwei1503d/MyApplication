package com.bwei.ydhl.bluetootch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;

public class AcceptThread extends Thread {
	private static final String NAME_SECURE = "BluetoothChatSecure";
	// private static final UUID MY_UUID_SECURE = UUID
	// .fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");

	static String SPP_UUID = "fa87c0d0-afac-11de-8a39-0800200c9a66";
	// 字符串转化成uuid 对象
	private static final UUID My_uuid = UUID.fromString(SPP_UUID);
	private static final String TAG = "AcceptThread";
	private BluetoothServerSocket bluetoothServerSocket;
	private BufferedWriter bufferedWriter;
	private BufferedReader bufferedReader;
	private Handler handler;
	private BluetoothSocket socket;

	public AcceptThread(Handler handler) {
		try {
			BluetoothAdapter mAdapter = BluetoothAdapter.getDefaultAdapter();
			bluetoothServerSocket = mAdapter
					.listenUsingRfcommWithServiceRecord(NAME_SECURE, My_uuid);
			this.handler = handler;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		super.run();
		try {
			while (true) {
				// 登录接收 连接 蓝牙设备
				socket = bluetoothServerSocket.accept();
				bufferedReader = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					handler.obtainMessage(2, line).sendToTarget();
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(String str) {
		try {
			if (bufferedWriter == null) {
				bufferedWriter = new BufferedWriter(new OutputStreamWriter(
						socket.getOutputStream()));
			}
			bufferedWriter.write(str);
			bufferedWriter.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void closeAccept(){
		if(socket != null){
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (bluetoothServerSocket != null) {
			try {
				bluetoothServerSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

package com.bwei.ydhl.bluetootch;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bwei.ydhl.R;

public class ChatActivity extends Activity {
	private EditText et_content;
	private TextView tv_content;
	private BluetoothAdapter defaultAdapter;
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 2:
				synchronized (this) {
					String message = (String) msg.obj;
					if(!TextUtils.isEmpty(message)){
						tv_content.append("接受" + message + "\n");
					}
				}
				break;
			default:
				break;
			}
		};
	};
	private SendThread sendThread;
	private String address;
	private AcceptThread acceptThread;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		tv_content = (TextView) findViewById(R.id.tv_content);
		et_content = (EditText) findViewById(R.id.et_content);
		address = getIntent().getStringExtra("address");
		//获取蓝牙适配器对象
		defaultAdapter = BluetoothAdapter.getDefaultAdapter();

		// 启动一个 线程  接受别人发送的数据
		acceptThread = new AcceptThread(handler);
		acceptThread.start();


		connect();
	}

	public void connect(View v) {
		connect();
	}
	private void connect() {
		BluetoothDevice device = defaultAdapter.getRemoteDevice(address);
		sendThread = new SendThread(handler, device);
		sendThread.start();
	}
	public void send(View v) {
		String message = et_content.getText().toString().trim();
		tv_content.append("发送:" + message + "\n");
		if (sendThread.connected) {
			sendThread.send(message + "\n");
		} else {
			acceptThread.sendMessage(message + "\n");
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		acceptThread.closeAccept();
		sendThread.closeAccept();
	}
}

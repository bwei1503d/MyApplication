package com.bwei.ydhl.bluetootch;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;

import com.bwei.ydhl.R;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class BluetoothActivity extends Activity {
//	https://github.com/Jasonchenlijian/FastBle
//	http://blog.csdn.net/u012992171/article/details/46517729
	private ListView listView;
	private BluetoothAdapter defaultAdapter;
	private ArrayList<BluetoothDevice> deviceList = new ArrayList<BluetoothDevice>();
	private MyBluetoothAdapter myBluetoothAdapter;
	private BroadcastReceiver receiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.blueactivity_main);
		listView = (ListView) findViewById(R.id.listView);

		// 获取蓝牙适配对象
		defaultAdapter = BluetoothAdapter.getDefaultAdapter();
		// 如果蓝牙状态不可用
		// 打开蓝牙设备
		defaultAdapter.enable();
		// 注册广播
		registBluetoothReceiver();
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				try {
					BluetoothDevice bluetoothDevice = deviceList.get(position);
					// 未配对，就去配对
					if (bluetoothDevice.getBondState() == BluetoothDevice.BOND_NONE) {
						String address = bluetoothDevice.getAddress();
						// 获取远程设备
						BluetoothDevice remoteDevice = defaultAdapter
								.getRemoteDevice(address);


						// 配对操作
						// 先获取字节码文件对象
//						Class<BluetoothDevice> clz = BluetoothDevice.class;
////						// 获取方法
//						Method method = clz.getMethod("createBond", null);
////						// 执行配对该方法
//						method.invoke(remoteDevice, null);
					} else if (bluetoothDevice.getBondState() == bluetoothDevice.BOND_BONDED) {
						Intent intent = new Intent(BluetoothActivity.this,
								ChatActivity.class);
						intent.putExtra("address", bluetoothDevice.getAddress());
						startActivity(intent);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
	}

	/**
	 * 注册蓝牙广播
	 */
	private void registBluetoothReceiver() {
		// 定义一个广播接收器
		receiver = new MyBluetoothReceiver();
		// 创建一个意图过滤器
		IntentFilter filter = new IntentFilter();
		// 注册一个搜素到蓝牙的一个意图action
		filter.addAction(BluetoothDevice.ACTION_FOUND);
		// 添加一个action 监听配对状态改变的一个事件
		filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);

		registerReceiver(receiver, filter);

	}

	public void search(View v) {
		// 搜寻蓝牙设备 已配对和没有配对的设备
		searchBondedDevices();

		// 搜索未配对设备
		searchUnBondedDevices();
		// 展示设备
		setAdapter();
	}


	/**
	 * 搜索未配对设备
	 */
	private void searchUnBondedDevices() {
		new Thread() {
			public void run() {
				// 如果当前正在搜素，先停止，开始本次搜索
				if (defaultAdapter.isDiscovering()) {
					defaultAdapter.cancelDiscovery();
				}
				// 开始搜索，就可以搜索到未配对的设备
				defaultAdapter.startDiscovery();
			};
		}.start();
	}

	/**
	 * 设置数据适配器
	 */
	private void setAdapter() {
		// 如果适配器为空，创建，设置适配器
		if (myBluetoothAdapter == null) {
			myBluetoothAdapter = new MyBluetoothAdapter(this, deviceList);
			listView.setAdapter(myBluetoothAdapter);
		} else {
			// 刷新适配器
			myBluetoothAdapter.notifyDataSetChanged();
		}
	}

	/**
	 * 搜寻已经配对的设备
	 */
	private void searchBondedDevices() {
		Set<BluetoothDevice> bondedDevices = defaultAdapter.getBondedDevices();
		for (BluetoothDevice bluetoothDevice : bondedDevices) {
			if (!deviceList.contains(bluetoothDevice))
				deviceList.add(bluetoothDevice);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (receiver != null) {
			// 反注册广播
			unregisterReceiver(receiver);
			receiver = null;
		}
	}

	// 接收所注册过的action的消息
	class MyBluetoothReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			// 获取事件类型
			String action = intent.getAction();
			// 获取蓝牙设备
			BluetoothDevice bluetoothDevice = intent
					.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
			if (action.equals(BluetoothDevice.ACTION_FOUND)) {
				// 发现蓝牙设备，发送了这样一个形式的广播
				// sendBroadcast(intent);
				// intent.putExtra(name, value);
				// 获取到蓝牙设备

				// 添加到一开始定义的集合中
				if (!deviceList.contains(bluetoothDevice)) {
					deviceList.add(bluetoothDevice);
				}
				// 刷新数据适配器
				setAdapter();
			} else if (action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)) {
				int bondState = bluetoothDevice.getBondState();
				switch (bondState) {
					case BluetoothDevice.BOND_NONE:
						Toast.makeText(BluetoothActivity.this, "配对失败", Toast.LENGTH_SHORT).show();
						break;
					case BluetoothDevice.BOND_BONDING:
						Toast.makeText(BluetoothActivity.this, "正在配对", Toast.LENGTH_SHORT).show();
						break;
					case BluetoothDevice.BOND_BONDED:
						Toast.makeText(BluetoothActivity.this, "配对成功", Toast.LENGTH_SHORT).show();
						// 重新搜索
						// searchBondedDevices();
						// searchUnBondedDevices();
						// 刷新适配器
						deviceList.remove(bluetoothDevice);
						deviceList.add(0, bluetoothDevice);
						setAdapter();
						break;
					default:
						break;
				}
			}

		}
	}
}

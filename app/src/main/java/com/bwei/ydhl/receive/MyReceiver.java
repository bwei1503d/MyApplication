package com.bwei.ydhl.receive;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        if(TextUtils.isEmpty(intent.getAction())){

            //发现蓝牙设备
            if( intent.getAction().equals(BluetoothDevice.ACTION_FOUND) ){

                //得到搜索蓝牙设备对象
              BluetoothDevice device =   intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                //如果该设备 之前没有配对过
                if(device.getBondState() == BluetoothDevice.BOND_NONE ){

                    String info = device.getName() + "  " + device.getAddress() ;


//                    if (listDevices.indexOf(str) == -1) {
//                        listDevices.add(str);
                    //通知数据 更新
                    System.out.println("onReceive info = " + info);

                }


            }



            if( BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(intent.getAction()) ) {

                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                switch (device.getBondState()) {

                    case BluetoothDevice.BOND_BONDING:
                        Log.e("tag"," 配对中 。。。。");


                    break;

                    case BluetoothDevice.BOND_BONDED:
                        Log.e("tag"," 正在配对 。。。。");

                        break;
                    case BluetoothDevice.BOND_NONE:
                        Log.e("tag"," 取消配对 。。。。");
                        break;

                }


            }



        }



    }
}

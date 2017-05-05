package com.bwei.ydhl.bluetootch;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bwei.ydhl.R;

import java.util.List;

/**
 * Created by muhanxi on 17/4/8.
 */

public class CBluetoothAdapter extends BaseAdapter {

    private List<BluetoothDevice> list ;
    private Context context;
    public CBluetoothAdapter(List<BluetoothDevice> list, Context context){

        this.list = list;
        this.context = context;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null ;

        if(convertView == null){

            convertView = LayoutInflater.from(context).inflate(R.layout.beike_bluetooth_adapter,null);
            viewHolder = new ViewHolder();

            viewHolder.textView = (TextView) convertView.findViewById(R.id.beike_blue_tv);

            convertView.setTag(viewHolder);


        }else {

            viewHolder = (ViewHolder) convertView.getTag() ;

        }

        viewHolder.textView.setText(list.get(position).getName() + "   " + list.get(position).getAddress());




        return convertView;
    }

    static class ViewHolder {
        TextView textView;
    }
}

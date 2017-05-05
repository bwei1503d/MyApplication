package com.bwei.ydhl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwei.ydhl.R;
import com.bwei.ydhl.httpurlconnection.HttpUrlConnectionActivity;
import com.bwei.ydhl.utils.StringUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by muhanxi on 17/4/11.
 */

public class HealthListAdapter extends BaseAdapter {

    private  List<com.bwei.ydhl.bean.HealthList> lists;
    private HttpUrlConnectionActivity context;
    public HealthListAdapter(HttpUrlConnectionActivity context, List list){
        this.context = context;
        this.lists = list;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null ;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.test_health_adapter,null);

            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.left_image);
            viewHolder.textViewDescription = (TextView) convertView.findViewById(R.id.test_healthtv);
            viewHolder.textViewTime = (TextView) convertView.findViewById(R.id.test_healthtv_timer);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.textViewDescription.setText(lists.get(position).getDescription());
        viewHolder.textViewTime.setText(StringUtils.millisecondToDate(lists.get(position).getTime()));

//        ImageLoader.getInstance().displayImage(lists.get(position).getImg(),viewHolder.imageView);

        context.loadImage(lists.get(position).getImg(),viewHolder.imageView);


        return convertView;
    }


    static class ViewHolder {

        ImageView imageView;
        TextView textViewDescription;
        TextView textViewTime;

    }

}

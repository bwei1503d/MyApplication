package com.bwei.ydhl.ketang.httpurlconnectiongetpost;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwei.ydhl.R;
import com.bwei.ydhl.bean.News;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by muhanxi on 17/4/12.
 */

public class ListAdapter extends BaseAdapter {

    public  Context context;
    public List<News> list;
    public ListAdapter(Context context, List<News> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder ;

        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = View.inflate(context,R.layout.list_adapter,null);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.news_imageview);
            viewHolder.textViewTitle = (TextView) convertView.findViewById(R.id.news_title);
            viewHolder.textViewDate = (TextView) convertView.findViewById(R.id.news_data);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag() ;
        }

//        viewHolder.imageView
        ImageLoader.getInstance().displayImage(list.get(position).getThumbnail_pic_s(),viewHolder.imageView);

        viewHolder.textViewTitle.setText(list.get(position).getTitle());
        viewHolder.textViewDate.setText(list.get(position).getDate());



        return convertView;
    }



    static  class ViewHolder {
        ImageView imageView ;
        TextView textViewTitle;
        TextView textViewDate;

    }



}

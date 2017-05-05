package com.bwei.ydhl.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwei.ydhl.R;
import com.bwei.ydhl.bean.GroupBean;
import com.bwei.ydhl.gropulistview.GroupActivity;
import com.bwei.ydhl.httpurlconnection.HttpUrlConnectionActivity;
import com.bwei.ydhl.utils.StringUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by muhanxi on 17/4/11.
 */

public class GroupAdapter extends BaseAdapter {
//    http://www.cnblogs.com/MianActivity/p/5867776.html
    private  List<GroupBean.ListBean> lists;
    private GroupActivity context;

    public static final int firstImage = 0 ;
    public static final int thirdImage = 1 ;

    public GroupAdapter(GroupActivity context, List list){
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
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if(lists.get(position).getType() == 1){
            return firstImage ;
        }else {
            return thirdImage ;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

         int type = getItemViewType(position);

        ViewHolder1 viewHoldersingle = null ;
        ViewHolder2 viewHoldermulti = null ;


        if(convertView == null){

            switch (type) {
                case firstImage :

                    viewHoldersingle = new ViewHolder1();
                    convertView = LayoutInflater.from(context).inflate(R.layout.groupadapterfirst,null);

                    viewHoldersingle.imageView = (ImageView) convertView.findViewById(R.id.imageview_left_group);
                    viewHoldersingle.textViewDescription = (TextView) convertView.findViewById(R.id.textview_title_group);
                    viewHoldersingle.textViewTime = (TextView) convertView.findViewById(R.id.textview_date_group);
                    convertView.setTag(viewHoldersingle);
                    break;
                case thirdImage:
                    viewHoldermulti = new ViewHolder2();
                    convertView = LayoutInflater.from(context).inflate(R.layout.groupadaptertwo,null);

                    viewHoldermulti.imageViewl = (ImageView) convertView.findViewById(R.id.multiimageview_left_group);
                    viewHoldermulti.imageViewr = (ImageView) convertView.findViewById(R.id.multiimageview_right_group);
                    viewHoldermulti.imageViewm = (ImageView) convertView.findViewById(R.id.multiimageview_middle_group);

                    viewHoldermulti.textViewDescription = (TextView) convertView.findViewById(R.id.multi_group_title);
                    viewHoldermulti.textViewTime = (TextView) convertView.findViewById(R.id.multi_group_date);
                    convertView.setTag(viewHoldermulti);
                    break;

            }

        }else {
            switch (type) {
                case firstImage :

                    viewHoldersingle = (ViewHolder1) convertView.getTag();

                    break;
                case thirdImage:
                    viewHoldermulti = (ViewHolder2) convertView.getTag();

                    break;

            }
        }


        switch (type) {
            case firstImage :

                viewHoldersingle.textViewDescription.setText(lists.get(position).getTitle());
                viewHoldersingle.textViewTime.setText(lists.get(position).getDate());

                ImageLoader.getInstance().displayImage(lists.get(position).getPic(),viewHoldersingle.imageView);
                break;
            case thirdImage:

                String pic = lists.get(position).getPic() ;
                String [] str =  lists.get(position).getPic().split("\\|");
//
//
                ImageLoader.getInstance().displayImage(str[0],viewHoldermulti.imageViewl);
                ImageLoader.getInstance().displayImage(str[1],viewHoldermulti.imageViewm);
                ImageLoader.getInstance().displayImage(str[2],viewHoldermulti.imageViewr);


                viewHoldermulti.textViewDescription.setText(lists.get(position).getTitle());
                viewHoldermulti.textViewTime.setText(lists.get(position).getDate());
                break;

        }




        return convertView;
    }


    static class ViewHolder1 {

        ImageView imageView;
        TextView textViewDescription;
        TextView textViewTime;

    }
    static class ViewHolder2 {

        ImageView imageViewl;
        ImageView imageViewr;
        ImageView imageViewm;
        TextView textViewDescription;
        TextView textViewTime;

    }

}

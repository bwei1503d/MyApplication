package com.bwei.ydhl.gropulistview;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;

import com.bwei.ydhl.R;
import com.bwei.ydhl.adapter.GroupAdapter;
import com.bwei.ydhl.bean.GroupBean;
import com.bwei.ydhl.utils.StringUtils;
import com.example.xlistview.XListView;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GroupActivity extends Activity implements XListView.IXListViewListener {


    List<GroupBean.ListBean> list = new ArrayList<GroupBean.ListBean>();

    GroupAdapter adapter ;

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {

                case 1:
                    GroupBean bean = (GroupBean) msg.obj ;

                    list.addAll(bean.getList());
                    adapter.notifyDataSetChanged();

                    break;
                case 2:
                    GroupBean beans = (GroupBean) msg.obj ;

                    list.addAll(beans.getList());
                    adapter.notifyDataSetChanged();

                    listView.stopLoadMore();

                    break;

            }

        }
    } ;
    private XListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);


        listView = (XListView) findViewById(R.id.group_listview);
        adapter = new GroupAdapter(this,list);
        listView.setAdapter(adapter);


        listView.setPullLoadEnable(true);
        listView.setXListViewListener(this);

        getdata(1);

    }


    private void getdata(final int page){

        new Thread(new Runnable() {
            @Override
            public void run() {


                try {
                    HttpClient client = new DefaultHttpClient();
                    HttpGet get = new HttpGet("http://qhb.2dyt.com/Bwei/news?page=1&postkey=1503d");
                    HttpResponse response =  client.execute(get);
                    if(response.getStatusLine().getStatusCode() == 200){

                        String content = StringUtils.inputStreamToString(response.getEntity().getContent());


                        Gson gson = new Gson();
                        GroupBean bean =  gson.fromJson(content, GroupBean.class);
                        Message message = Message.obtain();
                        message.what = page;
                        message.obj = bean;
                        handler.sendMessage(message);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }).start();

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

        getdata(2);

    }


}

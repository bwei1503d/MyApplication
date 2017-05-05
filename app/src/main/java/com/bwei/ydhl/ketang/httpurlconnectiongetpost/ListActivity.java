package com.bwei.ydhl.ketang.httpurlconnectiongetpost;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bwei.ydhl.R;
import com.bwei.ydhl.bean.News;
import com.bwei.ydhl.utils.StringUtils;
import com.bwei.ydhl.viewpager.MyPagerAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.HttpStatus;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * 显示新闻列表
 */
public class ListActivity extends Activity {


    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:

                    List<News> list = (List<News>) msg.obj ;
                    gList.addAll(list);
                    adapter.notifyDataSetChanged();


                    break;
                case 2:


                    // 切换
//                    if(viewPager.getCurrentItem() == images.length - 1){
//                        viewPager.setCurrentItem(0);
//                    }else{
//                        viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
//                    }
                    viewPager.setCurrentItem(viewPager.getCurrentItem()+1,true);

//                    handler.sendEmptyMessageDelayed(2,2000);

                    break;

            }


        }
    } ;

    private ListView listView;
    private List<News> gList = new ArrayList<News>();
    private ListAdapter adapter ;
    private View headerView;
    private ViewPager viewPager;


    String [] images = {"https://img10.360buyimg.com/da/jfs/t4747/277/1368712300/170619/35098d7f/58f038e0N9b3a0ca5.jpg",
//            "https://img14.360buyimg.com/da/jfs/t4915/21/1427207714/81116/b005bb06/58f08963Ndb295b3c.jpg",
            "https://img11.360buyimg.com/da/jfs/t4747/119/950664130/263110/96477810/58eb23a9Ndfd9b4e8.jpg",
            "https://img13.360buyimg.com/da/jfs/t4651/104/2867456043/68336/99da4c16/58f41eaeN5b614a63.jpg"};
    private LinearLayout linearLayout;
    List<ImageView> mViewListIndicator = new ArrayList<ImageView>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);



        //获取Application

//        IApplication application = (IApplication) getApplication() ;

//        Toast.makeText(this, ""+application.share, Toast.LENGTH_SHORT).show();


        //  1 获取新闻列表 2 跳转登录页面  post请求 3 跳转 新闻 详细内容页面 webview加载
        // http://qhb.2dyt.com/login?username=1111&password=1111&postkey=bwei

        listView = (ListView) findViewById(R.id.list_listview);
        adapter = new ListAdapter(getApplicationContext(),gList);

        headerView = LayoutInflater.from(this).inflate(R.layout.listviewhead,null);
        viewPager = (ViewPager) headerView.findViewById(R.id.listview_header);

        listView.addHeaderView(headerView,null,false);


        linearLayout = (LinearLayout) findViewById(R.id.linear_view);

        for(int i=0;i<images.length;i++){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) (getResources().getDimension(R.dimen.indicator_size)), (int) (getResources().getDimension(R.dimen.indicator_size)));
            ImageView imageView = new ImageView(this);
            params.setMargins(0,0,(int) (getResources().getDimension(R.dimen.indicator_size)),0);
            imageView.setLayoutParams(params);
            if(i==0){
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.oval_selected));
            } else {
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.oval_normal));
            }
            linearLayout.addView(imageView);
            mViewListIndicator.add(imageView);
        }

        View view = (View) LayoutInflater.from(this).inflate(R.layout.viewpageer_item,null);
        ImageView imageView = (ImageView) view.findViewById(R.id.viewpager_imageview);
        List<ImageView> lists = new ArrayList<ImageView>();
        for(int i=0;i<3;i++){
            ImageLoader.getInstance().displayImage(images[i],imageView);
            lists.add(imageView);
        }

        viewPager.setAdapter(new MyPagerAdapter(this,images));



        listView.setAdapter(adapter);




        Map map = new HashMap<String,String>();
        map.put("username","111");

        Set<String> set =  map.keySet();
        for(String key : set){
           String value = (String)  map.get(key);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {


                get("http://v.juhe.cn/toutiao/index?type=shehui&key=7903da88904cf7639e0c71f2f51ff9c7");

            }
        }).start();





        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(ListActivity.this,DetailActivity.class);
                intent.putExtra("url",gList.get(position).getUrl());
                startActivity(intent);

            }
        });


//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                Looper.prepare();
//                Toast.makeText(ListActivity.this, "111", Toast.LENGTH_SHORT).show();
//                Looper.loop();
//
//
//
//            }
//        }).start();


//        handler.sendEmptyMessageDelayed(2,2000);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for(int i=0;i<mViewListIndicator.size();i++){

                    if(i == position % images.length){
                        mViewListIndicator.get(i).setImageDrawable(getResources().getDrawable(R.drawable.oval_selected));
                    } else{
                        mViewListIndicator.get(i).setImageDrawable(getResources().getDrawable(R.drawable.oval_normal));
                    }

                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        viewPager.setCurrentItem(images.length * 100);

    }


    private void fragmnet(){


    }


    private void get(String path){

        try {
            URL url =  new URL(path);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(20000);
            connection.setConnectTimeout(20000);

            connection.setDoInput(true);
            connection.setDoOutput(true);

            System.out.println("connection.getResponseCode() = " + connection.getResponseCode());

            if(connection.getResponseCode() == HttpStatus.SC_OK){
               InputStream inputStream =  connection.getInputStream() ;
               String result =  StringUtils.inputStreamToString(inputStream);
               System.out.println("result = " + result);

               JSONObject jsonObjectResult =  JSON.parseObject(result);
                System.out.println("jsonObjectResult = " + jsonObjectResult);
                JSONObject result_object  = (JSONObject)jsonObjectResult.get("result");
                JSONArray data_object  = (JSONArray) result_object.get("data");

                List<News>  list = JSON.parseArray(data_object.toString(), News.class);
//
                Message message = new Message();
                message.what = 1 ;
                message.obj = list ;
                handler.sendMessage(message);

            }


            




        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    // http://blog.csdn.net/dreamlivemeng/article/details/51262538








}




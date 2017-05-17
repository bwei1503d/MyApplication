package com.bwei.ydhl.pull;

import android.app.Activity;
import android.os.Bundle;
import android.util.Xml;

import com.bwei.ydhl.R;
import com.bwei.ydhl.utils.StringUtils;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PullActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull);


        try {

            try {
                InputStream inputStream =  getAssets().open("data.json") ;

                String content = StringUtils.inputStraemToStringBuffer(inputStream);

//                String content = new String(inputStream.toString().getBytes(),"utf-8");

                JSONArray array = new JSONArray(content);


                System.out.println("array = " + array);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public List<Person> pullXml(InputStream inputStream){

        List<Person> list = null;
        Person person = null;

        try {
            // 利用ANDROID提供的API快速获得pull解析器
            XmlPullParser pullParser =  Xml.newPullParser() ;
            // 设置需要解析的XML数据
            pullParser.setInput(inputStream,"utf-8");

            int event = pullParser.getEventType();

            while (event != XmlPullParser.END_DOCUMENT) {


                String nodeName = pullParser.getName() ;

                switch (event) {

                    case XmlPullParser.START_DOCUMENT:
                        list = new ArrayList<Person>();
                        break;
                    case XmlPullParser.START_TAG:

                        if("user".equals(nodeName)){
                            String id = pullParser.getAttributeValue(0);
                            person = new Person();
                            person.setId(id);
                        }
                        if("name".equals(nodeName)){
                           String name =  pullParser.nextText();
                            person.setName(name);
                        }
                        if("age".equals(nodeName)){
                            int age = Integer.valueOf(pullParser.nextText());
                            person.setAge(age);
                        }

                        break;
                    case XmlPullParser.END_TAG:
                        if ("user".equals(nodeName)) {
                            list.add(person);
                            person = null;
                        }

                        break;

                }
                event = pullParser.next();

            }


        } catch (Exception e) {
            e.printStackTrace();
        }


        return  list ;
    }


}

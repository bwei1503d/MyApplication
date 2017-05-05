package com.bwei.ydhl.webservice;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bwei.ydhl.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class WebServiceActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_service);





        click();

    }






    public void click() {
        new Thread() {
            @Override
            public void run() {

                try {
//                    getMobile();
//                    getMobileInfor();
                    getGJInfor();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    /**
     * 联网获得天气情况
     */
    private void getInfo() {
         final String name = "GetWeather";
         final String namespace = "http://www.36wu.com/";
        // 设置访问的url地址
         final String URL = "http://web.36wu.com/WeatherService.asmx?WSDL";// 大小写转换快捷键:shift+ctrl+x/:shift+ctrl+y

         final String SOAPAction = "http://www.36wu.com/GetWeather";
        // 設置命名空間,及访问的方法名
        SoapObject soapObject = new SoapObject(namespace, name);
        // 携带要查询的数据
        soapObject.addProperty("district", "石家庄");
        // 添加自己在http://www.36wu.com/申请的key
        soapObject.addProperty("authkey", "c1cb66e8892646eda047d9db2f686476");

        // 得到HttpTransportSE对象,设置访问url
        HttpTransportSE se = new HttpTransportSE(URL);
        // 北京
        // 得到serializationEnvelope對象,设置Soap版本号
        SoapSerializationEnvelope serializationEnvelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER12);

        // 设置发送给服务器的信息
        serializationEnvelope.bodyOut = soapObject;
        // 设置支付.NET语言
        serializationEnvelope.dotNet = true;

        try {
            // 发送请求
            se.call(SOAPAction, serializationEnvelope);
            // 得到服务器返回的数据
            SoapObject soapObject_in = (SoapObject) serializationEnvelope.bodyIn;

            System.out.println("============" + soapObject_in.toString());
            // 得到GetWeatherResult字段下包含的信息
            SoapObject getWeatherResult = (SoapObject) soapObject_in
                    .getProperty("GetWeatherResult");
            SoapObject data = (SoapObject) getWeatherResult.getProperty("data");
            System.out.println(data.toString());
            System.out.println(data.getProperty(0));
            System.out.println(data.getProperty("city"));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static final String mobilename = "GetIpInfo";
    static final String mobilenamespace = "http://www.36wu.com/";
    // 设置访问的url地址
    static final String mobileURL = "http://web.36wu.com/IpService.asmx?WSDL";// 大小写转换快捷键:shift+ctrl+x/:shift+ctrl+y

    static final String mobileSOAPAction = "http://www.36wu.com/GetIpInfo";


    public void getIpInfor() throws Exception {

        SoapObject soapObject = new SoapObject(mobilenamespace,mobilename);
        soapObject.addProperty("authkey", "ca1823912db14b609331165e6f593037");
        soapObject.addProperty("ip","120.27.50.184");
        soapObject.addProperty("format","json");
        HttpTransportSE httpTransportSE = new HttpTransportSE(mobileURL);
        SoapSerializationEnvelope soapSerializationEnvelope = new SoapSerializationEnvelope(SoapSerializationEnvelope.VER11);
        soapSerializationEnvelope.dotNet = true;
        soapSerializationEnvelope.bodyOut = soapObject;
        httpTransportSE.call(mobileSOAPAction,soapSerializationEnvelope);

        SoapObject getWeatherResult = (SoapObject) soapSerializationEnvelope.bodyIn ;
        System.out.println(getWeatherResult.toString());

        SoapObject getWeatherZero = (SoapObject) getWeatherResult.getProperty(0) ;
        System.out.println(" " + getWeatherZero.getProperty(2));

    }

    public void getMobileInfor() throws Exception {


         final String mobilename = "GetMobileOwnership";
         final String mobilenamespace = "http://www.36wu.com/";
        // 设置访问的url地址
         final String mobileURL = "http://web.36wu.com/MobileService.asmx?WSDL";// 大小写转换快捷键:shift+ctrl+x/:shift+ctrl+y

         final String mobileSOAPAction = "http://www.36wu.com/GetMobileOwnership";

        SoapObject soapObject = new SoapObject(mobilenamespace,mobilename);
        soapObject.addProperty("authkey", "ca1823912db14b609331165e6f593037");
        soapObject.addProperty("mobile","18511085102");
        soapObject.addProperty("format","json");
        HttpTransportSE httpTransportSE = new HttpTransportSE(mobileURL);
        SoapSerializationEnvelope soapSerializationEnvelope = new SoapSerializationEnvelope(SoapSerializationEnvelope.VER11);
        soapSerializationEnvelope.dotNet = true;
        soapSerializationEnvelope.bodyOut = soapObject;
        httpTransportSE.call(mobileSOAPAction,soapSerializationEnvelope);

        SoapObject getWeatherResult = (SoapObject) soapSerializationEnvelope.bodyIn ;
        System.out.println(getWeatherResult.toString());

        SoapObject getWeatherZero = (SoapObject) getWeatherResult.getProperty(0) ;
        System.out.println(" " + getWeatherZero.getProperty(2));

    }

    public void toast(final String str) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
            }
        });
    }


    //获取方法名称
    static final String name = "GetLineInfo";
    //获取服务地址
    static final String nameSpace = "http://api.36wu.com/";
    //获取访问网址
    static final String Url = "http://web.36wu.com/BusService.asmx?WSDL";
    //获取动作
    static final String SOAPAction = nameSpace + name;
    //聯網獲取公交情況
    private void getGJInfor() {
        //设置空间名及访问的方法
        SoapObject soapObject = new SoapObject(nameSpace,name);
        //携带要查询的数据
        soapObject.addProperty("city", "北京");
        soapObject.addProperty("line", "1路");
        //添加申請的key
        soapObject.addProperty("authkey", "d117ac0e665848cd8162c3539a6ddef0");
        //得到对象存放要访问的网址
        HttpTransportSE se = new HttpTransportSE(Url);
        //得到一个存放se的对象的版本号
        SoapSerializationEnvelope soapSerializationEnvelope = new SoapSerializationEnvelope(SoapSerializationEnvelope.VER11);
        //设置发送给服务器的信息
        soapSerializationEnvelope.bodyOut = soapObject;
        //设置c#可以访问
        soapSerializationEnvelope.dotNet = true;

        try {
            //发送请求
            se.call(SOAPAction, soapSerializationEnvelope);
            //服务器返回数据
            System.out.println("soapObject_in = " + soapSerializationEnvelope.bodyIn);
            SoapObject soapObject_in = (SoapObject) soapSerializationEnvelope.bodyIn;
            SoapObject name = (SoapObject) soapObject_in.getProperty("name");
            SoapObject infor = (SoapObject) soapObject_in.getProperty("infor");
            System.out.println("name = " + name);
            System.out.println("infor = " + infor);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }






}

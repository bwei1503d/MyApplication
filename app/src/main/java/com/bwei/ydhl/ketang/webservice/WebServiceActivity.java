package com.bwei.ydhl.ketang.webservice;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bwei.ydhl.R;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class WebServiceActivity extends Activity implements View.OnClickListener{


    public final String TAG = getClass().getSimpleName();

    Button button ;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webservice);


        initView();


    }


    private void initView(){

        editText = (EditText) findViewById(R.id.edittext_webservice);
        button = (Button) findViewById(R.id.btn_webservice);
        button.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_webservice:



                getContent(editText.getText().toString().trim());

                break;
        }
    }



    // 查询手机号码归属地
    private void getContent(final String content){

        new Thread(new Runnable() {
            @Override
            public void run() {

                if(!TextUtils.isEmpty(content)){


                    //定义方法名称
                    final String name = "GetMobileOwnership" ;
                    //定义命名空间
                    final String namespace = "http://www.36wu.com/";
                    //定义url
                    final String url = "http://web.36wu.com/MobileService.asmx?WSDL" ;
                    // namespace + name
                    final String soapAction = namespace + name ;


                    // 设置命名空间,及访问的方法名
                    SoapObject soapObject = new SoapObject(namespace,name);
                    soapObject.addProperty("mobile",content);
                    soapObject.addProperty("format","xml");
                    soapObject.addProperty("authkey","d117ac0e665848cd8162c3539a6ddef0");

                    //  创建 HttpTransportSE 对象
                    HttpTransportSE httpTransportSE = new HttpTransportSE(url);

                    // 创建 SoapSerializationEnvelope 对象
                    SoapSerializationEnvelope soapSerializationEnvelope = new SoapSerializationEnvelope(SoapSerializationEnvelope.VER11);

                    //支持 c# 语言
                    soapSerializationEnvelope.dotNet = true ;
                    // 把传递参数 放到 SoapSerializationEnvelope 对象里面
                    soapSerializationEnvelope.bodyOut = soapObject ;

                    try {
                        //发送请求
                        httpTransportSE.call(soapAction,soapSerializationEnvelope);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (XmlPullParserException e) {
                        e.printStackTrace();
                    }

                    //得到ws 返回的数据 (取数据)
                    SoapObject soapObjectResult = (SoapObject) soapSerializationEnvelope.bodyIn ;
                    Log.e(TAG,"soapObjectResult start " + soapObjectResult);


                    SoapObject getMobileOwnershipResult = (SoapObject) soapObjectResult.getProperty("GetMobileOwnershipResult") ;
                    SoapObject data = (SoapObject) getMobileOwnershipResult.getProperty("data") ;

                    Log.e(TAG,"soapObjectResult nor" + getMobileOwnershipResult);

                    Log.e(TAG,"soapObjectResult " + getMobileOwnershipResult);

                    Log.e(TAG,"soapObjectResult " + getMobileOwnershipResult.getProperty(0));
                    Log.e(TAG,"soapObjectResult " + getMobileOwnershipResult.getProperty(1));

                    Log.e(TAG,"soapObjectResult " + data.getProperty("corp"));



                }

            }
        }).start();




    }






















}

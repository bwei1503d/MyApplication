package com.bwei.ydhl.js;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.bwei.ydhl.R;

/**
 * Created by muhanxi on 17/4/20.
 */

public class JsActivity extends Activity {

    private WebView webView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.js_layout);


        button = (Button) findViewById(R.id.js_btn);

        webView = (WebView) findViewById(R.id.webview_id);
        // 启用javascript
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(this, "key");

        webView.loadUrl("file:///android_asset/test.html");




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl("javascript:actionFromNative()");

            }
        });


    }



    public void actionFromJs() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(JsActivity.this, "js调用了Native函数", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void actionFromJsWithParam(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(JsActivity.this, "js调用了Native函数传递参数：" + str, Toast.LENGTH_SHORT).show();
            }
        });
    }

}

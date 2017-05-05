package com.bwei.ydhl.ketang.httpurlconnectiongetpost;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.bwei.ydhl.R;

public class DetailActivity extends Activity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        String url =  getIntent().getStringExtra("url");

        webView = (WebView) findViewById(R.id.detail_webview);

        webView.loadUrl(url);

    }
}

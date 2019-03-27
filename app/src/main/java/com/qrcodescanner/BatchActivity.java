package com.qrcodescanner;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

public class BatchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch);
        WebView webView = (WebView) findViewById(R.id.webview);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        SharedPreferences sp = getSharedPreferences("batch", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        String s1= sp.getString("batchid",null);
        webView.loadUrl("http://35.200.186.171/supplychain-frontend/view-batch.php?batchNo="+s1);
    }
}

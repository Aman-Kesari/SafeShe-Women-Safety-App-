package com.example.womensecurity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class webActivity extends AppCompatActivity {
    private WebView newsWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        getSupportActionBar().hide();
        String url=getIntent().getStringExtra("URL");
        newsWebView=findViewById(R.id.newsWebView);
        WebSettings webSettings=newsWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        newsWebView.loadUrl(url);
        newsWebView.setWebViewClient(new WebViewClient());
    }

    @Override
    public void onBackPressed() {
        if(newsWebView.canGoBack()){
            newsWebView.goBack();
        }else{
            super.onBackPressed();
        }
    }
}
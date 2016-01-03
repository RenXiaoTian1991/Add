package com.example.add.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.add.R;

/**
 * Created by myself on 15/9/6.
 */
public class ZhiHuDetailsActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private WebView webView;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhihu_details_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        String mTitle = getIntent().getStringExtra("title");
        String url = getIntent().getStringExtra("url");

        webView = (WebView) findViewById(R.id.webView);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new ChromeClient());
        webView.setWebViewClient(new ViewClient());
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.loadUrl(url);

        toolbar.setTitle(mTitle);

        //setTitle(mTitle);
    }

    private class ChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            progressBar.setProgress(newProgress);
            if (newProgress == 100) {
                progressBar.setVisibility(View.GONE);
            } else if (newProgress != 100) {
                progressBar.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            setTitle(title);
        }
    }

    private class ViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url != null) view.loadUrl(url);
            return true;
        }
    }
}

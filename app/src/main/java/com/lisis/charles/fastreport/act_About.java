package com.lisis.charles.fastreport;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class act_About extends AppCompatActivity {

    private WebView myView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        myView = (WebView) findViewById(R.id.webView);
        myView.loadUrl("file:///android_asset/view.html");
    }
}

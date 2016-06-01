package com.lisis.charles.fastreport;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class act_Acerca_de extends AppCompatActivity {

    private WebView myView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca_de);

        myView = (WebView) findViewById(R.id.mywebview);
        myView.loadUrl("file:///android_asset/view.html");
    }
}

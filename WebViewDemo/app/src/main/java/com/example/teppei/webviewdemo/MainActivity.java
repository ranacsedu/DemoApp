package com.example.teppei.webviewdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;

import org.json.JSONObject;

import java.util.Map;

import io.branch.referral.Branch;
import io.branch.referral.BranchError;

public class MainActivity extends AppCompatActivity {

    private WebView webview;
    private static final String AF_DEV_KEY = "<HLSoWZFpzn84UNyH8kWFpN>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize the Branch object
        Branch.getAutoInstance(this);

        // initizlize AppsFlyer object
        AppsFlyerConversionListener conversionDataListener =
                new AppsFlyerConversionListener() {
                    @Override
                    public void onInstallConversionDataLoaded(Map<String, String> conversionData) {

                    }

                    @Override
                    public void onInstallConversionFailure(String errorMessage) {

                    }

                    @Override
                    public void onAppOpenAttribution(Map<String, String> attributionData) {

                    }

                    @Override
                    public void onAttributionFailure(String errorMessage) {

                    }
                    //
                };
        //AppsFlyerLib.getInstance().init(AF_DEV_KEY, conversionDataListener, getApplicationContext());
        //AppsFlyerLib.getInstance().startTracking(this);
        AppsFlyerLib.getInstance().startTracking(this.getApplication(),AF_DEV_KEY);
        AppsFlyerLib.getInstance().sendDeepLinkData(this);



        setContentView(R.layout.activity_main);
        webview =(WebView)findViewById(R.id.webView);
        webview.setWebViewClient(new WebViewClient());
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);
        webview.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
        webview.loadUrl("https://www.google.com");

    }

    @Override
    public void onStart() {
        super.onStart();
        Branch branch = Branch.getInstance();

        // Branch init
        branch.initSession(new Branch.BranchReferralInitListener() {
            @Override
            public void onInitFinished(JSONObject referringParams, BranchError error) {
                if (error == null) {
                    // params are the deep linked params associated with the link that the user clicked -> was re-directed to this app
                    // params will be empty if no data found
                    // ... insert custom logic here ...
                    Log.i("BRANCH SDK", referringParams.toString());
                } else {
                    Log.i("BRANCH SDK", error.getMessage());
                }
            }
        }, this.getIntent().getData(), this);
    }

    @Override
    public void onNewIntent(Intent intent) {
        this.setIntent(intent);
    }
}
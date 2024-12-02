package com.studio.amplify.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.studio.amplify.R;
import com.studio.amplify.util.CommonApi;
import com.studio.amplify.util.Urls;

import org.json.JSONException;
import org.json.JSONObject;

public class CommunityWebviewActivity extends AppCompatActivity {
    WebView webViewForContent;
    TextView headerHeading;
    RelativeLayout rlBack;
    private CommonApi commonApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        commonApi = new CommonApi(CommunityWebviewActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_webview);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        headerHeading = findViewById(R.id.headerHeading);
        rlBack = findViewById(R.id.rlBack);

        headerHeading.setText(R.string.forum);

        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commonApi.finishActivity(CommunityWebviewActivity.this);
            }
        });

        getWebUrl();
    }

    private void getWebUrl () {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Urls.FORUM, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject();
                    System.out.println(" forumObject : " + obj.toString());
                    System.out.println(" forumUrl : " + Urls.FORUM);

                    Log.d("getForumResp:", response);

                    JSONObject jsonObject = new JSONObject(response);
                    String form = jsonObject.optString("forumurl");
                    webUrl(form);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }

    private void webUrl(String url) {
        webViewForContent  = new WebView(this);

        webViewForContent.getSettings().setJavaScriptEnabled(true); // enable javascript

        final Activity activity = this;

        webViewForContent.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                //  Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
            }
            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                // Redirect to deprecated method, so you can use it in all SDK versions
                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
            }
        });

        webViewForContent.loadUrl(url);
        setContentView(webViewForContent);

    }
}

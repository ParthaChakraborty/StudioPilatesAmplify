package com.studio.amplify.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.studio.amplify.R;
import com.studio.amplify.model.CheckboxItem;
import com.studio.amplify.util.BaseActivity;
import com.studio.amplify.util.CommonApi;
import com.studio.amplify.util.Constant;
import com.studio.amplify.util.Urls;
import com.studio.amplify.volleyparser.GetServiceCall;

import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebViewActivity extends BaseActivity {
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.tvForTitle)
    TextView tvForTitle;
    @BindView(R.id.imageViewForBanner)
    ImageView imageViewForBanner;
    @BindView(R.id.headerHeading)
    TextView headerHeading;

    @BindView(R.id.llForTextOnlyWebView)
    LinearLayout llForTextOnlyWebView;

    @BindView(R.id.llForVideoOnlyWebView)
    LinearLayout llForVideoOnlyWebView;

    @BindView(R.id.tvForTitleForVideo)
    TextView tvForTitleForVideo;
    @BindView(R.id.imageViewForBannerVideo)
    ImageView imageViewForBannerVideo;
    @BindView(R.id.webViewForVideo)
    WebView webViewForVideo;
    String CheckForBackPress;

    String urlKey, titleForHeaderToSet, contentFromFAQ, urlKeyForVideoWebView;
    Typeface feedFont, font, fontBold;
    CommonApi commonApi;
    String URL, newUrlKey;
    String urlKeys;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        commonApi = new CommonApi(WebViewActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);
        feedFont = Typeface.createFromAsset(this.getAssets(), commonApi.getRFG7());
        font = Typeface.createFromAsset(this.getAssets(), commonApi.getAileronRegularWebfront());
        fontBold = Typeface.createFromAsset(this.getAssets(), commonApi.getAileronBoldWebfront());

        tvForTitle.setTypeface(font);
        tvForTitleForVideo.setTypeface(fontBold);
        headerHeading.setTypeface(font);

        Bundle b = getIntent().getExtras();

        if (b != null) {
                urlKey = b.getString("urlKey");
                //urlKey = urlKey.replace("www.studiopilatesamplify.com/", "");
                webView.getSettings().setJavaScriptEnabled(true);
                urlKeyForVideoWebView = b.getString("webViewContentUrl");
                CheckForBackPress = b.getString("fromPushNotification");
                contentFromFAQ = b.getString("content");
                titleForHeaderToSet = b.getString("titleForHeader");
                callForWebServicePostMethod();
            }
            /*if(titleForHeaderToSet.equalsIgnoreCase("Message")) {
                callForWebServicePostMethod();

            }*/
       // }

        headerHeading.setText(titleForHeaderToSet);

        webView.loadData(contentFromFAQ, "text/html", "UTF-8");
        callForWebService();

        //Log.d("urltest",urlKeyForVideoWebView);
        if (urlKeyForVideoWebView != null) {
            methodForVideoWebview();
            llForVideoOnlyWebView.setVisibility(View.VISIBLE);
            llForTextOnlyWebView.setVisibility(View.GONE);

        } else {
            llForVideoOnlyWebView.setVisibility(View.GONE);
            llForTextOnlyWebView.setVisibility(View.VISIBLE);
        }
    }

    private void methodForVideoWebview() {
        Log.d("urltest",Urls.BASE_URL + urlKeyForVideoWebView);
        new GetServiceCall(Urls.BASE_URL + urlKeyForVideoWebView, GetServiceCall.TYPE_JSONOBJECT) {
            @Override
            public void response(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String title = jsonObject.optString("title");
                    tvForTitleForVideo.setText(title);
                    String image = jsonObject.optString("image");
                    Glide.with(WebViewActivity.this).load(image).into(imageViewForBannerVideo);
                    if(urlKeyForVideoWebView.contains("home-workouts")) {
                        imageViewForBannerVideo.setVisibility(View.GONE);
                    }
                    else {
                        imageViewForBannerVideo.setVisibility(View.VISIBLE);
                    }
                    String content = jsonObject.optString("content");
                    WebSettings webSettings = webViewForVideo.getSettings();
                    webSettings.setJavaScriptEnabled(true);
                    /*  webSettings.setUseWideViewPort(false);*/
                    Log.d("testvideo",content);
                    webViewForVideo.loadData(StringEscapeUtils.unescapeHtml4(content), "text/html", "utf-8");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void error(VolleyError error, String errorMsg) {
            }
        }.call();
    }


    private void callForWebService() {
        commonApi.showProgressDialog("");
        Log.d("TAG","Urls:" +Urls.BASE_URL + urlKey);
        new GetServiceCall(Urls.BASE_URL + urlKey, GetServiceCall.TYPE_JSONOBJECT) {
            @Override
            public void response(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String title = jsonObject.optString("title");
                    tvForTitle.setText(title);
                    String image = jsonObject.optString("image");
                    Glide.with(WebViewActivity.this).load(image).into(imageViewForBanner);
                    String content = jsonObject.optString("content");
                    Log.d("jsontest",content);
                    webView.loadData(StringEscapeUtils.unescapeHtml4(content), "text/html", "UTF-8");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                commonApi.dismissProgressDialog();
            }

            @Override
            public void error(VolleyError error, String errorMsg) {
                commonApi.dismissProgressDialog();
            }
        }.call();
    }

    @OnClick(R.id.rlBack)
    public void onViewClicked() {
        if (CheckForBackPress != null) {
            if (CheckForBackPress.equals("1")) {
                commonApi.openNewScreen(MainActivity.class, null);
                commonApi.finishActivity(WebViewActivity.this);
            }
        } else {
            commonApi.finishActivity(WebViewActivity.this);
        }


    }

    private void callForWebServicePostMethod() {
        StringRequest st = new StringRequest(Request.Method.POST, Urls.BASE_URL + urlKey, new Response.Listener<String>() {
        @Override
        public void onResponse(String jsonElements) {
            try {
                commonApi.dismissProgressDialog();

            if(Constant.FROM_FRAGMENT == 1) {   // from more fragement
                String data = jsonElements;
                webView.getSettings().setJavaScriptEnabled(true);
               // webView.loadData(data, "text/html; charset=utf-8", "UTF-8");
                Log.d("newtest",data);
                webView.loadDataWithBaseURL("", StringEscapeUtils.unescapeHtml4(data), "text/html", "UTF-8", "");
            } else if(Constant.FROM_FRAGMENT == 0) {   // from message adapter
                JSONObject jsonObject = new JSONObject(jsonElements);
                JSONArray jArray = jsonObject.getJSONArray("message_list");
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jsonObj = jArray.getJSONObject(i);
                 /*   String title = jsonObj.optString("message_date");
                    tvForTitle.setText(title);*/
                    String content = jsonObj.optString("message");
                    webView.loadData(StringEscapeUtils.unescapeHtml4(content), "text/html", "UTF-8");
                }
            }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            if (error.networkResponse == null) {
                commonApi.dismissProgressDialog();
                if (error.getClass().equals(NoConnectionError.class)) {

                }
            } else {
                commonApi.dismissProgressDialog();

            }
            error.printStackTrace();
        }
    });
        st.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 6, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getApplicationContext()).add(st);
}

    }

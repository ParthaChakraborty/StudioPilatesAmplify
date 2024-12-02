package com.studio.amplify.activity;

import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.studio.amplify.R;
import com.studio.amplify.adapter.FAQListAdapter;
import com.studio.amplify.model.FAQContentListItem;
import com.studio.amplify.model.FAQListItem;
import com.studio.amplify.util.BaseActivity;
import com.studio.amplify.util.CommonApi;
import com.studio.amplify.util.Urls;
import com.studio.amplify.volleyparser.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FAQActivity extends BaseActivity {

    //header
    @BindView(R.id.headerHeading)
    TextView headerHeading;
    //header

    @BindView(R.id.rvForFAQList)
    RecyclerView rvForFAQList;

    ArrayList<FAQListItem> faqListItemArrayList;
    FAQListAdapter faqListAdapter;

    CommonApi commonApi;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        commonApi = new CommonApi(FAQActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);

        headerHeading.setText(R.string.faq);

        faqListItemArrayList = new ArrayList<>();
        getDataForFAQListing();

    }

    private void getDataForFAQListing() {
        commonApi.showProgressDialog("");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Urls.GET_DETAIL_FOR_FAQ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                FAQListItem faqListItemObject = new FAQListItem();
                                JSONObject jsonObjectForFAQ = jsonArray.getJSONObject(i);
                                faqListItemObject.setCategory(jsonObjectForFAQ.optString("category"));
                                faqListItemArrayList.add(faqListItemObject);

                                ArrayList<FAQContentListItem> faqContentListItemArrayList = new ArrayList<>();
                                JSONArray jsonArrayForFAQContent = jsonObjectForFAQ.getJSONArray("faq_content");
                                for (int j = 0; j < jsonArrayForFAQContent.length(); j++) {
                                    FAQContentListItem faqContentListItem = new FAQContentListItem();
                                    JSONObject jsonObjectForContentList = jsonArrayForFAQContent.getJSONObject(j);
                                    faqContentListItem.setTitle(jsonObjectForContentList.optString("title"));
                                    faqContentListItem.setContent(jsonObjectForContentList.optString("content"));
                                    faqContentListItemArrayList.add(faqContentListItem);
                                }
                                faqListItemObject.setFaqContentListItemArrayList(faqContentListItemArrayList);
                            }
                            faqListAdapter = new FAQListAdapter(faqListItemArrayList, FAQActivity.this);
                            rvForFAQList.setAdapter(faqListAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        commonApi.dismissProgressDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                commonApi.dismissProgressDialog();
            }
        });
        MyApplication.getInstance().addToRequestQueue(stringRequest);
    }

    @OnClick(R.id.rlBack)
    public void onViewClicked() {
        commonApi.finishActivity(FAQActivity.this);
    }
}

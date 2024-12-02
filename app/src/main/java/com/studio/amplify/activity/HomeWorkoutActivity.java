package com.studio.amplify.activity;

import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;


import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.studio.amplify.R;
import com.studio.amplify.adapter.HomeWorkoutListAdapter;
import com.studio.amplify.model.HomeWorkoutListItem;
import com.studio.amplify.util.BaseActivity;
import com.studio.amplify.util.CommonApi;
import com.studio.amplify.util.Urls;
import com.studio.amplify.volleyparser.GetServiceCall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeWorkoutActivity extends BaseActivity {
    String urlKeyForVideoWebView, titleForHeaderToSet;

    @BindView(R.id.rootNestedScroll)
    NestedScrollView rootNestedScroll;
    @BindView(R.id.rvForVideos)
    RecyclerView rvForVideos;

    @BindView(R.id.workOutText)
    TextView workOutText;
    @BindView(R.id.ivBannerImage)
    ImageView ivBannerImage;

    ArrayList<HomeWorkoutListItem> homeWorkoutListItemArrayList;
    HomeWorkoutListAdapter homeWorkoutListAdapter;
    int count = 1;
    CommonApi commonApi;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        commonApi = new CommonApi(HomeWorkoutActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_workout);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);

        Bundle b = getIntent().getExtras();
        urlKeyForVideoWebView = b.getString("urlKeyForVideoWebView");
        titleForHeaderToSet = b.getString("titleForHeader");

        workOutText.setText(titleForHeaderToSet);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvForVideos.setLayoutManager(layoutManager);

        homeWorkoutListItemArrayList = new ArrayList<>();
        rvForVideos.setNestedScrollingEnabled(true);

        getDataForVideos();

        if (rootNestedScroll != null) {
            rootNestedScroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                        count++;
                        getDataForVideos();
                    }
                }
            });
        }
    }

    private void getDataForVideos() {
        commonApi.showProgressDialog("");
        new GetServiceCall(urlKeyForVideoWebView + count, GetServiceCall.TYPE_JSONOBJECT) {
            @Override
            public void response(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String bannerImage =  Urls.BASE_URL + jsonObject.getString("banner");
                    Glide.with(HomeWorkoutActivity.this).load(bannerImage).into(ivBannerImage);
                    JSONArray jsonArray = jsonObject.getJSONArray("lists");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        HomeWorkoutListItem homeWorkoutListItem = new HomeWorkoutListItem();
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        homeWorkoutListItem.setTitle(jsonObject1.optString("title"));
                        homeWorkoutListItem.setImage(jsonObject1.optString("image"));
                        homeWorkoutListItem.setUrl(jsonObject1.optString("url"));
                        homeWorkoutListItemArrayList.add(homeWorkoutListItem);
                    }
                    homeWorkoutListAdapter = new HomeWorkoutListAdapter(homeWorkoutListItemArrayList, HomeWorkoutActivity.this,titleForHeaderToSet);
                    rvForVideos.setAdapter(homeWorkoutListAdapter);

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

    @OnClick(R.id.imgBack)
    public void onViewClicked() {
        commonApi.finishActivity(HomeWorkoutActivity.this);
    }
}

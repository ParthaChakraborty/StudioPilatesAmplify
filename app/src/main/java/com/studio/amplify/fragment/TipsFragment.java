package com.studio.amplify.fragment;

import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.studio.amplify.R;
import com.studio.amplify.adapter.HealthtipsListAdapter;
import com.studio.amplify.model.HealthTipsListItem;
import com.studio.amplify.util.BaseFragment;
import com.studio.amplify.util.CommonApi;
import com.studio.amplify.util.Urls;
import com.studio.amplify.volleyparser.GetServiceCall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TipsFragment extends BaseFragment {
    //header
    @BindView(R.id.textViewHealthTips)
    TextView textViewHealthTips;
    //header
    //banner
    @BindView(R.id.ivBannerImage)
    ImageView ivBannerImage;
    //banner

    @BindView(R.id.rvForHealthTipsList)
    RecyclerView rvForHealthTipsList;
    @BindView(R.id.rootNestedScroll)
    NestedScrollView rootNestedScroll;
    @BindView(R.id.scrollView)
    ScrollView scrollView;

    Unbinder unbinder;
    ArrayList<HealthTipsListItem> healthTipsListItemArrayList;
    HealthtipsListAdapter healthtipsListAdapter;
    int count = 1;
    private CommonApi commonApi;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.tips_fragment, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this, layout);
        unbinder = ButterKnife.bind(this, layout);
        commonApi = new CommonApi(getActivity());

        setHasOptionsMenu(true);
        healthTipsListItemArrayList = new ArrayList<>();
        rvForHealthTipsList.setNestedScrollingEnabled(false);

        getDataForHealthTipsList();

        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        if (rootNestedScroll != null) {
            rootNestedScroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                        count++;
                        getDataForHealthTipsList();
                    }
                }
            });
        }
        return layout;
    }

    private void getDataForHealthTipsList() {
        commonApi.showProgressDialog("");
        Log.d("TAG", "tipsUrl:" + Urls.GET_HEALTH_TIPS_LISTING + count);
        new GetServiceCall(Urls.GET_HEALTH_TIPS_LISTING + count, GetServiceCall.TYPE_JSONOBJECT) {

            @Override
            public void response(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    System.out.println("tipsResp:" +response);
                    Glide.with(getActivity()).load(Urls.BASE_URL + jsonObject.optString("banner")).into(ivBannerImage);
                    JSONArray jsonArrayHealthTips = jsonObject.getJSONArray("lists");
                    for (int i = 0; i < jsonArrayHealthTips.length(); i++) {
                        HealthTipsListItem healthTipsListItem = new HealthTipsListItem();
                        JSONObject jsonObjectHealthTipsDetails = jsonArrayHealthTips.getJSONObject(i);
                        healthTipsListItem.setTitle(jsonObjectHealthTipsDetails.optString("title"));
                        healthTipsListItem.setUrl(jsonObjectHealthTipsDetails.optString("url"));
                        healthTipsListItem.setPost_date(jsonObjectHealthTipsDetails.optString("post_date"));
                        healthTipsListItem.setImage(jsonObjectHealthTipsDetails.optString("image"));
                        healthTipsListItemArrayList.add(healthTipsListItem);
                    }
                    healthtipsListAdapter = new HealthtipsListAdapter(healthTipsListItemArrayList, getActivity());
                    rvForHealthTipsList.setAdapter(healthtipsListAdapter);
                    healthtipsListAdapter.notifyDataSetChanged();


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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}


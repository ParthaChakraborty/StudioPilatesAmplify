package com.studio.amplify.fragment;

import android.app.FragmentTransaction;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.studio.amplify.R;
import com.studio.amplify.adapter.ActiveAdapter;
import com.studio.amplify.adapter.ChallengeAndCampaignAdapter;
import com.studio.amplify.adapter.MealsListAdapter;
import com.studio.amplify.model.ChallengeCampaignListItem;
import com.studio.amplify.model.MealListItem;
import com.studio.amplify.model.UpcomingChallengeAndCampaignsItem;
import com.studio.amplify.util.BaseFragment;
import com.studio.amplify.util.CommonApi;
import com.studio.amplify.util.LoadingInterface;
import com.studio.amplify.util.Urls;
import com.studio.amplify.volleyparser.GetServiceCall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ActiveChallengeAndCampaignFragment extends BaseFragment implements LoadingInterface {


    @BindView(R.id.ivBannerImage)
    ImageView ivBannerImage;
   /* @BindView(R.id.ivLogo)
    ImageView ivLogo;*/
    @BindView(R.id.mealPlanText)
    TextView mealPlanText;
    @BindView(R.id.radioChallenge)
    RadioButton radioChallenge;
    @BindView(R.id.radioCampaign)
    RadioButton radioCampaign;
    @BindView(R.id.toggle)
    RadioGroup toggle;
    @BindView(R.id.rvForActiveClngAndCmpn)
    RecyclerView rvForActiveClngAndCmpn;
    @BindView(R.id.tvHeadingNotFound)
    TextView tvHeadingNotFound;
    @BindView(R.id.rootLayoutForViews)
    LinearLayout rootLayoutForViews;
    @BindView(R.id.mainScr)
    NestedScrollView mainScr;
    @BindView(R.id.scrollView)
    ScrollView scrollView;

    Unbinder unbinder;
    private CommonApi commonApi;
    ActiveAdapter activeAdapter;
    UpcomingChallengeAndCampaignsItem upcomingChallengeAndCampaignsItem;
    ArrayList<UpcomingChallengeAndCampaignsItem> upcomingChallengeAndCampaignsItemsList
            = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.active_challenge_campaign_fragment, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this, v);
        unbinder = ButterKnife.bind(this, v);
        commonApi = new CommonApi(getActivity());
        getActiveChallenges();
        toggle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioCampaign) {
                    getActiveCampaigns();
                } else if (checkedId == R.id.radioChallenge) {
                    getActiveChallenges();
                }
            }
        });

        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        return v;
    }

    public void getActiveChallenges() {
        upcomingChallengeAndCampaignsItemsList.clear();
     //   RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        // Initialize a new JsonArrayRequest instance
        /*JsonArrayRequest request = new JsonArrayRequest(Urls.ACTIVE_CHALLENGES + loginCredentials.getUserId(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        try {
                            Log.d("***activeResp", jsonArray.toString());
                            //   if (upcomingChallengeAndCampaignsItem != null) {
                            for (int z = 0; z < jsonArray.length(); z++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(z);
                              //  Glide.with(getActivity()).load(Urls.BASE_URL + jsonObject.optString("banner")).into(ivBannerImage);
                                upcomingChallengeAndCampaignsItem = new UpcomingChallengeAndCampaignsItem();
                                upcomingChallengeAndCampaignsItem.setId(jsonObject.getInt("ID"));
                                upcomingChallengeAndCampaignsItem.setHeading(jsonObject.getString("Heading"));
                                upcomingChallengeAndCampaignsItem.setStatus(jsonObject.getInt("status"));
                                upcomingChallengeAndCampaignsItem.setStartDate(jsonObject.getString("Sdate"));
                                upcomingChallengeAndCampaignsItem.setEndDate(jsonObject.getString("Edate"));
                                upcomingChallengeAndCampaignsItem.setGroup_id(jsonObject.getInt("group_id"));
                                upcomingChallengeAndCampaignsItemsList.add(upcomingChallengeAndCampaignsItem);
                                 tvHeadingNotFound.setVisibility(View.GONE);
                            }
                            activeAdapter = new ActiveAdapter(loginCredentials.getUserId(), upcomingChallengeAndCampaignsItemsList, getActivity(), "CH", ActiveChallengeAndCampaignFragment.this);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                            rvForActiveClngAndCmpn.setLayoutManager(layoutManager);
                            rvForActiveClngAndCmpn.setAdapter(activeAdapter);
                          //  tvHeadingNotFound.setVisibility(View.VISIBLE);
                            tvHeadingNotFound.setText(R.string.challenge_not_found);
                            *//*} else {
                              tvHeadingNotFound.setVisibility(View.VISIBLE);
                              tvHeadingNotFound.setText(R.string.challenge_not_found);
                            }*//*
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        System.out.println(volleyError.toString());
                    }
                });

        requestQueue.add(request);*/


        new GetServiceCall(Urls.ACTIVE_CHALLENGES + loginCredentials.getUserId(),GetServiceCall.TYPE_JSONOBJECT) {
            @Override
            public void response(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("***activeResp", jsonObject.toString());
                    System.out.println(" actObject : " + jsonObject.toString());
                    System.out.println("actUrl : " + Urls.ACTIVE_CHALLENGES + loginCredentials.getUserId());
                    Glide.with(getActivity()).load(Urls.BASE_URL + jsonObject.optString("banner")).into(ivBannerImage);

                    JSONArray jsonArray = jsonObject.getJSONArray("rows");
                    for (int z = 0; z < jsonArray.length(); z++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(z);
                        upcomingChallengeAndCampaignsItem = new UpcomingChallengeAndCampaignsItem();
                        upcomingChallengeAndCampaignsItem.setId(jsonObject1.getInt("ID"));
                        upcomingChallengeAndCampaignsItem.setHeading(jsonObject1.getString("Heading"));
                        upcomingChallengeAndCampaignsItem.setStatus(jsonObject1.getInt("status"));
                        upcomingChallengeAndCampaignsItem.setStartDate(jsonObject1.getString("Sdate"));
                        upcomingChallengeAndCampaignsItem.setEndDate(jsonObject1.getString("Edate"));
                        upcomingChallengeAndCampaignsItem.setGroup_id(jsonObject1.getInt("group_id"));
                        //upcomingChallengeAndCampaignsItem.setImage(jsonObject.getString("banner"));
                        upcomingChallengeAndCampaignsItemsList.add(upcomingChallengeAndCampaignsItem);
                        tvHeadingNotFound.setVisibility(View.GONE);
                    }
                    activeAdapter = new ActiveAdapter(loginCredentials.getUserId(), upcomingChallengeAndCampaignsItemsList, getActivity(), "CH", ActiveChallengeAndCampaignFragment.this);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    rvForActiveClngAndCmpn.setLayoutManager(layoutManager);
                    rvForActiveClngAndCmpn.setAdapter(activeAdapter);
                    //  tvHeadingNotFound.setVisibility(View.VISIBLE);
                    tvHeadingNotFound.setText(R.string.challenge_not_found);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //commonApi.dismissProgressDialog();
            }

            @Override
            public void error(VolleyError error, String errorMsg) {
               // commonApi.dismissProgressDialog();
            }
        }.call();
    }

    /*public void getActiveCampaigns() {
        upcomingChallengeAndCampaignsItemsList.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        // Initialize a new JsonArrayRequest instance
        JsonArrayRequest request = new JsonArrayRequest(Urls.ACTIVE_CAMPAIGNS + loginCredentials.getUserId(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        try {
                            Log.d("***activecampaignResp", jsonArray.toString());
                            //  if(upcomingChallengeAndCampaignsItem != null) {
                            for (int z = 0; z < jsonArray.length(); z++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(z);
                           //     Glide.with(getActivity()).load(Urls.BASE_URL + jsonObject.optString("banner")).into(ivBannerImage);
                                upcomingChallengeAndCampaignsItem = new UpcomingChallengeAndCampaignsItem();
                                upcomingChallengeAndCampaignsItem.setId(jsonObject.getInt("ID"));
                                upcomingChallengeAndCampaignsItem.setHeading(jsonObject.getString("Heading"));
                                upcomingChallengeAndCampaignsItem.setStatus(jsonObject.getInt("status"));
                                upcomingChallengeAndCampaignsItem.setStartDate(jsonObject.getString("Sdate"));
                                upcomingChallengeAndCampaignsItem.setEndDate(jsonObject.getString("Edate"));
                                upcomingChallengeAndCampaignsItem.setGroup_id(jsonObject.getInt("group_id"));
                                upcomingChallengeAndCampaignsItemsList.add(upcomingChallengeAndCampaignsItem);
                               //  tvHeadingNotFound.setVisibility(View.GONE);
                            }
                            activeAdapter = new ActiveAdapter(loginCredentials.getUserId(), upcomingChallengeAndCampaignsItemsList, getActivity(), "CA", ActiveChallengeAndCampaignFragment.this);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                            rvForActiveClngAndCmpn.setLayoutManager(layoutManager);
                            rvForActiveClngAndCmpn.setAdapter(activeAdapter);
                           *//* tvHeadingNotFound.setVisibility(View.VISIBLE);
                            tvHeadingNotFound.setText(R.string.no_heading);*//*
                           *//*else {
                                tvHeadingNotFound.setVisibility(View.VISIBLE);
                                tvHeadingNotFound.setText(R.string.no_heading);
                            }*//*

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                    }
                });

        requestQueue.add(request);
    }*/

    @Override
    public void onRedirect(UpcomingChallengeAndCampaignsItem upcomingChallengeAndCampaignsItem, int userId, String type) {
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.addToBackStack(null);
   /*     Bundle args = new Bundle();
        args.putInt("ch_ca_id", upcomingChallengeAndCampaignsItem.getId());
        args.putInt("user_id", userId);
        args.putString("status",type);
        args.putString("From","Upcoming");*/

        loginCredentials.setCAMP_CHAL_DETAILS((upcomingChallengeAndCampaignsItem.getId()),type);

        FeedFragment feedFragment = new FeedFragment();
        //feedFragment.setArguments(args);
        ft.replace(R.id.rootLayout, feedFragment, "FeedFrag");
        ft.commit();

    }

    @Override
    public void onRedirectFromMyCampaignsAndChallenges(ChallengeCampaignListItem challengeCampaignListItem, int userId, String type) {

    }

    @Override
    public void onRediredectToMainActivity() {

    }

    public void getActiveCampaigns() {
        upcomingChallengeAndCampaignsItemsList.clear();
        new GetServiceCall(Urls.ACTIVE_CAMPAIGNS + loginCredentials.getUserId(),GetServiceCall.TYPE_JSONOBJECT) {
            @Override
            public void response(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("***activeCamResp", jsonObject.toString());
                    System.out.println(" camObject : " + jsonObject.toString());
                    System.out.println("camUrl : " + Urls.ACTIVE_CAMPAIGNS + loginCredentials.getUserId());
                    Glide.with(getActivity()).load(Urls.BASE_URL + jsonObject.optString("banner")).into(ivBannerImage);

                    JSONArray jsonArray = jsonObject.getJSONArray("rows");
                    for (int z = 0; z < jsonArray.length(); z++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(z);
                        upcomingChallengeAndCampaignsItem = new UpcomingChallengeAndCampaignsItem();
                        upcomingChallengeAndCampaignsItem.setId(jsonObject1.getInt("ID"));
                        upcomingChallengeAndCampaignsItem.setHeading(jsonObject1.getString("Heading"));
                        upcomingChallengeAndCampaignsItem.setStatus(jsonObject1.getInt("status"));
                        upcomingChallengeAndCampaignsItem.setStartDate(jsonObject1.getString("Sdate"));
                        upcomingChallengeAndCampaignsItem.setEndDate(jsonObject1.getString("Edate"));
                        upcomingChallengeAndCampaignsItem.setGroup_id(jsonObject1.getInt("group_id"));
                        upcomingChallengeAndCampaignsItemsList.add(upcomingChallengeAndCampaignsItem);
                    }
                    activeAdapter = new ActiveAdapter(loginCredentials.getUserId(), upcomingChallengeAndCampaignsItemsList, getActivity(), "CA", ActiveChallengeAndCampaignFragment.this);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    rvForActiveClngAndCmpn.setLayoutManager(layoutManager);
                    rvForActiveClngAndCmpn.setAdapter(activeAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //commonApi.dismissProgressDialog();
            }

            @Override
            public void error(VolleyError error, String errorMsg) {
                // commonApi.dismissProgressDialog();
            }
        }.call();
    }
}

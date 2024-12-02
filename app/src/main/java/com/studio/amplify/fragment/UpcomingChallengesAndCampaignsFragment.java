package com.studio.amplify.fragment;

import android.app.FragmentTransaction;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.studio.amplify.R;
import com.studio.amplify.adapter.ChallengeAndCampaignAdapter;
import com.studio.amplify.model.ChallengeCampaignListItem;
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


public class UpcomingChallengesAndCampaignsFragment extends BaseFragment implements LoadingInterface {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.toggle)
    RadioGroup toggle;
    @BindView(R.id.radioButtonChallenge)
    RadioButton radioButtonChallenge;
    @BindView(R.id.radioButtonCampaign)
    RadioButton radioButtonCampaign;
    @BindView(R.id.rvForUpcomingClngAndCmpn)
    RecyclerView rvForUpcomingClngAndCmpn;
    Unbinder unbinder;
    @BindView(R.id.tvHeadingNotFound)
    TextView tvHeadingNotFound;
    @BindView(R.id.ivBannerImage)
    ImageView ivBannerImage;
    @BindView(R.id.scrollView)
    ScrollView scrollView;

    private CommonApi commonApi;
    ChallengeAndCampaignAdapter challengeAndCampaignAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    UpcomingChallengeAndCampaignsItem upcomingChallengeAndCampaignsItem;
    ArrayList<UpcomingChallengeAndCampaignsItem> upcomingChallengeAndCampaignsItemsList
            = new ArrayList<>();

    public UpcomingChallengesAndCampaignsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpcomingChallengesAndCampaignsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpcomingChallengesAndCampaignsFragment newInstance(String param1, String param2) {
        UpcomingChallengesAndCampaignsFragment fragment = new UpcomingChallengesAndCampaignsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_upcoming_challenges_and_campaigns, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this, v);
        unbinder = ButterKnife.bind(this, v);
        commonApi = new CommonApi(getActivity());
        getUpcomingChallenges();
        toggle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButtonCampaign) {
                    getUpcomingCampaigns();
                } else if (checkedId == R.id.radioButtonChallenge) {
                    getUpcomingChallenges();
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

    /* public void getUpcomingChallenges() {
         upcomingChallengeAndCampaignsItemsList.clear();
         RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
         // Initialize a new JsonArrayRequest instance
         JsonArrayRequest request = new JsonArrayRequest(Urls.UPCOMING_CHALLENGES + loginCredentials.getUserId(),
                 new Response.Listener<JSONArray>() {
                     @Override
                     public void onResponse(JSONArray jsonArray) {
                         try {
                             Log.d("***resp", jsonArray.toString());
                          //   if (upcomingChallengeAndCampaignsItem != null) {
                                 for (int z = 0; z < jsonArray.length(); z++) {
                                     JSONObject jsonObject = jsonArray.getJSONObject(z);

                                     upcomingChallengeAndCampaignsItem = new UpcomingChallengeAndCampaignsItem();
                                     upcomingChallengeAndCampaignsItem.setId(jsonObject.getInt("ID"));
                                     upcomingChallengeAndCampaignsItem.setHeading(jsonObject.getString("Heading"));
                                     upcomingChallengeAndCampaignsItem.setStatus(jsonObject.getInt("status"));
                                     upcomingChallengeAndCampaignsItem.setStartDate(jsonObject.getString("Sdate"));
                                     upcomingChallengeAndCampaignsItem.setEndDate(jsonObject.getString("Edate"));
                                     upcomingChallengeAndCampaignsItemsList.add(upcomingChallengeAndCampaignsItem);
                                    // tvHeadingNotFound.setVisibility(View.GONE);
                                 }
                                 challengeAndCampaignAdapter = new ChallengeAndCampaignAdapter(loginCredentials.getUserId(), upcomingChallengeAndCampaignsItemsList, getActivity(), "CH", UpcomingChallengesAndCampaignsFragment.this);
                                 LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                                 rvForUpcomingClngAndCmpn.setLayoutManager(layoutManager);
                                 rvForUpcomingClngAndCmpn.setAdapter(challengeAndCampaignAdapter);
                            *//* } else {
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
                    }
                });

        requestQueue.add(request);
    }
*/
    public void getUpcomingChallenges() {
        upcomingChallengeAndCampaignsItemsList.clear();
        new GetServiceCall(Urls.UPCOMING_CHALLENGES + loginCredentials.getUserId(), GetServiceCall.TYPE_JSONOBJECT) {
            @Override
            public void response(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("***upChResp", jsonObject.toString());
                    System.out.println(" upChObject : " + jsonObject.toString());
                    System.out.println("upChUrl : " + Urls.UPCOMING_CHALLENGES + loginCredentials.getUserId());
                    Glide.with(getActivity()).load(Urls.BASE_URL + jsonObject.optString("banner")).into(ivBannerImage);

                    JSONArray jsonArray = jsonObject.getJSONArray("rows");
                    for (int z = 0; z < jsonArray.length(); z++) {
                        JSONObject jsonObject2 = jsonArray.getJSONObject(z);
                        upcomingChallengeAndCampaignsItem = new UpcomingChallengeAndCampaignsItem();
                        upcomingChallengeAndCampaignsItem.setId(jsonObject2.getInt("ID"));
                        upcomingChallengeAndCampaignsItem.setHeading(jsonObject2.getString("Heading"));
                        upcomingChallengeAndCampaignsItem.setStatus(jsonObject2.getInt("status"));
                        upcomingChallengeAndCampaignsItem.setStartDate(jsonObject2.getString("Sdate"));
                        upcomingChallengeAndCampaignsItem.setEndDate(jsonObject2.getString("Edate"));
                        upcomingChallengeAndCampaignsItemsList.add(upcomingChallengeAndCampaignsItem);
                    }
                    challengeAndCampaignAdapter = new ChallengeAndCampaignAdapter(loginCredentials.getUserId(), upcomingChallengeAndCampaignsItemsList, getActivity(), "CH", UpcomingChallengesAndCampaignsFragment.this);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    rvForUpcomingClngAndCmpn.setLayoutManager(layoutManager);
                    rvForUpcomingClngAndCmpn.setAdapter(challengeAndCampaignAdapter);

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


    /*public void getUpcomingCampaigns() {
        upcomingChallengeAndCampaignsItemsList.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        // Initialize a new JsonArrayRequest instance
        JsonArrayRequest request = new JsonArrayRequest(Urls.UPCOMING_CAMPAIGNS + loginCredentials.getUserId(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        try {
                            Log.d("***campaignResp", jsonArray.toString());
                            //  if(upcomingChallengeAndCampaignsItem != null) {
                            for (int z = 0; z < jsonArray.length(); z++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(z);

                                upcomingChallengeAndCampaignsItem = new UpcomingChallengeAndCampaignsItem();
                                upcomingChallengeAndCampaignsItem.setId(jsonObject.getInt("ID"));
                                upcomingChallengeAndCampaignsItem.setHeading(jsonObject.getString("Heading"));
                                upcomingChallengeAndCampaignsItem.setStatus(jsonObject.getInt("status"));
                                upcomingChallengeAndCampaignsItem.setStartDate(jsonObject.getString("Sdate"));
                                upcomingChallengeAndCampaignsItem.setEndDate(jsonObject.getString("Edate"));
                                upcomingChallengeAndCampaignsItemsList.add(upcomingChallengeAndCampaignsItem);
                                // tvHeadingNotFound.setVisibility(View.GONE);
                            }
                            challengeAndCampaignAdapter = new ChallengeAndCampaignAdapter(loginCredentials.getUserId(), upcomingChallengeAndCampaignsItemsList, getActivity(), "CA", UpcomingChallengesAndCampaignsFragment.this);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                            rvForUpcomingClngAndCmpn.setLayoutManager(layoutManager);
                            rvForUpcomingClngAndCmpn.setAdapter(challengeAndCampaignAdapter);
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

    public void getUpcomingCampaigns() {
        upcomingChallengeAndCampaignsItemsList.clear();
        new GetServiceCall(Urls.UPCOMING_CAMPAIGNS + loginCredentials.getUserId(), GetServiceCall.TYPE_JSONOBJECT) {
            @Override
            public void response(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("***upCamResp", jsonObject.toString());
                    System.out.println(" upCamObject : " + jsonObject.toString());
                    System.out.println("upCamUrl : " + Urls.UPCOMING_CAMPAIGNS + loginCredentials.getUserId());
                    Glide.with(getActivity()).load(Urls.BASE_URL + jsonObject.optString("banner")).into(ivBannerImage);

                    JSONArray jsonArray = jsonObject.getJSONArray("rows");
                    for (int z = 0; z < jsonArray.length(); z++) {
                        JSONObject jsonObject2 = jsonArray.getJSONObject(z);
                        upcomingChallengeAndCampaignsItem = new UpcomingChallengeAndCampaignsItem();
                        upcomingChallengeAndCampaignsItem.setId(jsonObject2.getInt("ID"));
                        upcomingChallengeAndCampaignsItem.setHeading(jsonObject2.getString("Heading"));
                        upcomingChallengeAndCampaignsItem.setStatus(jsonObject2.getInt("status"));
                        upcomingChallengeAndCampaignsItem.setStartDate(jsonObject2.getString("Sdate"));
                        upcomingChallengeAndCampaignsItem.setEndDate(jsonObject2.getString("Edate"));
                        upcomingChallengeAndCampaignsItemsList.add(upcomingChallengeAndCampaignsItem);
                    }
                    challengeAndCampaignAdapter = new ChallengeAndCampaignAdapter(loginCredentials.getUserId(), upcomingChallengeAndCampaignsItemsList, getActivity(), "CA", UpcomingChallengesAndCampaignsFragment.this);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    rvForUpcomingClngAndCmpn.setLayoutManager(layoutManager);
                    rvForUpcomingClngAndCmpn.setAdapter(challengeAndCampaignAdapter);
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


    @Override
    public void onRedirect(UpcomingChallengeAndCampaignsItem upcomingChallengeAndCampaignsItem, int userId, String type) {
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.addToBackStack(null);
   /*     Bundle args = new Bundle();
        args.putInt("ch_ca_id", upcomingChallengeAndCampaignsItem.getId());
        args.putInt("user_id", userId);
        args.putString("status",type);
        args.putString("From","Upcoming");*/

        loginCredentials.setCAMP_CHAL_DETAILS(upcomingChallengeAndCampaignsItem.getId(), type);

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



    // TODO: Rename method, update argument and hook method into UI event
}

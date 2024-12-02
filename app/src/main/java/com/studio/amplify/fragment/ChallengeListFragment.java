package com.studio.amplify.fragment;

import android.app.FragmentTransaction;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.studio.amplify.adapter.ChallengeCampaignListAdapter;
import com.studio.amplify.interfaces.ParticipateClick;
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

public class ChallengeListFragment extends BaseFragment implements LoadingInterface, ParticipateClick {
    @BindView(R.id.ivBannerImage)
    ImageView ivBannerImage;
    /*@BindView(R.id.ivLogo)
    ImageView ivLogo;*/
    @BindView(R.id.mealPlanText)
    TextView mealPlanText;
    @BindView(R.id.rvForChallengeCmpgn)
    RecyclerView rvForChallengeCmpgn;
    @BindView(R.id.rootLayoutForViews)
    LinearLayout rootLayoutForViews;
    @BindView(R.id.mainScr)
    NestedScrollView mainScr;
    @BindView(R.id.tvNoHeading)
    TextView tvNoHeading;
    Unbinder unbinder;
    @BindView(R.id.scrollView)
    ScrollView scrollView;

    private CommonApi commonApi;
    ChallengeCampaignListItem challengeListItem;
    ArrayList<ChallengeCampaignListItem> challengeListItemArrayList
            = new ArrayList<>();
    ChallengeCampaignListAdapter challengeAndCampaignAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.challenge_campaign_list_fragment, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        commonApi = new CommonApi(getActivity());
        unbinder = ButterKnife.bind(this, v);

        getChallengesList();

        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        return v;
    }

    @Override
    public void onRedirect(UpcomingChallengeAndCampaignsItem upcomingChallengeAndCampaignsItem, int userId, String type) {

    }

    @Override
    public void onRedirectFromMyCampaignsAndChallenges(ChallengeCampaignListItem challengeCampaignListItem, int userId, String type) {
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.addToBackStack(null);
        /*Bundle args = new Bundle();
        args.putInt("ch_ca_id", challengeCampaignListItem.getId());
        args.putInt("user_id", userId);
        args.putString("status",type);
        args.putString("From","MyChallenges");*/
        loginCredentials.setCAMP_CHAL_DETAILS(challengeCampaignListItem.getId(), type);
        FeedFragment feedFragment = new FeedFragment();
        //feedFragment.setArguments(args);
        ft.replace(R.id.rootLayout, feedFragment, "FeedFrag");
        ft.commit();
    }

    @Override
    public void onRediredectToMainActivity() {

    }

    public void getChallengesList() {
        challengeListItemArrayList.clear();
        new GetServiceCall(Urls.CHALLENGES_LIST + loginCredentials.getUserId(), GetServiceCall.TYPE_JSONOBJECT) {
            @Override
            public void response(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("***chResp", jsonObject.toString());
                    System.out.println(" chObject : " + jsonObject.toString());
                    System.out.println("chUrl : " + Urls.CHALLENGES_LIST + loginCredentials.getUserId());
                    Glide.with(getActivity()).load(Urls.BASE_URL + jsonObject.optString("banner")).into(ivBannerImage);

                    JSONArray jsonArray = jsonObject.getJSONArray("rows");
                    if(jsonArray.length() > 0) {
                        for (int z = 0; z < jsonArray.length(); z++) {
                            JSONObject jsonObject4 = jsonArray.getJSONObject(z);
                            challengeListItem = new ChallengeCampaignListItem();
                            challengeListItem.setId(jsonObject4.getInt("ID"));
                            challengeListItem.setHeading(jsonObject4.getString("Heading"));
                            challengeListItem.setStartDate(jsonObject4.getString("Sdate"));
                            challengeListItem.setEndDate(jsonObject4.getString("Edate"));
                            challengeListItem.setClassBuy(jsonObject4.getString("noofclassbuy"));
                            challengeListItem.setClassOpted(jsonObject4.getString("classcomplte"));
                            challengeListItemArrayList.add(challengeListItem);
                        }
                    }else {
                        tvNoHeading.setVisibility(View.VISIBLE);
                        tvNoHeading.setText("You have not participated in any challenge");
                    }
                    challengeAndCampaignAdapter = new ChallengeCampaignListAdapter(challengeListItemArrayList, getActivity(),ChallengeListFragment.this,loginCredentials.getUserId(),"CH",ChallengeListFragment.this);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    rvForChallengeCmpgn.setLayoutManager(layoutManager);
                    rvForChallengeCmpgn.setAdapter(challengeAndCampaignAdapter);
                    mealPlanText.setText(R.string.my_challenges);
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
    public void onItemClick() {
        getChallengesList();
    }
}

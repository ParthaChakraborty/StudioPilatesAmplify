package com.studio.amplify.fragment;

import android.app.FragmentTransaction;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.studio.amplify.BuildConfig;
import com.studio.amplify.R;
import com.studio.amplify.activity.CommunityWebviewActivity;
import com.studio.amplify.activity.EditProfileActivity;
import com.studio.amplify.activity.FAQActivity;
import com.studio.amplify.activity.HomeWorkoutActivity;
import com.studio.amplify.activity.LoginActivity;
import com.studio.amplify.activity.MyMessagingActivity;
import com.studio.amplify.activity.RecipeLibraryActivity;
import com.studio.amplify.activity.ShoppingListActivity;
import com.studio.amplify.activity.WebViewActivity;
import com.studio.amplify.util.BaseFragment;
import com.studio.amplify.util.CommonApi;
import com.studio.amplify.util.Constant;
import com.studio.amplify.util.Urls;
import com.studio.amplify.volleyparser.GetServiceCall;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MoreFragment extends BaseFragment {
    Unbinder unbinder;
    //header
    @BindView(R.id.headerHeading)
    TextView headerHeading;
    @BindView(R.id.rlBack)
    RelativeLayout rlBack;
    //header

    @BindView(R.id.tvHeadingAbout)
    TextView tvHeadingAbout;
    @BindView(R.id.tvHeadingAccount)
    TextView tvHeadingAccount;

    //label

    @BindView(R.id.tvFAQ)
    TextView tvFAQ;
    @BindView(R.id.tvHomeWorkouts)
    TextView tvHomeWorkouts;
    @BindView(R.id.tvCardioPlans)
    TextView tvCardioPlans;
    @BindView(R.id.tvEditProfile)
    TextView tvEditProfile;
    @BindView(R.id.tvMyMessaging)
    TextView tvMyMessaging;
    @BindView(R.id.tvPrivacyPolicy)
    TextView tvPrivacyPolicy;
    @BindView(R.id.tvTermsAndConditions)
    TextView tvTermsAndConditions;
    @BindView(R.id.tvAppVersion)
    TextView tvAppVersion;
    @BindView(R.id.tvShoppingList)
    TextView tvShoppingList;
    @BindView(R.id.tvRecipeLibrary)
    TextView tvRecipeLibrary;
    @BindView(R.id.tvScheduleClasses)
    TextView tvScheduleClasses;
    @BindView(R.id.tvBuyClasses)
    TextView tvBuyClasses;
    @BindView(R.id.tvmyCampaigns)
    TextView tvMyCampaigns;
    @BindView(R.id.tvmyChallenges)
    TextView tvMyChallenges;
    @BindView(R.id.tvForum)
    TextView tvForum;

    @BindView(R.id.tvLogout)
    TextView tvLogout;
    //label

    @BindView(R.id.tvAppVersionValue)
    TextView tvAppVersionValue;
    @BindView(R.id.imgMyMessagingArrow)
    ImageView imgMyMessagingArrow;
    @BindView(R.id.rlMyMessaging)
    RelativeLayout rlMyMessaging;
    @BindView(R.id.viewMyMessaging)
    View viewMyMessaging;

    private CommonApi commonApi;
    Bundle bundleDataToSend;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        commonApi = new CommonApi(getActivity());
        View layout = inflater.inflate(R.layout.more_fragment, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this, layout);
        rlBack.setVisibility(View.INVISIBLE);
        unbinder = ButterKnife.bind(this, layout);

        headerHeading.setText("More");

        tvAppVersionValue.setText(BuildConfig.VERSION_NAME);

        setHasOptionsMenu(true);
        bundleDataToSend = new Bundle();
        return layout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.rlChallengeIntroduction, R.id.rlFAQ, R.id.rlHomeWorkOuts,
            R.id.rlCardioPlans, R.id.rlEditProfile, R.id.rlMyMessaging, R.id.rlPrivacyPolicy,
            R.id.rlTermsAndConditions, R.id.rlShoppingList, R.id.rlRecipeLibrary,
            R.id.rlScheduleClasses, R.id.rlBuyClasses, R.id.rlLogOut,R.id.rlmyCampaigns,
            R.id.rlmyChallenges,R.id.rlUpcomingClngAndCmpn, R.id.rlActiveClngAndCmpn, R.id.rlForum})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rlChallengeIntroduction:
                bundleDataToSend.putString("urlKey", Urls.CHALLENGE_INTRODUCTION);
                bundleDataToSend.putString("titleForHeader", "Challenge Introduction");
                commonApi.openNewScreen(WebViewActivity.class, bundleDataToSend);
                break;
            case R.id.rlFAQ:
                commonApi.openNewScreen(FAQActivity.class, null);
                break;
            case R.id.rlHomeWorkOuts:
                bundleDataToSend.putString("urlKeyForVideoWebView", Urls.HOME_WORKOUTS);
                bundleDataToSend.putString("titleForHeader", "Home Workout");
                commonApi.openNewScreen(HomeWorkoutActivity.class, bundleDataToSend);
                break;
            case R.id.rlCardioPlans:
                bundleDataToSend.putString("urlKeyForVideoWebView", Urls.CARDIO_PLAN);
                bundleDataToSend.putString("titleForHeader", "Cardio Plans");
                commonApi.openNewScreen(HomeWorkoutActivity.class, bundleDataToSend);
                break;
            case R.id.rlEditProfile:
                commonApi.openNewScreen(EditProfileActivity.class, null);
                break;
            case R.id.rlMyMessaging:
                commonApi.openNewScreen(MyMessagingActivity.class, null);
                break;
            case R.id.rlPrivacyPolicy:
                bundleDataToSend.putString("urlKey", Urls.PRIVACY_POLICY);
                bundleDataToSend.putString("titleForHeader", "Privacy Policy");
                commonApi.openNewScreen(WebViewActivity.class, bundleDataToSend);
                break;
            case R.id.rlTermsAndConditions:
                bundleDataToSend.putString("urlKey", Urls.TERMS_AND_CONDITION);
                bundleDataToSend.putString("titleForHeader", "Terms And Conditions");
                commonApi.openNewScreen(WebViewActivity.class, bundleDataToSend);
                break;
            case R.id.rlShoppingList:
                commonApi.openNewScreen(ShoppingListActivity.class, null);
                break;
            case R.id.rlRecipeLibrary:
                commonApi.openNewScreen(RecipeLibraryActivity.class, null);
                break;
            case R.id.rlScheduleClasses:
                methodToOpenClass(Urls.SCHEDULE_CLASS);
                break;
            case R.id.rlBuyClasses:
                methodToOpenClass(Urls.BUY_CLASS);
                break;
            case R.id.rlLogOut:
                logoutMethod();
                break;
            case R.id.rlUpcomingClngAndCmpn:
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.addToBackStack(null);
                ft.replace(R.id.rootLayout, new UpcomingChallengesAndCampaignsFragment(), "NewFragmentTag");
                ft.commit();
                break;
            case R.id.rlmyChallenges:
                final FragmentTransaction ft1 = getFragmentManager().beginTransaction();
                ft1.addToBackStack(null);
                ft1.replace(R.id.rootLayout, new ChallengeListFragment(), "NewFragmentTag");
                ft1.commit();
                break;
            case R.id.rlmyCampaigns:
                final FragmentTransaction ft2 = getFragmentManager().beginTransaction();
                ft2.addToBackStack(null);
                ft2.replace(R.id.rootLayout, new CampaignListFragment(), "NewFragmentTag");
                ft2.commit();
                break;
            case R.id.rlActiveClngAndCmpn:
                final FragmentTransaction ft3 = getFragmentManager().beginTransaction();
                ft3.addToBackStack(null);
                ft3.replace(R.id.rootLayout, new ActiveChallengeAndCampaignFragment(), "NewFragmentTag");
                ft3.commit();
                break;
            case R.id.rlForum:
               // commonApi.openNewScreen(CommunityWebviewActivity.class, null);
                Constant.FROM_FRAGMENT = 1;
                bundleDataToSend.putString("urlKey", Urls.COMMUNITY + loginCredentials.getUserEmail());
                bundleDataToSend.putString("titleForHeader", "Forum");
                commonApi.openNewScreen(WebViewActivity.class, bundleDataToSend);
                break;
            }
        }


    private void logoutMethod() {
        commonApi.showProgressDialog("Please wait..");
        JSONObject obj = new JSONObject();
        try {
            obj.put("user_id", loginCredentials.getUserId());
            obj.put("device_id", Constant.FirebaseDeviceId);
            Log.d("TAG", "JSonObject" + obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        new GetServiceCall(Urls.USER_LOGOUT, GetServiceCall.TYPE_JSONOBJECT_POST, obj) {
            @Override
            public void response(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("sStatus").equalsIgnoreCase("1")) {
                        Toast.makeText(getActivity(), jsonObject.optString("sMessage"), Toast.LENGTH_SHORT).show();
                        commonApi.openNewScreen(LoginActivity.class, null);
                        loginCredentials.clearUserData(getActivity());
                        commonApi.finishActivity(getActivity());
                    } else {
                        Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
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

    //Open link for schedule class and buy class
    private void methodToOpenClass(String URL) {
        commonApi.showProgressDialog("");
        new GetServiceCall(URL, GetServiceCall.TYPE_JSONOBJECT) {
            @Override
            public void response(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String googlePlayLink = jsonObject.optString("google_play");
                    String forTrim = googlePlayLink.substring(googlePlayLink.indexOf("id") + 3, googlePlayLink.length()).trim();
                    String packageName = forTrim.replace("&hl=en", " ").trim();

                    boolean isAppInstalled = appInstalledOrNot(packageName);
                    if (isAppInstalled) {
                        Intent LaunchIntent = getActivity().getPackageManager().getLaunchIntentForPackage(packageName);
                        startActivity(LaunchIntent);
                    } else {
                        //final String appPackageName = "com.f45training.challenge"; // package name of the app
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
                        } catch (ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
                        }
                    }


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

    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getActivity().getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }
    //Open link for schedule class and buy class

}

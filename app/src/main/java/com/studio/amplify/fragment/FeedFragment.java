package com.studio.amplify.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.studio.amplify.R;
import com.studio.amplify.activity.CategoryListActivity;
import com.studio.amplify.adapter.ChallengeAndCampaignAdapter;
import com.studio.amplify.adapter.ChallengeCampaignListAdapter;
import com.studio.amplify.adapter.ClassListAdapter;
import com.studio.amplify.adapter.HomeWorkoutListAdapter;
import com.studio.amplify.adapter.MealsListAdapter;
import com.studio.amplify.interfaces.ParticipateClick;
import com.studio.amplify.model.ChallengeCampaignListItem;
import com.studio.amplify.model.MealListItem;
import com.studio.amplify.model.GoalItem;
import com.studio.amplify.model.UpcomingChallengeAndCampaignsItem;
import com.studio.amplify.pojo.feed_list.FeedList;
import com.studio.amplify.util.BaseFragment;
import com.studio.amplify.util.CommonApi;
import com.studio.amplify.util.Constant;
import com.studio.amplify.util.LoadingInterface;
import com.studio.amplify.util.OnClassItemClick;
import com.studio.amplify.util.Urls;
import com.studio.amplify.volleyparser.GetServiceCall;
import com.studio.amplify.volleyparser.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FeedFragment extends BaseFragment implements LoadingInterface, OnClassItemClick {

    //banner
    @BindView(R.id.ivBannerImage)
    ImageView ivBannerImage;
    @BindView(R.id.participateButton)
    Button participateButton;
    @BindView(R.id.ivBackgroundImage)
    ImageView ivBackgroundImage;
    @BindView(R.id.textViewChallengeDate)
    TextView textViewChallengeDate;
    @BindView(R.id.nextChallengeText)
    TextView nextChallengeText;

    @BindView(R.id.weeklyProgressBar)
    ImageView weeklyProgressBar;
    @BindView(R.id.ivCalendar)
    ImageView ivCalendar;
    @BindView(R.id.imageHomeWorkout2)
    ImageView imageHomeWorkout2;
    @BindView(R.id.imageCardioPlans)
    ImageView imageCardioPlans;
    @BindView(R.id.imageForHealthTips)
    ImageView imageForHealthTips;
    /*@BindView(R.id.imagePartnerOffers)
    ImageView imagePartnerOffers;*/
    //banner

    @BindView(R.id.rlRemainingLayout)
    RelativeLayout rlRemainingLayout;
    @BindView(R.id.rlNoActiveLayout)
    RelativeLayout rlNoActiveLayout;

    //check remaining days
    @BindView(R.id.textViewDateForRemainingDays)
    TextView textViewDateForRemainingDays;
    @BindView(R.id.textViewDaysCountForRemainingDays)
    TextView textViewDaysCountForRemainingDays;
    //check remaining days

    //goal layout
    @BindView(R.id.rootLayoutForGoalSetting)
    LinearLayout rootLayoutForGoalSetting;
    @BindView(R.id.textViewValueOfStartWeight)
    TextView textViewValueOfStartWeight;
    @BindView(R.id.textViewValueOfCurrentWeight)
    TextView textViewValueOfCurrentWeight;
    @BindView(R.id.textViewValueOfGoalWeight)
    TextView textViewValueOfGoalWeight;

    @BindView(R.id.textViewValueOfCurrentBodyFat)
    TextView textViewValueOfCurrentBodyFat;
    @BindView(R.id.textViewValueOfStartBodyFat)
    TextView textViewValueOfStartBodyFat;
    @BindView(R.id.textViewValueOfGoalBodyFat)
    TextView textViewValueOfGoalBodyFat;

    @BindView(R.id.textViewStartWeight)
    TextView textViewStartWeight;
    @BindView(R.id.textViewCurrentWeight)
    TextView textViewCurrentWeight;
    @BindView(R.id.textViewGoalWeight)
    TextView textViewGoalWeight;
    @BindView(R.id.textViewStartBodyFat)
    TextView textViewStartBodyFat;
    @BindView(R.id.textViewCurrentBodyFat)
    TextView textViewCurrentBodyFat;
    @BindView(R.id.textViewGoalBodyFat)
    TextView textViewGoalBodyFat;
    @BindView(R.id.textViewRemaining)
    TextView textViewRemaining;
    @BindView(R.id.textView_no_active)
    TextView textView_no_active;
    //goal layout

    //Healthy tips
    @BindView(R.id.rvForHealthTipsList)
    RecyclerView rvForHealthTipsList;
    @BindView(R.id.rootForHealthyTips)
    RelativeLayout rootForHealthyTips;
    ArrayList<MealListItem> healthTipsListItemArrayList;
    MealsListAdapter mealsListAdapter;
    @BindView(R.id.titleHealthTips)
    TextView titleHealthTips;
    //Healthy tips

    //home workout
    @BindView(R.id.rootForHomeWorkout)
    RelativeLayout rootForHomeWorkout;
    @BindView(R.id.rvForHomeWorkout)
    RecyclerView rvForHomeWorkout;
    ArrayList<MealListItem> homeWorkoutListItemArrayList;
    HomeWorkoutListAdapter homeWorkoutListAdapter;
    /*@BindView(R.id.titleHomeWorkout)
    TextView titleHomeWorkout;*/
    //home workout

    //cardio Plan
    @BindView(R.id.rootForCardioPlans)
    RelativeLayout rootForCardioPlans;
    @BindView(R.id.rvForCardioPlan)
    RecyclerView rvForCardioPlan;
    ArrayList<MealListItem> cardioPlanArrayList;
    @BindView(R.id.titleCardioPlan)
    TextView titleCardioPlan;
    /*@BindView(R.id.titlePartnerOffers)
    TextView titlePartnerOffers;*/


    //goals text layout
    @BindView(R.id.textone_first)
    TextView textone_first;
    @BindView(R.id.textone_second)
    TextView textone_second;
    @BindView(R.id.textone_third)
    TextView textone_third;
    @BindView(R.id.textwo_second)
    TextView textwo_second;
    @BindView(R.id.texttwo_third)
    TextView texttwo_third;
    @BindView(R.id.textthree_third)
    TextView textthree_third;
    @BindView(R.id.llFeedFirstLayout)
    LinearLayout llFeedFirstLayout;
    @BindView(R.id.llFeedSecondLayout)
    LinearLayout llFeedSecondLayout;
    @BindView(R.id.llFeedThirdLayout)
    LinearLayout llFeedThirdLayout;
    @BindView(R.id.llForTextFromSpinner)
    LinearLayout llForTextFromSpinner;

    //cardio plan

    //Partner Offers
    @BindView(R.id.rootForPartnerOffers)
    RelativeLayout rootForPartnerOffers;
    /*@BindView(R.id.rvForPartnerOffers)
    RecyclerView rvForPartnerOffers;*/
    ArrayList<MealListItem> partnerOffersArrayList;
    @BindView(R.id.titlePartnerOffers)
    TextView titlePartnerOffers;
    @BindView(R.id.imagePartnerOffers)
    ImageView imagePartnerOffers;
    //Partner Offers

    @BindView(R.id.rootLayoutForViews)
    LinearLayout rootLayoutForViews;
    @BindView(R.id.ScrollView)
    ScrollView scrollView;

    @BindView(R.id.spChoice)
    TextView spChoice;


    private Gson gson = new Gson();
    FeedList feedList;
    Unbinder unbinder;
    String start_body_weight, current_body_weight, goal_weight, start_body_fat,
            current_body_fat, goal_body_fat;
    String[] goalsArrayNew;

    private CommonApi commonApi;

    int ch_ca_id, userId;
    String ch_ca_type, group_name, user_group_name;
    //   Bundle b;
    private boolean isAlreadyOpened = false;
    int participate, group_id, user_group_id;

    LoadingInterface loadingInterface;
    String startDate, endDate, currentDate;
    String str_goals ="";
    String feedResp_str;


    ArrayList<UpcomingChallengeAndCampaignsItem> upcomingChallengeAndCampaignsItemsList = new ArrayList<>();
    ChallengeAndCampaignAdapter challengeAndCampaignAdapter;
    UpcomingChallengeAndCampaignsItem upcomingChallengeAndCampaignsItem;
    RecyclerView rvForUpcomingClngAndCmpn;
    String upcomingChallengeName, upcomingChallengeStartDate;
    int checkedItemsCount=0,goalsCount=0;
    boolean[] checkedItems;
    ArrayList<GoalItem> goalItemArrayList =new ArrayList<>();
    private ParticipateClick participateClick;
    //List<String> goalsArrayNew=new ArrayList<>();

    ArrayList<ChallengeCampaignListItem> challengeListItemArrayList
            = new ArrayList<>();

    ChallengeCampaignListItem challengeListItem;
    String selected_class="";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.feed_fragment, container, false);
        ButterKnife.bind(this, layout);
        unbinder = ButterKnife.bind(this, layout);
        commonApi = new CommonApi(getActivity());
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        healthTipsListItemArrayList = new ArrayList<>();
        homeWorkoutListItemArrayList = new ArrayList<>();
        cardioPlanArrayList = new ArrayList<>();
      //  partnerOffersArrayList = new ArrayList<>();
        rvForHealthTipsList.setNestedScrollingEnabled(false);
        rvForHomeWorkout.setNestedScrollingEnabled(false);
        rvForCardioPlan.setNestedScrollingEnabled(false);
      //  rvForPartnerOffers.setNestedScrollingEnabled(false);

        loadingInterface = (LoadingInterface) getActivity();
        loadingInterface.onRediredectToMainActivity();
        setGoalsValues();


        setHasOptionsMenu(true);
        Log.d("DIFFERENCE", "***days difference is " + getCountOfDays("2021-04-08", "2021-04-20"));

        // b = getArguments();
        if (loginCredentials.getCAMP_CHAL_TYPE().equals("")
                && loginCredentials.getCAMP_CHAL_ID() == 0) {
            getDataForDashboardDetails();

        } else {
            /*b = getArguments();
            if(b != null) {
                ch_ca_id = b.getInt("ch_ca_id");
                userId = b.getInt("user_id");
                ch_ca_type = b.getString("status");*/
            historyLoading();
        }
        //  }
      /*  if(b != null) {
            //if(!b.getString("From").equals("Splash")) {
                ch_ca_id = b.getInt("ch_ca_id");
                userId = b.getInt("user_id");
                ch_ca_type = b.getString("status");
                historyLoading();
            }
            else
            {
                historyLoading();
            }*/
        // }
        // else {
        // getDataForDashboardDetails();
        //}
        participateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmDialog();
               // callClassList();
               // participateNow(ch_ca_type, ch_ca_id, group_id);
            }
        });
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
       spChoice.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           goalsSelectNew(goalsArrayNew);
     }
   });
       rootForCardioPlans.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(getActivity(), CategoryListActivity.class);
               Bundle args = new Bundle();
               args.putString("Response",feedResp_str);
               intent.putExtra("BUNDLE",args);
               intent.putExtra("FLAG_NO","1");
               startActivity(intent);
           }
       });

        rootForHomeWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CategoryListActivity.class);
                Bundle args = new Bundle();
                args.putString("Response",feedResp_str);
                intent.putExtra("BUNDLE",args);
                intent.putExtra("FLAG_NO","3");
                startActivity(intent);
            }
        });

        rootForHealthyTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CategoryListActivity.class);
                Bundle args = new Bundle();
                args.putString("Response",feedResp_str);
                intent.putExtra("BUNDLE",args);
                intent.putExtra("FLAG_NO","2");
                startActivity(intent);
            }
        });

        rootForPartnerOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CategoryListActivity.class);
                Bundle args = new Bundle();
                args.putString("Response",feedResp_str);
                intent.putExtra("BUNDLE",args);
                intent.putExtra("FLAG_NO","4");
                startActivity(intent);
            }
        });

        return layout;

    }

    private void historyLoading() {
        getUpcomingChallenges();
        commonApi.showProgressDialog("Please wait...");

        String URL = Urls.HISTORY + loginCredentials.getUserId() + "/" + loginCredentials.getCAMP_CHAL_ID() + "/" + loginCredentials.getCAMP_CHAL_TYPE();
        System.out.println("historyResp: " + URL);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                commonApi.dismissProgressDialog();
                try {
                    JSONObject jsonObject1 = new JSONObject(response);
                    feedList = gson.fromJson(response, FeedList.class);
                    feedResp_str=response;
                    String dashBoardBanner = jsonObject1.optString("dashboard_banner");
                    Glide.with(getActivity()).load(dashBoardBanner).into(ivBannerImage);
                    Glide.with(getActivity()).load(jsonObject1.optString("home_workouts_banner")).into(imageHomeWorkout2);
                    Glide.with(getActivity()).load(jsonObject1.optString("cardio_plans_banner")).into(imageCardioPlans);
                    Glide.with(getActivity()).load(jsonObject1.optString("health_tips_banner")).into(imageForHealthTips);
                    Glide.with(getActivity()).load(jsonObject1.optString("partner_offers_banner")).into(imagePartnerOffers);
                    String nextChallengeDate = jsonObject1.optString("next_challange");
                    // String nextChallengeDate="2021-05-03";
                    String newdateString;
                    SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat newDateFormat = new SimpleDateFormat("dd MMMM");
                    newdateString = parseDate(nextChallengeDate, inputDateFormat, newDateFormat);
                    textViewChallengeDate.setText(newdateString);
                 /*   Date date=null;
                    SimpleDateFormat newDateFormat = new SimpleDateFormat("dd MMM" *//*yyyy*//**//*, hh:mm a*//*, Locale.US); // 31 Aug 2018, 01:55 PM
                    try {
                        date=newDateFormat.parse(nextChallengeDate);
                        newdateString = newDateFormat.format(date);
                        textViewChallengeDate.setText(newdateString);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/
                    //textViewChallengeDate.setText(nextChallengeDate);

                     // changed now //
                    /*String banner_text = jsonObject1.optString("banner_text");
                    nextChallengeText.setText(banner_text);*/
                    // changed now //
                    String banner_text = jsonObject1.optString("name");
                    nextChallengeText.setText(banner_text);

                    String unitToSet = jsonObject1.optString("unit");

                    Glide.with(getActivity()).load(jsonObject1.optString("image_under_date")).into(ivBackgroundImage);

                    ch_ca_id = jsonObject1.optInt("camchall_id");
                    ch_ca_type = jsonObject1.optString("camchall_status");
                    participate = jsonObject1.optInt("participate");

                    loginCredentials.setUserUnit(jsonObject1.optString("unit"));
                    loginCredentials.setCurrentDateFromFeed(jsonObject1.optString("current_date"));
                    loginCredentials.setChallengeStartDate(jsonObject1.optString("challange_start_date"));// new

                    loginCredentials.setCAMP_CHAL_DETAILS(ch_ca_id, ch_ca_type);

                    //  startDate = jsonObject1.optString("challange_start_date");
                    startDate = jsonObject1.optString("Sdate");
                    endDate = jsonObject1.optString("edate");
                    currentDate = jsonObject1.optString("current_date");

                    textViewDateForRemainingDays.setText(jsonObject1.optString("name"));
                    textViewDaysCountForRemainingDays.setText(jsonObject1.optString("days_remaining"));
                    loginCredentials.setChallengeRemainingDays(jsonObject1.optString("days_remaining"));

                    String weekNumber = jsonObject1.optString("week_no");
                    if (weekNumber.equalsIgnoreCase("1")) {
                        weeklyProgressBar.setImageResource(R.drawable.week1_bar);
                    } else if (weekNumber.equalsIgnoreCase("2")) {
                        weeklyProgressBar.setImageResource(R.drawable.week2_bar);
                    } else if (weekNumber.equalsIgnoreCase("3")) {
                        weeklyProgressBar.setImageResource(R.drawable.week3_bar);
                    } else if (weekNumber.equalsIgnoreCase("4")) {
                        weeklyProgressBar.setImageResource(R.drawable.week4_bar);
                    } else {
                        weeklyProgressBar.setImageResource(R.drawable.week5_bar);
                    }

                    if (participate == 1) {
                        if (!endDate.equals("") && isValidDatee(startDate, currentDate, endDate)) {
                            textViewDateForRemainingDays.setVisibility(View.VISIBLE);
                            textViewDaysCountForRemainingDays.setVisibility(View.VISIBLE);
                            textViewRemaining.setVisibility(View.VISIBLE);
                            ivCalendar.setVisibility(View.VISIBLE);
                            participateButton.setVisibility(View.GONE);
                           // llClassSpn.setVisibility(View.VISIBLE);
                           // getDataForClasses();
                        } else {
                            textViewDaysCountForRemainingDays.setVisibility(View.GONE);
                            participateButton.setVisibility(View.GONE);
                        }
                    } else if (participate == 0) {
                        if (!endDate.equals("")) {
                            //active challenge available
                            if (isValidDate(startDate, currentDate, endDate)) {
                                textViewDateForRemainingDays.setVisibility(View.GONE);
                                textViewDaysCountForRemainingDays.setVisibility(View.GONE);
                                textViewRemaining.setVisibility(View.GONE);
                                ivCalendar.setVisibility(View.GONE);
                                participateButton.setVisibility(View.VISIBLE);
                                participateButton.setText(textViewDateForRemainingDays.getText().toString());
                            }

                            // no active challenge available but upcoming challenge
                        } else if (endDate.equals("") && !upcomingChallengeStartDate.equals("")) {
                            ivCalendar.setVisibility(View.VISIBLE);
                            textViewDateForRemainingDays.setVisibility(View.VISIBLE);
                            textViewDateForRemainingDays.setText(upcomingChallengeName);
                            textViewDaysCountForRemainingDays.setVisibility(View.VISIBLE);
                            textViewRemaining.setVisibility(View.GONE);
                            textViewDaysCountForRemainingDays.setText("Starts in " + getCountOfDays(currentDate, upcomingChallengeStartDate) + "days");

                            // no active challenge no upcoming challenge
                        } else if (endDate.equals("") && upcomingChallengeStartDate.equals("")) {
                            ivCalendar.setVisibility(View.GONE);
                            textViewDateForRemainingDays.setVisibility(View.VISIBLE);
                            textViewDateForRemainingDays.setText(getResources().getString(R.string.no_active_text));
                            textViewDateForRemainingDays.setGravity(Gravity.CENTER);
                            textViewDaysCountForRemainingDays.setVisibility(View.GONE);
                            textViewRemaining.setVisibility(View.GONE);

                            // both active challenge no upcoming challenge availbale
                        } else if (!endDate.equals("") && !startDate.equals("")) {
                            textViewDateForRemainingDays.setVisibility(View.GONE);
                            textViewDaysCountForRemainingDays.setVisibility(View.GONE);
                            textViewRemaining.setVisibility(View.GONE);
                            ivCalendar.setVisibility(View.GONE);
                            participateButton.setVisibility(View.VISIBLE);
                            participateButton.setText(textViewDateForRemainingDays.getText().toString());
                        }
                    }

                    JSONObject jsonObjectMaintainPhase = jsonObject1.getJSONObject("maintenance_phase");
                    start_body_weight = jsonObjectMaintainPhase.optString("start_body_weight");
                    try {
                        textViewValueOfStartWeight.setText(commonApi.toConvertInDecimal(start_body_weight) + " " + unitToSet);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    current_body_weight = jsonObjectMaintainPhase.optString("current_body_weight");
                    try {
                        textViewValueOfCurrentWeight.setText(commonApi.toConvertInDecimal(current_body_weight) + " " + unitToSet);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    goal_weight = jsonObjectMaintainPhase.optString("goal_weight");
                    try {
                        textViewValueOfGoalWeight.setText(commonApi.toConvertInDecimal(goal_weight) + " " + unitToSet);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    start_body_fat = jsonObjectMaintainPhase.optString("start_body_fat");
                    try {
                        textViewValueOfStartBodyFat.setText(String.format("%s %%", commonApi.toConvertInDecimal(start_body_fat)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    current_body_fat = jsonObjectMaintainPhase.optString("current_body_fat");
                    try {
                        textViewValueOfCurrentBodyFat.setText(String.format("%s %%", commonApi.toConvertInDecimal(current_body_fat)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    goal_body_fat = jsonObjectMaintainPhase.optString("goal_body_fat");
                    try {
                        textViewValueOfGoalBodyFat.setText(String.format("%s %%", commonApi.toConvertInDecimal(goal_body_fat)));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (jsonObject1.has("healthy_tips")) {
                        rootForHealthyTips.setVisibility(View.VISIBLE);
                        JSONArray jsonArrayHealthTips = jsonObject1.getJSONArray("healthy_tips");
                        for (int j = 0; j < jsonArrayHealthTips.length(); j++) {
                            MealListItem mealListingItem = new MealListItem();
                            JSONObject jsonObjectHealthTipsDetails = jsonArrayHealthTips.getJSONObject(j);

                            mealListingItem.setTitle(jsonObjectHealthTipsDetails.optString("title"));
                            mealListingItem.setUrl(jsonObjectHealthTipsDetails.optString("url"));
                            mealListingItem.setImage(jsonObjectHealthTipsDetails.optString("image"));

                            healthTipsListItemArrayList.add(mealListingItem);
                        }

                        mealsListAdapter = new MealsListAdapter(healthTipsListItemArrayList, getActivity(), "0", "0", "Nutrition Tips");
                        rvForHealthTipsList.setAdapter(mealsListAdapter);
                    } else {
                        rootForHealthyTips.setVisibility(View.GONE);
                    }

                    if (jsonObject1.has("home_workouts")) {
                        rootForHomeWorkout.setVisibility(View.VISIBLE);
                        JSONArray jsonArray = jsonObject1.getJSONArray("home_workouts");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            MealListItem mealListingItem = new MealListItem();
                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);

                            mealListingItem.setTitle(jsonObject2.optString("title"));
                            mealListingItem.setImage(jsonObject2.optString("image"));
                            mealListingItem.setUrl(jsonObject2.optString("url"));
                            homeWorkoutListItemArrayList.add(mealListingItem);
                        }
                        mealsListAdapter = new MealsListAdapter(homeWorkoutListItemArrayList, getActivity(), "0", "1", "Home Workout");
                        rvForHomeWorkout.setAdapter(mealsListAdapter);
                    } else {
                        rootForHomeWorkout.setVisibility(View.GONE);
                    }

                    if (jsonObject1.has("cardio_plans")) {
                        rootForCardioPlans.setVisibility(View.VISIBLE);
                        JSONArray jsonArray = jsonObject1.getJSONArray("cardio_plans");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            MealListItem mealListingItem = new MealListItem();
                            JSONObject jsonObject3 = jsonArray.getJSONObject(i);

                            mealListingItem.setTitle(jsonObject3.optString("title"));
                            mealListingItem.setImage(jsonObject3.optString("image"));
                            mealListingItem.setUrl(jsonObject3.optString("url"));
                            cardioPlanArrayList.add(mealListingItem);
                        }
                        mealsListAdapter = new MealsListAdapter(cardioPlanArrayList, getActivity(), "0", "1", "Cardio Plans");
                        rvForCardioPlan.setAdapter(mealsListAdapter);
                    } else {
                        rootForCardioPlans.setVisibility(View.GONE);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                commonApi.dismissProgressDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                commonApi.dismissProgressDialog();
                error.printStackTrace();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(stringRequest);
    }

    public void getDataForDashboardDetails() {
        getUpcomingChallenges();
        commonApi.showProgressDialog("Please wait..");
        JSONObject object = new JSONObject();
        try {
            object.put("user_id", loginCredentials.getUserId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new GetServiceCall(Urls.GET_DATA_FOR_DASHBOARD, GetServiceCall.TYPE_JSONOBJECT_POST, object) {
            @Override
            public void response(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    System.out.println(" FeedUrl : " + Urls.GET_DATA_FOR_DASHBOARD);
                    System.out.println("FeedPage : " + jsonObject.toString());
                    feedList = gson.fromJson(response, FeedList.class);
                    feedResp_str=response;
                    String dashBoardBanner = jsonObject.optString("dashboard_banner");
                    Glide.with(getActivity()).load(dashBoardBanner).into(ivBannerImage);
                    Glide.with(getActivity()).load(jsonObject.optString("home_workouts_banner")).into(imageHomeWorkout2);
                    Glide.with(getActivity()).load(jsonObject.optString("cardio_plans_banner")).into(imageCardioPlans);
                    Glide.with(getActivity()).load(jsonObject.optString("health_tips_banner")).into(imageForHealthTips);
                    Glide.with(getActivity()).load(jsonObject.optString("partner_offers_banner")).into(imagePartnerOffers);

                    ch_ca_id = jsonObject.optInt("camchall_id");
                    ch_ca_type = jsonObject.optString("camchall_status");
                    participate = jsonObject.optInt("participate");

                    group_id = jsonObject.optInt("group_id");
                    group_name = jsonObject.optString("group_name");

                    user_group_id = jsonObject.optInt("use_group_id");
                    user_group_name = jsonObject.optString("user_group_name");
                    loginCredentials.setUserGroup(String.valueOf(user_group_id));
                    loginCredentials.setUserGroupName(user_group_name);

                    String nextChallengeDate = jsonObject.optString("next_challange");
                    //String nextChallengeDate="2021-05-03";
                    String banner_text = jsonObject.optString("name");
                    nextChallengeText.setText(banner_text);
                    // changed now //
                    /*String banner_text = jsonObject.optString("banner_text");
                    nextChallengeText.setText(banner_text);
                    if (nextChallengeDate != null) {
                        //String nextChallengeDate="2021-05-03";
                        String newdateString;
                        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd MMMM");
                        newdateString = parseDate(nextChallengeDate, inputDateFormat, newDateFormat);
                        textViewChallengeDate.setText(newdateString);

                    } else {
                        textViewChallengeDate.setVisibility(View.GONE);
                        //nextChallengeText.setVisibility(View.GONE);
                    }*/
                    // changed now //

                    String unitToSet = jsonObject.optString("unit");

                    Glide.with(getActivity()).load(jsonObject.optString("image_under_date")).into(ivBackgroundImage);

                  /*  String isLayoutDisableCheck = jsonObject.optString("is_disabled");
                    if (isLayoutDisableCheck.equalsIgnoreCase("1")) {
                        commonApi.enableViews(rootLayoutForGoalSetting, false);
                    } else {
                        commonApi.enableViews(rootLayoutForGoalSetting, true);
                    }
*/
                    //  startDate = jsonObject.optString("challange_start_date");

                    startDate = jsonObject.optString("Sdate");
                    endDate = jsonObject.optString("edate");

                    currentDate = jsonObject.optString("current_date");

                    loginCredentials.setCAMP_CHAL_DETAILS(ch_ca_id, ch_ca_type);

                    loginCredentials.setUserUnit(jsonObject.optString("unit"));
                    loginCredentials.setCurrentDateFromFeed(jsonObject.optString("current_date"));
                    loginCredentials.setChallengeStartDate(jsonObject.optString("challange_start_date"));// new


                    textViewDateForRemainingDays.setText(jsonObject.optString("name"));
                    textViewDaysCountForRemainingDays.setText(jsonObject.optString("days_remaining"));
                    loginCredentials.setChallengeRemainingDays(jsonObject.optString("days_remaining")); // new

                    if (participate == 1) {
                        if (!endDate.equals("") && isValidDate(startDate, currentDate, endDate)) {
                            textViewDateForRemainingDays.setVisibility(View.VISIBLE);
                            textViewDaysCountForRemainingDays.setVisibility(View.VISIBLE);
                            textViewRemaining.setVisibility(View.VISIBLE);
                            ivCalendar.setVisibility(View.VISIBLE);
                            participateButton.setVisibility(View.GONE);
                            //participateButton.setText(textViewDateForRemainingDays.getText().toString());

                        }
                    } else if (participate == 0) {
                        if (!endDate.equals("")) {
                            //active challenge available
                            if (isValidDate(startDate, currentDate, endDate)) {
                                textViewDateForRemainingDays.setVisibility(View.GONE);
                                textViewDaysCountForRemainingDays.setVisibility(View.GONE);
                                textViewRemaining.setVisibility(View.GONE);
                                ivCalendar.setVisibility(View.GONE);
                                participateButton.setVisibility(View.VISIBLE);
                                participateButton.setText(textViewDateForRemainingDays.getText().toString());
                            }

                            // no active challenge available but upcoming challenge
                        } else if (endDate.equals("") && !upcomingChallengeStartDate.equals("")) {
                            ivCalendar.setVisibility(View.VISIBLE);
                            textViewDateForRemainingDays.setVisibility(View.VISIBLE);
                            textViewDateForRemainingDays.setText(upcomingChallengeName);
                            textViewDaysCountForRemainingDays.setVisibility(View.VISIBLE);
                            textViewRemaining.setVisibility(View.GONE);
                            textViewDaysCountForRemainingDays.setText("Starts in " + getCountOfDays(currentDate, upcomingChallengeStartDate) + "days");

                            // no active challenge no upcoming challenge
                        } else if (endDate.equals("") && upcomingChallengeStartDate.equals("")) {
                            ivCalendar.setVisibility(View.GONE);
                            textViewDateForRemainingDays.setVisibility(View.VISIBLE);
                            textViewDateForRemainingDays.setText(getResources().getString(R.string.no_active_text));
                            textViewDateForRemainingDays.setGravity(Gravity.CENTER);
                            textViewDaysCountForRemainingDays.setVisibility(View.GONE);
                            textViewRemaining.setVisibility(View.GONE);

                            // both active challenge no upcoming challenge availbale
                        } else if (!endDate.equals("") && !startDate.equals("")) {
                            textViewDateForRemainingDays.setVisibility(View.GONE);
                            textViewDaysCountForRemainingDays.setVisibility(View.GONE);
                            textViewRemaining.setVisibility(View.GONE);
                            ivCalendar.setVisibility(View.GONE);
                            participateButton.setVisibility(View.VISIBLE);
                            participateButton.setText(textViewDateForRemainingDays.getText().toString());
                        }
                    }


                    String weekNumber = jsonObject.optString("week_no");
                    if (weekNumber.equalsIgnoreCase("1")) {
                        weeklyProgressBar.setImageResource(R.drawable.week1_bar);
                    } else if (weekNumber.equalsIgnoreCase("2")) {
                        weeklyProgressBar.setImageResource(R.drawable.week2_bar);
                    } else if (weekNumber.equalsIgnoreCase("3")) {
                        weeklyProgressBar.setImageResource(R.drawable.week3_bar);
                    } else if (weekNumber.equalsIgnoreCase("4")) {
                        weeklyProgressBar.setImageResource(R.drawable.week4_bar);
                    } else {
                        weeklyProgressBar.setImageResource(R.drawable.week5_bar);
                    }

                    participate = jsonObject.optInt("participate");
                    JSONObject jsonObjectMaintainPhase = jsonObject.optJSONObject("maintenance_phase");
                    start_body_weight = jsonObjectMaintainPhase.optString("start_body_weight");
                    try {
                        textViewValueOfStartWeight.setText(commonApi.toConvertInDecimal(start_body_weight) + " " + unitToSet);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    current_body_weight = jsonObjectMaintainPhase.optString("current_body_weight");
                    try {
                        textViewValueOfCurrentWeight.setText(commonApi.toConvertInDecimal(current_body_weight) + " " + unitToSet);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    goal_weight = jsonObjectMaintainPhase.optString("goal_weight");
                    try {
                        textViewValueOfGoalWeight.setText(commonApi.toConvertInDecimal(goal_weight) + " " + unitToSet);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    start_body_fat = jsonObjectMaintainPhase.optString("start_body_fat");
                    try {
                        textViewValueOfStartBodyFat.setText(String.format("%s %%", commonApi.toConvertInDecimal(start_body_fat)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    current_body_fat = jsonObjectMaintainPhase.optString("current_body_fat");
                    try {
                        textViewValueOfCurrentBodyFat.setText(String.format("%s %%", commonApi.toConvertInDecimal(current_body_fat)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    goal_body_fat = jsonObjectMaintainPhase.optString("goal_body_fat");
                    try {
                        textViewValueOfGoalBodyFat.setText(String.format("%s %%", commonApi.toConvertInDecimal(goal_body_fat)));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (jsonObject.has("healthy_tips")) {
                        rootForHealthyTips.setVisibility(View.VISIBLE);
                        JSONArray jsonArrayHealthTips = jsonObject.getJSONArray("healthy_tips");
                        for (int j = 0; j < jsonArrayHealthTips.length(); j++) {
                            MealListItem mealListingItem = new MealListItem();
                            JSONObject jsonObjectHealthTipsDetails = jsonArrayHealthTips.getJSONObject(j);

                            mealListingItem.setTitle(jsonObjectHealthTipsDetails.optString("title"));
                            mealListingItem.setUrl(jsonObjectHealthTipsDetails.optString("url"));
                            mealListingItem.setImage(jsonObjectHealthTipsDetails.optString("image"));

                            healthTipsListItemArrayList.add(mealListingItem);
                        }

                        mealsListAdapter = new MealsListAdapter(healthTipsListItemArrayList, getActivity(), "0", "0", "Nutrition Tips");
                        rvForHealthTipsList.setAdapter(mealsListAdapter);
                    } else {
                        rootForHealthyTips.setVisibility(View.GONE);
                    }

                    if (jsonObject.has("home_workouts")) {
                        rootForHomeWorkout.setVisibility(View.VISIBLE);
                        JSONArray jsonArray = jsonObject.getJSONArray("home_workouts");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            MealListItem mealListingItem = new MealListItem();
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            mealListingItem.setTitle(jsonObject1.optString("title"));
                            mealListingItem.setImage(jsonObject1.optString("image"));
                            mealListingItem.setUrl(jsonObject1.optString("url"));
                            homeWorkoutListItemArrayList.add(mealListingItem);
                        }
                        mealsListAdapter = new MealsListAdapter(homeWorkoutListItemArrayList, getActivity(), "0", "1", "Home Workout");
                        rvForHomeWorkout.setAdapter(mealsListAdapter);
                    } else {
                        rootForHomeWorkout.setVisibility(View.GONE);
                    }

                    if (jsonObject.has("cardio_plans")) {
                        rootForCardioPlans.setVisibility(View.VISIBLE);
                        JSONArray jsonArray = jsonObject.getJSONArray("cardio_plans");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            MealListItem mealListingItem = new MealListItem();
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            mealListingItem.setTitle(jsonObject1.optString("title"));
                            mealListingItem.setImage(jsonObject1.optString("image"));
                            mealListingItem.setUrl(jsonObject1.optString("url"));
                            cardioPlanArrayList.add(mealListingItem);
                        }
                        mealsListAdapter = new MealsListAdapter(cardioPlanArrayList, getActivity(), "0", "1", "Cardio Plans");
                        rvForCardioPlan.setAdapter(mealsListAdapter);
                    } else {
                        rootForCardioPlans.setVisibility(View.GONE);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.llClickOfStartWeight, R.id.llClickOfCurrentWeight, R.id.llClickOfGoalWeight,
            R.id.llClickOfStartBodyFat, R.id.llClickOfCurrentBodyFat, R.id.llClickOfGoalBodyFat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llClickOfStartWeight:
                if (!isValidDate(startDate, currentDate, endDate)) {
                    Toast.makeText(getActivity(), R.string.expire_text, Toast.LENGTH_LONG).show();
                } else if (participate == 1) {
                    openDialogForValue(start_body_weight, "Start Weight", "Enter your start body weight", "start-weight");
                } else {
                    showParticipateDialog(start_body_weight, "Start Weight", "Enter your start body weight", "start-weight");
                }

                break;

            case R.id.llClickOfCurrentWeight:
                if (!isValidDate(startDate, currentDate, endDate)) {
                    Toast.makeText(getActivity(), R.string.expire_text, Toast.LENGTH_LONG).show();
                } else if (participate == 1) {
                    openDialogForValue(current_body_weight, "Current Weight", "Enter your current body weight", "current-weight");
                } else {
                    showParticipateDialog(current_body_weight, "Current Weight", "Enter your current body weight", "current-weight");
                }

                break;

            case R.id.llClickOfGoalWeight:
                if (!isValidDate(startDate, currentDate, endDate)) {
                    Toast.makeText(getActivity(), R.string.expire_text, Toast.LENGTH_LONG).show();
                } else if (participate == 1) {
                    openDialogForValue(goal_weight, "Goal Weight", "Enter your goal body weight", "goal-weight");
                } else {
                    showParticipateDialog(goal_weight, "Goal Weight", "Enter your goal body weight", "goal-weight");
                }

                break;

            case R.id.llClickOfStartBodyFat:
                if (!isValidDate(startDate, currentDate, endDate)) {
                    Toast.makeText(getActivity(), R.string.expire_text, Toast.LENGTH_LONG).show();
                } else if (participate == 1) {
                    openDialogForValue(start_body_fat, "Start Fat", "Enter your start body fat", "start-body-fat");
                } else {
                    showParticipateDialog(start_body_fat, "Start Fat", "Enter your start body fat", "start-body-fat");
                }

                break;

            case R.id.llClickOfCurrentBodyFat:
                if (!isValidDate(startDate, currentDate, endDate)) {
                    Toast.makeText(getActivity(), R.string.expire_text, Toast.LENGTH_LONG).show();
                } else if (participate == 1) {
                    openDialogForValue(current_body_fat, "Current Fat", "Enter your current body fat", "current-body-fat");
                } else {
                    showParticipateDialog(current_body_fat, "Current Fat", "Enter your current body fat", "current-body-fat");
                }

                break;

            case R.id.llClickOfGoalBodyFat:
                if (!isValidDate(startDate, currentDate, endDate)) {
                    Toast.makeText(getActivity(), R.string.expire_text, Toast.LENGTH_LONG).show();
                } else if (participate == 1) {
                    openDialogForValue(goal_body_fat, "Goal Fat", "Enter your goal body fat", "goal-body-fat");
                } else {
                    showParticipateDialog(goal_body_fat, "Goal Fat", "Enter your goal body fat", "goal-body-fat");
                }

                break;
            default:
        }
    }

    private void openDialogForValue(String value, String titleText, String subTitleText, final String urlKeyToSend) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.goal_setting_dialog_for_feed);
        dialog.setCancelable(false);
        TextView alertTitle = dialog.findViewById(R.id.alertTitle);
        alertTitle.setText(titleText);
        TextView alertMessage = dialog.findViewById(R.id.alertMessage);
        alertMessage.setText(subTitleText);
        final EditText etValue = dialog.findViewById(R.id.etValue);
        etValue.setText(value);
        etValue.setHint(R.string.goal_setting_value);

        etValue.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // participate = 1;
                    //commonApi.closeKeyBoard();
                    if (loginCredentials.getCAMP_CHAL_ID() == 0 && loginCredentials.getCAMP_CHAL_TYPE().equals("")) {
                        participate = 1;
                        sendValueToWebservice(urlKeyToSend, etValue.getText().toString().trim(), ch_ca_id, ch_ca_type, group_id);
                        commonApi.dismissProgressDialog();
                        getDataForDashboardDetails();
                    } else {
                        participate = 1;
                        sendValueToWebservice(urlKeyToSend, etValue.getText().toString().trim(), loginCredentials.getCAMP_CHAL_ID(), loginCredentials.getCAMP_CHAL_TYPE(), Integer.parseInt(loginCredentials.getUserGroup()));
                        historyLoading();
                    }

                    //   sendValueToWebservice(urlKeyToSend, etValue.getText().toString().trim(), ch_ca_id, ch_ca_type);
                    dialog.dismiss();
                    return true;
                }
                return false;
            }
        });
        TextView alertCancel = dialog.findViewById(R.id.alertCancel);
        alertCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        TextView alertOk = dialog.findViewById(R.id.alertOk);
        alertOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  participate = 1;
                if (loginCredentials.getCAMP_CHAL_ID() == 0 && loginCredentials.getCAMP_CHAL_TYPE().equals("")) {
                    sendValueToWebservice(urlKeyToSend, etValue.getText().toString().trim(), ch_ca_id, ch_ca_type, group_id);
                    //commonApi.dismissProgressDialog();
                    getDataForDashboardDetails();

                } else {
                    participate = 1;
                    sendValueToWebservice(urlKeyToSend, etValue.getText().toString().trim(), loginCredentials.getCAMP_CHAL_ID(), loginCredentials.getCAMP_CHAL_TYPE(), Integer.parseInt(loginCredentials.getUserGroup()));
                    commonApi.dismissProgressDialog();
                    historyLoading();
                }
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void sendValueToWebservice(String urlKey, String weightAndFatValue, int ch_ca_id, String ch_ca_type, int group_id) {
        System.out.println("***sendValueToWebservice():" + "called");
        JSONObject object = new JSONObject();
        try {
            object.put("user_id", loginCredentials.getUserId());
            object.put(urlKey, weightAndFatValue);
            object.put("camchall_id", ch_ca_id);
            object.put("camchall_status", ch_ca_type);
            object.put("group_id", group_id);
            System.out.println("*** add weight params:" + object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("***goal setting url:", Urls.GOAL_SETTING + urlKey + "/save");

        new GetServiceCall(Urls.GOAL_SETTING + urlKey + "/save", GetServiceCall.TYPE_JSONOBJECT_POST, object) {
            @Override
            public void response(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    System.out.println("***goal setting resp:" + response.toString());
                    if (jsonObject.optString("sStatus").equalsIgnoreCase("1")) {
                        participate = 1;
                        commonApi.dismissProgressDialog();
                        historyLoading();


                    } else {
                        participate = 1;
                        getDataForDashboardDetails();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(VolleyError error, String errorMsg) {
            }
        }.call();
    }

    public void showParticipateDialog(final String parameterType, final String titleText, final String subTitleText, final String urlKey) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity(), R.style.myDialog);
        alertDialogBuilder.setMessage(R.string.participation_text);
        alertDialogBuilder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                if (participate == 0) {
                    participateNow(ch_ca_type, ch_ca_id, parameterType, titleText, subTitleText, urlKey, group_id);

                } else {
                    openDialogForValue(parameterType, titleText, subTitleText, urlKey);
                }
                isAlreadyOpened = true;

            }
        });

        alertDialogBuilder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public boolean isValidDate(String startDate, String currentDate, String endDate) {
        Boolean isValidDates = false;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date stDate = sdf.parse(startDate);
            Date eDate = sdf.parse(endDate);
            Date currDate = sdf.parse(currentDate);
           /* if (currDate.before(stDate) && currDate.after(eDate)) {
                isValidDates = false;
            } else {
                isValidDates = true;
            }*/
            if (currDate.after(stDate) && currDate.before(eDate) || (currDate.equals(stDate) || currDate.after(eDate))) {
                isValidDates = true;
            } else if (currDate.before(stDate) || currDate.after(eDate)) {
                isValidDates = false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return isValidDates;
    }

    public boolean isValidDatee(String startDate, String currentDate, String endDate) {
        Boolean isValidDates = false;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date stDate = sdf.parse(startDate);
            Date eDate = sdf.parse(endDate);
            Date currDate = sdf.parse(currentDate);

            if(((currDate.equals(stDate)) ||(currDate.after(stDate))) && ((currDate.equals(eDate)) ||(currDate.before(eDate)))) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return isValidDates;
    }



    /*public boolean isDateExpired(String currentDate, String endDate){
        Boolean isExpiredDate = false;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date eDate = sdf.parse(endDate);
            Date currDate = sdf.parse(currentDate);
            if (eDate.before(currDate)) {
                isExpiredDate = true;
            } else {
                isExpiredDate = false;
            }
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return isExpiredDate;
    }
*/

    public void participateNow(String type, int camp_chall_id, final String parameterType, final String titleText, final String subTitleText, final String urlKey, int id_group) {
        commonApi.showProgressDialog("Please Wait...");
        JSONObject object = new JSONObject();
        try {
            object.put("user_id", userId);
            object.put("camchall_id", camp_chall_id);
            object.put("camchall_status", type);
            object.put("group_id", id_group);
            object.put("selected_class",selected_class);
            Log.d("TAG", "participateJSonObject" + object);
            System.out.println(" participateUrl : " + Urls.PARTICIPATE_CAMPAIGNS_CHALLENGES);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new GetServiceCall(Urls.PARTICIPATE_CAMPAIGNS_CHALLENGES, GetServiceCall.TYPE_JSONOBJECT_POST, object) {
            @Override
            public void response(String response) {
                Log.d("***participate resp", response);
                commonApi.dismissProgressDialog();
                openDialogForValue(parameterType, titleText, subTitleText, urlKey);
            }

            @Override
            public void error(VolleyError error, String errorMsg) {
                commonApi.dismissProgressDialog();
            }
        }.call();
    }

    public void participateNow(String type, int camp_chall_id, final int id_group) {
        commonApi.showProgressDialog("Please Wait...");
        JSONObject object = new JSONObject();
        try {
            object.put("user_id", loginCredentials.getUserId());
            object.put("camchall_id", camp_chall_id);
            object.put("camchall_status", type);
            object.put("group_id", id_group);
            object.put("selected_class",selected_class);
            Log.d("TAG", "parObject" + object);
            System.out.println(" participateUrl : " + Urls.PARTICIPATE_CAMPAIGNS_CHALLENGES);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new GetServiceCall(Urls.PARTICIPATE_CAMPAIGNS_CHALLENGES, GetServiceCall.TYPE_JSONOBJECT_POST, object) {
            @Override
            public void response(String response) {
                Log.d("***participate resp", response);
                try {
                    commonApi.dismissProgressDialog();
                    JSONObject jsonObject = new JSONObject(response);
                    String group_name = jsonObject.getString("group_name");
                    loginCredentials.setUserGroup(String.valueOf(id_group));
                    loginCredentials.setUserGroupName(group_name);
                   // participateClick.onItemClick();
                   // new ChallengeListFragment().getChallengesList();
                    getDataForDashboardDetails();

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
                        upcomingChallengeName = jsonObject2.getString("Heading");
                        upcomingChallengeStartDate = jsonObject2.getString("Sdate");
                    }
                    challengeAndCampaignAdapter = new ChallengeAndCampaignAdapter(loginCredentials.getUserId(), upcomingChallengeAndCampaignsItemsList, getActivity(), "CH", FeedFragment.this);
                    /*LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    rvForUpcomingClngAndCmpn.setLayoutManager(layoutManager);
                    rvForUpcomingClngAndCmpn.setAdapter(challengeAndCampaignAdapter);*/

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

    public String getCountOfDays(String currentDate, String StartDate) {
        /*SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        Date createdConvertedDate = null, expireCovertedDate = null, todayWithZeroTime = null;
        try {
            createdConvertedDate = dateFormat.parse(currentDate);
            expireCovertedDate = dateFormat.parse(StartDate);

            Date today = new Date();

            todayWithZeroTime = dateFormat.parse(dateFormat.format(today));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int cYear = 0, cMonth = 0, cDay = 0;

        if (createdConvertedDate.after(todayWithZeroTime)) {
            Calendar cCal = Calendar.getInstance();
            cCal.setTime(createdConvertedDate);
            cYear = cCal.get(Calendar.YEAR);
            cMonth = cCal.get(Calendar.MONTH);
            cDay = cCal.get(Calendar.DAY_OF_MONTH);

        } else {
            Calendar cCal = Calendar.getInstance();
            cCal.setTime(todayWithZeroTime);
            cYear = cCal.get(Calendar.YEAR);
            cMonth = cCal.get(Calendar.MONTH);
            cDay = cCal.get(Calendar.DAY_OF_MONTH);
        }


    *//*Calendar todayCal = Calendar.getInstance();
    int todayYear = todayCal.get(Calendar.YEAR);
    int today = todayCal.get(Calendar.MONTH);
    int todayDay = todayCal.get(Calendar.DAY_OF_MONTH);
    //

        Calendar eCal = Calendar.getInstance();
        eCal.setTime(expireCovertedDate);

        int eYear = eCal.get(Calendar.YEAR);
        int eMonth = eCal.get(Calendar.MONTH);
        int eDay = eCal.get(Calendar.DAY_OF_MONTH);

        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();

        date1.clear();
        date1.set(cYear, cMonth, cDay);
        date2.clear();
        date2.set(eYear, eMonth, eDay);

        long diff = date2.getTimeInMillis() - date1.getTimeInMillis();

        float dayCount = (float) diff / (24  60  60 * 1000);

        return ("" + (int) dayCount + " Days");*/

        int numOfDays = 0;

        try {
            Date currDate = new SimpleDateFormat("yyyy-MM-dd").parse(currentDate);
            Date stDate = new SimpleDateFormat("yyyy-MM-dd").parse(StartDate);
            long diff = stDate.getTime() - currDate.getTime();
            int numOfYear = (int) ((diff / (1000 * 60 * 60 * 24)) / 365);
            numOfDays = (int) (diff / (1000 * 60 * 60 * 24));
            int hours = (int) (diff / (1000 * 60 * 60));
            int minutes = (int) (diff / (1000 * 60));
            int seconds = (int) (diff / (1000));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(numOfDays);

    }

    @Override
    public void onRedirect(UpcomingChallengeAndCampaignsItem upcomingChallengeAndCampaignsItem, int userId, String type) {

    }

    @Override
    public void onRedirectFromMyCampaignsAndChallenges(ChallengeCampaignListItem challengeCampaignListItem, int userId, String type) {

    }

    @Override
    public void onRediredectToMainActivity() {

    }

    public String parseDate(String inputDateString, SimpleDateFormat inputDateFormat, SimpleDateFormat outputDateFormat) {
        Date date = null;
        String outputDateString = null;
        try {
            date = inputDateFormat.parse(inputDateString);
            outputDateString = outputDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputDateString;
    }

    public void goalsSelectNew(String[] goalsArrayNew) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        checkedItems = new boolean[goalsArrayNew.length];


        builder.setTitle("Select Goals");
        boolean[] check = new boolean[goalsArrayNew.length];

        for (int k = 0; k < goalsArrayNew.length; k++) {
            check[k] = goalItemArrayList.get(k).isChecked();
            checkedItems[k] = goalItemArrayList.get(k).isChecked();

        }


        builder.setMultiChoiceItems(goalsArrayNew, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {

                        if(isChecked){
                        if (checkedItemsCount >= 3 ) {
                            Toast.makeText(getActivity(), "You can select a maximum of 3 goals", Toast.LENGTH_LONG).show();
                            checkedItems[indexSelected]=false;
                            ((AlertDialog) dialog).getListView().setItemChecked(indexSelected, false);
                        }
                        else {
                            check[indexSelected] = isChecked;
                            //GoalItem goalItem = new GoalItem();
                           // goalItem.setChecked(isChecked);
                           // goalItem.setTitle(goalsArrayNew[indexSelected]);
                            goalItemArrayList.get(indexSelected).setChecked(isChecked);
                            //Toast.makeText(getActivity(),selectedItem.getTitle()+ " "+ selectedItem.getSelectedIndex() +" set"+ " "+ selectedItem.isChecked(),Toast.LENGTH_LONG).show();
                            if (isChecked) {
                                checkedItemsCount = checkedItemsCount + 1;
                            } else {
                                checkedItemsCount = checkedItemsCount - 1;
                            }
                        }
                        }
                        else {
                            check[indexSelected] = isChecked;
                            GoalItem goalItem = new GoalItem();
                            goalItem.setChecked(isChecked);
                            //selectedItem.setSelectedIndex(indexSelected);
                            goalItem.setTitle(goalsArrayNew[indexSelected]);
                            goalItemArrayList.set(indexSelected, goalItem);
                            //Toast.makeText(getActivity(),selectedItem.getTitle()+ " "+ selectedItem.getSelectedIndex() +" set"+ " "+ selectedItem.isChecked(),Toast.LENGTH_LONG).show();
                            if (isChecked) {
                                checkedItemsCount = checkedItemsCount + 1;
                            } else {
                                checkedItemsCount = checkedItemsCount - 1;
                            }
                        }

            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // TODO
                str_goals = "";
                if(checkedItemsCount==1)
                {
                    for(int z = 0; z< goalItemArrayList.size(); z++)
                    {
                        if(goalItemArrayList.get(z).isChecked())
                        {
                            str_goals = goalItemArrayList.get(z).getGoalId();


                        }
                    }
                }
                else
                {
                   for(int z = 0; z< goalItemArrayList.size(); z++)
                    {
                        if(goalItemArrayList.get(z).isChecked())
                        {
                            str_goals = str_goals + goalItemArrayList.get(z).getGoalId()+",";
                        }
                    }
                    /*int index = str_goals.lastIndexOf(",");
                    str_goals = str_goals.substring(0, index);*/
                    str_goals = str_goals.replaceAll(", $", "");

                }
               // setTexts(checkedItemsCount);
                saveChallengeGoals();
            }
        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

              //  setGoalsValues();
                dialog.dismiss();


            }
        });
        // create the builderl
        builder.create();
        builder.setCancelable(false);

        // create the alert dialog with the
        // alert dialog builder instance
        AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(arg0 -> {
            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.black));
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.black));
        });

        alertDialog.show();
    }

    public void setTexts(int checkedItemsCount) {
        if(checkedItemsCount>0)
                {
                    int index_item=0;
                    if(checkedItemsCount==1)
                    {
                        llFeedFirstLayout.setVisibility(View.VISIBLE);
                        for(int k = 0; k< goalItemArrayList.size(); k++)
                        {
                            if(goalItemArrayList.get(k).isChecked())
                            {
                                index_item=k;
                            }
                        }
                        textone_first.setText(goalItemArrayList.get(index_item).getTitle());
                        llForTextFromSpinner.setVisibility(View.VISIBLE);
                        llFeedFirstLayout.setVisibility(View.VISIBLE);
                        llFeedSecondLayout.setVisibility(View.GONE);
                        llFeedThirdLayout.setVisibility(View.GONE);
                    }
                    else if(checkedItemsCount==2)
                    {
                        int items_displayed=0;
                        for(int k = 0; k< goalItemArrayList.size(); k++)
                        {
                            if(goalItemArrayList.get(k).isChecked())
                            {
                                index_item=k;
                                items_displayed=items_displayed+1;
                                if(items_displayed==1)
                                {
                                    textone_second.setText(goalItemArrayList.get(k).getTitle());
                                }
                                else if(items_displayed==2)
                                {
                                    textwo_second.setText(goalItemArrayList.get(k).getTitle());
                                }

                            }
                        }
                        textone_first.setText(goalItemArrayList.get(index_item).getTitle());
                        llForTextFromSpinner.setVisibility(View.VISIBLE);
                        llFeedFirstLayout.setVisibility(View.GONE);
                        llFeedSecondLayout.setVisibility(View.VISIBLE);
                        llFeedThirdLayout.setVisibility(View.GONE);
                    }
                     else if(checkedItemsCount==3)
                    {
                        int items_displayed=0;
                        for(int k = 0; k< goalItemArrayList.size(); k++)
                        {
                            if(goalItemArrayList.get(k).isChecked())
                            {
                                index_item=k;
                                items_displayed=items_displayed+1;
                                if(items_displayed==1)
                                {
                                    textone_third.setText(goalItemArrayList.get(k).getTitle());
                                }
                                else if(items_displayed==2)
                                {
                                    texttwo_third.setText(goalItemArrayList.get(k).getTitle());
                                }
                                else if(items_displayed==3)
                                {
                                    textthree_third.setText(goalItemArrayList.get(k).getTitle());
                                }

                            }
                        }
                        llForTextFromSpinner.setVisibility(View.VISIBLE);
                        llFeedFirstLayout.setVisibility(View.GONE);
                        llFeedSecondLayout.setVisibility(View.GONE);
                        llFeedThirdLayout.setVisibility(View.VISIBLE);
                }
                else
                {
                    llForTextFromSpinner.setVisibility(View.GONE);
                    llFeedFirstLayout.setVisibility(View.GONE);
                    llFeedSecondLayout.setVisibility(View.GONE);
                    llFeedThirdLayout.setVisibility(View.GONE);
                }
            }  else {
            llForTextFromSpinner.setVisibility(View.GONE);
            llFeedFirstLayout.setVisibility(View.GONE);
            llFeedSecondLayout.setVisibility(View.GONE);
            llFeedThirdLayout.setVisibility(View.GONE);
        }
    }

    private void setGoalsValues() {
        if (!commonApi.isInternetAvailable(getActivity())) {
            commonApi.showDialog(getActivity(), Constant.NO_INTERNET, "", getResources().getColor(R.color.black), false);
            return;
        }
       // commonApi.showProgressDialog("Please wait..");
        JSONObject obj = new JSONObject();
        try {
            obj.put("user_id", loginCredentials.getUserId());
            System.out.println(" goalsObject : " + obj.toString());
            System.out.println(" goalsUrl : " + Urls.CHALLENGE_GOALS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        new GetServiceCall(Urls.CHALLENGE_GOALS, GetServiceCall.TYPE_JSONOBJECT_POST, obj) {
            @Override
            public void response(String response) {
                try {
                    Log.d("goalsResp:", response);
                    JSONObject jsonObject = new JSONObject(response);
                  //  commonApi.dismissProgressDialog();

                    //if (jsonObject.optString("sStatus").equalsIgnoreCase("1")) {
                        JSONArray jsonArray=jsonObject.getJSONArray("lists");
                        goalsArrayNew=new String[jsonArray.length()];
                        for(int z=0;z<jsonArray.length();z++) {
                            JSONObject jsonObject1=jsonArray.getJSONObject(z);
                            GoalItem goalItem = new GoalItem();
                            goalItem.setGoalId(String.valueOf(jsonObject1.optInt("id")));
                            goalItem.setTitle(jsonObject1.optString("title"));
                            goalsArrayNew[z]=jsonObject1.optString("title");
                            if(jsonObject1.optInt("selected")==1)
                            {
                                goalItem.setChecked(true);
                                checkedItemsCount=checkedItemsCount+1;
                            }
                            else
                            {
                                goalItem.setChecked(false);
                            }
                            goalItemArrayList.add(goalItem);

                        }
                  //  spChoice.setText(checkedItemsCount + " Selected");
                    spChoice.setText("Select Goals");
                    spChoice.setGravity(Gravity.CENTER);
                        if(checkedItemsCount==0)
                        {
                            llForTextFromSpinner.setVisibility(View.GONE);
                            llFeedFirstLayout.setVisibility(View.GONE);
                            llFeedSecondLayout.setVisibility(View.GONE);
                            llFeedThirdLayout.setVisibility(View.GONE);
                        }
                        else {
                            setTexts(checkedItemsCount);
                        }

                } catch (Exception e) {
                    e.printStackTrace();
                }
               // commonApi.dismissProgressDialog();
            }

            @Override
            public void error(VolleyError error, String errorMsg) {
              //  commonApi.dismissProgressDialog();
            }
        }.call();

    }

    public void saveChallengeGoals(){
        if (!commonApi.isInternetAvailable(getActivity())) {
            commonApi.showDialog(getActivity(), Constant.NO_INTERNET, "", getResources().getColor(R.color.black), false);
            return;
        }
        commonApi.showProgressDialog("Please wait..");
        JSONObject obj = new JSONObject();
        try {
            obj.put("user_id", loginCredentials.getUserId());
            obj.put("mygoals", str_goals);
            System.out.println(" goalsSaveObject : " + obj.toString());
            System.out.println(" goalsSaveUrl : " + Urls.CHALLENGE_GOALS_SAVE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        new GetServiceCall(Urls.CHALLENGE_GOALS_SAVE, GetServiceCall.TYPE_JSONOBJECT_POST, obj) {
            @Override
            public void response(String response) {
                try {
                    Log.d("goalsResp:", response);
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("sStatus").equalsIgnoreCase("1")) {
                        spChoice.setText(checkedItemsCount + " Selected");
                        spChoice.setGravity(Gravity.CENTER);
                        setTexts(checkedItemsCount);
                        //dialogMessageForSuccess();
                    } else {
                        commonApi.showDialog(getActivity(), jsonObject.getString("sMessage"), "", getResources().getColor(R.color.black), false);
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

    public void callClassList() {

        commonApi.showProgressDialog("Please Wait...");
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
        bottomSheetDialog.setContentView(R.layout.classlist_view);
        bottomSheetDialog.setCanceledOnTouchOutside(false);

       // TextView btn_Add = bottomSheetDialog.findViewById(R.id.btn_Add);
        RecyclerView rvForClassList = bottomSheetDialog.findViewById(R.id.rvForClassList);
        ImageView img_close = bottomSheetDialog.findViewById(R.id.img_close);

        String s = Urls.GET_CLASS_LIST+ch_ca_id+"/"+ch_ca_type;
        List<String> classArrayList = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, s,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            commonApi.dismissProgressDialog();
                            JSONObject jsonObject = new JSONObject(response);
                            String listValues = jsonObject.getString("classlists");
                            JSONArray listItems = new JSONArray(listValues);

                            for (int i = 0; i < listItems.length(); i++) {
                                String str = listItems.getString(i);
                                classArrayList.add(str);
                            }
                            if(classArrayList.size()>0 && !classArrayList.get(0).equals("")) {
                                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                                rvForClassList.setLayoutManager(layoutManager);
                                ClassListAdapter classListAdapter = new ClassListAdapter(classArrayList, getActivity(), FeedFragment.this);
                                rvForClassList.setAdapter(classListAdapter);
                               // btn_Add.setText("Add");
                              //  btn_Add.setVisibility(View.VISIBLE);
                                bottomSheetDialog.show();
                                //participateNow(ch_ca_type, ch_ca_id, group_id,selected_class);
                            } else {
                              //  btn_Add.setVisibility(View.GONE);
                                bottomSheetDialog.show();
                            }
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

       /* btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                participateNow(ch_ca_type, ch_ca_id, group_id,selected_class);
            }
        });*/

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // selected_class = "";
                bottomSheetDialog.dismiss();
                participateNow(ch_ca_type, ch_ca_id, group_id);
            }
        });
    }

    @Override
    public void onItemClick(String data) {
        selected_class = data;
        Log.d("Selected: ",data);

    }

    public void  showConfirmDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.class_conf_dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        RelativeLayout btnYes = dialog.findViewById(R.id.btnYes);
        RelativeLayout btnNo  = dialog.findViewById(R.id.btnNo);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                callClassList();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                participateNow(ch_ca_type, ch_ca_id, group_id);
            }

        });

    }


}

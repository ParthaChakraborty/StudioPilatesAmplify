package com.studio.amplify.fragment;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnCalendarPageChangeListener;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.bumptech.glide.Glide;
import com.studio.amplify.R;
import com.studio.amplify.activity.ShowListingActivity;
import com.studio.amplify.activity.StopWatchActivity;
import com.studio.amplify.activity.TrackDetailsActivity;
import com.studio.amplify.adapter.TrackingListAdapter;
import com.studio.amplify.model.CalendarEventItem;
import com.studio.amplify.model.TrackingListItem;
import com.studio.amplify.util.BaseFragment;
import com.studio.amplify.util.CommonApi;
import com.studio.amplify.util.Constant;
import com.studio.amplify.util.OnItemClick;
import com.studio.amplify.util.StaticValues;
import com.studio.amplify.util.Urls;
import com.studio.amplify.volleyparser.GetServiceCall;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
//import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

public class TrackingFragment extends BaseFragment implements /* DatePickerListener,*/ OnItemClick {

    //banner
    @BindView(R.id.goalTrackingText)
    TextView goalTrackingText;
    @BindView(R.id.textViewRunningWeek)
    TextView textViewRunningWeek;
    @BindView(R.id.ivBannerImage)
    ImageView ivBannerImage;
    //banner

   /* @BindView(R.id.horizontalPicker)
    HorizontalPicker horizontalPicker;*/
    @BindView(R.id.tvTotalCalculation)
    TextView tvTotalCalculation;

    //tracking items
    @BindView(R.id.rootLayoutForViews)
    LinearLayout rootLayoutForViews;
    @BindView(R.id.rvForTrackingItems)
    RecyclerView rvForTrackingItems;
    @BindView(R.id.textViewPointPlan)
    TextView textViewPointPlan;
    ArrayList<TrackingListItem> trackingItemArrayList;
    TrackingListAdapter trackingListAdapter;
    @BindView(R.id.tvTotalPointsHeading)
    TextView tvTotalPointsHeading;
    @BindView(R.id.tvTotalPointTarget)
    TextView tvTotalPointTarget;
    @BindView(R.id.tvViewList)
    TextView tvViewList;
    @BindView(R.id.viewKonfettiLib)
    KonfettiView viewKonfettiLib;
    //tracking items

    //mood today
    @BindView(R.id.rootRlForEmojiFeedback)
    LinearLayout rootRlForEmojiFeedback;
    @BindView(R.id.textViewShareYourMood)
    TextView textViewShareYourMood;
    @BindView(R.id.rlSmileThrill)
    RelativeLayout rlSmileThrill;
    @BindView(R.id.rlSmileContent)
    RelativeLayout rlSmileContent;
    @BindView(R.id.rlSmileDiscouraged)
    RelativeLayout rlSmileDiscouraged;
    @BindView(R.id.rlSmileFrustrated)
    RelativeLayout rlSmileFrustrated;
    @BindView(R.id.textViewFeedback)
    TextView textViewFeedback;
    @BindView(R.id.textViewWhatsOnYourMind)
    TextView textViewWhatsOnYourMind;
    @BindView(R.id.ivForAward)
    ImageView ivForAward;
    @BindView(R.id.rlForAward)
    RelativeLayout rlForAward;
    String isAwardStatus;
    @BindView(R.id.textViewForAwardText)
    TextView textViewForAwardText;
    @BindView(R.id.textViewSmileThrill)
    TextView textViewSmileThrill;
    @BindView(R.id.textViewSmileContent)
    TextView textViewSmileContent;
    @BindView(R.id.textViewSmileDiscouraged)
    TextView textViewSmileDiscouraged;
    @BindView(R.id.textViewSmileFrustrated)
    TextView textViewSmileFrustrated;
    //mood today
    @BindView(R.id.feel_layout)
    RelativeLayout feel_layout;
    @BindView(R.id.ScrollView)
    ScrollView scrollView;
    @BindView(R.id.rlStopWatch)
    RelativeLayout rlStopWatch;

    Unbinder unbinder;
    int participate;int ch_ca_id;
    String finalDateFromPicker, dateSelected;
    Drawable highlightForEmoji;
    String dateToSend, targetPointToCheck;
    //  String challangeID;
    int dateChange = 0;
    int group_id;
    String ch_ca_type, group_name, user_group_name, dateTimeFormat = "yyyy-MM-dd", challenge_name;
    // String pointPlanText;
    boolean isTargetPoint = true, isDialogShown = false, flag = false;
    private CommonApi commonApi;
    int id;
    String startDate, endDate, currentDate;
    String status;
    private boolean isAlreadyOpened = false;
  //  String value, operation, spinnerSelection;

    private Calendar startCalender,endCalender;
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
    //goal layout

    @BindView(R.id.tvHead)
    TextView tvHead;

    @BindView(R.id.ivBadgeIcon)
    ImageView ivBadgeIcon;

    @BindView(R.id.tvBadgeHead)
    TextView tvBadgeHead;

    @BindView(R.id.calendarView)
    CalendarView calendarView;

    @BindView(R.id.viewForAward)
    View viewForAward;


    @BindView(R.id.rlBadge_recoginition)
     RelativeLayout rlBadge_recoginition;

    @BindView(R.id.ivForAward2)
    ImageView ivForAward2;

    @BindView(R.id.textViewForAwardText2)
    TextView textViewForAwardText2;


    boolean onResumeCalled=false;

    String start_body_weight, current_body_weight, goal_weight, start_body_fat, current_body_fat, goal_body_fat;
    //ArrayList<CalendarEventItem> calenderEventItemArrayList;
    int days, month, year;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.tracking_fragment, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this, layout);
        unbinder = ButterKnife.bind(this, layout);
        commonApi = new CommonApi(getActivity());

        getBanner();
        highlightForEmoji = getResources().getDrawable(R.drawable.emoji_border_design);
        setWeightStats();


        // initialize it and attach a listener
        /*Log.d("***Challenge start date",loginCredentials.getChallengeStartDate());
        Log.d("***No.of days",String.valueOf(Daybetween(loginCredentials.getChallengeStartDate())));
*/
     //   QuranGalleryScrollView.setScrollingEnabled(false);

        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });


        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                int index;
                String idString = "";
                Calendar clickedDayCalendar = eventDay.getCalendar();

                Log.d("condition","start "+clickedDayCalendar.equals(startCalender)+","+clickedDayCalendar.before(endCalender));
                if (clickedDayCalendar.after(startCalender) && clickedDayCalendar.before(endCalender)) {
                    java.text.SimpleDateFormat d = new java.text.SimpleDateFormat("MM");
                    //java.text.SimpleDateFormat e = new java.text.SimpleDateFormat("dd");

                    // onResumeCalled=false;

                    String calendarYear = String.valueOf(clickedDayCalendar.get(Calendar.YEAR));
                    String calendarMonth = (d.format(clickedDayCalendar.getTime()));
                    String calendarDay = String.valueOf(clickedDayCalendar.get(Calendar.DAY_OF_MONTH));


                    if (calendarDay.trim().length() < 2) {
                        calendarDay = "0" + clickedDayCalendar.get(Calendar.DAY_OF_MONTH);

                    } else {
                        calendarDay = String.valueOf(clickedDayCalendar.get(Calendar.DAY_OF_MONTH));
                    }

                    Bundle cal = new Bundle();
                    cal.putString("year", calendarYear);
                    cal.putString("month", calendarMonth);
                    cal.putString("day", calendarDay);
                    //if(calenderEventItemArrayList.contains(calendarYear + "-" + calendarMonth + "-" + calendarDay)) {
                    // Toast.makeText(getActivity(), "No checked events on this date", Toast.LENGTH_LONG).show();
                /*if (calenderEventItemArrayList.size() > 0) {
                    for (int z = 0; z < calenderEventItemArrayList.size(); z++) {
                        if (calenderEventItemArrayList.get(z).getDate().equals(calendarYear + "-" + calendarMonth + "-" + calendarDay)) {
                            cal.putString("idString", calenderEventItemArrayList.get(z).getIdString());
                        } else {
                            cal.putString("idString", "");
                        }
                    }
                } else {
                    cal.putString("idString", "");
                }*/

                /*if (StaticValues.calenderEventItemArrayList.size() > 0) {
                    for (int z = 0; z < StaticValues.calenderEventItemArrayList.size(); z++) {
                        if (StaticValues.calenderEventItemArrayList.get(z).getDate().equals(calendarYear + "-" + calendarMonth + "-" + calendarDay)) {
                            cal.putString("idString", StaticValues.calenderEventItemArrayList.get(z).getIdString());
                        } else {
                            //cal.putString("idString", "");
                        }
                    }
                } else {
                    cal.putString("idString", "");
                }*/

                    for (int z = 0; z < StaticValues.calenderEventItemArrayList.size(); z++) {
                        if (StaticValues.calenderEventItemArrayList.get(z).getDate().equals(calendarYear + "-" + calendarMonth + "-" + calendarDay)) {
                            idString = StaticValues.calenderEventItemArrayList.get(z).getIdString();
                        }
                    }
                    if (!idString.equals("")) {
                        cal.putString("idString", idString);
                    } else {
                        cal.putString("idString", "");
                    }
                    cal.putString("date",finalDateFromPicker);

                    //}
                    // } else {
                    // cal.putString("idString","");
                    // Toast.makeText(getActivity(),"No checked events on this date",Toast.LENGTH_LONG).show();
                    //  }

                    /*for (int z = 0; z < calenderItemArrayList.size(); z++) {
                        if (calenderItemArrayList.get(z).getDate().equals(calendarYear + "-" + calendarMonth + "-" + calendarDay)) {
                            cal.putStringArrayList("idList", calenderItemArrayList.get(z).getIdList());
                        }
                    }*/
                    commonApi.openNewScreen(TrackDetailsActivity.class, cal);
                }
                else if((clickedDayCalendar.get(Calendar.DAY_OF_YEAR) == startCalender.get(Calendar.DAY_OF_YEAR) &&
                        clickedDayCalendar.get(Calendar.YEAR) == startCalender.get(Calendar.YEAR) && clickedDayCalendar.get(Calendar.MONTH)==startCalender.get(Calendar.MONTH)) && clickedDayCalendar.before(endCalender)){
                    java.text.SimpleDateFormat d = new java.text.SimpleDateFormat("MM");
                    String calendarYear = String.valueOf(clickedDayCalendar.get(Calendar.YEAR));
                    String calendarMonth = (d.format(clickedDayCalendar.getTime()));
                    String calendarDay = String.valueOf(clickedDayCalendar.get(Calendar.DAY_OF_MONTH));



                    if (calendarDay.trim().length() < 2) {
                        calendarDay = "0" + clickedDayCalendar.get(Calendar.DAY_OF_MONTH);

                    } else {
                        calendarDay = String.valueOf(clickedDayCalendar.get(Calendar.DAY_OF_MONTH));
                    }

                    Bundle cal = new Bundle();
                    cal.putString("year", calendarYear);
                    cal.putString("month", calendarMonth);
                    cal.putString("day", calendarDay);
                    //if(calenderEventItemArrayList.contains(calendarYear + "-" + calendarMonth + "-" + calendarDay)) {
                    // Toast.makeText(getActivity(), "No checked events on this date", Toast.LENGTH_LONG).show();
                /*if (calenderEventItemArrayList.size() > 0) {
                    for (int z = 0; z < calenderEventItemArrayList.size(); z++) {
                        if (calenderEventItemArrayList.get(z).getDate().equals(calendarYear + "-" + calendarMonth + "-" + calendarDay)) {
                            cal.putString("idString", calenderEventItemArrayList.get(z).getIdString());
                        } else {
                            cal.putString("idString", "");
                        }
                    }
                } else {
                    cal.putString("idString", "");
                }*/

                /*if (StaticValues.calenderEventItemArrayList.size() > 0) {
                    for (int z = 0; z < StaticValues.calenderEventItemArrayList.size(); z++) {
                        if (StaticValues.calenderEventItemArrayList.get(z).getDate().equals(calendarYear + "-" + calendarMonth + "-" + calendarDay)) {
                            cal.putString("idString", StaticValues.calenderEventItemArrayList.get(z).getIdString());
                        } else {
                            //cal.putString("idString", "");
                        }
                    }
                } else {
                    cal.putString("idString", "");
                }*/

                    for (int z = 0; z < StaticValues.calenderEventItemArrayList.size(); z++) {
                        if (StaticValues.calenderEventItemArrayList.get(z).getDate().equals(calendarYear + "-" + calendarMonth + "-" + calendarDay)) {
                            idString = StaticValues.calenderEventItemArrayList.get(z).getIdString();
                        }
                    }
                    if (!idString.equals("")) {
                        cal.putString("idString", idString);
                    } else {
                        cal.putString("idString", "");
                    }
                    cal.putString("date",finalDateFromPicker);

                    //}
                    // } else {
                    // cal.putString("idString","");
                    // Toast.makeText(getActivity(),"No checked events on this date",Toast.LENGTH_LONG).show();
                    //  }

                    /*for (int z = 0; z < calenderItemArrayList.size(); z++) {
                        if (calenderItemArrayList.get(z).getDate().equals(calendarYear + "-" + calendarMonth + "-" + calendarDay)) {
                            cal.putStringArrayList("idList", calenderItemArrayList.get(z).getIdList());
                        }
                    }*/
                    commonApi.openNewScreen(TrackDetailsActivity.class, cal);

                }
            }
        });

        calendarView.setOnPreviousPageChangeListener(new OnCalendarPageChangeListener() {
            @Override
            public void onChange() {
               String onlyYearMonth = getYearMonth(calendarView.getCurrentPageDate().getTimeInMillis(), dateTimeFormat);
                showChallengeDates(onlyYearMonth);
            }
        });

        calendarView.setOnForwardPageChangeListener(new OnCalendarPageChangeListener() {
            @Override
            public void onChange() {
              String  onlyYearMonth = getYearMonth(calendarView.getCurrentPageDate().getTimeInMillis(), dateTimeFormat);
                showChallengeDates(onlyYearMonth);
            }
        });

      /*  horizontalPicker.setListener(this)
                //.setDays(Daybetween(loginCredentials.getChallengeStartDate()))
                .setDays(10)
                .setOffset(5)
                .setDateSelectedColor(getResources().getColor(R.color.black))
                .setDateSelectedTextColor(Color.WHITE)
                .setMonthAndYearTextColor(Color.WHITE)
                .setDayOfWeekTextColor(getResources().getColor(R.color.black))
                .setTodayDateTextColor(getResources().getColor(R.color.black))
                .setTodayDateBackgroundColor(Color.WHITE)
                .setUnselectedDayTextColor(Color.WHITE)
                .setDayOfWeekTextColor(getResources().getColor(R.color.black))
                .showTodayButton(false)
                .init();

        // or on the View directly after init was completed
        horizontalPicker.setBackgroundColor(Color.WHITE);
        horizontalPicker.setDate(new DateTime());*/


       // showChallengeDates();
        Log.d("***new datetime", new DateTime().toString());

        try {
            DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
            DateTime dt = formatter.parseDateTime(loginCredentials.getCurrentDateFromFeed());
            Log.d("***currentDateFromFeed", dt.toString());
           // horizontalPicker.setDate(dt);
        } catch (Exception e) {
            e.printStackTrace();
        }



        return layout;
    }

    /*@Override
    public void onDateSelected(DateTime dateSelected) {
        dateToSend = String.valueOf(dateSelected.toDate());
        Log.d("***selected date", dateToSend);
        finalDateFromPicker = dateSelected.toLocalDate().toString().replace("-", "");
        loginCredentials.setCurrentDate(finalDateFromPicker);
        dateChange = dateChange + 1;
        if (dateChange == 1) {
            getDataForTrackingDetails(finalDateFromPicker);
        } else {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date strDate = sdf.parse(dateSelected.toLocalDate().toString());
                if (new Date().after(strDate)) {
                    Log.d("***right date", "execution  allowed");
                    feel_layout.setVisibility(View.VISIBLE);
                    isTargetPoint = true;
                    getDataForTrackingDetails(finalDateFromPicker);

                } else {
                    Log.d("***wrong date", "execution not allowed");
                    rvForTrackingItems.setAdapter(null);
                    tvTotalCalculation.setText("NA");
                    feel_layout.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
*/
    private void getDataForTrackingDetails(String date) {

     //   commonApi.showProgressDialog("Please wait..");
      //  trackingItemArrayList.clear();
        String url;
        /*if(loginCredentials.getCAMP_CHAL_ID() == 0 && loginCredentials.getCAMP_CHAL_TYPE().equals("")) {
            System.out.println(" trackUrl : " + Urls.GET_TRACKING_PAGE_DETAIL + loginCredentials.getUserId() + "/" + date + "/" + loginCredentials.getCAMP_CHAL_TYPE() + "/" + loginCredentials.getCAMP_CHAL_ID());
            url = Urls.GET_TRACKING_PAGE_DETAIL + loginCredentials.getUserId() + "/" + date;
        } else {*/
        url = Urls.GET_TRACKING_PAGE_DETAIL + loginCredentials.getUserId() + "/" + date + "/" + loginCredentials.getCAMP_CHAL_ID() + "/" + loginCredentials.getCAMP_CHAL_TYPE();

       // }
        Log.d("***", url);
        new GetServiceCall(url, GetServiceCall.TYPE_JSONOBJECT) {
            @Override
            public void response(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    System.out.println("***TrackingPage : " + jsonObject.toString());
                    try {
                        Glide.with(getActivity()).load(Urls.BASE_URL + jsonObject.optString("banner")).into(ivBannerImage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String runningWeek = jsonObject.optString("week_no");
                    textViewRunningWeek.setText("WEEK " + runningWeek + " RUNNING");
                    //for award
                    targetPointToCheck = jsonObject.optString("target_point");
                    isAwardStatus = jsonObject.optString("is_award");


                    String badgeHead = jsonObject.optString("badgeheading");
                    /*tvBadgeHead.setText(badgeHead);
                    try {
                        Glide.with(getActivity()).load(jsonObject.getString("badgeicon")).into(ivBadgeIcon);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/

                     if(badgeHead.equals("null") || badgeHead.equals("")) {
                         ivBadgeIcon.setVisibility(View.GONE);
                         tvBadgeHead.setVisibility(View.GONE);
                    } else {
                         flag=true;
                         ivBadgeIcon.setVisibility(View.VISIBLE);
                         tvBadgeHead.setVisibility(View.VISIBLE);
                         tvBadgeHead.setText(badgeHead);
                         try {
                             Glide.with(getActivity()).load(jsonObject.getString("badgeicon")).into(ivBadgeIcon);
                         } catch (Exception e) {
                             e.printStackTrace();
                         }
                    }

                    String awardhead = jsonObject.getString("awardheading1");
                     /*textViewForAwardText.setText(awardhead);
                    try {
                        Glide.with(getActivity()).load(jsonObject.getString("awardicon1")).into(ivForAward);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }*/

                    if(awardhead.equals("") || awardhead.equals("null")){
                        textViewForAwardText.setVisibility(View.GONE);
                        ivForAward.setVisibility(View.GONE);
                    } else {
                        flag = true;
                        textViewForAwardText.setVisibility(View.VISIBLE);
                        ivForAward.setVisibility(View.VISIBLE);
                        textViewForAwardText.setText(jsonObject.getString("awardheading1"));
                        Glide.with(getActivity()).load(jsonObject.getString("awardicon1")).into(ivForAward);
                    }

                    String awardhead2 = jsonObject.getString("awardheading2");
                    /*textViewForAwardText2.setText(awardhead2);
                    try {
                        Glide.with(getActivity()).load(jsonObject.getString("awardicon2")).into(ivForAward2);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }*/

                    if(awardhead2.equals("") || awardhead2.equals("null")){
                        textViewForAwardText2.setVisibility(View.GONE);
                        ivForAward2.setVisibility(View.GONE);
                    } else {
                        flag = true;
                        textViewForAwardText2.setVisibility(View.VISIBLE);
                        ivForAward2.setVisibility(View.VISIBLE);
                        textViewForAwardText2.setText(awardhead2);
                        Glide.with(getActivity()).load(jsonObject.getString("awardicon2")).into(ivForAward2);
                    }

                    if(flag) {
                        tvHead.setVisibility(View.VISIBLE);
                    } else {
                        tvHead.setVisibility(View.GONE);
                    }

                    /*if(tvBadgeHead.getText().toString().equals("") && textViewForAwardText.getText().toString().equals("")
                    && textViewForAwardText2.getText().toString().equals("")) {
                        tvHead.setVisibility(View.GONE);
                        tvBadgeHead.setVisibility(View.GONE);
                        ivBadgeIcon.setVisibility(View.GONE);
                        textViewForAwardText.setVisibility(View.GONE);
                        ivForAward.setVisibility(View.GONE);
                        textViewForAwardText2.setVisibility(View.GONE);
                        ivForAward2.setVisibility(View.GONE);
                    } else if(tvBadgeHead.getText().toString().equals("") && textViewForAwardText.getText().toString().equals("")) {
                        tvHead.setVisibility(View.VISIBLE);
                        tvBadgeHead.setVisibility(View.GONE);
                        ivBadgeIcon.setVisibility(View.GONE);
                        textViewForAwardText.setVisibility(View.GONE);
                        ivForAward.setVisibility(View.GONE);
                    } else if(textViewForAwardText.getText().toString().equals("") && textViewForAwardText2.getText().toString().equals("")) {
                        tvHead.setVisibility(View.VISIBLE);
                        textViewForAwardText.setVisibility(View.GONE);
                        ivForAward2.setVisibility(View.GONE);
                        textViewForAwardText2.setVisibility(View.GONE);
                        ivForAward2.setVisibility(View.GONE);
                    } else if(tvBadgeHead.getText().toString().equals("") && textViewForAwardText2.getText().toString().equals("")) {
                        tvHead.setVisibility(View.VISIBLE);
                        tvBadgeHead.setVisibility(View.GONE);
                        ivBadgeIcon.setVisibility(View.GONE);
                        textViewForAwardText2.setVisibility(View.GONE);
                        ivForAward2.setVisibility(View.GONE);
                    } else if(tvBadgeHead.getText().toString().equals("")) {
                        tvBadgeHead.setVisibility(View.GONE);
                        ivBadgeIcon.setVisibility(View.GONE);
                    } else if(textViewForAwardText.getText().toString().equals("")) {
                        textViewForAwardText.setVisibility(View.GONE);
                        ivForAward.setVisibility(View.GONE);
                    } else if(textViewForAwardText2.getText().toString().equals("")) {
                        textViewForAwardText2.setVisibility(View.GONE);
                        ivForAward2.setVisibility(View.GONE);
                    }*/



                   /* if (isAwardStatus.equalsIgnoreCase("1")) {
                        rlForAward.setVisibility(View.VISIBLE);
                    } else {
                        rlForAward.setVisibility(View.GONE);
                    }*/
                    //for award

                    //for layout disability of previous challenge
                    String isLayoutDisableCheck = jsonObject.optString("is_disabled");
                    if (isLayoutDisableCheck.equalsIgnoreCase("1")) {
                        commonApi.enableViews(rootRlForEmojiFeedback, false);
                    } else {
                        commonApi.enableViews(rootRlForEmojiFeedback, true);
                    }
                    //for layout disability


                    String targetPointText = jsonObject.optString("target_point_text");
                    tvTotalPointTarget.setText(targetPointText);
                    String pointPlanText = jsonObject.optString("point_plan_text");
                    textViewPointPlan.setText(pointPlanText);
                    String emojiValue = jsonObject.optString("emoji");
                    if (emojiValue.equalsIgnoreCase("Thrilled")) {
                        rlSmileThrill.setBackground(null);
                    }
                    if (emojiValue.equalsIgnoreCase("Content")) {
                        rlSmileContent.setBackground(null);
                    }
                    if (emojiValue.equalsIgnoreCase("Discouraged")) {
                        rlSmileDiscouraged.setBackground(null);
                    }
                    if (emojiValue.equalsIgnoreCase("Frustrated")) {
                        rlSmileFrustrated.setBackground(null);
                    }

                    String onYourMindValue = jsonObject.optString("on_your_mind");
                    textViewFeedback.setText(onYourMindValue);

                    JSONArray jsonArrayForItemList = jsonObject.getJSONArray("track_item");
                    for (int i = 0; i < jsonArrayForItemList.length(); i++) {
                        JSONObject jsonObject1 = jsonArrayForItemList.getJSONObject(i);
                        TrackingListItem trackingItem = new TrackingListItem();

                        trackingItem.setItem_id(jsonObject1.optString("item_id"));
                        trackingItem.setItem(jsonObject1.optString("item"));
                        trackingItem.setItem_point(jsonObject1.optString("item_point"));
                        //   trackingItem.setChossen_option("");
                        // trackingItem.setChossen_option_point("0");
                        trackingItem.setIcon(jsonObject1.optString("icon"));
                        trackingItem.setIsChecked(jsonObject1.optString("isChecked"));
                       /* challangeID = jsonObject.optString("challange_id");
                        trackingItem.setChallange_id(challangeID);*/

                        //for option listing
                        List<String> myOptionList = new ArrayList<String>(Arrays.asList(jsonObject1.optString("options").split(",")));
//                        if (!myOptionList.isEmpty()) {
                        trackingItem.setOptions(jsonObject1.optString("options"));
                        trackingItem.setOption_points(jsonObject1.optString("option_points"));
//                        }
                        //for option listing

                        trackingItem.setChossen_option(jsonObject1.optString("chossen_option"));
                        trackingItem.setChossen_option_point(jsonObject1.optString("chossen_option_point"));

                    //    trackingItemArrayList.add(trackingItem);
                    }
                    Constant.totalPoints = 0;
                    trackingListAdapter = new TrackingListAdapter(trackingItemArrayList, getActivity(), TrackingFragment.this, dateToSend, isLayoutDisableCheck, isTargetPoint);
                    rvForTrackingItems.setAdapter(trackingListAdapter);
                    trackingListAdapter.notifyDataSetChanged();
                    //   calculateTotalPoints();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
              //  commonApi.dismissProgressDialog();
            }

            @Override
            public void error(VolleyError error, String errorMsg) {
                commonApi.dismissProgressDialog();
            }
        }.call();
        rlSmileThrill.setBackground(null);
        rlSmileContent.setBackground(null);
        rlSmileDiscouraged.setBackground(null);
        rlSmileFrustrated.setBackground(null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.rlSmileThrill, R.id.rlSmileContent, R.id.rlSmileDiscouraged, R.id.rlSmileFrustrated,
            R.id.tvViewList/*, R.id.tvClock*/, R.id.rlStopWatch,R.id.llClickOfStartWeight, R.id.llClickOfCurrentWeight, R.id.llClickOfGoalWeight,
            R.id.llClickOfStartBodyFat, R.id.llClickOfCurrentBodyFat, R.id.llClickOfGoalBodyFat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rlSmileThrill:
                rlSmileThrill.setBackground(highlightForEmoji);
                rlSmileContent.setBackground(null);
                rlSmileDiscouraged.setBackground(null);
                rlSmileFrustrated.setBackground(null);
                openDialogForValue("Thrilled");
                break;
            case R.id.rlSmileContent:
                rlSmileContent.setBackground(highlightForEmoji);
                rlSmileThrill.setBackground(null);
                rlSmileDiscouraged.setBackground(null);
                rlSmileFrustrated.setBackground(null);
                openDialogForValue("Content");
                break;
            case R.id.rlSmileDiscouraged:
                rlSmileDiscouraged.setBackground(highlightForEmoji);
                rlSmileContent.setBackground(null);
                rlSmileThrill.setBackground(null);
                rlSmileFrustrated.setBackground(null);
                openDialogForValue("Discouraged");
                break;
            case R.id.rlSmileFrustrated:
                rlSmileFrustrated.setBackground(highlightForEmoji);
                rlSmileContent.setBackground(null);
                rlSmileThrill.setBackground(null);
                rlSmileDiscouraged.setBackground(null);
                openDialogForValue("Frustrated");
                break;
            case R.id.tvViewList:
                Bundle b13 = new Bundle();
                //  b13.putString("challange_id", challangeID);
                //  b13.putString("point_plan_text", pointPlanText);
                b13.putInt("camchall_id", loginCredentials.getCAMP_CHAL_ID());
                b13.putString("camchall_status", loginCredentials.getCAMP_CHAL_TYPE());
                commonApi.openNewScreen(ShowListingActivity.class, b13);
                break;
            /*case R.id.tvClock:
                commonApi.openNewScreen(StopWatchActivity.class, null);
                break;*/
            case R.id.rlStopWatch:
                commonApi.openNewScreen(StopWatchActivity.class, null);
                break;
            case R.id.llClickOfStartWeight:
                /*if (!isValidDate(startDate, currentDate, endDate)) {
                    Toast.makeText(getActivity(), R.string.expire_text, Toast.LENGTH_LONG).show();
                } else*/ if (participate == 1) {
                if (!endDate.equals("") && isValidDatee(startDate, currentDate, endDate)) {
                    openDialogForValue1(start_body_weight, "Start Weight", "Enter your start body weight", "start-weight");
                } else
                    Toast.makeText(getActivity(), R.string.expire_text, Toast.LENGTH_LONG).show();
            } else {
                showParticipateDialog(start_body_weight, "Start Weight", "Enter your start body weight", "start-weight");
            }

                break;

            case R.id.llClickOfCurrentWeight:
                /*if (!isValidDate(startDate, currentDate, endDate)) {
                    Toast.makeText(getActivity(), R.string.expire_text, Toast.LENGTH_LONG).show();
                } else*/ if (participate == 1) {
                if (!endDate.equals("") && isValidDatee(startDate, currentDate, endDate)) {
                    openDialogForValue1(current_body_weight, "Current Weight", "Enter your current body weight", "current-weight");
                } else
                    Toast.makeText(getActivity(), R.string.expire_text, Toast.LENGTH_LONG).show();
            } else {
                showParticipateDialog(current_body_weight, "Current Weight", "Enter your current body weight", "current-weight");
            }

                break;

            case R.id.llClickOfGoalWeight:
                /*if (!isValidDate(startDate, currentDate, endDate)) {
                    Toast.makeText(getActivity(), R.string.expire_text, Toast.LENGTH_LONG).show();
                } else*/ if (participate == 1) {
                if (!endDate.equals("") && isValidDatee(startDate, currentDate, endDate)) {
                    openDialogForValue1(goal_weight, "Goal Weight", "Enter your goal body weight", "goal-weight");
                } else
                    Toast.makeText(getActivity(), R.string.expire_text, Toast.LENGTH_LONG).show();
            } else {
                showParticipateDialog(goal_weight, "Goal Weight", "Enter your goal body weight", "goal-weight");
            }

                break;

            case R.id.llClickOfStartBodyFat:
                /*if (!isValidDate(startDate, currentDate, endDate)) {
                    Toast.makeText(getActivity(), R.string.expire_text, Toast.LENGTH_LONG).show();
                } else*/ if (participate == 1) {
                if (!endDate.equals("") && isValidDatee(startDate, currentDate, endDate)) {
                    openDialogForValue1(start_body_fat, "Start Fat", "Enter your start body fat", "start-body-fat");
                } else
                    Toast.makeText(getActivity(), R.string.expire_text, Toast.LENGTH_LONG).show();
            } else {
                showParticipateDialog(start_body_fat, "Start Fat", "Enter your start body fat", "start-body-fat");
            }

                break;

            case R.id.llClickOfCurrentBodyFat:
                /*if (!isValidDate(startDate, currentDate, endDate)) {
                    Toast.makeText(getActivity(), R.string.expire_text, Toast.LENGTH_LONG).show();
                } else*/ if (participate == 1) {
                if (!endDate.equals("") && isValidDatee(startDate, currentDate, endDate)) {
                    openDialogForValue1(current_body_fat, "Current Fat", "Enter your current body fat", "current-body-fat");
                } else
                    Toast.makeText(getActivity(), R.string.expire_text, Toast.LENGTH_LONG).show();
            } else {
                showParticipateDialog(current_body_fat, "Current Fat", "Enter your current body fat", "current-body-fat");
            }

                break;

            case R.id.llClickOfGoalBodyFat:
                /*if (!isValidDate(startDate, currentDate, endDate)) {
                    Toast.makeText(getActivity(), R.string.expire_text, Toast.LENGTH_LONG).show();
                } else*/ if (participate == 1) {
                if (!endDate.equals("") && isValidDatee(startDate, currentDate, endDate)) {
                    openDialogForValue1(goal_body_fat, "Goal Fat", "Enter your goal body fat", "goal-body-fat");
                } else
                    Toast.makeText(getActivity(), R.string.expire_text, Toast.LENGTH_LONG).show();
            } else {
                showParticipateDialog(goal_body_fat, "Goal Fat", "Enter your goal body fat", "goal-body-fat");
            }
                break;
            default:
        }
    }

    private void openDialogForValue(final String emojiValue) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.goal_setting_dialog_for_feed);
        dialog.setCancelable(false);
        TextView alertTitle = dialog.findViewById(R.id.alertTitle);
        alertTitle.setText(R.string.challenge_journal_heading);
        TextView alertMessage = dialog.findViewById(R.id.alertMessage);
        alertMessage.setVisibility(View.GONE);
        final EditText etValue = dialog.findViewById(R.id.etValue);
        etValue.setHint(R.string.challenge_journal_body);
        etValue.setInputType(InputType.TYPE_CLASS_TEXT);

        etValue.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    sendEmojiFeedBackToWebservice(emojiValue, etValue.getText().toString().trim());
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
                rlSmileThrill.setBackground(null);
                rlSmileContent.setBackground(null);
                rlSmileDiscouraged.setBackground(null);
                rlSmileFrustrated.setBackground(null);
            }
        });
        TextView alertOk = dialog.findViewById(R.id.alertOk);
        alertOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmojiFeedBackToWebservice(emojiValue, etValue.getText().toString().trim());
                dialog.dismiss();
                rlSmileThrill.setBackground(null);
                rlSmileContent.setBackground(null);
                rlSmileDiscouraged.setBackground(null);
                rlSmileFrustrated.setBackground(null);
            }
        });

        dialog.show();
    }

    private void sendEmojiFeedBackToWebservice(String emojiValueToSend, final String feedBack) {
        JSONObject object = new JSONObject();
        try {
            object.put("emoji", emojiValueToSend);
            object.put("emoji_date", finalDateFromPicker);
            object.put("on_your_mind", feedBack);
            // object.put("challange_id", challangeID);
            object.put("camchall_id", loginCredentials.getCAMP_CHAL_ID());
            object.put("camchall_status", loginCredentials.getCAMP_CHAL_TYPE());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new GetServiceCall(Urls.EMOJI_BASED_FEEDBACK + loginCredentials.getUserId(), GetServiceCall.TYPE_JSONOBJECT_POST, object) {
            @Override
            public void response(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    System.out.println("***TrackPageResp : " + jsonObject.toString());
                    if (jsonObject.getString("sMessage").equalsIgnoreCase("Success")) {
                        textViewFeedback.setText(feedBack);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //commonApi.dismissProgressDialog();
            }

            @Override
            public void error(VolleyError error, String errorMsg) {
                //commonApi.dismissProgressDialog();
            }
        }.call();
    }

    @Override
    public void onClickForCalculation(List trackingItemArrayList1, TrackingListItem trackingListItem, String value, String operation, String spinnerSelection, boolean isTargetPoint) {
        calculateTotalPoints(trackingItemArrayList1, trackingListItem, value, spinnerSelection, isTargetPoint);
        //  calculateTotalPointsForList(value, spinnerSelection);
        // calculatePointsForList(value, operation, spinnerSelection);
    }

    private void calculatePointsForList(String value, String operation, String spinnerSelection) {
        try {
            int totalPoint;// = Integer.parseInt(tvTotalCalculation.getText().toString());
//        if (operation.equalsIgnoreCase("p")){
            totalPoint = Integer.parseInt(value) + Integer.parseInt(spinnerSelection);
//        }else if(operation.equalsIgnoreCase("m")){
//            totalPoint-=Integer.parseInt(value);
//        }
            tvTotalCalculation.setText(String.valueOf(totalPoint));

            if (!targetPointToCheck.equals("")) {
                if (totalPoint > Integer.parseInt(targetPointToCheck)) {
                    isAwardStatus = "1";
                    if (!isDialogShown) {
                        forAwardSaveAPI("1");
                        // isDialogShown = true;
                        rlForAward.setVisibility(View.VISIBLE);
                        viewKonfettiLib.setVisibility(View.VISIBLE);
                        methodForAwardAnimation();
                        methodForAwardPopup();
                        isDialogShown = true;
                    }
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void methodForAwardAnimation() {
        viewKonfettiLib.build()
                .addColors(Color.YELLOW, Color.BLACK, Color.RED)
                .setDirection(400.0, 100.0)
                .setSpeed(3f, 8f)
                .setFadeOutEnabled(true)
                .setTimeToLive(2500L)
                .addShapes(Shape.RECT, Shape.CIRCLE)
                .addSizes(new Size(12, 5f))
                .setPosition(-50f, viewKonfettiLib.getWidth() + 50f, -50f, -50f)
                .stream(500, 4000L);
    }

    private void methodForAwardPopup() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.tracking_award_popup);
        dialog.setCancelable(false);
        Button buttonToFinish = dialog.findViewById(R.id.buttonToFinish);
        buttonToFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void forAwardSaveAPI(final String isCheckedForAward) {
        String words[] = dateToSend.split(" ");
        String monthToSend = words[1]; // first two words
        String yearToSend = words[5];
        JSONObject jsonObject = new JSONObject();
        try {
            // jsonObject.put("challange_id", challangeID);
            jsonObject.put("is_award", isCheckedForAward);
            jsonObject.put("track_date", finalDateFromPicker);
            jsonObject.put("track_month", monthToSend);
            jsonObject.put("track_year", yearToSend);
            jsonObject.put("camchall_id", loginCredentials.getCAMP_CHAL_ID());
            jsonObject.put("camchall_status", loginCredentials.getCAMP_CHAL_TYPE());
            System.out.println(" awardObject : " + jsonObject.toString());
            System.out.println(" awardUrl : " + Urls.FOR_AWARD + loginCredentials.getUserId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new GetServiceCall(Urls.FOR_AWARD + loginCredentials.getUserId(), GetServiceCall.TYPE_JSONOBJECT_POST, jsonObject) {
            @Override
            public void response(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    System.out.println("Award : " + jsonObject.toString());
                    if (jsonObject.optString("sStatus").equalsIgnoreCase("1")) {

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

    private void calculateTotalPoints(List<TrackingListItem> trackingItemArrayList1, TrackingListItem trackingListItem, String totalPoints, String spinnerSelection, boolean isTargetPoint) {
        try {
            // int totalPoint = Constant.totalPoints;
            int totalPoint = 0;
            for (int i = 0; i < trackingItemArrayList1.size(); i++) {
                if (trackingItemArrayList1.get(i).getIsChecked().equals("1")) {
                    if (!trackingItemArrayList1.get(i).getChossen_option().equals("")) {
                        Log.e("*** add 1 intrfc", String.valueOf(totalPoint));
                        //totalPoint -= Integer.parseInt(trackingListItem.getItem_point());
                        totalPoint += Integer.parseInt(trackingItemArrayList1.get(i).getChossen_option_point());
                        Log.e("***points added in " + trackingItemArrayList1.get(i).getItem(), String.valueOf(Integer.parseInt(trackingItemArrayList1.get(i).getChossen_option_point())));
                        Log.e("***tot pts on add " + trackingItemArrayList1.get(i).getItem(), String.valueOf(totalPoint));
                        //Constant.totalPoints=totalPoint;
                    } else {
                        Log.e("*** add 2 intrfc", String.valueOf(totalPoint));
                        //totalPoint -= Integer.parseInt(trackingListItem.getItem_point());
                        totalPoint += Integer.parseInt(trackingItemArrayList1.get(i).getItem_point());
                        Log.e("***points added in " + trackingItemArrayList1.get(i).getItem(), String.valueOf(Integer.parseInt(trackingItemArrayList1.get(i).getItem_point())));
                        Log.e("***tot pts on add " + trackingItemArrayList1.get(i).getItem(), String.valueOf(totalPoint));
                        //Constant.totalPoints=totalPoint;
                    }
                }
            }
            tvTotalCalculation.setText(String.valueOf(totalPoint));
            // Constant.totalPoints=totalPoint;
            // tvTotalCalculation.setText(String.valueOf(Constant.totalPoints));
            ///// for jhuri and award ///////

            if (!targetPointToCheck.equals("")) {

                if (totalPoint > Integer.parseInt(targetPointToCheck)) {
                   // isAwardStatus = "1";
                   // rlForAward.setVisibility(View.VISIBLE);
                    if (!isTargetPoint) {
                        rlForAward.setVisibility(View.VISIBLE);
                        viewKonfettiLib.setVisibility(View.VISIBLE);
                        methodForAwardAnimation();
                        methodForAwardPopup();
                        isDialogShown = true;
                        isTargetPoint = true;
                    } //else  {
                       // rlForAward.setVisibility(View.INVISIBLE);
                     else {
                        rlForAward.setVisibility(View.VISIBLE);
                        viewKonfettiLib.setVisibility(View.GONE);
                        isDialogShown = false;
                        isTargetPoint = false;
                    }
                } else {
                    rlForAward.setVisibility(View.GONE);
                    viewKonfettiLib.setVisibility(View.GONE);
                    isDialogShown = false;
                    isTargetPoint = false;
                }
            }
            ///// for jhuri and award ///////

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        //calculateTotalPointsForList(value, spinnerSelection);
    }

    public int Daybetween(String date1) {
        int numOfDays = 0, numOfDays1 = 0;
        try {
            Date challenge_start_date = new SimpleDateFormat("yyyy-MM-dd").parse(date1);
            Date today = new Date();
            long diff = today.getTime() - challenge_start_date.getTime();
            // int numOfYear = (int) ((diff / (1000 * 60 * 60 * 24)) / 365);
            numOfDays = (int) (diff / (1000 * 60 * 60 * 24));
            //int hours = (int) (diff / (1000 * 60 * 60));
            // int minutes = (int) (diff / (1000 * 60));
            //int seconds = (int) (diff / (1000));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        numOfDays = Integer.parseInt(loginCredentials.getChallengeRemainingDays()) + numOfDays;
        Log.d("***numdays", String.valueOf(numOfDays));
        //numOfDays1=numOfDays*2;
        // Log.d("***numdays1",String.valueOf(numOfDays));
        //numOfDays=numOfDays-numOfDays1;
        //Log.d("***days after calc",String.valueOf(numOfDays));
        return numOfDays;
    }
    public void setWeightStats()
    {
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
    }
    private void getDataForDashboardDetails() {

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
                    String unitToSet = jsonObject.optString("unit");
                    System.out.println(" FeedUrl : " + Urls.GET_DATA_FOR_DASHBOARD);
                    System.out.println("FeedPage : " + jsonObject.toString());
                    startDate = jsonObject.optString("Sdate");
                    endDate = jsonObject.optString("edate");
                    currentDate = jsonObject.optString("current_date");
                    participate = jsonObject.optInt("participate");
                    ch_ca_id = jsonObject.optInt("camchall_id");
                    ch_ca_type = jsonObject.optString("camchall_status");
                    group_id = jsonObject.optInt("group_id");
                    challenge_name = jsonObject.optString("name");

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
    private void historyLoading() {
        commonApi.showProgressDialog("Please wait...");
        String URL = Urls.HISTORY + loginCredentials.getUserId() + "/" + loginCredentials.getCAMP_CHAL_ID() + "/" + loginCredentials.getCAMP_CHAL_TYPE();
        System.out.println("historyResp: " + URL);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                commonApi.dismissProgressDialog();
                try {
                    JSONObject jsonObject1 = new JSONObject(response);
                    String unitToSet = jsonObject1.optString("unit");
                    startDate = jsonObject1.optString("Sdate");
                    endDate = jsonObject1.optString("edate");
                    group_id = jsonObject1.optInt("group_id");
                    ch_ca_id = jsonObject1.optInt("camchall_id");
                    ch_ca_type = jsonObject1.optString("camchall_status");
                    currentDate = jsonObject1.optString("current_date");
                    participate = jsonObject1.optInt("participate");
                    challenge_name = jsonObject1.optString("name");

                    JSONObject jsonObjectMaintainPhase = jsonObject1.getJSONObject("maintenance_phase");
                    start_body_weight = jsonObjectMaintainPhase.optString("start_body_weight");
                    try{
                        Log.d("startdate",startDate);
                        String dtarr[] = startDate.split("-");
                        //Log.d("startdate",d.toString());
                        Calendar strtC = Calendar.getInstance();
                        strtC.set(Calendar.YEAR,Integer.parseInt(dtarr[0]));
                        strtC.set(Calendar.MONTH, Integer.parseInt(dtarr[1]) - 1);
                        strtC.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dtarr[2]));
                        calendarView.setMinimumDate(strtC);
                        Log.d("startdate",strtC.toString());

                        String dtarr2[] = endDate.split("-");
                        Calendar endC = Calendar.getInstance();
                        endC.set(Calendar.YEAR,Integer.parseInt(dtarr2[0]));
                        endC.set(Calendar.MONTH, Integer.parseInt(dtarr2[1]) - 1);
                        endC.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dtarr2[2]));
                        calendarView.setMaximumDate(endC);
                        Log.d("startdate",endC.toString());

                        startCalender = strtC;
                        endCalender = endC;

                    }catch (Exception e){

                        Log.d("dateerror",e.getMessage());
                    }

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


    public void showParticipateDialog(final String parameterType, final String titleText, final String subTitleText, final String urlKey) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity(), R.style.myDialog);
        alertDialogBuilder.setMessage("You first have to participate in the" + " " + challenge_name + " " +
                "then you will be able to update the weights. To Participate in the current challenge, you can do that from the Feed Tab or from Active Challenges.");
        alertDialogBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int arg1) {
                /*if (participate == 0) {
                    participateNow(ch_ca_type, ch_ca_id, parameterType, titleText, subTitleText, urlKey, group_id);

                } else {
                    openDialogForValue1(parameterType, titleText, subTitleText, urlKey);
                }
                isAlreadyOpened = true;*/
             dialog.dismiss();
            }
        });

       /* alertDialogBuilder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });*/
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    private void openDialogForValue1(String value, String titleText, String subTitleText, final String urlKeyToSend) {
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
    public void participateNow(String type, int camp_chall_id, final String parameterType, final String titleText, final String subTitleText, final String urlKey, int id_group) {
        commonApi.showProgressDialog("Please Wait...");
        JSONObject object = new JSONObject();
        try {
            object.put("user_id", loginCredentials.getUserId());
            object.put("camchall_id", camp_chall_id);
            object.put("camchall_status", type);
            object.put("group_id", id_group);
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
                openDialogForValue1(parameterType, titleText, subTitleText, urlKey);
            }

            @Override
            public void error(VolleyError error, String errorMsg) {
                commonApi.dismissProgressDialog();
            }
        }.call();
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

    public void getBanner() {
        Calendar calendar = Calendar.getInstance();
        java.text.SimpleDateFormat d = new java.text.SimpleDateFormat("MM");
        String month = (d.format(calendar.getTime()));
        /*if (String.valueOf(month).trim().length() < 2) {
            month = Integer.parseInt(String.valueOf(Integer.parseInt("0" + calendar.get(Calendar.MONTH))));
        } else {
            month = Integer.parseInt(String.valueOf(calendar.get(Calendar.MONTH)));
        }*/
        java.text.SimpleDateFormat k = new java.text.SimpleDateFormat("dd");
        String days = (k.format(calendar.getTime()));
        /*if (String.valueOf(days).trim().length() < 2) {
            days = Integer.parseInt("0" + calendar.get(Calendar.DAY_OF_MONTH));
        } else {
            days = calendar.get(Calendar.DAY_OF_MONTH);
        }*/
        int year = calendar.get(Calendar.YEAR);

            dateSelected = year + "-" + month + "-" + days;

        dateToSend = dateSelected;
        Log.d("***selected date", dateToSend);
        finalDateFromPicker = dateSelected.replace("-", "");
        loginCredentials.setCurrentDate(finalDateFromPicker);
        dateChange = dateChange + 1;
       // Log.d("Final",finalDateFromPicker);
        if (dateChange == 1) {
            getDataForTrackingDetails(finalDateFromPicker);
        } else {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date strDate = sdf.parse(dateSelected);
                if (new Date().after(strDate)) {
                    Log.d("***right date", "execution  allowed");
                    feel_layout.setVisibility(View.VISIBLE);
                    isTargetPoint = true;
                    getDataForTrackingDetails(finalDateFromPicker);

                } else {
                    Log.d("***wrong date", "execution not allowed");
                    rvForTrackingItems.setAdapter(null);
                    tvTotalCalculation.setText("NA");
                    feel_layout.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void setCalendarEvents(ArrayList<CalendarEventItem> calenderItemArrayList)
    {
    //calendarView.setEvents(null);
            List<EventDay> events = new ArrayList<>();
            //Calendar calendar1 = Calendar.getInstance();
            for (int d = 0; d < calenderItemArrayList.size(); d++) {
                String date = calenderItemArrayList.get(d).getDate();
                //String date="2021-08-03";
                String parts[] = date.split("-");

                int day = Integer.parseInt(parts[2]);
                int month = Integer.parseInt(parts[1]);
                int year = Integer.parseInt(parts[0]);

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month - 1);
                calendar.set(Calendar.DAY_OF_MONTH, day);

                events.add(new EventDay(calendar, getColorcode(calenderItemArrayList.get(d).getIdList())));
                calendarView.setEvents(events);
            }

/*        Calendar calendar2 = Calendar.getInstance();
        calendar2.add(Calendar.DAY_OF_MONTH, 10);
        events.add(new EventDay(calendar2, R.drawable.sample_icon_3, Color.parseColor("#228B22")));*/

 /*       Calendar calendar3 = Calendar.getInstance();
        calendar3.add(Calendar.DAY_OF_MONTH, 7);
        events.add(new EventDay(calendar3, DrawableUtils.getThreeDots(this)));

        Calendar calendar4 = Calendar.getInstance();
        calendar4.add(Calendar.DAY_OF_MONTH, 13);
        events.add(new EventDay(calendar4, DrawableUtils.getThreeDots(this)));*/
        }

    public void showChallengeDates(String yearMonth){
        if (!commonApi.isInternetAvailable(getActivity())) {
            commonApi.showDialog(getActivity(), Constant.NO_INTERNET, "", getResources().getColor(R.color.black), false);
            return;
        }
      //  commonApi.showProgressDialog("Please wait..");
        JSONObject obj = new JSONObject();
        try {
            Calendar rightNow = Calendar.getInstance();
            java.text.SimpleDateFormat c = new java.text.SimpleDateFormat("MM");
            obj.put("user_id", loginCredentials.getUserId());
            obj.put("YearMonth", yearMonth);
            System.out.println(" datesObject : " + obj.toString());
            System.out.println(" datesUrl : " + Urls.SHOW_DATES);
        } catch (Exception e) {
            e.printStackTrace();
        }
        new GetServiceCall(Urls.SHOW_DATES, GetServiceCall.TYPE_JSONOBJECT_POST, obj) {
            @Override
            public void response(String response) {
                try {
                    StaticValues.calenderEventItemArrayList.clear();
                    Log.d("datesResp:", response);
                    JSONObject jsonObject = new JSONObject(response);
                    //calenderEventItemArrayList = new ArrayList<>();
                    //if (jsonObject.optString("sStatus").equalsIgnoreCase("1")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("calender");
                       /* ArrayList<Integer> idList = new ArrayList<>();
                        ArrayList<String> dateList = new ArrayList<>();*/
                        for (int h = 0; h < jsonArray.length(); h++) {
                            JSONObject jsonObject2 = jsonArray.getJSONObject(h);
                            CalendarEventItem calenderItem = new CalendarEventItem();
                            calenderItem.setDate(jsonObject2.getString("date"));
                            ArrayList<String> finalItemsList=new ArrayList<>();
                            //if(!jsonObject2.optString("ids").trim().equals("")) {
                                calenderItem.setIdString(jsonObject2.optString("ids"));
                          //  }
                            if(jsonObject2.getString("ids").contains(",")) {
                                List<String> items = Arrays.asList(jsonObject2.getString("ids").split("\\s*,\\s*"));
                                finalItemsList.addAll(items);
                            } else {
                                finalItemsList.add(jsonObject2.getString("ids"));
                            }
                            calenderItem.setIdList(finalItemsList);
                            if(!calenderItem.getIdString().trim().equals("")) {
                                StaticValues.calenderEventItemArrayList.add(calenderItem);
                            }

                         //   idList.add(jsonObject2.optInt("ids"));
                          //  dateList.add(jsonObject2.optString("date"));
                        }
               if(StaticValues.calenderEventItemArrayList.size() > 0) {
              setCalendarEvents(StaticValues.calenderEventItemArrayList);
                     }                   /*} else {
                      //  commonApi.showDialog(getActivity(), jsonObject.getString("sMessage"), "", getResources().getColor(R.color.black), false);
                    }*/
                } catch (Exception e) {
                    e.printStackTrace();
                }
            //    commonApi.dismissProgressDialog();
            }

            @Override
            public void error(VolleyError error, String errorMsg) {
              //  commonApi.dismissProgressDialog();
            }
        }.call();
    }

    public Drawable getColorcode(ArrayList<String> idList) {
        Drawable drawable = null;
        if(idList.size()==1) {
            if (idList.contains("1183")) {
                drawable = getResources().getDrawable(R.drawable.green);
            } else if (idList.contains("1184")) {
                drawable = getResources().getDrawable(R.drawable.white);
            } else if (idList.contains("1185")) {
                drawable = getResources().getDrawable(R.drawable.black);
            } else if (idList.contains("1186")) {
                drawable = getResources().getDrawable(R.drawable.grey);
            }
        }
        if(idList.size()==2) {
         if (idList.contains("1183") && idList.contains("1184")) {
             drawable = getResources().getDrawable(R.drawable.grn_wht);
            } else if (idList.contains("1183") && idList.contains("1185")) {
              drawable = getResources().getDrawable(R.drawable.grn_blk);
            } else if (idList.contains("1183") && idList.contains("1186")) {
             drawable = getResources().getDrawable(R.drawable.grn_gry);
            } else if (idList.contains("1184") && idList.contains("1185")) {
             drawable = getResources().getDrawable(R.drawable.wht_blk);
            } else if (idList.contains("1184") && idList.contains("1186")) {
             drawable = getResources().getDrawable(R.drawable.wht_gry);
            } else if (idList.contains("1185") && idList.contains("1186")) {
             drawable = getResources().getDrawable(R.drawable.blk_gry);
            }
        }
        if(idList.size()==3) {
            if (idList.contains("1183") && idList.contains("1184") && idList.contains("1185")) {
                drawable = getResources().getDrawable(R.drawable.grn_wht_blk);
            } else if (idList.contains("1184") && idList.contains("1185") && idList.contains("1186")) {
                drawable = getResources().getDrawable(R.drawable.wht_blk_gry);
            } else if (idList.contains("1183") && idList.contains("1184") && idList.contains("1186")) {
                drawable = getResources().getDrawable(R.drawable.grn_wht_gry);
            } else if (idList.contains("1183") && idList.contains("1185") && idList.contains("1186")) {
                drawable = getResources().getDrawable(R.drawable.grn_blk_gry);
            }
        }
        if(idList.size()==4) {
            if (idList.contains("1183") && idList.contains("1184") && idList.contains("1185") && idList.contains("1186")) {
                drawable = getResources().getDrawable(R.drawable.all);
            }
        }
        return drawable;
    }

    @Override
    public void onResume() {
        super.onResume();
        String onlyYearMonth = getYearMonth(calendarView.getCurrentPageDate().getTimeInMillis(), dateTimeFormat);
        showChallengeDates(onlyYearMonth);
    }

    public static String getYearMonth(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        String date = formatter.format(calendar.getTime());
        String[] strArray = date.split("-");
        System.out.println(strArray[0]);
        System.out.println(strArray[1]);

        /*Pattern p = Pattern.compile(".*-\\s*(.*)");
        Matcher m = p.matcher(date);

        if (m.find())
            System.out.println(m.group(0));*/

        return strArray[0] + "-" + strArray[1];
    }

   /* @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        showChallengeDates();
    }*/
}
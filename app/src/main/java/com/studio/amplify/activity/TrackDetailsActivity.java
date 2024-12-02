package com.studio.amplify.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;
import com.github.jhonnyx2012.horizontalpicker.HorizontalPicker;
import com.harrywhewell.scrolldatepicker.DayScrollDatePicker;
import com.harrywhewell.scrolldatepicker.OnDateSelectedListener;
import com.studio.amplify.R;
import com.studio.amplify.model.CheckboxItem;
import com.studio.amplify.util.BaseActivity;
import com.studio.amplify.util.CommonApi;
import com.studio.amplify.util.Constant;
import com.studio.amplify.util.StaticValues;
import com.studio.amplify.util.Urls;
import com.studio.amplify.volleyparser.GetServiceCall;
import com.vivekkaushik.datepicker.DatePickerTimeline;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.Nullable;
import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class TrackDetailsActivity extends BaseActivity /*implements DatePickerListener*/ {
    private CommonApi commonApi;
    ImageView imgBackArrow, imgGoal, imgReformer, imgCardio, imgWorkout,imgClass, imgClassSel;
    Switch mySwitchId;

    //  HorizontalPicker horizontalPicker;
    String dateToSend, finalDateFromPicker;
    int dateChange = 0;

    TextView tvTrackTitle1, tvTrackTitle2, tvTrackTitle3, tvTrackTitle4, tvTrackTitle5, btn_updte;
    ArrayList<CheckboxItem> chechboxItemArrayList = new ArrayList<>();
    boolean checkGoalImageStatus = false;
    boolean checkReformerImageStatus = false;
    boolean checkCardioImageStatus = false;
    boolean checkWorkoutImageStatus = false;
    String check_goals ="";
    DateTimeFormatter formatter;
    DateTime dt;
    int checkedItemsCount = 0;
    String year, month, day, dateTimeFormat = "yyyy-MM-dd";
    String idString, isCompleted="0";
    HorizontalCalendarView calendarView;
    boolean update = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        commonApi = new CommonApi(TrackDetailsActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_details);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        imgBackArrow = findViewById(R.id.imgBackArrow);
       // horizontalPicker = findViewById(R.id.horizontalPicker);
        tvTrackTitle1 = findViewById(R.id.tvTrackTitle1);
        tvTrackTitle2 = findViewById(R.id.tvTrackTitle2);
        tvTrackTitle3 = findViewById(R.id.tvTrackTitle3);
        tvTrackTitle4 = findViewById(R.id.tvTrackTitle4);
        tvTrackTitle5 = findViewById(R.id.tvTrackTitle5);
        btn_updte     = findViewById(R.id.btn_updte);
      //  calendarView = findViewById(R.id.calendarView);

        imgGoal = findViewById(R.id.imgGoal);
        imgReformer = findViewById(R.id.imgReformer);
        imgCardio = findViewById(R.id.imgCardio);
        imgWorkout = findViewById(R.id.imgWorkout);

        mySwitchId  = findViewById(R.id.mySwitchId);

        Bundle cal = getIntent().getExtras();
        if (cal != null) {
            year = cal.getString("year");
            month = cal.getString("month");
            day = cal.getString("day");
            idString = cal.getString("idString");
            finalDateFromPicker = cal.getString("date");
        }

       Calendar startDate = Calendar.getInstance();
       // startDate.add(Calendar.MONTH, -1);
        //Calendar calendar = Calendar.getInstance();
        startDate.set(Calendar.YEAR,Integer.parseInt(year) );
        startDate.set(Calendar.MONTH, Integer.parseInt(month)-1);
        startDate.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
        Log.d("startdate",getDate(startDate.getTimeInMillis(),dateTimeFormat));

        Calendar endDate = Calendar.getInstance();
        endDate.set(Calendar.YEAR,Integer.parseInt(year)+1 );
        endDate.set(Calendar.MONTH, Integer.parseInt(month)-1);
        endDate.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
        Log.d("enddate",getDate(endDate.getTimeInMillis(),dateTimeFormat));
        //endDate.add(Calendar.MONTH, 2);

        DatePickerTimeline picker = findViewById(R.id.calendarView);
        picker.setInitialDate(Integer.parseInt(year),Integer.parseInt(month)-1,Integer.parseInt(day));

        picker.setActiveDate(startDate);
        picker.setOnDateSelectedListener(new com.vivekkaushik.datepicker.OnDateSelectedListener() {
            @Override
            public void onDateSelected(int i, int i1, int i2, int i3) {
                Log.d("selected"," "+i+","+i1+","+i2);
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(Calendar.YEAR,i );
                selectedDate.set(Calendar.MONTH, i1);
                selectedDate.set(Calendar.DAY_OF_MONTH, i2);
                //Log.d("selected",getDate(selectedDate.getTimeInMillis(),dateTimeFormat));

                finalDateFromPicker = getDate(selectedDate.getTimeInMillis(), dateTimeFormat);
                Log.d("new picker","selected date - "+finalDateFromPicker);
                getTodaysClass(selectedDate.getTimeInMillis(), dateTimeFormat);
                getTrackItem(getIdStringForNewDate());
                //getTodaysClass(Calendar.getInstance().getTimeInMillis(), dateTimeFormat);
            }

            @Override
            public void onDisabledDateSelected(int i, int i1, int i2, int i3, boolean b) {

            }
        });

        // initialize it and attach a listener
        //picker.setStartDate(Integer.parseInt(day),Integer.parseInt(month),Integer.parseInt(year));
        //picker.setEndDate(Integer.parseInt(day),Integer.parseInt(month),Integer.parseInt(year)+1);
       /* HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .build();

        */

       finalDateFromPicker = getDate(startDate.getTimeInMillis(), dateTimeFormat);
        getTodaysClass(startDate.getTimeInMillis(), dateTimeFormat);
        getTrackItem(idString);


       /* horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {

                Log.d("startdate","ondateselected "+position);
                finalDateFromPicker = getDate(horizontalCalendar.getDateAt(position+1).getTimeInMillis(), dateTimeFormat);
                getTrackItem(getIdStringForNewDate());
                getTodaysClass(Calendar.getInstance().getTimeInMillis(), dateTimeFormat);
            }


            @Override
            public void onCalendarScroll(HorizontalCalendarView calendarView, int dx, int dy) {

                Log.d("startdate","scroll "+dx+","+dy);
            }

            @Override
            public boolean onDateLongClicked(Calendar date, int position) {
                Log.d("startdate","ondatelongclicked");
                return true;
            }
        });

        */

            /*horizontalPicker.setListener(this)
                    //.setDays(Daybetween(loginCredentials.getChallengeStartDate()))
                    .setDays(365)
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


            try {
                 formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
               // DateTime dt = formatter.parseDateTime(loginCredentials.getCurrentDateFromFeed());
                 dt = formatter.parseDateTime(year + "-" + month + "-" + day);
               String newdt=new DateTime().toString();
                Log.d("***SelectedDate", dt.toString());
                Log.d("***CurrentDate", newdt);
                horizontalPicker.setBackgroundColor(Color.WHITE);
                finalDateFromPicker = dt.toLocalDate().toString();
                //String[] separated = finalDateFromPicker.split("T");
               // finalDateFromPicker = separated[0];
                horizontalPicker.setDate(dt);
                //horizontalPicker.setDate(dt);
            } catch (Exception e) {
                e.printStackTrace();
            }*/


            //getTrackItem();

            imgGoal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!checkGoalImageStatus) {
                        checkGoalImageStatus = true;
                        setCheckedValues(checkGoalImageStatus, 3);
                        imgGoal.setImageResource(R.drawable.enable_grn);

                    } else {
                        checkGoalImageStatus = false;
                        setCheckedValues(checkGoalImageStatus, 3);
                        imgGoal.setImageResource(R.drawable.disable_grn);
                    }
                }
            });

            imgReformer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!checkReformerImageStatus) {
                        checkReformerImageStatus = true;
                        setCheckedValues(checkReformerImageStatus, 2);
                        imgReformer.setImageResource(R.drawable.enable_wht);
                    } else {
                        checkReformerImageStatus = false;
                        setCheckedValues(checkReformerImageStatus, 2);
                        imgReformer.setImageResource(R.drawable.disable_wht);
                    }
                }
            });

            imgCardio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!checkCardioImageStatus) {
                        checkCardioImageStatus = true;
                        setCheckedValues(checkCardioImageStatus, 1);
                        imgCardio.setImageResource(R.drawable.enable_blk);
                    } else {
                        checkCardioImageStatus = false;
                        setCheckedValues(checkCardioImageStatus, 1);
                        imgCardio.setImageResource(R.drawable.disable_blk);
                    }
                }
            });

            imgWorkout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!checkWorkoutImageStatus) {
                        checkWorkoutImageStatus = true;
                        setCheckedValues(checkWorkoutImageStatus, 0);
                        imgWorkout.setImageResource(R.drawable.enable_gry);
                    } else {
                        checkWorkoutImageStatus = false;
                        setCheckedValues(checkWorkoutImageStatus, 0);
                        imgWorkout.setImageResource(R.drawable.disable_gry);
                    }

                }
            });



           imgBackArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    update = true;
                    tickBoxes();
                    //onBackPressed();
                    //commonApi.finishActivity(TrackDetailsActivity.this);

                }
            });

        btn_updte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update = true;
                tickBoxes();
            }
        });

        mySwitchId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update = false;
                tickBoxes();
            }
        });

    }

    private void tickBoxes() {
        checkedItemsCount=0;
        check_goals = "";

        for(int x=0;x<chechboxItemArrayList.size();x++)//get the no. of items checked
        {
            if(chechboxItemArrayList.get(x).isChecked())
            {
                checkedItemsCount=checkedItemsCount+1;
            }
        }

            for (int z = 0; z < chechboxItemArrayList.size(); z++) {
                if(checkedItemsCount==1) {
                    if (chechboxItemArrayList.get(z).isChecked()) {
                        check_goals = String.valueOf(chechboxItemArrayList.get(z).getId());
                    }
                }
                else if(checkedItemsCount>1) {
                    if (chechboxItemArrayList.get(z).isChecked()) {
                        check_goals = check_goals + chechboxItemArrayList.get(z).getId() + ",";
                    }
                }
                }
            /*int index = check_goals.lastIndexOf(",");
            check_goals = check_goals.substring(0, index);*/
           check_goals = check_goals.replaceAll(", $", "");
        saveGoals();
    }

    private String getIdStringForNewDate() {
        String  str_track_id = "";

        for (int z = 0; z < StaticValues.calenderEventItemArrayList.size(); z++) {
                if (StaticValues.calenderEventItemArrayList.get(z).getDate().equals(finalDateFromPicker)) {
                    str_track_id = String.valueOf(StaticValues.calenderEventItemArrayList.get(z).getIdString());
                }

        }
        return str_track_id;
    }


    /*@Override
        public void onDateSelected(DateTime dateSelected){
            dateToSend = String.valueOf(dateSelected.toDate());
            Log.d("***selected date", dateToSend);
            finalDateFromPicker = dateSelected.toLocalDate().toString();
            String[] separated = finalDateFromPicker.split("T");
            finalDateFromPicker = separated[0];
          //  String new1 = finalDateFromPicker.replace(str1,"T");
          //  loginCredentials.setCurrentDate(finalDateFromPicker);
           dateChange = dateChange + 1;
            if (dateChange == 1) {
                getTrackItem(idString);
            } else {
                getTrackItem(getIdStringForNewDate());
            }
            }*/
               /* try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date strDate = sdf.parse(dateSelected.toLocalDate().toString());
                    if (new Date().after(strDate)) {
                        Log.d("***right date", "execution  allowed");
                        getTrackItem();

                    } else {
                        Log.d("***wrong date", "execution not allowed");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }*//*
            //}*/



        private void setCheckboxVisibility() {
            try {
                if (chechboxItemArrayList.size() == 1) {
                    tvTrackTitle1.setText(chechboxItemArrayList.get(0).getTitle());
                    imgWorkout.setVisibility(View.VISIBLE);
                    tvTrackTitle2.setVisibility(View.GONE);
                    tvTrackTitle3.setVisibility(View.GONE);
                    tvTrackTitle4.setVisibility(View.GONE);
                    tvTrackTitle5.setVisibility(View.GONE);
                    imgReformer.setVisibility(View.GONE);
                    imgCardio.setVisibility(View.GONE);
                    imgGoal.setVisibility(View.GONE);

                } else if (chechboxItemArrayList.size() == 2) {
                    tvTrackTitle1.setText(chechboxItemArrayList.get(0).getTitle());
                    tvTrackTitle2.setText(chechboxItemArrayList.get(1).getTitle());
                    imgWorkout.setVisibility(View.VISIBLE);
                    imgCardio.setVisibility(View.VISIBLE);
                    tvTrackTitle3.setVisibility(View.GONE);
                    tvTrackTitle4.setVisibility(View.GONE);
                    tvTrackTitle5.setVisibility(View.GONE);
                    imgReformer.setVisibility(View.GONE);
                    imgGoal.setVisibility(View.GONE);

                } else if (chechboxItemArrayList.size() == 3) {
                    tvTrackTitle1.setText(chechboxItemArrayList.get(0).getTitle());
                    tvTrackTitle2.setText(chechboxItemArrayList.get(1).getTitle());
                    tvTrackTitle3.setText(chechboxItemArrayList.get(2).getTitle());
                    imgWorkout.setVisibility(View.VISIBLE);
                    imgReformer.setVisibility(View.VISIBLE);
                    imgCardio.setVisibility(View.VISIBLE);
                    tvTrackTitle4.setVisibility(View.GONE);
                    imgGoal.setVisibility(View.GONE);

                } else if (chechboxItemArrayList.size() == 4) {
                    tvTrackTitle1.setText(chechboxItemArrayList.get(0).getTitle());
                    tvTrackTitle2.setText(chechboxItemArrayList.get(1).getTitle());
                    tvTrackTitle3.setText(chechboxItemArrayList.get(2).getTitle());
                    tvTrackTitle4.setText(chechboxItemArrayList.get(3).getTitle());
                    imgGoal.setVisibility(View.VISIBLE);
                    imgReformer.setVisibility(View.VISIBLE);
                    imgCardio.setVisibility(View.VISIBLE);
                    imgWorkout.setVisibility(View.VISIBLE);
                } else if (chechboxItemArrayList.size() == 5) {
                    tvTrackTitle1.setText(chechboxItemArrayList.get(0).getTitle());
                    tvTrackTitle2.setText(chechboxItemArrayList.get(1).getTitle());
                    tvTrackTitle3.setText(chechboxItemArrayList.get(2).getTitle());
                    tvTrackTitle4.setText(chechboxItemArrayList.get(3).getTitle());
                    imgGoal.setVisibility(View.VISIBLE);
                    imgReformer.setVisibility(View.VISIBLE);
                    imgCardio.setVisibility(View.VISIBLE);
                    imgWorkout.setVisibility(View.VISIBLE);

                }
                tvTrackTitle5.setVisibility(View.VISIBLE);
                imgClass.setVisibility(View.VISIBLE);

            } catch (Exception e) {
                e.printStackTrace();

            }
        }


        private void getTrackItem (String finalIdString) {
            if (!commonApi.isInternetAvailable(this)) {
                Toast.makeText(this, Constant.NO_INTERNET, Toast.LENGTH_SHORT).show();
                return;
            }
            StringRequest stringRequest = new StringRequest(Request.Method.GET, Urls.TITLE_LIST, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject obj = new JSONObject();
                        System.out.println(" getCheckObject : " + obj.toString());
                        System.out.println(" getCheckUrl : " + Urls.TITLE_LIST);

                        Log.d("getCheckResp:", response);
                        chechboxItemArrayList.clear();
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("lists");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            CheckboxItem checkboxItem = new CheckboxItem();
                            checkboxItem.setId(jsonObject1.optInt("id"));
                           // finalIdString.replace(","," , ");
                           if(finalIdString.contains(String.valueOf(jsonObject1.optInt("id"))))    {
                            checkboxItem.setChecked(true);
                            setCheckedValues(String.valueOf(jsonObject1.optInt("id")),true);
                           } else {
                               checkboxItem.setChecked(false);
                               setCheckedValues(String.valueOf(jsonObject1.optInt("id")),false);
                           }
                            checkboxItem.setTitle(jsonObject1.optString("title"));
                            chechboxItemArrayList.add(checkboxItem);
                        }
                        setCheckboxVisibility();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(stringRequest);
        }

        private void getTodaysClass (long milliSeconds, String dateFormat) {
        if (!commonApi.isInternetAvailable(this)) {
            Toast.makeText(this, Constant.NO_INTERNET, Toast.LENGTH_SHORT).show();
            return;
        }
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

            // Create a calendar object that will convert the date and time value in milliseconds to date.
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(milliSeconds);
            String date = formatter.format(calendar.getTime());
            String[] strArray = date.split("-");

            StringRequest stringRequest = new StringRequest(Request.Method.GET, Urls.GET_TODAYS_CLASS+loginCredentials.getUserId()+"/"+strArray[0]+strArray[1]+strArray[2]+"/"+loginCredentials.getCAMP_CHAL_ID() + "/" +loginCredentials.getCAMP_CHAL_TYPE(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("res***",response);
                    String classComplete = jsonObject.getString("classcomplte");
                    if(classComplete.equals("1")){
                        mySwitchId.setChecked(true);
                        isCompleted ="1";
                    } else {
                        mySwitchId.setChecked(false);
                        isCompleted ="0";
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }



    public void saveGoals() {
        if (!commonApi.isInternetAvailable(TrackDetailsActivity.this)) {
            commonApi.showDialog(TrackDetailsActivity.this, Constant.NO_INTERNET, "", getResources().getColor(R.color.black), false);
            return;
        }
        if(mySwitchId.isChecked()){
            isCompleted="1";
        }
        else {
            isCompleted="0";
        }
       // commonApi.showProgressDialog("Please wait..");
        Log.d("66666",finalDateFromPicker);
        JSONObject obj = new JSONObject();
        try {
            obj.put("user_id", loginCredentials.getUserId());
            obj.put("date", finalDateFromPicker);
            obj.put("myclassesgoals", check_goals);
            obj.put("camchall_id", loginCredentials.getCAMP_CHAL_ID());
            obj.put("camchall_status", loginCredentials.getCAMP_CHAL_TYPE());
            obj.put("is_completed", isCompleted);
            System.out.println(" goalsSaveObject : " + obj.toString());
            System.out.println(" goalsSaveUrl : " + Urls.UPDATE_GOALS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        new GetServiceCall(Urls.UPDATE_GOALS, GetServiceCall.TYPE_JSONOBJECT_POST, obj) {
            @Override
            public void response(String response) {
                try {
                    Log.d("updateGoalsResp:", response);
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("sStatus").equalsIgnoreCase("1")) {
                        imgGoal.setVisibility(View.VISIBLE);
                        imgCardio.setVisibility(View.VISIBLE);
                        imgReformer.setVisibility(View.VISIBLE);
                        imgWorkout.setVisibility(View.VISIBLE);
                        if(update) {
                            commonApi.showDialog(TrackDetailsActivity.this, jsonObject.getString("sMessage"), "", getResources().getColor(R.color.black), true);
                            commonApi.finishActivity(TrackDetailsActivity.this);
                        }
                    } else {
                        commonApi.showDialog(TrackDetailsActivity.this, jsonObject.getString("sMessage"), "", getResources().getColor(R.color.black), false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
              //  commonApi.dismissProgressDialog();
            }

            @Override
            public void error(VolleyError error, String errorMsg) {
              //  commonApi.dismissProgressDialog();
            }
        }.call();
    }

    public void setCheckedValues(boolean isChecked, int indexSelected) {

        chechboxItemArrayList.get(indexSelected).setChecked(isChecked);
        //Toast.makeText(getActivity(),selectedItem.getTitle()+ " "+ selectedItem.getSelectedIndex() +" set"+ " "+ selectedItem.isChecked(),Toast.LENGTH_LONG).show();
        /*if (isChecked) {
            checkedItemsCount = checkedItemsCount + 1;
        } else {
            checkedItemsCount = checkedItemsCount - 1;
        }*/
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        update = true;
        tickBoxes();

    }

   public void setCheckedValues(String id,boolean isChecked) {
       if(id.equals("1183")) {
           if(isChecked) {
               imgGoal.setImageResource(R.drawable.enable_grn);
           }
           else
           {
               imgGoal.setImageResource(R.drawable.disable_grn);
           }
       } else if(id.equals("1184")) {
           if(isChecked) {
               imgReformer.setImageResource(R.drawable.enable_wht);
           }
           else
           {
               imgReformer.setImageResource(R.drawable.disable_wht);
           }
       } else if(id.equals("1185")) {
           if(isChecked) {
               imgCardio.setImageResource(R.drawable.enable_blk);
           }
           else
           {
               imgCardio.setImageResource(R.drawable.disable_blk);
           }
       } else if(id.equals("1186")) {
           if(isChecked) {
               imgWorkout.setImageResource(R.drawable.enable_gry);
           }
           else
           {
               imgWorkout.setImageResource(R.drawable.disable_gry);
           }
       }
   }

    public static String getDate(long milliSeconds, String dateFormat)
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
        System.out.println(strArray[2]);


        return strArray[0] + "-" + strArray[1] + "-" + strArray[2];
    }

}


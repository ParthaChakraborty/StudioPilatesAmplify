package com.studio.amplify.fragment;

import android.app.DatePickerDialog;
import android.app.FragmentTransaction;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.studio.amplify.R;
import com.studio.amplify.activity.ShoppingListActivity;
import com.studio.amplify.adapter.MealsListAdapter;
import com.studio.amplify.model.MealListItem;
import com.studio.amplify.util.BaseFragment;
import com.studio.amplify.util.CommonApi;
import com.studio.amplify.util.Urls;
import com.studio.amplify.volleyparser.GetServiceCall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MealsFragment extends BaseFragment {

    //banner
    @BindView(R.id.ivBannerImage)
    ImageView ivBannerImage;
    @BindView(R.id.mealPlanText)
    TextView mealPlanText;
    //banner

    //radio button
    @BindView(R.id.toggle)
    RadioGroup toggle;
    @BindView(R.id.radioButtonMainStream)
    RadioButton radioButtonMainStream;
    @BindView(R.id.radioButtonVegetarian)
    RadioButton radioButtonVegetarian;
    //radio button

    @BindView(R.id.tvForDateMeals)
    TextView tvForDateMeals;
    @BindView(R.id.mealPlanTextBlankValue)
    TextView mealPlanTextBlankValue;
    @BindView(R.id.rvForMealsList)
    RecyclerView rvForMealsList;
    @BindView(R.id.scrollView)
    ScrollView scrollView;

    @BindView(R.id.ivMealsCalender)
    ImageView ivMealsCalender;
    @BindView(R.id.tvForMealsDate)
    TextView tvForMealsDate;
    @BindView(R.id.cal)
    CalendarView cal;
    @BindView(R.id.imgBack)
    ImageView imgBack;

    Unbinder unbinder;
    String dietPlanType = "Mainstream";
    ArrayList<MealListItem> mealListingItemArrayList;

    MealsListAdapter mealsListAdapter;
    // For date
    String dateToSendToWebService;
    Calendar calenderObject;
    String dayOfTheWeek, day, monthString, monthNumber, year, wholeDate;

    private CommonApi commonApi;
    private int daysToAdd = 1;//adding date
    private int daysToSubtract = 1;//subtracting date
    //For date
    String callType = "";
    DatePicker picker;
    DatePickerDialog datePickerDialog;
    Calendar myCalendar = Calendar.getInstance();
    int daySelected, monthSelected, yearSelected;
    int days, month;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.meals_fragment, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this, layout);
        unbinder = ButterKnife.bind(this, layout);
        commonApi = new CommonApi(getActivity());

        setHasOptionsMenu(true);
        checkingDietPlanFromUserData();

        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        toggle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButtonVegetarian) {
                    dietPlanType = "vegetarian";
                    getDataForMealsListing(dietPlanType/*, ""*/);
                } else {
                    dietPlanType = "mainstream";
                    getDataForMealsListing(dietPlanType/*, ""*/);
                }
            }
        });

        if (loginCredentials.getUserGender().equalsIgnoreCase("F")) {
            mealPlanTextBlankValue.setText(R.string.global_female);
        } else if (loginCredentials.getUserGender().equalsIgnoreCase("M")) {
            mealPlanTextBlankValue.setText(R.string.global_male);
        } else if(loginCredentials.getUserGender().equalsIgnoreCase("U")) {
            mealPlanTextBlankValue.setText(R.string.other);
        } else if(loginCredentials.getUserGender().equalsIgnoreCase("O")) {
            mealPlanTextBlankValue.setText(R.string.edit_other);
        } else if(loginCredentials.getUserGender().equalsIgnoreCase("P")) {
            mealPlanTextBlankValue.setText(R.string.prefer);
        }

        mealListingItemArrayList = new ArrayList<>();
        rvForMealsList.setNestedScrollingEnabled(false);

        //DATE CALCULATION
        calenderObject = Calendar.getInstance();
        getcompleteDate();
       // callType = "firstTime";
     //   getDataForMealsListing(dietPlanType/*, callType*/);

        //DATE CALCULATION
        ivMealsCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (String.valueOf(month).trim().length() < 2) {
                     month = Integer.parseInt("0" + myCalendar.get(Calendar.MONTH));
                } else {
                     month = myCalendar.get(Calendar.MONTH);
                }

                if (String.valueOf(days).trim().length() < 2) {
                    days = Integer.parseInt("0" + myCalendar.get(Calendar.DAY_OF_MONTH));
                } else {
                    days = myCalendar.get(Calendar.DAY_OF_MONTH);
                }
                int year = myCalendar.get(Calendar.YEAR);
                datePickerDialog = new DatePickerDialog(getActivity(), R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tvForMealsDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                        if (String.valueOf(month).trim().length() < 2) {
                                dateToSendToWebService = String.valueOf(year) + String.valueOf("0" + (month + 1)) + String.valueOf(dayOfMonth);
                                if(String.valueOf(dayOfMonth).trim().length() < 2) {
                                    dateToSendToWebService = String.valueOf(year) + String.valueOf((month + 1)) + String.valueOf("0" + dayOfMonth);
                                } else {
                                    dateToSendToWebService = String.valueOf(year)  + String.valueOf("0" + (month + 1)) + String.valueOf(dayOfMonth);
                                }
                        } else {
                            dateToSendToWebService = String.valueOf(year)  + String.valueOf((month + 1)) + String.valueOf(dayOfMonth);

                        }
                        mealListingItemArrayList.clear();
                       // mealsListAdapter.notifyDataSetChanged();
                        getDataForMealsListing(dietPlanType/*, ""*/);
                    }
                }, year, month, days);
                datePickerDialog.show();
            }
        });

        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                if (String.valueOf(month).trim().length() < 2) {
                    dateToSendToWebService = String.valueOf(year) + String.valueOf("0" + (month + 1)) + String.valueOf(dayOfMonth);
                    if(String.valueOf(dayOfMonth).trim().length() < 2) {
                        dateToSendToWebService = String.valueOf(year) + String.valueOf("0" +(month + 1)) + String.valueOf("0" + dayOfMonth);
                    } else {
                        dateToSendToWebService = String.valueOf(year)  + String.valueOf("0" + (month + 1)) + String.valueOf(dayOfMonth);
                    }
                } else {
                    dateToSendToWebService = String.valueOf(year)  + String.valueOf((month + 1)) + String.valueOf(dayOfMonth);

                }
                mealListingItemArrayList.clear();
                // mealsListAdapter.notifyDataSetChanged();
                getDataForMealsListing(dietPlanType/*, ""*/);
            }
        });

        return layout;
    }


    private void checkingDietPlanFromUserData() {
        // checking diet plan from user data
        if (loginCredentials.getUserDietPlanCode().equalsIgnoreCase("12")) {
            radioButtonMainStream.setChecked(true);
            dietPlanType = "mainstream";
            return;
        }
        if (loginCredentials.getUserDietPlanCode().equalsIgnoreCase("13")) {
            radioButtonVegetarian.setChecked(true);
            dietPlanType = "vegetarian";
            return;
        }
        if (loginCredentials.getUserDietPlanCode().equalsIgnoreCase("5")) {
            radioButtonMainStream.setChecked(true);
            dietPlanType = "mainstream";
            return;
        }
        if (loginCredentials.getUserDietPlanCode().equalsIgnoreCase("7")) {
            radioButtonVegetarian.setChecked(true);
            dietPlanType = "vegetarian";
        }
        if (loginCredentials.getUserDietPlanCode().equalsIgnoreCase("14")) {
            radioButtonMainStream.setChecked(true);
            dietPlanType = "mainstream";
        }
        if(loginCredentials.getUserDietPlanCode().equalsIgnoreCase("15")) {
            radioButtonVegetarian.setChecked(true);
            dietPlanType = "vegetarian";
        }
        if(loginCredentials.getUserDietPlanCode().equalsIgnoreCase("19")) {
            radioButtonVegetarian.setChecked(true);
            dietPlanType = "vegetarian";
        }
        if (loginCredentials.getUserDietPlanCode().equalsIgnoreCase("18")) {
            radioButtonMainStream.setChecked(true);
            dietPlanType = "mainstream";
        }
        if(loginCredentials.getUserDietPlanCode().equalsIgnoreCase("22")) {
            radioButtonVegetarian.setChecked(true);
            dietPlanType = "vegetarian";
        }
        if (loginCredentials.getUserDietPlanCode().equalsIgnoreCase("21")) {
            radioButtonMainStream.setChecked(true);
            dietPlanType = "mainstream";
        }

        // checking diet plan from user data
    }


    public void getcompleteDate() {
        try {
            dayOfTheWeek = (String) DateFormat.format("EEEE", calenderObject); // Thursday
            day = (String) DateFormat.format("dd", calenderObject); // 20
            monthString = (String) DateFormat.format("MMM", calenderObject); // Jun
            monthNumber = (String) DateFormat.format("MM", calenderObject); // 06
            year = (String) DateFormat.format("yyyy", calenderObject); // 2013

            daySelected=Integer.parseInt(day);
            monthSelected=Integer.parseInt(monthNumber);
            yearSelected=Integer.parseInt(year);

            wholeDate = day + " " + monthString + " " + year;
            dateToSendToWebService = year + monthNumber + day;

           // tvForDateMeals.setText(dayOfTheWeek + "," + " " + wholeDate);
            tvForMealsDate.setText(dayOfTheWeek + "," + " " + wholeDate);
            getDataForMealsListing(dietPlanType/*, callType*/);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void calculateDate(Boolean previous, Boolean after) throws ParseException {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
            calenderObject.setTime(sdf.parse(wholeDate));// all done
            System.out.println("CurrentDate : " + (calenderObject.get(Calendar.MONTH) + 1) + "-" + calenderObject.get(Calendar.DATE) + "-" + calenderObject.get(Calendar.YEAR));
            if (after) {
                // add days to current date
                calenderObject.add(calenderObject.DATE, daysToAdd);
                System.out.println("NextDate: " + (calenderObject.get(Calendar.MONTH) + 1) + "-" + calenderObject.get(Calendar.DATE) + "-" + calenderObject.get(Calendar.YEAR));
            } else if (previous) {
                //subtract date from current date
                calenderObject.add(calenderObject.DATE, -daysToSubtract);
                System.out.println("BeforeDate : " + (calenderObject.get(Calendar.MONTH) + 1) + "-" + calenderObject.get(Calendar.DATE) + "-" + calenderObject.get(Calendar.YEAR));
            }
            getcompleteDate();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.ivForPreviousDate, R.id.ivForNextDate, R.id.imgBack})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivForPreviousDate:
                try {
                    calculateDate(true, false);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                mealListingItemArrayList.clear();
                mealsListAdapter.notifyDataSetChanged();
                getDataForMealsListing(dietPlanType/*, ""*/);

                break;
            case R.id.ivForNextDate:
                try {
                    calculateDate(false, true);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                getDataForMealsListing(dietPlanType/*, ""*/);
                break;
            case R.id.imgBack:
                FragmentTransaction tx = getFragmentManager().beginTransaction();
                tx.replace( R.id.rootLayout, new MealPlanFragment() ).addToBackStack( "tag" ).commit();
                break;
            default:
        }
    }

    private void getDataForMealsListing(String dietPlan /*final String callType*/) {
        mealListingItemArrayList.clear();
        commonApi.showProgressDialog("");
        new GetServiceCall(Urls.GET_DATA_FOR_MEALS_LIST + loginCredentials.getUserGender() + "-" + dietPlan + "/" + dateToSendToWebService, GetServiceCall.TYPE_JSONOBJECT) {
            @Override
            public void response(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    System.out.println(" Object : " + jsonObject.toString());
                    System.out.println("Url : " + Urls.GET_DATA_FOR_MEALS_LIST+ loginCredentials.getUserGender() + "-" + dietPlan + "/" + dateToSendToWebService);
                    Glide.with(getActivity()).load(Urls.BASE_URL + jsonObject.optString("banner")).into(ivBannerImage);
                    if(jsonObject.has("meals")) {
                        for(int a = 0; a<=4; a++) {
                            MealListItem mealListingItem = new MealListItem();
                            mealListingItem.setTitle("");
                            mealListingItem.setMeal_timing("");
                            mealListingItem.setUrl("");
                            mealListingItem.setImage("");
                            mealListingItemArrayList.add(mealListingItem);
                        }
                        JSONObject jsonObjectMeals = jsonObject.getJSONObject("meals");
                        Iterator<String> keys = jsonObjectMeals.keys();
                        while( keys.hasNext() ) {
                            String key = keys.next();
                            Log.v("**********", "**********");
                            Log.v("category key", key);

                            JSONObject innerJObject = jsonObjectMeals.getJSONObject(key);
                            MealListItem mealListingItem = new MealListItem();
                            mealListingItem.setTitle(innerJObject.optString("title"));
                            mealListingItem.setMeal_timing(innerJObject.optString("meal_timing"));
                            mealListingItem.setUrl(innerJObject.optString("url"));
                            mealListingItem.setImage(innerJObject.optString("image"));
                            if(innerJObject.optString("meal_timing").equals("Breakfast")) {
                                mealListingItemArrayList.set(0, mealListingItem);
                            } else if(innerJObject.optString("meal_timing").equals("Morning Snack")) {
                                mealListingItemArrayList.set(1, mealListingItem);
                            } else if(innerJObject.optString("meal_timing").equals("Lunch")) {
                                mealListingItemArrayList.set(2, mealListingItem);
                            } else if(innerJObject.optString("meal_timing").equals("Afternoon Snack")) {
                                mealListingItemArrayList.set(3, mealListingItem);
                            } else if(innerJObject.optString("meal_timing").equals("Dinner")) {
                                mealListingItemArrayList.set(4, mealListingItem);
                            } /*else {
                                mealListingItemArrayList.add(mealListingItem);
                            }*/
                        }

                        // for (int i = 0; i < jsonObjectMeals.length(); i++) {
                        // MealListItem mealListingItem = new MealListItem();

                            /*JSONObject jsonObjectMealDetails = jsonObjectMeals.getJSONObject(i);
                            mealListingItem.setTitle(jsonObjectMealDetails.optString("title"));
                            mealListingItem.setMeal_timing(jsonObjectMealDetails.optString("meal_timing"));
                            mealListingItem.setUrl(jsonObjectMealDetails.optString("url"));
                            mealListingItem.setImage(jsonObjectMealDetails.optString("image"));*/

                        //    }
                        //if(callType.equals("firstTime")) {
                        mealsListAdapter = new MealsListAdapter(mealListingItemArrayList, getActivity(), "1", "0", "Recipe");
                        rvForMealsList.setAdapter(mealsListAdapter);
                   /* } else {
                        mealsListAdapter.notifyDataSetChanged();
                    }*/
                    }
                    else
                    {
                        mealsListAdapter = new MealsListAdapter(mealListingItemArrayList, getActivity(), "1", "0", "Recipe");
                        rvForMealsList.setAdapter(mealsListAdapter);
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

}


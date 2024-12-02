package com.studio.amplify.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.studio.amplify.R;
import com.studio.amplify.util.BaseActivity;
import com.studio.amplify.util.CommonApi;
import com.studio.amplify.util.Constant;
import com.studio.amplify.util.Urls;
import com.studio.amplify.volleyparser.GetServiceCall;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateGenderAndDietPlanActivity extends BaseActivity {

    @BindView(R.id.headerHeading)
    TextView headerHeading;
    //Header
    @BindView(R.id.rlGender)
    RelativeLayout rlGender;
    @BindView(R.id.rlDiet)
    RelativeLayout rlDiet;
    @BindView(R.id.rlUnit)
    RelativeLayout rlUnit;

    @BindView(R.id.rlForAgeRange)
    RelativeLayout rlForAgeRange;
    //Header

    //TextView
    @BindView(R.id.tvMale)
    TextView tvMale;
    @BindView(R.id.tvFemale)
    TextView tvFemale;
    @BindView(R.id.tvMainstream)
    TextView tvMainstream;
    @BindView(R.id.tvVegetarian)
    TextView tvVegetarian;
    @BindView(R.id.tvKg)
    TextView tvKg;
    @BindView(R.id.tvLb)
    TextView tvLb;
    @BindView(R.id.tvUnspecified)
    TextView tvUnspecified;
    @BindView(R.id.tvAgeUnder25)
    TextView tvAgeUnder25;
    @BindView(R.id.tvAge25To34)
    TextView tvAge25To34;
    @BindView(R.id.tvAge35To44)
    TextView tvAge35To44;
    @BindView(R.id.tvAge45To54)
    TextView tvAge45To54;
    @BindView(R.id.tvAge55To64)
    TextView tvAge55To64;
    @BindView(R.id.tvAge65Plus)
    TextView tvAge65Plus;
    @BindView(R.id.tvOther)
    TextView tvOther;
    @BindView(R.id.tvPrefer)
    TextView tvPrefer;
    //TextView

    //layouts
    @BindView(R.id.rlMale)
    RelativeLayout rlMale;
    @BindView(R.id.rlFemale)
    RelativeLayout rlFemale;
    @BindView(R.id.rlUnspecified)
    RelativeLayout rlUnspecified;
    @BindView(R.id.rlMainstream)
    RelativeLayout rlMainstream;
    @BindView(R.id.rlVegetarian)
    RelativeLayout rlVegetarian;
    @BindView(R.id.rlKg)
    RelativeLayout rlKg;

    @BindView(R.id.rlLb)
    RelativeLayout rlLb;

    @BindView(R.id.rlAgeUnder25)
    RelativeLayout rlAgeUnder25;

    @BindView(R.id.rlAge25To34)
    RelativeLayout rlAge25To34;

    @BindView(R.id.rlAge35To44)
    RelativeLayout rlAge35To44;

    @BindView(R.id.rlAge45To54)
    RelativeLayout rlAge45To54;

    @BindView(R.id.rlAge55To64)
    RelativeLayout rlAge55To64;
    @BindView(R.id.rlAge65Plus)
    RelativeLayout rlAge65Plus;

    @BindView(R.id.rlOther)
    RelativeLayout rlOther;
    @BindView(R.id.rlPrefer)
    RelativeLayout rlPrefer;

    //layouts

    //ImageView
    @BindView(R.id.imgMale)
    ImageView imgMale;
    @BindView(R.id.imgFemale)
    ImageView imgFemale;
    @BindView(R.id.imgMainstream)
    ImageView imgMainstream;
    @BindView(R.id.imgVegetarian)
    ImageView imgVegetarian;
    @BindView(R.id.imgLb)
    ImageView imgLb;
    @BindView(R.id.imgKg)
    ImageView imgKg;
    @BindView(R.id.imgUnspecified)
    ImageView imgUnspecified;

    @BindView(R.id.imgAgeUnder25)
    ImageView imgAgeUnder25;

    @BindView(R.id.imgAge25To34)
    ImageView imgAge25To34;

    @BindView(R.id.imgAge35To44)
    ImageView imgAge35To44;

    @BindView(R.id.imgAge45To54)
    ImageView imgAge45To54;

    @BindView(R.id.imgAge55To64)
    ImageView imgAge55To64;

    @BindView(R.id.imgAge65Plus)
    ImageView imgAge65Plus;

    @BindView(R.id.imgOther)
    ImageView imgOther;
    @BindView(R.id.imgPrefer)
    ImageView imgPrefer;

    //ImageView


    private CommonApi commonApi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        commonApi = new CommonApi(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_gender_and_diet_plan);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);

        imgMainstream.setImageResource(R.drawable.b_check);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            Integer fromIntent = bundle.getInt("fromIntent");

            if (fromIntent == 1) {
                headerHeading.setText("Gender");
                rlGender.setVisibility(View.VISIBLE);
                rlDiet.setVisibility(View.GONE);
                rlUnit.setVisibility(View.GONE);
                rlForAgeRange.setVisibility(View.GONE);
                if (loginCredentials.getUserGender().equalsIgnoreCase("M")) {
                    imgMale.setImageResource(R.drawable.b_check);
                    imgFemale.setImageResource(R.drawable.b_uncheck);
                    imgUnspecified.setImageResource(R.drawable.b_uncheck);
                    imgOther.setImageResource(R.drawable.b_uncheck);
                    imgPrefer.setImageResource(R.drawable.b_uncheck);

                } else if (loginCredentials.getUserGender().equalsIgnoreCase("F")) {
                    imgFemale.setImageResource(R.drawable.b_check);
                    imgMale.setImageResource(R.drawable.b_uncheck);
                    imgUnspecified.setImageResource(R.drawable.b_uncheck);
                    imgOther.setImageResource(R.drawable.b_uncheck);
                    imgPrefer.setImageResource(R.drawable.b_uncheck);

                } else if (loginCredentials.getUserGender().equalsIgnoreCase("U")) {
                    imgUnspecified.setImageResource(R.drawable.b_check);
                    imgMale.setImageResource(R.drawable.b_uncheck);
                    imgFemale.setImageResource(R.drawable.b_uncheck);
                    imgOther.setImageResource(R.drawable.b_uncheck);
                    imgPrefer.setImageResource(R.drawable.b_uncheck);

                } else if (loginCredentials.getUserGender().equalsIgnoreCase("O")) {
                    imgOther.setImageResource(R.drawable.b_check);
                    imgMale.setImageResource(R.drawable.b_uncheck);
                    imgFemale.setImageResource(R.drawable.b_uncheck);
                    imgPrefer.setImageResource(R.drawable.b_uncheck);
                    imgUnspecified.setImageResource(R.drawable.b_uncheck);

                } else if (loginCredentials.getUserGender().equalsIgnoreCase("P")) {
                    imgPrefer.setImageResource(R.drawable.b_check);
                    imgMale.setImageResource(R.drawable.b_uncheck);
                    imgFemale.setImageResource(R.drawable.b_uncheck);
                    imgOther.setImageResource(R.drawable.b_uncheck);
                    imgUnspecified.setImageResource(R.drawable.b_uncheck);
                }

            } else if (fromIntent == 3) {
                headerHeading.setText("Unit");
                rlUnit.setVisibility(View.VISIBLE);
                rlGender.setVisibility(View.GONE);
                rlDiet.setVisibility(View.GONE);
                rlForAgeRange.setVisibility(View.GONE);
                if (loginCredentials.getUserUnit().equalsIgnoreCase("kg")) {
                    imgKg.setImageResource(R.drawable.b_check);
                    imgLb.setImageResource(R.drawable.b_uncheck);

                } else if (loginCredentials.getUserUnit().equalsIgnoreCase("lb")) {
                    imgLb.setImageResource(R.drawable.b_check);
                    imgKg.setImageResource(R.drawable.b_uncheck);
                }

            } else if (fromIntent == 4) {
                headerHeading.setText("Age Range");
                rlForAgeRange.setVisibility(View.VISIBLE);
                rlGender.setVisibility(View.GONE);
                rlDiet.setVisibility(View.GONE);
                rlUnit.setVisibility(View.GONE);

                /*if (loginCredentials.getUserUnit().equalsIgnoreCase("Under 25")) {
                    imgAgeUnder25.setImageResource(R.drawable.b_check);
                    imgAge25To34.setImageResource(R.drawable.b_uncheck);

                } else if (loginCredentials.getUserUnit().equalsIgnoreCase("lb")) {
                    imgLb.setImageResource(R.drawable.b_check);
                    imgKg.setImageResource(R.drawable.b_uncheck);
                }*/

            }


            else {
                headerHeading.setText("Diet Plan");
                rlDiet.setVisibility(View.VISIBLE);
                rlGender.setVisibility(View.GONE);
                rlUnit.setVisibility(View.GONE);
                rlForAgeRange.setVisibility(View.GONE);
                if (loginCredentials.getUserDietPlanCode().equalsIgnoreCase("5")) {
                    imgMainstream.setImageResource(R.drawable.b_check);
                    imgVegetarian.setImageResource(R.drawable.b_uncheck);
                } else if (loginCredentials.getUserDietPlanCode().equalsIgnoreCase("7")) {
                    imgMainstream.setImageResource(R.drawable.b_uncheck);
                    imgVegetarian.setImageResource(R.drawable.b_check);
                } else if (loginCredentials.getUserDietPlanCode().equalsIgnoreCase("12")) {
                    imgMainstream.setImageResource(R.drawable.b_check);
                    imgVegetarian.setImageResource(R.drawable.b_uncheck);
                } else if (loginCredentials.getUserDietPlanCode().equalsIgnoreCase("13")) {
                    imgMainstream.setImageResource(R.drawable.b_uncheck);
                    imgVegetarian.setImageResource(R.drawable.b_check);
                } else if (loginCredentials.getUserDietPlanCode().equalsIgnoreCase("14")) {
                    imgMainstream.setImageResource(R.drawable.b_check);
                    imgVegetarian.setImageResource(R.drawable.b_uncheck);
                } else if (loginCredentials.getUserDietPlan().equalsIgnoreCase("15")) {
                    imgVegetarian.setImageResource(R.drawable.b_check);
                    imgMainstream.setImageResource(R.drawable.b_uncheck);
                } else if (loginCredentials.getUserDietPlan().equalsIgnoreCase("19")) {
                    imgVegetarian.setImageResource(R.drawable.b_check);
                    imgMainstream.setImageResource(R.drawable.b_uncheck);
                } else if (loginCredentials.getUserDietPlan().equalsIgnoreCase("18")) {
                    imgMainstream.setImageResource(R.drawable.b_check);
                    imgVegetarian.setImageResource(R.drawable.b_uncheck);
                } else if (loginCredentials.getUserDietPlan().equalsIgnoreCase("21")) {
                    imgMainstream.setImageResource(R.drawable.b_check);
                    imgVegetarian.setImageResource(R.drawable.b_uncheck);
                } else if (loginCredentials.getUserDietPlan().equalsIgnoreCase("22")) {
                    imgVegetarian.setImageResource(R.drawable.b_check);
                    imgMainstream.setImageResource(R.drawable.b_uncheck);
                }

            }
        }
    }//end of onCreate


    @OnClick({R.id.rlFemale, R.id.rlMale,R.id.rlUnspecified, R.id.rlMainstream,
            R.id.rlVegetarian, R.id.rlBack, R.id.rlKg, R.id.rlLb,R.id.rlAgeUnder25,R.id.rlAge25To34,
            R.id.rlAge35To44, R.id.rlAge45To54, R.id.rlAge55To64,R.id.rlAge65Plus, R.id.rlOther, R.id.rlPrefer})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rlMale:
                imgMale.setImageResource(R.drawable.b_check);
                imgFemale.setImageResource(R.drawable.b_uncheck);
                imgUnspecified.setImageResource(R.drawable.b_uncheck);
                imgPrefer.setImageResource(R.drawable.b_uncheck);
                imgOther.setImageResource(R.drawable.b_uncheck);
                updateGenderAndDietPlanMethod("gender", "M");
                break;

            case R.id.rlFemale:
                imgFemale.setImageResource(R.drawable.b_check);
                imgMale.setImageResource(R.drawable.b_uncheck);
                imgUnspecified.setImageResource(R.drawable.b_uncheck);
                imgPrefer.setImageResource(R.drawable.b_uncheck);
                imgOther.setImageResource(R.drawable.b_uncheck);
                updateGenderAndDietPlanMethod("gender", "F");
                break;

            case R.id.rlUnspecified:
                imgUnspecified.setImageResource(R.drawable.b_check);
                imgMale.setImageResource(R.drawable.b_uncheck);
                imgFemale.setImageResource(R.drawable.b_uncheck);
                imgOther.setImageResource(R.drawable.b_uncheck);
                imgPrefer.setImageResource(R.drawable.b_uncheck);
                updateGenderAndDietPlanMethod("gender", "U");
                break;

            case R.id.rlOther:
                imgOther.setImageResource(R.drawable.b_check);
                imgMale.setImageResource(R.drawable.b_uncheck);
                imgFemale.setImageResource(R.drawable.b_uncheck);
                imgPrefer.setImageResource(R.drawable.b_uncheck);
                imgUnspecified.setImageResource(R.drawable.b_uncheck);
                updateGenderAndDietPlanMethod("gender", "O");
                break;

            case R.id.rlPrefer:
                imgPrefer.setImageResource(R.drawable.b_check);
                imgMale.setImageResource(R.drawable.b_uncheck);
                imgFemale.setImageResource(R.drawable.b_uncheck);
                imgOther.setImageResource(R.drawable.b_uncheck);
                imgUnspecified.setImageResource(R.drawable.b_uncheck);
                updateGenderAndDietPlanMethod("gender", "P");
                break;

            case R.id.rlMainstream:
                imgMainstream.setImageResource(R.drawable.b_check);
                imgVegetarian.setImageResource(R.drawable.b_uncheck);
                if (loginCredentials.getUserGender().equals("M")) {
                    updateGenderAndDietPlanMethod("diet-plan", "5");
                } else if(loginCredentials.getUserGender().equals("F")){
                    updateGenderAndDietPlanMethod("diet-plan", "12");
                } else if(loginCredentials.getUserGender().equals("U")) {
                    updateGenderAndDietPlanMethod("diet-plan", "14");
                } else if(loginCredentials.getUserGender().equals("O")) {
                    updateGenderAndDietPlanMethod("diet-plan", "18");
                } else if(loginCredentials.getUserGender().equals("P")) {
                    updateGenderAndDietPlanMethod("diet-plan", "21");
                }
                break;

            case R.id.rlVegetarian:
                imgVegetarian.setImageResource(R.drawable.b_check);
                imgMainstream.setImageResource(R.drawable.b_uncheck);
                if (loginCredentials.getUserGender().equals("M")) {
                    updateGenderAndDietPlanMethod("diet-plan", "7");
                } else if(loginCredentials.getUserGender().equals("F")){
                    updateGenderAndDietPlanMethod("diet-plan", "13");
                } else if(loginCredentials.getUserGender().equals("U")) {
                    updateGenderAndDietPlanMethod("diet-plan", "15");
                } else if(loginCredentials.getUserGender().equals("O")) {
                    updateGenderAndDietPlanMethod("diet-plan", "19");
                } else if(loginCredentials.getUserGender().equals("P")) {
                    updateGenderAndDietPlanMethod("diet-plan", "22");
                }

                break;
            case R.id.rlBack:
                commonApi.finishActivity(UpdateGenderAndDietPlanActivity.this);
                break;

            case R.id.rlKg:
                imgKg.setImageResource(R.drawable.b_check);
                imgLb.setImageResource(R.drawable.b_uncheck);
                loginCredentials.setUserUnit("kg");
                updateGenderAndDietPlanMethod("unit", "kg");
                break;

            case R.id.rlLb:
                imgLb.setImageResource(R.drawable.b_check);
                imgKg.setImageResource(R.drawable.b_uncheck);
                loginCredentials.setUserUnit("lb");
                updateGenderAndDietPlanMethod("unit", "lb");
                break;

            case R.id.rlAgeUnder25:
                imgAgeUnder25.setImageResource(R.drawable.b_check);
                imgAge25To34.setImageResource(R.drawable.b_uncheck);
                imgAge35To44.setImageResource(R.drawable.b_uncheck);
                imgAge45To54.setImageResource(R.drawable.b_uncheck);
                imgAge55To64.setImageResource(R.drawable.b_uncheck);
                imgAge65Plus.setImageResource(R.drawable.b_uncheck);
                //loginCredentials.setUserUnit("lb");
                updateGenderAndDietPlanMethod("agerange", "Under 25");
            break;
            case R.id.rlAge25To34:
                imgAgeUnder25.setImageResource(R.drawable.b_uncheck);
                imgAge25To34.setImageResource(R.drawable.b_check);
                imgAge35To44.setImageResource(R.drawable.b_uncheck);
                imgAge45To54.setImageResource(R.drawable.b_uncheck);
                imgAge55To64.setImageResource(R.drawable.b_uncheck);
                imgAge65Plus.setImageResource(R.drawable.b_uncheck);
                //loginCredentials.setUserUnit("lb");
                updateGenderAndDietPlanMethod("agerange", "25 - 34");
                break;
            case R.id.rlAge35To44:
                imgAgeUnder25.setImageResource(R.drawable.b_uncheck);
                imgAge25To34.setImageResource(R.drawable.b_uncheck);
                imgAge35To44.setImageResource(R.drawable.b_check);
                imgAge45To54.setImageResource(R.drawable.b_uncheck);
                imgAge55To64.setImageResource(R.drawable.b_uncheck);
                imgAge65Plus.setImageResource(R.drawable.b_uncheck);
                //loginCredentials.setUserUnit("lb");
                updateGenderAndDietPlanMethod("agerange", "35 - 44");
                break;
            case R.id.rlAge45To54:
                imgAgeUnder25.setImageResource(R.drawable.b_uncheck);
                imgAge25To34.setImageResource(R.drawable.b_uncheck);
                imgAge35To44.setImageResource(R.drawable.b_uncheck);
                imgAge45To54.setImageResource(R.drawable.b_check);
                imgAge55To64.setImageResource(R.drawable.b_uncheck);
                imgAge65Plus.setImageResource(R.drawable.b_uncheck);
                //loginCredentials.setUserUnit("lb");
                updateGenderAndDietPlanMethod("agerange", "45 - 54");
                break;
            case R.id.rlAge55To64:
                imgAgeUnder25.setImageResource(R.drawable.b_uncheck);
                imgAge25To34.setImageResource(R.drawable.b_uncheck);
                imgAge35To44.setImageResource(R.drawable.b_uncheck);
                imgAge45To54.setImageResource(R.drawable.b_uncheck);
                imgAge55To64.setImageResource(R.drawable.b_check);
                imgAge65Plus.setImageResource(R.drawable.b_uncheck);
                //loginCredentials.setUserUnit("lb");
                updateGenderAndDietPlanMethod("agerange", "55 - 64");
                break;
            case R.id.rlAge65Plus:
                imgAgeUnder25.setImageResource(R.drawable.b_uncheck);
                imgAge25To34.setImageResource(R.drawable.b_uncheck);
                imgAge35To44.setImageResource(R.drawable.b_uncheck);
                imgAge45To54.setImageResource(R.drawable.b_uncheck);
                imgAge55To64.setImageResource(R.drawable.b_uncheck);
                imgAge65Plus.setImageResource(R.drawable.b_check);
                //loginCredentials.setUserUnit("lb");
                updateGenderAndDietPlanMethod("agerange", "65 Plus");
                break;
        }
    }

    private void updateGenderAndDietPlanMethod(final String urlKey, final String value) {
        if (!commonApi.isInternetAvailable(this)) {
            Toast.makeText(this, Constant.NO_INTERNET, Toast.LENGTH_SHORT).show();
            return;
        }
        commonApi.showProgressDialog("");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", loginCredentials.getUserId());
            if (urlKey.equals("gender")) {
                jsonObject.put(urlKey, value);
            } else if (urlKey.equals("unit")) {
                jsonObject.put(urlKey, value);
            } else
                jsonObject.put(urlKey, value);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        new GetServiceCall(Urls.EDIT_PROFILE_UPDATE + urlKey, GetServiceCall.TYPE_JSONOBJECT_POST, jsonObject) {
            @Override
            public void response(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("sStatus").equalsIgnoreCase("1")) {
                        if (urlKey.equals("gender")) {
                            loginCredentials.setUserGender(value);
                            callForDietPlanMethod();
                        } else if (urlKey.equals("unit")) {
                            loginCredentials.setUserUnit(value);
                        }else if(urlKey.equals("agerange"))
                        {
                            loginCredentials.setAgeRange(value);
                        }
                        else {
                            if (urlKey.equals("diet-plan")) {
                                if (value.equalsIgnoreCase("7")) {
                                    loginCredentials.setUserDietPlanCode(value);
                                    loginCredentials.setUserDietPlan("Vegetarian");
                                    imgVegetarian.setImageResource(R.drawable.b_check);
                                } else if (value.equalsIgnoreCase("5")) {
                                    loginCredentials.setUserDietPlanCode(value);
                                    loginCredentials.setUserDietPlan("Mainstream");
                                    imgMainstream.setImageResource(R.drawable.b_check);
                                } else if (value.equalsIgnoreCase("12")) {
                                    loginCredentials.setUserDietPlanCode(value);
                                    loginCredentials.setUserDietPlan("Mainstream");
                                    imgMainstream.setImageResource(R.drawable.b_check);
                                } else if (value.equalsIgnoreCase("13")) {
                                    loginCredentials.setUserDietPlanCode(value);
                                    loginCredentials.setUserDietPlan("Vegetarian");
                                    imgVegetarian.setImageResource(R.drawable.b_check);
                                } else if(value.equalsIgnoreCase("14")) {
                                    loginCredentials.setUserDietPlanCode(value);
                                    loginCredentials.setUserDietPlan("Mainstream");
                                } else if(value.equalsIgnoreCase("15")) {
                                    loginCredentials.setUserDietPlanCode(value);
                                    loginCredentials.setUserDietPlan("Vegetarian");
                                } else if(value.equalsIgnoreCase("19")) {
                                    loginCredentials.setUserDietPlanCode(value);
                                    loginCredentials.setUserDietPlan("Vegetarian");
                                } else if(value.equalsIgnoreCase("22")) {
                                    loginCredentials.setUserDietPlanCode(value);
                                    loginCredentials.setUserDietPlan("Vegetarian");
                                } else if(value.equalsIgnoreCase("18")) {
                                    loginCredentials.setUserDietPlanCode(value);
                                    loginCredentials.setUserDietPlan("Mainstream");
                                } else if(value.equalsIgnoreCase("21")) {
                                    loginCredentials.setUserDietPlanCode(value);
                                    loginCredentials.setUserDietPlan("Mainstream");
                                }
                            }

                        }

                        Toast.makeText(UpdateGenderAndDietPlanActivity.this, jsonObject.getString("sMessage"), Toast.LENGTH_SHORT).show();
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

    // to update diet plan
    private void callForDietPlanMethod() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", loginCredentials.getUserId());
            if (loginCredentials.getUserGender().equalsIgnoreCase("F")) {
                jsonObject.put("diet-plan", "12");
            } else if(loginCredentials.getUserGender().equalsIgnoreCase("M")){
                jsonObject.put("diet-plan", "5");
            } else if(loginCredentials.getUserGender().equalsIgnoreCase("U")){
                jsonObject.put("diet-plan", "14");
            } else if(loginCredentials.getUserGender().equalsIgnoreCase("O")){
                jsonObject.put("diet-plan", "18");
            } else if(loginCredentials.getUserGender().equalsIgnoreCase("P")){
                jsonObject.put("diet-plan", "21");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new GetServiceCall(Urls.UPDATE_DIET_PLAN, GetServiceCall.TYPE_JSONOBJECT_POST, jsonObject) {

            @Override
            public void response(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("sStatus").equalsIgnoreCase("1")) {
                        if (loginCredentials.getUserGender().equalsIgnoreCase("F")) {
                            loginCredentials.setUserDietPlan("Mainstream");
                            loginCredentials.setUserDietPlanCode("12");

                        } else if(loginCredentials.getUserGender().equalsIgnoreCase("M")){
                            loginCredentials.setUserDietPlan("Mainstream");
                            loginCredentials.setUserDietPlanCode("5");

                        } else if(loginCredentials.getUserGender().equalsIgnoreCase("U")) {
                            loginCredentials.setUserDietPlan("Mainstream");
                            loginCredentials.setUserDietPlanCode("14");

                        } else if(loginCredentials.getUserGender().equalsIgnoreCase("O")) {
                            loginCredentials.setUserDietPlan("Mainstream");
                            loginCredentials.setUserDietPlanCode("18");

                        } else if(loginCredentials.getUserGender().equalsIgnoreCase("P")) {
                            loginCredentials.setUserDietPlan("Mainstream");
                            loginCredentials.setUserDietPlanCode("21");

                        }

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

    // to update diet plan


}
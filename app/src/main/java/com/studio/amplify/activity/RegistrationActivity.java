package com.studio.amplify.activity;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.studio.amplify.R;
import com.studio.amplify.util.BaseActivity;
import com.studio.amplify.util.CommonApi;
import com.studio.amplify.util.Constant;
import com.studio.amplify.util.Urls;
import com.studio.amplify.volleyparser.GetServiceCall;
import com.studio.amplify.volleyparser.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RegistrationActivity extends BaseActivity {
    //header
    @BindView(R.id.rlBack)
    RelativeLayout rlBack;
    @BindView(R.id.headerHeading)
    TextView headerHeading;
    //header

    @BindView(R.id.lnSignUpMain)
    LinearLayout lnSignUpMain;

    @BindView(R.id.etFirstName)
    EditText etFirstName;
    @BindView(R.id.etLastName)
    EditText etLastName;
    @BindView(R.id.etEmailAddress)
    EditText etEmailAddress;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.etConfirmPassword)
    EditText etConfirmPassword;

    @BindView(R.id.buttonSignUp)
    Button buttonSignUp;
    @BindView(R.id.spGender)
    Spinner spGender;
    @BindView(R.id.spStudio)
    Spinner spStudio;
    @BindView(R.id.spCountry)
    Spinner spCountry;
    @BindView(R.id.spUnit)
    Spinner spUnit;
    @BindView(R.id.spGroup)
    Spinner spGroup;
    @BindView(R.id.checkImage)
    ImageView checkImage;

    ArrayList<String> genderArrayList;
    ArrayList<String> unitArrayList;

    ArrayList<String> studioArrayList = new ArrayList<>();
    ArrayList<String> studioIDArrayList = new ArrayList<>();

    ArrayList<String> countryArrayList = new ArrayList<>();
    ArrayList<String> countryCodeArrayList = new ArrayList<>();

    ArrayList<String> groupIDArrayList = new ArrayList<>();
    ArrayList<String> groupNameArrayList = new ArrayList<>();

    String selectedGender, selectedStudio, selectedCountry, selectedCountryId,
            selectedStudioId, selectedUnit, selectedGroup, selectedGroupId;
    boolean checkImageStatus = false;

    @BindView(R.id.textViewTermsAndConditions)
    TextView textViewTermsAndConditions;
    Bundle bundleDataToSend;

    private CommonApi commonApi;

    TextView congratulationText;
    TextView popupMessage;
    Button nextToLogin;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        commonApi = new CommonApi(RegistrationActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);

        getCountryMethod();
        getStudioMethod();
        getGroupMethod();

        headerHeading.setText(R.string.register);

        bundleDataToSend = new Bundle();

        //gender array
        genderArrayList = new ArrayList<>();
        genderArrayList.add("Gender");
        genderArrayList.add("Identify as Male");
        genderArrayList.add("Identify as Female");
        genderArrayList.add("Unspecified/Non-binary");
        genderArrayList.add("Other");
        genderArrayList.add("Prefer not to say");
        ArrayAdapter<String> arrayAdapterForGenderList = new ArrayAdapter<>(this, R.layout.spinner_item, genderArrayList);
        spGender.setAdapter(arrayAdapterForGenderList);
        //gender array

        //unit array
        unitArrayList = new ArrayList<>();
        unitArrayList.add("Unit");
        unitArrayList.add("kg");
        unitArrayList.add("lb");
        ArrayAdapter<String> arrayAdapterForUnitList = new ArrayAdapter<>(this, R.layout.spinner_item, unitArrayList);
        spUnit.setAdapter(arrayAdapterForUnitList);
        //unit array

        onClickSpinnerItems();
        forSpannableStringMethod();

    }

    private void forSpannableStringMethod() {
        SpannableStringBuilder spanTxt = new SpannableStringBuilder("I have read and agree to the ");
        spanTxt.append("Terms and Conditions");
        spanTxt.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                bundleDataToSend.putString("urlKey", Urls.TERMS_AND_CONDITION);
                bundleDataToSend.putString("titleForHeader", "Terms And Conditions");
                commonApi.openNewScreen(WebViewActivity.class, bundleDataToSend);
            }
        }, spanTxt.length() - "Terms and Conditions".length(), spanTxt.length(), 0);
        spanTxt.append(" and");
        spanTxt.setSpan(new ForegroundColorSpan(Color.WHITE), 34, spanTxt.length(), 0);
        spanTxt.append(" Privacy Policy");
        spanTxt.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                bundleDataToSend.putString("urlKey", Urls.PRIVACY_POLICY);
                bundleDataToSend.putString("titleForHeader", "Privacy Policy");
                commonApi.openNewScreen(WebViewActivity.class, bundleDataToSend);
            }
        }, spanTxt.length() - "Privacy Policy".length(), spanTxt.length(), 0);
        textViewTermsAndConditions.setMovementMethod(LinkMovementMethod.getInstance());
        textViewTermsAndConditions.setText(spanTxt, TextView.BufferType.SPANNABLE);
    }


    // method for country web service
    private void getCountryMethod() {
        commonApi.showProgressDialog("Please wait..");
        countryArrayList.add("Country");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Urls.GET_COUNTRY_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.optJSONObject(i);
                                countryCodeArrayList.add(jsonObject1.optString("country_code"));
                                countryArrayList.add(jsonObject1.optString("country_name"));
                                System.out.println(" Object : " + jsonObject1.toString());
                                System.out.println(" Url : " + Urls.GET_COUNTRY_LIST);
                            }
                            ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>
                                    (RegistrationActivity.this, R.layout.spinner_item, countryArrayList);
                            countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spCountry.setAdapter(countryAdapter);

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
    }
//end of method for country web service


    // method for studio web service
    private void getStudioMethod() {
        studioArrayList.add("Studio");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Urls.GET_STUDIO_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.optJSONObject(i);
                                studioIDArrayList.add(jsonObject1.optString("studio_id"));
                                studioArrayList.add(jsonObject1.optString("studio_name"));
                                System.out.println(" Object : " + jsonObject1.toString());
                                System.out.println(" Url : " + Urls.GET_STUDIO_LIST);
                            }
                            ArrayAdapter<String> studioAdapter = new ArrayAdapter<String>
                                    (RegistrationActivity.this, R.layout.spinner_item, studioArrayList);
                            studioAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spStudio.setAdapter(studioAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        MyApplication.getInstance().addToRequestQueue(stringRequest);
    }


    private void getGroupMethod() {
        groupNameArrayList.add("Group");
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, Urls.GET_GROUP_LIST,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.optJSONObject(i);
                                    try {
                                        groupIDArrayList.add(jsonObject1.optString("group_id"));
                                        groupNameArrayList.add(jsonObject1.optString("group_name"));
                                        System.out.println(" groupObject : " + jsonObject1.toString());
                                        System.out.println(" groupUrl : " + Urls.GET_GROUP_LIST);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                                try {
                                    ArrayAdapter<String> groupAdapter = new ArrayAdapter<String>
                                            (RegistrationActivity.this, R.layout.spinner_item, groupNameArrayList);
                                    groupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spGroup.setAdapter(groupAdapter);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try {
                        error.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            MyApplication.getInstance().addToRequestQueue(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//end of method for studio web service

    private void onClickSpinnerItems() {
        spGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spGender.getSelectedItem().toString().equals("Identify as Male")) {
                    selectedGender = "M";
                } else if (spGender.getSelectedItem().toString().equals("Identify as Female")) {
                    selectedGender = "F";
                } else if(spGender.getSelectedItem().toString().equals("Unspecified/Non-binary") ){
                    selectedGender = "U";
                } else if (spGender.getSelectedItem().toString().equals("Other")) {
                    selectedGender = "O";
                } else if (spGender.getSelectedItem().toString().equals("Prefer not to say")) {
                    selectedGender = "P";
                }

                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.white));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spStudio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedStudio = spStudio.getSelectedItem().toString();
                if (position != 0) {
                    selectedStudioId = studioIDArrayList.get(position - 1);
                }
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.white));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCountry = spCountry.getSelectedItem().toString();
                if (position != 0) {
                    selectedCountryId = countryCodeArrayList.get(position - 1);
                }
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.white));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spUnit.getSelectedItem().toString().equalsIgnoreCase("Kg")) {
                    selectedUnit = "kg";
                } else
                    selectedUnit = "lb";
                ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.white));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedGroup = spGroup.getSelectedItem().toString();
                if (position != 0) {
                    selectedGroupId = groupIDArrayList.get(position - 1);
                }
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.white));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @OnClick({R.id.buttonSignUp, R.id.checkImage, R.id.backToLogin, R.id.rlBack})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.buttonSignUp:
                commonApi.errorMsg = "";
                commonApi.errorMsg = commonApi.checkRequiredFields(lnSignUpMain);
                if (commonApi.errorMsg.equals("")) {
                    uploadNewUserData();
                } else {
                    commonApi.showToastMessage(RegistrationActivity.this, commonApi.errorMsg);
                }
                break;
            case R.id.checkImage:
                if (!checkImageStatus) {
                    checkImageStatus = true;
                    checkImage.setImageResource(R.drawable.check_w);
                } else {
                    checkImageStatus = false;
                    checkImage.setImageResource(R.drawable.uncheck_w);
                }
                break;
            case R.id.backToLogin:
                onBackPressed();
                break;
            case R.id.rlBack:
                onBackPressed();
                break;
            default:
        }

    }

    private void uploadNewUserData() {
        if (!commonApi.checkEmailValidation(etEmailAddress.getText().toString().trim())) {
            commonApi.showToastMessage(RegistrationActivity.this, Constant.EMAIL_VALIDATION);
            return;
        }
        if (spGender.getSelectedItemPosition() <= 0) {
            commonApi.showToastMessage(RegistrationActivity.this, Constant.SELECT_GENDER);
            return;
        }
        if (spStudio.getSelectedItemPosition() <= 0) {
            commonApi.showToastMessage(RegistrationActivity.this, Constant.SELECT_STUDIO);
            return;
        }
        if (spCountry.getSelectedItemPosition() <= 0) {
            commonApi.showToastMessage(RegistrationActivity.this, Constant.SELECT_COUNTRY);
            return;
        }
        if (spUnit.getSelectedItemPosition() <= 0) {
            commonApi.showToastMessage(RegistrationActivity.this, Constant.SELECT_UNIT);
            return;
        }
        if (spGroup.getSelectedItemPosition() <= 0) {
            commonApi.showToastMessage(RegistrationActivity.this, Constant.SELECT_GROUP);
            return;
        }
        if (!etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
            commonApi.showToastMessage(RegistrationActivity.this, Constant.PASSWORD_AND_CONFIRM_PASSWORD_MATCHING);
            return;
        }
        if (!checkImageStatus) {
            commonApi.showToastMessage(RegistrationActivity.this, Constant.TERMS_CONDITION);
            return;
        }
        if (!commonApi.isInternetAvailable(this)) {
            commonApi.showDialog(RegistrationActivity.this, Constant.NO_INTERNET, "", getResources().getColor(R.color.black), false);
            return;
        }
        commonApi.showProgressDialog("Please wait..");
        JSONObject obj = new JSONObject();
        try {
            obj.put("first_name", etFirstName.getText().toString().trim());
            obj.put("last_name", etLastName.getText().toString().trim());
            obj.put("email", etEmailAddress.getText().toString().trim());
            obj.put("password", etPassword.getText().toString().trim());
            obj.put("gender", selectedGender);
            obj.put("country", selectedCountryId);
            obj.put("studio", selectedStudioId);
            obj.put("unit", selectedUnit);
            obj.put("group", selectedGroupId);
            System.out.println(" regObject : " + obj.toString());
            System.out.println(" regUrl : " + Urls.USER_REGISTRATION);
        } catch (Exception e) {
            e.printStackTrace();
        }
        new GetServiceCall(Urls.USER_REGISTRATION, GetServiceCall.TYPE_JSONOBJECT_POST, obj) {
            @Override
            public void response(String response) {
                try {
                    Log.d("regResp:", response);
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("sStatus").equalsIgnoreCase("1")) {
                        dialogMessageForSuccess();
                    } else {
                        commonApi.showDialog(RegistrationActivity.this, jsonObject.getString("sMessage"), "", getResources().getColor(R.color.black), false);
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

    } //uploadNewUserData

    private void dialogMessageForSuccess() {
// custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.register_popup_design);
        dialog.setCancelable(false);
        nextToLogin = dialog.findViewById(R.id.nextToLogin);
        congratulationText = dialog.findViewById(R.id.congratulationText);
        popupMessage = dialog.findViewById(R.id.popupMessage);
// if button is clicked, close the custom dialog
        nextToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void onBackPressed() {
        commonApi.openNewScreen(LoginActivity.class, null);
        commonApi.finishActivity(RegistrationActivity.this);
    }
}
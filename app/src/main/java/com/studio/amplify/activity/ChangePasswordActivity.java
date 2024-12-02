package com.studio.amplify.activity;

import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

public class ChangePasswordActivity extends BaseActivity {

    //heading
    @BindView(R.id.headerHeading)
    TextView headerHeading;
    @BindView(R.id.rlBack)
    RelativeLayout rlBack;
    //heading

    //label
    @BindView(R.id.etOldPassword)
    EditText etOldPassword;
    @BindView(R.id.etNewPassword)
    EditText etNewPassword;
    @BindView(R.id.etConfirmPassword)
    EditText etConfirmPassword;
    /*@BindView(R.id.btnSubmit)
    Button btnSubmit;*/
    @BindView(R.id.btnSubmit)
    TextView btnSubmit;
    @BindView(R.id.textToVerify)
    TextView textToVerify;
    @BindView(R.id.rootLLForChangePassword)
    LinearLayout rootLLForChangePassword;
    //label

    CommonApi commonApi;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        commonApi = new CommonApi(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);

        headerHeading.setText(R.string.change_password);
        textToVerify.setText(R.string.verify);
        btnSubmit.setEnabled(false);
        onClickListner();

    }

    private void onClickListner() {
        etOldPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textToVerify.setText(R.string.verify);
                textToVerify.setTextColor(getResources().getColor(R.color.not_verified_color));
                etNewPassword.setEnabled(false);
                etConfirmPassword.setEnabled(false);
                btnSubmit.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick({R.id.btnSubmit, R.id.rlBack, R.id.textToVerify})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit:
                changePasswordMethod();
                break;
            case R.id.rlBack:
                commonApi.finishActivity(ChangePasswordActivity.this);
                break;
            case R.id.textToVerify:
                if (etOldPassword.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(this, "Please enter your current password.", Toast.LENGTH_SHORT).show();
                } else {
                    oldPasswordValidationMethod();
                }
                break;
            default:

        }
    }

    private void oldPasswordValidationMethod() {
        if (!commonApi.isInternetAvailable(this)) {
            Toast.makeText(this, Constant.NO_INTERNET, Toast.LENGTH_SHORT).show();
        }
        commonApi.showProgressDialog("");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", loginCredentials.getUserId());
            jsonObject.put("current_password", etOldPassword.getText().toString().trim());
            System.out.println(" Object : " + jsonObject.toString());
            System.out.println(" Url : " + Urls.CHECK_CURRENT_PASSWORD);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new GetServiceCall(Urls.CHECK_CURRENT_PASSWORD, GetServiceCall.TYPE_JSONOBJECT_POST, jsonObject) {
            @Override
            public void response(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("sStatus").equalsIgnoreCase("1")) {

                        textToVerify.setText("Verified");
                        etOldPassword.setEnabled(false);
                        textToVerify.setEnabled(false);
                        textToVerify.setTextColor(getResources().getColor(R.color.verified_color));

                        etNewPassword.setFocusable(true);
                        etNewPassword.requestFocus();
                        etNewPassword.setFocusableInTouchMode(true);
                        etNewPassword.setEnabled(true);

                        etConfirmPassword.setFocusable(true);
                        etConfirmPassword.setFocusableInTouchMode(true);
                        etConfirmPassword.setEnabled(true);
                        btnSubmit.setEnabled(true);

                    } else {
                        textToVerify.setText("Not Verified");
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

    private void changePasswordMethod() {
        if (etNewPassword.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(this, "Please enter your new password.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (etConfirmPassword.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(this, "Please enter your confirm password.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!commonApi.isInternetAvailable(this)) {
            Toast.makeText(this, Constant.NO_INTERNET, Toast.LENGTH_SHORT).show();
            return;
        }
        commonApi.showProgressDialog("");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", loginCredentials.getUserId());
            jsonObject.put("new_password", etNewPassword.getText().toString());
            jsonObject.put("confirm_password", etConfirmPassword.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new GetServiceCall(Urls.CHANGE_PASSWORD, GetServiceCall.TYPE_JSONOBJECT_POST, jsonObject) {
            @Override
            public void response(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("sStatus").equalsIgnoreCase("1")) {
                        loginCredentials.setUserPassword(etNewPassword.getText().toString());
                        commonApi.showDialog(ChangePasswordActivity.this, jsonObject.getString("sMessage"), "", getResources().getColor(R.color.black), true);

                    } else {
                        commonApi.showDialog(ChangePasswordActivity.this, jsonObject.getString("sMessage"), "", getResources().getColor(R.color.black), false);
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

}

